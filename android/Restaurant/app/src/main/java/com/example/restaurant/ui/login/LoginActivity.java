package com.example.restaurant.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.App;
import com.example.restaurant.R;
import com.example.restaurant.entities.Employee;
import com.example.restaurant.handlers.FailureError;
import com.example.restaurant.handlers.ResponseError;
import com.example.restaurant.ui.receipt.ReceiptsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NAME = "employee_name";

    private Spinner spinner;
    private ArrayAdapter<Employee> adapter;
    private List<Employee> employees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        spinner = findViewById(R.id.spinner_login);

        employees = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        new Thread(this::getWaiters).start();

        Button button = findViewById(R.id.button_login);

        button.setOnClickListener(view -> {
            if (login()) {
                Intent intent = new Intent(this, ReceiptsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_login_no_one_chosen), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getWaiters() {
        Call<List<Employee>> call = App.interfaceApi.getWaiters();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    employees.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    errorDownloadingWaiters(new ResponseError<>(response, LoginActivity.this).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                errorDownloadingWaiters(new FailureError(LoginActivity.this, t).getMessage());
            }
        });
    }

    private void errorDownloadingWaiters(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set properties
        builder.setMessage(message)
                .setTitle(R.string.error_downloading_waiters);

        // Add the buttons
        builder.setNegativeButton(R.string.error_try_again, (dialog, id) -> getWaiters());
        builder.setPositiveButton(R.string.error_close_application, (dialog, id) -> {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        });
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private boolean login() {
        if (spinner.getCount() > 0) {
            Employee employee = (Employee) spinner.getSelectedItem();

            SharedPreferences sharedPref = this.getSharedPreferences(
                    getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(EMPLOYEE_ID, employee.getId());
            editor.putString(EMPLOYEE_NAME, employee.getName());
            editor.apply();

            return true;
        }
        return false;
    }
}
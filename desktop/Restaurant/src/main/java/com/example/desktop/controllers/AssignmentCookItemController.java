package com.example.desktop.controllers;

import com.example.desktop.AppDatabase;
import com.example.desktop.entities.Employee;
import com.example.desktop.entities.Order;
import com.example.desktop.ui.CooksView;
import com.example.desktop.ui.ItemView;

import java.awt.event.WindowEvent;

public class AssignmentCookItemController {

    private final ItemView view;
    private Employee employee;
    private Order orderToAssign;
    private CooksView cooksView;

    private final AppDatabase db;

    public AssignmentCookItemController(Employee employee, ItemView view,
                                        Order orderToAssign, CooksView cooksView) {
        this.employee = employee;
        this.view = view;
        this.orderToAssign = orderToAssign;
        this.cooksView = cooksView;

        db = AppDatabase.getAppDatabase();
        updateView();
        updateActionListener();
    }

    private void updateView() {
        view.getLabel().setText(employee.toString());
    }

    private void updateActionListener() {
        view.getButton1().addActionListener(e -> assignCook());
    }

    private void assignCook() {
        db.setEmployeePreparingOrder(orderToAssign, employee.getId());
        cooksView.getFrameCooks().dispatchEvent(new WindowEvent(
                cooksView.getFrameCooks(), WindowEvent.WINDOW_CLOSING));
    }
}

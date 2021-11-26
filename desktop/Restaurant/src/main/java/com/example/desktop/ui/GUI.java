package com.example.desktop.ui;

import com.example.desktop.App;
import com.example.desktop.entities.Employee;
import com.example.desktop.entities.Order;
import com.example.desktop.entities.SpecialRequest;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class GUI {

    private final JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private final JPanel panelTop = new JPanel();

    public GUI() {

        JFrame frame = new JFrame("Kitchen application");
        frame.setMinimumSize(new Dimension(800, 450));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Border blackline = BorderFactory.createLineBorder(Color.black);

        panelLeft.setBorder(blackline);
        panelLeft.setPreferredSize(new Dimension(400, 150));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));

        panelRight.setBorder(blackline);
        panelRight.setPreferredSize(new Dimension(400, 150));

        panelTop.setBorder(blackline);
        panelTop.setPreferredSize(new Dimension(800, 25));
        panelTop.setBackground(Color.GRAY);

        JPanel panelLeftTitle = new JPanel();
        panelLeftTitle.setBorder(blackline);
        panelLeftTitle.setBackground(Color.DARK_GRAY);

        JLabel panelLeftTitleText = new JLabel("Orders placed:");
        panelLeftTitleText.setForeground(Color.WHITE);

        panelLeftTitle.add(panelLeftTitleText);
        panelLeft.add(panelLeftTitle);

        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        Vector<JPanel> orders = new Vector<JPanel>();

        for (int i = 0; i < 4; ++i) {
            for (Order order : App.getOrdersFromApi()) {
                JPanel orderPanel = new JPanel();
                orderPanel.setBorder(blackline);
                orderPanel.setPreferredSize(new Dimension(200, 30));
                JLabel date = new JLabel(order.getDate().toString());
                JLabel dish_name = new JLabel(order.getDish().getName());
                JLabel receiptId = new JLabel(order.getReceiptId().toString());
                orderPanel.add(date);
                orderPanel.add(dish_name);
                orderPanel.add(receiptId);
                for (SpecialRequest request: order.getRequests())
                    orderPanel.add(new JLabel(request.getRequest()));
                orders.add(orderPanel);
            }
        }

        for (JPanel order: orders) scrollablePanel.add(order);

        JScrollPane scrollFrame = new JScrollPane(scrollablePanel);
        scrollablePanel.setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(400, 350));

        panelLeft.add(scrollFrame);

        frame.add(panelLeft, BorderLayout.LINE_START);
        frame.add(panelRight, BorderLayout.LINE_END);
        frame.add(panelTop, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }
}
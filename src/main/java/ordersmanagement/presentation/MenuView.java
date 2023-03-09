package ordersmanagement.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JFrame{
    private JPanel panel1;
    private JButton ordersButton;
    private JButton clientsButton;
    private JButton productsButton;



    public MenuView() {


       // menuView.createUIMenu(menuView);
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientsView clientsView = new ClientsView("Clients");
                //clientsView.setVisible(true);

            }
        });
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductsView productsView = new ProductsView("Products");
                //productsView.setVisible(true);
            }
        });
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdersView ordersView = new OrdersView("Orders");
                //ordersView.setVisible(true);
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public static void main(String[] args){
        //new MenuView().openNewWindow("Order Management System :)");

        JFrame frame = new JFrame("MENUUUUUUUUUUUUUUUUUUUUUUU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MenuView().panel1);
        frame.pack();
        frame.setSize(450,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

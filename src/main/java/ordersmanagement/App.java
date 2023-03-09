package ordersmanagement;

import ordersmanagement.presentation.MenuView;

import javax.swing.*;

public class App {

    public static void main(String[] args){

        JFrame frame = new JFrame("Order Management Application :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MenuView().getPanel1());
        frame.pack();
        frame.setSize(450,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

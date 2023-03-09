package ordersmanagement.businesslogic.validators;

import ordersmanagement.model.Orders;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Checks if all the input data fields of the Order class are valid
 */

public class OrderValidator implements Validator<Orders>{

    @Override
    public boolean validate(Orders order) {

        int quantity = order.getQuantity();
        if(quantity <= 0) {
            showMessageDialog(null,new JLabel("The quantity must be a number greater than zero."),"Invalid input", JOptionPane.ERROR_MESSAGE );
        }
        return true;
    }
}

package ordersmanagement.businesslogic.validators;

import ordersmanagement.model.Product;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Checks if all the input data fields of the Product class are valid
 */

public class ProductValidator implements Validator<Product>{

    @Override
    public boolean validate(Product product) {

        int stock = product.getStock();
        if(stock < 0) {
//            throw new IllegalArgumentException("The stock cannot be a negative number!");
            showMessageDialog(null,new JLabel("The stock cannot be a negative number!"),"Invalid input", JOptionPane.ERROR_MESSAGE );
        }

        int price = product.getPrice();
        if(price < 0) {
//            throw new IllegalArgumentException("The price cannot be a negative number!");
            showMessageDialog(null,new JLabel("The price cannot be a negative number!"),"Invalid input", JOptionPane.ERROR_MESSAGE );
        }

        String name = product.getName();
        if(name.equals("")){
//            throw new IllegalArgumentException("Please enter a valid name!");
            showMessageDialog(null,new JLabel("Please enter a valid name!"),"Invalid input", JOptionPane.ERROR_MESSAGE );
        }
        return true;
    }
}

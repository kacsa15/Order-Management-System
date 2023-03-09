package ordersmanagement.presentation;


import ordersmanagement.businesslogic.validators.ProductValidator;
import ordersmanagement.dataaccess.OrderDAO;
import ordersmanagement.dataaccess.ProductDAO;
import ordersmanagement.model.Orders;
import ordersmanagement.model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * View class of the Product type object
 */

public class ProductsView extends JFrame{
    private JPanel panel1;
    private JButton deleteButton;
    private JButton showProductsButton;
    private JButton insertButton;
    private JButton updateButton1;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JTextField idTextField;
    private JTable productTable;


    public JTable getProductTable() {
        return productTable;
    }

    public void setProductTable(JTable productTable) {
        this.productTable = productTable;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public void setPriceTextField(JTextField priceTextField) {
        this.priceTextField = priceTextField;
    }

    public JTextField getStockTextField() {
        return stockTextField;
    }

    public void setStockTextField(JTextField stockTextField) {
        this.stockTextField = stockTextField;
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(JTextField idTextField) {
        this.idTextField = idTextField;
    }

    public ProductsView() {

        showProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO productDAO = new ProductDAO();
                List<Product> productsList = productDAO.findAll();

                productTable = new JTable();
                try {
                    setProductTable(productDAO.makeTable(productsList));
                    //TableModel tableModel =
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }

                showMessageDialog(null,new JScrollPane(productTable),"Clients Table", PLAIN_MESSAGE );

            }
        });

        updateButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ProductDAO productDAO = new ProductDAO();
                Product product = getData();


                Product toBeUpdated = productDAO.findById(product.getIdProduct());

                if(toBeUpdated == null)
                    showMessageDialog(null,new JLabel("The product with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );
                else{
                    if(Objects.equals(product.getName(), ""))
                        product.setName(toBeUpdated.getName());
                    if(product.getPrice() == -1)
                        product.setPrice(toBeUpdated.getPrice());
                    if(product.getStock() == -1)
                        product.setStock(toBeUpdated.getStock());

                    productDAO.update(product);

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ProductDAO productDAO = new ProductDAO();
                Product product = getData();

                Product toBeDeleted = productDAO.findById(product.getIdProduct());
                if(toBeDeleted != null){

                    OrderDAO orderDAO = new OrderDAO();

                    List<Orders> listOrders = orderDAO.findAll();
                    for(Orders orders1 : listOrders){
                        if(orders1.getIdProduct() == product.getIdProduct()){
                            //delete this order
                            orderDAO.delete(orders1);
                        }
                    }

                    productDAO.delete(product);
                }
                else
                if(toBeDeleted == null)
                    showMessageDialog(null,new JLabel("The product with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );

            }
        });

        insertButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO productDAO = new ProductDAO();
                Product product = getData();

                ProductValidator productValidator = new ProductValidator();
                if(productValidator.validate(product) && product.getIdProduct() != 0)
                    productDAO.insert(product);
                else{
                    showMessageDialog(null,new JLabel("Please enter a valid ID"),"Invalid input", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
    }


    public Product getData(){

        int idProduct = 0;
        String name = getNameTextField().getText();
        int price = -1;
        int stock = -1;

        if( !Objects.equals(getIdTextField().getText(), ""))
            idProduct = Integer.parseInt(getIdTextField().getText());

        if(!Objects.equals(getPriceTextField().getText(), ""))
            price = Integer.parseInt(String.valueOf(getPriceTextField().getText()));

        if(!Objects.equals(getStockTextField().getText(), ""))
            stock = Integer.parseInt(String.valueOf(getStockTextField().getText()));


        return new Product(idProduct,name,price,stock);

    }

    public ProductsView(String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(new ProductsView().panel1);
        frame.pack();
        frame.setSize(450,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

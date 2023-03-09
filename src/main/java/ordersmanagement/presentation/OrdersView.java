package ordersmanagement.presentation;

import ordersmanagement.businesslogic.validators.ClientValidator;
import ordersmanagement.businesslogic.validators.OrderValidator;
import ordersmanagement.dataaccess.ClientDAO;
import ordersmanagement.dataaccess.OrderDAO;
import ordersmanagement.dataaccess.ProductDAO;
import ordersmanagement.model.Client;
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
 * View class of the Order type object
 */

public class OrdersView extends JFrame{
    private JPanel panel1;
    private JButton deleteButton;
    private JButton showOrdersButton;
    private JButton insertButton;
    private JButton updateButton1;
    private JTextField clientIdTextField;
    private JTextField productIdTextField;
    private JTextField quantityTextField;
    private JTextField orderIdTextField;
    private JTable ordersTable;


    public JTextField getClientIdTextField() {
        return clientIdTextField;
    }

    public void setClientIdTextField(JTextField clientIdTextField) {
        this.clientIdTextField = clientIdTextField;
    }

    public JTextField getProductIdTextField() {
        return productIdTextField;
    }

    public void setProductIdTextField(JTextField productIdTextField) {
        this.productIdTextField = productIdTextField;
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public void setQuantityTextField(JTextField quantityTextField) {
        this.quantityTextField = quantityTextField;
    }

    public JTextField getOrderIdTextField() {
        return orderIdTextField;
    }

    public void setOrderIdTextField(JTextField orderIdTextField) {
        this.orderIdTextField = orderIdTextField;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }

    public void setOrdersTable(JTable ordersTable) {
        this.ordersTable = ordersTable;
    }

    public OrdersView(){
        showOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderDAO orderDAO = new OrderDAO();
                List<Orders> ordersList = orderDAO.findAll();

                ordersTable = new JTable();
                try {
                    setOrdersTable(orderDAO.makeTable(ordersList));
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                showMessageDialog(null,new JScrollPane(ordersTable),"Clients Table", PLAIN_MESSAGE,null );
            }
        });
        insertButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                OrderDAO orderDAO = new OrderDAO();
                Orders orders = getData();


                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.findById(orders.getIdProduct());

                OrderValidator orderValidator = new OrderValidator();


                int orderQuantity;

                if(orderValidator.validate(orders) && product != null){

                    orderQuantity = product.getStock() - orders.getQuantity();

                    if(orderQuantity >= 0){
                        product.setStock(orderQuantity);
                        productDAO.update(product);
                        orderDAO.insert(orders);
                    }
                }
                else
                    showMessageDialog(null,new JLabel("We do not have the required quantity."),"Invalid input", JOptionPane.ERROR_MESSAGE );

            }
        });
        updateButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                OrderDAO orderDAO = new OrderDAO();
                Orders orders = getData();


                Orders toBeUpdated = orderDAO.findById(orders.getIdOrders());

                if(toBeUpdated == null)
                    showMessageDialog(null,new JLabel("The order with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );
                else{
                    if(orders.getIdClient() == 0)
                        orders.setIdClient(toBeUpdated.getIdClient());
                    if(orders.getIdProduct() == 0)
                        orders.setIdProduct(toBeUpdated.getIdProduct());
                    if(orders.getQuantity() == -1)
                        orders.setQuantity(toBeUpdated.getQuantity());

                    orderDAO.update(orders);

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                OrderDAO orderDAO = new OrderDAO();
                Orders orders = getData();

                Orders toBeDeleted = orderDAO.findById(orders.getIdOrders());
                if(toBeDeleted != null)
                    orderDAO.delete(orders);
                else
                if(toBeDeleted == null)
                    showMessageDialog(null,new JLabel("The order with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );
            }
        });
    }

    public Orders getData(){

        int idOrders = 0;
        int idClient = 0;
        int idProduct = 0;
        int quantity = -1;

        if(!Objects.equals(getOrderIdTextField().getText(), ""))
            idOrders = Integer.parseInt(getOrderIdTextField().getText());

        if(!Objects.equals(getClientIdTextField().getText(), ""))
            idClient = Integer.parseInt(getClientIdTextField().getText());

        if(!Objects.equals(getProductIdTextField().getText(), ""))
            idProduct = Integer.parseInt(getProductIdTextField().getText());

        if(!Objects.equals(getQuantityTextField().getText(), ""))
            quantity = Integer.parseInt(getQuantityTextField().getText());

        return new Orders(idOrders,idClient,idProduct,quantity);

    }

    public OrdersView(String title) {
        JFrame frame = new JFrame(title);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new OrdersView().panel1);
        frame.pack();
        frame.setSize(450,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

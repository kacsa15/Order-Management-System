package ordersmanagement.presentation;

import ordersmanagement.businesslogic.validators.ClientValidator;
import ordersmanagement.dataaccess.ClientDAO;
import ordersmanagement.dataaccess.OrderDAO;
import ordersmanagement.model.Client;
import ordersmanagement.model.Orders;

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
 * View class of the Client type object
 */

public class ClientsView extends JFrame {
    private  JPanel panel1;
    private JButton deleteButton;
    private JButton showClientsButton;
    private JButton insertButton;
    private JButton updateButton1;
    private JTextField idClient;
    private JTextField nameClient;
    private JTextField addressClient;
    private JTextField emailClient;
    private JTextField ageClient;



    public JTextField getIdClient() {
        return idClient;
    }

    public JTextField getNameClient() {
        return nameClient;
    }

    public JTextField getAddressClient() {
        return addressClient;
    }

    public JTextField getEmailClient() {
        return emailClient;
    }

    public JTextField getAgeClient() {
        return ageClient;
    }



    public ClientsView() {

        showClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientDAO clientDAO = new ClientDAO();
                List<Client> clientList = clientDAO.findAll();

                JTable clientsTable = new JTable();
                try {
                    clientsTable = clientDAO.makeTable(clientList);
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                showMessageDialog(null,new JScrollPane(clientsTable),"Clients Table", PLAIN_MESSAGE );
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientDAO clientDAO = new ClientDAO();
                Client client = getData();
                ClientValidator clientValidator = new ClientValidator();
                if(clientValidator.validate(client) && client.getIdClient() != 0)
                    clientDAO.insert(client);
            }
        });

        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO();
                Client client = getData();


                Client toBeUpdated = clientDAO.findById(client.getIdClient());

                if(toBeUpdated == null)
                    showMessageDialog(null,new JLabel("The client with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );
                else{
                    if(Objects.equals(client.getName(), ""))
                        client.setName(toBeUpdated.getName());
                    if(Objects.equals(client.getEmail(), ""))
                        client.setEmail(toBeUpdated.getEmail());
                    if(Objects.equals(client.getAddress(), ""))
                        client.setAddress(toBeUpdated.getAddress());
                    if(client.getAge() == 0)
                        client.setAge(toBeUpdated.getAge());

                    clientDAO.update(client);

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO();
                Client client = getData();
                Client toBeDeleted = clientDAO.findById(client.getIdClient());
                if(toBeDeleted != null){

                    //if we delete a client we need to delete its orders too
                    OrderDAO orderDAO = new OrderDAO();


                    List<Orders> listOrders = orderDAO.findAll();
                    for(Orders orders1 : listOrders){
                        if(orders1.getIdClient() == client.getIdClient()){
                            //delete this order
                            orderDAO.delete(orders1);
                            System.out.println("yay");
                        }
                    }
                    clientDAO.delete(client);
                }
                else
                if(toBeDeleted == null)
                    showMessageDialog(null,new JLabel("The client with this ID does not exist"),"Invalid input", JOptionPane.ERROR_MESSAGE );


            }
        });
    }

    public Client getData(){

        int idClient = 0;
        String name = getNameClient().getText();
        String address = getAddressClient().getText();
        String email = getEmailClient().getText();
        int age = 0;

        if(!Objects.equals(getIdClient().getText(), ""))
            idClient = Integer.parseInt(getIdClient().getText());

        if(!Objects.equals(getAgeClient().getText(), ""))
            age = Integer.parseInt(String.valueOf(getAgeClient().getText()));

        System.out.println(age);

        return new Client(idClient,name,address,email,age);

    }


    public ClientsView(String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(new ClientsView().panel1);
        frame.pack();
        frame.setSize(450,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}

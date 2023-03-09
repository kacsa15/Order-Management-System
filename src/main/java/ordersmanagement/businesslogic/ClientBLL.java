package ordersmanagement.businesslogic;


import ordersmanagement.businesslogic.validators.EmailValidator;
import ordersmanagement.businesslogic.validators.Validator;
import ordersmanagement.dataaccess.ClientDAO;
import ordersmanagement.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());

        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client st = clientDAO.findById(id, "idClient");
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

}


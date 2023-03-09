package ordersmanagement.dataaccess;

import ordersmanagement.model.Client;

import java.util.List;

/**
 * ClientDAO extends the AbstractDAO class, getting all methods
 */
public class ClientDAO extends AbstractDAO<Client>{

    String field = "idClient";

    public ClientDAO() {

    }

    public Client findById(int id) {
        return super.findById(id, field);
    }

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }


    public void delete(Client client) {
        super.delete(client, field);
    }


    public void update(Client client) {
        super.update(client, field);
    }


    public void insert(Client client) {
        super.insert(client, field);
    }
}

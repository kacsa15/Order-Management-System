package ordersmanagement.dataaccess;

import ordersmanagement.model.Orders;

import java.util.List;

/**
 * OrderDAO extends the AbstractDAO class, getting all methods
 */

public class OrderDAO extends AbstractDAO<Orders>{

    String field = "idOrders";

    public OrderDAO(){

    }


    public Orders findById(int id) {
        return super.findById(id, field);
    }

    @Override
    public List<Orders> findAll() {
        return super.findAll();
    }


    public void delete(Orders orders) {
        super.delete(orders, field);
    }


    public void insert(Orders orders) {
        super.insert(orders, field);
    }

    public void update(Orders orders) {
        super.update(orders, field);
    }
}

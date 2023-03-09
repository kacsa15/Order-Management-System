package ordersmanagement.dataaccess;

import ordersmanagement.model.Product;

import java.util.List;

/**
 * ProductDAO extends the AbstractDAO class, getting all methods
 */

public class ProductDAO extends AbstractDAO<Product>{

    String field = "idProduct";

    public ProductDAO(){

    }


    public Product findById(int id) {
        return super.findById(id, field);
    }

    @Override
    public List<Product> findAll() {
        return super.findAll();
    }


    public void insert(Product product) {
        super.insert(product, field);
    }


    public void delete(Product product) {
        super.delete(product, field);
    }


    public void update(Product product) {
        super.update(product, field);
    }
}

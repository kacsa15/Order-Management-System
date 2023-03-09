package ordersmanagement.model;

/**
 * Model class: contains all the necessary fields and methods of a Order type object
 */

public class Orders {

    private int idOrders;
    private int idClient;
    private int idProduct;
    private int quantity;

    public Orders(int idOrders, int idClient, int idProduct, int quantity) {
        this.idOrders = idOrders;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
    public Orders(){

    }


    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

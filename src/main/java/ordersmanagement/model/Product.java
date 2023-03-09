package ordersmanagement.model;

/**
 * Model class: contains all the necessary fields and methods of a Product type object
 */

public class Product {

    private int idProduct;
    private String name;
    private int price;
    private int stock;

    public Product(int idProduct, String name, int price, int stock) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    public Product(){}

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

package ordersmanagement.model;

/**
 * Model class: contains all the necessary fields and methods of a Client type object
 */

public class Client {
    private int idClient;
    private String name;
    private String address;
    private String email;
    private int age;


    public Client(int idClient, String name, String address, String email, int age) {
        this.idClient = idClient;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }
    public Client(){

    }

    public Client(int idClient){
        this.idClient = idClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}

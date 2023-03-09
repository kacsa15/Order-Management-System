package ordersmanagement;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import ordersmanagement.dataaccess.ClientDAO;
import ordersmanagement.dataaccess.OrderDAO;
import ordersmanagement.dataaccess.ProductDAO;
import ordersmanagement.model.Client;
import ordersmanagement.model.Orders;
import ordersmanagement.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;


public class OrderBill {

    public static final String DEST = "C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_3\\Bills\\first_bill.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OrderBill().createPdf(DEST);

    }


    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Get the data for the first order
        OrderDAO orderDAO = new OrderDAO();
        ClientDAO clientDAO = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();

        Orders orders = orderDAO.findById(1);

        int idClient = orders.getIdClient();
        int idProduct = orders.getIdProduct();
        int quantity = orders.getQuantity();
        Client client = clientDAO.findById(idClient);
        Product product = productDAO.findById(idProduct);
        int finalPrice = quantity * product.getPrice();


        //Add paragraph to the document
        document.add(new Paragraph("Id of the order: " + String.valueOf(orders.getIdOrders())));
        document.add(new Paragraph("Client name: " + client.getName()));
        document.add(new Paragraph("Product name: " + product.getName()));
        document.add(new Paragraph("Quantity: " + quantity));
        document.add(new Paragraph("Final price: " + finalPrice));

        //Close document
        document.close();
    }
}

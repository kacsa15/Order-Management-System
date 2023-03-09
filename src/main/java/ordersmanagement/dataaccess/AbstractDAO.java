package ordersmanagement.dataaccess;

import ordersmanagement.connection.ConnectionFactory;
import ordersmanagement.model.Client;
import ordersmanagement.model.Orders;
import ordersmanagement.model.Product;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 *
 * This class implements operations to be applied to classes, like Product, Client or Order
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createQuery(String field, String command, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" WHERE " + field + " = " + id);
        return sb.toString();
    }

    private String createDeleteQuery(String field, String command, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        sb.append("FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" WHERE " + field + " = " + id);
        return sb.toString();
    }

    private String findALLQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        return sb.toString();
    }


    /**
     *
     * @return returns the data from a table
     */
    public List<T> findAll() {

        String query = findALLQuery();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }


    /**
     *
     * @param id search by the class id and return it, if it was found
     * @param field specifies the id type of the class (idClient, idProduct, idOrders)
     * @return null if the object was not found, otherwise it returns the object
     */
    public T findById(int id, String field) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = createQuery(field, "SELECT ", id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if(objects.isEmpty())
                return null;
            return objects.get(0);


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }


    private String createInsertQueryBeginning() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" VALUES ( ");
        return sb.toString();
    }

    /**
     *
     * @param t - insert the object t in the table
     * @param field - specifies the type of the id
     */

    public void insert(T t, String field) {

        Field[] fields = t.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder(createInsertQueryBeginning());
        fields[0].setAccessible(true);
        Object id = null;

        try {
           id = fields[0].get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(findById((Integer) id, field) == null) {

            for (Field field1 : fields) {
                field1.setAccessible(true);
                try {
                    Object value = field1.get(t);
                    if (value.getClass().getSimpleName().equals("String")) {
                        value = "'" + value + "'";
                    }
                    query.append(value);

                    query.append(" , ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);

            query.append(") ;");


            executeDatabase(query);
        }
        else {
            showMessageDialog(null,new JLabel("An item with this ID already exists, please choose another ID"),"Invalid input", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Creates the connection between the application and the database
     * it is a secondary, helper function
     * @param query to be executed
     */
    private void executeDatabase(StringBuilder query) {
        String queryString = String.valueOf(query);
        Connection connection = null;
        PreparedStatement statementUpdate = null;

        try {
            connection = ConnectionFactory.getConnection();
            statementUpdate = connection.prepareStatement(queryString);
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statementUpdate);
            ConnectionFactory.close(connection);
        }
    }


    private String createUpdateQueryBeginning() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" SET ");
        return sb.toString();
    }

    /**
     *
     * @param t - updates the object t in the table
     * @param field specifies the type of the id
     */
    public void update(T t, String field) {
        Field[] fields = t.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder(createUpdateQueryBeginning());
        fields[0].setAccessible(true);
        Object idValue = null;
        try {
            idValue = fields[0].get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int id = (Integer) idValue;
        T updatedField = findById(id, field);
        if( updatedField != null){
            int i=1;
            while (i<fields.length){
                fields[i].setAccessible(true);
                query.append(fields[i].getName()).append(" = ");

                try {
                    Object value = fields[i].get(t);
                    String typeValue = value.getClass().getSimpleName();
                    if(typeValue.equals("String")){
                        value = "'" + value + "'";
                    }
                    query.append(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                query.append(", ");
                i++;
            }
        }
        query.deleteCharAt(query.length()-1);
        query.deleteCharAt(query.length()-1);


        query.append(" WHERE " + field + " = ");
        query.append(idValue);

        executeDatabase(query);

    }

    /**
     *
     * @param t- deletes this object from the application
     * @param field specifies the type of the id
     */
    public void delete(T t, String field) {

        Field[] fields = t.getClass().getDeclaredFields();
        Field id = fields[0];
        id.setAccessible(true);
        Object value = null;

        try {
            value = id.get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int valueId = 0;
        if(value != null){
             valueId = (Integer) value;
        }

        if(findById(valueId, field) == null){
            System.out.println("error occured ERROR ERROR ERROR ");
        }else{
            Connection connection = null;
            PreparedStatement statementDelete = null;
            String query = createDeleteQuery(field, "DELETE ", valueId);

            try {
                connection = ConnectionFactory.getConnection();
                statementDelete = connection.prepareStatement(query);
                statementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                ConnectionFactory.close(statementDelete);
                ConnectionFactory.close(connection);
            }
        }
    }

    /**
     *
     * @param tList based on this parameter this function creates the table to be displayed on the screen
     * @return returns a JTable, which can be seen on the user interface of the application
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public JTable makeTable(List<T> tList) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        if(tList!=null && tList.size() > 0){
            int i=0;
            String[] columns = new String[tList.get(0).getClass().getDeclaredFields().length];
            for(Field field : tList.get(0).getClass().getDeclaredFields()){
                columns[i] = field.getName();
                i++;
            }


            int j=0;
            Object[][] table = new Object[tList.size()+1][i];
            for(T object : tList){
                int k=0;
                for(Field field : object.getClass().getDeclaredFields()){
                    String fieldName = field.getName();
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,object.getClass());
                    Method method = propertyDescriptor.getReadMethod();
                    table[j][k]=method.invoke(object);
                    k++;
                }
                j++;
            }
            return new JTable(table,columns);
        }
        return new JTable(1,0);
    }
}

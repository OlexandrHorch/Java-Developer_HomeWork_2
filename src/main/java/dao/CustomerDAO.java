package dao;

import entity.Customer;
import entity.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static service.ConnectionDB.*;

public class CustomerDAO {
    public CustomerDAO() {
        initDbDriver();
        initConnection();
        initPreparedStatements();
    }


    private void initDbDriver() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;

    private void initConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl, DB_LOGIN, DB_PASSWORD);
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Statement st;
    private PreparedStatement createSt;
    private PreparedStatement updateSt;


    private void initPreparedStatements() {
        try {
            createSt = connection.prepareStatement("INSERT INTO customers (name, address) VALUES (?, ?)");

            updateSt = connection.prepareStatement("UPDATE customers SET name=?, address=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createCustomer(Customer customer) {
        try {
            createSt.setString(1, customer.getName());
            createSt.setString(2, customer.getAddress());

            createSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Customer getCustomerById(long customerId) {
        String sql = "SELECT id, name, address FROM customers WHERE id=" + customerId;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

            if (rs.first()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                return customer;

            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return null;
    }


    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<Customer>();

        String sql = "SELECT id, name, address FROM customers";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                result.add(customer);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }


    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateCustomer(Customer customer) {
        try {
            updateSt.setString(1, customer.getName());
            updateSt.setString(2, customer.getAddress());
            updateSt.setLong(3, customer.getId());
            updateSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCustomerById(long customerId) {
        String sqlFKChecks0 = "SET FOREIGN_KEY_CHECKS=0";
        String sqlFKChecks1 = "SET FOREIGN_KEY_CHECKS=1";

        String sqlCustomers = "DELETE FROM customers WHERE id=" + customerId;
        String sqlProjects = "DELETE FROM projects WHERE id_customer=" + customerId;
        String sqlDevelopersProjects = "DELETE FROM developers_projects WHERE id_project IN" +
                "(SELECT id FROM projects WHERE id_customer=" + customerId + ")";

        try {
            st.executeUpdate(sqlFKChecks0);

            st.executeUpdate(sqlDevelopersProjects);
            st.executeUpdate(sqlProjects);
            st.executeUpdate(sqlCustomers);

            st.executeUpdate(sqlFKChecks1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
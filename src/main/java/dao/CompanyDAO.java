package dao;

import entity.Company;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static service.ConnectionDB.*;

public class CompanyDAO {
    public CompanyDAO() {
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
            createSt = connection.prepareStatement("INSERT INTO companies (name, address) VALUES (?, ?)");

            updateSt = connection.prepareStatement("UPDATE companies SET name=?, address=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createCompany(Company company) {
        try {
            createSt.setString(1, company.getName());
            createSt.setString(2, company.getAddress());

            createSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Company getCompanyById(long companyId) {
        String sql = "SELECT id, name, address FROM companies WHERE id=" + companyId;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

            if (rs.first()) {
                Company company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
                return company;

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


    public List<Company> getAllCompanies() {
        List<Company> result = new ArrayList<Company>();

        String sql = "SELECT id, name, address FROM companies";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
                result.add(company);
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


    public void updateCompany(Company company) {
        try {
            updateSt.setString(1, company.getName());
            updateSt.setString(2, company.getAddress());
            updateSt.setLong(3, company.getId());
            updateSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCompanyById(long companyId) {
        String sqlFKChecks0 = "SET FOREIGN_KEY_CHECKS=0";
        String sqlFKChecks1 = "SET FOREIGN_KEY_CHECKS=1";

        String sqlCompanies = "DELETE FROM companies WHERE id=" + companyId;
        String sqlDevelopers = "DELETE FROM developers WHERE id_company=" + companyId;
        String sqlProjects = "DELETE FROM projects WHERE id_company=" + companyId;
        String sqlDevelopersProject = "DELETE FROM developers_projects WHERE id_project IN" +
                "(SELECT id FROM projects WHERE id_company=" + companyId + ")";
        String sqlDevelopersSkills = "DELETE FROM developers_skills WHERE id_developer IN" +
                "(SELECT id FROM developers WHERE id_company=" + companyId + ")";

        try {
            st.executeUpdate(sqlFKChecks0);

            st.executeUpdate(sqlDevelopersProject);
            st.executeUpdate(sqlDevelopersSkills);
            st.executeUpdate(sqlDevelopers);
            st.executeUpdate(sqlProjects);
            st.executeUpdate(sqlCompanies);

            st.executeUpdate(sqlFKChecks1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
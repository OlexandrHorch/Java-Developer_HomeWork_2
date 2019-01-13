package dao;

import entity.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static service.ConnectionDB.*;

public class DeveloperDAO {
    public DeveloperDAO() {
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
            createSt = connection.prepareStatement("INSERT INTO developers (first_name, age, id_company, salary) VALUES (?, ?, ?, ?)");

            updateSt = connection.prepareStatement("UPDATE developers SET first_name=?, age=?, id_company=?, salary=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createDeveloper(Developer developer) {
        try {
            createSt.setString(1, developer.getFirstName());
            createSt.setInt(2, developer.getAge());
            createSt.setLong(3, developer.getIdCompany());
            createSt.setInt(4, developer.getSalary());

            createSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Developer getDeveloperById(long developerId) {
        String sql = "SELECT id, first_name, age, id_company, salary FROM developers WHERE id=" + developerId;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

            if (rs.first()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                return developer;

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


    public List<Developer> getDevelopersFromCompanyByProjectId(long idProject) {
        List<Developer> result = new ArrayList<Developer>();

        String sql = "SELECT id, first_name, age, id_company, salary FROM developers WHERE id_company IN" +
                "(SELECT id_company FROM projects WHERE id=" + idProject + ")";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }


    public List<Developer> getAllDevelopers() {
        List<Developer> result = new ArrayList<Developer>();

        String sql = "SELECT id, first_name, age, id_company, salary FROM developers";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);
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


    public void updateDeveloper(Developer developer) {
        try {
            updateSt.setString(1, developer.getFirstName());
            updateSt.setInt(2, developer.getAge());
            updateSt.setLong(3, developer.getIdCompany());
            updateSt.setInt(4, developer.getSalary());
            updateSt.setLong(5, developer.getId());
            updateSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteDeveloperById(long developerId) {
        String sqlFKChecks0 = "SET FOREIGN_KEY_CHECKS=0";
        String sqlFKChecks1 = "SET FOREIGN_KEY_CHECKS=1";

        String sqlDevelopers = "DELETE FROM developers WHERE id=" + developerId;
        String sqlDevelopersSkills = "DELETE FROM developers_skills WHERE id_developer=" + developerId;
        String sqlDevelopersProjects = "DELETE FROM developers_projects WHERE id_developer=" + developerId;

        try {
            st.executeUpdate(sqlFKChecks0);

            st.executeUpdate(sqlDevelopersSkills);
            st.executeUpdate(sqlDevelopersProjects);
            st.executeUpdate(sqlDevelopers);

            st.executeUpdate(sqlFKChecks1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getSumDevelopersSalaryInProjectByProjectId(long id) {
        String sql = "SELECT sum(salary) FROM developers WHERE id IN" +
                "(SELECT id_developer FROM developers_projects WHERE id_project IN" +
                "(SELECT id FROM projects WHERE id=" + id + "))";

        ResultSet rs = null;
        int sumSalary = 0;

        try {
            rs = st.executeQuery(sql);
            rs.first();
            sumSalary = rs.getInt("sum(salary)");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return sumSalary;
    }


    public List<Developer> getAllDevelopersByProjectId(long id) {
        List<Developer> result = new ArrayList<Developer>();

        String sql = "SELECT id, first_name, age, id_company, salary FROM developers WHERE id IN" +
                "(SELECT id_developer FROM developers_projects WHERE id_project=" + id + ")";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }


    public List<Developer> getAllJavaDevelopers() {
        List<Developer> result = new ArrayList<Developer>();

        String sql = "SELECT id, first_name, age, id_company, salary FROM developers WHERE id IN" +
                "(SELECT id_developer FROM developers_skills WHERE id_skill IN" +
                "(SELECT id FROM skills WHERE area='Java'))";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }


    public List<Developer> getAllMiddleDevelopers() {
        List<Developer> result = new ArrayList<Developer>();

        String sql = "SELECT id, first_name, age, id_company, salary FROM developers WHERE id IN" +
                "(SELECT id_developer FROM developers_skills WHERE id_skill IN" +
                "(SELECT id FROM skills WHERE level='middle'))";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setAge(rs.getInt("age"));
                developer.setIdCompany(rs.getLong("id_company"));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }
}
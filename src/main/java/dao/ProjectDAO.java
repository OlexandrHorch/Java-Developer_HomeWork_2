package dao;

import entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static service.ConnectionDB.*;

public class ProjectDAO {
    public ProjectDAO() {
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
            createSt = connection.prepareStatement("INSERT INTO projects (name, id_company, id_customer, cost) VALUES (?, ?, ?, ?)");

            updateSt = connection.prepareStatement("UPDATE projects SET name=?, id_company=?, id_customer=?, cost=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createProject(Project project) {
        try {

            createSt.setString(1, project.getName());
            createSt.setLong(2, project.getIdCompany());
            createSt.setLong(3, project.getIdCustomer());
            createSt.setInt(4, project.getCost());

            createSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Project getProjectById(long projectId) {
        String sql = "SELECT id, name, id_company, id_customer, cost FROM projects WHERE id=" + projectId;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

            if (rs.first()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));
                project.setIdCompany(rs.getLong("id_company"));
                project.setIdCustomer(rs.getLong("id_customer"));
                project.setCost(rs.getInt("cost"));
                return project;

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


    public List<Project> getAllProjects() {
        List<Project> result = new ArrayList<Project>();

        String sql = "SELECT id, name, id_company, id_customer, cost FROM projects";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));
                project.setIdCompany(rs.getLong("id_company"));
                project.setIdCustomer(rs.getLong("id_customer"));
                project.setCost(rs.getInt("cost"));
                result.add(project);
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


    public void updateProjectById(Project project) {
        try {
            updateSt.setString(1, project.getName());
            updateSt.setLong(2, project.getIdCompany());
            updateSt.setLong(3, project.getIdCustomer());
            updateSt.setInt(4, project.getCost());
            updateSt.setLong(5, project.getId());
            updateSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteProjectById(long projectId) {
        String sqlProjects = "DELETE FROM projects WHERE id=" + projectId;
        String sqlDevelopersProjects = "DELETE FROM developers_projects WHERE id_project=" + projectId;

        try {

            st.executeUpdate(sqlDevelopersProjects);
            st.executeUpdate(sqlProjects);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void assignDeveloperToProject(long idDeveloper, long idProject) {
        String sql = "INSERT INTO developers_projects (id_developer, id_project) VALUES" +
                "(" + idDeveloper + ", " + idProject + ")";

        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
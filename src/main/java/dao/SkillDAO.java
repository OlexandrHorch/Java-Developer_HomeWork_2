package dao;

import entity.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static service.ConnectionDB.*;

public class SkillDAO {
    public SkillDAO() {
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
            createSt = connection.prepareStatement("INSERT INTO skills (area, level) VALUES (?, ?)");

            updateSt = connection.prepareStatement("UPDATE skills SET area=?, level=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createSkill(Skill skill) {
        try {
            createSt.setString(1, skill.getArea());
            createSt.setString(2, skill.getLevel());

            createSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Skill getSkillById(long skillId) {
        String sql = "SELECT id, area, level FROM skills WHERE id=" + skillId;

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

            if (rs.first()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));
                skill.setArea(rs.getString("area"));
                skill.setLevel(rs.getString("level"));
                return skill;

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


    public List<Skill> getAllSkills() {
        List<Skill> result = new ArrayList<Skill>();

        String sql = "SELECT id, area, level FROM skills";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));
                skill.setArea(rs.getString("area"));
                skill.setLevel(rs.getString("level"));
                result.add(skill);
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


    public void updateSkill(Skill skill) {
        try {
            updateSt.setString(1, skill.getArea());
            updateSt.setString(2, skill.getLevel());
            updateSt.setLong(3, skill.getId());
            updateSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteSkillById(long skillId) {
        String sqlFKChecks0 = "SET FOREIGN_KEY_CHECKS=0";
        String sqlFKChecks1 = "SET FOREIGN_KEY_CHECKS=1";

        String sqlSkills = "DELETE FROM skills WHERE id=" + skillId;
        String sqlDevelopersSkills = "DELETE FROM developers_skills WHERE id_skill=" + skillId;

        try {
            st.executeUpdate(sqlFKChecks0);

            st.executeUpdate(sqlDevelopersSkills);
            st.executeUpdate(sqlSkills);

            st.executeUpdate(sqlFKChecks1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Skill> getSkillsByDeveloperId(long idDeveloper) {
        List<Skill> result = new ArrayList<Skill>();

        String sql = "SELECT id, area, level FROM skills WHERE id IN" +
                "(SELECT id_skill FROM developers_skills WHERE id_developer=" + idDeveloper + ")";

        ResultSet rs = null;

        try {
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));
                skill.setArea(rs.getString("area"));
                skill.setLevel(rs.getString("level"));
                result.add(skill);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }


    public void assignSkillToDeveloper(long idDeveloper, long idSkill) {
        String sql = "INSERT INTO developers_skills (id_developer, id_skill) VALUES" +
                "(" + idDeveloper + ", " + idSkill + ")";

        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
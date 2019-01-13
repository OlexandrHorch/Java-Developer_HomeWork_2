package service;

public class ConnectionDB {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String SERVER_PATH = "localhost:3306";
    public static String DB_NAME = "it_relationship";
    public static String DB_LOGIN = "root";
    public static String DB_PASSWORD = "root";

    public static String connectionUrl = "jdbc:mysql://" + SERVER_PATH + "/" + DB_NAME +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
}
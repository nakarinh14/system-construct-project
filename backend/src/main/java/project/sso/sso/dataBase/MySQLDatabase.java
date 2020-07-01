package project.sso.sso.dataBase;

import java.sql.*;

public abstract class MySQLDatabase<IDtype> {
    public static final String jdbcDriverStr = "com.mysql.cj.jdbc.Driver";
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/construct_project?" + "user=root&password=A1234567!";

    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;

    public MySQLDatabase() {
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public abstract boolean deletedata(IDtype id);

    public abstract ResultSet getDataSet();

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

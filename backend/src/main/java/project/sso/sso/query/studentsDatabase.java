package project.sso.sso.query;

import java.sql.ResultSet;

public class studentsDatabase extends MySQLDatabase<String> {

    public String getIDbyStudent(String firstname, String lastname, String year) {
        try {
            preparedStatement = connection.prepareStatement("select id FROM construct_project.students where firstname=? and lastname=? and year=?;");
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, year);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public boolean addStudent(String id, String firstname, String lastname, String year) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.students values (?,?,?,?);");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, year);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletedata(String id) {
        try {
            preparedStatement = connection.prepareStatement("delete from construct_project.students where id=?;");
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getDataSet() {
        try {
            return statement.executeQuery("select id, firstname, lastname, year from construct_project.students;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

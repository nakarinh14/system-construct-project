package project.sso.sso.dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class sectionsDatabase extends MySQLDatabase<String> {

    public String getIDbySection(String time, String section) {
        try {
            preparedStatement = connection.prepareStatement("select id FROM construct_project.sections where section=? and time=?;");
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, time);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public boolean addSection(String id, String time, String section, String registered, String capacity, String instructorid) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.sections values (?,?,?,?,?,?,DEFAULT,DEFAULT);");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, section);
            preparedStatement.setString(4, registered);
            preparedStatement.setString(5, capacity);
            preparedStatement.setString(6, instructorid);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSection(String id, String time, String section, String registered, String capacity, String instructorid, String info) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.sections values (?,?,?,?,?,?,?,DEFAULT);");
            addSecData(id, time, section, registered, capacity, instructorid, info);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addSecData(String id, String time, String section, String registered, String capacity, String instructorid, String info) throws SQLException {
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, time);
        preparedStatement.setString(3, section);
        preparedStatement.setString(4, registered);
        preparedStatement.setString(5, capacity);
        preparedStatement.setString(6, instructorid);
        preparedStatement.setString(7, info);
    }

    public boolean addSection(String id, String time, String section, String registered, String capacity, String instructorid, String info, String sectionscol) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.sections values (?,?,?,?,?,?,?,?);");
            addSecData(id, time, section, registered, capacity, instructorid, info);
            preparedStatement.setString(8, sectionscol);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletedata(String id) {
        try {
            preparedStatement = connection.prepareStatement("delete from construct_project.sections where id=?;");
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultSet getDataSet() {
        try {
            return statement.executeQuery("select id, time, section, registered, capacity, instructorid, info, sectionscol from construct_project.sections;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

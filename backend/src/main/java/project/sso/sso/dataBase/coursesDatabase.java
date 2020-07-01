package project.sso.sso.dataBase;


import java.sql.ResultSet;

public class coursesDatabase extends MySQLDatabase<String> {


    public String getIDbyCourse(String division, String name) {
        try {
            preparedStatement = connection.prepareStatement("select id FROM construct_project.courses where division=? and name=?;");
            preparedStatement.setString(1, division);
            preparedStatement.setString(2, name);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public String getName(String id) {
        try {
            preparedStatement = connection.prepareStatement("select name from construct_project.courses where id=?;");
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDivision(String id) {
        try {
            preparedStatement = connection.prepareStatement("select division from construct_project.courses where id=?;");
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("division");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addCourse(String id, String division, String name) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.courses values (?,?,?);");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, division);
            preparedStatement.setString(3, name);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editCourse(String id, String division, String name) {
        try {
            preparedStatement = connection.prepareStatement("update construct_project.courses set division=?, name=? where id=?;");
            preparedStatement.setString(1, division);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, id);
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
            preparedStatement = connection.prepareStatement("delete from construct_project.courses where id=?;");
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
            return statement.executeQuery("select id, division, name from construct_project.courses;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

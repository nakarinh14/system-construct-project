package project.sso.sso.dataBase;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.ResultSet;

public class loginDatabase extends MySQLDatabase<String> {

    public boolean addNewLogin(String id, String pwd) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.login values (?,?);");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, BCrypt.hashpw(pwd, BCrypt.gensalt()));
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String id, String pwd) {
        try {
            preparedStatement = connection.prepareStatement("select password from construct_project.login where id=?;");
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                String passwordHash = resultSet.getString("password");
                return BCrypt.checkpw(pwd, passwordHash);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletedata(String id) {
        try {
            preparedStatement = connection.prepareStatement("delete from construct_project.login where id=?;");
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
            return statement.executeQuery("select id from construct_project.login;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

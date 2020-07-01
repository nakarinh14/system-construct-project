package project.sso.sso.dataBase;

import java.sql.ResultSet;

public class enrollmentDatabase extends MySQLDatabase<Integer> {

    public Integer getIDbyEnroll(String student_id, String course_id) {
        try {
            preparedStatement = connection.prepareStatement("select id FROM construct_project.enrollment where student_id=? and course_id=?;");
            preparedStatement.setString(1, student_id);
            preparedStatement.setString(2, course_id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getStudentID(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("select student_id from construct_project.enrollment where id=?;");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("student_id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCourseID(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("select course_id from construct_project.enrollment where id=?;");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("course_id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addEnrollment(Integer id, String student_id, String course_id) {
        try {
            preparedStatement = connection.prepareStatement("insert into construct_project.enrollment values (?,?,?);");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, student_id);
            preparedStatement.setString(3, course_id);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editEnrollment(Integer id, String student_id, String course_id) {
        try {
            preparedStatement = connection.prepareStatement("update construct_project.enrollment set student_id=?, course_id=? where id=?;");
            preparedStatement.setString(1, student_id);
            preparedStatement.setString(2, course_id);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletedata(Integer id) {
        try {
            preparedStatement = connection.prepareStatement("delete from construct_project.enrollment where id=?;");
            preparedStatement.setInt(1, id);
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
            return statement.executeQuery("select id, student_id, course_id from construct_project.enrollment;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

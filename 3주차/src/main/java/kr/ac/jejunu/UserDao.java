package kr.ac.jejunu;

import java.sql.*;

public class UserDao {

    public User findById(Integer id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
            DriverManager
                .getConnection("jdbc:mysql://localhost/portal?characterEncoding=utf-8&serverTimezone=UTC"
                ,"root","1234");
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM userinfo WHERE id=?"
        );
        ps.setInt(1,id);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        ps.close();
        connection.close();

        return user;
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager
                        .getConnection("jdbc:mysql://localhost/portal?characterEncoding=utf-8&serverTimezone=UTC"
                                ,"root","1234");
        PreparedStatement ps = connection.prepareStatement(
                "insert into userinfo (name, password) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, user.getName());
        ps.setString(2,user.getPassword());

        ps.executeUpdate();

        ResultSet resultSet = ps.getGeneratedKeys();
        resultSet.next();

        user.setId(resultSet.getInt(1));

        resultSet.close();
        ps.close();
        connection.close();
    }
}

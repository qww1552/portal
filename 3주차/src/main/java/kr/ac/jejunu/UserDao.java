package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findById(Integer id) throws SQLException {
        User user=null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM userinfo WHERE id=?"
            );
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }

            resultSet.close();
            ps.close();
        }

        return user;
    }

    public void insert(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into userinfo (name, password) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getInt(1));

            resultSet.close();
            ps.close();

        }
    }

    public void update(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "update userinfo set name=?, password=? where id=?",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3,user.getId());
            ps.executeUpdate();

            ps.close();

        }
    }

    public void delete(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "delete from userinfo where id=?",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1,id);
            ps.executeUpdate();

            ps.close();

        }
    }
}

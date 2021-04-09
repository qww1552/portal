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
            StatementStrategy statementStrategy = new FindByIdStatementStrategy();
            PreparedStatement ps = statementStrategy.makeStatement(id, connection);
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
            StatementStrategy statementStrategy = new InsertStatementStrategy();
            PreparedStatement ps = statementStrategy.makeStatement(user, connection);
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
            StatementStrategy statementStrategy = new UpdateStatementStrategy();
            PreparedStatement ps = statementStrategy.makeStatement(user, connection);
            ps.executeUpdate();

            ps.close();
        }
    }

    public void delete(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            StatementStrategy statementStrategy = new DeleteStatementStrategy();
            PreparedStatement ps = statementStrategy.makeStatement(id, connection);
            ps.executeUpdate();

            ps.close();

        }
    }
}

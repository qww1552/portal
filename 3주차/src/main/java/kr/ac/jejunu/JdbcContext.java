package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = statementStrategy.makeStatement(connection);
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getInt(1));

            resultSet.close();
            ps.close();
        }
    }

    User jdbcContextForFindById(StatementStrategy statementStrategy) throws SQLException {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = statementStrategy.makeStatement(connection);
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

    void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = statementStrategy.makeStatement(connection);
            ps.executeUpdate();

            ps.close();
        }
    }
}
package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void update(String sql, Object[] params) throws SQLException {
        jdbcContextForUpdate((connection) -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql
            );
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            return ps;
        });
    }
    public void insert(User user, String sql, Object[] params) throws SQLException {
        jdbcContextForInsert(user, connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            return ps;
        });
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
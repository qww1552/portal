package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertStatementStrategy implements StatementStrategy {
    private final User user;

    public InsertStatementStrategy(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into userinfo (name, password) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());
        return ps;
    }
}
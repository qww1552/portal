package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateStatementStrategy implements StatementStrategy {
    private final User user;

    public UpdateStatementStrategy(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "update userinfo set name=?, password=? where id=?",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());
        ps.setInt(3,user.getId());
        return ps;
    }
}

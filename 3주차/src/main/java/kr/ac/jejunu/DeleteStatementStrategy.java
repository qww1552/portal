package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteStatementStrategy implements StatementStrategy {
    private final Integer id;

    public DeleteStatementStrategy(Integer id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "delete from userinfo where id=?",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setInt(1,id);
        return ps;
    }
}

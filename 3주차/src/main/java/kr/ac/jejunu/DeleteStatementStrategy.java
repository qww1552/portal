package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteStatementStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makeStatement(Object object, Connection connection) throws SQLException {
        Integer id = (Integer) object;
        PreparedStatement ps = connection.prepareStatement(
                "delete from userinfo where id=?",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setInt(1,id);
        return ps;
    }
}

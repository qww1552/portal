package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindByIdStatementStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makeStatement(Object object, Connection connection) throws SQLException {
        Integer id = (Integer) object;
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM userinfo WHERE id=?"
        );
        ps.setInt(1, id);
        return ps;
    }
}

package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindByIdStatementStrategy implements StatementStrategy {
    private final Integer id;

    public FindByIdStatementStrategy(Integer id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM userinfo WHERE id=?"
        );
        ps.setInt(1, id);
        return ps;
    }
}

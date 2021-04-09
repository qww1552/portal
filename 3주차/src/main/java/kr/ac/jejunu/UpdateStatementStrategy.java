package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateStatementStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makeStatement(Object object, Connection connection) throws SQLException {
        User user = (User) object;
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

package kr.ac.jejunu;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Integer id) {
        String sql = "SELECT * FROM userinfo WHERE id=?";
        Object[] params = {id};
        return jdbcTemplate.query(sql, rs -> {
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        },id);
    }
//    private User findById(String sql, Object[] params) throws SQLException {
//        return jdbcTemplate.get (connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            for (int i = 0; i < params.length; i++) {
//                ps.setObject(i + 1, params[i]);
//            }
//            return ps;
//        });
//    }

    public void update(User user) throws SQLException {
        String sql = "update userinfo set name=?, password=? where id=?";
        Object[] params = {user.getName(), user.getPassword(), user.getId()};
        jdbcTemplate.update(sql, params);
    }

    public void delete(Integer id) throws SQLException {
        String sql = "delete from userinfo where id=?";
        Object[] params = {id};
        jdbcTemplate.update(sql, params);
    }

    public void insert(User user) throws SQLException {
        String sql = "insert into userinfo (name, password) values (?, ?)";
        Object[] params = {user.getName(), user.getPassword()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1,params[i]);
            }
            return preparedStatement;
        },keyHolder);
        user.setId(keyHolder.getKey().intValue());
    }

}

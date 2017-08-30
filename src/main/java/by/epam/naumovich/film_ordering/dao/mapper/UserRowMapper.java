package by.epam.naumovich.film_ordering.dao.mapper;

import by.epam.naumovich.film_ordering.bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Spring JDBC RowMapper implementation which maps User table in database on User entity.
 *
 * @author Dzmitry_Naumovich
 * @version 1.0
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setLogin(rs.getString(2));
        user.setName(rs.getString(3));
        user.setSurname(rs.getString(4));
        user.setPassword(rs.getString(5));
        user.setSex(rs.getString(6).charAt(0));
        user.setType(rs.getString(7).charAt(0));
        user.setRegDate(rs.getDate(8));
        user.setRegTime(rs.getTime(9));
        user.setBirthDate(rs.getDate(10));
        user.setPhone(rs.getString(11));
        user.setEmail(rs.getString(12));
        user.setAbout(rs.getString(13));
        return user;
    }
}

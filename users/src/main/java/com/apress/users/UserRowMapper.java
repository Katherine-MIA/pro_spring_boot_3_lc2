package com.apress.users;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// The book doesn't say much about this yet but it does say that this is an implementation
// of the functional interface RowMapper used for giving a custom implementation to the mapRow()
// function which is used together with JdbcTemplate class for mapping the rows of a java.sql.ResultSet
// result one row at a time.
// So to me this is the translation of the result yielded by SQL cal to the objects in the java server/application.
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Array array = resultSet.getArray("user_role");
        String[] rolesArray = Arrays.copyOf((Object[])array.getArray(),
                ((Object[])array.getArray()).length,
                String[].class);
        List<UserRole> roles = Arrays.stream(rolesArray)
                .map(UserRole::valueOf).collect(Collectors.toList());

        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .userRoles(roles)
                .build();
        // return newUser; -> Sonar says return the creating statement of user instead of placing it in a variable
        // and returning that.
    }
}

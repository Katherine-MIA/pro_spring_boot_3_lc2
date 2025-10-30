package com.apress.users;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository implements SimpleRepository<User, Integer> {
    // Automatically @Autowired by Spring Boot autoconfiguration
    // This provides interaction with the database. It does so in several ways:
    //  "query: This is one of several overload methods that executes a SQL
    //query and maps each row to a result object via RowMapper, in this case
    //our UserRowMapper."
    //  "queryForObject: This is one of several overload methods that query
    // a given SQL statement to create a prepared statement from a list of
    // arguments to bind to the query, and it maps a single result row to a
    // result object via a RowMapper."
    //  "update: This is one of several overload methods that issues a single
    // SQL update operation (you can use INSERT, UPDATE, or DELETE
    // statements) via a prepared statement, binding the given arguments"
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Object[] params = new Object[] {id};
        User user = jdbcTemplate.queryForObject(sql, params, new int[] { Types.INTEGER }, new UserRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM users";
        return this.jdbcTemplate.query(sql, new UserRowMapper());
    }

    // Getting the Id from the keyHolder, which will be populated with different keys, and
    // it will return an autoincrement value.
    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (name, email, password, gravatar_url, user_role, active) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String[] array = user.userRoles()
                    .stream()
                    .map(Enum::name)
                    .toArray(String[]::new);
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.name());
            ps.setString(2, user.email());
            ps.setString(3, user.password());
            ps.setString(4, user.gravatarUrl());
            ps.setArray(5,connection.createArrayOf("varchar", array));
            ps.setBoolean(6, user.active());
            return ps;
        }, keyHolder);
        return user.withId((Integer)keyHolder.getKeys().get("id"));
        // Sonar said again that it's useless to attribute the statement to a variable just to return it so
        // it's better to return the statement. Sorry book writer sir, sonar drives me mad as well.
    }

    @Override
    // Book says for this method the id param must be Long, but it declares that it implements
    // SimpleRepository<User, Integer> so for this method to be overridden properly, the id param should be Integer.
    public void deleteById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

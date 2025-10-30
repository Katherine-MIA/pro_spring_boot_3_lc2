package com.apress.users;

import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// usage of java record will make objects immutable and generates setters, getters, toString(),
// equals() and hashCode() methods.
@Builder
public record User(Integer id,
                   String email,
                   String name,
                   String password,
                   boolean active,
                   String gravatarUrl,
                   @Singular("role")List<UserRole> userRoles){  //record declaration: like parameters of functions
    // (type, name and possible annotations)
    // Record constructors: canonical, compact or custom. In this case, it's employed, the use of
    // compact constructor -> omits all arguments and applies the logic written inside.
    public User {
        Objects.requireNonNull(email);
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);
        //?= ->  zero-width positive lookahead assertion, what follows it (in the parenthesis) must match
        // what is captured by ?
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one " +
                    "number, one uppercase, one lowercase and one special character");
        }
        pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new IllegalArgumentException("Email must be a valid email address");

        if(null == gravatarUrl) {
            gravatarUrl = UserGravatar.getGravatarUrlFromEmail(email);
        }

        if(null == userRoles){
            userRoles = new ArrayList<>(){{add(UserRole.INFO);}};
        }
    }

    // Record type can have written methods that return a copy of the object.
    // Here the copy is created based on its integer id.
    public User withId(Integer id){
        return new User(id,
                this.email(),
                this.name(),
                this.password(),
                this.active(),
                this.gravatarUrl(),
                this.userRoles());
    }
}
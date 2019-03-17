package com.vladmirk.transkontservice;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private enum DummyUser{
        user, admin, den, ruslan, vk;
    }

    private DummyUser findUser(String username) {
        try {
        return DummyUser.valueOf(username.toLowerCase());}
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw  new UsernameNotFoundException("Specify a user");
        DummyUser user = findUser(username);
        if (user == null)
            throw new UsernameNotFoundException(username + "doesn't exist");
        return new org.springframework.security.core.userdetails.User(
                user.name(), "{noop}" + user.name(),
                Stream.of("user", "admin")
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}

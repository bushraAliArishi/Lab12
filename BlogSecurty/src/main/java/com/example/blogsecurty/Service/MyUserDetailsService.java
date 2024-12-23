package com.example.blogsecurty.Service;

import com.example.blogsecurty.Model.MyUser;
import com.example.blogsecurty.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findMyUserByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("Wrong username or password");
        }
        return myUser;
    }
}

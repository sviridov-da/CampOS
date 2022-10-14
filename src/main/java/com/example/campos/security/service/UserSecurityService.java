package com.example.campos.security.service;

import com.example.campos.model.User;
import com.example.campos.repository.interfaces.UserRepository;
import com.example.campos.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${spring.security.init.admin.username}")
    private String ADMIN_USERNAME;
    @Value("${spring.security.init.admin.password}")
    private String ADMIN_PASS;



    public UserSecurityService(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void initAdmin(){
        if(userRepository.findUserByUsername(ADMIN_USERNAME) == null){
            userRepository.save(new User(ADMIN_USERNAME, encoder.encode(ADMIN_PASS), Role.ADMIN));
        }
        if(userRepository.findUserByUsername("test") == null){
            userRepository.save(new User("test", encoder.encode("test"), Role.EMPLOYER));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }
}

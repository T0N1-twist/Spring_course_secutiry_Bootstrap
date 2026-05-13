package com.kata.springsecurity.spring_course_springsecurity.service;

import com.kata.springsecurity.spring_course_springsecurity.repository.UserRepository;
import com.kata.springsecurity.spring_course_springsecurity.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;



    public  UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,RoleService roleService ) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
       this.roleService = roleService;
    }

  @Override
  public List<User> getAllUsers() {
     return userRepository.findAll();
  }

  @Override
    public User getUserById(Long id) {
     return userRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("User not found"));
  }

    @Override
    @Transactional
    public void saveUser(String username, String lastName, Integer age,
                         String email, String password, List<Integer> roleIds) {
        User user = new User();
        user.setUsername(username);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        if (roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>(roleService.getRolesByIds(roleIds)));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, String username, String lastName, Integer age,
                           String email, String password, List<Integer> roleIds) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setUsername(username);
        existing.setLastName(lastName);
        existing.setAge(age);
        existing.setEmail(email);

        if (password != null && !password.isBlank()) {
            existing.setPassword(passwordEncoder.encode(password));
        }
        if (roleIds != null) {
            existing.setRoles(new HashSet<>(roleService.getRolesByIds(roleIds)));
        }
        userRepository.save(existing);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by email: " + email));
    }


    @Override
    public boolean isExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findWithRolesByUsername(String username) {
        return userRepository.findWithRolesByUsername(username)
                .orElseThrow(() -> new RuntimeException("User by this username is not found"));
    }



}

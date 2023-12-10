package com.myserv.api.rh.services;

import com.myserv.api.rh.model.RoleType;
import com.myserv.api.rh.model.Roles;
import com.myserv.api.rh.model.User;
import com.myserv.api.rh.model.UserDTO;
import com.myserv.api.rh.repository.RoleRepository;
import com.myserv.api.rh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private  UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAllUsersExceptAdmin("admin@admin.com");
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(convertToUserDTO(user));
        }

        return userDTOs;
    }

    public Optional<UserDTO> getById(String idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = convertToUserDTO(user);
            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
    }


    public void deleteById(String id){
          userRepository.deleteById(id);
    }
    public User updateUser(String id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            // Update the fields you want to change from updatedUser into existingUser
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            String newPassword = updatedUser.getPassword();
            if (newPassword != null && !newPassword.isEmpty()) {
                // Hash the new password before saving it
                existingUser.setPassword(passwordEncoder.encode(newPassword));
            }

        }
        return userRepository.save(existingUser);

    }
    public void createadmine() {

        User userAdmin = new User();
        User savedUser = null;

        if (!roleRepository.existsByName(RoleType.ROLE_ADMIN)) {
            Roles roleAdmin = new Roles();
            roleAdmin.setName(RoleType.ROLE_ADMIN);
            Roles dpt = roleRepository.save(roleAdmin);


        }
        if (!roleRepository.existsByName(RoleType.ROLE_USER)) {
            Roles roleAdmin = new Roles();
            roleAdmin.setName(RoleType.ROLE_USER);
            Roles dpt = roleRepository.save(roleAdmin);


        }

        if (!repository.existsByEmail("admin@admin.com")) {
            userAdmin.setEmail("admin@admin.com");
            userAdmin.setFullName("admin");
            userAdmin.setPassword(new BCryptPasswordEncoder().encode("admin.123"));
            Set<Roles> roles = new HashSet<>();
            Roles userRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            userAdmin.setRoles(roles);

            savedUser=repository.save(userAdmin);
        }

    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());

        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

}

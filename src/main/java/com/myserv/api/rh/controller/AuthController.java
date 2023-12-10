package com.myserv.api.rh.controller;

import com.myserv.api.rh.auth.*;
import com.myserv.api.rh.config.JwtUtils;
import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.RoleRepository;
import com.myserv.api.rh.repository.UserRepository;
import com.myserv.api.rh.services.EmailService;
import com.myserv.api.rh.services.PasswordResetTokenService;
import com.myserv.api.rh.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private  HttpServletRequest servletRequest;




    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginForm) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            System.out.println(userDetails.getFullName());

            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getFullName(),
                    roles));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid email or password."));
        }
    }
    @PostMapping("/signup/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFullName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Roles userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.getRoles().add(userRole);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFullName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Roles userRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.getRoles().add(userRole);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    /*@PostMapping("/checkemail/{email}")
    public ResponseEntity<?> restPasswordEmail(@PathVariable String email){
        boolean result=userRepository.existsByEmail(email);
        if(result){
            Mail mail=new Mail(email,"444");
            emailService.sendCodeByMail(mail);
            return ResponseEntity.ok(new MessageResponse("check your email"));

        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email not existe!"));
        }

    }*/

    @PostMapping("/auth/password-reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam("email") String email)  {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);

            try {
                String passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
                return ResponseEntity.ok("Demande de réinitialisation du mot de passe envoyée par e-mail");
            } catch (UnsupportedEncodingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating password reset URL");
            }
        } else {
            return ResponseEntity.badRequest().body("Utilisateur introuvable pour l'e-mail fourni");
        }
    }
    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws  UnsupportedEncodingException {
        String code = "click : http://localhost:4200/restpassword/"+ passwordToken;
        String subject = "Password Reset Request Verification";
        String body = "<p> Hi, "+ user.getFullName()+", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+"" +
                "Please, follow the link below to complete the action.</p>"+
                "<p> "+ code + "</p>"+
                "<p> Users Registration Portal Service";
        emailService.sendMail(user.getEmail(), subject, body);


        return code;
    }
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }

    @PostMapping("/auth/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil
    ){
        System.out.println(passwordRequestUtil);
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(passwordRequestUtil.getCodeemail());
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> theUser = Optional.ofNullable(passwordResetTokenService.findUserByPasswordToken(passwordRequestUtil.getCodeemail()).get());
        if (theUser.isPresent()) {
            passwordResetTokenService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }



}

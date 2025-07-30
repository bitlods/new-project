package com.microservice.UserService.controller;

import com.microservice.UserService.UserRepository.UserRepository;
import com.microservice.UserService.auth.JWTTokenProvider;
import com.microservice.UserService.auth.UserDetailsServiceImpl;
import com.microservice.UserService.dto.AuthenticationDto;
import com.microservice.UserService.dto.RegisterDto;
import com.microservice.UserService.entity.User;
import com.microservice.UserService.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@RestController
//@CrossOrigin("http://localhost:5173/")
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;
    @PostMapping("/register")
    public AuthenticationDto registerUser(@ModelAttribute RegisterDto registerDto,
                                          @RequestParam("profile") MultipartFile profile) throws IOException {
        User dbUser = userRepository.findByEmail(registerDto.getEmail());
        if (dbUser != null) {
            return new AuthenticationDto(null, "User is already Exist with this email " + registerDto.getEmail() + " Please try to login");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmail(registerDto.getEmail());
        user.setGender(registerDto.getGender());
        user.setProfile(profile.getBytes());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User savedUser = userRepository.save(user);
        //we need to give the sample of user details to create token
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
        String token = JWTTokenProvider.generateToken(authentication);
        return new AuthenticationDto(null, "Register Success");
    }
    @PostMapping("/login")
    public AuthenticationDto loginUser(@RequestParam("email")String email,@RequestParam("password") String password){
        Authentication authentication=authentication(email,password);
        String token=JWTTokenProvider.generateToken(authentication);
        AuthenticationDto authenticationDto= new AuthenticationDto(token,"Login Success");
        return authenticationDto;
    }
    private Authentication authentication(String email, String password) {
        UserDetails userDetails=userDetailsServiceimpl.loadUserByUsername(email);
        if(userDetails==null){
            throw new CustomException("User Not Found this email "+email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new CustomException("Password Mismatch");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
    }
}

package com.springsecurity.example.Controller;

import com.springsecurity.example.dto.APIResponse;
import com.springsecurity.example.dto.LoginDto;
import com.springsecurity.example.dto.UserDto;
import com.springsecurity.example.service.AuthService;
import com.springsecurity.example.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    // http://localhost:8080/api/v1/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto dto){
        APIResponse<String> response=authService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    // http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> verifyLogin(@RequestBody LoginDto loginDto){

        APIResponse<String> response=new APIResponse<>();
        UsernamePasswordAuthenticationToken token=
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());


        try{
            Authentication authenticate = authManager.authenticate(token);
            if(authenticate.isAuthenticated()){
                String jwtToken=jwtService.generateToken(loginDto.getUsername(), authenticate.getAuthorities().iterator().next().getAuthority());
                response.setMessage("Login Successful");
                response.setStatus(200);
                response.setData(jwtToken);
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    // http://localhost:8080/api/v1/auth/profile
    @GetMapping("/profile")
    public ResponseEntity<String> profile(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(userDetails.getUsername(), HttpStatus.OK);
    }
}
package com.group.Gestion.src.security;


import com.group.Gestion.src.DTOs.LoginResponseDto;
import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.service.AuthenticationService;
import com.group.Gestion.src.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@CrossOrigin("*")
public class SecurityController {
    private AuthenticationService authenticationService;
    private JwtEncoder jwtEncoder;

    public static boolean isUserAuthenticated(HttpSession http){
        if(http.getAttribute("loggedEmployee")!=null){
            return true;
        }
        return false;
    }

    @PostMapping("/register")
    public String registerEmployee(@RequestParam String  email,@RequestParam String  password,@RequestParam String  firstName,@RequestParam String  lastName){
        authenticationService.registerUser(email,password,firstName,lastName);
        return "redirect:/auth/login";
    }
    @Autowired
    public SecurityController(JwtEncoder jwtEncoder,AuthenticationService authenticationService) {
        this.jwtEncoder=jwtEncoder;
        this.authenticationService=authenticationService;
    }



    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String username, @RequestParam("password") String password,HttpSession http){
        if(SecurityController.isUserAuthenticated(http)){
            return "redirect:/employee/welcome";
        }else{
            if(authenticationService.loginEmployee(username,password).getJwt().isEmpty()){
                System.out.println("Sorry we can't log you In with the email & password you entered  [EMAIL]="+username);
                return "redirect:/auth/login";
            }else{
                Employee loggedEmployee = authenticationService.loginEmployee(username,password).getEmployee();
                String jwtToken = authenticationService.loginEmployee(username,password).getJwt();
                http.setAttribute("loggedEmployee",loggedEmployee);
                System.out.println("YOU ARE LOGGED IN AS: "+ loggedEmployee.getEmail() + " | And you jwt token is: "+jwtToken);
                return "redirect:/employee/welcome";
            }
        }

    }
    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession http){
        http.invalidate();
        return "redirect:/auth/login";
    }
    @GetMapping("/register")
    public String register(){
        return "redirect:/auth/login";
    }
}

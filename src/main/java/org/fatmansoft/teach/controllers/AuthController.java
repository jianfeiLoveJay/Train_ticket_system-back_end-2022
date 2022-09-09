package org.fatmansoft.teach.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.fatmansoft.teach.models.EUserType;
import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.models.UserType;
import org.fatmansoft.teach.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.fatmansoft.teach.payload.request.LoginRequest;
import org.fatmansoft.teach.payload.request.SignupRequest;
import org.fatmansoft.teach.payload.response.JwtResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.security.jwt.JwtUtils;
import org.fatmansoft.teach.security.services.UserDetailsImpl;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        int role=0;
        if(roles.get(0).equals("ROLE_ADMIN"))
            role=1;
        if(roles.get(0).equals("ROLE_USER"))
            role=2;
        if(roles.get(0).equals("ROLE_FORBIDDEN"))
            role=3;

        List userlist;
        userlist = userRepository.findUserById(userDetails.getId());
        User user;
        user=(User) userlist.get(0);
        String usernum=user.getUsernum();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                usernum,
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.ok(new MessageResponse("用户名已存在!"));
        }

        if (userRepository.existsByUsernum(signUpRequest.getUsernum())) {
            return ResponseEntity.ok(new MessageResponse("每人只能注册一个账号!"));
        }

        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getUsernum());

        int strRoles = signUpRequest.getRole();
                switch (strRoles)
                {
                    case 1:
                        UserType adminRole = userTypeRepository.findByName(EUserType.ROLE_ADMIN);
                        user.setUserType(adminRole);
                        break;
                    case 2:
                        UserType userRole = userTypeRepository.findByName(EUserType.ROLE_USER);
                        user.setUserType(userRole);
                        break;
                    default:
                        UserType forbiddenRole = userTypeRepository.findByName(EUserType.ROLE_FORBIDDEN);
                        user.setUserType(forbiddenRole);
                }
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("用户注册成功!"));
        //        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: 用户名已存在!"));
//        }
        // Create new user's account
    }

}

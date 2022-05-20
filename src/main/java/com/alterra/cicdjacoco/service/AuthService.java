package com.alterra.cicdjacoco.service;

import com.alterra.cicdjacoco.config.JwtTokenProvider;
import com.alterra.cicdjacoco.constantapp.ResponseMassage;
import com.alterra.cicdjacoco.domain.dao.UserDao;
import com.alterra.cicdjacoco.domain.dto.TokenResponse;
import com.alterra.cicdjacoco.domain.dto.UserDto;
import com.alterra.cicdjacoco.repository.UserRepository;
import com.alterra.cicdjacoco.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> register(UserDto req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            log.error("User already exist");
            return ResponseUtil.build(ResponseMassage.USER_EXISTS, null, HttpStatus.BAD_REQUEST);
        }

        if (req.getAuthor() == null || !req.getAuthor().equals("ADMIN")) {
            log.info(" User : {}", req.getAuthor());
            UserDao userDao = UserDao.builder()
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .nameUser(req.getNameUser())
                    .telephoneNumber(req.getTelephoneNumber())
                    .author("USER")
                    .alamat(req.getAlamat())
                    .build();
            userRepository.save(userDao);
            log.info("User is Saved");
            System.out.println("ADMIN");
            System.out.println(req.getAuthor());
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, null, HttpStatus.OK);
        }

        if (req.getAuthor().equals("ADMIN") ) {
            log.info(" admin , {}", req.getAuthor());
            UserDao userDao = UserDao.builder()
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .author("ADMIN")
                    .nameUser(req.getNameUser())
                    .telephoneNumber(req.getTelephoneNumber())
                    .alamat(req.getAlamat())
                    .build();
            userRepository.save(userDao);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, null, HttpStatus.OK);

        }
        return null;
    }


    public ResponseEntity<?> authenticateAndGenerateToken(UserDto req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generationToken(authentication);
            return ResponseUtil.build(ResponseMassage.KEY_FOUND, TokenResponse.builder().token(jwt).build(),
                    HttpStatus.OK);
        } catch (BadCredentialsException e){
            log.error("Bad Credential", e.getMessage());
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

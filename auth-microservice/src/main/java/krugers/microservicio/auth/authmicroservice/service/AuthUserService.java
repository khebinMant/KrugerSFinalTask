package krugers.microservicio.auth.authmicroservice.service;

import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import krugers.microservicio.auth.authmicroservice.dto.AuthUserDto;
import krugers.microservicio.auth.authmicroservice.entity.AuthUser;
import krugers.microservicio.auth.authmicroservice.entity.TokenDto;
import krugers.microservicio.auth.authmicroservice.repository.AuthUserRepository;
import krugers.microservicio.auth.authmicroservice.security.JwtProvider;

@Service
public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public  AuthUser save(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getUserName());
        AuthUser authUser = AuthUser.builder()
            .userName(dto.getEmail())
            .password(password)
            .build();

        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user =  authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(dto.getUserName(), user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        
        return null;
    }

    public TokenDto validate(String token){
        if(!jwtProvider.validate(token)){
            return null;
        }
        String username = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByUserName(username).isPresent())
            return null;
        return new TokenDto(token);
    }
}

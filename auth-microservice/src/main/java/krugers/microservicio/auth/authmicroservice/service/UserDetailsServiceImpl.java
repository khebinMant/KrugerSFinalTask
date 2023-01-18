package krugers.microservicio.auth.authmicroservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import krugers.microservicio.auth.authmicroservice.entity.AuthUser;
import krugers.microservicio.auth.authmicroservice.repository.AuthUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthUser> userEntity  = authUserRepository.findByUserName(username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));

        UserDetails userDet = new User(userEntity.get().getUserName(), userEntity.get().getPassword(), roles);
        return userDet;

    }
    
}

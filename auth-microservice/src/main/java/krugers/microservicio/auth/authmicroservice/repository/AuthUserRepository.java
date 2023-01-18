package krugers.microservicio.auth.authmicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import krugers.microservicio.auth.authmicroservice.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>{
    Optional<AuthUser> findByUserName(String userName);
}

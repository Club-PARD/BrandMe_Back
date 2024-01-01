package com.soim.brandme.user.repo;

import com.soim.brandme.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}

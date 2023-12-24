package com.soim.brandme.user.domain.repo;

import com.soim.brandme.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}

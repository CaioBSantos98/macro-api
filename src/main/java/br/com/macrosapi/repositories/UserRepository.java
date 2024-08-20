package br.com.macrosapi.repositories;

import br.com.macrosapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmailAndActiveTrue(String email);
}

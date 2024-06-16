package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
        boolean existsByEmail(String email);


        Optional<User> findByEmail(String email);
}

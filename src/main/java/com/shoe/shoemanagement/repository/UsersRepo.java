package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.SystemUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<SystemUsers, Integer> {
    Optional<SystemUsers> findByEmail(String email);
}

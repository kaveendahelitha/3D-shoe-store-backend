package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
        boolean existsByEmail(String email);


        Optional<User> findByEmail(String email);
        @Transactional
        @Modifying
        //using talias
        @Query("update User u set u.password = ?2 where u.email = ?1")
        void updatePassword(String email,String password);
}

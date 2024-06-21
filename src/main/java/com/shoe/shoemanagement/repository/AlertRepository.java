package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
}


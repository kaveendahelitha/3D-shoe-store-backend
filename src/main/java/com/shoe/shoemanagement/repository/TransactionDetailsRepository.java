

package com.shoe.shoemanagement.repository;

import com.shoe.shoemanagement.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, String> {
    // Additional query methods can be defined here if needed
}

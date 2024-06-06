package com.lamanini.La.manini.repositories;

import com.lamanini.La.manini.models.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase,Long> {
    @Query("SELECT p FROM Purchase p ORDER BY p.id DESC LIMIT 1")
    Purchase findLatestPurchase();


}

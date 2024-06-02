package com.lamanini.La.manini.reposetories;

import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase,Long> {
    @Query("SELECT p FROM Purchase p ORDER BY p.id DESC LIMIT 1")
    Purchase findLatestPurchase();


}

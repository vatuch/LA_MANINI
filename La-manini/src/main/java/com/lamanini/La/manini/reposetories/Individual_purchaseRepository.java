package com.lamanini.La.manini.reposetories;
import com.lamanini.La.manini.models.Individual_purchase;
import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Individual_purchaseRepository extends CrudRepository<Individual_purchase,Long> {
    @Query("SELECT p FROM Individual_purchase p ORDER BY p.id DESC LIMIT 1")
    Individual_purchase findLatestIndividualPurchase();

}

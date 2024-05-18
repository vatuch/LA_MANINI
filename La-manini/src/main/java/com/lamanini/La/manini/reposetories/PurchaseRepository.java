package com.lamanini.La.manini.reposetories;

import com.lamanini.La.manini.models.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase,Long> {
}

package com.lamanini.La.manini.reposetories;
import com.lamanini.La.manini.models.Individual_purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Individual_purchaseRepository extends CrudRepository<Individual_purchase,Long> {
}

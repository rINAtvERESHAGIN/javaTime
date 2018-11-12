package com.javaPeople.repository;

import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {
    List<Contribution> findByName(String name);// 1

    @Query(value = "SELECT u FROM Contribution u WHERE u.resource = :resource")
    List<Contribution> findByResource(@Param("resource") CircleResource circleResource);

}

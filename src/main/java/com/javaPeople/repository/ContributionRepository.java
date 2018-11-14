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

// @Query(value = "SELECT * c FROM Contribution ")
//    List<Contribution> findAll;

//    @Query(value = "SELECT * FROM resource_circle.contribution e ", nativeQuery = true)
//    List<Contribution> findAll(Long contribution);

    @Query(value = "SELECT * " +
            "FROM resource_circle.contribution " +
            "WHERE resource_circle.contribution.factor = (SELECT MAX(factor) FROM resource_circle.contribution)",
            nativeQuery = true)
    Contribution findResourceByMaximumFactorFromComtribution();
    // для функции поиска ресурса с наибольшим колличеством событий
    @Query(value = "SELECT *" +
            "FROM resource_circle.contribution re " +
            "WHERE re.resource_id = ?  ",
            nativeQuery = true)
    List<Contribution> findByResourceId(Long resource_id);

}

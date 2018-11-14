package com.javaPeople.repository;

import com.javaPeople.domain.CircleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircleResourceRepository extends JpaRepository<CircleResource, Long> {
    List<CircleResource> findByName (String name);

//    @Query( value = "SELECT * FROM resource_circle.CircleResource u WHERE u.status = ?",nativeQuery = true)
//    List<CircleResource> findBySomething(Long Id);

//    @Query("SELECT u FROM CircleResource u WHERE u.status = :status and u.name = :name") // предпочтительней
//    Name findUserByStatusAndNameNamedParams(
//            @Param("status") Integer status,
//            @Param("name") String name);
    @Query(value = "SELECT resource_circle.resource.name r FROM resource_circle.resource r WHERE r.id=resource_circle.contribution.resource_id",nativeQuery = true)
    List<CircleResource> findCircleResourceByContributionId(Long id);
}

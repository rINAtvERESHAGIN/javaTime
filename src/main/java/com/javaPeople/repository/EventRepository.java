package com.javaPeople.repository;

import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Transactional
    @Query(value = "SELECT * FROM resource_circle.Event e WHERE e.contribution_id = ?", nativeQuery = true)
    List<Event> findByContributionId(Long contribution);

//    List<Event> findByContribution(Contribution contribution);

    @Query(value = "SELECT u FROM Event u WHERE u.contribution = :contribution")
    List<Event> findByContribution(@Param("contribution") Contribution contribution);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM Event e WHERE e.id in :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT COUNT(*) " +
            "FROM resource_circle.event " +
            "WHERE resource_circle.event.contribution_id = ?",nativeQuery = true)
    int findEventCountByContributionId(Long id);
}

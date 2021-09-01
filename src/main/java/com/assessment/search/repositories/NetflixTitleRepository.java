package com.assessment.search.repositories;

import com.assessment.search.entities.NetflixTitlesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NetflixTitleRepository extends JpaRepository<NetflixTitlesEntity, String>,
        JpaSpecificationExecutor<NetflixTitlesEntity> {
}

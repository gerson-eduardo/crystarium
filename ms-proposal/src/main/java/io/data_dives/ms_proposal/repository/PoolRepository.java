package io.data_dives.ms_proposal.repository;

import io.data_dives.ms_proposal.model.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoolRepository extends JpaRepository<Pool, Long> {

    Optional<Pool> findByProposal_Id(Long id);
}

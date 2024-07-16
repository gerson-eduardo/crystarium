package io.data_dives.ms_proposal.repository;

import io.data_dives.ms_proposal.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByCpfAndProposal_Id(String cpf, Long id);
}

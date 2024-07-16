package io.data_dives.ms_proposal.service;

import io.data_dives.ms_proposal.dto.CreateVoteDto;

public interface IVoteService {
    void createVote(CreateVoteDto dto);
}

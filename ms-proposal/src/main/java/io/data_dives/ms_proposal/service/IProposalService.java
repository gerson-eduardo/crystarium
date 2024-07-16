package io.data_dives.ms_proposal.service;

import io.data_dives.ms_proposal.dto.CreateProposalDto;

public interface IProposalService {
    void createProposal(CreateProposalDto dto);
}

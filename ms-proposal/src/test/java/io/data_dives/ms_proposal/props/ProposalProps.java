package io.data_dives.ms_proposal.props;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.model.Proposal;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProposalProps {
    public static List<Proposal> PROPOSALS = new ArrayList<>(Arrays.asList(
            new Proposal(1L, "Proposal 1 title", "This is a description of proposal 1", false, "11144477735", ZonedDateTime.now(), ZonedDateTime.now()),
            new Proposal(1L, "Proposal 2 title", "This is a description of proposal 2", true, "11144477735", ZonedDateTime.now(), ZonedDateTime.now()),
            new Proposal(1L, "Proposal 3 title", "This is a description of proposal 3", false, "11144477735", ZonedDateTime.now(), ZonedDateTime.now())
    ));
    public static Proposal PROPOSAL1 = PROPOSALS.getFirst();
    public static Proposal PROPOSAL2 = PROPOSALS.get(1);
    public static Proposal PROPOSAL3 = PROPOSALS.get(2);

    public static CreateProposalDto CREATE_PROPOSAL_DTO1 = new CreateProposalDto(PROPOSAL1);
    public static CreateProposalDto CREATE_PROPOSAL_DTO2 = new CreateProposalDto(PROPOSAL2);
    public static CreateProposalDto CREATE_PROPOSAL_DTO3 = new CreateProposalDto(PROPOSAL3);
}

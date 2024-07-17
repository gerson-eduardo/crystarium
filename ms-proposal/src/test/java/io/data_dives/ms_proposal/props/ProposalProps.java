package io.data_dives.ms_proposal.props;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import io.data_dives.ms_proposal.model.Proposal;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProposalProps {

    public static ZonedDateTime NOW = ZonedDateTime.of(2024, 7, 7, 17, 32, 46, 0, Clock.systemDefaultZone().getZone());
    public static List<Proposal> PROPOSALS = new ArrayList<>(Arrays.asList(
            new Proposal(1L, "Proposal 1 title", "This is a description of proposal 1", false, "11144477735", NOW, NOW),
            new Proposal(2L, "Proposal 2 title", "This is a description of proposal 2", true, "11144477735", NOW.plusDays(1), NOW.plusDays(1)),
            new Proposal(3L, "Proposal 3 title", "This is a description of proposal 3", false, "11144477735", NOW, NOW.plusMinutes(5))
    ));
    public static Proposal PROPOSAL1 = PROPOSALS.getFirst();
    public static Proposal PROPOSAL2 = PROPOSALS.get(1);
    public static Proposal PROPOSAL3 = PROPOSALS.get(2);

    public static Proposal PROPOSAL1_NO_ID = PROPOSAL1.removeId();
    public static Proposal PROPOSAL2_NO_ID = PROPOSAL2.removeId();
    public static Proposal PROPOSAL3_NO_ID = PROPOSAL3.removeId();

    public static CreateProposalDto CREATE_PROPOSAL_DTO1 = new CreateProposalDto(PROPOSAL1);
    public static CreateProposalDto CREATE_PROPOSAL_DTO2 = new CreateProposalDto(PROPOSAL2);
    public static CreateProposalDto CREATE_PROPOSAL_DTO3 = new CreateProposalDto(PROPOSAL3);
}

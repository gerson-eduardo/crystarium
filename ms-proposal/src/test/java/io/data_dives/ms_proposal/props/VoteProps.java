package io.data_dives.ms_proposal.props;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import io.data_dives.ms_proposal.model.Vote;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL1;
import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL2;
import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL3;

public class VoteProps {

    public static ZonedDateTime NOW = ZonedDateTime.of(2024, 7, 7, 17, 32, 46, 0, Clock.systemDefaultZone().getZone());
    public static List<Vote> VOTES = new ArrayList<>(Arrays.asList(
       new Vote(1L, "11144477735", PROPOSAL1, true, NOW, NOW),
       new Vote(2L, "11144477735", PROPOSAL2, false, NOW.plusDays(1), NOW.plusDays(1)),
       new Vote(3L, "11144477735", PROPOSAL3, true, NOW.plusMinutes(5), NOW.plusMinutes(10))
    ));

    public static Vote VOTE1 = VOTES.getFirst();
    public static Vote VOTE2 = VOTES.get(1);
    public static Vote VOTE3 = VOTES.get(2);

    public static Vote VOTE1_NO_ID = VOTE1.removeId();
    public static Vote VOTE2_NO_ID = VOTE2.removeId();
    public static Vote VOTE3_NO_ID = VOTE3.removeId();

    public static CreateVoteDto CREATE_VOTE_DTO1 = new CreateVoteDto(VOTE1);
    public static CreateVoteDto CREATE_VOTE_DTO2 = new CreateVoteDto(VOTE2);
    public static CreateVoteDto CREATE_VOTE_DTO3 = new CreateVoteDto(VOTE3);
}

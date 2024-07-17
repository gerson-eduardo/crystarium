package io.data_dives.ms_proposal.props;

import io.data_dives.ms_proposal.model.Pool;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL1;
import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL2;
import static io.data_dives.ms_proposal.props.ProposalProps.PROPOSAL3;

public class PoolProps {

    public static ZonedDateTime NOW = ZonedDateTime.of(2024, 7, 7, 17, 32, 46, 0, Clock.systemDefaultZone().getZone());
    public static List<Pool> POOLS = new ArrayList<>(Arrays.asList(
            new Pool(1L, PROPOSAL1, NOW, NOW.plusMinutes(1), NOW, NOW, true),
            new Pool(2L, PROPOSAL2, NOW.plusDays(1), NOW.plusDays(1).plusMinutes(1), NOW.plusDays(1), NOW.plusDays(1).plusMinutes(2), false),
            new Pool(3L, PROPOSAL3, NOW, NOW.plusMinutes(1), NOW, NOW, true)
    ));

    public static Pool POOL1 = POOLS.getFirst();
    public static Pool POOL2 = POOLS.get(1);
    public static Pool POOL3 = POOLS.get(2);

    public static Pool POOL1_NO_ID = POOL1.removeId();
    public static Pool POOL2_NO_ID = POOL2.removeId();
    public static Pool POOL3_NO_ID = POOL3.removeId();
}

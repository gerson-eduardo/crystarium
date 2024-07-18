package io.data_dives.ms_proposal.props;

import io.data_dives.ms_proposal.dto.BooleanMessageResponse;

public class MessageProps {
    public static BooleanMessageResponse BOOLEAN_MSG_RESP1 = new BooleanMessageResponse("Request is valid", true);
    public static BooleanMessageResponse BOOLEAN_MSG_RESP2 = new BooleanMessageResponse("Request is invalid", false);
}

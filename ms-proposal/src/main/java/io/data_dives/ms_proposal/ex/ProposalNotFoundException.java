package io.data_dives.ms_proposal.ex;

public class ProposalNotFoundException extends RuntimeException{
    public ProposalNotFoundException(String msg){
        super(msg);
    }
}

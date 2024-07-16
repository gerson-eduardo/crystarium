package io.data_dives.ms_proposal.ex;

public class VoteConflictException extends RuntimeException{
    public VoteConflictException(String msg){
        super(msg);
    }
}

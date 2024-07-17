package io.data_dives.ms_proposal.ex;

public class PoolAlreadyStartedException extends RuntimeException{
    public PoolAlreadyStartedException(String msg){
        super(msg);
    }
}

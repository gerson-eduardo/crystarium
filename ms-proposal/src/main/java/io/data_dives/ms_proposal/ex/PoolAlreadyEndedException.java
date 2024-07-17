package io.data_dives.ms_proposal.ex;

public class PoolAlreadyEndedException extends RuntimeException{
    public PoolAlreadyEndedException(String msg){
        super(msg);
    }
}

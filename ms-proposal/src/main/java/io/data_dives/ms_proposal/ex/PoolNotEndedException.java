package io.data_dives.ms_proposal.ex;

public class PoolNotEndedException extends RuntimeException{
    public PoolNotEndedException(String msg){
        super(msg);
    }
}

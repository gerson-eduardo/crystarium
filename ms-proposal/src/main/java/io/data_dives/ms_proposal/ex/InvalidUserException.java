package io.data_dives.ms_proposal.ex;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String msg){
        super(msg);
    }
}

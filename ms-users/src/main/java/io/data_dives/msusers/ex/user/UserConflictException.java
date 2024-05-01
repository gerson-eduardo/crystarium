package io.data_dives.msusers.ex;

public class UserConflictException extends RuntimeException{
    public UserConflictException(String msg){
        super(msg);
    }
}

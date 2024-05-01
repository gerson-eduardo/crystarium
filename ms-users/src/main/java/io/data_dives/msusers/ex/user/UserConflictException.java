package io.data_dives.msusers.ex.user;

public class UserConflictException extends RuntimeException{
    public UserConflictException(String msg){
        super(msg);
    }
}

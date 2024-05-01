package io.data_dives.msusers.ex.user;

public class EmailConflictException extends RuntimeException{
    public EmailConflictException(String msg){
        super(msg);
    }
}

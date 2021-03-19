package com.adrian.interview.utils.errorHandling;

public class InvalidActionException extends IllegalArgumentException {
    public InvalidActionException(String s) {
        super(s);
    }
}

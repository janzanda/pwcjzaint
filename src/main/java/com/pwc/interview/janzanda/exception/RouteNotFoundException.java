package com.pwc.interview.janzanda.exception;

/**
 * Exception thrown in case of no path found.
 *
 * @author zandajan
 */
public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }
}

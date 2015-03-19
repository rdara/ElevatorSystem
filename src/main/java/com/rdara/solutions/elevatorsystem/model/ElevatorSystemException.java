/**
 * 
 */
package com.rdara.solutions.elevatorsystem.model;

/**
 * @author Ramesh Dara
 */
public class ElevatorSystemException extends RuntimeException {

    ExceptionReason reason;
    String data;
    Throwable causedBy;

    public ElevatorSystemException(ExceptionReason reason, String data, String message, Throwable causedBy) {
        super(message);
        this.reason = reason;
        this.data = data;
        this.causedBy = causedBy;
    }

    public ElevatorSystemException(ExceptionReason reason, String data) {
        this(reason, data, reason.name() + ": " + data, null);
    }
}

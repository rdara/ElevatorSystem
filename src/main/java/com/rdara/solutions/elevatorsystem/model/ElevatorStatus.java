/**
 * 
 */
package com.rdara.solutions.elevatorsystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rdara.solutions.elevatorsystem.utils.Utilities;

/**
 * @author Ramesh Dara
 */
public enum ElevatorStatus {
    MOVING_UP,
    MOVING_DOWN,
    HALTED,
    STOPPED,
    UNDER_MAINTAINANCE;

    @JsonCreator
    public static ElevatorStatus fromValue(String value) {
        return Utilities.getEnumFromString(ElevatorStatus.class, value);
    }
}

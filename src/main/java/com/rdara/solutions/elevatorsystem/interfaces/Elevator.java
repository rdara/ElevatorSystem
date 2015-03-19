/**
 * 
 */
package com.rdara.solutions.elevatorsystem.interfaces;

import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;
import com.rdara.solutions.elevatorsystem.model.ElevatorStatus;

/**
 * @author Ramesh Dara
 */
public interface Elevator extends Runnable {

    void add(int floor);

    void move();

    void halt();

    ElevatorStatus getElevatorStatus();

    void setElevatorStatus(ElevatorStatus status);

    ElevatorDirection getElevatorDirection();

    void setElevatorDirection(ElevatorDirection direction);

    int getCurrentFloor();

    int getId();
}

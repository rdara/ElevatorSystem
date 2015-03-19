/**
 * 
 */
package com.rdara.solutions.elevatorsystem.interfaces;

import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;

/**
 * @author Ramesh Dara
 */
public interface Floor {
    //On a floor, persons can request for going up or going down.

    void addFloorRequest(int floor, ElevatorDirection direction);

    void removeFloorRequest(int floor, ElevatorDirection direction);

    boolean isFloorHasARequest(int floor, ElevatorDirection direction);

    //Gets the floor thats the fartherest in a direction.
    // E.x., if there are UP requests from the floors 12, 34, 67, then 67 is returned.

    int getFartherestFloorRequest(ElevatorDirection direction);

    //From the given floor, in the lift direction, find the nearest floor that has request.
    int getClosestFloorRequest(int floor, ElevatorDirection direction);

}

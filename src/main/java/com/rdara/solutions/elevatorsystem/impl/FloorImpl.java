/**
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import com.rdara.solutions.elevatorsystem.interfaces.Floor;
import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;
import com.rdara.solutions.elevatorsystem.model.ElevatorSystemException;
import com.rdara.solutions.elevatorsystem.model.ExceptionReason;

/**
 * @author Ramesh Dara
 */
public class FloorImpl implements Floor {

    int totalFloors;

    SortedSet<Integer> floorUpRequests;
    SortedSet<Integer> floorDownRequests;

    public FloorImpl(int totalFloors) {
        this.totalFloors = totalFloors;
        floorUpRequests = new ConcurrentSkipListSet<Integer>();
        floorDownRequests = new ConcurrentSkipListSet<Integer>();
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Floor#addRequest(int, com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public void addFloorRequest(int floor, ElevatorDirection direction) {
        if (floor < 0 || floor > totalFloors) {
            throw new ElevatorSystemException(ExceptionReason.FLOOR_DOESNT_EXIST, new Integer(floor).toString());
        }
        SortedSet<Integer> floorsSet = getFloorsSet(direction);
        floorsSet.add(floor);
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Floor#removeRequest(int, com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public void removeFloorRequest(int floor, ElevatorDirection direction) {
        SortedSet<Integer> floorsSet = getFloorsSet(direction);

        floorsSet.remove(floor);
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Floor#getFartherestFloorRequest(com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public int getFartherestFloorRequest(ElevatorDirection direction) {
        SortedSet<Integer> floorsSet = getFloorsSet(direction);
        return floorsSet.last();
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Floor#isFloorHasARequest(int, com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public boolean isFloorHasARequest(int floor, ElevatorDirection direction) {
        SortedSet<Integer> floorsSet = getFloorsSet(direction);
        return floorsSet.contains(floor);
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Floor#getClosestFloorRequest(int, com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public int getClosestFloorRequest(int floor, ElevatorDirection direction) {
        SortedSet<Integer> floorsSet = getFloorsSet(direction);
        SortedSet<Integer> subSet;
        switch (direction) {
        case UP:
            subSet = floorsSet.tailSet(floor + 1);
            return subSet.isEmpty() ? -1 : subSet.first();
        case DOWN:
            subSet = floorsSet.headSet(floor);
            return subSet.isEmpty() ? -1 : subSet.last();
        default:
            throw new ElevatorSystemException(ExceptionReason.UNSUPPORTED_ELEVATOR_DIRECTION, direction.name());
        }
    }

    private SortedSet<Integer> getFloorsSet(ElevatorDirection direction) {
        switch (direction) {
        case UP:
            return floorUpRequests;
        case DOWN:
            return floorDownRequests;
        default:
            throw new ElevatorSystemException(ExceptionReason.UNSUPPORTED_ELEVATOR_DIRECTION, direction.name());
        }
    }

}

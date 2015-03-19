/**
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rdara.solutions.elevatorsystem.interfaces.Elevator;
import com.rdara.solutions.elevatorsystem.interfaces.Floor;
import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;
import com.rdara.solutions.elevatorsystem.model.ElevatorStatus;
import com.rdara.solutions.elevatorsystem.model.ElevatorSystemException;
import com.rdara.solutions.elevatorsystem.model.ExceptionReason;

/**
 * @author Ramesh Dara
 */
public class ElevatorControlSystem {
    static final int NUMBER_OF_FLOORS = 100;
    Floor floors;

    Map<Integer, Elevator> elevatorsMap = new ConcurrentHashMap<Integer, Elevator>();

    //Currently creating a Thread for an Elevator.
    // To scale, we can create a ThreadPool and with ExecutorService we can manage
    //Few threads that works on a Elevator Workers. Reserved for the next iteration
    public ElevatorControlSystem(int noOfFloors, int noOfElevators) {
        if (noOfFloors < 1) {
            throw new ElevatorSystemException(ExceptionReason.UNSUPPORTED_FLOORS, (new Integer(noOfFloors)).toString());
        }
        if (noOfElevators < 1) {
            throw new ElevatorSystemException(ExceptionReason.UNSUPPORTED_ELEVATORS,
                    (new Integer(noOfElevators)).toString());
        }

        floors = new FloorImpl(noOfFloors);
        for (int i = 0; i < noOfElevators; i++) {
            Elevator elevator = new ElevatorImpl(i);
            new Thread(elevator).start();
            elevatorsMap.put(i, elevator);
        }
    }

    public void addFloorRequest(int floor, ElevatorDirection direction) {
        floors.addFloorRequest(floor, direction);
        Elevator elevator = pickupElevator(floor, direction);
        elevator.add(floor);

    }

    public void addElevatorRequest(int elevatorId, int floor) {
        Elevator elevator = elevatorsMap.get(elevatorId);
        elevator.add(floor);

    }

    public int getCurrentFloor(int elevatorId) {
        return elevatorsMap.get(elevatorId).getCurrentFloor();
    }

    public Elevator getElevator(int elevatorId) {
        return elevatorsMap.get(elevatorId);
    }

    //Pick up an elevator thats closest "approaching" to the requested floor
    //If none is found, select an "stopped" elevator thats closest to the requested floor
    private Elevator pickupElevator(int floor, ElevatorDirection direction) {
        Elevator selectedElevator = null;
        int noOfFloorsAway = Integer.MAX_VALUE;
        int elevatorFloor = 0;
        for (Elevator elevator : elevatorsMap.values()) {
            switch (direction) {
            case UP:
                elevatorFloor = elevator.getCurrentFloor();
                if (elevator.getElevatorDirection() == direction && elevatorFloor < floor) {
                    if (noOfFloorsAway < (floor - elevatorFloor)) {
                        noOfFloorsAway = floor - elevatorFloor;
                        selectedElevator = elevator;
                    }
                }
                break;
            case DOWN:
                elevatorFloor = elevator.getCurrentFloor();
                if (elevator.getElevatorDirection() == direction && elevatorFloor > floor) {
                    if (noOfFloorsAway < (elevatorFloor - floor)) {
                        noOfFloorsAway = elevatorFloor - floor;
                        selectedElevator = elevator;
                    }
                }
                break;
            default:
                break;
            }
        }
        if (selectedElevator == null) {
            noOfFloorsAway = Integer.MAX_VALUE;
            for (Elevator elevator : elevatorsMap.values()) {
                if (elevator.getElevatorStatus() == ElevatorStatus.STOPPED) {
                    if (noOfFloorsAway > Math.abs(elevator.getCurrentFloor() - floor)) {
                        noOfFloorsAway = Math.abs(elevator.getCurrentFloor() - floor);
                        selectedElevator = elevator;
                    }
                }
            }
        }
        //Fall back...here chosing the first elevator always. We can rotate which elevator requires to be chosen as default....
        if (selectedElevator == null) {
            selectedElevator = elevatorsMap.values().iterator().next();
        }
        return selectedElevator;
    }

}

/**
 * 
 */
package com.rdara.solutions.elevatorsystem;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.rdara.solutions.elevatorsystem.impl.ElevatorImpl;
import com.rdara.solutions.elevatorsystem.interfaces.Elevator;
import com.rdara.solutions.elevatorsystem.model.ElevatorStatus;

/**
 * @author Ramesh Dara
 */
public class SimulatorTest {

    final Elevator elevator = new ElevatorImpl(0);

    @Test
    public void testSimulator() {

        System.out.println("Simulation STARTTING.....");
        System.out.println("The elevator progress and floor requests (random times) are shown in output as they occur");

        Thread t = new Thread(elevator);
        t.start();
        System.out.println("Initial requested floor: 5");
        elevator.add(5);

        new Thread() {
            public void run() {
                int[] floors = new int[] { 10, 12, 5, 15, 7 };
                for (int i = 0; i < floors.length; i++) {
                    elevator.add(floors[i]);
                    System.out.println("Adding floor request for : " + floors[i]);
                    waitFor(50);
                }
            }
        }.start();

        int curFloor = elevator.getCurrentFloor();
        ElevatorStatus curStatus = ElevatorStatus.UNDER_MAINTAINANCE;
        int i = 0;
        while (elevator.getElevatorStatus() != ElevatorStatus.STOPPED) {
            if (curFloor != elevator.getCurrentFloor() || curStatus != elevator.getElevatorStatus()) {
                curFloor = elevator.getCurrentFloor();
                curStatus = elevator.getElevatorStatus();
                System.out.println("Elevator at " + curFloor + " floor and is " + curStatus);
            }
        }
    }

    private void waitFor(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
        }

    }

}

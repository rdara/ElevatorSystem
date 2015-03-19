/**
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rdara.solutions.elevatorsystem.interfaces.Elevator;
import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;
import com.rdara.solutions.elevatorsystem.model.ElevatorStatus;

/**
 * @author Ramesh Dara
 */
public class ElevatorImplTest {

    Elevator elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorImpl(0);

    }

    @Test
    public void testMove() {
        elevator.add(15);
        elevator.move();
        Assert.assertEquals(15, elevator.getCurrentFloor());
        Assert.assertEquals(ElevatorDirection.NONE, elevator.getElevatorDirection());
        Assert.assertEquals(ElevatorStatus.STOPPED, elevator.getElevatorStatus());

    }

    @Test
    public void testHalt() {
        elevator.halt();
        Assert.assertEquals(1, elevator.getCurrentFloor());
        Assert.assertEquals(ElevatorStatus.HALTED, elevator.getElevatorStatus());
    }

    @Test
    public void testId() {
        Assert.assertEquals(0, elevator.getId());
    }

    @Test
    public void testIncrementalMoveUp() {
        Thread t = new Thread(elevator);
        t.start();
        Assert.assertEquals(1, elevator.getCurrentFloor());
        Assert.assertEquals(ElevatorDirection.NONE, elevator.getElevatorDirection());
        Assert.assertEquals(ElevatorStatus.STOPPED, elevator.getElevatorStatus());

        int curFloor = elevator.getCurrentFloor();
        elevator.add(10);

        while (curFloor < 10) {
            Assert.assertTrue(curFloor <= elevator.getCurrentFloor());
            curFloor = elevator.getCurrentFloor();
            if (curFloor == 5) {
                Assert.assertEquals(ElevatorDirection.UP, elevator.getElevatorDirection());
                Assert.assertEquals(ElevatorStatus.MOVING_UP, elevator.getElevatorStatus());
            }
        }
        Assert.assertEquals(10, elevator.getCurrentFloor());
        t.stop();
    }

    /*
     * First move the elevator to 10th floor.
     * Then request to the 5th floor. The elevator has to change its direction to come down.
     */
    @Test
    public void testIncrementalMoveDown() {
        Thread t = new Thread(elevator);
        t.start();
        Assert.assertEquals(1, elevator.getCurrentFloor());
        Assert.assertEquals(ElevatorDirection.NONE, elevator.getElevatorDirection());
        Assert.assertEquals(ElevatorStatus.STOPPED, elevator.getElevatorStatus());

        elevator.add(10);
        int curFloor = elevator.getCurrentFloor();

        while (curFloor < 10) {
            curFloor = elevator.getCurrentFloor();
        }
        Assert.assertEquals(10, elevator.getCurrentFloor());

        curFloor = elevator.getCurrentFloor();
        elevator.add(5);
        while (curFloor > 5) {
            Assert.assertTrue(curFloor >= elevator.getCurrentFloor());
            curFloor = elevator.getCurrentFloor();
            if (curFloor == 7) {
                Assert.assertEquals(ElevatorDirection.DOWN, elevator.getElevatorDirection());
                Assert.assertEquals(ElevatorStatus.MOVING_DOWN, elevator.getElevatorStatus());
            }
        }
        Assert.assertEquals(5, elevator.getCurrentFloor());

        t.stop();
    }

    @Test
    public void testHaltInBetween() {
        Thread t = new Thread(elevator);
        Assert.assertEquals(1, elevator.getCurrentFloor());
        Assert.assertEquals(ElevatorDirection.NONE, elevator.getElevatorDirection());
        Assert.assertEquals(ElevatorStatus.STOPPED, elevator.getElevatorStatus());

        int curFloor = elevator.getCurrentFloor();
        elevator.add(10);
        elevator.add(5);
        t.start();
        boolean halted = false;
        while (curFloor < 10) {
            Assert.assertTrue(curFloor <= elevator.getCurrentFloor());
            curFloor = elevator.getCurrentFloor();
            if (curFloor == 5 && elevator.getElevatorStatus() == ElevatorStatus.HALTED) {
                halted = true;
            }
        }
        Assert.assertTrue(halted);
        t.stop();
    }

}

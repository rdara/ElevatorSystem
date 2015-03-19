/**
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;

/**
 * @author Ramesh Dara
 */
public class ElevatorControlSystemTest {

    ElevatorControlSystem ecs;

    @Before
    public void setup() {
        ecs = new ElevatorControlSystem(100, 3);
    }

    @Test
    public void testAllElevatorsCreated() {
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(i, ecs.getElevator(i).getId());
        }
    }

    @Test
    public void testAddElevatorRequest() {
        ecs.addElevatorRequest(1, 3);
        int curFloor = ecs.getCurrentFloor(1);
        while (curFloor < 3) {
            curFloor = ecs.getCurrentFloor(1);
        }
        Assert.assertEquals(3, ecs.getCurrentFloor(1));
    }

    @Test
    public void testAddFloorRequest() {
        ecs.addElevatorRequest(0, 2);
        ecs.addElevatorRequest(1, 5);
        ecs.addElevatorRequest(2, 9);

        int curFloor = ecs.getCurrentFloor(2);
        while (curFloor < 9) {
            curFloor = ecs.getCurrentFloor(2);
        }
        Assert.assertEquals(2, ecs.getCurrentFloor(0));
        Assert.assertEquals(5, ecs.getCurrentFloor(1));
        Assert.assertEquals(9, ecs.getCurrentFloor(2));

        //Because elevator 2 is closest that elevator should serve
        ecs.addFloorRequest(12, ElevatorDirection.UP);
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
        }

        boolean bFound = false;
        for (int i = 0; i < 2; i++) {
            if (ecs.getCurrentFloor(i) == 12) {
                bFound = true;
            }
        }
        Assert.assertTrue(bFound);
        //Assert.assertEquals(12, ecs.getCurrentFloor(2));

    }

}

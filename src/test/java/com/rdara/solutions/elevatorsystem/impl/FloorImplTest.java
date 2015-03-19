/**
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rdara.solutions.elevatorsystem.interfaces.Floor;
import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;

/**
 * @author Ramesh Dara
 */
public class FloorImplTest {

    Floor floor;

    @Before
    public void setUp() {
        floor = new FloorImpl(100);
    }

    @Test
    public void testFarthereseRequestUP() {
        floor.addFloorRequest(5, ElevatorDirection.UP);
        Assert.assertEquals(5, floor.getFartherestFloorRequest(ElevatorDirection.UP));
        floor.addFloorRequest(15, ElevatorDirection.UP);
        Assert.assertEquals(15, floor.getFartherestFloorRequest(ElevatorDirection.UP));
        floor.addFloorRequest(10, ElevatorDirection.UP);
        Assert.assertEquals(15, floor.getFartherestFloorRequest(ElevatorDirection.UP));
        floor.removeFloorRequest(15, ElevatorDirection.UP);
        Assert.assertEquals(10, floor.getFartherestFloorRequest(ElevatorDirection.UP));
    }

    public void testFarthereseRequestDown() {
        floor.addFloorRequest(5, ElevatorDirection.DOWN);
        Assert.assertEquals(5, floor.getFartherestFloorRequest(ElevatorDirection.DOWN));
        floor.addFloorRequest(15, ElevatorDirection.DOWN);
        Assert.assertEquals(15, floor.getFartherestFloorRequest(ElevatorDirection.DOWN));
        floor.addFloorRequest(10, ElevatorDirection.DOWN);
        Assert.assertEquals(15, floor.getFartherestFloorRequest(ElevatorDirection.DOWN));
        floor.removeFloorRequest(15, ElevatorDirection.DOWN);
        Assert.assertEquals(10, floor.getFartherestFloorRequest(ElevatorDirection.DOWN));
    }

    @Test
    public void testClosestFloorRequestUP() {
        floor.addFloorRequest(5, ElevatorDirection.UP);
        floor.addFloorRequest(15, ElevatorDirection.UP);
        floor.addFloorRequest(10, ElevatorDirection.UP);
        floor.addFloorRequest(18, ElevatorDirection.UP);

        Assert.assertEquals(5, floor.getClosestFloorRequest(3, ElevatorDirection.UP));
        Assert.assertEquals(10, floor.getClosestFloorRequest(5, ElevatorDirection.UP));
        Assert.assertEquals(10, floor.getClosestFloorRequest(6, ElevatorDirection.UP));
        Assert.assertEquals(10, floor.getClosestFloorRequest(7, ElevatorDirection.UP));
        Assert.assertEquals(10, floor.getClosestFloorRequest(8, ElevatorDirection.UP));
        Assert.assertEquals(10, floor.getClosestFloorRequest(9, ElevatorDirection.UP));
        Assert.assertEquals(15, floor.getClosestFloorRequest(10, ElevatorDirection.UP));
        Assert.assertEquals(-1, floor.getClosestFloorRequest(40, ElevatorDirection.UP));

    }

    @Test
    public void testClosestFloorRequestDown() {
        floor.addFloorRequest(5, ElevatorDirection.DOWN);
        floor.addFloorRequest(15, ElevatorDirection.DOWN);
        floor.addFloorRequest(10, ElevatorDirection.DOWN);
        floor.addFloorRequest(18, ElevatorDirection.DOWN);

        Assert.assertEquals(18, floor.getClosestFloorRequest(20, ElevatorDirection.DOWN));
        Assert.assertEquals(-1, floor.getClosestFloorRequest(3, ElevatorDirection.DOWN));
        Assert.assertEquals(5, floor.getClosestFloorRequest(6, ElevatorDirection.DOWN));
        Assert.assertEquals(5, floor.getClosestFloorRequest(7, ElevatorDirection.DOWN));
        Assert.assertEquals(5, floor.getClosestFloorRequest(8, ElevatorDirection.DOWN));
        Assert.assertEquals(5, floor.getClosestFloorRequest(9, ElevatorDirection.DOWN));
        Assert.assertEquals(5, floor.getClosestFloorRequest(10, ElevatorDirection.DOWN));
    }

    @Test
    public void testFloorHasARequest() {
        floor.addFloorRequest(5, ElevatorDirection.UP);
        Assert.assertTrue(floor.isFloorHasARequest(5, ElevatorDirection.UP));
        Assert.assertFalse(floor.isFloorHasARequest(5, ElevatorDirection.DOWN));
        Assert.assertFalse(floor.isFloorHasARequest(15, ElevatorDirection.UP));
        Assert.assertFalse(floor.isFloorHasARequest(15, ElevatorDirection.DOWN));

        floor.addFloorRequest(15, ElevatorDirection.DOWN);
        Assert.assertFalse(floor.isFloorHasARequest(15, ElevatorDirection.UP));
        Assert.assertTrue(floor.isFloorHasARequest(15, ElevatorDirection.DOWN));
    }

}

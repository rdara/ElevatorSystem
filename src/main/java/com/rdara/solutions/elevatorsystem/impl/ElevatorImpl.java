/**
 * 
 * Once a person inside the elevator, he choses his desired destination.
 * Elevator itself requires to be should be smart enough service all the person's destinations that are onboard.
 * These destinations BELONG to Elevator. 
 * And hence its more appropriate to make Elevator smart enough to deal with its onboarded persons rather the ElevatorControlSystem.
 * 
 * Once the Elevator starts "move", thats initiated by the ElevatorControlSystem, it moves up and down and serve all the persons
 * inside the elevator and stop at the "floor" as requested by the ElevatorControlSystem.
 * 
 * 
 * WHY Both Direction and Status?
 * While lift is going up/down (direction), it might be hating either for a person in elevator to get out
 * or to serve a floor request to get persons on-board. After the, "HALT", the elevator continues its movement.
 * As we have to represent that UP and HALT and DOWN and HALT, has both Direction and Status.
 * 
 * Ofocurse one can argue, why not MOVE_UP_HALT and MOVE_DOWN_HALT......
 * 
 * That falls on the design principal (LINEAR vs CARTESIAN) ... we may be having STOPPED, SERVICED, other states and the cartesian
 * number of statuses (DIRECTION X STATUS) makes it more difficult to manage over linear (DIRECTION + STATUS).
 * 
 * 
 */
package com.rdara.solutions.elevatorsystem.impl;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.rdara.solutions.elevatorsystem.interfaces.Elevator;
import com.rdara.solutions.elevatorsystem.model.ElevatorDirection;
import com.rdara.solutions.elevatorsystem.model.ElevatorStatus;

/**
 * @author Ramesh Dara
 */
public class ElevatorImpl implements Elevator, Runnable {

    static final int WAIT_TIME_IN_MILLS = 100;

    public static final Object FLOOR_REQUEST = new Object();

    AtomicInteger currentFloor = new AtomicInteger(1);
    ElevatorStatus status = ElevatorStatus.STOPPED;
    ElevatorDirection direction = ElevatorDirection.NONE;
    int id;

    //These queues represents person's request of which floor they would like to 
    //go while in the elevator
    //ConcurrentSkipListSet is a better option?

    BlockingQueue<Integer> upQueue = new PriorityBlockingQueue<Integer>();
    BlockingQueue<Integer> downQueue = new PriorityBlockingQueue<Integer>(11, new Comparator<Integer>() {
        public int compare(Integer x, Integer y) {
            if (x < y)
                return 1;
            if (x > y)
                return -1;
            return 0;
        }
    });

    public ElevatorImpl(int id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#add(int)
     */
    public void add(int floor) {
        int cf = currentFloor.get();
        if (floor > cf) {
            upQueue.add(floor);
        } else if (floor < cf) {
            downQueue.add(floor);
        } else {
            //lift is at the desired floor....
            halt();
        }
        synchronized (FLOOR_REQUEST) {
            FLOOR_REQUEST.notifyAll();
        }
    }

    /*
     * The "move" incrementally serves all the requests and gets stopped.
     * 
     * If its moving upi, it will continue to move up halting at floor requests and person's requests within elevator.
     * 
     * After it reaches the maximum floor of all the requests, it switches direction to serve requests in other direction.
     * 
     * Once after all the requests are fulfilled, it will halt!
     * 
     * Floor requests and elevator requests can be done any time and are honored.
     * 
     * (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#move()
     */

    public void move() {

        synchronized (FLOOR_REQUEST) {
            while (upQueue.isEmpty() && downQueue.isEmpty()) {
                try {
                    FLOOR_REQUEST.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        while (!upQueue.isEmpty() || !downQueue.isEmpty()) {
            switch (direction) {
            case UP:
                while (!upQueue.isEmpty()) {
                    moveUp();
                }
                direction = ElevatorDirection.DOWN;
                break;
            case DOWN:
                while (!downQueue.isEmpty()) {
                    moveDown();
                }
                direction = ElevatorDirection.UP;
                break;
            case NONE:
                if (!upQueue.isEmpty()) {
                    direction = ElevatorDirection.UP;
                } else if (!downQueue.isEmpty()) {
                    direction = ElevatorDirection.DOWN;
                } else {
                    direction = ElevatorDirection.UP;
                }
            }
        }
        setElevatorDirection(ElevatorDirection.NONE);
        setElevatorStatus(ElevatorStatus.STOPPED);
    }

    public void run() {
        while (true) {
            move();
        }
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#getElevatorStatus()
     */
    public ElevatorStatus getElevatorStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#setElevatorStatus(com.rdara.solutions.elevatorsystem.model.ElevatorStatus)
     */
    public void setElevatorStatus(ElevatorStatus status) {
        this.status = status;
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#getElevatorDirection()
     */
    public ElevatorDirection getElevatorDirection() {
        return direction;
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#setElevatorDirection(com.rdara.solutions.elevatorsystem.model.ElevatorDirection)
     */
    public void setElevatorDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#getCurrentFloor()
     */
    public int getCurrentFloor() {
        // TODO Auto-generated method stub
        return currentFloor.get();
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#halt()
     */
    public void halt() {
        //Wait a while
        setElevatorStatus(ElevatorStatus.HALTED);
        waitFor();
    }

    /* (non-Javadoc)
     * @see com.rdara.solutions.elevatorsystem.interfaces.Elevator#getId()
     */
    public int getId() {
        // TODO Auto-generated method stub
        return id;
    }

    private void moveUp() {
        if (!upQueue.isEmpty()) {
            setElevatorDirection(ElevatorDirection.UP);
            setElevatorStatus(ElevatorStatus.MOVING_UP);
            while (upQueue.peek() > currentFloor.get()) {
                //wait - time taken to move from one floor to another floor
                waitFor();
                currentFloor.incrementAndGet();
            }
            if (upQueue.peek() == currentFloor.get()) {
                upQueue.poll();
                halt();
            }
        }
    }

    private void moveDown() {
        if (!downQueue.isEmpty()) {
            setElevatorStatus(ElevatorStatus.MOVING_DOWN);
            setElevatorDirection(ElevatorDirection.DOWN);
            while (downQueue.peek() < currentFloor.get()) {
                //wait - time taken to move from one floor to another floor
                waitFor();
                currentFloor.decrementAndGet();
            }
            if (downQueue.peek() == currentFloor.get()) {
                downQueue.poll();
                halt();
            }
        }
    }

    private void waitFor() {
        try {
            TimeUnit.MILLISECONDS.sleep(WAIT_TIME_IN_MILLS);
        } catch (InterruptedException e) {
        }
    }

}

# ElevatorSystem

#How to Obtain
git clone https://github.com/rdara/ElevatorSystem.git

#How to Run
Switch to 'ElevatorSystem' folder of the code that you obtianed with git clone.
Issue the comman 'mvn install'

#Simulator
Simulator is included as a junit test case at SimultaorTest.java

#Cases considered
- The elevator transit time from one floor to other floor (capped at 100 ms)
- The elevator halts either for a floor request or elevator request (capped at 100 ms)
- The floor request is for to move up or move down
- A passenger inside an elevator can select any floor despite its direction. The elevator switches direction optimally.
- Requests can come any time
- The Elevator CoControl System can chose an Elevator that can best serve a floor request. If a passenger requests to move up on a floor, then if there is any elevator thats close and moving up is selected. If not, then closest stopped elevator is selected. If not, first elevator is selected.
      

#Sample Output of the simulator
Simulation STARTTING.....
The elevator progress and floor requests (random times) are shown in output as they occur
Initial requested floor: 5
Elevator at 1 floor and is MOVING_UP
Adding floor request for : 10
Adding floor request for : 12
Elevator at 2 floor and is MOVING_UP
Adding floor request for : 5
Adding floor request for : 15
Elevator at 3 floor and is MOVING_UP
Adding floor request for : 7
Elevator at 4 floor and is MOVING_UP
Elevator at 5 floor and is MOVING_UP
Elevator at 5 floor and is HALTED
Elevator at 5 floor and is MOVING_UP
Elevator at 5 floor and is HALTED
Elevator at 5 floor and is MOVING_UP
Elevator at 6 floor and is MOVING_UP
Elevator at 7 floor and is MOVING_UP
Elevator at 7 floor and is HALTED
Elevator at 7 floor and is MOVING_UP
Elevator at 8 floor and is MOVING_UP
Elevator at 9 floor and is MOVING_UP
Elevator at 10 floor and is MOVING_UP
Elevator at 10 floor and is HALTED
Elevator at 10 floor and is MOVING_UP
Elevator at 11 floor and is MOVING_UP
Elevator at 12 floor and is MOVING_UP
Elevator at 12 floor and is HALTED
Elevator at 12 floor and is MOVING_UP
Elevator at 13 floor and is MOVING_UP
Elevator at 14 floor and is MOVING_UP
Elevator at 15 floor and is MOVING_UP
Elevator at 15 floor and is HALTED

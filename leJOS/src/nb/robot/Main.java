package nb.robot;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
  /**
   * @param args
   * @throws InterruptedException 
   */
  public static void main(String[] args) throws InterruptedException {
    SnatcherRobot robot = new SnatcherRobot();
    SnatcherBehavior snatcher = new SnatcherBehavior(robot);
    VotingBehavior voting = new VotingBehavior(robot);
    ActivateVotingBehavior activator = new ActivateVotingBehavior(voting);
    DeactivateVotingBehavior deactivator = new DeactivateVotingBehavior(voting);
    CancelProgramBehavior cancelProgram = new CancelProgramBehavior();
    System.out.println("Press button...");
    int pressed = Button.waitForPress();
    if (pressed == Button.ID_RIGHT) {
      voting.setActive(true);
    }
    LCD.clear();
    Behavior [] bArray = {
        snatcher,
        voting,
        activator, 
        deactivator,
        cancelProgram};
    Arbitrator arbitrator = new Arbitrator(bArray);
    arbitrator.start();
  }
}


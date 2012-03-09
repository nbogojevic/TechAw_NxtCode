package nb.robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
  /**
   * @param args
   */
  public static void main(String[] args) {
    SnatcherRobot snatcher = new SnatcherRobot();
    SnatcherBehavior snatcherBehavior = new SnatcherBehavior(snatcher);
    Behavior [] bArray = {snatcherBehavior};
    Arbitrator arbitrator = new Arbitrator(bArray);
    arbitrator.start();
//    Button.ESCAPE.waitForPressAndRelease();
  }
}


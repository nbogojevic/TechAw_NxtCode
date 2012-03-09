package nb.robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
  /**
   * @param args
   */
  public static void main(String[] args) {
    SnatcherRobot robot = new SnatcherRobot();
    SnatcherBehavior snatcher = new SnatcherBehavior(robot);
    VotingBehavior voting = new VotingBehavior(robot);
    ActivateVotingBehavior activator = new ActivateVotingBehavior(voting);
    DeactivateVotingBehavior deactivator = new DeactivateVotingBehavior(voting);
    CancelProgramBehavior cancelProgram = new CancelProgramBehavior();
    Behavior [] bArray = {snatcher,
//        votingBehavior,
        activator, 
        deactivator,
        cancelProgram};
    Arbitrator arbitrator = new Arbitrator(bArray);
    arbitrator.start();
  }
}


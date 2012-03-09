package nb.robot;

import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

public class VotingBehavior implements Behavior {
  private SnatcherRobot robot;
  private ColorReader colorReader;
  
  public VotingBehavior(SnatcherRobot robot) {
    this.robot = robot;
    colorReader = new ColorReader(robot);
  }

  @Override
  public void action() {
    try {
      Action action = colorReader.waitNextAction();
      if (action != null) {
        robot.perform(action);
      }
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
  }

  @Override
  public void suppress() {
    colorReader.suppress();
  }

  @Override
  public boolean takeControl() {
    return robot.getColorID() != Color.BLACK;
  }
}

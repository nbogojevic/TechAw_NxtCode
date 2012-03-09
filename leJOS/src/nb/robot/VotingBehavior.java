package nb.robot;

import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

public class VotingBehavior implements Behavior {
  private SnatcherRobot robot;
  private ColorReader colorReader;
  private boolean active;
  
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
    return active && robot.getColorID() != Color.BLACK;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}

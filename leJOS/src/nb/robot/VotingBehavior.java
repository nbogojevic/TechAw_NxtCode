package nb.robot;

import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;

public class VotingBehavior implements Behavior {
  private static final double SLOW_ROTATE = 15d;
  private SnatcherRobot robot;
  private LightReader colorReader;
  private boolean active;
  
  public VotingBehavior(SnatcherRobot robot) {
    this.robot = robot;
    colorReader = new LightReader(robot.colorReader);
  }

  @Override
  public void action() {
    robot.slowSpeed();
    try {
      Action action = colorReader.waitNextAction();
      if (action != null) {
        robot.perform(action);
      }
      else {
        LCD.drawString("NULL", 0, 3);
      }
      Thread.sleep(1000);
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
    return active;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}

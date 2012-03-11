package nb.robot;

import lejos.robotics.subsumption.Behavior;

public class VotingBehavior implements Behavior {
  private SnatcherRobot robot;
  private LightReader colorReader;
  private boolean active;
  
  public VotingBehavior(SnatcherRobot robot) {
    this.robot = robot;
    colorReader = new LightReader(robot);
  }

  @Override
  public void action() {
    robot.slowSpeed();
    try {
      Action action = colorReader.waitNextAction();
      if (action != null) {
        robot.perform(action);
      }
      Thread.yield();
    } catch (Exception e) {
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

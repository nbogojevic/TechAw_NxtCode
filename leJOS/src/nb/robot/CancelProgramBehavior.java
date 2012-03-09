package nb.robot;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class CancelProgramBehavior implements Behavior {
  public CancelProgramBehavior() {
  }

  @Override
  public void action() {
    System.exit(0);
  }

  @Override
  public void suppress() {
  }

  @Override
  public boolean takeControl() {
    return Button.ESCAPE.isPressed();
  }

}

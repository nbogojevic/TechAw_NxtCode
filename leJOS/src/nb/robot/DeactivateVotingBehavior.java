package nb.robot;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class DeactivateVotingBehavior implements Behavior {
  private VotingBehavior votingBehavior;

  public DeactivateVotingBehavior(VotingBehavior votingBehavior) {
    this.votingBehavior = votingBehavior;
  }

  @Override
  public void action() {
    votingBehavior.setActive(false);
  }

  @Override
  public void suppress() {
  }

  @Override
  public boolean takeControl() {
    return Button.LEFT.isPressed();
  }

}

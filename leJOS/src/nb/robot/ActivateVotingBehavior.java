package nb.robot;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class ActivateVotingBehavior implements Behavior {
  private VotingBehavior votingBehavior;

  public ActivateVotingBehavior(VotingBehavior votingBehavior) {
    this.votingBehavior = votingBehavior;
  }

  @Override
  public void action() {
    Sound.beepSequenceUp();
    votingBehavior.setActive(true);
  }

  @Override
  public void suppress() {
  }

  @Override
  public boolean takeControl() {
    return Button.RIGHT.isPressed();
  }

}

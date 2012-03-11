package nb.robot;

import java.util.ArrayList;

import nb.robot.commands.PilotCommandFactory;

import lejos.robotics.subsumption.Behavior;

public class SnatcherBehavior implements Behavior, Suppressor {
  private boolean suppress;
  private SnatcherRobot snatcher;
  private ArrayList<Runnable> actions;
  private PilotCommandFactory commandFactory;

  public SnatcherBehavior(SnatcherRobot snatcher) {
    super();
    this.snatcher = snatcher;
    commandFactory = new PilotCommandFactory(snatcher);
    actions = new ArrayList<Runnable>();
    initActions();
  }

  private void initActions() {
    Runnable grab = new Runnable() {      
      public void run() { snatcher.grab(); }
    };
    Runnable findObject = new Runnable() {      
      public void run() { snatcher.findObject(SnatcherBehavior.this); }
    };
    Runnable moveCloser = new Runnable() {      
      public void run() { snatcher.moveCloser(); }
    };
    Runnable backtrack = new Runnable() {      
      public void run() { snatcher.backtrack(); }
    };
    Runnable release = new Runnable() {      
      public void run() { snatcher.release(); }
    };
    Runnable advance = new Runnable() {      
      public void run() { snatcher.advance(); }
    };
    actions.add(grab);
    actions.add(findObject);
    actions.add(moveCloser);
    actions.add(findObject);
    actions.add(moveCloser);
    actions.add(backtrack);
    actions.add(release);
    actions.add(advance);
    actions.add(grab);
    actions.add(commandFactory.turnBack());
    actions.add(commandFactory.travel(60));
    actions.add(release);
    actions.add(backtrack);
    actions.add(grab);
    actions.add(commandFactory.turnBack());
    actions.add(commandFactory.travel(90));
  }

  @Override
  public void action() {
    suppress = false;
    for (Runnable action : actions) {
      action.run();
      if (suppress) {
        return;
      }
      Thread.yield();
    }
  }

  @Override
  public void suppress() {
    suppress = true;
  }

  @Override
  public boolean takeControl() {
    return true;
  }

  @Override
  public boolean isSupressed() {
    return suppress;
  }

}

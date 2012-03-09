package nb.robot;

import java.util.ArrayList;

import lejos.robotics.subsumption.Behavior;

public class SnatcherBehavior implements Behavior, Suppressor {

  private boolean suppress;
  private SnatcherRobot snatcher;
  private ArrayList<Runnable> actions;

  public SnatcherBehavior(SnatcherRobot snatcher) {
    super();
    this.snatcher = snatcher;
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
    Runnable left = new Runnable() {      
      public void run() { snatcher.rotate(90); }
    };
    Runnable turnBack = new Runnable() {      
      public void run() { snatcher.rotate(180); }
    };
    Runnable goForward60cm = new Runnable() {      
      public void run() { snatcher.travel(60); }
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
    actions.add(turnBack);
    actions.add(goForward60cm);
    actions.add(release);
    actions.add(backtrack);
    actions.add(turnBack);
    actions.add(advance);
    actions.add(left);
    actions.add(advance);
    actions.add(left);
    actions.add(goForward60cm);
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

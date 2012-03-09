package nb.robot;

import lejos.robotics.Color;
import lejos.robotics.ColorDetector;

public class ColorReader {
  private static final long WAIT_TOO_LONG = 30000L;  
  private ColorDetector colorDetector;
  private boolean supress;
  private boolean timeout;
  private long start;
  private int currentColorId;

  public ColorReader(ColorDetector colorDetector) {
    this.colorDetector = colorDetector;
  }

  public Action waitNextAction() throws InterruptedException {
    start = System.currentTimeMillis();
    currentColorId = Color.BLACK;
    if (!nextColorToken()) {
      return null;
    }
    int token1 = currentColorId;
    if (nextColorToken()) {
      return null;
    }
    int token2 = currentColorId;
    if (token2 == Color.BLACK) {
      lostMessage();
      return Action.stop;
    }
    return decode(new int[] { token1, token2 }); 
  }

  private boolean nextColorToken() throws InterruptedException {
    int lastColorId = currentColorId;
    while (lastColorId == currentColorId || supress || timeout) {
      lastColorId = currentColorId;
      currentColorId = colorDetector.getColorID();
      checkWaitingTooLong();
      Thread.sleep(100);
    }
    return !(supress || timeout);
  }
  
  private Action decode(int[] tokens) {
    for (Action action : Action.values()) {
      if (action.match(tokens)) {
        return action;
      }
    }
    return null;
  }

  private void lostMessage() {
    // TODO Auto-generated method stub
    
  }

  private void checkWaitingTooLong() {
    long waiting = System.currentTimeMillis() - start;
    timeout = waiting > WAIT_TOO_LONG;
  }



  public void suppress() {
    supress = true;

  }

}

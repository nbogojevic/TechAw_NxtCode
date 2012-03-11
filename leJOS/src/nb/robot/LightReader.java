package nb.robot;

import java.util.Queue;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.LightDetector;
import lejos.util.Delay;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LightReader {
  private LightDetector lightDetector;
  private boolean supress;
  private int calibratedTokenMeasures[];
  private static final int STANDARD_TOKEN_MEASURES[] = new int[] {483, 420, 341, 247, 88, 64};
  private int usefulTokens;
  private Queue<Action> actions;

  public LightReader(LightDetector lightDetector) {
    this.lightDetector = lightDetector;
    this.calibratedTokenMeasures = new int[Tokens.TOKEN_COUNT];
    actions = new Queue<Action>();
    calibrate();
  }

  public Action waitNextAction() throws InterruptedException {
    LCD.clearDisplay();
    while (!supress) {
      int measure = lightDetector.getNormalizedLightValue();
      if (findToken(measure) == Tokens.BLACK) {
        break;
      }
      Thread.yield();
    }
    while (!supress) {
      int measure = lightDetector.getNormalizedLightValue();
      if (findToken(measure) != Tokens.BLACK) {
        break;
      }
      Thread.yield();
    }
    Delay.msDelay(200);
    int measure1 = lightDetector.getNormalizedLightValue();
    int token1 = findToken(measure1);
    LCD.drawInt(measure1, 4, 5, 1);
    LCD.drawInt(token1, 4, 0, 1);

    while (!supress) {
      int measure = lightDetector.getNormalizedLightValue();
      if (findToken(measure) != token1) {
        break;
      }
      Thread.yield();
    }
    Delay.msDelay(200);
    int measure2 = lightDetector.getNormalizedLightValue();
    int token2 = findToken(measure2);
    LCD.drawInt(measure2, 4, 5, 2);
    LCD.drawInt(token2, 4, 0, 2);
    Sound.beep();
    if (supress) {
      return Action.stop;
    }
    if (token2 == Tokens.BLACK) {
      lostMessage();
      return Action.stop;
    }
    Sound.beep();
    Action action = decode(new int[] { token1, token2 });
    actions.addElement(action);
    LCD.drawString(action.toString(), 0, 5);
    return action; 
  }

  private int findToken(int measure) {
    if (measure >= calibratedTokenMeasures[0]) {
      return 0;
    }
    for (int i=1; i<usefulTokens; i++) {
      if (measure >= calibratedTokenMeasures[i]) {
        if (abs(measure-calibratedTokenMeasures[i-1]) < abs(measure-calibratedTokenMeasures[i])) {
          return i-1;
        }        
      }
    }
    
    return usefulTokens-1;
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
    Sound.buzz();
  }

  public void suppress() {
    supress = true;
  }
  
  public void calibrate() {
    Sound.twoBeeps();
    int smallestDifference = Integer.MAX_VALUE;
    for (int i=0; i<calibratedTokenMeasures.length; i++) {
      if (Button.waitForPress() == Button.ID_ESCAPE) {
        System.out.println("Using default calibration for remaining tokens.");
        defaultCalibration(i);
        report();
        Sound.twoBeeps();
        Button.waitForPress();
        LCD.clear();
        return;
      }
      int sum = 0;
      int max = 0;
      int min = Integer.MAX_VALUE;
      for (int j=0; j<10; j++) {
        int measured = lightDetector.getNormalizedLightValue();
        max = max(measured, max);
        min = min(measured, min);
        sum += measured;
        Delay.msDelay(100);
      }
      int avg = sum / 10;
      calibratedTokenMeasures[i] = avg;
      System.out.println(""+i+"="+avg+" "+max+" "+min);
      smallestDifference = min(smallestDifference, abs(max-avg));
      smallestDifference = min(smallestDifference, abs(min-avg));
      Sound.beep();
    }
    smallestDifference = max(10, smallestDifference/2);
    Sound.twoBeeps();
    removedUnusedTokens(smallestDifference);
    report();
    Sound.twoBeeps();
    Button.waitForPress();
    LCD.clear();
  }

  private void defaultCalibration(int start) {
    for (int i=start; i<STANDARD_TOKEN_MEASURES.length; i++) {
      calibratedTokenMeasures[i] = STANDARD_TOKEN_MEASURES[i];
    }
    usefulTokens = STANDARD_TOKEN_MEASURES.length;
  }

  private void report() {
    System.out.println("Tokens:" + usefulTokens);
    for (int i=0; i<usefulTokens; i++) {
      System.out.println(""+i+"="+calibratedTokenMeasures[i]);
    }
    int minDistance = findMinimalTokenDistance();
    System.out.println("MinDistance:" + minDistance);
  }

  private int findMinimalTokenDistance() {
    int minDistance = Integer.MAX_VALUE;
    for (int i=1; i<usefulTokens; i++) {
      int currentDistance = abs(calibratedTokenMeasures[i]-calibratedTokenMeasures[i-1]);
      minDistance = min(minDistance, currentDistance);
    }
    return minDistance;
  }

  private void removedUnusedTokens(int smallestDifference) {
    usefulTokens = calibratedTokenMeasures.length;
    for (int i=1; i<usefulTokens; ) {
      int currentDistance = abs(calibratedTokenMeasures[i]-calibratedTokenMeasures[i-1]) / 2;
      if (currentDistance < smallestDifference) {
        for (int j=i; j<usefulTokens; j++) {
          calibratedTokenMeasures[j-1] = calibratedTokenMeasures[j];
        }
        usefulTokens--;
      }
      else {
        i++;
      }
    }
  }
}

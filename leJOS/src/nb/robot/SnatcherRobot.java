package nb.robot;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.ColorDetector;
import lejos.robotics.navigation.DifferentialPilot;

public class SnatcherRobot extends DifferentialPilot implements ColorDetector {
  private static final int ALREADY_CLOSE = 20;
  private static final int ARM_SPEED = 180;
  private static final int RELEASE_ANGLE = 400;
  
  private static final int FULL_CIRCLE = 360;
  private static final int ROTATE_SPEED = 70;
  private static final int MOVEMENT_SPEED = 10;
  private static final int BACKTRACK_DISTANCE = 20;
  
  private TouchSensor armLimiterTouchSensor = new TouchSensor(SensorPort.S1);
  private UltrasonicSensor distanceSensor = new UltrasonicSensor(SensorPort.S4);
  private ColorSensor colorReader = new ColorSensor(SensorPort.S3);  

  private NXTRegulatedMotor armMotor = Motor.A;
  
  private int closestTargetDistance;
  
  public SnatcherRobot() {
    super(3.6f, 16.8f, Motor.C, Motor.B);
    setRotateSpeed(ROTATE_SPEED);
    setTravelSpeed(MOVEMENT_SPEED);
    armMotor.setSpeed(ARM_SPEED);
  }
     
  public void grab() {
    armMotor.forward();
    while (!armLimiterTouchSensor.isPressed()) {
    }
    armMotor.stop();      
  }
  
  public void release() {
    armMotor.rotate(-RELEASE_ANGLE);      
  }
  
  public void findObject(Suppressor suppressor) {
    double angleOfTarget = scanForObject(suppressor);
    rotate(-angleOfTarget);
  }

  private double scanForObject(Suppressor suppressor) {
    closestTargetDistance = distanceSensor.getDistance();
    if (closestTargetDistance < ALREADY_CLOSE) {
      System.out.println(closestTargetDistance);
      System.out.println(closestTargetDistance);
      System.out.println(closestTargetDistance);
      System.out.println(closestTargetDistance);
      return 0;
    }
    reset();
    rotate(FULL_CIRCLE, true);
    double angleOfTarget = 0;
    while (isMoving() && (suppressor == null || !suppressor.isSupressed())) {
      int measuredDistance = distanceSensor.getDistance();
      if (measuredDistance < closestTargetDistance) {
        closestTargetDistance = measuredDistance;
        angleOfTarget = getMovement().getAngleTurned();
      }
      Thread.yield();
    }
    angleOfTarget = getMovement().getAngleTurned()-angleOfTarget;
    stop();
    return angleOfTarget;
  }

  public void moveCloser() {
    if (closestTargetDistance >= ALREADY_CLOSE) {
      travel(closestTargetDistance*0.75);
    }
  }

  public void backtrack() {
    travel(-BACKTRACK_DISTANCE);
  }
  
  public void advance() {
    travel(BACKTRACK_DISTANCE);
  }
  
  @Override
  public int getColorID() {
    return colorReader.getColorID();
  }

  @Override
  public Color getColor() {
    return colorReader.getColor();
  }

  public void perform(Action action) {
    switch (action) {
    case stop:
      stop();
      break;
    case forward:
      forward();
      break;
    case reverse:
      backward();
      break;
    case left:
      rotateLeft();
      break;
    case right:
      rotateRight();
      break;
    case grab:
      break;
    case drop:
      break;
    }
  }
  
}
package nb.robot.commands;

import lejos.robotics.navigation.DifferentialPilot;

public class PilotCommandFactory {
  private DifferentialPilot pilot;
  
  public PilotCommandFactory(DifferentialPilot pilot) {
    super();
    this.pilot = pilot;
  }

  public Runnable left()  { 
    return new Runnable() {      
      public void run() { pilot.rotate(90); }
    };
  }
  public Runnable right()  { 
    return new Runnable() {      
      public void run() { pilot.rotate(-90); }
    };
  }
  public Runnable turnBack()  { 
    return new Runnable() {      
      public void run() { pilot.rotate(160); }
    };
  }
  public Runnable travel(final int distance)  { 
    return new Runnable() {      
      public void run() { pilot.travel(distance); }
    };
  }
}

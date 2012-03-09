package nb.robot;

import lejos.robotics.Color;

public interface Tokens {
  public static final int[] FORWARD_TOKENS = { Color.GREEN, Color.BLUE};
  public static final int[] REVERSE_TOKENS = { Color.GREEN, Color.YELLOW };
  public static final  int[] LEFT_TOKENS = { Color.BLUE, Color.RED };
  public static final  int[] RIGHT_TOKENS = { Color.BLUE, Color.GREEN };
  public static final int[] STOP_TOKENS = { Color.RED, Color.YELLOW };
  public static final int[] GRAB_TOKENS = { Color.YELLOW, Color.GREEN };
  public static final int[] DROP_TOKENS = { Color.YELLOW, Color.RED };  
  public static final int[] QUIT_TOKENS = { Color.RED, Color.BLUE };  

}

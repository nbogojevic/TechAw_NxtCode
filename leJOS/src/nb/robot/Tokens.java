package nb.robot;


public interface Tokens {
  public static int WHITE = 0;
  public static int LIGHT_GRAY = 1;
  public static int MED_GRAY = 2;
  public static int DARK_GRAY = 3;
  public static int VERY_DARK_GRAY = 4;
  public static int USEFUL_TOKENS = 5;
  public static int BLACK = 5;
  public static int TOKEN_COUNT = 6;
  public static final int[] FORWARD_TOKENS = { WHITE, LIGHT_GRAY};
  public static final int[] REVERSE_TOKENS = { WHITE, MED_GRAY };
  public static final int[] LEFT_TOKENS = { WHITE, DARK_GRAY };
  public static final int[] RIGHT_TOKENS = { LIGHT_GRAY, WHITE };
  public static final int[] STOP_TOKENS = {  LIGHT_GRAY, MED_GRAY };
  public static final int[] GRAB_TOKENS = { LIGHT_GRAY, DARK_GRAY };
  public static final int[] DROP_TOKENS = { MED_GRAY, WHITE };  
  public static final int[] QUIT_TOKENS = { MED_GRAY, LIGHT_GRAY };  

}

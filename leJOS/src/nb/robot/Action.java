package nb.robot;

import static nb.robot.Tokens.*;

enum Action { 
  stop(STOP_TOKENS), forward(FORWARD_TOKENS), reverse(REVERSE_TOKENS), 
  left(LEFT_TOKENS), right(RIGHT_TOKENS), grab(GRAB_TOKENS), drop(DROP_TOKENS), 
  quit(QUIT_TOKENS);
  
  
  private int []tokens;
  
  private Action(int[] tokens) {
    assert(tokens != null);
    assert(tokens.length == 2);
    this.tokens = tokens;
  }
  
  public boolean match(int[] tokenToMatch) {
    if (tokenToMatch != null && tokens != null && tokenToMatch.length == tokens.length) {
      for (int i = 0; i < tokenToMatch.length; i++) {
        if (tokenToMatch[i] != tokens[i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  
}
package interest_group_s;

/**
 * enum: states that the client is in
 */
public enum State {
	NOT_LOGGED_IN,  // not logged in
        LOGGED_IN,      // logged in, waiting for user input 
        IN_AG_CMD,      // in "ag" command
        IN_SG_CMD,      // in "sg" command
        IN_RG_CMD       // in "rg" command
}
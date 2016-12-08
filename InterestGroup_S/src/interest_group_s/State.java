package interest_group_s;

import java.io.Serializable;

/**
 * enum: states that the client is in
 */
public final class State implements Serializable {
        // not logged in
	public static final String NOT_LOGGED_IN = "NOT LOGGED IN";
        // logged in, waiting for user input 
        public static final String LOGGED_IN = "LOGGED IN";
        // in "ag" command
        public static final String IN_AG_CMD = "IN AG CMD";
        // in "sg" command
        public static final String IN_SG_CMD = "IN SG CMD";
        // in "rg" command
        public static final String IN_RG_CMD = "IN RG CMD";
}
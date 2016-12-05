
package interest_group_s;

import com.fasterxml.jackson.core.*;

/**
 *
 * @author majiasheng
 */

/**
 * Server side
 */
public class InterestGroup_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    void login_handler() {}
	
	/****************************************
					ag
	****************************************/
	/** 
	 * lists all existing groups, a default number of groups at a time 
	 */
    void ag_handler() {}
	
	/** 
	 * lists N groups at a time
	 */
	void ag_handler(int N);
	
	/****************************************
					sg
	****************************************/
	void sg_handler() {}

	
	/****************************************
					rg
	****************************************/
	void rg_handler() {}
		
	/****************************************
				SUB COMMNADS
	****************************************/
	/**
	 * - subscribe to groups
	 */
	void s_handler() {}

	/** 
	 *	unsubscribe
	 */
	void u_handler() {}
	
	/** 
	 *	lists the next N discussion groups
	 */
	void n_handler() {}
	
	/** 
	 *	exit from ag command
	 */
	void q_handler() {}
	
	/** 
	 *	marks a post as read
	 */	
	void r_handler() {} 
	
	/** 
	 *	post to the group
	 */	
	void p_handler() {} 
}

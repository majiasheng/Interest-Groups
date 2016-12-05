
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
    }
	
	/**
     * Updates program info on every client change requests
	*/
	static void update() {}
		

	static void login_handler() {}
	
	/****************************************
					ag - Ruoping
	****************************************/
	/** 
	 * lists all existing groups, a default number of groups at a time 
	 */
    static void ag_handler() {}
	
	/** 
	 * lists N groups at a time
	 */
	static void ag_handler(int N){};

	/**
	 * - subscribe to groups
	 */
	static void s_handler_ag() {}

	/** 
	 *	unsubscribe
	 */
	static void u_handler_ag() {}
	
	/** 
	 *	lists the next N discussion groups
	 */
	static void n_handler_ag() {}
	
	/** 
	 *	exit from ag command
	 */
	static void q_handler_ag() {}
	
	/****************************************
					sg - Liwen
	****************************************/
	static void sg_handler() {}
	
	/** 
	 *	marks a post as read
	 */	
	static void r_handler_sg() {} 

	/** 
	 *	lists the next N discussion groups
	 */	
	static void n_handler_sg() {} 

	/** 
	 *	post to the group
	 */	
	static void p_handler_sg() {} 

	/** 
	 *	exit from ag command
	 */
	static void q_handler_sg() {}
	
	/****************************************
					rg - Jia-Sheng
	****************************************/
	static void rg_handler() {}

	/****************************************
				OTHER FUNCTIONS
	****************************************/
	
	void createUser() {}

	
}

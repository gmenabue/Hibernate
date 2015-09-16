/**
 * 
 */
package clasesDAO;

import org.hibernate.Session;

/**
 * @author Giordano Menabue
 *
 */
public class SuperDAO {
	
	//Session a setear.
	Session session = null;
	/**
	 * Constructor de la clase SuperDAO.
	 */
	public SuperDAO(){
		//constructor
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	
	

}

package sessionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionManager {
	
	/**
	 * Constructor de Session Manager. 
	 */
	private SessionManager(){
		//Constructor privado de la clase SessionManager
		//para usar Singleton
	}
	
	//Objeto privado de SessionFactory
	private static SessionFactory nueva_session_factory;
	
	static {
		
		Configuration configuration = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	nueva_session_factory = configuration.buildSessionFactory(builder.build());
		
	}
	
	
	
	
	//Objeto Session para abrir una nueva Session
	private static Session session = nueva_session_factory.openSession();
/**
 * Método para obtener una nueva Session.
 * @return Objeto de SessionFactory
 */
	public static Session obtener_session(){
		
		return session;
		
	}
	
	/**
	 * Método para obtener una nueva Session.
	 * @return
	 */
	public static Session obtenerSesionNueva ()
	{
		return nueva_session_factory.openSession();
	}
	/**
	 * Método para cerrar la Session.
	 * @param session
	 */
	public static void cerrar_session(Session session){
		session.close();
	}
	
	/**
	 * Método para cerrar la SessionFactory
	 */
	public static void cerrar_session_factory(){
		nueva_session_factory.close();
	}
	
}

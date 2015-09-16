package gior.examples.recuperarpaises;


import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import Hibernate.Countries;
import Hibernate.Regions;

public class RecuperarPaises {
	
	public static boolean recuperar_paises(){
		boolean recuperado = false;
		
		//Cargo la configuración: MAPPING entre Tablas y Objetos así como La descripción de la base de de datos
    	//Dicho de otra forma: cargamos en memoria en la clase Configuration el hibernate.cfg.xml
    	Configuration configuration = new Configuration().configure();
    	//Preparo a un objeto, que será el encargado de generarme el estado de comunicación con la base de datos
    	//StandardServiceRegistryBuilder se preconfigura el entorno a emplear
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	
    	//Ahora sí, obtengo el objeto SessionFactory, a partir de la anterior clase /servicio
    	//que ya es la clase que encapsula al Pool y demás recursos físicos
    	SessionFactory miSessionFactory = configuration.buildSessionFactory(builder.build());
    	
    	//Ahora ya con sesion, obtengo y manejo conexiones que me va dando SessionFactory
    	Session session = miSessionFactory.openSession();
    	
    	
    	Transaction transaction = null;
    	//Y procedo a guardarlo --> INICIO DE LA TRANSACCION
    	
    	Regions region = new Regions();
    	Countries country = new Countries();
    	
    	
    	/*Al hacer el método . createSQLQuery de la sesión, el objeto se 
    	 *pone en estado PERSISTENT por lo que las modificaciones que hagamos
    	 *al objeto se harán directamente tambien enla base de datos. 
    	 */
    	
    	
    	try 
    	{
    		
    		transaction = session.beginTransaction();
    		@SuppressWarnings("unchecked")
			List<Regions> list = session.createSQLQuery("select * from regions").addEntity(Regions.class).list();
    		Iterator<Regions> it = list.iterator();
    		
    		while (it.hasNext())
    		{
    			region = it.next();
    			System.out.println(region.toString());
    			//Declaro un Set de paises para guardar los paises;
    			Set <Countries> paises = region.getCountrieses();
    			//Declaramos un Iterator para recorrer la lista de paises.
    			Iterator<Countries> itc = paises.iterator();
    				//Recorremos la lista de paises con el while
    				while(itc.hasNext()){
    					country = itc.next();
    					System.out.println(country.toString());
    				}
    			
    			
    			
    			
    			
    			
    		}
    		transaction.commit();//si todo ha ido bien, persisto los cambio, los hago de verdad, no en la copia de la BD
    		recuperado = true;
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacción
    	}
    	finally 
    	{
    		session.close();//haya ido bien o mal, libero recursos!
    		miSessionFactory.close();
    	}
		
		return false;
		
	}

}

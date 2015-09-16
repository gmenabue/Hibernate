package PracticaHibernate;


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import Hibernate.Employees;



/**
 * @author alumno
 *
 */
public class Operaciones {
	
	public static Boolean incremento_salario (){
		Boolean incrementado = false;
		
		//Cargo la configuraci�n: MAPPING entre Tablas y Objetos as� como La descripci�n de la base de de datos
    	//Dicho de otra forma: cargamos en memoria en la clase Configuration el hibernate.cfg.xml
    	Configuration configuration = new Configuration().configure();
    	//Preparo a un objeto, que ser� el encargado de generarme el estado de comunicaci�n con la base de datos
    	//StandardServiceRegistryBuilder se preconfigura el entorno a emplear
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	
    	//Ahora s�, obtengo el objeto SessionFactory, a partir de la anterior clase /servicio
    	//que ya es la clase que encapsula al Pool y dem�s recursos f�sicos
    	SessionFactory miSessionFactory = configuration.buildSessionFactory(builder.build());
    	
    	//Ahora ya con sesion, obtengo y manejo conexiones que me va dando SessionFactory
    	Session session = miSessionFactory.openSession();
    	
    	
    	Transaction transaction = null;
    	//Y procedo a guardarlo --> INICIO DE LA TRANSACCION
    	
    	Employees emp = new Employees();
    	BigDecimal incremento = new BigDecimal(1.2);
    	
    	
    	try 
    	{
    		
    		transaction = session.beginTransaction();
    		@SuppressWarnings("unchecked")
			List<Employees> list = session.createSQLQuery("select * from employees where department_id = 80").addEntity(Employees.class).list();
    		Iterator<Employees> it = list.iterator();
    		
    		while (it.hasNext())
    		{
    			emp = it.next();
    			emp.setSalary(incremento.multiply(emp.getSalary()));
    			//System.out.println(emp.getFirstName());
    			//session.merge(emp);
    			incrementado = true;
    		}
    		transaction.commit();//si todo ha ido bien, persisto los cambio, los hago de verdad, no en la copia de la BD
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacci�n
    	}
    	finally 
    	{
    		session.close();//haya ido bien o mal, libero recursos!
    		miSessionFactory.close();
    	}
		
		return incrementado;
		
	}
	
	public static Boolean disminuir_salario (){
		Boolean disminuido = false;
		
		//Cargo la configuraci�n: MAPPING entre Tablas y Objetos as� como La descripci�n de la base de de datos
    	//Dicho de otra forma: cargamos en memoria en la clase Configuration el hibernate.cfg.xml
    	Configuration configuration = new Configuration().configure();
    	//Preparo a un objeto, que ser� el encargado de generarme el estado de comunicaci�n con la base de datos
    	//StandardServiceRegistryBuilder se preconfigura el entorno a emplear
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	
    	//Ahora s�, obtengo el objeto SessionFactory, a partir de la anterior clase /servicio
    	//que ya es la clase que encapsula al Pool y dem�s recursos f�sicos
    	SessionFactory miSessionFactory = configuration.buildSessionFactory(builder.build());
    	
    	//Ahora ya con sesion, obtengo y manejo conexiones que me va dando SessionFactory
    	Session session = miSessionFactory.openSession();
    	
    	
    	Transaction transaction = null;
    	//Y procedo a guardarlo --> INICIO DE LA TRANSACCION
    	
    	Employees emp = new Employees();
    	BigDecimal disminuir = new BigDecimal(0.8);
    	
    	/*Al hacer el m�todo . createSQLQuery de la sesi�n, el objeto se 
    	 *pone en estado PERSISTENT por lo que las modificaciones que hagamos
    	 *al objeto se har�n directamente tambien enla base de datos. 
    	 */
    	
    	
    	try 
    	{
    		
    		transaction = session.beginTransaction();
    		@SuppressWarnings("unchecked")
			List<Employees> list = session.createSQLQuery("select * from employees where department_id = 80").addEntity(Employees.class).list();
    		Iterator<Employees> it = list.iterator();
    		
    		while (it.hasNext())
    		{
    			emp = it.next();
    			emp.setSalary(disminuir.multiply(emp.getSalary()));
    			//System.out.println(emp.getFirstName());
    			//Como est� en estado PERSISTENT se modifica directamente sin
    			//necesidad de hacer el "session.merge(emp)"
    			disminuido = true;
    		}
    		transaction.commit();//si todo ha ido bien, persisto los cambio, los hago de verdad, no en la copia de la BD
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacci�n
    	}
    	finally 
    	{
    		session.close();//haya ido bien o mal, libero recursos!
    		miSessionFactory.close();
    	}
		
		return disminuido;
		
	}

}

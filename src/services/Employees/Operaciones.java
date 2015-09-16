package services.Employees;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import sessionManager.SessionManager;
import clasesDAO.DepartmentsDAO;
import clasesDAO.EmployeesDAO;
import Hibernate.Departments;
import Hibernate.Employees;



/**
 * @author Giordano Menabue
 *
 */
public class Operaciones {
	//Objeto Logger para el Log
	private final static Logger log = Logger.getLogger("mylog");
	//Objeto de empleado DAO para llamar a los métodos del DAO
	EmployeesDAO empleado_dao;

	/**
	 * Constructor de la clase Operaciones donde inicializoal empleadoDAO
	 */
	public Operaciones(){
		empleado_dao = new EmployeesDAO();
	}
	
	
	/**
	 * Método para incrementar salario de un Empleado!
	 * @return Boolean "Para saber si ha ido bien"
	 */
	public Boolean incremento_salario (){
		//Declaro un Boolean para saber si se incrementó o no.
		Boolean incrementado = false;
	
		//Declaro un BigDecimal para aumentar el salario
    	BigDecimal incremento = new BigDecimal(1.2);
    	Employees emp = new Employees();
    	
    	//Hago un set de la session, llamando al SessionManager
    	empleado_dao.setSession(SessionManager.obtenerSesionNueva());
    	//Inicio la transacción llamando a getSession de SuperDAO
    	Transaction transaction = empleado_dao.getSession().beginTransaction();
    	
    	try 
    	{
    		//Me creo una lista de empleados donde voy a meter los resultados
    		//de read_all, método de EmployeesDAO
    		List<Employees> lista = empleado_dao.read_all();
    		Iterator<Employees> it = lista.iterator();
    		
    		//Recorro la lista
    		while (it.hasNext())
    		{
    			emp = it.next();
    			emp.setSalary(incremento.multiply(emp.getSalary()));
    			//System.out.println(emp.getFirstName());
    			//getSession().merge(emp);
    			incrementado = true;
    		}
    		//Si el proceso ha ido bien, hago Commit.
    		transaction.commit();
    	}
    	catch (Exception e)
    	{
    		//Si el proceso no ha ido bien, capturo la exepción, envío el error
    		// a un log y hago rollback de la transación.
    		log.error("Error en el método Incremento Salario");
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacción
    	}
    	finally 
    	{
    		//Cierro la sesión
    		SessionManager.cerrar_session(empleado_dao.getSession());
    		//SessionManager.cerrar_session_factory();
    		
    	}
		
		return incrementado;
		
	}
	
	
	
	/**
	 * Método para recuperar departamentos por id
	 * @param department_id
	 * @return Lista de empleados del departamento solicitado.
	 */
	public Set<Employees> recuperar_empleados_departamentos_id(int department_id){
		
		
		//Me declaro una lista de departamentos para guardar los departamentos recuperados
		List<Departments> lista = new ArrayList<Departments>();
		//Me declaro un set de empleados para guardar la lista de empleados
		Set<Employees> lista_emp = new HashSet<Employees>();
		//Objeto de DepartmentsDAO
		DepartmentsDAO ddao = new DepartmentsDAO();
		//Obtengo la Session y la seteo en la clase SuperDAO
		ddao.setSession(SessionManager.obtenerSesionNueva());
		//Inicio la transacción
		Transaction transaction = ddao.getSession().beginTransaction();
		
		
		
		try 
    	{
			//Leo todos los departamentos y los guardo en la lista de departamentos
    		lista = ddao.read_all2(department_id);
    		Iterator<Departments> it = lista.iterator();
    		Departments dep = new Departments();
    		
    		//Recorro la lista
    		while (it.hasNext())
    		{
    			dep = it.next();
    			
    			System.out.println('\n' + dep.getDepartmentName());
    			
    			//Leo los empleados de cada departamento y los meto en 
    			//una lista de empleados.
    			lista_emp = dep.getEmployeeses();
    			
    			Employees emp = new Employees();
        		
    			//Recorro la lista de empleados
        		Iterator<Employees> ite = lista_emp.iterator();
        				
        				while (ite.hasNext()){
        					
        					emp = ite.next();
        					System.out.println(emp.getFirstName() + " " + emp.getLastName());
        				}
    			
    		}
    		//Si el proceso ha ido bien, hago Commit.
    		transaction.commit();
    		}
    	catch (Exception e)
    	{
    		//Si el proceso no ha ido bien, capturo la exepción, envío el error
    		// a un log y hago rollback de la transación.
    		log.error("Error en el método Recuperar Empleados Por Departamentos");
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacción
    	}
    	finally 
    	{
    		//Cierro la sesión
    		SessionManager.cerrar_session(ddao.getSession());
    		//SessionManager.cerrar_session_factory();
    	}
		
		
		return lista_emp;
		
	}
	
	
	
	/**
	 * Método para mostrar los mejor pagados de todos los departamentos
	 * @return Set de los empleados mejor pagados.
	 */
	public Set<Employees> lista_empleados_mejor_pagados(){
		
		//Declaro un set de empleados para guardar la lista de empleados
		Set<Employees> mejor_pagados = new HashSet<Employees>();
		//Objeto de DepartmentsDAO
		DepartmentsDAO ddao = new DepartmentsDAO();
		//Hago un set de la Session en SuperDAO llamando a SessionManager
		ddao.setSession(SessionManager.obtenerSesionNueva());
		//Comienzo la Transacción
		Transaction transaction = ddao.getSession().beginTransaction();
		
		
		
		try 
    	{
			//Declaro una lista de departamentos para guardar los 
			//departamentos recuperados en read_all.
    		List<Departments> dep_list = ddao.read_all();
    		
    		Iterator<Departments> it = dep_list.iterator();
    		Departments dep = new Departments();
    		Employees emp_mejor_salario = new Employees();
    		
    		//Recorro la lista de departamentos
    		while (it.hasNext())
    		{
    			dep = it.next();
    			
    			System.out.println('\n' + dep.getDepartmentName() );
    			
    			//Declaro un set de empleados para guardar los empleados
    			//de cada departamento.
    			@SuppressWarnings("unchecked")
				Set<Employees> lista_emp = dep.getEmployeeses();
    			
    		
    		BigDecimal salario = new BigDecimal(0);	
    		Employees emp = new Employees();
    		
    		Iterator<Employees> ite = lista_emp.iterator();
    		
    				//Recorro la lista de empleados de cada departamento
    				while (ite.hasNext()){
    					
    					emp = ite.next();
    					
    					//Le digo que para el salario de cada empleado que sea
    					//que el anterior, me lo guarde en una variable de empleado.
    					if(emp.getSalary().intValue() > salario.intValue() ){
    						
    						salario = emp.getSalary();
    						emp_mejor_salario = emp;
    						
    					}
    					
    				}	
    			//Agrego el empleado con mejor salario de cada departamento 
    			//en la lista mejor pagados.	
    			mejor_pagados.add(emp_mejor_salario);
    		}
    		transaction.commit();//si todo ha ido bien, persisto los cambio, los hago de verdad, no en la copia de la BD
    	}
    	catch (Exception e)
    	{
    		//Si el proceso no ha ido bien, capturo la exepción, envío el error
    		// a un log y hago rollback de la transación.
    		log.error("Error en el método Empleados Mejor Pagados");
    		e.printStackTrace();
    		transaction.rollback();//si algo ha ido mal, deshago la transacción
    	}
    	finally 
    	{
    		//Cierro la sesión
    		SessionManager.cerrar_session(ddao.getSession());
    		//SessionManager.cerrar_session_factory();
    	}
		
		
		return mejor_pagados;
	}
	
	
}

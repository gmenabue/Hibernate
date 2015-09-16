package services.Employees;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import sessionManager.SessionManager;
import Hibernate.Employees;
import clasesDAO.EmployeesDAO;
public class TestOperaciones {

	@Test
	public void testIncremento_salario() {
		Session session = null;
		
		Employees empleado1DTO = new Employees();
		Employees empleado2DTO = new Employees();
		
		EmployeesDAO empleadoDAO = new EmployeesDAO();
		Operaciones oper = new Operaciones();
		session = SessionManager.obtenerSesionNueva();
		empleadoDAO.setSession(session);
		empleado1DTO = empleadoDAO.read(100);
	
		
		System.out.println("Salario del empleado :" + empleado1DTO.getSalary());
		
		oper.incremento_salario(); //ejecuto la operacion incrementar salario
		

		session.close(); // cierro la sesion
		
		session = SessionManager.obtenerSesionNueva(); // creo nueva sesion
		empleadoDAO.setSession(session);
		empleado2DTO = empleadoDAO.read(100);
	

		System.out.println("Salario del empleado :" + empleado2DTO.getSalary());
		
		assertTrue("Sueldo no incrementado", empleado1DTO.getSalary().intValue()< empleado2DTO.getSalary().intValue());
		
		session.close();
		SessionManager.cerrar_session_factory();
	}
	
	@Test
	public void recuperar_empleados_departamentos_id() {
		
		Session session = null;
		EmployeesDAO empleadoDAO = new EmployeesDAO();
		Operaciones oper = new Operaciones();
		Set<Employees> lista_por_dep = new HashSet<Employees>();
		
		
		session = SessionManager.obtenerSesionNueva();
		empleadoDAO.setSession(session);
		
		
		lista_por_dep = oper.recuperar_empleados_departamentos_id(80);
		
		for (Employees emp : lista_por_dep)
		{
			assertFalse("No se han obtenido los empleados del departamento", emp.getDepartments().getDepartmentId()!=80);	
		}
		session.close();
		SessionManager.cerrar_session_factory();
	}

}

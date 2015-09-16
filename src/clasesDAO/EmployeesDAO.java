/**
 * 
 */
package clasesDAO;

import java.util.List;

import Hibernate.Employees;
import interfaces.InterfaceDAO;

/**
 * @author Giordano Menabue
 *
 */
public class EmployeesDAO extends SuperDAO implements InterfaceDAO<Employees> {
	
	@Override
	public boolean create(Employees g) {
		
		return false;
	}

	@Override
	public Employees read(Object id) {
		Employees emp = new Employees();
		emp = (Employees) this.getSession().createSQLQuery("select * from employees WHERE EMPLOYEE_ID = " + id).addEntity(Employees.class).uniqueResult();
		return emp;
	}

	@Override
	public Employees update(Employees g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Employees> read_all() {
		@SuppressWarnings("unchecked")
		List<Employees> list = getSession().createSQLQuery("select * from employees").addEntity(Employees.class).list();
		return list;
		
	}
	public List<Employees> read_all_dep(short id) {
		String consulta = "select * from employees where department_id = " + id + "ORDER BY SALARY DESC";
		@SuppressWarnings("unchecked")
		List<Employees> list = getSession().createSQLQuery(consulta).addEntity(Employees.class).list();
		return list;
		
	}

}

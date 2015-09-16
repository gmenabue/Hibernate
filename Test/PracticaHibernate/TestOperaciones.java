package PracticaHibernate;

import static org.junit.Assert.*;

import org.junit.Test;

import PracticaHibernate.Operaciones;


public class TestOperaciones {

	@Test
	public void testIncremento_salario() {
	@SuppressWarnings("unused")
	Boolean correcto = false;
	assertTrue("correcto", Operaciones.incremento_salario());
	}

}

package br.ufg.inf.es.sandbox.persistencia;

import org.junit.Assert;
import org.junit.Test;

public class TesteConfigDiretorio {

	@Test
	public void testDefinirDiretorioDePersistencia(){
		Assert.assertNotNull(System.getenv("SAEP_PERSISTENCIA"));
	}

}

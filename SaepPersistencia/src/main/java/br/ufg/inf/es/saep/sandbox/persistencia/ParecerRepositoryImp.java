package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;

public class ParecerRepositoryImp implements ParecerRepository{

	@Override
	public void adicionaNota(String id, Nota nota) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeNota(String id, Avaliavel original) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persisteParecer(Parecer parecer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizaFundamentacao(String parecer, String fundamentacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parecer byId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeParecer(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Radoc radocById(String identificador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String persisteRadoc(Radoc radoc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRadoc(String identificador) {
		// TODO Auto-generated method stub
		
	}

}

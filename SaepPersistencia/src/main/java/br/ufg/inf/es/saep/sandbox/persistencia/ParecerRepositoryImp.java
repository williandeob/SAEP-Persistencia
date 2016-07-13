package br.ufg.inf.es.saep.sandbox.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.util.UtilJsonPersistencia;

public class ParecerRepositoryImp implements ParecerRepository{
	public static String repositoryParecer = "src/main/resources/br/ufg/inf/es/saep/sandbox/persistencia/Parecer/";
	
	@Override
	public void adicionaNota(String id, Nota nota) {
		File file = new File(repositoryParecer.concat(id).concat(".json"));
		if(!file.exists()){
			throw new IdentificadorDesconhecido("id");
		}else{
			try {
				Parecer parecer = (Parecer) UtilJsonPersistencia.recuperarJson(file.getAbsolutePath(), Parecer.class);
				List<Nota> notas = parecer.getNotas();
				Iterator<Nota> inotas = notas.iterator();
				boolean novaNota = true;
				
				while(inotas.hasNext()){
					Nota notaItem = inotas.next();
					if(notaItem.getItemOriginal().equals(nota.getItemOriginal())){
						novaNota = false;	
					}
				}
				
				if(novaNota){
					notas.add(nota);
					UtilJsonPersistencia.persistirJson(parecer, file.getAbsolutePath());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void removeNota(String id, Avaliavel original) {
		File file = new File(repositoryParecer.concat(id).concat(".json"));
		if(file.exists()){
			
			try {
				Parecer parecer = (Parecer) UtilJsonPersistencia.recuperarJson(file.getAbsolutePath(), Parecer.class);
				List<Nota> notasParecer = parecer.getNotas();
				Iterator<Nota> iNotas = notasParecer.iterator();
				
				while(iNotas.hasNext()){
					Nota nota = iNotas.next();
					if(nota.getItemOriginal().equals(original)){
						notasParecer.remove(original);
					}
				}		
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
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
		File file = new File(repositoryParecer.concat(id).concat(".json"));
		Parecer parecer = null;
		if(file.exists()){
			try {
				parecer = (Parecer) UtilJsonPersistencia.recuperarJson(file.getAbsolutePath(), Parecer.class);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		return parecer;
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

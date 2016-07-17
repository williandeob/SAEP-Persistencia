package br.ufg.inf.es.saep.sandbox.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufg.inf.es.saep.sandbox.dominio.CampoExigidoNaoFornecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoUsaTipoException;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.util.UtilJsonPersistencia;

public class ResolucaoRepositoryImp implements ResolucaoRepository {

	@Override
	public Resolucao byId(String id) {
		File fileJsonResolucao = new File(ConfigRepository.getRepositoryResolucao().concat(id).concat(".json"));
		Resolucao resolucao = null;
		if (!fileJsonResolucao.exists()) {
			return null;
		} else {
			try {
				resolucao = (Resolucao) UtilJsonPersistencia.recuperarJson(fileJsonResolucao.getAbsolutePath(),
						Resolucao.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return resolucao;

		}

	}

	@Override
	public String persiste(Resolucao resolucao) {
		if ("".equals(resolucao.getId().trim()) || resolucao.getId() == null) {
			throw new CampoExigidoNaoFornecido("id");
		}

		if (byId(resolucao.getId()) != null) {
			throw new IdentificadorExistente("id");
		}

		try {
			UtilJsonPersistencia.persistirJson(resolucao, ConfigRepository.getRepositoryResolucao().concat(resolucao.getId()).concat(".json"));
			return resolucao.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean remove(String identificador) {
		File fileJsonResolucao = new File(ConfigRepository.getRepositoryResolucao().concat(identificador).concat(".json"));
		return fileJsonResolucao.delete();
	}

	@Override
	public List<String> resolucoes() {
		List<String> listaDeIdentificadore = new ArrayList<String>();
		File diretorioResolucao = new File(ConfigRepository.getRepositoryResolucao());
		String[] namesFiles = diretorioResolucao.list();
		if(namesFiles.length > 0){
			for(String name : namesFiles){
				listaDeIdentificadore.add(name.replace(".json", "").trim());
			}
		}
		return listaDeIdentificadore;
	}

	@Override
	public void persisteTipo(Tipo tipo) {
		File fileTipo = new File(ConfigRepository.getRepositoryTipo().concat(tipo.getId()).concat(".json"));
		
		if(fileTipo.exists()){
			throw new IdentificadorExistente("id");
		}

		try {
			UtilJsonPersistencia.persistirJson(tipo, ConfigRepository.getRepositoryTipo().concat(tipo.getId()).concat(".json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeTipo(String codigo) {
		File fileJsonTipo = new File(ConfigRepository.getRepositoryTipo().concat(codigo).concat(".json"));
		if(fileJsonTipo.exists()){
			
			File fileJsonResolucao = new File(ConfigRepository.getRepositoryResolucao());
			File[] listResolucaoFiles = fileJsonResolucao.listFiles();
			if(listResolucaoFiles.length>0){
				
				for(File file : listResolucaoFiles){
					try {
						Resolucao resolucao = (Resolucao) UtilJsonPersistencia.recuperarJson(file.getAbsolutePath(), Resolucao.class);
						List<Regra> regras = resolucao.getRegras();
						
						if(regras.size()>0){
							Iterator<Regra> iRegra = regras.iterator();
							while(iRegra.hasNext()){
								Regra regra = iRegra.next();
								if(regra.getTipoRelato().equals(codigo)){
									throw new ResolucaoUsaTipoException("Existe referencia do tipo com a resolução");
								}	
							}	
						}
										
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
				
			}
			
			fileJsonTipo.delete();
		}
	}

	@Override
	public Tipo tipoPeloCodigo(String codigo) {
		Tipo tipo = null;
		File fileJsonTipo = new File(ConfigRepository.getRepositoryTipo().concat(codigo).concat(".json"));
		
		if(fileJsonTipo.exists()){
			try {
				tipo = (Tipo) UtilJsonPersistencia.recuperarJson(fileJsonTipo.getAbsolutePath(), Tipo.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return tipo;
	}

	@Override
	public List<Tipo> tiposPeloNome(String nome) {
		File tipoDiretorio = new File(ConfigRepository.getRepositoryTipo());
		File[] listaFilesTipo = tipoDiretorio.listFiles();
		List<Tipo> listTipos = new ArrayList<Tipo>();
		
		if(listaFilesTipo.length > 0){
			for(File file : listaFilesTipo){
				try {
					Tipo tipoRecuperado = (Tipo) UtilJsonPersistencia.recuperarJson(file.getAbsolutePath(), Tipo.class);
					if(tipoRecuperado.getNome().contains(nome)){
						listTipos.add(tipoRecuperado);
					}			
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
		
		return listTipos;
	}

}

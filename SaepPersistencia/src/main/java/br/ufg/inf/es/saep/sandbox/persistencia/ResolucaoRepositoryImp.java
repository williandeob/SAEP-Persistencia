package br.ufg.inf.es.saep.sandbox.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.ufg.inf.es.saep.sandbox.dominio.CampoExigidoNaoFornecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.util.UtilJsonPersistencia;

public class ResolucaoRepositoryImp implements ResolucaoRepository {
	private static String repository = "src/main/resources/br/ufg/inf/es/saep/sandbox/persistencia/Resolucao/";

	@Override
	public Resolucao byId(String id) {
		File fileJsonResolucao = new File(repository.concat(id).concat(".json"));
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
			UtilJsonPersistencia.persistirJson(resolucao, repository.concat(resolucao.getId()).concat(".json"));
			return resolucao.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean remove(String identificador) {
		File fileJsonResolucao = new File(repository.concat(identificador).concat(".json"));
		return fileJsonResolucao.delete();
	}

	@Override
	public List<String> resolucoes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persisteTipo(Tipo tipo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTipo(String codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Tipo tipoPeloCodigo(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tipo> tiposPeloNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

}

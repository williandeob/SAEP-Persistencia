package br.ufg.inf.es.saep.sandbox.persistencia;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.util.UtilJsonPersistencia;

public class Exec {

	public static void main(String[] args) {
		testResolucaoPersiste();
		testResolucaoRecuperar();
		testResolucaoById();
		testResolucaoList();
		testResolucaoExcluir();	
	}

	public static void testResolucaoPersiste() {
		List<Regra> listRegras = new ArrayList<Regra>();

		List<String> dependeDe = new ArrayList<String>();
		Regra regra = new Regra("variavel", 1, "descricao", 10, 1, "expressao", "entao", "senao", "tipoRelato", 5,
				dependeDe);

		listRegras.add(regra);

		Resolucao resolucao = new Resolucao("1", "nome", "descricao", new Date(), listRegras);
		
		ResolucaoRepository resolucaoRepository = new ResolucaoRepositoryImp();
		resolucaoRepository.persiste(resolucao);
		
	}
	
	public static void testResolucaoRecuperar(){
		try {
			Resolucao resolucao = (Resolucao) UtilJsonPersistencia.recuperarJson
					("src/main/resources/br/ufg/inf/es/saep/sandbox/persistencia/Resolucao/1.json", Resolucao.class);
			System.out.println(resolucao.getNome());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testResolucaoById(){
		ResolucaoRepository resolucaoRepository = new ResolucaoRepositoryImp();
		Resolucao resolucao = resolucaoRepository.byId("1");
		if(resolucao == null){
			System.out.println("Object null");
		}else{
			System.out.println(resolucao.getNome());
		}
	}
	
	public static void testResolucaoExcluir(){
		ResolucaoRepository resolucaoRepository = new ResolucaoRepositoryImp();
		if(resolucaoRepository.remove("1")){
			System.out.println("Registro removido");
		}else{
			System.out.println("Falha na remoção do arquivo");
		}
	}
	
	public static void testResolucaoList(){
		ResolucaoRepository resolucaoRepository = new ResolucaoRepositoryImp();
		List<String> lista = resolucaoRepository.resolucoes();
		System.out.println(lista.size());
	}

}

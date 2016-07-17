package br.ufg.inf.es.sandbox.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;
import org.junit.rules.ExpectedException;

import br.ufg.inf.es.saep.sandbox.dominio.CampoExigidoNaoFornecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.ConfigRepository;
import br.ufg.inf.es.saep.sandbox.persistencia.ResolucaoRepositoryImp;

public class TesteResolucaoRepository{
	List<Regra> regras;
	Resolucao resolucao;
	ResolucaoRepositoryImp resolucaoRepository;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp(){
		regras = new ArrayList<Regra>();
		Regra regra = new Regra("hrAulaGraducao", 0, "calculo de horas aula", 24, 0, null, null, null, "1", 1, null);
		regras.add(regra);
		resolucao = new Resolucao("1", "consuni", "avaliação de progressão", new Date(), regras);
		resolucaoRepository = new ResolucaoRepositoryImp();
		resolucaoRepository.persiste(resolucao);
	}
	
	@After
	public void tearDown(){
		File diretorio = new File(ConfigRepository.getRepositoryResolucao());
		File[] files = diretorio.listFiles();
		for(File file : files){
			file.delete();
		}
	}
	
	@Test
	public void testById() {
		Resolucao resolucaoRecuperada = resolucaoRepository.byId(resolucao.getId());
		Assert.assertEquals(resolucao, resolucaoRecuperada);
	}
	
	@Test
	public void testRemove() {
		resolucaoRepository.remove(resolucao.getId());
		File diretorio = new File(ConfigRepository.getRepositoryResolucao());
		Assert.assertEquals(0, diretorio.list().length);
	}
	
	@Test
	public void testResolucoes() {
		List<String> identificadores = resolucaoRepository.resolucoes();
		Assert.assertEquals(resolucao.getId(), identificadores.get(0));
	}
	
	@Test(expected = IdentificadorExistente.class)
	public void testInserirResolucaoJaExistente(){
		resolucaoRepository.persiste(resolucao);
	}
	
	@Test(expected = CampoExigidoNaoFornecido.class)
	public void testInserirResolucaoSemId(){
		Resolucao resolucaoSemId = new Resolucao("", "consuni", "avaliação de progressão", new Date(), regras);
		resolucaoRepository.persiste(resolucaoSemId);
	}
	
}

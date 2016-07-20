package br.ufg.inf.es.sandbox.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.*;
import org.junit.rules.ExpectedException;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.CampoExigidoNaoFornecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.ConfigRepository;
import br.ufg.inf.es.saep.sandbox.persistencia.ResolucaoRepositoryImp;

public class TesteResolucaoRepository{
	List<Regra> regras;
	Set<Atributo> atributos;
	Resolucao resolucao;
	ResolucaoRepositoryImp resolucaoRepository;
	Tipo tipo;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp(){
		resolucaoRepository = new ResolucaoRepositoryImp();
		regras = new ArrayList<Regra>();
		Regra regra = new Regra("hrAulaGraducao", 0, "calculo de horas aula", 24, 0, null, null, null, "1", 1, null);
		regras.add(regra);
		resolucao = new Resolucao("1", "consuni", "avaliação de progressão", new Date(), regras);
		
		resolucaoRepository.persiste(resolucao);
		
		atributos = new HashSet<Atributo>();
		Atributo atributo = new Atributo("nomeAtributo", "descricaoAtributo", 1);
		atributos.add(atributo);
		tipo = new Tipo("1", "nomeTipo", "descricaoTipo", atributos);
		resolucaoRepository.persisteTipo(tipo);
	}
	
	@After
	public void tearDown(){
		File diretorioResolucao = new File(ConfigRepository.getRepositoryResolucao());
		File diretorioTipo = new File(ConfigRepository.getRepositoryTipo());
		File[] files = diretorioResolucao.listFiles();
		for(File file : files){
			file.delete();
		}
		
		File[] filesTipo = diretorioTipo.listFiles();
		for(File file : filesTipo){
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
	
	@Test(expected = IdentificadorExistente.class)
	public void testInserirTipoJaExistente(){
		resolucaoRepository.persisteTipo(tipo);
	}
	
	@Test
	public void testTipoPeloCodigo(){
		Tipo tipoRecuperado = resolucaoRepository.tipoPeloCodigo(tipo.getId());
		Assert.assertEquals(tipo, tipoRecuperado);
	}
	
	@Test
	public void testTipoPeloNome(){
		List<Tipo> listaDeTipos;
		listaDeTipos = resolucaoRepository.tiposPeloNome("nome");
		Assert.assertTrue(listaDeTipos.size()>0);
	}

}

package br.ufg.inf.es.saep.sandbox.persistencia;

import java.io.File;

public class ConfigRepository {
	private static String diretorioRaizPersistencia = System.getenv("SAEP_PERSISTENCIA");
	private static String repositoryResolucao = montarDiretorioPersistenciaJson("Resolucao");
	private static String repositoryTipo = montarDiretorioPersistenciaJson("Tipo");
	private static String repositoryParecer = montarDiretorioPersistenciaJson("Parecer");
			
	private static String montarDiretorioPersistenciaJson(String diretorio){
		return diretorioRaizPersistencia.concat(File.separator).concat(diretorio).concat(File.separator);
	}

	public static String getDiretorioRaizPersistencia() {
		return diretorioRaizPersistencia;
	}

	public static String getRepositoryResolucao() {
		return repositoryResolucao;
	}

	public static String getRepositoryTipo() {
		return repositoryTipo;
	}

	public static String getRepositoryParecer() {
		return repositoryParecer;
	}
	
}

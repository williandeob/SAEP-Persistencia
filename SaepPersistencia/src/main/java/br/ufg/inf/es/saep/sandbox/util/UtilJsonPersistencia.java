package br.ufg.inf.es.saep.sandbox.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.gson.Gson;

public class UtilJsonPersistencia {

	public static void persistirJson(Object objectToPersist, String path) throws FileNotFoundException, IOException {
		Gson parseToJSON = new Gson();
		String jsonResolucao = parseToJSON.toJson(objectToPersist);
		File fileJsonPersiste = new File(path);
		FileOutputStream fops = null;
		try {
			fops = new FileOutputStream(fileJsonPersiste);
			fops.write(jsonResolucao.getBytes(Charset.forName("UTF-8")));
			fops.flush();
		} finally {
			if (fops != null) {
				fops.close();
			}
		}
	}

	public static Object recuperarJson(String path, Class<?> classParse) throws FileNotFoundException, IOException {
		StringBuilder sbJsonObject = new StringBuilder();
		File fileJson = new File(path);
		FileReader fr = new FileReader(fileJson);
		BufferedReader br = new BufferedReader(fr);

		try {
			String line = br.readLine();
			while (line != null) {
				sbJsonObject.append(line);
				line = br.readLine();
			}

			Gson fromJsonObject = new Gson();
			return fromJsonObject.fromJson(sbJsonObject.toString(), classParse);

		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		}
	}

}

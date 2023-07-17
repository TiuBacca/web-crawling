package com.axreng.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Crawler {

	private static int contador = 0;
	private static Boolean termoEncontrado;

	public static void crawl(String url, String termoBusca) throws IOException {
		termoEncontrado = false; // Inicialmente, o termo não foi encontrado

		System.out.println("Search starting with base URL " + url + " and keyword " + termoBusca);
		Set<String> visitedUrls = new HashSet<>();
		visitPage(Util.buscaURLBase(), visitedUrls, Util.buscaKeyWord());
		System.out.println("Search finished with " + contador + " results found: ");
		if (!termoEncontrado) {
			System.out.println("Term not found. Program terminated.");
			System.exit(0);
		}

	}

	private static void visitPage(String url, Set<String> visitedUrls, String termoBusca) throws IOException {
		if (visitedUrls.contains(url) || validaResultadosMaximo()) {
			return;
		}

		visitedUrls.add(url);

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder htmlContent = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					htmlContent.append(line);
				}
				reader.close();

				if (htmlContent.toString().contains(termoBusca)) {
					System.out.println("Result found: " + url);
					contador++;
					termoEncontrado = true; // O termo foi encontrado
				}

				Set<String> foundUrls = Util.extractUrlsFromHtml(htmlContent.toString());
				for (String foundUrl : foundUrls) {
					visitPage(foundUrl, visitedUrls, termoBusca);
					if (!termoEncontrado && foundUrl.equals(foundUrls.toArray()[foundUrls.size() - 1])) {
						termoEncontrado = false; // Último link encontrado e termo não encontrado
					}
				}
			}

			connection.disconnect();
		} catch (Exception e) {
		}

	}

	private static Boolean validaResultadosMaximo() {
		return contador >= Util.buscaMaxResults();
	}

}

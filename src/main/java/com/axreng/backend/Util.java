package com.axreng.backend;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public static Set<String> extractUrlsFromHtml(String html) {
		Set<String> urls = new HashSet<>();
		Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String foundUrl = matcher.group(2);
			urls.add(foundUrl);
		}
		return urls;
	}

	public static String buscaURLBase() {
		return System.getenv("BASE_URL");
	}

	public static String buscaKeyWord() {
		return System.getenv("KEYWORD");
	}

	public static Integer buscaMaxResults() {
		return Integer.valueOf(System.getenv("MAX_RESULTS"));
	}
}

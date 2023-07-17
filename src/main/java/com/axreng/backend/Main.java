package com.axreng.backend;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String url = "http://hiring.axreng.com/";
		String termoBusca = "four";
		try {
			Crawler.crawl(url, termoBusca);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package edu.olivet.se530.dummy;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.olivet.se530.HtmlCrawler;

public class DummyHtmlCrawler implements HtmlCrawler {

	@Override
	public Document getDocument(String isbn, String condition) throws MalformedURLException, IOException {
		isbn = StringUtils.leftPad(isbn, 10, "0");//add "0"	     
		System.out.println("isbn===" + isbn);
		String filename="E:\\2016\\oop\\yy-se530\\Assignment\\" + isbn + "_" + condition.toUpperCase() + "_1.html";
		File input = new File(filename);
		Document doc = Jsoup.parse(input, "UTF-8", "/");
		return doc;		    
	}
}

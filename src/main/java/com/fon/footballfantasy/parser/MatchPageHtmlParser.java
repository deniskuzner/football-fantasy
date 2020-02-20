package com.fon.footballfantasy.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Match;

@Component
public class MatchPageHtmlParser {
	
	private static final String URL = "https://fbref.com/en/matches";
	
	public Match parse(String matchUrl) throws IOException {
		Match match = new Match();
		
		Document document = Jsoup.connect(URL + matchUrl).timeout(10000).get();
		
		Elements clubs = document.getElementsByAttributeValue("itemprop", "performer");
		// Razmisliti da li da se pravi DTO koji ce da ima samo url, pa posle u servisu da se spaja sa celim klubom iz baze
		Club hostDTO = Club.builder().url(clubs.get(0).getElementsByAttributeValue("itemprop", "name").attr("href")).build();
		Club guestDTO = Club.builder().url(clubs.get(1).getElementsByAttributeValue("itemprop", "name").attr("href")).build();
		
		Element table= document.getElementById("events_wrap");
		Elements events = table.getElementsByClass("event");

		for (Element event : events) {
			//napraviti if koji ce da proverava da li je: gol, karton ili izmena pa u zavisnosti od toga kreirati te podklase
		}
		
		return match;
	}

}

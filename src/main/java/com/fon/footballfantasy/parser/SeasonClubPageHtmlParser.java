package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.exception.HtmlParserException;

@Component
public class SeasonClubPageHtmlParser {
	
	private static final String URL = "https://fbref.com/en/comps/54/Serbian-SuperLiga-Stats";
	
	@Autowired
	ClubPageHtmlParser parser;
	
	public List<Club> getSeasonClubs() {
		List<Club> seasonClubs = new ArrayList<>();
		
		try {
			Document document = Jsoup.connect(URL).timeout(10000).get();
			Elements rows = document.getElementById("results32950_overall").select("tbody tr");
			rows.remove(8);

			for (Element row : rows) {
				Club club = parser.parse(row.select("td a").get(0).attr("href").substring(10));
				seasonClubs.add(club);
			}
		} catch (IOException e) {
			throw new HtmlParserException("Page could not be found: " + URL, e);
		}
		
		return seasonClubs;
	}
	
}

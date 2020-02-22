package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;

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
				Club club = Club.builder().url(row.select("td a").get(0).attr("href")).name(row.select("td a").get(0).text()).build();
				Map<String,Object> clubMap = parser.parse(club.getUrl());
				club.setPlayers((List<Player>) clubMap.get("players"));
				club.setImage((String) clubMap.get("image"));
				seasonClubs.add(club);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return seasonClubs;
	}
	
}

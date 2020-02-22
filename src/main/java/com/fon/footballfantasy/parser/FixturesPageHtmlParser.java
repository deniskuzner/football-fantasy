package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;

@Component
public class FixturesPageHtmlParser {
	
	private static final String URL = "https://fbref.com/en/comps/54/schedule/Serbian-SuperLiga-Fixtures";
	
	public List<Gameweek> parse() {
		List<Gameweek> result = new ArrayList<>();
		
		Document document = null;
		try {
			document = Jsoup.connect(URL).timeout(10000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements rows = document.select("table tbody tr");
		
		List<Match> allMatches = new ArrayList<>();
		
		for (Element row : rows) {
			
			if(row.hasClass("spacer"))
				continue;
			
			String matchUrl = row.getElementsByAttributeValue("data-stat", "match_report").get(0).select("a").attr("href");
			int gameweekOrderNumber = Integer.parseInt(row.getElementsByAttributeValue("data-stat", "gameweek").get(0).text());
			LocalDate date = LocalDate.parse(row.getElementsByAttributeValue("data-stat", "date").get(0).text());
			String timeString = row.getElementsByAttributeValue("data-stat", "time").get(0).text();
			LocalTime time = timeString.isEmpty() ? LocalTime.parse("00:00") :LocalTime.parse(row.getElementsByAttributeValue("data-stat", "time").get(0).text());
			Club host = Club.builder().url(row.getElementsByAttributeValue("data-stat", "squad_a").get(0).select("a").attr("href")).build();
			Club guest = Club.builder().url(row.getElementsByAttributeValue("data-stat", "squad_b").get(0).select("a").attr("href")).build();
			String venue = row.getElementsByAttributeValue("data-stat", "venue").get(0).text();
			
			Match match = Match.builder().url(matchUrl).gameweekOrderNumber(gameweekOrderNumber).date(date).time(time).host(host).guest(guest).venue(venue).build();
			
			allMatches.add(match);
		}
		
		List<Integer> gameweekOrderNumbers = allMatches.stream().map(Match::getGameweekOrderNumber).distinct().collect(Collectors.toList());
		
		for (Integer i : gameweekOrderNumbers) {
			List<Match> gameweekMatches = allMatches.stream().filter(m -> m.getGameweekOrderNumber() == i).collect(Collectors.toList());
			Gameweek gameweek = Gameweek.builder().orderNumber(i).matches(gameweekMatches).build();
			
			result.add(gameweek);
		}
		
		return result;
	}

}

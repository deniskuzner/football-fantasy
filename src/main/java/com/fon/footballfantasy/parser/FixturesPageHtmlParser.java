package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.time.LocalDateTime;
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
			Gameweek gameweek = Gameweek.builder().orderNumber(gameweekOrderNumber).build();
			String date = row.getElementsByAttributeValue("data-stat", "date").get(0).text();
			String time = row.getElementsByAttributeValue("data-stat", "time").get(0).text();
			LocalDateTime dateTime = LocalDateTime.parse(date + "T" + (time.isEmpty() ? "00:00" :LocalTime.parse(time)));
			Club host = Club.builder().url(row.getElementsByAttributeValue("data-stat", "squad_a").get(0).select("a").attr("href").substring(10)).build();
			Club guest = Club.builder().url(row.getElementsByAttributeValue("data-stat", "squad_b").get(0).select("a").attr("href").substring(10)).build();
			String venue = row.getElementsByAttributeValue("data-stat", "venue").get(0).text();
			Match match = Match.builder().url(matchUrl.isEmpty() ? null : matchUrl).gameweek(gameweek).dateTime(dateTime).host(host).guest(guest).venue(venue).build();
			allMatches.add(match);
		}
		
		List<Integer> gameweekOrderNumbers = allMatches.stream().map(m -> m.getGameweek().getOrderNumber()).distinct().collect(Collectors.toList());
		
		for (Integer i : gameweekOrderNumbers) {
			Gameweek gameweek = Gameweek.builder().orderNumber(i).build();
			List<Match> gameweekMatches = allMatches.stream()
					.filter(m -> m.getGameweek().getOrderNumber() == i)
					.map(m -> {m.setGameweek(gameweek);
							   return m;})
					.collect(Collectors.toList());
			
			gameweek.setMatches(gameweekMatches);
			result.add(gameweek);
		}
		
		return result;
	}

}

package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.event.Card;
import com.fon.footballfantasy.domain.event.Goal;
import com.fon.footballfantasy.domain.event.MatchEvent;
import com.fon.footballfantasy.domain.event.MinutesPlayed;
import com.fon.footballfantasy.domain.event.Substitution;
import com.fon.footballfantasy.exception.HtmlParserException;

@Component
public class MatchPageHtmlParser {

	private static final String URL = "https://fbref.com/en/matches";
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchPageHtmlParser.class);

	public List<MatchEvent> parse(String matchUrl) {

		List<MatchEvent> result = new ArrayList<>();

		Document document;
		try {
			document = Jsoup.connect(URL + matchUrl).timeout(10000).get();
		} catch (IOException e) {
			throw new HtmlParserException("Page could not be found: " + URL + matchUrl, e);
		}
		
		
		Elements clubs = document.getElementsByAttributeValue("itemprop", "performer");
		Club hostDTO = Club.builder().url(clubs.get(0).getElementsByAttributeValue("itemprop", "name").attr("href").replace("/en/squads", "")).build();
		Club guestDTO = Club.builder().url(clubs.get(1).getElementsByAttributeValue("itemprop", "name").attr("href").replace("/en/squads", "")).build();

		// Adding minutes played for host and guest players
		Elements statsTables = document.select("table.stats_table tbody");
		result.addAll(getMinutesPlayed(statsTables, hostDTO, guestDTO));

		// Adding goal, card and substitution events
		Elements events= document.select("#events_wrap div.event");
		result.addAll(getPlayByPlayEvents(events, hostDTO, guestDTO));
		
		return result;
	}
	
	private List<MinutesPlayed> getMinutesPlayed(Elements statsTables, Club hostDTO, Club guestDTO) {
		List<MinutesPlayed> result = new ArrayList<>();
		Elements hostPlayersRows = statsTables.get(0).select("tr");
		Elements guestPlayersRows = statsTables.get(2).select("tr");
		
		for (Element hostPlayer : hostPlayersRows) {
			Player player = Player.builder().url(hostPlayer.select("th a").attr("href").replace("/en/players", "")).build();
			int minutesPlayed;
			try {
				minutesPlayed = Integer.parseInt(hostPlayer.getElementsByAttributeValue("data-stat", "minutes").text());
			} catch (NumberFormatException e) {
				minutesPlayed = 0;
			}
			result.add(MinutesPlayed.builder().player(player)
					.minutesPlayed(minutesPlayed).minute("").result("").club(hostDTO).build());
		}
		for (Element guestPlayer : guestPlayersRows) {
			Player player = Player.builder().url(guestPlayer.select("th a").attr("href").replace("/en/players", "")).build();
			int minutesPlayed;
			try {
				minutesPlayed = Integer.parseInt(guestPlayer.getElementsByAttributeValue("data-stat", "minutes").text());
			} catch (NumberFormatException e) {
				minutesPlayed = 0;
			}
			result.add(MinutesPlayed.builder().player(player)
					.minutesPlayed(minutesPlayed).minute("").result("").club(guestDTO).build());
		}

		return result;
	}

	private List<MatchEvent> getPlayByPlayEvents(Elements events, Club hostDTO, Club guestDTO) {
		List<MatchEvent> result = new ArrayList<>();
		
		for (Element event : events) {
			MatchEvent matchEvent = null;
			
			String eventString = event.select("div").get(0).text();
			String minute = eventString.trim().split(" ")[0].trim();
			String matchResult = eventString.trim().split(" ")[1];
			Club eventClub = null;
			if(event.hasClass("a")) {
				eventClub = hostDTO;
			} else {
				eventClub = guestDTO;
			}
			
			if(eventString.contains("Goal") || eventString.contains("Penalty Kick")) {
				matchEvent = getGoal(event);
			}
			
			if(eventString.contains("Card") && !eventString.contains("Penalty Kick")) {
				matchEvent = getCard(event, eventString.contains("Yellow") ? "YELLOW" : "RED");
			}
			
			if(eventString.contains("Substitute") && !eventString.contains("Own Goal") && !eventString.contains("Penalty Kick")) {
				matchEvent = getSubstitution(event);
			}
			
			if(matchEvent == null)
				continue;
			
			matchEvent.setMinute(minute);
			matchEvent.setResult(matchResult);
			matchEvent.setClub(eventClub);
			LOGGER.info("Parsed match event: {}", eventString);
			
			result.add(matchEvent);
		}
		
		return result;
	}

	private Goal getGoal(Element event) {
		Player goalPlayer = Player.builder().url(event.select("a").attr("href").replace("/en/players", "")).build();
		boolean ownGoal = event.select("div").get(0).text().contains("Own Goal") ? true : false;
		return Goal.builder().goalPlayer(goalPlayer).ownGoal(ownGoal).build();
	}

	private Card getCard(Element event, String card) {
		Player player = Player.builder().url(event.select("a").attr("href").replace("/en/players", "")).build();
		return Card.builder().card(card).player(player).build();
	}

	private Substitution getSubstitution(Element event) {
		Player inPlayer = Player.builder().url(event.select("a").get(0).attr("href").replace("/en/players", "")).build();
		Player outPlayer = Player.builder().url(event.select("a").get(1).attr("href").replace("/en/players", "")).build();
		return Substitution.builder().inPlayer(inPlayer).outPlayer(outPlayer).build();
	}

}

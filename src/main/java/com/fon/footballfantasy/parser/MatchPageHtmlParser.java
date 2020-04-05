package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.Substitution;

@Component
public class MatchPageHtmlParser {

	private static final String URL = "https://fbref.com/en/matches";

	public List<MatchEvent> parse(String matchUrl) throws IOException {

		List<MatchEvent> result = new ArrayList<>();

		Document document = Jsoup.connect(URL + matchUrl).timeout(10000).get();
		
		// Adding minutes played for host and guest players
		Elements statsTables = document.select("table.stats_table tbody");
		result.addAll(getMinutesPlayed(statsTables));
		
		Elements clubs = document.getElementsByAttributeValue("itemprop", "performer");
		Club hostDTO = Club.builder().url(clubs.get(0).getElementsByAttributeValue("itemprop", "name").attr("href")).build();
		Club guestDTO = Club.builder().url(clubs.get(1).getElementsByAttributeValue("itemprop", "name").attr("href")).build();

		// Adding goal, card and substitution events
		Elements events= document.select("#events_wrap div.event");
		result.addAll(getPlayByPlayEvents(events, hostDTO, guestDTO));
		
		return result;
	}
	
	private List<MinutesPlayed> getMinutesPlayed(Elements statsTables) {
		List<MinutesPlayed> result = new ArrayList<>();
		Elements allPlayersRows = new Elements();
		allPlayersRows.addAll(statsTables.get(0).select("tr"));
		allPlayersRows.addAll(statsTables.get(2).select("tr"));
		
		for (Element hostPlayer : allPlayersRows) {
			Player player = Player.builder().url(hostPlayer.select("th a").attr("href")).build();
			int minutesPlayed;
			try {
				minutesPlayed = Integer.parseInt(hostPlayer.getElementsByAttributeValue("data-stat", "minutes").text());
			} catch (NumberFormatException e) {
				minutesPlayed = 0;
			}
			result.add(MinutesPlayed.builder().player(player)
					.minutesPlayed(minutesPlayed).build());
		}

		return result;
	}

	private List<MatchEvent> getPlayByPlayEvents(Elements events, Club hostDTO, Club guestDTO) {
		List<MatchEvent> result = new ArrayList<>();
		
		for (Element event : events) {
			MatchEvent matchEvent = null;
			
			String eventString = event.select("div").get(0).text();
			int minute = Integer.parseInt(eventString.substring(1,eventString.indexOf("’")));
			Club eventClub = null;
			if(event.hasClass("a")) {
				eventClub = hostDTO;
			} else {
				eventClub = guestDTO;
			}
			
			if(eventString.contains("Goal")) {
				matchEvent = getGoal(event);
			}
			
			if(eventString.contains("Card")) {
				matchEvent = getCard(event, eventString.contains("Yellow") ? "YELLOW" : "RED");
			}
			
			if(eventString.contains("Substitute")) {
				matchEvent = getSubstitution(event);
			}
			
			if(matchEvent == null)
				continue;
			
			matchEvent.setMinute(minute);
			matchEvent.setClub(eventClub);
			
			result.add(matchEvent);
		}
		
		return result;
	}

	private Goal getGoal(Element event) {
		Player goalPlayer = Player.builder().url(event.select("a").attr("href")).build();
		return Goal.builder().goalPlayer(goalPlayer).assistPlayer(null).build();
	}

	private Card getCard(Element event, String card) {
		Player player = Player.builder().url(event.select("a").attr("href")).build();
		return Card.builder().card(card).player(player).build();
	}

	private Substitution getSubstitution(Element event) {
		Player inPlayer = Player.builder().url(event.select("a").get(0).attr("href")).build();
		Player outPlayer = Player.builder().url(event.select("a").get(1).attr("href")).build();
		return Substitution.builder().inPlayer(inPlayer).outPlayer(outPlayer).build();
	}

}

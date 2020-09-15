package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.exception.HtmlParserException;

@Component
public class ClubPageHtmlParser {

	private static final String URL = "https://fbref.com/en/squads";

	public Club parse(String clubUrl) {
		Document document;
		try {
			document = Jsoup.connect(URL + clubUrl).timeout(10000).get();
		} catch (IOException e) {
			throw new HtmlParserException("Page could not be found: " + URL + clubUrl, e);
		}

		String clubImage = document.select("img.teamlogo").attr("src");
		String fullClubName = document.select("div#meta div h1 span").text();
		String clubName = fullClubName.substring(fullClubName.indexOf(" "), fullClubName.lastIndexOf(" Stats")).trim();
		String fullManagerName = document.select("div#meta div p").get(3).text();
		String manager = fullManagerName.substring(fullManagerName.indexOf(": ") + 1).trim();
		Club result = Club.builder().url(clubUrl).name(clubName).manager(manager).image(clubImage).build();

		Elements rows = document.getElementsByTag("table").get(0).select("tbody tr");
		List<Player> players = getPlayers(rows, result);
		result.setPlayers(players);

		return result;
	}

	private List<Player> getPlayers(Elements rows, Club club) {
		List<Player> players = new ArrayList<>();
		for (Element row : rows) {
			String url = row.select("th a").attr("href");
			String name = row.select("th").text();
			String nationality = row.select("td").get(0).text().substring(row.select("td").get(0).text().indexOf(" ") + 1);
			String fullPosition = row.select("td").get(1).text();
			if(fullPosition.isEmpty())
				continue;
			
			String position = row.select("td").get(1).text().substring(0,2);

			String age = row.select("td").get(2).text();
			
			if(url.isEmpty())
				continue;

			Element playerPage;
			try {
				playerPage = Jsoup.connect("https://fbref.com" + url).timeout(10000).get();
			} catch (IOException e) {
				throw new HtmlParserException("Page could not be found: https://fbref.com" + url, e);
			}
			Element playerInfo = playerPage.getElementById("meta");
			
			// Last club check
			String lastClub = playerInfo.select("a").attr("href");
			if(lastClub.isEmpty())
				continue;
			
			String lastClubUrl = lastClub.substring(10);
			if(!lastClubUrl.equals(club.getUrl()))
				continue;
			
			// Player info
			String image = playerInfo.select("img").attr("src");
			String height = playerInfo.getElementsByAttributeValue("itemprop", "height").text();
			String weight = playerInfo.getElementsByAttributeValue("itemprop", "weight").text();
			String birthDate = playerInfo.getElementsByAttributeValue("itemprop", "birthDate").attr("data-birth");

			Player p = Player.builder().url(url.substring(11)).name(name).nationality(nationality).position(position)
					.age(age).image(image).height(height).weight(weight).birthDate(birthDate).club(club).build();

			players.add(p);
		}
		return players;
	}

}

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

@Component
public class ClubPageHtmlParser {

	private static final String URL = "https://fbref.com/en/squads";

	public Club parse(String clubUrl) throws IOException {

		Document document = Jsoup.connect(URL + clubUrl).timeout(10000).get();

		String clubImage = document.select("img.teamlogo").attr("src");
		String fullClubName = document.select("div#meta div h1 span").text();
		String clubName = fullClubName.substring(fullClubName.indexOf(" "), fullClubName.lastIndexOf(" ")).trim();
		Club result = Club.builder().url(clubUrl).name(clubName).image(clubImage).build();

		Element table = document.getElementsByTag("table").get(0);
		Elements rows = table.select("tbody tr");

		List<Player> players = new ArrayList<>();

		for (Element row : rows) {
			String url = row.select("th a").attr("href");
			String name = row.select("th").text();
			String nationality = row.select("td").get(0).text()
					.substring(row.select("td").get(0).text().indexOf(" ") + 1);
			String position = row.select("td").get(1).text();
			String age = row.select("td").get(2).text();

			Element playerPage = Jsoup.connect("https://fbref.com" + url).timeout(10000).get();

			Element playerInfo = playerPage.getElementById("meta");
			
			// Last club check
			String lastClub = playerInfo.select("a").attr("href");
			if(lastClub.isEmpty())
				continue;
			
			String lastClubUrl = lastClub.substring(10);
			if(!lastClubUrl.equals(clubUrl))
				continue;
			
			// Player info
			String image = playerInfo.select("img").attr("src");
			String height = playerInfo.getElementsByAttributeValue("itemprop", "height").text();
			String weight = playerInfo.getElementsByAttributeValue("itemprop", "weight").text();
			String birthDate = playerInfo.getElementsByAttributeValue("itemprop", "birthDate").attr("data-birth");

			Player p = Player.builder().url(url.substring(11)).name(name).nationality(nationality).position(position)
					.age(age).image(image).height(height).weight(weight).birthDate(birthDate).club(result).build();

			players.add(p);
		}
		result.setPlayers(players);

		return result;

	}

}

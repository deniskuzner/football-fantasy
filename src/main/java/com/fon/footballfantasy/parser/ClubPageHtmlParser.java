package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Player;

@Component
public class ClubPageHtmlParser {
	
	private static final String URL = "https://fbref.com";
	
	public Map<String, Object> parse(String clubUrl) throws IOException {
		
		Map<String, Object> result = new HashMap<>();
		
		Document document = Jsoup.connect(URL + clubUrl).timeout(10000).get();
		Element table = document.getElementsByTag("table").get(0);
		Elements rows = table.select("tbody tr");
		
		List<Player> players = new ArrayList<>();
		
		for (Element row : rows) {
			String url = row.select("th a").attr("href");
			String name = row.select("th").text();
			String nationality = row.select("td").get(0).text().substring(row.select("td").get(0).text().indexOf(" ") + 1);
			String position = row.select("td").get(1).text();
			String age = row.select("td").get(2).text();

			Element playerPage = null;
			
			playerPage = Jsoup.connect("https://fbref.com" + url).timeout(10000).get();
			
			Element playerInfo = playerPage.getElementById("info");
			String image = playerInfo.select("img").attr("src");
			String height = playerInfo.getElementsByAttributeValue("itemprop", "height").text();
			String weight = playerInfo.getElementsByAttributeValue("itemprop", "weight").text();
			String birthDate = playerInfo.getElementsByAttributeValue("itemprop", "birthDate").attr("data-birth");
			
			Player p = Player.builder().url(url).name(name).nationality(nationality).position(position)
					.age(age).image(image).height(height).weight(weight).birthDate(birthDate).build();
			
			players.add(p);
		}
		
		result.put("players", players);
		result.put("image", document.select("img.teamlogo").attr("src"));
		
		return result;
		
	}

}

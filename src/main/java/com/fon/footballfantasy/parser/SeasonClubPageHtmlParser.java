package com.fon.footballfantasy.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.exception.HtmlParserException;
import com.fon.footballfantasy.parser.thread.ClubParserThread;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SeasonClubPageHtmlParser {

	private static final String URL = "https://fbref.com/en/comps/54/Serbian-SuperLiga-Stats";
	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

	@Autowired
	ClubPageHtmlParser parser;

	public List<Club> getSeasonClubs() {
		List<Club> seasonClubs = new ArrayList<>();
		List<Future<Club>> clubsFuture = Collections.synchronizedList(new ArrayList<>());

		try {
			Document document = Jsoup.connect(URL).timeout(10000).get();
			Elements rows = document.getElementById("results107571_overall").select("tbody tr");
			for (Element row : rows) {
				fetchClub(clubsFuture, row);
			}

			for (Future<Club> future : clubsFuture) {
				Club club = future.get();
				log.info("Parsed club: {}", club);
				seasonClubs.add(club);
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			throw new HtmlParserException("Page could not be found: " + URL, e);
		}

		return seasonClubs;
	}

	private void fetchClub(List<Future<Club>> clubsFuture, Element row) {
		String clubUrl = row.select("td a").get(0).attr("href").substring(10);
		Future<Club> clubFuture = executor.submit(new ClubParserThread(clubUrl));
		clubsFuture.add(clubFuture);
	}

}

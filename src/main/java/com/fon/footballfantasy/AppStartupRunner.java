package com.fon.footballfantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.parser.ClubPageHtmlParser;
import com.fon.footballfantasy.parser.FixturesPageHtmlParser;
import com.fon.footballfantasy.parser.MatchPageHtmlParser;
import com.fon.footballfantasy.parser.SeasonClubPageHtmlParser;

@Component
public class AppStartupRunner implements ApplicationRunner {
	
	@Autowired
	ClubPageHtmlParser clubParser;
	
	@Autowired
	SeasonClubPageHtmlParser seasonClubParser;
	
	@Autowired
	MatchPageHtmlParser matchParser;
	
	@Autowired
	FixturesPageHtmlParser fixturesParser;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//seasonClubParser.getSeasonClubs();
		//matchParser.parse("/7d66ca4a/Napredak-Krusevac-Partizan-August-1-2020-Serbian-SuperLiga");
		//fixturesParser.parse();
		//clubParser.parse("/72804f2b/Radnicki-Nis-Stats");
	}

}

package com.fon.footballfantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.parser.MatchPageHtmlParser;
import com.fon.footballfantasy.parser.SeasonClubPageHtmlParser;

@Component
public class AppStartupRunner implements ApplicationRunner {
	
	@Autowired
	SeasonClubPageHtmlParser playerParser;
	
	@Autowired
	MatchPageHtmlParser matchParser;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//playerParser.getSeasonClubs();
		matchParser.parse("/b6d88aea/Vozdovac-TSC-Backa-Top-July-19-2019-Serbian-SuperLiga");
	}

}

package com.fon.footballfantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.parser.SeasonClubPageHtmlParser;

@Component
public class AppStartupRunner implements ApplicationRunner {
	
	@Autowired
	SeasonClubPageHtmlParser parser;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		parser.getSeasonClubs();
	}

}

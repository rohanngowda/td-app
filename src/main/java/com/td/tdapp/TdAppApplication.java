package com.td.tdapp;

import static java.lang.System.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.util.StringUtils;

import com.beust.jcommander.JCommander;

@SpringBootApplication
public class TdAppApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(TdAppApplication.class);
	private static String commandKey = "td";
	
	@Autowired
	private TDUtil tdUtil;
	
	public static void main(String[] args) {
		SpringApplication.run(TdAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PropertySource<?> ps = new SimpleCommandLinePropertySource(args);
		String inputArgs = (String)ps.getProperty(commandKey);
		if(StringUtils.isEmpty(inputArgs)) {
		log.info("Invalid input args");
		exit(0);
		}

		log.info("Input Args = {}", inputArgs); 
		Args input = new Args();
		TableInfo tableInfo = new TableInfo();
		JCommander.newBuilder()
		  .addObject(input)
		  .build()
		  .parse(inputArgs.split(" "));
		
		tableInfo = tdUtil.fetch(input);
		tableInfo.printTable();
        exit(0);
	}
}

package br.com.collaborativevotingsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import io.micrometer.common.util.StringUtils;

public class UtilsSystem {
	
	private static final String LANGUAGE_PT_BR = "br";
	private static final String LANGUAGE_EN = "en";

	public static Locale getLocaleByLanguage(String language) {
		Locale locale = null;
		if(!StringUtils.isBlank(language) && (language.equalsIgnoreCase(LANGUAGE_PT_BR) || language.equalsIgnoreCase(LANGUAGE_EN))) {
			locale = new Locale(language);				
		}else {
			locale = new Locale("br");
		}
		return locale;
	}
	
	public static String formatDateTimeRange(LocalDateTime start, LocalDateTime end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formattedStart = start.format(formatter);
		String formattedEnd = end.format(formatter);

		return formattedStart + " até " + formattedEnd;
	}
}

package br.com.collaborativevotingsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import io.micrometer.common.util.StringUtils;

public class UtilsSystem {

	public static Locale getLocaleByLanguage(String language) {
		Locale locale = null;
		if (StringUtils.isEmpty(language)) {
			locale = new Locale("br");
		} else {
			locale = new Locale(language);
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

package br.com.collaborativevotingsystem.utils;

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
}

package br.com.collaborativevotingsystem.validation;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.SheduleDescriptionIsBlanckOrNullException;
import br.com.collaborativevotingsystem.exception.SheduleTitleIsBlanckOrNullException;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.utils.UtilsSystem;
import io.micrometer.common.util.StringUtils;

public class ValidationShedule implements ValidationInterface {

	private Shedule shedule;

	private Locale locale;

	private MessageSource messageSource;

	public ValidationShedule(Shedule shedule, String language, MessageSource messageSource) {
		this.shedule = shedule;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
	}

	@Override
	public void execute() throws Exception {
		if (StringUtils.isBlank(shedule.getTitle())) {
			String message = messageSource.getMessage(SheduleTitleIsBlanckOrNullException.MESSAGE, null, locale);
			throw new SheduleTitleIsBlanckOrNullException(message);
		}

		if (StringUtils.isBlank(shedule.getDescription())) {
			String message = messageSource.getMessage(SheduleDescriptionIsBlanckOrNullException.MESSAGE, null, locale);
			throw new SheduleDescriptionIsBlanckOrNullException(message);
		}
	}
}

package br.com.collaborativevotingsystem.validation;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.ScheduleDescriptionIsBlanckOrNullException;
import br.com.collaborativevotingsystem.exception.ScheduleTitleIsBlanckOrNullException;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.utils.UtilsSystem;
import io.micrometer.common.util.StringUtils;

public class ValidationSchedule implements ValidationInterface {

	private Schedule schedule;

	private Locale locale;

	private MessageSource messageSource;

	public ValidationSchedule(Schedule schedule, String language, MessageSource messageSource) {
		this.schedule = schedule;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
	}

	@Override
	public void execute() throws Exception {
		if (StringUtils.isBlank(schedule.getTitle())) {
			String message = messageSource.getMessage(ScheduleTitleIsBlanckOrNullException.MESSAGE, null, locale);
			throw new ScheduleTitleIsBlanckOrNullException(message);
		}

		if (StringUtils.isBlank(schedule.getDescription())) {
			String message = messageSource.getMessage(ScheduleDescriptionIsBlanckOrNullException.MESSAGE, null, locale);
			throw new ScheduleDescriptionIsBlanckOrNullException(message);
		}
	}
}

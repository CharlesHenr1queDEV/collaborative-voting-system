package br.com.collaborativevotingsystem.validation;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.VotingSessionNotExistException;
import br.com.collaborativevotingsystem.exception.VotingInProgressException;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.utils.UtilsSystem;

public class VotingResultValidation implements ValidationInterface{

	private Schedule schedule;

	private Locale locale;

	private MessageSource messageSource;

	public VotingResultValidation(Schedule schedule, String language, MessageSource messageSource) {
		this.schedule = schedule;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
	}

	@Override
	public void execute() throws Exception {	
		
		if(schedule.getVotingSession() == null) {
			String message = messageSource.getMessage(VotingSessionNotExistException.MESSAGE, new Object[] {schedule.getTitle()}, locale);
			throw new VotingSessionNotExistException(message);
		}
		
		if(LocalDateTime.now().isBefore(schedule.getVotingSession().getVotingEndDate())) {
			String message = messageSource.getMessage(VotingInProgressException.MESSAGE, null, locale);
			throw new VotingInProgressException(message);
		}
		
	}

}

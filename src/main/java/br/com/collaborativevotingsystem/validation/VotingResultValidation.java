package br.com.collaborativevotingsystem.validation;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.VotingSessionNotExistException;
import br.com.collaborativevotingsystem.exception.VotingInProgressException;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.utils.UtilsSystem;

public class VotingResultValidation implements ValidationInterface{

	private Shedule shedule;

	private Locale locale;

	private MessageSource messageSource;

	public VotingResultValidation(Shedule shedule, String language, MessageSource messageSource) {
		this.shedule = shedule;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
	}

	@Override
	public void execute() throws Exception {	
		
		if(shedule.getVotingSession() == null) {
			String message = messageSource.getMessage(VotingSessionNotExistException.MESSAGE, new Object[] {shedule.getTitle()}, locale);
			throw new VotingSessionNotExistException(message);
		}
		
		if(LocalDateTime.now().isBefore(shedule.getVotingSession().getVotingEndDate())) {
			String message = messageSource.getMessage(VotingInProgressException.MESSAGE, null, locale);
			throw new VotingInProgressException(message);
		}
		
	}

}

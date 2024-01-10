package br.com.collaborativevotingsystem.validation;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.VotingSessionAlreadyExistsInScheduleException;
import br.com.collaborativevotingsystem.exception.VotingSessionDurationMinutesInvalidException;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.utils.UtilsSystem;

public class VotingSessionValidation implements ValidationInterface{

	private VotingSession votingSession;
	
	private Shedule shedule;

	private Locale locale;

	private MessageSource messageSource;

	public VotingSessionValidation(VotingSession votingSession, Shedule shedule, String language, MessageSource messageSource) {
		this.votingSession = votingSession;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
		this.shedule = shedule;
	}

	@Override
	public void execute() throws Exception {
		if(shedule.getVotingSession() != null) {
			String message = messageSource.getMessage(VotingSessionAlreadyExistsInScheduleException.MESSAGE, new Object[] {shedule.getTitle()}, locale);
			throw new VotingSessionAlreadyExistsInScheduleException(message);
		}
		
		if(votingSession.getVotingDurationMinutes() <= 0) {
			String message = messageSource.getMessage(VotingSessionDurationMinutesInvalidException.MESSAGE, null, locale);
			throw new VotingSessionDurationMinutesInvalidException(message);
		}
	}

}

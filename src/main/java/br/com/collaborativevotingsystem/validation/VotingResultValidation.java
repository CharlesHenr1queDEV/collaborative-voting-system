package br.com.collaborativevotingsystem.validation;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.SectionVotingNotExistException;
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
		
		if(shedule.getSectionVoting() == null) {
			String message = messageSource.getMessage(SectionVotingNotExistException.MESSAGE, new Object[] {shedule.getTitle()}, locale);
			throw new SectionVotingNotExistException(message);
		}
		
		if(LocalDateTime.now().isBefore(shedule.getSectionVoting().getVotingEndDate())) {
			String message = messageSource.getMessage(VotingInProgressException.MESSAGE, null, locale);
			throw new VotingInProgressException(message);
		}
		
	}

}

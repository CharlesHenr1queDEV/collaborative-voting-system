package br.com.collaborativevotingsystem.validation;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.collaborativevotingsystem.exception.VoteAlreadyComputedAssociateException;
import br.com.collaborativevotingsystem.exception.VotingSessionNotExistException;
import br.com.collaborativevotingsystem.exception.VotingSessionTimeHasExpiredException;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.model.Vote;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.service.VotingSessionService;
import br.com.collaborativevotingsystem.utils.UtilsSystem;

public class VoteValidation implements ValidationInterface {

	private Vote vote;
	
	private Schedule schedule;

	private Locale locale;

	private MessageSource messageSource;

	private VotingSessionService votingSessionService;

	public VoteValidation(Vote vote, Schedule schedule, String language, MessageSource messageSource,
			VotingSessionService votingSessionService) {
		this.vote = vote;
		this.locale = UtilsSystem.getLocaleByLanguage(language);
		this.messageSource = messageSource;
		this.votingSessionService = votingSessionService;
		this.schedule = schedule;
	}

	@Override
	public void execute() throws Exception {

		if (vote.getVotingSession() == null) {
			String message = messageSource.getMessage(VotingSessionNotExistException.MESSAGE, new Object[] {schedule.getTitle()}, locale);
			throw new VotingSessionNotExistException(message);
		}

		boolean isAssociateVoted = votingSessionService.isAssociateVoted(vote.getAssociateIdentifier(),
				vote.getVotingSession());

		if (isAssociateVoted) {
			String message = messageSource.getMessage(VoteAlreadyComputedAssociateException.MESSAGE,
					new Object[] { schedule.getTitle(), vote.getAssociateIdentifier() }, locale);
			throw new VoteAlreadyComputedAssociateException(message);
		}

		if (LocalDateTime.now().isAfter(vote.getVotingSession().getVotingEndDate())) {
			VotingSession votingSession = vote.getVotingSession();
			
			String rangeVotingTime = UtilsSystem.formatDateTimeRange(votingSession.getVotingStartDate(), votingSession.getVotingEndDate());
			String message = messageSource.getMessage(VotingSessionTimeHasExpiredException.MESSAGE, new Object[] {rangeVotingTime}, locale);
			throw new VotingSessionTimeHasExpiredException(message);
		}
	}
}

package br.com.collaborativevotingsystem.service;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.builder.VotingSessionBuilder;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.repository.VotingSessionRepository;
import br.com.collaborativevotingsystem.validation.VotingSessionValidation;

@Service
public class VotingSessionService {
	
	private ScheduleService scheduleService;
	
	private VotingSessionRepository votingSessionRepository;
	
	private MessageSource messageSource;
	
	public VotingSessionService(ScheduleService scheduleService, VotingSessionRepository votingSessionRepository, MessageSource messageSource) {
		this.scheduleService = scheduleService;
		this.votingSessionRepository = votingSessionRepository;
		this.messageSource = messageSource;
	}

	public VotingSession open(Long id, int votingDurationMinutes, String language) throws Exception {
		try {
			Schedule schedule = this.scheduleService.findById(id);
			VotingSession votingSession = createVotingSession(schedule, votingDurationMinutes);
			
			VotingSessionValidation votingSessionValidation = new VotingSessionValidation(votingSession, schedule, language, messageSource);
			votingSessionValidation.execute();
			
			schedule.setVotingSession(votingSession);
			return votingSessionRepository.save(votingSession);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VotingSession createVotingSession(Schedule schedule, int votingDurationMinutes) {
		VotingSession votingSession = new VotingSessionBuilder()
				.withVotingStartDate(LocalDateTime.now())
				.withVotingDurationMinutes(votingDurationMinutes)
				.withSchedule(schedule)
				.build();
	
		return votingSession;
	}
	
	public boolean isAssociateVoted(String associateIdentifier, VotingSession votingSession) {
		return votingSessionRepository.existsVotesByVotesAssociateIdentifierAndId(associateIdentifier, votingSession.getId());
	}

}

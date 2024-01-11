package br.com.collaborativevotingsystem.service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	
	public VotingSessionService(ScheduleService scheduleService, VotingSessionRepository votingSessionRepository, MessageSource messageSource) {
		this.scheduleService = scheduleService;
		this.votingSessionRepository = votingSessionRepository;
		this.messageSource = messageSource;
	}

	public VotingSession open(Long id, int votingDurationMinutes, String language) throws Exception {
		try {
			Schedule schedule = this.scheduleService.findById(id);
			VotingSession votingSession = prepareVotingSession(schedule, votingDurationMinutes);
			
			VotingSessionValidation votingSessionValidation = new VotingSessionValidation(votingSession, schedule, language, messageSource);
			votingSessionValidation.execute();
			schedule.setVotingSession(votingSession);
			
			votingSessionRepository.save(votingSession);
			
			scheduler.schedule(this::sendVotingResult, votingSession.getVotingDurationMinutes(), TimeUnit.MINUTES);
			
			return votingSession;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VotingSession prepareVotingSession(Schedule schedule, int votingDurationMinutes) {
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
	
	private void sendVotingResult() {
		System.out.println("Resultado da votação depois de 1 min");
	}

}

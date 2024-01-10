package br.com.collaborativevotingsystem.service;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.builder.VotingSessionBuilder;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.repository.VotingSessionRepository;
import br.com.collaborativevotingsystem.validation.VotingSessionValidation;

@Service
public class VotingSessionService {
	
	private SheduleService sheduleService;
	
	private VotingSessionRepository votingSessionRepository;
	
	private MessageSource messageSource;
	
	public VotingSessionService(SheduleService sheduleService, VotingSessionRepository votingSessionRepository, MessageSource messageSource) {
		this.sheduleService = sheduleService;
		this.votingSessionRepository = votingSessionRepository;
		this.messageSource = messageSource;
	}

	public VotingSession open(Long id, int votingDurationMinutes) throws Exception {
		try {
			Shedule shedule = this.sheduleService.findById(id);
			VotingSession votingSession = createVotingSession(shedule, votingDurationMinutes);
			
			VotingSessionValidation votingSessionValidation = new VotingSessionValidation(votingSession, shedule, null, messageSource);
			votingSessionValidation.execute();
			
			shedule.setVotingSession(votingSession);
			return votingSessionRepository.save(votingSession);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VotingSession createVotingSession(Shedule shedule, int votingDurationMinutes) {
		VotingSession votingSession = new VotingSessionBuilder()
				.withVotingStartDate(LocalDateTime.now())
				.withVotingDurationMinutes(votingDurationMinutes)
				.withShedule(shedule)
				.build();
	
		return votingSession;
	}
	
	public boolean isAssociateVoted(String associateIdentifier, VotingSession votingSession) {
		return votingSessionRepository.existsVotesByVotesAssociateIdentifierAndId(associateIdentifier, votingSession.getId());
	}

}

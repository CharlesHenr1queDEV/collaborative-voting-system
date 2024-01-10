package br.com.collaborativevotingsystem.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.repository.VoteRepository;
import br.com.collaborativevotingsystem.validation.VoteValidation;

@Service
public class VoteService {

	private SheduleService sheduleService;

	private VoteRepository voteRepository;
	
	private MessageSource messageSource;
	
	private VotingSessionService votingSessionService; 

	public VoteService(SheduleService sheduleService, VoteRepository voteRepository, MessageSource messageSource, VotingSessionService votingSessionService) {
		this.sheduleService = sheduleService;
		this.voteRepository = voteRepository;
		this.messageSource = messageSource;
		this.votingSessionService = votingSessionService;
	}

	public void vote(VoteDTO voteDTO, Long sheduleId) throws Exception {
		try {
			Shedule shedule = this.sheduleService.findById(sheduleId);
			voteDTO.setVotingSession(shedule.getVotingSession());
			
			VoteValidation validation = new VoteValidation(voteDTO.generateVote(), shedule, null, messageSource, votingSessionService);
			validation.execute();
			
			voteRepository.save(voteDTO.generateVote());
		} catch (Exception e) {
			throw e;
		}
	}

}

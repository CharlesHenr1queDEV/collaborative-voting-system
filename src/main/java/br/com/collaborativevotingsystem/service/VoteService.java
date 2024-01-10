package br.com.collaborativevotingsystem.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.repository.VoteRepository;
import br.com.collaborativevotingsystem.validation.VoteValidation;

@Service
public class VoteService {

	private ScheduleService scheduleService;

	private VoteRepository voteRepository;
	
	private MessageSource messageSource;
	
	private VotingSessionService votingSessionService; 

	public VoteService(ScheduleService scheduleService, VoteRepository voteRepository, MessageSource messageSource, VotingSessionService votingSessionService) {
		this.scheduleService = scheduleService;
		this.voteRepository = voteRepository;
		this.messageSource = messageSource;
		this.votingSessionService = votingSessionService;
	}

	public void vote(VoteDTO voteDTO, Long scheduleId, String language) throws Exception {
		try {
			Schedule schedule = this.scheduleService.findById(scheduleId);
			voteDTO.setVotingSession(schedule.getVotingSession());
			
			VoteValidation validation = new VoteValidation(voteDTO.generateVote(), schedule, language, messageSource, votingSessionService);
			validation.execute();
			
			voteRepository.save(voteDTO.generateVote());
		} catch (Exception e) {
			throw e;
		}
	}

}

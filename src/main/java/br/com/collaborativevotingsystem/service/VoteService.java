package br.com.collaborativevotingsystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.model.Vote;
import br.com.collaborativevotingsystem.repository.VoteRepository;
import br.com.collaborativevotingsystem.validation.VoteValidation;

@Service
public class VoteService {
	
	private final Logger logger = LogManager.getLogger(VoteService.class);

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

	public VoteDTO vote(VoteDTO voteDTO, String language) throws Exception {
		try {
			logger.info("[VOTE] Iniciando processo de votação");
			Schedule schedule = this.scheduleService.findById(voteDTO.getScheduleId());
			voteDTO.setVotingSession(schedule.getVotingSession());
			
			logger.info("[VOTE] Validando voto");
			VoteValidation validation = new VoteValidation(voteDTO.generateVote(), schedule, language, messageSource, votingSessionService);
			validation.execute();
			
			Vote save = voteRepository.save(voteDTO.generateVote());
			logger.info("[VOTE] Voto salvo com sucesso!");
			return save.generateTransportObject();
		} catch (Exception e) {
			logger.error("[VOTE]" + e.getMessage());
			throw e;
		}
	}
}

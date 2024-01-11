package br.com.collaborativevotingsystem.service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.builder.VotingSessionBuilder;
import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.enums.RabbitQueueEnum;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.repository.VotingSessionRepository;
import br.com.collaborativevotingsystem.validation.VotingSessionValidation;

@Service
public class VotingSessionService {
	
	private final Logger logger = LogManager.getLogger(VotingSessionService.class);
	
	private ScheduleService scheduleService;
	
	private VotingSessionRepository votingSessionRepository;
	
	private MessageSource messageSource;
	
	private RabbitMessageSenderService rabbitMessageSenderService;
	
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public VotingSessionService(ScheduleService scheduleService, VotingSessionRepository votingSessionRepository, MessageSource messageSource,
			RabbitMessageSenderService rabbitMessageSenderService) {
		this.scheduleService = scheduleService;
		this.votingSessionRepository = votingSessionRepository;
		this.messageSource = messageSource;
		this.rabbitMessageSenderService = rabbitMessageSenderService;
	}

	public VotingSession open(Long id, int votingDurationMinutes, String language) throws Exception {
		try {
			logger.info("[VOTING_SESSION] Iniciando abertura da sessão");
			Schedule schedule = this.scheduleService.findById(id);
			VotingSession votingSession = prepareVotingSession(schedule, votingDurationMinutes);
			
			logger.info("[VOTING_SESSION] Validando dados para abertura de sessão");
			VotingSessionValidation votingSessionValidation = new VotingSessionValidation(votingSession, schedule, language, messageSource);
			votingSessionValidation.execute();
			schedule.setVotingSession(votingSession);
			
			votingSessionRepository.save(votingSession);
			logger.info("[VOTING_SESSION] Sessão de votação iniciada:");
			
			scheduleService.updateSheduleResultVoting(schedule, ResultVotingEnum.PENDING);
			
			logger.info("[VOTING_SESSION] Criando processamento para enviar o resultado da votação após o intervalo de: " + votingSession.getVotingDurationMinutes() + " Minutos");
			scheduler.schedule(() -> sendVotingResult(schedule, language), votingSession.getVotingDurationMinutes(), TimeUnit.MINUTES);
			
			return votingSession;
		} catch (Exception e) {
			logger.error("[VOTING_SESSION] " + e.getMessage());
			throw e;
		}
	}
	
	public boolean isAssociateVoted(String associateIdentifier, VotingSession votingSession) {
		return votingSessionRepository.existsVotesByVotesAssociateIdentifierAndId(associateIdentifier, votingSession.getId());
	}
	
	private void sendVotingResult(Schedule schedule, String language){
		try {
			logger.info("[VOTING_SESSION] Iniciando processo para processar resultado da votação");
			VotingResultDTO result = scheduleService.getResult(schedule.getId(), language);
			
			rabbitMessageSenderService.sendMessage(RabbitQueueEnum.RESULT, result);
			
			scheduleService.updateSheduleResultVoting(schedule, result.getFinalVoteResult());
			logger.info("[VOTING_SESSION] Processamento de envio e armazenamento do resultado da votação concluido");
		} catch (Exception e) {
			logger.error("[VOTING_SESSION] " + e);
		}
	}
	
	private VotingSession prepareVotingSession(Schedule schedule, int votingDurationMinutes) {
		VotingSession votingSession = new VotingSessionBuilder()
				.withVotingStartDate(LocalDateTime.now())
				.withVotingDurationMinutes(votingDurationMinutes)
				.withSchedule(schedule)
				.build();
	
		return votingSession;
	}
}

package br.com.collaborativevotingsystem.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.repository.ScheduleRepository;
import br.com.collaborativevotingsystem.validation.ValidationSchedule;
import br.com.collaborativevotingsystem.validation.VotingResultValidation;

@Service
public class ScheduleService {
	
	private final Logger logger = LogManager.getLogger(ScheduleService.class);

	private ScheduleRepository scheduleRepository;
	
	private MessageSource messageSource;

	public ScheduleService(ScheduleRepository scheduleRepository, MessageSource messageSource) {
		this.scheduleRepository = scheduleRepository;
		this.messageSource = messageSource;
	}
	
	public Schedule findById(Long id) throws Exception {
		logger.info("[SCHEDULE] Buscando schedule por id: " + id);
		
		Optional<Schedule> scheduleOpt = scheduleRepository.findById(id);
		return scheduleOpt.orElseThrow(() -> new Exception("Schedule não encontrado com o id: " + id));
	}
	
	public List<ScheduleDTO> findAll() throws Exception {
		logger.info("[SCHEDULE] Listando todos as pautas");
		
		List<Schedule> listSchedule = scheduleRepository.findAll();
		if(listSchedule != null) {			
			return listSchedule.stream().map(schedule -> schedule.generateTransportObject()).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO, String language) throws Exception {
		try {
			logger.info("[SCHEDULE] Iniciando criação de pauta");
			Schedule schedule = scheduleDTO.generateSchedule();

			logger.info("[SCHEDULE] Validando os dados");
			ValidationSchedule validationSchedule = new ValidationSchedule(schedule, language, messageSource);
			validationSchedule.execute();
			
			scheduleRepository.save(schedule);
			logger.info("[SCHEDULE] Criação realizada com sucesso!");
			return schedule.generateTransportObject();			
		} catch (Exception e) {
			logger.error("[SCHEDULE] " + e.getMessage());
			throw e;
		}
		
	}
	
	public void deleteScheduleById(Long id) throws Exception {
		logger.info("[SCHEDULE] Deletando registro ");
		
		Schedule schedule = findById(id);
		scheduleRepository.deleteById(schedule.getId());
	}
	
	public void updateSheduleResultVoting(Schedule schedule, ResultVotingEnum result) {
		logger.info("[SCHEDULE] Atualizando status da votação da pauta: " + schedule.getId());
		
		schedule.setResultVotingEnum(result);
		scheduleRepository.save(schedule);
	}

	public VotingResultDTO getResult(Long scheduleId, String language) throws Exception {
		try {
			logger.info("[SCHEDULE] Calculando resultado de votação de uma pauta");
			Schedule schedule = findById(scheduleId);
			
			VotingResultValidation votingResultValidation = new VotingResultValidation(schedule, language, messageSource);
			votingResultValidation.execute();
			
			VotingResultDTO voteResult = scheduleRepository.getVoteResult(schedule);
			voteResult.calculateFinalVoteResult();
			logger.info("[SCHEDULE] Processo finalizado");
			
			return voteResult;
		} catch (Exception e) {
			logger.error("[SCHEDULE]" + e.getMessage());
			throw e;
		}
	}
}

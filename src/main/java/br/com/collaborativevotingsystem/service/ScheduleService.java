package br.com.collaborativevotingsystem.service;

import java.util.Optional;

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

	private ScheduleRepository scheduleRepository;
	
	private MessageSource messageSource;

	public ScheduleService(ScheduleRepository scheduleRepository, MessageSource messageSource) {
		this.scheduleRepository = scheduleRepository;
		this.messageSource = messageSource;
	}

	public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO, String language) throws Exception {
		Schedule schedule = scheduleDTO.generateSchedule();
		
		ValidationSchedule validationSchedule = new ValidationSchedule(schedule, language, messageSource);
		validationSchedule.execute();

		scheduleRepository.save(schedule);

		return schedule.generateTransportObject();			
	}

	public Schedule findById(Long id) throws Exception {
		Optional<Schedule> scheduleOpt = scheduleRepository.findById(id);
		return scheduleOpt.orElseThrow(() -> new Exception("Schedule não encontrado com o id: " + id));
	}
	
	public void updateSheduleResultVoting(Schedule schedule, ResultVotingEnum result) {
		schedule.setResultVotingEnum(result);
		scheduleRepository.save(schedule);
	}

	public VotingResultDTO getResult(Long scheduleId, String language) throws Exception {
		Schedule schedule = findById(scheduleId);
		
		
		VotingResultValidation votingResultValidation = new VotingResultValidation(schedule, language, messageSource);
		votingResultValidation.execute();
		
		VotingResultDTO voteResult = scheduleRepository.getVoteResult(schedule);
		voteResult.calculateFinalVoteResult();
//		List<Vote> votes = schedule.getVotingSession().getVotes();
		
		return voteResult;
	}
//	
//	private VotingResultDTO prepareVotingResult(List<Vote> votes) {
//		
//		int voteTotal = votes.size();
//		int numberOfVotesNo = (int) votes.stream().filter(vote -> vote.getVoteChoice().equals(VoteChoiceEnum.NO)).count();
//		int numberOfVotesYes = (int) votes.stream().filter(vote -> vote.getVoteChoice().equals(VoteChoiceEnum.YES)).count();
//
//		VotingResultDTO votingResult = new VotingResultDTO();
//		votingResult.setTotalVotes(voteTotal);
//		votingResult.setNumberOfVotesNo(numberOfVotesNo);
//		votingResult.setNumberOfVotesYes(numberOfVotesYes);
//		votingResult.setFinalVoteResult(getFinalVotingResult(numberOfVotesYes, numberOfVotesNo));
//		
//		return votingResult;
//	}
//	
//	private ResultVotingEnum getFinalVotingResult(int numberOfVotesYes, int numberOfVotesNo) {
//	    if (numberOfVotesYes > numberOfVotesNo) {
//	        return ResultVotingEnum.APROVED;
//	    } else if (numberOfVotesNo > numberOfVotesYes) {
//	        return ResultVotingEnum.NOT_APROVED;
//	    } else {
//	        return ResultVotingEnum.DRAW;
//	    }
//	}
}

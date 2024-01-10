package br.com.collaborativevotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResult;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.model.Vote;
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

	public VotingResult getResult(Long scheduleId, String language) throws Exception {
		Schedule schedule = findById(scheduleId);
		
		VotingResultValidation votingResultValidation = new VotingResultValidation(schedule, language, messageSource);
		votingResultValidation.execute();
		
		List<Vote> votes = schedule.getVotingSession().getVotes();
		
		return prepareVotingResult(votes);
	}
	
	private VotingResult prepareVotingResult(List<Vote> votes) {
		
		int voteTotal = votes.size();
		int numberOfVotesNo = (int) votes.stream().filter(vote -> vote.getVoteChoice().equals(VoteChoiceEnum.NO)).count();
		int numberOfVotesYes = (int) votes.stream().filter(vote -> vote.getVoteChoice().equals(VoteChoiceEnum.YES)).count();

		VotingResult votingResult = new VotingResult();
		votingResult.setTotalVotes(voteTotal);
		votingResult.setNumberOfVotesNo(numberOfVotesNo);
		votingResult.setNumberOfVotesYes(numberOfVotesYes);
		votingResult.setFinalVoteResult(getFinalVotingResult(numberOfVotesYes, numberOfVotesNo));
		
		return votingResult;
	}
	
	private ResultVotingEnum getFinalVotingResult(int numberOfVotesYes, int numberOfVotesNo) {
	    if (numberOfVotesYes > numberOfVotesNo) {
	        return ResultVotingEnum.YES;
	    } else if (numberOfVotesNo > numberOfVotesYes) {
	        return ResultVotingEnum.NO;
	    } else {
	        return ResultVotingEnum.DRAW;
	    }
	}
}

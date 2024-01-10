package br.com.collaborativevotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.SheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResult;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.model.Vote;
import br.com.collaborativevotingsystem.repository.SheduleRepository;
import br.com.collaborativevotingsystem.validation.ValidationShedule;
import br.com.collaborativevotingsystem.validation.VotingResultValidation;

@Service
public class SheduleService {

	private SheduleRepository sheduleRepository;
	
	private MessageSource messageSource;

	public SheduleService(SheduleRepository sheduleRepository, MessageSource messageSource) {
		this.sheduleRepository = sheduleRepository;
		this.messageSource = messageSource;
	}

	public SheduleDTO createShedule(SheduleDTO sheduleDTO, String language) throws Exception {
		Shedule shedule = sheduleDTO.generateShedule();
		
		ValidationShedule validationShedule = new ValidationShedule(shedule, language, messageSource);
		validationShedule.execute();

		sheduleRepository.save(shedule);

		return shedule.generateTransportObject();			
	}

	public Shedule findById(Long id) throws Exception {
		Optional<Shedule> sheduleOpt = sheduleRepository.findById(id);
		return sheduleOpt.orElseThrow(() -> new Exception("Shedule não encontrado com o id: " + id));
	}

	public VotingResult getResult(Long sheduleId, String language) throws Exception {
		Shedule shedule = findById(sheduleId);
		
		VotingResultValidation votingResultValidation = new VotingResultValidation(shedule, language, messageSource);
		votingResultValidation.execute();
		
		List<Vote> votes = shedule.getVotingSession().getVotes();
		
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

package br.com.collaborativevotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.SheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResult;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.model.Vote;
import br.com.collaborativevotingsystem.repository.SheduleRepository;

@Service
public class SheduleService {

	private SheduleRepository sheduleRepository;

	public SheduleService(SheduleRepository sheduleRepository) {
		this.sheduleRepository = sheduleRepository;
	}

	public SheduleDTO createShedule(SheduleDTO sheduleDTO) {
		// Criar validação antes de iniciar esse processamento
		Shedule shedule = sheduleDTO.generateShedule();
		sheduleRepository.save(shedule);

		return shedule.generateTransportObject();
	}

	public Shedule findById(Long id) throws Exception {
		Optional<Shedule> byUUID = sheduleRepository.findById(id);
		return byUUID.orElseThrow(() -> new Exception("Shedule não encontrado para o UUID: " + id));
	}

	public VotingResult getResult(Long sheduleId) throws Exception {
		//Criar validações antes de processar essas partes aqui
		//Não posso obter o resultado enquanto estiver em processo de votação
		Shedule shedule = findById(sheduleId);
		List<Vote> votes = shedule.getSectionVoting().getVotes();
		
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

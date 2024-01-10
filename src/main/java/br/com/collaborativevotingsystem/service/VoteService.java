package br.com.collaborativevotingsystem.service;

import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.repository.VoteRepository;

@Service
public class VoteService {

	private SheduleService sheduleService;

	private VoteRepository voteRepository;

	public VoteService(SheduleService sheduleService, VoteRepository voteRepository) {
		this.sheduleService = sheduleService;
		this.voteRepository = voteRepository;
	}

	public void vote(VoteDTO voteDTO, Long sheduleId) throws Exception {
		try {
			Shedule shedule = this.sheduleService.findById(sheduleId);
			// Criar uma classe de validação para ter varios tipos de validação e lançar exeções personalizadas
			voteDTO.setSectionVoting(shedule.getSectionVoting());

			voteRepository.save(voteDTO.generateVote());
		} catch (Exception e) {
			throw e;
		}
	}

}

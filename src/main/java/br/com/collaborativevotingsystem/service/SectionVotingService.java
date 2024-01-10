package br.com.collaborativevotingsystem.service;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.builder.SectionVotingBuilder;
import br.com.collaborativevotingsystem.model.SectionVoting;
import br.com.collaborativevotingsystem.model.Shedule;
import br.com.collaborativevotingsystem.repository.SectionVotingRepository;
import br.com.collaborativevotingsystem.validation.ValidationShedule;

@Service
public class SectionVotingService {
	
	private SheduleService sheduleService;
	
	private SectionVotingRepository sectionVotingRepository;
	
	private MessageSource messageSource;
	
	public SectionVotingService(SheduleService sheduleService, SectionVotingRepository sectionVotingRepository, MessageSource messageSource) {
		this.sheduleService = sheduleService;
		this.sectionVotingRepository = sectionVotingRepository;
		this.messageSource = messageSource;
	}

	public SectionVoting open(Long id, int votingDurationMinutes) throws Exception {
		try {
			Shedule shedule = this.sheduleService.findById(id);
	
			return createSectionVoting(shedule, votingDurationMinutes);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public SectionVoting createSectionVoting(Shedule shedule, int votingDurationMinutes) {
		SectionVoting sectionVoting = new SectionVotingBuilder()
				.withVotingStartDate(LocalDateTime.now())
				.withVotingDurationMinutes(votingDurationMinutes)
				.withShedule(shedule)
				.build();
		
		sectionVotingRepository.save(sectionVoting);
		shedule.setSectionVoting(sectionVoting);
		
		return sectionVotingRepository.save(sectionVoting);
	}

}

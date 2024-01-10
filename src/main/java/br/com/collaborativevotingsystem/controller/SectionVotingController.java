package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.model.SectionVoting;
import br.com.collaborativevotingsystem.service.SectionVotingService;

@RestController
@RequestMapping("api/v1/section-voting")
public class SectionVotingController {
	
	private SectionVotingService sectionVotingService;
	
	public SectionVotingController(SectionVotingService sectionVotingService) {
		this.sectionVotingService = sectionVotingService;
	}
	
	@PostMapping("/open")
	public ResponseEntity<?> openSectionVoting(@RequestParam Long id, @RequestParam(defaultValue = "1") int votingDurationMinutes){
		try {
			SectionVoting sectionVotingCreated = sectionVotingService.open(id, votingDurationMinutes);
			String message = String.format("Sessão inicializada na pauta: %s , duração de: %s, Data de termino da votação: %s", sectionVotingCreated.getShedule().getId(), sectionVotingCreated.getVotingDurationMinutes(), sectionVotingCreated.getVotingEndDate());
			
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
}

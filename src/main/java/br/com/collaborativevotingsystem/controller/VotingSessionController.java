package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.service.VotingSessionService;

@RestController
@RequestMapping("api/v1/voting-session")
public class VotingSessionController {
	
	private VotingSessionService votingSessionService;
	
	public VotingSessionController(VotingSessionService votingSessionService) {
		this.votingSessionService = votingSessionService;
	}
	
	@PostMapping("/open")
	public ResponseEntity<?> openVotingSession(@RequestParam Long scheduleId, @RequestParam(defaultValue = "1") int votingDurationMinutes,  @RequestHeader(name = "language", required = false) String language){
		try {
			VotingSession votingSessionCreated = votingSessionService.open(scheduleId, votingDurationMinutes, language);
			String message = String.format("Sessão inicializada na pauta: %s , duração de: %s, Data de termino da votação: %s", votingSessionCreated.getShedule().getId(), votingSessionCreated.getVotingDurationMinutes(), votingSessionCreated.getVotingEndDate());
			
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
}

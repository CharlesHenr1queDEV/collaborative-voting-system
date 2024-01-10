package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.exception.VotingSessionNotExistException;
import br.com.collaborativevotingsystem.service.VoteService;

@RestController
@RequestMapping("api/v1/vote")
public class VoteController {

	private VoteService voteService;

	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}

	@PostMapping
	public ResponseEntity<?> vote(@RequestParam(required=true) Long scheduleId, @RequestBody VoteDTO voteDTO, @RequestHeader(name = "language", required = false) String language) {
		try {
			voteService.vote(voteDTO, scheduleId, language);
			return new ResponseEntity<>("Voto computado com sucesso", HttpStatus.CREATED);
        }catch (VotingSessionNotExistException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } 
	}

}

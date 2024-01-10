package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.service.VoteService;

@RestController
@RequestMapping("api/v1/vote")
public class VoteController {

	private VoteService voteService;

	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}

	@PostMapping
	public ResponseEntity<?> vote(@RequestParam Long sheduleId, @RequestBody VoteDTO voteDTO) {
		try {
			voteService.vote(voteDTO, sheduleId);
			return new ResponseEntity<>("Voto computado com sucesso", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

}

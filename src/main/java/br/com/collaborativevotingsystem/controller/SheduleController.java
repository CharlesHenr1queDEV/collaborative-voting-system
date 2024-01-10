package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.SheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResult;
import br.com.collaborativevotingsystem.service.SheduleService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/v1/shedule")
public class SheduleController {
	
	private SheduleService sheduleService;
	
	public SheduleController(SheduleService sheduleService) {
		this.sheduleService = sheduleService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createShedule(@RequestBody SheduleDTO shaduleDTO, @RequestHeader(name="language", required=false) String language){
		try {
			SheduleDTO shedule = sheduleService.createShedule(shaduleDTO, language);
			return new ResponseEntity<>(shedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
	
	
	@GetMapping("/result/{sheduleId}")
	public ResponseEntity<?> getResult(@PathVariable Long sheduleId, @RequestHeader(name="language", required=false) String language){
		try {
			VotingResult votingResult = sheduleService.getResult(sheduleId, language);
			return new ResponseEntity<>(votingResult, HttpStatus.OK);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
	
}

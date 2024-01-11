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

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.service.ScheduleService;

@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {
	
	private ScheduleService scheduleService;
	
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO shaduleDTO, @RequestHeader(name="language", required=false) String language){
		try {
			ScheduleDTO schedule = scheduleService.createSchedule(shaduleDTO, language);
			return new ResponseEntity<>(schedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
	
	
	@GetMapping("/result/{scheduleId}")
	public ResponseEntity<?> getResult(@PathVariable Long scheduleId, @RequestHeader(name="language", required=false) String language){
		try {
			VotingResultDTO votingResult = scheduleService.getResult(scheduleId, language);
			return new ResponseEntity<>(votingResult, HttpStatus.OK);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
	
}

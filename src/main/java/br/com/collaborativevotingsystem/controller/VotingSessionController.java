package br.com.collaborativevotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.VotingSessionDTO;
import br.com.collaborativevotingsystem.exception.CollaborativeVotingSystemException;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.service.VotingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/voting-session")
public class VotingSessionController {
	
	private VotingSessionService votingSessionService;
	
	public VotingSessionController(VotingSessionService votingSessionService) {
		this.votingSessionService = votingSessionService;
	}
	
	@Operation(summary = "Abrir uma sessão de votação", description = "Abre uma nova sessão de votação em uma pauta")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sessão de votação aberta com sucesso", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = VotingSessionDTO.class))),
			@ApiResponse(responseCode = "400", description = "Problema durante o processamento de validação", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@PostMapping("/open")
	public ResponseEntity<?> openVotingSession(@RequestParam Long scheduleId, @RequestParam(defaultValue = "1") int votingDurationMinutes, 
			@Parameter(name = "language", description = "Idioma da solicitação", in = ParameterIn.HEADER, schema = @Schema(type = "string", defaultValue = "br", example = "br"), examples = {
                    @ExampleObject(name = "Opção en", value = "en"),
                    @ExampleObject(name = "Opção pt_br", value = "br")
                }) @RequestHeader(name = "language", required = false) String language){
		try {
			VotingSessionDTO transportObject = votingSessionService.open(scheduleId, votingDurationMinutes, language).generateTransportObject();
			return new ResponseEntity<>(transportObject, HttpStatus.CREATED);
		} catch (CollaborativeVotingSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}

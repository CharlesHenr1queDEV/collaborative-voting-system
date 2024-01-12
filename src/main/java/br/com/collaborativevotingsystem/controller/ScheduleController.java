package br.com.collaborativevotingsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.exception.CollaborativeVotingSystemException;
import br.com.collaborativevotingsystem.model.Schedule;
import br.com.collaborativevotingsystem.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {
	
	private ScheduleService scheduleService;
	
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	@Operation(summary = "Listar todas as pautas", description = "Retorna uma lista de todas as pautas")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDTO.class))),
		    @ApiResponse(responseCode = "204", description = "Não foi encontrado nenhuma pauta", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
		})
	@GetMapping
	public ResponseEntity<?> getAllSchedule(){
		try {
			List<ScheduleDTO> listScheduleDTO = scheduleService.findAll();
			if(listScheduleDTO.isEmpty()) {
				return new ResponseEntity<>(listScheduleDTO, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listScheduleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@Operation(summary = "Buscar uma pauta ", description = "Retorna a pauta solicitada")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDTO.class))),
		    @ApiResponse(responseCode = "204", description = "Não foi encontrado nenhuma pauta", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
		})
	@GetMapping("/{id}")
	public ResponseEntity<?> getScheduleById(@PathVariable Long id){
		try {
			ScheduleDTO scheduleDTO = scheduleService.findById(id).generateTransportObject();
			return new ResponseEntity<>(scheduleDTO, HttpStatus.OK);
        }catch (CollaborativeVotingSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@Operation(summary = "Criar nova pauta", description = "Retorna a pauta criada com identificador")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "Criação de pauta realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDTO.class))),
		    @ApiResponse(responseCode = "400", description = "Problema durante o processamento de validação", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
		    
		})
	@PostMapping("/create")
	public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO shaduleDTO,
			@Parameter(name = "language", description = "Idioma da solicitação", in = ParameterIn.HEADER, schema = @Schema(type = "string", defaultValue = "br", example = "br"), examples = {
                    @ExampleObject(name = "Opção en", value = "en"),
                    @ExampleObject(name = "Opção br", value = "br")
                }) @RequestHeader(name = "language", required = false) String language) {
		try {
			ScheduleDTO schedule = scheduleService.createSchedule(shaduleDTO, language);
			return new ResponseEntity<>(schedule, HttpStatus.CREATED);
		} catch (CollaborativeVotingSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Deletar uma pauta", description = "Deletar uma pauta a partir de um id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
        	scheduleService.deleteScheduleById(id);
        	return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}   
    }
	
	
	@Operation(summary = "Buscar status da pauta", description = "Busca da pauta a informação se a pauta foi aprovada, não aprovada, empate e etc.")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "Resultado retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
		    @ApiResponse(responseCode = "400", description = "Problema durante o processamento de validação", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
		    
		})
	@GetMapping("/result/{scheduleId}")
	public ResponseEntity<?> getScheduleStatusVoting(@PathVariable Long scheduleId){
		try {
			ScheduleDTO scheduleDTO = scheduleService.findById(scheduleId).generateTransportObject();
			return new ResponseEntity<>(scheduleDTO.getResultVotingEnum(), HttpStatus.OK);
		} catch (CollaborativeVotingSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@Operation(summary = "Buscar resultado detalhado", description = "Consulta realizada para extrair informaçoes mais completas sobre a votação como quantidade total de votos, votos positivos e etc.")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "Resultado retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VotingResultDTO.class))),
		    @ApiResponse(responseCode = "400", description = "Problema durante o processamento de validação", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
		    
		})
	@GetMapping("/result-detailed/{scheduleId}")
	public ResponseEntity<?> getResultDetailed(@PathVariable Long scheduleId, 
			@Parameter(name = "language", description = "Idioma da solicitação", in = ParameterIn.HEADER, schema = @Schema(type = "string", defaultValue = "br", example = "br"), examples = {
                    @ExampleObject(name = "Opção en", value = "en"),
                    @ExampleObject(name = "Opção pt_br", value = "br")
                }) @RequestHeader(name="language", required=false) String language){
		try {
			Schedule sh = new Schedule();
			VotingResultDTO votingResult = scheduleService.getResult(scheduleId, language);
			return new ResponseEntity<>(votingResult, HttpStatus.OK);
		} catch (CollaborativeVotingSystemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}

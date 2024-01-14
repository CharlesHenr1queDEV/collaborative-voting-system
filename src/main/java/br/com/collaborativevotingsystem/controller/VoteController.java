package br.com.collaborativevotingsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.exception.CollaborativeVotingSystemException;
import br.com.collaborativevotingsystem.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/vote")
@Validated
public class VoteController {

	private VoteService voteService;

	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}
	
	@Operation(summary = "Registrar novo voto", description = "Registra voto em uma pauta")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "Voto computado com sucesso", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = VoteDTO.class))),
		    @ApiResponse(responseCode = "400", description = "Problema durante o processamento de validação", content = @Content),
		    @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
		})
	@PostMapping
	public ResponseEntity<?> registerVote(@RequestBody @Valid VoteDTO voteDTO,
			@Parameter(name = "language", description = "Idioma da solicitação", in = ParameterIn.HEADER, schema = @Schema(type = "string", defaultValue = "br", example = "br"), examples = {
                    @ExampleObject(name = "Opção en", value = "en"),
                    @ExampleObject(name = "Opção pt_br", value = "br")
                }) @RequestHeader(name = "language", required = false) String language) {
		try {
			VoteDTO voteDto = voteService.vote(voteDTO, language);
			return new ResponseEntity<>(voteDto, HttpStatus.CREATED);
        }catch (CollaborativeVotingSystemException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
	

	
}

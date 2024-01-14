package br.com.collaborativevotingsystem.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.service.ScheduleService;
import br.com.collaborativevotingsystem.service.VotingSessionService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoteControllerTest {

	private final String API_URL = "/api/v1/vote";

	@Autowired
	private MockMvc mock;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private VotingSessionService votingService;

	private final String TITLE = "Evento Genérico";

	private final String DESCRIPTION = "Descrição Genérica";

	private ScheduleDTO schedule;

	private VotingSession votingSession;

	@BeforeAll
	public void setup() throws Exception {

		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setTitle(TITLE);
		scheduleDTO.setDescription(DESCRIPTION);

		this.schedule = scheduleService.createSchedule(scheduleDTO, null);

		this.votingSession = votingService.open(schedule.getId(), 2, null);

	}

	@Test
	public void vote_whenVoteDTOIsValid_andExistSchedule_thenSuccess() throws Exception {
		
		VoteDTO voteDTO = prepareVoteDTO(schedule.getId(), "07178984406", "sim");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void vote_whenVoteDTOIsValid_andNotExistSchedule_thenError() throws Exception {
		
		VoteDTO voteDTO = prepareVoteDTO(9999L, "07178984406", "sim");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void vote_whenVoteDTOIsValid_andNotExistVotingSession_thenError() throws Exception {

		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setTitle(TITLE);
		scheduleDTO.setDescription(DESCRIPTION);

		ScheduleDTO scheduleDtoInMethod = scheduleService.createSchedule(scheduleDTO, null);

		VoteDTO voteDTO = prepareVoteDTO(scheduleDtoInMethod.getId(), "58298634055", "sim");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(
				MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Ainda não foi criado uma sessão de votação para a pauta: " + TITLE));
	}

	@Test
	public void vote_whenAssociateIdentifierNotValid_thenError() throws Exception {

		VoteDTO voteDTO = prepareVoteDTO(1l, "5829863401", "sim");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(
				MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void vote_whenAssociateIdentifierIsEmpty_thenError() throws Exception {

		VoteDTO voteDTO = prepareVoteDTO(1l, "", "sim");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(
				MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void vote_whenVoteChoiceIsEmpty_thenError() throws Exception {

		VoteDTO voteDTO = prepareVoteDTO(1l, "07178984406", "");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(
				MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void vote_whenVoteChoiceIsNull_thenError() throws Exception {

		VoteDTO voteDTO = prepareVoteDTO(1l, "07178984406", null);
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(voteDTO);

		mock.perform(
				MockMvcRequestBuilders.post(API_URL)
				.content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


	@Test
	private VoteDTO prepareVoteDTO(Long scheduleId, String associateIdentifier, String voteChoice) {

		VoteDTO vote = new VoteDTO();
		vote.setScheduleId(scheduleId);
		vote.setAssociateIdentifier(associateIdentifier);
		vote.setVoteChoice(voteChoice);

		return vote;
	}
}

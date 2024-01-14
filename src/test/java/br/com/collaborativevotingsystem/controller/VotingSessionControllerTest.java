package br.com.collaborativevotingsystem.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.service.ScheduleService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VotingSessionControllerTest {

	private final String END_POINT = "/api/v1/voting-session/open";

	@Autowired
	private MockMvc mock;

	@Autowired
	private ScheduleService scheduleService;

	private ScheduleDTO schedule;

	private final String title = "Evento Genérico";
	private final String description = "Descrição Genérica";

	@BeforeAll
	public void setup() throws Exception {

		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setTitle(title);
		scheduleDTO.setDescription(description);

		this.schedule = scheduleService.createSchedule(scheduleDTO, null);
	}
	
	
	@Test
	@Order(1)
	public void openVotingSession_whenScheduleIdIsEmpty_thenError() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.post(END_POINT)
				.param("scheduleId", ""))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@Order(2)
	public void openVotingSession_whenVotingDurationLessThanOrEqualToZero_thenError() throws Exception {

		mock.perform(MockMvcRequestBuilders.post(END_POINT)
				.param("scheduleId", schedule.getId().toString())
				.param("votingDurationMinutes", "-1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content()
				.string("A duração de uma sessão não pode ser menor ou igual a 0 minutos."));

	}

	@Test
	@Order(3)
	public void openVotingSession_whenParamsIsValid_thenSuccess() throws Exception {

		mock.perform(MockMvcRequestBuilders.post(END_POINT)
				.param("scheduleId", schedule.getId().toString())
				.param("votingDurationMinutes", "1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	@Order(4)
	public void openVotingSession_whenParamsIsValid_andDuplicateId_thenError() throws Exception {

		mock.perform(MockMvcRequestBuilders.post(END_POINT)
				.param("scheduleId", schedule.getId().toString())
				.param("votingDurationMinutes", "1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content()
				.string("Já existe uma sessão inicializada para a pauta: " + title));

	}
}

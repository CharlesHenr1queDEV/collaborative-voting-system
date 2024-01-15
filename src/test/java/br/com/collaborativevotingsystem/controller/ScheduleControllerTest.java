package br.com.collaborativevotingsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {

	private final String END_POINT = "/api/v1/schedule";

	@Autowired
	private MockMvc mock;

	@BeforeEach
	@Test
	public void whenScheduleIsValid_thenSuccess() throws Exception {

		ScheduleDTO dto = new ScheduleDTO();
		dto.setTitle("title test");
		dto.setDescription("description test");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(dto);

		mock.perform(MockMvcRequestBuilders.post(END_POINT + "/create").content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description test"));
	}

	@Test
	public void whenTitleIsEmpty_thenError() throws Exception {

		ScheduleDTO dto = new ScheduleDTO();
		dto.setTitle("");
		dto.setDescription("description test");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(dto);

		mock.perform(MockMvcRequestBuilders.post(END_POINT + "/create").content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content()
						.string("O título da pauta está nulo ou vazio. Por favor, forneça um título válido."));
	}

	@Test
	public void whenTitleIsNull_thenError() throws Exception {

		ScheduleDTO dto = new ScheduleDTO();
		dto.setTitle(null);
		dto.setDescription("descrição");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(dto);

		mock.perform(MockMvcRequestBuilders.post(END_POINT + "/create").content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content()
						.string("O título da pauta está nulo ou vazio. Por favor, forneça um título válido."));
	}

	@Test
	public void whenDescriptionIsEmpty_thenError() throws Exception {

		ScheduleDTO dto = new ScheduleDTO();
		dto.setTitle("title teste");
		dto.setDescription("");
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(dto);

		mock.perform(MockMvcRequestBuilders.post(END_POINT + "/create").content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content()
						.string("A descrição da pauta está nulo ou vazia. Por favor, forneça uma descrição válida."));
	}

	@Test
	public void whenDescriptionIsNull_thenError() throws Exception {

		ScheduleDTO dto = new ScheduleDTO();
		dto.setTitle("title teste");
		dto.setDescription(null);
		ObjectMapper objectMapper = new ObjectMapper();

		String dtoObjectString = objectMapper.writeValueAsString(dto);

		mock.perform(MockMvcRequestBuilders.post(END_POINT + "/create").content(dtoObjectString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content()
						.string("A descrição da pauta está nulo ou vazia. Por favor, forneça uma descrição válida."));
	}

	@Test
	public void findById_whenIdIsValid_thenSuccess() throws Exception {

		mock.perform(MockMvcRequestBuilders.get(END_POINT + "/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
	}

	@Test
	public void findById_whenIdInvalid_thenError() throws Exception {

		mock.perform(MockMvcRequestBuilders.get(END_POINT + "/{id}", 99)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void findById_whenIdNull_thenError() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.get(END_POINT + "/{id}", "")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void findAll_whenContentSchedule_thenSuccess() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.get(END_POINT))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getScheduleStatus_whenIdIsValid_thenSuccess() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.get(END_POINT+"/status/{scheduleId}", 1))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void getScheduleStatus_whenIdInvalid_thenError() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.get(END_POINT+"/status/{scheduleId}", 99))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void getResultDetailed_whenIdIsValid_andNotVotingSessionOpen_thenError() throws Exception {
		
		mock.perform(MockMvcRequestBuilders.get(END_POINT+"/result-detailed/{scheduleId}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


}

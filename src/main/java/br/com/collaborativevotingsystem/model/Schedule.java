package br.com.collaborativevotingsystem.model;

import java.io.Serializable;

import br.com.collaborativevotingsystem.dto.ScheduleDTO;
import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = -3620125786415817467L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id da pauta")
	private Long id;
	
	@Schema(description = "Título da pauta")
	private String title;
	
	@Schema(description = "Descrição da pauta")
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "voting_session_id", referencedColumnName = "id")
	private VotingSession votingSession;
	
	@Enumerated(EnumType.STRING)
	@Schema(description = "Status da votação")
	private ResultVotingEnum resultVotingEnum;

	public Schedule() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public VotingSession getVotingSession() {
		return votingSession;
	}

	public void setVotingSession(VotingSession votingSession) {
		this.votingSession = votingSession;
	}
	
	public ResultVotingEnum getResultVotingEnum() {
		return resultVotingEnum;
	}

	public void setResultVotingEnum(ResultVotingEnum resultVotingEnum) {
		this.resultVotingEnum = resultVotingEnum;
	}

	public ScheduleDTO generateTransportObject() {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setId(id);
		scheduleDTO.setTitle(title);
		scheduleDTO.setDescription(description);
		scheduleDTO.setResultVotingEnum(resultVotingEnum);
		return scheduleDTO;
	}
}

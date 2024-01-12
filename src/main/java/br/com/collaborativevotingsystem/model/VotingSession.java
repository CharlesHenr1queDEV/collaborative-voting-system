package br.com.collaborativevotingsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.collaborativevotingsystem.dto.VotingSessionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class VotingSession implements Serializable {

	private static final long serialVersionUID = 1763744151745671641L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Schema(description = "Data de inicio da sessão de votação")
	private LocalDateTime votingStartDate;

	@Schema(description = "Data do fim da sessão de votação")
	private LocalDateTime votingEndDate;

	@Schema(description = "Quantidade de tempo em minutos, que a sessão vai ficar aberta")
	private int votingDurationMinutes;

	@OneToOne
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@OneToMany(mappedBy = "votingSession", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Vote> votes = new ArrayList<>();

	public VotingSession() {
		this.votes = new ArrayList<>();
	}

	public VotingSession(LocalDateTime votingStartDate, LocalDateTime votingEndDate, int votingDurationMinutes,
			Schedule schedule) {
		this.votingStartDate = votingStartDate;
		this.votingEndDate = votingEndDate;
		this.votingDurationMinutes = votingDurationMinutes;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVotingStartDate() {
		return votingStartDate;
	}

	public void setVotingStartDate(LocalDateTime votingStartDate) {
		this.votingStartDate = votingStartDate;
	}

	public LocalDateTime getVotingEndDate() {
		return votingEndDate;
	}

	public void setVotingEndDate(LocalDateTime votingEndDate) {
		this.votingEndDate = votingEndDate;
	}

	public int getVotingDurationMinutes() {
		return votingDurationMinutes;
	}

	public void setVotingDurationMinutes(int votingDurationMinutes) {
		this.votingDurationMinutes = votingDurationMinutes;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public VotingSessionDTO generateTransportObject() {
		VotingSessionDTO votingSessionDTO = new VotingSessionDTO();
		votingSessionDTO.setSchedule(schedule.generateTransportObject());
		votingSessionDTO.setVotingDurationMinutes(votingDurationMinutes);
		votingSessionDTO.setVotingEndDate(votingEndDate);
		votingSessionDTO.setVotingStartDate(votingStartDate);
		
		return votingSessionDTO;
	}

}

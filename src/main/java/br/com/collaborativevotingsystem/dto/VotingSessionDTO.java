package br.com.collaborativevotingsystem.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public class VotingSessionDTO {

	@Schema(description = "Data de inicio da sessão de votação")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime votingStartDate;

	@Schema(description = "Data do fim da sessão de votação")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime votingEndDate;

	@Schema(description = "Quantidade de tempo em minutos, que a sessão vai ficar aberta")
	private int votingDurationMinutes;

	private ScheduleDTO schedule;
	
	public VotingSessionDTO() {
		
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

	public ScheduleDTO getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleDTO schedule) {
		this.schedule = schedule;
	}
	
	
}

package br.com.collaborativevotingsystem.builder;

import java.time.LocalDateTime;

import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.model.Schedule;

public class VotingSessionBuilder {

	private LocalDateTime votingStartDate;

	private LocalDateTime votingEndDate;

	private int votingDurationMinutes;

	private Schedule schedule;

	public VotingSessionBuilder withVotingStartDate(LocalDateTime votingStartDate) {
		this.votingStartDate = votingStartDate;
		return this;
	}

	public VotingSessionBuilder withVotingDurationMinutes(int votingDurationMinutes) {
		this.votingDurationMinutes = votingDurationMinutes;
		return this;
	}

	public VotingSessionBuilder withSchedule(Schedule schedule) {
		this.schedule = schedule;
		return this;
	}

	public VotingSession build() {
		// Configura a data de término com base na data de início e na duração
		this.votingEndDate = this.votingStartDate.plusMinutes(votingDurationMinutes);
		return new VotingSession(votingStartDate, votingEndDate, votingDurationMinutes, schedule);
	}

}

package br.com.collaborativevotingsystem.builder;

import java.time.LocalDateTime;

import br.com.collaborativevotingsystem.model.SectionVoting;
import br.com.collaborativevotingsystem.model.Shedule;

public class SectionVotingBuilder {

	private LocalDateTime votingStartDate;

	private LocalDateTime votingEndDate;

	private int votingDurationMinutes;

	private Shedule shedule;

	public SectionVotingBuilder withVotingStartDate(LocalDateTime votingStartDate) {
		this.votingStartDate = votingStartDate;
		return this;
	}

	public SectionVotingBuilder withVotingDurationMinutes(int votingDurationMinutes) {
		this.votingDurationMinutes = votingDurationMinutes;
		return this;
	}

	public SectionVotingBuilder withShedule(Shedule shedule) {
		this.shedule = shedule;
		return this;
	}

	public SectionVoting build() {
		// Configura a data de término com base na data de início e na duração
		this.votingEndDate = this.votingStartDate.plusMinutes(votingDurationMinutes);
		return new SectionVoting(votingStartDate, votingEndDate, votingDurationMinutes, shedule);
	}

}

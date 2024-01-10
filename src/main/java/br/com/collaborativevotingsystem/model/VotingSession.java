package br.com.collaborativevotingsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

	private LocalDateTime votingStartDate;

	private LocalDateTime votingEndDate;

	private int votingDurationMinutes;

	@OneToOne
	@JoinColumn(name = "shedule_id")
	private Shedule shedule;

	@OneToMany(mappedBy = "votingSession", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vote> votes;

	public VotingSession(LocalDateTime votingStartDate, LocalDateTime votingEndDate, int votingDurationMinutes,
			Shedule shedule) {
		this.votingStartDate = votingStartDate;
		this.votingEndDate = votingEndDate;
		this.votingDurationMinutes = votingDurationMinutes;
		this.shedule = shedule;
	}

	public VotingSession() {

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

	public Shedule getShedule() {
		return shedule;
	}

	public void setShedule(Shedule shedule) {
		this.shedule = shedule;
	}

}

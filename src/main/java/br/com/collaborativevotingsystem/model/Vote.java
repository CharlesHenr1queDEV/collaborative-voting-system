package br.com.collaborativevotingsystem.model;

import java.io.Serializable;

import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote implements Serializable{
	
	private static final long serialVersionUID = -8139421705377472950L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private VoteChoiceEnum voteChoice;
			
	@Column(unique = true)
	private String associateIdentifier;
	
	@ManyToOne
	@JoinColumn(name = "voting_session_id")
	private VotingSession votingSession;

	public Vote(VoteChoiceEnum voteChoice, String associateIdentifier, VotingSession votingSession) {
		this.voteChoice = voteChoice;
		this.associateIdentifier = associateIdentifier;
		this.votingSession = votingSession;
	}
	
	public Vote() {}

	public VoteChoiceEnum getVoteChoice() {
		return voteChoice;
	}

	public void setVoteChoice(VoteChoiceEnum voteChoice) {
		this.voteChoice = voteChoice;
	}

	public String getAssociateIdentifier() {
		return associateIdentifier;
	}

	public void setAssociateIdentifier(String associateIdentifier) {
		this.associateIdentifier = associateIdentifier;
	}

	public VotingSession getVotingSession() {
		return votingSession;
	}

	public void setVotingSession(VotingSession votingSession) {
		this.votingSession = votingSession;
	}
	
	
}

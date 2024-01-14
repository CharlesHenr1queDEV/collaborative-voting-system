package br.com.collaborativevotingsystem.model;

import java.io.Serializable;

import br.com.collaborativevotingsystem.dto.VoteDTO;
import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    uniqueConstraints = @UniqueConstraint(columnNames = {"associate_identifier", "voting_session_id"})
	)
public class Vote implements Serializable{
	
	private static final long serialVersionUID = -8139421705377472950L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id do voto")
	private Long id;

	@Schema(description = "Opção de voto")
	private VoteChoiceEnum voteChoice;
			
	@Schema(description = "Identificador do associado (CPF)")
	private String associateIdentifier;
	
	@ManyToOne
	@JoinColumn(name = "voting_session_id")
	private VotingSession votingSession;

	public Vote() {}

	public Vote(VoteChoiceEnum voteChoice, String associateIdentifier, VotingSession votingSession) {
		this.voteChoice = voteChoice;
		this.associateIdentifier = associateIdentifier;
		this.votingSession = votingSession;
	}
	

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
	
	public VoteDTO generateTransportObject() {
		VoteDTO voteDTO = new VoteDTO();
		voteDTO.setScheduleId(votingSession.getSchedule() != null ? votingSession.getSchedule().getId() : null);
		voteDTO.setAssociateIdentifier(associateIdentifier);
		voteDTO.setVoteChoice(voteChoice.getValue());
		voteDTO.setVotingSession(votingSession);
		
		return voteDTO;
	}
	
}

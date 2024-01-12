package br.com.collaborativevotingsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.Vote;
import br.com.collaborativevotingsystem.model.VotingSession;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VoteDTO {

	@NotBlank
	private Long scheduleId;
	
    @Schema(description = "Opção de voto")
    @Pattern(regexp = "^(sim|não)$", message = "VoteChoice deve ser 'sim' ou 'não'")
    @NotBlank
	private String voteChoice;

    @Schema(description = "Identificador do associado (CPF)")
    @NotBlank
	private String associateIdentifier;

    @Schema(description = "Sessão de votação")
    @JsonIgnore
	private VotingSession votingSession;
    
	public VoteDTO() {
	}


	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public String getVoteChoice() {
		return voteChoice;
	}

	public void setVoteChoice(String voteChoice) {
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
	
	public Vote generateVote() {
		Vote vote = new Vote();
		vote.setAssociateIdentifier(associateIdentifier);
		vote.setVotingSession(votingSession);
		vote.setVoteChoice(VoteChoiceEnum.findByValue(voteChoice));

		return vote;
	}

}

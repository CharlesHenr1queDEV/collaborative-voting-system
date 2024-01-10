package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.VotingSession;
import br.com.collaborativevotingsystem.model.Vote;

public class VoteDTO {

	private String voteChoice;

	private String associateIdentifier;

	private VotingSession votingSession;

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

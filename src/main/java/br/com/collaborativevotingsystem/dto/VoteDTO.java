package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import br.com.collaborativevotingsystem.model.SectionVoting;
import br.com.collaborativevotingsystem.model.Vote;

public class VoteDTO {

	private String voteChoice;

	private String associateIdentifier;

	private SectionVoting sectionVoting;

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

	public SectionVoting getSectionVoting() {
		return sectionVoting;
	}

	public void setSectionVoting(SectionVoting sectionVoting) {
		this.sectionVoting = sectionVoting;
	}

	public Vote generateVote() {
		Vote vote = new Vote();
		vote.setAssociateIdentifier(associateIdentifier);
		vote.setSectionVoting(sectionVoting);
		vote.setVoteChoice(VoteChoiceEnum.findByValue(voteChoice));

		return vote;
	}

}

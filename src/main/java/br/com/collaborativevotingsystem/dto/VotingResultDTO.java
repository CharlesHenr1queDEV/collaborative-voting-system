package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.ResultVotingEnum;

public class VotingResult {

	private int totalVotes;
	
	private int numberOfVotesYes;
	
	private int numberOfVotesNo;
	
	private ResultVotingEnum finalVoteResult;

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	public int getNumberOfVotesYes() {
		return numberOfVotesYes;
	}

	public void setNumberOfVotesYes(int numberOfVotesYes) {
		this.numberOfVotesYes = numberOfVotesYes;
	}

	public int getNumberOfVotesNo() {
		return numberOfVotesNo;
	}

	public void setNumberOfVotesNo(int numberOfVotesNo) {
		this.numberOfVotesNo = numberOfVotesNo;
	}
	
	public ResultVotingEnum getFinalVoteResult() {
		return finalVoteResult;
	}

	public void setFinalVoteResult(ResultVotingEnum finalVoteResult) {
		this.finalVoteResult = finalVoteResult;
	}

	@Override
	public String toString() {
		StringBuilder sf = new StringBuilder();
		sf.append("Resultado da votação: " + finalVoteResult.getMessage());
		sf.append("\n");
		sf.append("Total de votos computados: " + totalVotes);
		sf.append("\n");
		sf.append("Total de votos Sim: " + numberOfVotesYes);	
		sf.append("\n");
		sf.append("Total de votos Não: " + numberOfVotesNo);	
		
		return sf.toString();
	}

}

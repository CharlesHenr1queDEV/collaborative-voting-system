package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public class VotingResultDTO {

	@Schema(description = "Quantidade total de votos")
	private int totalVotes;
	
	@Schema(description = "Quantidade de votos a favor")
	private int numberOfVotesYes;
	
	@Schema(description = "Quantidade de votos contra")
	private int numberOfVotesNo;
	
	@Schema(description = "Resultado final")
	private ResultVotingEnum finalVoteResult;
	
	public VotingResultDTO() {

	}

	public VotingResultDTO(long totalVotes, long numberOfVotesYes, long numberOfVotesNo) {
        this.totalVotes = (int) totalVotes;
        this.numberOfVotesYes = (int) numberOfVotesYes;
        this.numberOfVotesNo = (int) numberOfVotesNo;
    }

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
	
	public void calculateFinalVoteResult() {
		  this.finalVoteResult = (numberOfVotesYes > numberOfVotesNo) ? ResultVotingEnum.APPROVED :
              (numberOfVotesYes < numberOfVotesNo) ? ResultVotingEnum.NOT_APPROVED :
              ResultVotingEnum.DRAW;
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

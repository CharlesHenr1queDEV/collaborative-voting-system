package br.com.collaborativevotingsystem.exception;

public class VotingInProgressException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = -8149212260985804694L;

	public final static String MESSAGE = "error.voting.in.progress";

	public VotingInProgressException(String message) {
		super(message);
	}

}

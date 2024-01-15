package br.com.collaborativevotingsystem.exception;

public class VotingSessionNotExistException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = -1043026999008142851L;

	public final static String MESSAGE = "error.session.voting.not.exist";

	public VotingSessionNotExistException(String message) {
		super(message);
	}
}

package br.com.collaborativevotingsystem.exception;

public class VotingSessionTimeHasExpiredException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = -7746606307096412969L;
	
	public final static String MESSAGE = "error.voting.session.time.expired";

	public VotingSessionTimeHasExpiredException(String message) {
		super(message);
	}
}

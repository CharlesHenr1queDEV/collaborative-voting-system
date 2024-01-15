package br.com.collaborativevotingsystem.exception;

public class VotingSessionDurationMinutesInvalidException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = 4697721400524645281L;
	
	public final static String MESSAGE = "error.voting.session.duration.minutes.invalid";

	public VotingSessionDurationMinutesInvalidException(String message) {
		super(message);
	}

}

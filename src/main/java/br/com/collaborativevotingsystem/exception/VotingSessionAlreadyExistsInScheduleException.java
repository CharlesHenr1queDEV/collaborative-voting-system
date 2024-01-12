package br.com.collaborativevotingsystem.exception;

public class VotingSessionAlreadyExistsInScheduleException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = 4697721400524645281L;
	
	public final static String MESSAGE = "error.voting.session.already.exist.in.schedule";

	public VotingSessionAlreadyExistsInScheduleException(String message) {
		super(message);
	}
}

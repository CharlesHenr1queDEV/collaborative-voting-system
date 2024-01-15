package br.com.collaborativevotingsystem.exception;

public class ScheduleTitleIsBlanckOrNullException extends CollaborativeVotingSystemException{

	private static final long serialVersionUID = -8752462317416418872L;
	
	public final static String MESSAGE ="error.schedule.title.blanck.null";
	
	public ScheduleTitleIsBlanckOrNullException(String message) {
		super(message);
	}
	
}

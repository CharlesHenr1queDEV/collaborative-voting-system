package br.com.collaborativevotingsystem.exception;

import br.com.collaborativevotingsystem.CollaborativeVotingSystemException;

public class ScheduleDescriptionIsBlanckOrNullException extends CollaborativeVotingSystemException{
	
	private static final long serialVersionUID = 3677093408621388494L;
	
	public final static String MESSAGE ="error.schedule.description.blanck.null";
	
	public ScheduleDescriptionIsBlanckOrNullException(String message) {
		super(message);
	}
}

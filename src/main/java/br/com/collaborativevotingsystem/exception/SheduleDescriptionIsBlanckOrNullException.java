package br.com.collaborativevotingsystem.exception;

import br.com.collaborativevotingsystem.CollaborativeVotingSystemException;

public class SheduleDescriptionIsBlanckOrNullException extends CollaborativeVotingSystemException{
	
	private static final long serialVersionUID = 3677093408621388494L;
	
	public final static String MESSAGE ="error.shedule.description.blanck.null";
	
	public SheduleDescriptionIsBlanckOrNullException(String message) {
		super(message);
	}
}

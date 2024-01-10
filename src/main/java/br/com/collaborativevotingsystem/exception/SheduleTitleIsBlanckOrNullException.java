package br.com.collaborativevotingsystem.exception;

import br.com.collaborativevotingsystem.CollaborativeVotingSystemException;

public class SheduleTitleIsBlanckOrNullException extends CollaborativeVotingSystemException{

	private static final long serialVersionUID = -8752462317416418872L;
	
	public final static String MESSAGE ="error.shedule.title.blanck.null";
	
	public SheduleTitleIsBlanckOrNullException(String message) {
		super(message);
	}
	
}

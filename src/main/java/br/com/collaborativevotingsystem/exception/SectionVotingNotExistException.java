package br.com.collaborativevotingsystem.exception;

import br.com.collaborativevotingsystem.CollaborativeVotingSystemException;

public class SectionVotingNotExistException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = -1043026999008142851L;

	public final static String MESSAGE = "error.section.voting.not.exist";

	public SectionVotingNotExistException(String message) {
		super(message);
	}
}

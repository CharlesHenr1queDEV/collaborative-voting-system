package br.com.collaborativevotingsystem.exception;

import br.com.collaborativevotingsystem.CollaborativeVotingSystemException;

public class VoteAlreadyComputedAssociateException extends CollaborativeVotingSystemException {

	private static final long serialVersionUID = -5897427275517491483L;

	public final static String MESSAGE = "error.vote.already.computed.associate";

	public VoteAlreadyComputedAssociateException(String message) {
		super(message);
	}
}

package br.com.collaborativevotingsystem;

public class CollaborativeVotingSystemException extends RuntimeException {

	private static final long serialVersionUID = 8906474866950846469L;

	private Object[] args;

	protected CollaborativeVotingSystemException() {
		super();
	}

	protected CollaborativeVotingSystemException(final String message, Object... args) {
		super(message);
		this.args = args;
	}

}

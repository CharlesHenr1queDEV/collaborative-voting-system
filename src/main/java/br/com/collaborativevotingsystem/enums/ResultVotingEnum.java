package br.com.collaborativevotingsystem.enums;

public enum ResultVotingEnum {
	
	APPROVED("Sim", "A maioria dos votos foi 'Sim'. A pauta foi aprovada."),
	NOT_APPROVED("Não", "A maioria dos votos foram 'Não'. A pauta não foi aprovada."),
	DRAW("Empate", "A quantidade de votos foram iguais! Não houve uma definição para está pauta."),
	PENDING("Pendente", "Votação ou ainda está ocorrendo"),
	NOT_INITIALIZED("Não inicializada", "Votação inicialiazada");

	private String value;
	private String message;
	
	ResultVotingEnum(String value, String message) {
		this.value = value;
		this.message = message;
	}

	public String getValue() {
		return value;
	}
	
	public String getMessage() {
		return message;
	}

}

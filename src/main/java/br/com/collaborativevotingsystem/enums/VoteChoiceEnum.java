package br.com.collaborativevotingsystem.enums;

public enum VoteChoiceEnum {

	YES("Sim"),
	NO("Não");
	
	private String value;

	VoteChoiceEnum(String value) {
		this.value = value;
	}

}

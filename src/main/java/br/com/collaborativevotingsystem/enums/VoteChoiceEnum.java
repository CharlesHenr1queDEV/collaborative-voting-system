package br.com.collaborativevotingsystem.enums;

public enum VoteChoiceEnum {

	NO("Não"), YES("Sim");

	private String value;

	VoteChoiceEnum(String value) {
		this.value = value;
	}

	public static VoteChoiceEnum findByValue(String value) {
		for (VoteChoiceEnum voteChoiceEnum : VoteChoiceEnum.values()) {
			if (voteChoiceEnum.value.equalsIgnoreCase(value)) {
				return voteChoiceEnum;
			}
		}
		throw new IllegalArgumentException("Valor inválido para VoteChoiceEnum: " + value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}

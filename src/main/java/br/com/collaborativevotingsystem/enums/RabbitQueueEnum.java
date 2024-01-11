package br.com.collaborativevotingsystem.enums;

public enum RabbitQueueEnum {

	RESULT(RabbitQueueEnum.QUEUE_VOTING_RESULT, "voting.result");

	private String rabbitQueueName;
	private String rabbitKey;

	public static final String QUEUE_VOTING_RESULT = "VOTING_RESULT";

	RabbitQueueEnum(String name, String routingKey) {
		this.rabbitQueueName = name;
		this.rabbitKey = routingKey;
	}

	public String getRabbitQueueName() {
		return rabbitQueueName;
	}

	public void setRabbitQueueName(String rabbitQueueName) {
		this.rabbitQueueName = rabbitQueueName;
	}

	public String getRabbitKey() {
		return rabbitKey;
	}

	public void setRabbitKey(String rabbitKey) {
		this.rabbitKey = rabbitKey;
	}

}

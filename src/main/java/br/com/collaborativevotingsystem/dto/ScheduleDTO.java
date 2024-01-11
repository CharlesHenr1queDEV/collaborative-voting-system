package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.model.Schedule;

public class ScheduleDTO {

	private Long id;

	private String title;

	private String description;

	private ResultVotingEnum resultVotingEnum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResultVotingEnum getResultVotingEnum() {
		return resultVotingEnum;
	}

	public void setResultVotingEnum(ResultVotingEnum resultVotingEnum) {
		this.resultVotingEnum = resultVotingEnum;
	}

	public Schedule generateSchedule() {
		Schedule schedule = new Schedule();
		schedule.setId(id);
		schedule.setTitle(title);
		schedule.setDescription(description);
		schedule.setResultVotingEnum(resultVotingEnum != null ? resultVotingEnum : ResultVotingEnum.NOT_INITIALIZED);

		return schedule;
	}
}

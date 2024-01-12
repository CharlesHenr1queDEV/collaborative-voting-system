package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.enums.ResultVotingEnum;
import br.com.collaborativevotingsystem.model.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class ScheduleDTO {

    @Schema(description = "ID da pauta", readOnly = true)
	private Long id;

    @Schema(description = "Título da pauta")
    @NotBlank
	private String title;

    @Schema(description = "Descrição da pauta")
    @NotBlank
	private String description;

    @Schema(description = "Status da votação", readOnly = true)
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

package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.model.Schedule;

public class ScheduleDTO {

	private Long id;
	
	private String title;

	private String description;

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

	public Schedule generateSchedule() {
		Schedule schedule = new Schedule();
		schedule.setId(id);
		schedule.setTitle(title);
		schedule.setDescription(description);
		
		return schedule;
	}
}

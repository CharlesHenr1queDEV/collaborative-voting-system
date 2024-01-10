package br.com.collaborativevotingsystem.dto;

import br.com.collaborativevotingsystem.model.Shedule;

public class SheduleDTO {

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

	public Shedule generateShedule() {
		Shedule shedule = new Shedule();
		shedule.setId(id);
		shedule.setTitle(title);
		shedule.setDescription(description);
		
		return shedule;
	}
}

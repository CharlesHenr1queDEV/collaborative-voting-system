package br.com.collaborativevotingsystem.model;

import java.io.Serializable;

import br.com.collaborativevotingsystem.dto.SheduleDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Shedule implements Serializable {
	
	private static final long serialVersionUID = -3620125786415817467L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "section_voting_id", referencedColumnName = "id")
	private SectionVoting sectionVoting;

	public Shedule() {}

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
	
	public SectionVoting getSectionVoting() {
		return sectionVoting;
	}

	public void setSectionVoting(SectionVoting sectionVoting) {
		this.sectionVoting = sectionVoting;
	}

	public SheduleDTO generateTransportObject() {
		SheduleDTO sheduleDTO = new SheduleDTO();
		sheduleDTO.setId(id);
		sheduleDTO.setTitle(title);
		sheduleDTO.setDescription(description);
		return sheduleDTO;
	}
}

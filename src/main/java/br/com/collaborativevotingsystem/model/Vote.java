package br.com.collaborativevotingsystem.model;

import java.io.Serializable;

import br.com.collaborativevotingsystem.enums.VoteChoiceEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote implements Serializable{
	
	private static final long serialVersionUID = -8139421705377472950L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private VoteChoiceEnum voteChoice;

	private String associateIdentifier;

	@ManyToOne
	@JoinColumn(name = "section_voting_id")
	private SectionVoting sectionVoting;
}

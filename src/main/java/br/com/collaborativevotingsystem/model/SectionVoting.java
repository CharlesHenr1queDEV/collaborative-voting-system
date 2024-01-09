package br.com.collaborativevotingsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionVoting implements Serializable {

	private static final long serialVersionUID = 1763744151745671641L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime votingStartDate;
	
	private LocalDateTime votingEndDate;
	
	private int votingDurationMinutes;

	@OneToMany(mappedBy = "sectionVoting", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vote> votes;

}

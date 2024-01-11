package br.com.collaborativevotingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("SELECT new br.com.collaborativevotingsystem.dto.VotingResultDTO(" + " COUNT(v), "
			+ " COUNT(CASE WHEN v.voteChoice = 1 THEN 1 END), " 
			+ " COUNT(CASE WHEN v.voteChoice = 0 THEN 1 END) " + ") "
			+ " FROM Vote v " + "WHERE v.votingSession.schedule = :schedule")
	VotingResultDTO getVoteResult(@Param("schedule") Schedule schedule);

}

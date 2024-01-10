package br.com.collaborativevotingsystem.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.collaborativevotingsystem.model.Schedule;

public interface ScheduleRepository extends CrudRepository<Schedule, Long>{
	
}

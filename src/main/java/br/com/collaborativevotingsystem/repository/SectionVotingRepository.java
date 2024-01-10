package br.com.collaborativevotingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.collaborativevotingsystem.model.SectionVoting;

@Repository
public interface SectionVotingRepository extends CrudRepository<SectionVoting, Long> {

}

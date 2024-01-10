package br.com.collaborativevotingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.collaborativevotingsystem.model.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

}

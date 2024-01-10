package br.com.collaborativevotingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.collaborativevotingsystem.model.VotingSession;

@Repository
public interface VotingSessionRepository extends CrudRepository<VotingSession, Long> {
   
	boolean existsVotesByVotesAssociateIdentifierAndId(String associateIdentifier, Long votingSessionId);

}

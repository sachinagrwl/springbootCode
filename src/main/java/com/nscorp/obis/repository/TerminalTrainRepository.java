package com.nscorp.obis.repository;

import com.nscorp.obis.domain.TermTrainComposite;
import com.nscorp.obis.domain.TerminalTrain;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TerminalTrainRepository extends JpaRepository<TerminalTrain,TermTrainComposite>{
	
	/* boolean findByTermIdAndTrainNr(Long termId, String trainNr); */

	boolean existsByTermIdAndTrainNr(Long termId, String trainNr);
	
	boolean existsByTrainNr(String trainNr);

	TerminalTrain findByTermIdAndTrainNr(Long termId, String trainNr);
	
	boolean existsByTrainNrAndTrainDesc(String trainNr, String trainDesc );
	
	void deleteByTermIdAndTrainNr(Long termId, String trainNr);

	
}
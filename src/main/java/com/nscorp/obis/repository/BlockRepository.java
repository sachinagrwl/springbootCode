package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
	List<Block> findByTermIdAndTrainNr(Long termId, String trainNr);

	Block findByTermIdAndTrainNrIgnoreCase(Long termId, String trainNr);

	boolean existsByTermIdAndTrainNr(Long termId, String trainNr);

	boolean existsByBlockId(Long blockId);

	Block findByBlockId(Long blockId);

	void deleteByTermIdAndTrainNr(Long termId, String trainNr);

	void deleteByBlockId(Long blockId);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

	List<Block> findByTermIdAndTrainNr(Long termId, String trainNr, Sort sort);

	boolean existsByBlockNm(String blockNm);

	List<Block> findByTermIdAndTrainNrOrderByBlockOrderAsc(Long termId, String trainNr);

	Block findByBlockOrder(int existingBlockOrder);

}

package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Block;
import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.repository.TerminalTrainRepository;
import com.nscorp.obis.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TerminalTrainServiceImpl implements TerminalTrainService {

	
	
	@Autowired
	private TerminalTrainRepository terminalTrainRepo;
	
	@Autowired
	private BlockRepository blockRepo;

	@Autowired
	private TrainRepository trainRepository;


	@Autowired
	Environment env;

	@Autowired
	DataSource datasource;

	@PersistenceContext
	EntityManager entityManager;
	
	
	/* This Method Is Used To Fetch All Values */
	@Override
	public List<TerminalTrain> getAllTerminalTrains() {
		List<TerminalTrain> terminalTrain= terminalTrainRepo.findAll();
		if(terminalTrain.isEmpty()) {
			throw new NoRecordsFoundException();
		}
		return terminalTrain;
	}


	/**
	 * This Method Is Used To Update TRAIN_NR and TRAIN_DESCm used preparedstatement
	 * 	to update data since, DB has triggers and composite primary key contraints
	 */

	@Override
	public TerminalTrain updateTrainDesc(TerminalTrain trainDescObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
	
			if(!terminalTrainRepo.existsByTermIdAndTrainNr(trainDescObj.getTermId(),trainDescObj.getOldTrainNr())) {
				throw new NoRecordsFoundException("TerminalTrainRepo :: No record Found Under this Term Id:"+trainDescObj.getTermId()+" and Train Nr:"+trainDescObj.getOldTrainNr());
							
			}
		if(!terminalTrainRepo.existsByTermIdAndTrainNr(trainDescObj.getTermId(),trainDescObj.getTrainNr())) {
			String updateQuery = "UPDATE INTERMODAL.TERM_TRAIN set TRAIN_NR=?, TRAIN_DESC=? where TERM_ID=? AND TRAIN_NR=?";
			Connection connection = null;
			try {
				String dbUrl = env.getProperty(CommonConstants.DATASOURCE_URL);
				connection = datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateQuery);

				statement.setString(1, trainDescObj.getTrainNr());
				statement.setString(2, trainDescObj.getTrainDesc());
				statement.setLong(3, trainDescObj.getTermId());
				statement.setString(4, trainDescObj.getOldTrainNr());



				int updateCount = statement.executeUpdate();
				statement.close();
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
				throw new RecordNotDeletedException("DB Connection");
			}
		}
			
			

/**
 * Updating old train number with new train number
 */


          if(trainRepository.existsByTrainNumber(trainDescObj.getTrainNr())) {

			  if (blockRepo.existsByTermIdAndTrainNr(trainDescObj.getTermId(), trainDescObj.getOldTrainNr())) {

				  if (trainDescObj.getOldTrainNr() != null && trainDescObj.getTrainNr() != null) {
					  List<Block> existingBlockDesc = blockRepo.findByTermIdAndTrainNr(trainDescObj.getTermId(), trainDescObj.getOldTrainNr());

					  for (Block exBlock : existingBlockDesc) {

						  exBlock.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						  exBlock.setTrainNr(trainDescObj.getTrainNr());
						  blockRepo.save(exBlock);
					  }

				  }
			  }
		  } else {
			  throw new NoRecordsFoundException("BlockRepo :: No record Found Under this Term Id:"+trainDescObj.getTermId()+" and Train Nr:"+trainDescObj.getOldTrainNr());
		  }

				return trainDescObj;
			}
	
	@Override
	public void deleteTrain(TerminalTrain terminalTrain) {
		// TODO Auto-generated method stub
		if(terminalTrainRepo.existsByTermIdAndTrainNr(terminalTrain.getTermId(),terminalTrain.getTrainNr())) {
			if(blockRepo.existsByTermIdAndTrainNr(terminalTrain.getTermId(),terminalTrain.getTrainNr())) {
			blockRepo.deleteByTermIdAndTrainNr(terminalTrain.getTermId(),terminalTrain.getTrainNr());
			}
			terminalTrainRepo.deleteByTermIdAndTrainNr(terminalTrain.getTermId(),terminalTrain.getTrainNr());
		}else {
			String rep = terminalTrain.getTermId()  + " and " + terminalTrain.getTrainNr() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}
	}
}
		


	


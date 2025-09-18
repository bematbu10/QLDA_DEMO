package com.qlda.sontay.repository.disbursement;

import com.qlda.sontay.domain.disbursement.Disbursement;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisbursementRepository extends MongoRepository<Disbursement, ObjectId> {
    List<Disbursement> findByProjectId(ObjectId projectId);
}

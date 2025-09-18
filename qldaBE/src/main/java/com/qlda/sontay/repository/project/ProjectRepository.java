package com.qlda.sontay.repository.project;

import com.qlda.sontay.domain.project.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    List<Project> findByStatus(String status);
    List<Project> findByManager(String manager);
    List<Project> findByBudgetGreaterThan(Double budget);
    // query native :
//    // Lấy project theo taskStatus
//    @Query("{ 'taskStatus': ?0 }")
//    List<Project> findProjectsByStatus(String taskStatus);
//
//    // Lấy project có budget lớn hơn số cho trước
//    @Query("{ 'budget': { $gt: ?0 } }")
//    List<Project> findProjectsWithBudgetGreaterThan(Double minBudget);
//
//    // Lấy project theo manager và taskStatus
//    @Query("{ 'manager': ?0, 'taskStatus': ?1 }")
//    List<Project> findByManagerAndStatus(String manager, String taskStatus);
//
//    // Tìm project có chứa một contractorCompanyName
//    @Query("{ 'contractorCompanyName': { $in: [?0] } }")
//    List<Project> findByContractorCompanyName(String companyName);
}

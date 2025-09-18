package com.qlda.sontay.domain.project;

import com.qlda.sontay.common.ProjectStatus;
import com.qlda.sontay.domain.base.TimeFrameEntity;
import com.qlda.sontay.domain.embedded_model.BaseDocument;
import com.qlda.sontay.domain.embedded_model.DocumentFolder;
import com.qlda.sontay.domain.project.project_phase.ProjectPhase;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_project")
public class Project extends TimeFrameEntity {

    @Field("name")
    String name;

    @Field("description")
    String description;

    @Field("taskStatus")
    ProjectStatus status;

    @Field("progress")
    Double progress;

    @Field("team_size")
    Integer teamSize;

    @Field("budget")
    Double budget;

    @Field("manager")
    String manager;

    @Field("category")
    String category;

    @Field("location")
    String location;

    // pháp lý đầu tư
    @Field("investment_level")
    String investmentLevel;

    @Field("investment_approval")
    String investmentApproval;

    @Field("project_group")
    String projectGroup;

    @Field("investor")
    String investor;

    @Field("investment_type")
    String investmentType;

    @Field("management_type")
    String managementType;

    @Field("project_scale")
    String projectScale;

    @Field("design_step_count")
    Integer designStepCount;

    @Field("design_capacity")
    String designCapacity;

    @Field("approval_date")
    Date approvalDate;

    @Field("legal_documents")
    List<String> legalDocuments;

    // xây dựng
    @Field("construction_level")
    String constructionLevel;

    @Field("construction_type")
    String constructionType;

    @Field("construction_location")
    String constructionLocation;

    @Field("design_standards")
    String designStandards;

    // hồ sơ & mục tiêu
    @Field("goals")
    String goals;

    @Field("synthetic_method")
    String syntheticMethod;

    @Field("notes")
    String notes;

    // liên kết
    @DocumentReference()
    List<ProjectPhase> phases;

    // gói thầu
    @Field("number_tbmt")
    String numberTBMT;

    @Field("time_execution")
    String timeExecution;

    @Field("contractor_company_name")
    List<String> contractorCompanyName;

    @Field("contractor")
    String contractor;

    @Field("contractor_price")
    Double contractorPrice;

    @Field("related_documents")
    List<BaseDocument> relatedDocuments;

    @Field("role_executor")
    String roleExecutor;

    // nguồn vốn
    @Field("capital_project")
    String capitalProject;

    // lĩnh vực
    @Field("field")
    String field;

    // tài liệu dự án
    @Field("document_folder")
    List<DocumentFolder> documentFolder;

    // milestones
    @Field("milestones")
    List<String> milestones;
}

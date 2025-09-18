package com.qlda.sontay.dto.project;

import com.qlda.sontay.common.ProjectStatus;
import com.qlda.sontay.domain.embedded_model.BaseDocument;
import com.qlda.sontay.domain.embedded_model.DocumentFolder;
import com.qlda.sontay.dto.base.TimeFrameDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDTO extends TimeFrameDTO {

    String id;
    String name;
    String description;
    ProjectStatus status;
    Double progress;

    Integer teamSize;
    Double budget;

    String manager;
    String category;
    String location;

    // pháp lý đầu tư
    String investmentLevel;
    String investmentApproval;
    String projectGroup;
    String investor;
    String investmentType;
    String managementType;
    String projectScale;

    Integer designStepCount;
    String designCapacity;
    Date approvalDate;
    List<String> legalDocuments;

    // xây dựng
    String constructionLevel;
    String constructionType;
    String constructionLocation;
    String designStandards;

    // hồ sơ & mục tiêu
    String goals;
    String syntheticMethod;
    String notes;

    // liên kết (chỉ truyền id để map)
    List<ProjectPhaseDTO> phases;

    // gói thầu
    String numberTBMT;
    String timeExecution;
    List<String> contractorCompanyName;
    String contractor;
    Double contractorPrice;

    // tài liệu liên quan
    List<BaseDocument> relatedDocuments;

    String roleExecutor;
    String capitalProject;
    String field;

    // tài liệu dự án
    List<DocumentFolder> documentFolder;

    // milestones
    List<String> milestones;
}

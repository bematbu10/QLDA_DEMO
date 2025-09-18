package com.qlda.sontay.mapper.disbursement;

import com.qlda.sontay.domain.disbursement.Disbursement;
import com.qlda.sontay.dto.disbusement.DisbursementDTO;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.mapper.base.ObjectIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" ,  uses = {ObjectIdMapper.class })
public interface DisbursementDTOMapper extends EntityMapper<DisbursementDTO, Disbursement> {
    @Override
    @Mapping(source = "id", target = "id")   // map list Phase <-> PhaseDTO
    Disbursement toEntity(DisbursementDTO dto);

    @Override
    @Mapping(source = "id", target = "id")
    DisbursementDTO toDto(Disbursement entity);
}

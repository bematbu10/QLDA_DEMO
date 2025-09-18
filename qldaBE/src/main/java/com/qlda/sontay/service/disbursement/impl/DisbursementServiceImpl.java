package com.qlda.sontay.service.disbursement.impl;

import com.qlda.sontay.domain.disbursement.Disbursement;
import com.qlda.sontay.dto.disbusement.DisbursementDTO;
import com.qlda.sontay.mapper.disbursement.DisbursementDTOMapper;
import com.qlda.sontay.repository.disbursement.DisbursementRepository;
import com.qlda.sontay.service.disbursement.DisbursementService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisbursementServiceImpl implements DisbursementService {

    private final DisbursementRepository disbursementRepository;

    private final DisbursementDTOMapper disbursementDTOMapper;

    @Override
    public DisbursementDTO create(DisbursementDTO dto) {
        Disbursement entity = disbursementDTOMapper.toEntity(dto);
        Disbursement saved = disbursementRepository.save(entity);
        return disbursementDTOMapper.toDto(saved);
    }

    @Override
    public void deleteById(String id) {
        disbursementRepository.deleteById(new ObjectId(id));
    }

    @Override
    public List<DisbursementDTO> findAll() {
        return disbursementRepository.findAll().stream()
            .map(disbursementDTOMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public DisbursementDTO findById(String id) {
        return disbursementRepository.findById(new ObjectId(id))
            .map(disbursementDTOMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Disbursement not found with id: " + id));
    }
}

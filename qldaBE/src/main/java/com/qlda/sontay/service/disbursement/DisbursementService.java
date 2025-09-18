package com.qlda.sontay.service.disbursement;



import com.qlda.sontay.dto.disbusement.DisbursementDTO;

import java.util.List;

public interface DisbursementService {
    DisbursementDTO create(DisbursementDTO dto);

    void deleteById(String id);

    List<DisbursementDTO> findAll();

    DisbursementDTO findById(String id);
}

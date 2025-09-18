package com.qlda.sontay.web.rest.controller;


import com.qlda.sontay.service.project.ProjectPhaseService;
import com.qlda.sontay.service.project.base.ProjectPhaseServiceBase;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import com.qlda.sontay.dto.common.IResponseMessage;
import com.qlda.sontay.dto.common.SuccessResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-phases")
@Slf4j(topic = "PHASE-CONTROLLER")
@Tag(name = "phase Controller")
@RequiredArgsConstructor
public class ProjectPhaseController {

    private final ProjectPhaseService projectPhaseService;

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> create(@RequestBody ProjectPhaseDTO request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(projectPhaseService.create(request)));
    }

    @GetMapping("/getAll")
    ResponseEntity<IResponseMessage> getAll() {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectPhaseService.findAll()));
    }

    @GetMapping("/getPhaseById/{projectPhaseId}")
    ResponseEntity<IResponseMessage> getProjectById(@PathVariable ObjectId projectPhaseId) {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectPhaseService.findById(projectPhaseId)));
    }

    @PutMapping("/update/{projectPhaseId}")
    ResponseEntity<IResponseMessage> update(@RequestBody ProjectPhaseDTO request, @PathVariable ObjectId projectPhaseId) {
        return ResponseEntity.ok().body(
            SuccessResponseMessage.UpdatedSuccess(projectPhaseService.update(projectPhaseId, request))
        );
    }

    @DeleteMapping("/delete/{projectPhaseId}")
    ResponseEntity<IResponseMessage> delete(@PathVariable ObjectId projectPhaseId) {
        return ResponseEntity.ok().body(
            SuccessResponseMessage.DeletedSuccess(projectPhaseService.delete(projectPhaseId))
        );
    }
}

package com.qlda.sontay.web.rest.controller;


import com.qlda.sontay.service.project.ProjectService;
import com.qlda.sontay.dto.common.IResponseMessage;
import com.qlda.sontay.dto.common.SuccessResponseMessage;
import com.qlda.sontay.dto.project.ProjectDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@Slf4j(topic = "PROJECT-CONTROLLER")
@Tag(name = "project Controller")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> create(@RequestBody ProjectDTO request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(projectService.create(request)));
    }

    @GetMapping("/getAll")
    ResponseEntity<IResponseMessage> getAll() {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectService.findAll()));
    }

    @GetMapping("/getProject/{projectId}")
    ResponseEntity<IResponseMessage> getProjectById(@PathVariable ObjectId projectId) {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectService.findById(projectId)));
    }

    @PutMapping("/update/{projectId}")
    ResponseEntity<IResponseMessage> update(@RequestBody ProjectDTO request, @PathVariable ObjectId projectId) {
        return ResponseEntity.ok().body(
            SuccessResponseMessage.UpdatedSuccess(projectService.update(projectId, request))
        );
    }

    @DeleteMapping("/delete/{projectId}")
    ResponseEntity<IResponseMessage> delete(@PathVariable ObjectId projectId) {
        return ResponseEntity.ok().body(
            SuccessResponseMessage.DeletedSuccess(projectService.delete(projectId))
        );
    }
}

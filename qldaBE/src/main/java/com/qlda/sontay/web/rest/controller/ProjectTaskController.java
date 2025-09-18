package com.qlda.sontay.web.rest.controller;


import com.qlda.sontay.service.project.ProjectTaskService;
import com.qlda.sontay.dto.project.ProjectTaskDTO;
import com.qlda.sontay.dto.common.IResponseMessage;
import com.qlda.sontay.dto.common.SuccessResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-tasks")
@Slf4j(topic = "TASK-CONTROLLER")
@Tag(name = "task Controller")
@RequiredArgsConstructor
public class ProjectTaskController {

    private final ProjectTaskService projectTaskService;

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> create(@RequestBody ProjectTaskDTO request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(projectTaskService.create(request)));
    }

    @GetMapping("/getAll")
    ResponseEntity<IResponseMessage> getAll() {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectTaskService.findAll()));
    }

    @GetMapping("/getTaskById/{projectTaskId}")
    ResponseEntity<IResponseMessage> getProjectById(@PathVariable ObjectId projectTaskId) {
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(projectTaskService.findById(projectTaskId)));
    }

//    @PutMapping("/update/{projectId}")
//    ResponseEntity<IResponseMessage> update(@RequestBody ProjectDTO request, @PathVariable ObjectId projectId) {
//        return ResponseEntity.ok().body(
//            SuccessResponseMessage.UpdatedSuccess(projectTaskService.update(projectId, request))
//        );
//    }

    @DeleteMapping("/delete/{projectTaskId}")
    ResponseEntity<IResponseMessage> delete(@PathVariable ObjectId projectTaskId) {
        return ResponseEntity.ok().body(
            SuccessResponseMessage.DeletedSuccess(projectTaskService.delete(projectTaskId))
        );
    }
}

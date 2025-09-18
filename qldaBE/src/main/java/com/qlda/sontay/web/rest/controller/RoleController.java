package com.qlda.sontay.web.rest.controller;


import com.qlda.sontay.service.auth.RoleService;
import com.qlda.sontay.dto.common.IResponseMessage;
import com.qlda.sontay.dto.common.SuccessResponseMessage;
import com.qlda.sontay.dto.auth.request.RoleRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@Slf4j(topic = "ROLE-CONTROLLER")
@Tag(name = "role Controller")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> create(@RequestBody RoleRequest request){
        return  ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(roleService.create(request)));
    }

    @GetMapping
    ResponseEntity<IResponseMessage> getAll(){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(roleService.getAll()));
    }

    @DeleteMapping("/{roleId}")
    ResponseEntity<IResponseMessage> delete(@PathVariable String roleId) {
        return ResponseEntity.ok().body(
                SuccessResponseMessage.DeletedSuccess(roleService.delete(roleId))
        );
    }
}

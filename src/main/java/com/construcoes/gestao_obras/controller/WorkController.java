package com.construcoes.gestao_obras.controller;

import com.construcoes.gestao_obras.controller.dto.WorkRequest;
import com.construcoes.gestao_obras.controller.dto.WorkResponse;
import com.construcoes.gestao_obras.entities.User;
import com.construcoes.gestao_obras.entities.Work;
import com.construcoes.gestao_obras.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableMethodSecurity
public class WorkController {

    @Autowired
    private WorkRepository workRepository;

    @PostMapping("/{userId}/work")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<WorkResponse> createWork(@PathVariable UUID userId, @RequestBody WorkRequest createWorkRequest){

        User user = new User();
        user.setUserId(userId);

        Work work = new Work();
        work.setName(createWorkRequest.name());
        work.setLocation(createWorkRequest.location());
        work.setUser(user);

        workRepository.save(work);

        return ResponseEntity.ok(new WorkResponse(
                work.getWorkId(),
                work.getName(),
                work.getLocation(),
                work.getUser().getUserId()
        ));

    }

    @GetMapping("/{userId}/work")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<WorkResponse>> listWorkByUser(@PathVariable UUID userId){
        User user = new User();
        user.setUserId(userId);
        List<WorkResponse> listWork = workRepository.findByUser(user)
                .stream()
                .map(work -> new WorkResponse(
                        work.getWorkId(),
                        work.getName(),
                        work.getLocation(),
                        work.getUser().getUserId()
                ))
                .collect(Collectors.toList());


        return ResponseEntity.ok(listWork);
    }

    @DeleteMapping("/{userId}/work")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<HttpStatusCode> deleteWork(@RequestBody WorkRequest workRequest){

        workRepository.deleteById(workRequest.workId());
        return ResponseEntity.ok(HttpStatusCode.valueOf(200));

    }

    @PutMapping("/{userId}/work")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<WorkResponse> updateWork(@PathVariable UUID userId, @RequestBody WorkRequest workRequest){

        User user = new User();
        user.setUserId(userId);
        Work work = workRepository.saveAndFlush(new Work(workRequest.workId(), workRequest.name(), workRequest.location(), user));

        return ResponseEntity.ok(new WorkResponse(
                work.getWorkId(),
                work.getName(),
                work.getLocation(),
                work.getUser().getUserId()
        ));

    }
}

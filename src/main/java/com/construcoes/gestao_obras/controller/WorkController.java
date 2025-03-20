package com.construcoes.gestao_obras.controller;

import com.construcoes.gestao_obras.controller.dto.CreateWorkRequest;
import com.construcoes.gestao_obras.controller.dto.CreateWorkResponse;
import com.construcoes.gestao_obras.controller.dto.ListWorkResponse;
import com.construcoes.gestao_obras.entities.User;
import com.construcoes.gestao_obras.entities.Work;
import com.construcoes.gestao_obras.repository.WorkRepository;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<CreateWorkResponse> createWork(@PathVariable UUID userId, @RequestBody CreateWorkRequest createWorkRequest){

        User user = new User();
        user.setUserId(userId);

        Work work = new Work();
        work.setName(createWorkRequest.name());
        work.setLocation(createWorkRequest.location());
        work.setUser(user);

        workRepository.save(work);

        return ResponseEntity.ok(new CreateWorkResponse(
                work.getWorkId(),
                work.getName(),
                work.getLocation(),
                work.getUser().getUserId()
        ));

    }

    @GetMapping("/{userId}/work")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<ListWorkResponse>> listWorkByUser(@PathVariable UUID userId){
        User user = new User();
        user.setUserId(userId);
        List<ListWorkResponse> listWork = workRepository.findByUser(user)
                .stream()
                .map(work -> new ListWorkResponse(
                        work.getWorkId(),
                        work.getName(),
                        work.getLocation(),
                        work.getUser().getUserId()
                ))
                .collect(Collectors.toList());


        return ResponseEntity.ok(listWork);
    }
}

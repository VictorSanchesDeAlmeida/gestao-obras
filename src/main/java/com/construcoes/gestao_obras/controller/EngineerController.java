package com.construcoes.gestao_obras.controller;

import com.construcoes.gestao_obras.controller.dto.EngineerRequest;
import com.construcoes.gestao_obras.controller.dto.EngineerResponse;
import com.construcoes.gestao_obras.entities.Contract;
import com.construcoes.gestao_obras.entities.Engineer;
import com.construcoes.gestao_obras.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@EnableMethodSecurity
public class EngineerController {

    @Autowired
    private EngineerRepository engineerRepository;

    @PostMapping("/{userId}/work/{workId}/contract/{contractId}/engineer")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EngineerResponse> createEngineer(
            @PathVariable UUID userId, @PathVariable Long workId, @PathVariable Long contractId,
            @RequestBody EngineerRequest engineerRequest,
            @AuthenticationPrincipal Jwt jwt
            ){

        UUID authenticatedUserId = UUID.fromString(jwt.getClaimAsString("userId"));

        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }

        Contract contract = new Contract();
        contract.setContractId(contractId);

        Engineer engineer = new Engineer();
        engineer.setName(engineerRequest.name());
        engineer.setContract(contract);

        engineer = engineerRepository.save(engineer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{engineerId}")
                .buildAndExpand(engineer.getEngineerId())
                .toUri();

        return ResponseEntity.created(location).body(new EngineerResponse(
                engineer.getEngineerId(),
                engineer.getName(),
                engineer.getContract().getContractId()
        ));

    }

}

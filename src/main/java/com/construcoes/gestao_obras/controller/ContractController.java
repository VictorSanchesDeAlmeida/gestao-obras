package com.construcoes.gestao_obras.controller;

import com.construcoes.gestao_obras.controller.dto.ContractRequest;
import com.construcoes.gestao_obras.controller.dto.ContractResponse;
import com.construcoes.gestao_obras.entities.Contract;
import com.construcoes.gestao_obras.entities.User;
import com.construcoes.gestao_obras.entities.Work;
import com.construcoes.gestao_obras.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@EnableMethodSecurity
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @PostMapping("/{userId}/work/{workId}/contract")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ContractResponse> createContract(@PathVariable UUID userId, @PathVariable Long workId, @RequestBody ContractRequest contractRequest){

        User user = new User();
        user.setUserId(userId);

        Work work = new Work();
        work.setWorkId(workId);
        work.setUser(user);

        Contract contract = new Contract();
        contract.setTitle(contractRequest.title());
        contract.setWork(work);

        contract = contractRepository.save(contract);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{contractId}")
                .buildAndExpand(contract.getContractId())
                .toUri();

        return ResponseEntity.created(location).body(new ContractResponse(
                contract.getContractId(),
                contract.getTitle(),
                contract.getWork().getWorkId()
        ));

    }

    @GetMapping("/{userId}/work/{workId}/contract")
    public ResponseEntity<List<ContractResponse>> listContractsByWork(@PathVariable UUID userId, @PathVariable Long workId, @AuthenticationPrincipal Jwt jwt){

        UUID authenticatedUserId = UUID.fromString(jwt.getClaimAsString("userId"));

        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }

        Work work = new Work();
        work.setWorkId(workId);

        List<ContractResponse> listContracts = contractRepository.findByWork(work)
                .stream()
                .map(contract -> new ContractResponse(
                        contract.getContractId(),
                        contract.getTitle(),
                        contract.getWork().getWorkId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listContracts);
    }

    @DeleteMapping("/{userId}/work/{workId}/contract")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteContract(@PathVariable UUID userId, @RequestBody ContractRequest contractRequest, @AuthenticationPrincipal Jwt jwt){
        UUID authenticatedUserId = UUID.fromString(jwt.getClaimAsString("userId"));

        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }

        contractRepository.deleteById(contractRequest.contractId());

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{userId}/work/{workId}/contract")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ContractResponse> updateContract(
            @PathVariable UUID userId,
            @PathVariable Long workId,
            @RequestBody ContractRequest contractRequest,
            @AuthenticationPrincipal Jwt jwt){

        UUID authenticatedUserId = UUID.fromString(jwt.getClaimAsString("userId"));

        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }


        Work work = new Work();
        work.setWorkId(workId);

        Contract contract = contractRepository.saveAndFlush(new Contract(contractRequest.contractId(), contractRequest.title(), work));

        return ResponseEntity.ok(new ContractResponse(
                contract.getContractId(),
                contract.getTitle(),
                contract.getWork().getWorkId()
        ));

    }

}

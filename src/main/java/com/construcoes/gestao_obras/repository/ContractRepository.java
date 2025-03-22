package com.construcoes.gestao_obras.repository;

import com.construcoes.gestao_obras.entities.Contract;
import com.construcoes.gestao_obras.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByWork(Work work);
}

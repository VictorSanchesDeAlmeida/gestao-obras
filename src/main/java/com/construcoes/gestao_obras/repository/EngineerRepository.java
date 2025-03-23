package com.construcoes.gestao_obras.repository;

import com.construcoes.gestao_obras.entities.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {
}

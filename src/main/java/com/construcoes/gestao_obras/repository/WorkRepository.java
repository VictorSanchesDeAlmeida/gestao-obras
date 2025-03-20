package com.construcoes.gestao_obras.repository;

import com.construcoes.gestao_obras.entities.User;
import com.construcoes.gestao_obras.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByUser(User user);
}

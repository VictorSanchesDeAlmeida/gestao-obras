package com.construcoes.gestao_obras.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long workId;

    private String name;

    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

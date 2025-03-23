package com.construcoes.gestao_obras.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_engineers")
public class Engineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engineer_id")
    private Long engineerId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Engineer(){}

    public Engineer(Long engineerId, String name, Contract contract) {
        this.engineerId = engineerId;
        this.name = name;
        this.contract = contract;
    }

    public Long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Long engineerId) {
        this.engineerId = engineerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}

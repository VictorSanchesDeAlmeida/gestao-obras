package com.construcoes.gestao_obras.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;


    private String title;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    public Contract(){}

    public Contract(Long contractId, String title, Work work){
        this.contractId = contractId;
        this.title = title;
        this.work = work;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}

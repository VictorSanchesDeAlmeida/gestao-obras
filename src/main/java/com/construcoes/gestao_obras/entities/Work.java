package com.construcoes.gestao_obras.entities;

import jakarta.persistence.*;

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

    public User getUser() {
        return user;
    }

    public Work(){}

    public Work(Long workId, String name, String location, User user){
        this.workId = workId;
        this.name = name;
        this.location = location;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

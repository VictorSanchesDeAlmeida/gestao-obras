package com.construcoes.gestao_obras.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public enum Values{
        ADMIN,
        BASIC;

        public static Role.Values fromString(String name) {
            for (Role.Values value : Role.Values.values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Role not found: " + name);
        }
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

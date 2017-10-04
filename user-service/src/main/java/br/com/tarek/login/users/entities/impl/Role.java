package br.com.tarek.login.users.entities.impl;

import br.com.tarek.login.users.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    private static final long serialVersionUID = -5864143261223299849L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String role;

    /**
     * Construtor Vazio
     */
    public Role() {
    }

    /**
     * Construtor para testes
     *
     * @param role
     */
    public Role(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}

package br.com.tarek.login.profiles.entities.impl;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "profiles")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column
    @NotEmpty(message = "Please inform your business name")
    private String name;

    @Column(name = "business_type")
    private String businessType;

    @Column
    private String website;

    /**
     * Construtor padrão
     */
    public Profile() {
    }

    public Profile(String name, String businessType, String website) {
        this.name = name;
        this.businessType = businessType;
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", businessType='" + businessType + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

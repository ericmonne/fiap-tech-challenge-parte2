package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype;

import java.util.Date;

public class JdbcUserTypeEntity {

    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private Date created;
    private Date update;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(final Date update) {
        this.update = update;
    }
}

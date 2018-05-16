package com.ninjaCorporation.Changeset.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Tenant")
@Access(AccessType.FIELD)
@SequenceGenerator(name = "idgen", allocationSize = 1, initialValue = 1, sequenceName = "Tenant_seq")
public class Tenant extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, length = 37)
    private String uuid;
    
    @Column(nullable = false, length = 250)
    private String name;
    
    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "tenant")
    private List<User> users = new ArrayList<>();

    public Tenant() {
        uuid = generateUuid();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

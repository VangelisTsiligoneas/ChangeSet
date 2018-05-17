package com.ninjaCorporation.Changeset.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "User")
@Access(AccessType.FIELD)
@SequenceGenerator(name = "idgen", allocationSize = 1, initialValue = 1, sequenceName = "User_seq")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, length = 250)
    private String username;

    @ManyToOne
    @JoinColumn(name = "tenant")
    private Tenant tenant;
    
    @OneToMany(mappedBy = "user")
    private List<Changeset> changesets = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<Changeset> getChangesets() {
        return changesets;
    }

    public void setChangesets(List<Changeset> changesets) {
        this.changesets = changesets;
    }
}

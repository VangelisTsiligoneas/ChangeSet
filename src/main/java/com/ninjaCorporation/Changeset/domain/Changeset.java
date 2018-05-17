package com.ninjaCorporation.Changeset.domain;

import com.ninjaCorporation.Changeset.utils.TimeUtils;
import java.sql.Timestamp;
import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Changeset")
@Access(AccessType.FIELD)
@SequenceGenerator(name = "idgen", allocationSize = 1, initialValue = 1, sequenceName = "Changeset_seq")
public class Changeset extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private Timestamp dateCreated;

    @Column
    private Timestamp dateModified;

    @Column(length = 100000)
    private String data;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Changeset() {
        dateCreated = TimeUtils.getCurrentTimestamp();
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

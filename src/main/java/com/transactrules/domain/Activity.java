package com.transactrules.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private Instant dateTime;

    @ManyToOne
    @JsonIgnoreProperties("activities")
    private Behaviour behaviour;

    @ManyToOne
    @JsonIgnoreProperties("activities")
    private Employee employee;

    @ManyToMany(mappedBy = "activities")
    @JsonIgnore
    private Set<AchievementProgress> achievementProgresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public Activity dateTime(Instant dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public Activity behaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
        return this;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Activity employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<AchievementProgress> getAchievementProgresses() {
        return achievementProgresses;
    }

    public Activity achievementProgresses(Set<AchievementProgress> achievementProgresses) {
        this.achievementProgresses = achievementProgresses;
        return this;
    }

    public Activity addAchievementProgress(AchievementProgress achievementProgress) {
        this.achievementProgresses.add(achievementProgress);
        achievementProgress.getActivities().add(this);
        return this;
    }

    public Activity removeAchievementProgress(AchievementProgress achievementProgress) {
        this.achievementProgresses.remove(achievementProgress);
        achievementProgress.getActivities().remove(this);
        return this;
    }

    public void setAchievementProgresses(Set<AchievementProgress> achievementProgresses) {
        this.achievementProgresses = achievementProgresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}

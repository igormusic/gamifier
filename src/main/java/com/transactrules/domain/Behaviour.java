package com.transactrules.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Behaviour.
 */
@Entity
@Table(name = "behaviour")
public class Behaviour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "points", nullable = false)
    private Integer points;

    @OneToMany(mappedBy = "behaviour")
    private Set<AchievementBehaviour> achievements = new HashSet<>();

    @OneToMany(mappedBy = "behaviour")
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Behaviour name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public Behaviour points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Set<AchievementBehaviour> getAchievements() {
        return achievements;
    }

    public Behaviour achievements(Set<AchievementBehaviour> achievementBehaviours) {
        this.achievements = achievementBehaviours;
        return this;
    }

    public Behaviour addAchievement(AchievementBehaviour achievementBehaviour) {
        this.achievements.add(achievementBehaviour);
        achievementBehaviour.setBehaviour(this);
        return this;
    }

    public Behaviour removeAchievement(AchievementBehaviour achievementBehaviour) {
        this.achievements.remove(achievementBehaviour);
        achievementBehaviour.setBehaviour(null);
        return this;
    }

    public void setAchievements(Set<AchievementBehaviour> achievementBehaviours) {
        this.achievements = achievementBehaviours;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Behaviour activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Behaviour addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setBehaviour(this);
        return this;
    }

    public Behaviour removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setBehaviour(null);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Behaviour)) {
            return false;
        }
        return id != null && id.equals(((Behaviour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Behaviour{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", points=" + getPoints() +
            "}";
    }
}

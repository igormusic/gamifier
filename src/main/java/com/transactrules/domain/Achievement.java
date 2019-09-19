package com.transactrules.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Achievement.
 */
@Entity
@Table(name = "achievement")
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "achievement")
    private Set<AchievementBehaviour> behaviours = new HashSet<>();

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

    public Achievement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AchievementBehaviour> getBehaviours() {
        return behaviours;
    }

    public Achievement behaviours(Set<AchievementBehaviour> achievementBehaviours) {
        this.behaviours = achievementBehaviours;
        return this;
    }

    public Achievement addBehaviour(AchievementBehaviour achievementBehaviour) {
        this.behaviours.add(achievementBehaviour);
        achievementBehaviour.setAchievement(this);
        return this;
    }

    public Achievement removeBehaviour(AchievementBehaviour achievementBehaviour) {
        this.behaviours.remove(achievementBehaviour);
        achievementBehaviour.setAchievement(null);
        return this;
    }

    public void setBehaviours(Set<AchievementBehaviour> achievementBehaviours) {
        this.behaviours = achievementBehaviours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Achievement)) {
            return false;
        }
        return id != null && id.equals(((Achievement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Achievement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

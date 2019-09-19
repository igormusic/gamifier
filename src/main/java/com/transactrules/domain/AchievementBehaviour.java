package com.transactrules.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AchievementBehaviour.
 */
@Entity
@Table(name = "achievement_behaviour")
public class AchievementBehaviour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "times", nullable = false)
    private Integer times;

    @ManyToOne
    @JsonIgnoreProperties("behaviours")
    private Achievement achievement;

    @ManyToOne
    @JsonIgnoreProperties("achievements")
    private Behaviour behaviour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public AchievementBehaviour times(Integer times) {
        this.times = times;
        return this;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public AchievementBehaviour achievement(Achievement achievement) {
        this.achievement = achievement;
        return this;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public AchievementBehaviour behaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
        return this;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AchievementBehaviour)) {
            return false;
        }
        return id != null && id.equals(((AchievementBehaviour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AchievementBehaviour{" +
            "id=" + getId() +
            ", times=" + getTimes() +
            "}";
    }
}

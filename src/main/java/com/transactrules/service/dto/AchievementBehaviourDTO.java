package com.transactrules.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.transactrules.domain.AchievementBehaviour} entity.
 */
public class AchievementBehaviourDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer times;


    private Long achievementId;

    private String achievementName;

    private Long behaviourId;

    private String behaviourName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Long achievementId) {
        this.achievementId = achievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public Long getBehaviourId() {
        return behaviourId;
    }

    public void setBehaviourId(Long behaviourId) {
        this.behaviourId = behaviourId;
    }

    public String getBehaviourName() {
        return behaviourName;
    }

    public void setBehaviourName(String behaviourName) {
        this.behaviourName = behaviourName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AchievementBehaviourDTO achievementBehaviourDTO = (AchievementBehaviourDTO) o;
        if (achievementBehaviourDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), achievementBehaviourDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AchievementBehaviourDTO{" +
            "id=" + getId() +
            ", times=" + getTimes() +
            ", achievement=" + getAchievementId() +
            ", achievement='" + getAchievementName() + "'" +
            ", behaviour=" + getBehaviourId() +
            ", behaviour='" + getBehaviourName() + "'" +
            "}";
    }
}

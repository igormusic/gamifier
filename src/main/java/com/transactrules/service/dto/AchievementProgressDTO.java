package com.transactrules.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.transactrules.domain.enumeration.AchievementStatus;

/**
 * A DTO for the {@link com.transactrules.domain.AchievementProgress} entity.
 */
public class AchievementProgressDTO implements Serializable {

    private Long id;

    private AchievementStatus status;


    private Set<ActivityDTO> activities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AchievementStatus getStatus() {
        return status;
    }

    public void setStatus(AchievementStatus status) {
        this.status = status;
    }

    public Set<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityDTO> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AchievementProgressDTO achievementProgressDTO = (AchievementProgressDTO) o;
        if (achievementProgressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), achievementProgressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AchievementProgressDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

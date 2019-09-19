package com.transactrules.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.transactrules.domain.enumeration.AchievementStatus;

/**
 * A AchievementProgress.
 */
@Entity
@Table(name = "achievement_progress")
public class AchievementProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AchievementStatus status;

    @ManyToMany
    @JoinTable(name = "achievement_progress_activity",
               joinColumns = @JoinColumn(name = "achievement_progress_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"))
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AchievementStatus getStatus() {
        return status;
    }

    public AchievementProgress status(AchievementStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AchievementStatus status) {
        this.status = status;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public AchievementProgress activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public AchievementProgress addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getAchievementProgresses().add(this);
        return this;
    }

    public AchievementProgress removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getAchievementProgresses().remove(this);
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
        if (!(o instanceof AchievementProgress)) {
            return false;
        }
        return id != null && id.equals(((AchievementProgress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AchievementProgress{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

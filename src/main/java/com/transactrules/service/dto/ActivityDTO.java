package com.transactrules.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.transactrules.domain.Activity} entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    private Instant dateTime;


    private Long behaviourId;

    private String behaviourName;

    private Long employeeId;

    private String employeeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if (activityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", behaviour=" + getBehaviourId() +
            ", behaviour='" + getBehaviourName() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeName() + "'" +
            "}";
    }
}

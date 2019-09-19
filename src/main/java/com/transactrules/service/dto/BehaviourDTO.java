package com.transactrules.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.transactrules.domain.Behaviour} entity.
 */
public class BehaviourDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer points;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BehaviourDTO behaviourDTO = (BehaviourDTO) o;
        if (behaviourDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), behaviourDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BehaviourDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", points=" + getPoints() +
            "}";
    }
}

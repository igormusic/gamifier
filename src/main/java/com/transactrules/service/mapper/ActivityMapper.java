package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {BehaviourMapper.class, EmployeeMapper.class})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {

    @Mapping(source = "behaviour.id", target = "behaviourId")
    @Mapping(source = "behaviour.name", target = "behaviourName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.name", target = "employeeName")
    ActivityDTO toDto(Activity activity);

    @Mapping(source = "behaviourId", target = "behaviour")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(target = "achievementProgresses", ignore = true)
    @Mapping(target = "removeAchievementProgress", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}

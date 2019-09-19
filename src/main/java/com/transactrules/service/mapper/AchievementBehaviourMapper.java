package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.AchievementBehaviourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AchievementBehaviour} and its DTO {@link AchievementBehaviourDTO}.
 */
@Mapper(componentModel = "spring", uses = {AchievementMapper.class, BehaviourMapper.class})
public interface AchievementBehaviourMapper extends EntityMapper<AchievementBehaviourDTO, AchievementBehaviour> {

    @Mapping(source = "achievement.id", target = "achievementId")
    @Mapping(source = "achievement.name", target = "achievementName")
    @Mapping(source = "behaviour.id", target = "behaviourId")
    @Mapping(source = "behaviour.name", target = "behaviourName")
    AchievementBehaviourDTO toDto(AchievementBehaviour achievementBehaviour);

    @Mapping(source = "achievementId", target = "achievement")
    @Mapping(source = "behaviourId", target = "behaviour")
    AchievementBehaviour toEntity(AchievementBehaviourDTO achievementBehaviourDTO);

    default AchievementBehaviour fromId(Long id) {
        if (id == null) {
            return null;
        }
        AchievementBehaviour achievementBehaviour = new AchievementBehaviour();
        achievementBehaviour.setId(id);
        return achievementBehaviour;
    }
}

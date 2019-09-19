package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.AchievementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Achievement} and its DTO {@link AchievementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AchievementMapper extends EntityMapper<AchievementDTO, Achievement> {


    @Mapping(target = "behaviours", ignore = true)
    @Mapping(target = "removeBehaviour", ignore = true)
    Achievement toEntity(AchievementDTO achievementDTO);

    default Achievement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        achievement.setId(id);
        return achievement;
    }
}

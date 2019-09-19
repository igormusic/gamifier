package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.AchievementProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AchievementProgress} and its DTO {@link AchievementProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class})
public interface AchievementProgressMapper extends EntityMapper<AchievementProgressDTO, AchievementProgress> {


    @Mapping(target = "removeActivity", ignore = true)

    default AchievementProgress fromId(Long id) {
        if (id == null) {
            return null;
        }
        AchievementProgress achievementProgress = new AchievementProgress();
        achievementProgress.setId(id);
        return achievementProgress;
    }
}

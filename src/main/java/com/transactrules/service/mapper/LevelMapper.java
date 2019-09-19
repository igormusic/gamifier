package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.LevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Level} and its DTO {@link LevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {



    default Level fromId(Long id) {
        if (id == null) {
            return null;
        }
        Level level = new Level();
        level.setId(id);
        return level;
    }
}

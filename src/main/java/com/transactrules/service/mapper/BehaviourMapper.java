package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.BehaviourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Behaviour} and its DTO {@link BehaviourDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BehaviourMapper extends EntityMapper<BehaviourDTO, Behaviour> {


    @Mapping(target = "achievements", ignore = true)
    @Mapping(target = "removeAchievement", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivity", ignore = true)
    Behaviour toEntity(BehaviourDTO behaviourDTO);

    default Behaviour fromId(Long id) {
        if (id == null) {
            return null;
        }
        Behaviour behaviour = new Behaviour();
        behaviour.setId(id);
        return behaviour;
    }
}

package com.transactrules.service.mapper;

import com.transactrules.domain.*;
import com.transactrules.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    EmployeeDTO toDto(Employee employee);

    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivity", ignore = true)
    @Mapping(source = "teamId", target = "team")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}

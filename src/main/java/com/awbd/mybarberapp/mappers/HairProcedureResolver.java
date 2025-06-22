package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.services.HairProcedureService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HairProcedureResolver {

    private final HairProcedureService service;

    public HairProcedureResolver(HairProcedureService service) {
        this.service = service;
    }

    public List<HairProcedure> toEntityList(List<Long> ids) {
        return ids.stream()
                .map(service::findById) // presupune că ai `HairProcedure findById(Long id)`
                .collect(Collectors.toList());
    }

    public List<Long> toIdList(List<HairProcedure> entities) {
        return entities.stream()
                .map(HairProcedure::getId)
                .collect(Collectors.toList());
    }
}

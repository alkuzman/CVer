package com.cver.team.persistence.jena.objectMappers.dataObjectMappers.dateObjectMappers;

import com.cver.team.model.data.date.DateOfFoundation;
import com.cver.team.persistence.jena.objectMappers.dataObjectMappers.DateObjectMapper;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by PC on 25/08/2016.
 */
@Component
public class DateOfFoundationObjectMapper {
    @Autowired
    DateObjectMapper dateObjectMapper;

    public DateOfFoundation generateDate(Model model, Resource resource) {
        DateOfFoundation dateOfFoundation = new DateOfFoundation();
        dateOfFoundation = dateObjectMapper.generateDate(model, resource, dateOfFoundation);

        return dateOfFoundation;
    }
}
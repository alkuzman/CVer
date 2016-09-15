package com.cver.team.services;

import com.cver.team.model.entity.Entity;

import java.util.List;

/**
 * Created by PC on 16/08/2016.
 */
public interface EntityService {
    List<Entity> query(String query, String type, String owner, String memberOf, String isWatchedBy, String owns, Integer offset, Integer limit);

    List<String> autocomplete(String query, String type, String owner, String memberOf, String isWatchedBy, String owns, Integer limit);

    List<String> types(String query, String type, String owner, String memberOf, String isWatchedBy, String owns, Integer limit);
}

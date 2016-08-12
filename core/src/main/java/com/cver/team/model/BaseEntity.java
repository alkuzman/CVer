package com.cver.team.model;

import com.cver.team.model.literal.Identifier;
import com.cver.team.model.externalresource.tag.Tag;

/**
 * This class is hold just for identification of objects among their classes. The main usage of this object
 * is in relational databases.
 *
 * @author CVerTeam
 * @version 1.0
 * @since 1/17/2016
 */
public class BaseEntity {
    private Identifier identifier;
    private Tag tag;
    private String type;


    public BaseEntity(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseEntity() {
        type = getClass().getSimpleName();
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "identifier=" + identifier +
                ", type='" + type + '\'' +
                '}';
    }
}
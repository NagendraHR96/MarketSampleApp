package com.neory.marketsimplifieddemoapp.ui.model;

import java.io.Serializable;

public class JsonObjectResult implements Serializable {
    private String name;
    private String description;
    private Owner owner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}

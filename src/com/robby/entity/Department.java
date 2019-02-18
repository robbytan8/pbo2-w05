package com.robby.entity;

import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Robby
 */
public class Department implements Serializable {

    private static final long serialVersionUID = 20171L;

    private int id;
    private String code;
    private String name;
    private final BooleanProperty statusProperty = new SimpleBooleanProperty();

    public Department() {
        statusProperty.setValue(Boolean.TRUE);
    }

    public Department(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isStatus() {
        return statusProperty.get();
    }

    public BooleanProperty getStatusProperty() {
        return statusProperty;
    }

    private void setStatus(boolean value) {
        statusProperty.set(value);
    }

    private BooleanProperty statusProperty() {
        return statusProperty;
    }

    @Override
    public String toString() {
        return getName();
    }

}

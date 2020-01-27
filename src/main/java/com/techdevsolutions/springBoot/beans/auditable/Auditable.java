package com.techdevsolutions.springBoot.beans.auditable;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class Auditable implements Serializable {
    private Integer id = 0;
    private String name = "";
    private String createdBy = "";
    private String updatedBy = "";
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
    private Boolean removed = false;

    public Auditable() {
    }

    public Auditable(Integer id, String name, String createdBy, String updatedBy, ZonedDateTime createdDate,
                     ZonedDateTime updatedDate, Boolean removed) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.removed = removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditable auditable = (Auditable) o;
        return Objects.equals(id, auditable.id) &&
                Objects.equals(name, auditable.name) &&
                Objects.equals(createdBy, auditable.createdBy) &&
                Objects.equals(updatedBy, auditable.updatedBy) &&
                Objects.equals(createdDate, auditable.createdDate) &&
                Objects.equals(updatedDate, auditable.updatedDate) &&
                Objects.equals(removed, auditable.removed);
    }

    @Override
    public String toString() {
        return "Auditable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", removed=" + removed +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdBy, updatedBy, createdDate, updatedDate, removed);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }
}

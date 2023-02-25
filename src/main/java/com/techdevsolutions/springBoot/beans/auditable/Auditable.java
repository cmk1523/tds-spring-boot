package com.techdevsolutions.springBoot.beans.auditable;

import java.time.ZonedDateTime;

public class Auditable {
    private String id; // UUID

    private String createdBy = "";
    private String updatedBy = "";
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
    private Boolean removed = false;

    public Auditable() {
    }

    public Auditable(String id, String name, String createdBy, String updatedBy, ZonedDateTime createdDate,
                     ZonedDateTime updatedDate, Boolean removed) {
        this.id = id;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.removed = removed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

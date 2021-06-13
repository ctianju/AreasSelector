package com.mobile.areasselectapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 地区实体
 */
public class AreaEntity implements Serializable {
    public String areaId;
    public String idPath;
    public String areaName;
    public String parentId;
    private ArrayList<AreaEntity> children;

    public AreaEntity(AreaEntity ae) {
        this.areaId = ae.getAreaId();
        this.idPath = ae.getIdPath();
        this.areaName = ae.getAreaName();
        this.parentId = ae.getParentId();
        this.children = ae.getChildren();
    }

    public AreaEntity(String areaId, String idPath, String areaName, String parentId, ArrayList<AreaEntity> children) {
        this.areaId = areaId;
        this.idPath = idPath;
        this.areaName = areaName;
        this.parentId = parentId;
        this.children = children;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ArrayList<AreaEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<AreaEntity> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "AreaEntity{" +
                "areaId='" + areaId + '\'' +
                ", idPath='" + idPath + '\'' +
                ", areaName='" + areaName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", children=" + children +
                '}';
    }
}

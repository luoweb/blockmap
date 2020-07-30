package com.nowcoder.community.entity;
//总机构表
public class Institution {   //机构表
    private int id;
    private String institutionname;  //机构名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return institutionname;
    }

    public void setName(String name) {
        this.institutionname = institutionname;
    }
    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", institutionname='" + institutionname + '\'' +
                '}';
    }

}

package org.icbc.dataAccess.dto;
//总机构表
public class InstitutionDto {
	private int id;
    private String institutionname;  //机构名称
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInstitutionname() {
		return institutionname;
	}
	public void setInstitutionname(String institutionname) {
		this.institutionname = institutionname;
	}
    
    
}

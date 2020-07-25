package org.icbc.actions.subinstitutionRequestActions;

import org.icbc.bussinessService.ISubinstitutionService;
import org.icbc.dataAccess.dto.SubinstitutionDto;

public class AddSubinstitutionAction {
	private int id;
    private String subinstitutionname;
    private int institutionId;
    private String profession;
    private String province;
    private String city;
    private String district;
    private String street; 
    private String detailAddress;
    private float lon;
    private float lat;
    private int riskLevel;
    private ISubinstitutionService subinstitutionService;
    public String execute() {
    	SubinstitutionDto subinstitutionDto = new SubinstitutionDto();
    	subinstitutionDto.setSubinstitutionname(subinstitutionname);
    	subinstitutionDto.setInstitutionId(institutionId);
    	subinstitutionDto.setProfession(profession);
    	subinstitutionDto.setProvince(province);
    	subinstitutionDto.setCity(city);
    	subinstitutionDto.setDistrict(district);
    	subinstitutionDto.setStreet(street);
    	subinstitutionDto.setDetailAddress(detailAddress);
    	subinstitutionDto.setLon(lon);
    	subinstitutionDto.setLat(lat);
    	subinstitutionDto.setRiskLevel(riskLevel);
    	subinstitutionService.addNewSubinstitution(subinstitutionDto);
    	return "success";
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubinstitutionname() {
		return subinstitutionname;
	}
	public void setSubinstitutionname(String subinstitutionname) {
		this.subinstitutionname = subinstitutionname;
	}
	public int getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public int getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}
	public ISubinstitutionService getSubinstitutionService() {
		return subinstitutionService;
	}
	public void setSubinstitutionService(ISubinstitutionService subinstitutionService) {
		this.subinstitutionService = subinstitutionService;
	}
    
}

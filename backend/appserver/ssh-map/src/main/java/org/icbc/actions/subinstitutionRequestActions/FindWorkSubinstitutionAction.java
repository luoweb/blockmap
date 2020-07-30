package org.icbc.actions.subinstitutionRequestActions;

import java.util.List;

import org.icbc.bussinessService.ISubinstitutionService;
import org.icbc.dataAccess.dto.SubinstitutionDto;

public class FindWorkSubinstitutionAction {
	private int page;
	private int limit;
	private List<SubinstitutionDto> subinstitutions;
	private ISubinstitutionService subinstitutionService;
	public String execute() {
		subinstitutions = subinstitutionService.selectWorkSubinstitution(page, limit);
		if(subinstitutions == null || subinstitutions.size() ==0) {
			return "fail";
		}
		else {
			return "success";
		}
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List<SubinstitutionDto> getSubinstitutions() {
		return subinstitutions;
	}
	public void setSubinstitutions(List<SubinstitutionDto> subinstitutions) {
		this.subinstitutions = subinstitutions;
	}
	public ISubinstitutionService getSubinstitutionService() {
		return subinstitutionService;
	}
	public void setSubinstitutionService(ISubinstitutionService subinstitutionService) {
		this.subinstitutionService = subinstitutionService;
	}
	
}

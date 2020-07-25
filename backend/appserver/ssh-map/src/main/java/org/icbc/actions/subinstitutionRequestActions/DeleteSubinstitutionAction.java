package org.icbc.actions.subinstitutionRequestActions;

import org.icbc.bussinessService.ISubinstitutionService;
import org.icbc.dataAccess.dto.SubinstitutionDto;

public class DeleteSubinstitutionAction {
	private long id;
	private ISubinstitutionService subinstitutionService;
	public String execute() {
		SubinstitutionDto subinstitution = subinstitutionService.getSubinstitution(id);
		if(subinstitution == null) {
			return "fail";
		}
		else {
			subinstitutionService.deleteSubinstitution(subinstitution);
			return "success";
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ISubinstitutionService getSubinstitutionService() {
		return subinstitutionService;
	}
	public void setSubinstitutionService(ISubinstitutionService subinstitutionService) {
		this.subinstitutionService = subinstitutionService;
	}
	
}

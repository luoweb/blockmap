package org.icbc.actions.subinstitutionRequestActions;

import org.icbc.bussinessService.ISubinstitutionService;
import org.icbc.dataAccess.dto.SubinstitutionDto;

public class UpdateRiskLevelAction {
	private long id;
	private int risklevel;
	private ISubinstitutionService subinstitutionService;
	public String execute() {
		SubinstitutionDto subinstitutionDto = subinstitutionService.getSubinstitution(id);
		if(subinstitutionDto == null) {
			return "fail";
		}else {
			subinstitutionDto.setRiskLevel(risklevel);
			subinstitutionService.updateSubinstitution(subinstitutionDto);
			return "success";
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRisklevel() {
		return risklevel;
	}
	public void setRisklevel(int risklevel) {
		this.risklevel = risklevel;
	}
	public ISubinstitutionService getSubinstitutionService() {
		return subinstitutionService;
	}
	public void setSubinstitutionService(ISubinstitutionService subinstitutionService) {
		this.subinstitutionService = subinstitutionService;
	}
	
}

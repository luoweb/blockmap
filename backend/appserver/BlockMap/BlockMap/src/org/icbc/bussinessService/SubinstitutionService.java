package com.nowcoder.community.service;

import com.nowcoder.community.dao.SubinstitutionMapper;
import com.nowcoder.community.entity.Subinstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubinstitutionService {
     @Autowired
    SubinstitutionMapper subinstitutionMapper;
    public List<Subinstitution> findWorkSubinstitution(int page,int limit){   //复工查询，查询可以复工的企业，传入页码和最多显示页数
        int offset=(page-1)*limit;
       return subinstitutionMapper.selectWorkSubinstitution(offset,limit);
    }
    public List<Subinstitution> selectSubinstitutionByName(String name){
        return subinstitutionMapper.selectSubinstitution(name);
    }
    public int deletSubinstitutionById(int id){
        return subinstitutionMapper.deletSubinstitution(id);
    }
    public int addSubinstitution(Subinstitution subinstitution){
        return subinstitutionMapper.addSubinstitution(subinstitution);
    }
    public int updateInstitutionRiskLevel(int id,int riskLevel){
        return subinstitutionMapper.updateSubinstitution(id,riskLevel);
    }
}

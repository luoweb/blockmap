package org.icbc.dataAccess.dao;

import org.icbc.dataAccess.dto.ConfirmInformationPostDto;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ConfirmInformationPostDaoImpl extends HibernateDaoSupport implements IConfirmInformationPostDao{

	public boolean testHibernateTemplate(){
        if(getHibernateTemplate() == null)return false;
        return true;
    }
	@Override
	public ConfirmInformationPostDto getById(Long id) {
		// TODO Auto-generated method stub
		return (ConfirmInformationPostDto) getHibernateTemplate().get(ConfirmInformationPostDto.class, id);
	}

	@Override
	public void delete(ConfirmInformationPostDto confirm) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(confirm);
	}

	@Override
	public void save(ConfirmInformationPostDto confirm) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(confirm);
	}

	@Override
	public void update(ConfirmInformationPostDto confirm) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(confirm);
	}

	@Override
	public void saveOrUpdate(ConfirmInformationPostDto confirm) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(confirm);
	}

}

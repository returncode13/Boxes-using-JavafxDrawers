/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.VariableArgumentDAO;
import db.dao.VariableArgumentDAOImpl;
import db.model.VariableArgument;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VariableArgumentServiceImpl implements VariableArgumentService{
    
    VariableArgumentDAO vaDao=new VariableArgumentDAOImpl();
    
    @Override
    public void createVariableArgument(VariableArgument va) {
        vaDao.createVariableArgument(va);
    }

    @Override
    public VariableArgument getVariableArgument(Long vaid) {
        return vaDao.getVariableArgument(vaid);
    }

    @Override
    public void updateVariableArgument(Long vaid, VariableArgument newVa) {
        vaDao.updateVariableArgument(vaid, newVa);
    }

    @Override
    public void deleteVariableArgument(Long vaid) {
        vaDao.deleteVariableArgument(vaid);
    }
    
}

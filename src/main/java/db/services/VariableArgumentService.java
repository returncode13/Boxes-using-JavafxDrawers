/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.VariableArgument;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface VariableArgumentService {
    public void createVariableArgument(VariableArgument va);
    public VariableArgument getVariableArgument(Long vaid);
    public void updateVariableArgument(Long vaid,VariableArgument newVa);
    public void deleteVariableArgument(Long vaid);
    
}

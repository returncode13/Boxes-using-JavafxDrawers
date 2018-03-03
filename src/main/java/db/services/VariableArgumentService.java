/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Dot;
import db.model.VariableArgument;
import db.model.Workspace;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface VariableArgumentService {
    public void createVariableArgument(VariableArgument va);
    public VariableArgument getVariableArgument(Long vaid);
    public void updateVariableArgument(Long vaid,VariableArgument newVa);
    public void deleteVariableArgument(Long vaid);

    public List<VariableArgument> getVariableArgumentsForDot(Dot dbDot);
     public List<VariableArgument> getVariableArgumentsForWorkspace(Workspace w);
}

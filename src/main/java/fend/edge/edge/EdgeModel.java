/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.edge;

import fend.dot.DotModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface EdgeModel {
    public DotModel getDotModel();
    public Long getId();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
    
}

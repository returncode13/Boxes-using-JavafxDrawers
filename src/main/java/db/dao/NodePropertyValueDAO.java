/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface NodePropertyValueDAO {
    public void createNodePropertyValue(NodePropertyValue npv);
    public NodePropertyValue getNodePropertyValue(Long npvid);
    public void updateNodePropertyValue(Long npvid,NodePropertyValue newNpv);
    public void deleteNodePropertyValue(Long npvid);
    
    public List<NodePropertyValue> getNodePropertyValuesFor(Job job);

    public NodePropertyValue getNodePropertyValuesFor(Job jobStep, NodeProperty nodeProperty);

    public void removeAllNodePropertyValuesFor(Job job);

    public void updateCoordinateXforJob(Job job, double x);

    public void updateCoordinateYforJob(Job job, double y);

    public List<NodePropertyValue> getNodePropertyXYvaluesForWorkspace(Workspace workspace);

    public NodePropertyValue getNodePropertyValueFor(Job job, String propname);
}

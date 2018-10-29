/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;


import db.dao.NodePropertyValueDAO;
import db.dao.NodePropertyValueDAOImpl;
import db.model.Job;
import db.model.NodeProperty;

import db.model.NodePropertyValue;
import db.model.Workspace;

import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class NodePropertyValueServiceImpl implements NodePropertyValueService{

    NodePropertyValueDAO npvdao=new NodePropertyValueDAOImpl();
    
    
    @Override
    public void createNodePropertyValue(NodePropertyValue npv) {
        npvdao.createNodePropertyValue(npv);
    }

    @Override
    public NodePropertyValue getNodePropertyValue(Long npvid) {
        return npvdao.getNodePropertyValue(npvid);
    }

    @Override
    public void updateNodePropertyValue(Long npvid, NodePropertyValue newNpv) {
        npvdao.updateNodePropertyValue(npvid, newNpv);
    }

    @Override
    public void deleteNodePropertyValue(Long npvid) {
        npvdao.deleteNodePropertyValue(npvid);
    }

    @Override
    public List<NodePropertyValue> getNodePropertyValuesFor(Job job) {
        return npvdao.getNodePropertyValuesFor(job);
    }

    @Override
    public NodePropertyValue getNodePropertyValueFor(Job jobStep, NodeProperty nodeProperty) {
        return npvdao.getNodePropertyValuesFor(jobStep,nodeProperty);
    }

    @Override
    public void removeAllNodePropertyValuesFor(Job job) {
        npvdao.removeAllNodePropertyValuesFor(job);
    }

    @Override
    public void updateCoordinateXforJob(Job job, double x) {
        npvdao.updateCoordinateXforJob(job, x);
    }

    @Override
    public void updateCoordinateYforJob(Job job, double y) {
        npvdao.updateCoordinateYforJob(job, y);
    }

    @Override
    public List<NodePropertyValue> getNodePropertyXYvaluesForWorkspace(Workspace workspace) {
        return npvdao.getNodePropertyXYvaluesForWorkspace(workspace);
    }

    @Override
    public NodePropertyValue getNodePropertyValueFor(Job job, String propname) {
        return npvdao.getNodePropertyValueFor(job,propname);
    }
    
    
   
    
  
    
}

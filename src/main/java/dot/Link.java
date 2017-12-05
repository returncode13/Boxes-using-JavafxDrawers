/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dot;

import job.job0.JobType0Model;



/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
class Link {
    JobType0Model parent;
    JobType0Model child;

    public JobType0Model getParent() {
        return parent;
    }

    public void setParent(JobType0Model parent) {
        this.parent = parent;
    }

    public JobType0Model getChild() {
        return child;
    }

    public void setChild(JobType0Model child) {
        this.child = child;
    }
    
    
}

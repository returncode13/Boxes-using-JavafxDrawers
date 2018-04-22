/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.properties;

import fend.job.job0.property.properties.JobType0Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * This class only holds property names pertaining to type 4. 
 * 
 */
public class JobType4Properties implements JobType0Properties{
    List<String> properties=new ArrayList<>();

    public JobType4Properties() {
        this.properties.add("from");
        this.properties.add("to");
        this.properties.add("x");
        this.properties.add("y");
    }

    public List<String> getProperties() {
        return properties;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0.property;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Class holds properties of job steps
 * Holds the name,value of a single Property. e.g. "From"  or "To" or "X" or "Y"
 */
public class JobModelProperty {
    String propertyName;
    String propertyValue=new String();

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    
}

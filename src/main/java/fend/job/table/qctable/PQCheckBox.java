/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PQCheckBox extends CheckBox{

    
    /**
     * Toggles the state of the {@code CheckBox}. If allowIndeterminate is
     * true, then each invocation of this function will advance the CheckBox
     * through the states checked, undefined, and unchecked. If
     * allowIndeterminate is false, then the CheckBox will only cycle through
     * the checked and unchecked states, and forcing indeterminate to equal to
     * false.
     */
    
    @Override
    public void fire() {
        super.fire(); 
        if(!isDisabled()){
            
            if(isAllowIndeterminate()){
                 if(!isSelected() && !isIndeterminate()){
                    setIndeterminate(true);
                }else if(isIndeterminate()){
                    setSelected(true);
                    setIndeterminate(false);
                }else if(isSelected() && !isIndeterminate()){
                    setSelected(false);
                    setIndeterminate(false);
                }
            }else{
                setSelected(!isSelected());
                setIndeterminate(false);
            }
            fireEvent(new ActionEvent());
            
           
        }
    }
    
}

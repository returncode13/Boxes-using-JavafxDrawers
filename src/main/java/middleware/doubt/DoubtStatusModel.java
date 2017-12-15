/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.doubt;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusModel {
    public static final String YES="Y";
    public static final String OVERRIDE="O";
    public static String getNewDoubtMessage(String function,Double tolerance,Double error,Double evaluated,Double lhs,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.TRACES)){
            message=doubttype+": the RHS of the function "+function+" evaluated to "+evaluated+" with tolerance: "+tolerance+" and error: "+error;
        }
        
        return message;
    } 
}

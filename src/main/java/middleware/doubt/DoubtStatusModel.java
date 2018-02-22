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
    
    public static final String GOOD="GOOD";
    public static final String ERROR="ERROR";
    public static final String WARNING="WARNING";
    
    public static String getNewDoubtTraceMessage(String function,Double tolerance,Double error,Double evaluated,Double lhs,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.TRACES)){
            message=doubttype+": the RHS of the function "+function+" evaluated to "+evaluated+" with tolerance: "+tolerance+" and error: "+error;
        }
        if(doubttype.equals(DoubtTypeModel.QC)){
            message=doubttype+": one or more of the qcs in the parent job "+function+" has failed in the child: ";
        }
        
        return message;
    } 
    
    
    
    
    
    
    public static String getNewDoubtTimeMessage(String parentJob,String parentTime,String childJob ,String childTime,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.TIME)){
            message=doubttype+": for line "+sub+"the parentjob "+parentJob+"  was run at a later time  "+parentTime+" while the childjob: "+childJob+" was run earlier at "+childTime;
        }
        
        return message;
    } 
    
     public static String getNewDoubtQCcessage(String parentJob,String  childJob,String sub,String doubttype){
        String message=new String();
        
        if(doubttype.equals(DoubtTypeModel.QC)){
            message=doubttype+": line "+sub+" in the parent job: "+parentJob+" has failed one or more QC causing this doubt in the childjob "+childJob;
        }
        
        return message;
    } 

    public static String getNewDoubtInsightMessage(String parentnameJobStep, String sub,String insightVersionsInParentJob, String insightVersionInParentHeader, String childnameJobStep0,String doubttype) {
        String message=new String();
         if(doubttype.equals(DoubtTypeModel.INSIGHT)){
            message=doubttype+": line: "+sub+" was run with Insight Version "+insightVersionInParentHeader+" which was not amongst the list of Insight versions declared for the job: "+parentnameJobStep+" \ninsight Versions: "+insightVersionsInParentJob;
        }
        
        return message;
    }

    public static String getNewDoubtInheritanceMessage(String parent, String subsurface, String child, String doubttype) {
        String message=new String();
         if(doubttype.equals(DoubtTypeModel.INHERIT)){
            message=doubttype+": line: "+subsurface+" in job "+child+" has inherited doubt(s) from its parent "+parent;
        }
        
        return message;
    }
}

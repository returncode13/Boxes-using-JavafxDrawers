/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.doubt;

import db.model.Doubt;
import db.model.Job;
import java.util.List;

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
            message=doubttype+": the RHS of the function "+function+" evaluated ("+evaluated+")  > error("+error+") > tolerance: ("+tolerance+")";
        }
        if(doubttype.equals(DoubtTypeModel.QC)){
            message=doubttype+": one or more of the qcs in the parent job "+function+" has failed in the child: ";
        }
        
        return message;
    } 
    
    public static String getTraceDependencyWarningMessage(String function,Double tolerance,Double error,Double evaluated,Double lhs,String doubttype){
        String message=new String();
        message=doubttype+": the RHS of the function "+function+" evaluated("+evaluated+") < tolerance("+tolerance+") < error ("+error+")";
        return message;
    }
    
     public static String getTraceDependencyPassedMessage(String parentJob,String childJob ,String delta , String tolerance,String error,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.TRACES)){
            message=doubttype+": Passed Trace Dependency for line "+sub+"the jobs "+parentJob+" ->  childjob: "+childJob+"  delta("+delta+") <=  tolerance ("+tolerance+")  < error ("+error+")";
        }
        return message;
    }
    
    public static String getTimeDependencyPassedMessage(String parentJob,String parentTime,String childJob ,String childTime,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.TIME)){
            message=doubttype+": Passed Time Dependency for line "+sub+" the jobs "+parentJob+" ->  childjob: "+childJob+" times ["+parentTime+","+childTime+"]";
        }
        return message;
    }
    
     public static String getQcDependencyPassedMessage(String parentJob,String childJob ,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.QC)){
            message=doubttype+": Passed Qc Dependency for line "+sub+" the jobs "+parentJob+" ->  childjob: "+childJob+" ";
        }
        return message;
    }
     
      public static String getInsightDependencyPassedMessage(String parentJob,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.INSIGHT)){
            message=doubttype+": Passed Insight Dependency for line "+sub+" the jobs "+parentJob+" ";
        }
        return message;
    }
    
      
       public static String getIODependencyPassedMessage(Job child,String sub,String doubttype){
        String message=new String();
        if(doubttype.equals(DoubtTypeModel.IO)){
            message=doubttype+": Passed Insight Dependency for line "+sub+" the jobs "+child.getNameJobStep()+" ";
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
     
     public static String getNew2DoubtQCmessage(String parentJob,String sub,String doubttype){
        String message=new String();
        
        if(doubttype.equals(DoubtTypeModel.QC)){
            message=doubttype+": line "+sub+" in the job: "+parentJob+" has failed one or more QC";
        }
        
        return message;
    } 

    public static String getNewDoubtInsightMessage(String parentnameJobStep, String sub,String insightVersionsInParentJob, String insightVersionInParentHeader,String doubttype) {
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
    
    
    public static String getInheritanceMessage(Job desc,Doubt cause){
        String message=new String();
        message= cause.getDoubtType().getName()+" : line : "+cause.getSubsurface().getSubsurface()+" : in job : "+desc.getNameJobStep()+""
                + " has inherited doubt(s) from its parent : "+cause.getChildJob().getNameJobStep();
                return message;
    }
    
    public static String getIOMessage(String sub,Job child,Job parent,List<String> inputVolsToChild,List<String> parentVolumes,String doubttype){
        String message=doubttype+": line: "+sub+" in child job: "+child.getNameJobStep()+" had the following input volume(s) "+inputVolsToChild.toString()+" \n "
                + " while in parent job "+parent.getNameJobStep()+" has volumes "+parentVolumes.toString();
        return message;
    }
}

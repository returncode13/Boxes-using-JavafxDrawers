/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.properties;

import db.model.User;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath
 */
public class AppProperties {
    public static String DEVELOPER_MODE="DEVELOPER";
    public static String OFFICE_MODE="OFFICE";
    public static String PRODUCTION_MODE="PRODUCTION";
    public static final String VERSION="Internal-36.0.0";             //www.semver.org MAJOR.MINOR.PATCH
                                                            /*
                                                                    1. MAJOR version when you make incompatible API changes
                                                                    2. MINOR version when you add functionality in a backwards-compatible manner.
                                                                    3. PATCH version when you make backwards-compatible bug fixes
                                                            */
    
    //public static final String INSIGHT_LOCATION="/d/sw/Insight";
    private static  String INSIGHT_LOCATION="/home/sharath/programming/obpmanager/dummy";
    
    
    public static  String MODE=DEVELOPER_MODE;
    public static final String TIMESTAMP_FORMAT="yyyyMMddHHmmss";
    //public static final Boolean INSTALL=false;
    private static String project=new String("no project selected");
    
    private String workspaceName=new String("unknown session");
    private String irdbHost=new String("no host assigned");
    private static User currentUser;
        
    public static int BULK_TRANSACTION_BATCH_SIZE=50;                            //control for batch processing.   see persistence.xml. the two values HAVE to be the same
    public static double PERCENTAGE_OF_PROCESSORS_USED=0.5;                 // 0<p<=1 control for percentage of processors used.
    
    
    public static int APPLICATION_PORT_ON_LOCAL=5434;                       //port to be bound on localhost
    public static int APPLICATION_PORT_ON_REMOTE=5432;                      //irdb host port that is been used by postgres
    public static final String URLTEMPLATE_FOR_DATABASE_LISTING="jdbc:postgresql://localhost:"+AppProperties.APPLICATION_PORT_ON_LOCAL+"/template1";   //for listing the databases
    final public static String PROJECT_URL="jdbc:postgresql://localhost:"+AppProperties.APPLICATION_PORT_ON_LOCAL+"/";  //for appending to the chosen database
    public final static String DATABASE_USER="fgeo";
    public final static double TIME_FOR_GUEST_QUERY=60;                   //in  secs
   
    
    public final static Integer LOG_RECURSION_COUNTER=10;                   //the number of times to recursively check for logs . A hack for the race condition until a better solution is found
    
    public static String getProject() {
        return project;
    }

    public static void setProject(String project) {
        AppProperties.project = project;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String WorkspaceName) {
        this.workspaceName = WorkspaceName;
    }

    public String getIrdbHost() {
        return irdbHost;
    }

    public void setIrdbHost(String irdbHost) {
        this.irdbHost = irdbHost;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AppProperties.currentUser = currentUser;
    }

    public static String getINSIGHT_LOCATION() {
        if(MODE.equals(DEVELOPER_MODE)){
                INSIGHT_LOCATION="/home/sharath/programming/obpmanager/dummy";
        }else{
            if(MODE.equals(OFFICE_MODE)){
                INSIGHT_LOCATION="/d/sw/Insight";
            }else{
                INSIGHT_LOCATION="/d/sw/Insight";
            }
            
        }
        return INSIGHT_LOCATION;
    }

    public static String getMODE() {
        return MODE;
    }

    public static void setMODE(String MODE) {
        AppProperties.MODE = MODE;
    }

   public static String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
   }
    
    
    
    
}

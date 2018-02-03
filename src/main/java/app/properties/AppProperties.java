/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.properties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath
 */
public class AppProperties {
    public static final String VERSION="8.0.0";             //www.semver.org MAJOR.MINOR.PATCH
                                                            /*
                                                                    1. MAJOR version when you make incompatible API changes
                                                                    2. MINOR version when you add functionality in a backwards-compatible manner.
                                                                    3. PATCH version when you make backwards-compatible bug fixes
                                                            */
    
    //public static final String INSIGHT_LOCATION="/d/sw/Insight";
    public static final String INSIGHT_LOCATION="/home/sharath/programming/obpmanager/dummy";
    public static final String TIMESTAMP_FORMAT="yyyyMMddHHmmss";
    private static String project=new String("no project selected");
    private String sessionName=new String("unknown session");
    private String irdbHost=new String("no host assigned");
    
    
    public String getProject() {
        return project;
    }

    public static void setProject(String project) {
        AppProperties.project = project;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getIrdbHost() {
        return irdbHost;
    }

    public void setIrdbHost(String irdbHost) {
        this.irdbHost = irdbHost;
    }

   

    
    
    
}

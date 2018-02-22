/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.properties;

import db.model.User;

/**
 *
 * @author sharath
 */
public class AppProperties {
    public static final String VERSION="9.2.1";             //www.semver.org MAJOR.MINOR.PATCH
                                                            /*
                                                                    1. MAJOR version when you make incompatible API changes
                                                                    2. MINOR version when you add functionality in a backwards-compatible manner.
                                                                    3. PATCH version when you make backwards-compatible bug fixes
                                                            */
    
    public static final String INSIGHT_LOCATION="/d/sw/Insight";
    //public static final String INSIGHT_LOCATION="/home/sharath/programming/obpmanager/dummy";
    public static final String TIMESTAMP_FORMAT="yyyyMMddHHmmss";
    public static final Boolean INSTALL=false;
    private static String project=new String("no project selected");
    private String workspaceName=new String("unknown session");
    private String irdbHost=new String("no host assigned");
    private static User currentUser;
    
    public String getProject() {
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

   

    
    
    
}

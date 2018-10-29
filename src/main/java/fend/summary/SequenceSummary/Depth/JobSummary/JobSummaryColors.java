/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryColors {
    /**
     * Colors for when the sequence is absent during summary
     **/
    final public static String TIME_NO_SEQ_PRESENT="#FFFFFF";
    final public static String TRACES_NO_SEQ_PRESENT="#FFFFFF";
    final public static String IO_NO_SEQ_PRESENT="#FFFFFF";
    final public static String QC_NO_SEQ_PRESENT="#FFFFFF";
    final public static String INSIGHT_NO_SEQ_PRESENT="#FFFFFF";
    final public static String INHERITANCE_NO_SEQ_PRESENT="#FFFFFF";
    final public static String WORKFLOW_NO_SEQ_PRESENT="#FFFFFF";
     
    /**
     * Colors for when the Sequence/Subsurface is  GOOD during summary
     **/
    final public static String TIME_GOOD="#00E572";
    final public static String TRACES_GOOD="#00E526";
    final public static String IO_GOOD="#26E500";
    final public static String QC_GOOD="#72E500";
    final public static String INSIGHT_GOOD="#BFE500";
    final public static String INHERITANCE_GOOD="#FFFFFF";
    
    /**
     * Colors for when the Sequence/Subsurface is in the WARNING STATE during summary
     **/
    final public static String TIME_WARNING="#A54CFF";
    final public static String TRACES_WARNING="#6A4CFF";
    final public static String IO_WARNING="#4C6AFF";
    final public static String QC_WARNING="#4CA5FF";
    final public static String INSIGHT_WARNING="#4CE1FF";
    final public static String INHERITANCE_WARNING="#FFFFFF";
    
    /**
     * Colors for when the Sequence/Subsurface is in the DOUBT STATE during summary
     **/
    final public static String TIME_DOUBT="#FF7F00";
    final public static String TRACES_DOUBT="#FF2A00";
    final public static String IO_DOUBT="#FF002A";
    final public static String QC_DOUBT="#FF007F";
    final public static String INSIGHT_DOUBT="#FF00D4";
    final public static String INHERITANCE_DOUBT="#000000";
    
    
     /**
     * Colors for when the Sequence/Subsurface is in the INHERITED-DOUBT STATE during summary i.e. the cell has a doubt thats non-overridden (in the doubtful) state
     **/
    final public static String TIME_INHERITED_DOUBT="#FFBF7F";
    final public static String TRACES_INHERITED_DOUBT="#FF947F";
    final public static String IO_INHERITED_DOUBT="#FF7F94";
    final public static String QC_INHERITED_DOUBT="#FF7FBF";
    final public static String INSIGHT_INHERITED_DOUBT="#FF7FE9";
    final public static String INHERITANCE_INHERITED_DOUBT="#444444";
    


      /**
     * Colors for when the Sequence/Subsurface is in the OVERRIDE STATE during summary i.e. the cell has a doubt that is now in the state "OVERRIDE"
     **/
    final public static String TIME_OVERRRIDE="#3F7F5F";
    final public static String TRACES_OVERRRIDE="#3F7F4A";
    final public static String IO_OVERRRIDE="#4A7F37";
    final public static String QC_OVERRRIDE="#5F7F3F";
    final public static String INSIGHT_OVERRRIDE="#747F3F";
    final public static String INHERITANCE_OVERRRIDE="#A0522D";
    
    
     /**
     * Colors for when the Sequence/Subsurface is in the INHERITED-OVERRIDE STATE during summary i.e. the cell has is a descendant of a cell whose doubt was set to "OVERRIDE"
     **/
    final public static String TIME_INHERITED_OVERRRIDE="#99FFCC";
    final public static String TRACES_INHERITED_OVERRRIDE="#99FFAA";
    final public static String IO_INHERITED_OVERRRIDE="#A9FF99";
    final public static String QC_INHERITED_OVERRRIDE="#CCFF99";
    final public static String INSIGHT_INHERITED_OVERRRIDE="#EEFF99";
    final public static String INHERITANCE_INHERITED_OVERRRIDE="#B88E7A";
}

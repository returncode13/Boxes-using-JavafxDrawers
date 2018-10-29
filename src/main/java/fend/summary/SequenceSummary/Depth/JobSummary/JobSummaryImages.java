/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import javafx.scene.image.Image;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * 
 * Credit: <div>Icons made by <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
    
 */
public class JobSummaryImages {
    //time images basic :
    //https://www.flaticon.com/free-icon/stopwatch_139017#term=time&page=1&position=78
    
    private  Image TIME_DOUBT;   
    private  Image TIME_GOOD;
    private  Image TIME_INHERITED_DOUBT;
    private  Image TIME_OVERRIDE;
    private  Image TIME_INHERITED_OVERRIDE; 
    private  Image TIME_WARNING;
    private  Image TIME_NO_SEQ_PRESENT;
    
    //trace images basic:
    // https://www.flaticon.com/free-icon/sound_727202#term=audio%20wave&page=1&position=38
    private  Image TRACE_DOUBT;
    private  Image TRACE_GOOD;
    private  Image TRACE_INHERITED_DOUBT;
    private  Image TRACE_OVERRIDE;
    private  Image TRACE_INHERITED_OVERRIDE;
    private  Image TRACE_WARNING;
    private  Image TRACE_NO_SEQ_PRESENT;
    
    
    //qc images basic: 
    //https://www.flaticon.com/free-icon/success_148767
    private  Image QC_DOUBT;
    private  Image QC_GOOD;
    private  Image QC_INHERITED_DOUBT;
    private  Image QC_OVERRIDE;
    private  Image QC_INHERITED_OVERRIDE;
    private  Image QC_WARNING;
    private  Image QC_NO_SEQ_PRESENT;
    
    //insight images basic:
    //https://www.flaticon.com/free-icon/hexagon_156145#term=hexagon&page=1&position=33
    private  Image INSIGHT_DOUBT;
    private  Image INSIGHT_GOOD;
    private  Image INSIGHT_INHERITED_DOUBT;
    private  Image INSIGHT_OVERRIDE;
    private  Image INSIGHT_INHERITED_OVERRIDE;
    private  Image INSIGHT_WARNING;
    private  Image INSIGHT_NO_SEQ_PRESENT;
    
    
    //io images basic:
    //https://www.flaticon.com/free-icon/input_411062#term=input&page=1&position=20
    private  Image IO_DOUBT;
    private  Image IO_GOOD;
    private  Image IO_INHERITED_DOUBT;
    private  Image IO_OVERRIDE;
    private  Image IO_INHERITED_OVERRIDE;
    private  Image IO_WARNING;
    private  Image IO_NO_SEQ_PRESENT;
    
     //workflow images basic:
    //https://www.flaticon.com/free-icon/input_411062#term=input&page=1&position=20
    private  Image WORKFLOW_DOUBT;
    private  Image WORKFLOW_GOOD;
    private  Image WORKFLOW_INHERITED_DOUBT;
    private  Image WORKFLOW_OVERRIDE;
    private  Image WORKFLOW_INHERITED_OVERRIDE;
    private  Image WORKFLOW_WARNING;
    private  Image WORKFLOW_NO_SEQ_PRESENT;

    public JobSummaryImages() {
    
    //time
    TIME_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/time/time_doubt.png"));   //https://www.flaticon.com/free-icon/stopwatch_139017#term=time&page=1&position=78
    TIME_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/time/time_good.png"));
    TIME_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/time/time_inherited_doubt.png"));
    TIME_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/time/time_override.png"));
    TIME_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/time/time_inherited_override.png")); 
    TIME_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/time/time_warning.png"));
    TIME_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/time/time_no_seq_present.png"));
    
    
    //trace
    TRACE_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_doubt.png"));   //https://www.flaticon.com/free-icon/sound_727202#term=audio%20wave&page=1&position=38
    TRACE_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_good.png"));
    TRACE_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_inherited_doubt.png"));
    TRACE_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_override.png"));
    TRACE_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_inherited_override.png")); 
    TRACE_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_warning.png"));
    TRACE_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/trace/trace_no_seq_present.png"));
    
    //qc
    QC_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_doubt.png")); //https://www.flaticon.com/free-icon/settings_128531#term=cross&page=1&position=42
    QC_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_good.png")); //https://www.flaticon.com/free-icon/success_148767
    QC_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_inherited_doubt.png"));
    QC_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_override.png"));
    QC_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_inherited_override.png")); 
    QC_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_warning.png"));
    QC_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/qc/qc_no_seq_present.png"));
    
    //insight
    INSIGHT_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_doubt.png")); 
    INSIGHT_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_good.png")); 
    INSIGHT_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_inherited_doubt.png"));
    INSIGHT_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_override.png"));
    INSIGHT_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_inherited_override.png")); 
    INSIGHT_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_warning.png"));    //https://www.flaticon.com/free-icon/hexagon_156145#term=hexagon&page=1&position=33
    INSIGHT_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/insight/insight_no_seq_present.png"));
    
    //io
    IO_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/io/io_doubt.png")); //https://www.flaticon.com/free-icon/input_411062#term=input&page=1&position=20
    IO_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/io/io_good.png")); 
    IO_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/io/io_inherited_doubt.png"));
    IO_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/io/io_override.png"));
    IO_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/io/io_inherited_override.png")); 
    IO_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/io/io_warning.png"));    
    IO_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/io/io_no_seq_present.png"));   
    
    
    
    //WORKFLOW
     
    WORKFLOW_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_doubt.png"));   //https://www.flaticon.com/free-icon/diagram_134659#term=structure%20diagram&page=1&position=17
    WORKFLOW_GOOD=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_good.png")); 
    WORKFLOW_INHERITED_DOUBT=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_inherited_doubt.png"));
    WORKFLOW_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_override.png"));
    WORKFLOW_INHERITED_OVERRIDE=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_inherited_override.png")); 
    WORKFLOW_WARNING=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_warning.png"));    
    WORKFLOW_NO_SEQ_PRESENT=new Image(getClass().getResourceAsStream("/icons/summary/workflow/workflow_no_seq_present.png"));
    }

    public Image getWORKFLOW_DOUBT() {
        return WORKFLOW_DOUBT;
    }

    public Image getWORKFLOW_GOOD() {
        return WORKFLOW_GOOD;
    }

    public Image getWORKFLOW_INHERITED_DOUBT() {
        return WORKFLOW_INHERITED_DOUBT;
    }

    public Image getWORKFLOW_OVERRIDE() {
        return WORKFLOW_OVERRIDE;
    }

    public Image getWORKFLOW_INHERITED_OVERRIDE() {
        return WORKFLOW_INHERITED_OVERRIDE;
    }

    public Image getWORKFLOW_WARNING() {
        return WORKFLOW_WARNING;
    }

    public Image getWORKFLOW_NO_SEQ_PRESENT() {
        return WORKFLOW_NO_SEQ_PRESENT;
    }
    
    

    public Image getTIME_DOUBT() {
        return TIME_DOUBT;
    }

    public Image getTIME_GOOD() {
        return TIME_GOOD;
    }

    public Image getTIME_INHERITED_DOUBT() {
        return TIME_INHERITED_DOUBT;
    }

    public Image getTIME_OVERRIDE() {
        return TIME_OVERRIDE;
    }

    public Image getTIME_INHERITED_OVERRIDE() {
        return TIME_INHERITED_OVERRIDE;
    }

    public Image getTIME_WARNING() {
        return TIME_WARNING;
    }

    public Image getTIME_NO_SEQ_PRESENT() {
        return TIME_NO_SEQ_PRESENT;
    }

    public Image getTRACE_DOUBT() {
        return TRACE_DOUBT;
    }

    public Image getTRACE_GOOD() {
        return TRACE_GOOD;
    }

    public Image getTRACE_INHERITED_DOUBT() {
        return TRACE_INHERITED_DOUBT;
    }

    public Image getTRACE_OVERRIDE() {
        return TRACE_OVERRIDE;
    }

    public Image getTRACE_INHERITED_OVERRIDE() {
        return TRACE_INHERITED_OVERRIDE;
    }

    public Image getTRACE_WARNING() {
        return TRACE_WARNING;
    }

    public Image getTRACE_NO_SEQ_PRESENT() {
        return TRACE_NO_SEQ_PRESENT;
    }

    public Image getQC_DOUBT() {
        return QC_DOUBT;
    }

    public Image getQC_GOOD() {
        return QC_GOOD;
    }

    public Image getQC_INHERITED_DOUBT() {
        return QC_INHERITED_DOUBT;
    }

    public Image getQC_OVERRIDE() {
        return QC_OVERRIDE;
    }

    public Image getQC_INHERITED_OVERRIDE() {
        return QC_INHERITED_OVERRIDE;
    }

    public Image getQC_WARNING() {
        return QC_WARNING;
    }

    public Image getQC_NO_SEQ_PRESENT() {
        return QC_NO_SEQ_PRESENT;
    }

    public Image getINSIGHT_DOUBT() {
        return INSIGHT_DOUBT;
    }

    public Image getINSIGHT_GOOD() {
        return INSIGHT_GOOD;
    }

    public Image getINSIGHT_INHERITED_DOUBT() {
        return INSIGHT_INHERITED_DOUBT;
    }

    public Image getINSIGHT_OVERRIDE() {
        return INSIGHT_OVERRIDE;
    }

    public Image getINSIGHT_INHERITED_OVERRIDE() {
        return INSIGHT_INHERITED_OVERRIDE;
    }

    public Image getINSIGHT_WARNING() {
        return INSIGHT_WARNING;
    }

    public Image getINSIGHT_NO_SEQ_PRESENT() {
        return INSIGHT_NO_SEQ_PRESENT;
    }

    public Image getIO_DOUBT() {
        return IO_DOUBT;
    }

    public Image getIO_GOOD() {
        return IO_GOOD;
    }

    public Image getIO_INHERITED_DOUBT() {
        return IO_INHERITED_DOUBT;
    }

    public Image getIO_OVERRIDE() {
        return IO_OVERRIDE;
    }

    public Image getIO_INHERITED_OVERRIDE() {
        return IO_INHERITED_OVERRIDE;
    }

    public Image getIO_WARNING() {
        return IO_WARNING;
    }

    public Image getIO_NO_SEQ_PRESENT() {
        return IO_NO_SEQ_PRESENT;
    }
    
    
    
}

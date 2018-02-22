/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.volume.volume4;

import middleware.sequences.SubsurfaceHeaders;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import fend.job.job0.JobType0Model;

import fend.volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Type 4 Volumes. text files . p190 .ads. scalars
 */
public class Volume4 implements Volume0{
    //private final String LOGPATH="/../../000scratch/logs";                      //location of logs relative to dugio
    private final Long type=4L;
    private Long id;
    private StringProperty name;
    private File volume;
    private JobType0Model parentJob;
    private List<SubsurfaceHeaders> subsurfaces;
    
    public Volume4(JobType0Model parentBox) {
       // id=UUID.randomUUID().getMostSignificantBits();
        id=null;
        this.parentJob = parentBox;
        name=new SimpleStringProperty();
        subsurfaces=new ArrayList<>();
    }
    
    
    @Override
    public Long getType() {
        return this.type;
    }

    @Override
    public StringProperty getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

  

    @Override
    public File getVolume() {
        return this.volume;
    }
    
    @Override
    public void setVolume(File f) {
       this.volume=f;
        //if(WorkspaceModel.DEBUG)System.out.println("volume.volume4.Volume4.setVolume(): found "+f.listFiles(getSubsurfaceTimeStampFilter).length+ " files");
       
        
        
        
        
    }

    @Override
    public JobType0Model getParentJob() {
        return this.parentJob;
    }

  
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

     /**
     * equals() and hashCode()
     */
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.volume);
        hash = 97 * hash + Objects.hashCode(this.parentJob);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Volume4 other = (Volume4) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.volume, other.volume)) {
            return false;
        }
        if (!Objects.equals(this.parentJob, other.parentJob)) {
            return false;
        }
        return true;
    }

   
    /**
     * toString() for JFXListCell bug
     */
     
    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public List<SubsurfaceHeaders> getSubsurfaces() {
        subsurfaces.clear();
        /*
        for(File sub:this.volume.listFiles(getSubsurfaceTimeStampFilter)){
            String s=sub.getName();                                 //2D-subname.0
            String name=s.substring(3, s.indexOf("."));             //subname
           
            SubsurfaceHeaders subsurface=new SubsurfaceHeaders(this);
            subsurface.setSubsurfaceName(name);
             BasicFileAttributes attr=null;
           try {
              attr=Files.readAttributes(Paths.get(this.volume.getAbsolutePath()),BasicFileAttributes.class);
           } catch (IOException ex) {
               Logger.getLogger(Volume4.class.getName()).log(Level.SEVERE, null, ex);
           }
            DateTimeFormatter formatter=DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            DateTime dt=formatter.parseDateTime(attr.creationTime().toString());
            DateTimeFormatter opformat=new DateTimeFormatterBuilder()
              .appendYear(4, 4)
              .appendMonthOfYear(2)
              .appendDayOfMonth(2)
              .appendHourOfDay(2)
              .appendMinuteOfHour(2)
              .appendSecondOfMinute(2)
              .toFormatter();
            //if(WorkspaceModel.DEBUG)System.out.println("volume.volume1.Volume4.setVolume(): found "+sub.getName()+" sn: "+name+" with time "+opformat.print(dt));
            subsurface.setTimeStamp(opformat.print(dt));
            subsurfaces.add(subsurface);
         
       }
        */
        
        
        return this.subsurfaces;
    }
    
    /**
     * Lesser privates
     */
   //final private String SUBSURFACE_SEARCH="*.0";                                //get only these files under the dugio
   //final private FileFilter getSubsurfaceNamesFilter=new WildcardFileFilter(SUBSURFACE_SEARCH);
   
  // final private String SUBSURFACE_TIMESTAMP="^((?!headers).)*idx";             //get the time stamps and the subsurface names  only the .single.idx files. Exclude the headers.single.idx files
  // final Pattern pat=Pattern.compile(SUBSURFACE_TIMESTAMP);
  /* final private FileFilter getSubsurfaceTimeStampFilter=new FileFilter(){
        @Override
        public boolean accept(File pathname) {
            return pat.matcher(pathname.getName()).matches();
        }
       
   };*/

    @Override
    public File getLogFolder() {
        System.out.println("fend.volume.volume4.Volume4.getLogFolder(): returning file pointing to : "+volume.getAbsolutePath());
        
        return volume;
    }
}
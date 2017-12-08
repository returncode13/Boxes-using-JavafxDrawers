/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;

import fend.dot.anchor.AnchorModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.css.SimpleStyleableStringProperty;
import fend.job.job0.JobType0Model;
import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotModel {
    public static final String NJS="N";
    public static final String JOIN="J";
    public static final String SPLIT="S";
    
    private Long id;
    Color color;
    DoubleProperty x=new SimpleDoubleProperty();
    DoubleProperty y=new SimpleDoubleProperty();
    /**
     * ParentSet and ChildSet are used to trip the state of the dot. (join,split,njs)
     */
    Set<JobType0Model> parentSet;
    Set<JobType0Model> childSet;
    Set<LinkModel> setOfLinks;
    
    
    ObservableList<LinkModel> observableLinkList;
    ObservableSet<JobType0Model> parents;
    ObservableSet<JobType0Model> children;
    
    boolean split=false;       //1 Parent --> m Children
    boolean join=false;        //m Parents --> 1 Child
    boolean njs=false;         //1 Parent --> 1 Child
    boolean enableFurtherLinks=true;  
    
    StringProperty status;     //display the status of the Dot on it
    BooleanProperty delete;     //delete this node;
    
    public DotModel(){
        id=UUID.randomUUID().getMostSignificantBits();
        status=new SimpleStringProperty();
        delete=new SimpleBooleanProperty(false);
        setOfLinks=new HashSet<>();
        observableLinkList=FXCollections.observableArrayList(setOfLinks);
        parentSet=new HashSet<>();
        childSet=new HashSet<>();
        
        parents=FXCollections.observableSet(parentSet);
        children=FXCollections.observableSet(childSet);
        
        parents.addListener(new SetChangeListener<JobType0Model>(){
            @Override
            public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
                updateConnectionStatus("Parent");
            }
            
        });
        
        children.addListener(new SetChangeListener<JobType0Model>(){
            @Override
            public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
                updateConnectionStatus("Children");
                
            }
            
        });
        
        
    }   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    public DoubleProperty getX() {
      return x;
    }

    public void setX(DoubleProperty x) {
       this.x = x;
    }

    public DoubleProperty getY() {
        return y;
    }

    public void setY(DoubleProperty y) {
       this.y = y;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }

    public BooleanProperty getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete.set(delete);
    }

    public Set<LinkModel> getSetOfLinks() {
        return setOfLinks;
    }

    public void setSetOfLinks(Set<LinkModel> setOfLinks) {
        this.setOfLinks = setOfLinks;
    }
    
    
    
    
    

    public void addToParents(JobType0Model box) {
        if(!this.parents.contains(box)){
            this.parents.add(box);
        }
    }


    public void addToChildren(JobType0Model box) {
        if(!this.children.contains(box)){
            this.children.add(box);
        }
    }

    public ObservableSet<JobType0Model> getParents() {
        return parents;
    }

    public ObservableSet<JobType0Model> getChildren() {
        return children;
    }

    public boolean isSplit() {
        return split;
    }

    public boolean isJoin() {
        return join;
    }

    public boolean isNjs() {
        return njs;
    }

    public boolean enableFurtherLinks() {
        return enableFurtherLinks;
    }
    
     private void updateConnectionStatus(String from) {
                 if((parents.size()==1) && (children.size()==1)){
                      System.out.println("dot.DotModel.updateConnectionStatus():  from "+from+" setting njs=true ParentSize: "+parents.size()+" ChildrenSize: "+children.size());
                        njs=true;
                        split=false;
                        join=false;
                        enableFurtherLinks=true;
                        status.set(this.NJS);
                        
                        setOfLinks.clear();
                        LinkModel link=new LinkModel();
                        
                        for (Iterator<JobType0Model> iterator = parents.iterator(); iterator.hasNext();) {     //This is a workaround to "get" the first element from a SET :(. no get() implementation in Set.
                         JobType0Model next = iterator.next();
                         link.setParent(next);
                         
                        }
                        for (Iterator<JobType0Model> iterator = children.iterator(); iterator.hasNext();) {
                         JobType0Model next = iterator.next();
                         link.setChild(next);
                        }
                        
                       
                        setOfLinks.add(link);
                    }
                    else if(parents.size()==1 && children.size()>1){
                        System.out.println("dot.DotModel.updateConnectionStatus():  from "+from+" setting split=true ParentSize: "+parents.size()+" ChildrenSize: "+children.size());
                        njs=false;
                        split=true;
                        join=false;
                        enableFurtherLinks=true;
                        status.set(this.SPLIT);
                        setOfLinks.clear();
                            
                            for(JobType0Model child:children){
                                LinkModel link=new LinkModel();
                                 for (Iterator<JobType0Model> iterator = parents.iterator(); iterator.hasNext();) {
                                    JobType0Model next = iterator.next();
                                    link.setParent(next);
                                 }
                                link.setChild(child);
                                setOfLinks.add(link);
                            }
                    }
                    else if(parents.size()>1&& children.size()==1){
                         System.out.println("dot.DotModel.updateConnectionStatus():  from "+from+" setting join=true ParentSize: "+parents.size()+" ChildrenSize: "+children.size());
                        njs=false;
                        split=false;
                        join=true;
                        enableFurtherLinks=true;
                        status.set(this.JOIN);
                        
                        setOfLinks.clear();
                            
                            for(JobType0Model parent:parents){
                                LinkModel link=new LinkModel();
                                link.setParent(parent);
                                for (Iterator<JobType0Model> iterator = children.iterator(); iterator.hasNext();) {
                                    JobType0Model next = iterator.next();
                                   link.setChild(next);
                                 }
                                setOfLinks.add(link);
                            }
                        
                        
                    }else{
                         System.out.println("dot.DotModel.updateConnectionStatus(): from "+from+" Disabling further links ParentSize: "+parents.size()+" ChildrenSize: "+children.size());
                        enableFurtherLinks=false;
                    }
            }
    
}

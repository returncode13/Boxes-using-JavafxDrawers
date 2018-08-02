/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.seq.sub;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSubsurface extends QcTableSequence{
    
   
     private Sequence sequence;
    private Subsurface subsurface;
    private Comment qcComment;
    
    List<QcMatrixRowModelParent> qcmatrix=new ArrayList<>();
    ObservableList<QcMatrixRowModelParent> observableQcMatrix;
    QcTableSequence parent;
    private QcTableService qcTableService=new QcTableServiceImpl();
    private String updateTime=new String();
    ObservableList<StringProperty> changedList=FXCollections.observableArrayList();
    ListProperty<StringProperty> changedListProperty=new SimpleListProperty<>(changedList);
    
    private Boolean isChild=true;
    private Boolean isParent=false;                          //used to update the checkbox states
   
    public boolean updateChildren=false;
    public boolean updateParent=false;
   
    private StringProperty commentProperty=new SimpleStringProperty("");

     @Override
    public StringProperty commentProperty(){
        return this.commentProperty;
    }
    
     @Override
    public String getComment() {
        return commentProperty.get();
    }

    /*  @Override
    public void setComment(String comment) {
    this.commentProperty.set(comment);
    }
    
    @Override
    public void addToComment(String comment) {
    String newcomment=comment+"\n"+this.getComment();
    this.commentProperty.set(newcomment);
    }
    */

     @Override
    public Comment getQcComment() {
        return qcComment;
    }

     @Override
    public void setQcComment(Comment subQcComment) {
        this.qcComment = subQcComment;
        //show the latest comment.
        if(!subQcComment.getComments().isEmpty()){
         String usertimeComment=subQcComment.getComments().split("\n")[0];
            int start=usertimeComment.indexOf(">",usertimeComment.indexOf(">")+1);
        String latestComment=usertimeComment.substring(start+1);
        commentProperty.set(latestComment);
        }else{
            commentProperty.set("");
        }
    }
    
    
    
     
     
     
     
     @Override
     public Boolean isChild() {
        return isChild;
    }

    
     @Override
    public Sequence getSequence() {
        return sequence;
    }

     @Override
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

     @Override
    public Subsurface getSubsurface() {
        return subsurface;
    }

     @Override
    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

     @Override
    public ObservableList<QcMatrixRowModelParent> getQcmatrix() {
        return observableQcMatrix;
    }

     @Override
    public void setQcmatrix(List<QcMatrixRowModelParent> qcmatrix) {
        changedList.clear();
        changedListProperty.clear();
        changedListProperty.unbind();
        
         for(QcMatrixRowModelParent q:qcmatrix){
            QcMatrixRowModelParent nq=new QcMatrixRowModelParent();
            nq.setId(q.getId());
             //System.out.println("loading result for QMid: "+nq.getId()+" Subid: "+subsurface.getId()+" - "+subsurface.getSubsurface());
             try{
                 QcTable qcTableFromDb=qcTableService.getQcTableFor(nq.getId(), subsurface);
                 
                 if(qcTableFromDb==null){
                     nq.setPassQc(q.isPassQc());
                 }else{
                        Boolean result=qcTableFromDb.getResult();
                        if(result==null){
                            nq.setPassQc(QcMatrixRowModelParent.INDETERMINATE);
                        }else if(result){
                            nq.setPassQc(QcMatrixRowModelParent.SELECTED);
                        }else{
                            nq.setPassQc(QcMatrixRowModelParent.UNSELECTED);
                        }
                 }
                 
                 
             }catch(Exception e){
               nq.setPassQc(q.isPassQc());
             }
            
            nq.setCheckedByUser(q.getCheckedByUser());
            nq.setName(q.getName().get());
          
            nq.setQctype(q.getQctype());
            
            
            
            
            StringProperty changed=new SimpleStringProperty();
            changed.bind(nq.passQcProperty());
            changed.addListener(qcTableSelectionChangedListener);
            changedList.add(changed);
            this.qcmatrix.add(nq);
        }
        this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
        changedListProperty=new SimpleListProperty<>(changedList);
        changedListProperty.addListener(listHasChanged);
        horizontalQc();
    }

     @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

     @Override
    public Boolean isParent() {
        return isParent;
    }

     @Override
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

     @Override
    public void setParent(QcTableSequence parent) {
        this.parent = parent;
    }

     @Override
    public QcTableSequence getParent() {
        return parent;
    }
    
     @Override
     public ObservableList<QcTableSequence> getChildren() {
        return parent.getChildren();
    }

     @Override
    public String getUpdateTime() {
        
        return updateTime;
    }
     
    private ChangeListener<String> qcTableSelectionChangedListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            
            //System.out.println("fend.job.table.qctable.seq.sub.QcTableSubsurface.changed(): from "+oldValue+" to "+newValue);
        }
    };
    
     private ListChangeListener<StringProperty> listHasChanged=new ListChangeListener<StringProperty>() {
         @Override
         public void onChanged(ListChangeListener.Change<? extends StringProperty> c) {
             while(c.next()){
                 if(c.wasUpdated()){
                //     System.out.println(".onChanged(): from "+c.getFrom()+" to "+c.getTo()+" was updated");
                 }
             }
         }
     };
     
     
     
    private StringProperty labelProperty=new SimpleStringProperty(QcMatrixRowModelParent.UNSELECTED);
    
    
     @Override
     public StringProperty labelProperty(){
       return labelProperty;
   }
    
     @Override
    public void setLabel(String label){
        labelProperty.set(label);
    }
    
     @Override
    public  String getLabel(){
        return labelProperty.get();
    }
    
     @Override
    public void horizontalQc() {
        int indeterminate=0;
        int selected=0;
        
        for(QcMatrixRowModelParent q:qcmatrix){
            indeterminate+=q.getIndeterminateProperty().get()?1:0;
            selected+=q.getCheckUncheckProperty().get()?1:0;
        }
        
      //   System.out.println("fend.job.table.qctable.seq.QcTableSequence.horizontalQc(): indeterminate: "+indeterminate+" selected: "+selected+" no of qcmatrix: "+qcmatrix.size());
        
        if(indeterminate>0){
            String label=QcMatrixRowModelParent.INDETERMINATE;
            setLabel(label);
        }else if(indeterminate==0 && selected==qcmatrix.size()){
            String label=QcMatrixRowModelParent.SELECTED;
            setLabel(label);
        }else{
            String label=QcMatrixRowModelParent.UNSELECTED;
            setLabel(label);
        }
    }
    
    
    private BooleanProperty refreshTableProperty=new SimpleBooleanProperty(true);
    
    public BooleanProperty refreshTableProperty(){
        return refreshTableProperty;
    }
    
    public void refreshTable(){
        boolean val=refreshTableProperty.get();
        refreshTableProperty.set(!val);
    }
    
    
}

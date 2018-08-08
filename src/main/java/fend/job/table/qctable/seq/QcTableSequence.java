/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.seq;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.User;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSequence  {
    private Sequence sequence;
    private Subsurface subsurface;
    private Comment qcComment;
    List<QcMatrixRowModelParent> qcmatrix=new ArrayList<>();
    ObservableList<QcMatrixRowModelParent> observableQcMatrix;
    ObservableList<QcTableSequence> children;
    List<StringProperty> changedProperty=new ArrayList<>();
    
    private Boolean isChild=false;
    private Boolean isParent=true;                          //used to update the checkbox states
    private String updateTime=new String();
    public boolean updateChildren=false;
    public boolean updateParent=false;
    
    
    private User user;
    protected int childrenHaveComments=0;
    
    private BooleanProperty updateParentStatusProperty=new SimpleBooleanProperty(false);
    
    
    private String commentStack=new String("");
    
    private StringProperty commentProperty=new SimpleStringProperty("");

    public StringProperty commentProperty(){
        return this.commentProperty;
    }
    
    public String getComment() {
        return commentProperty.get();
    }

    /* public void setComment(String comment) {
    this.commentProperty.set(comment);
    }
    
    public void addToComment(String comment) {
    String newcomment=comment+"\n"+this.getComment();
    this.commentProperty.set(newcomment);
    }*/

    public QcTableSequence() {
        redoComments.addListener(CHILDREN_HAVE_COMMENTS);
    }
    
    
    
     public Comment getQcComment() {
        return qcComment;
    }

     
    private BooleanProperty redoComments=new SimpleBooleanProperty(false);
    
    public BooleanProperty redoCommentsProperty(){
        return redoComments;
    } 
     
    public final static  String DELETED="---";
    
    public void setLatestComment(Comment seqQcComment) {
        this.qcComment = seqQcComment;
        
        //show the latest comment.
        if(!seqQcComment.getComments().isEmpty()){
            this.commentStack=seqQcComment.getComments();
            String usertimeComment=seqQcComment.getComments().split("\n")[0];
            int start=usertimeComment.indexOf(">",usertimeComment.indexOf(">")+1);
        String latestComment=usertimeComment.substring(start+1);
        if(latestComment.trim().isEmpty()){
            latestComment=DELETED;
        }
            commentProperty.set(latestComment);
                       
        }else{
            commentProperty.set("");
        }
        
        for(QcTableSequence child:children){
            if(child.hasComments()) {
                redoComments.set(!redoComments.get());
                break;
            }
        }
    }

    
    
    
    
    public String getCommentStack() {
        return commentStack;
    }

    public void setCommentStack(String commentStack) {
        this.commentStack = commentStack;
    }
    
    
    
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Subsurface getSubsurface() {
        if(subsurface==null) return new Subsurface();
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public ObservableList<QcMatrixRowModelParent> getQcmatrix() {
        return observableQcMatrix;
    }

    public void setQcmatrix(List<QcMatrixRowModelParent> qcmatrix,Map<Long, Map<Subsurface, QcTable>> qcmatrixRowSubQcTableMap) {
        
       
        
        for(QcMatrixRowModelParent q:qcmatrix){
            
            if(raceMap.containsKey(q)){
                raceMap.get(q).put(IND, new HashMap<>());
                raceMap.get(q).put(CHK, new HashMap<>());
                raceMap.get(q).put(UNCHK, new HashMap<>());
            }else{
                raceMap.put(q, new HashMap<>());
                raceMap.get(q).put(IND, new HashMap<>());
                raceMap.get(q).put(CHK, new HashMap<>());
                raceMap.get(q).put(UNCHK, new HashMap<>());
            }
            
            QcMatrixRowModelParent nq=new QcMatrixRowModelParent();
            nq.setCheckUncheckProperty(q.getCheckUncheckProperty().get());
            nq.setIndeterminateProperty(q.getIndeterminateProperty().get());
            nq.setCheckedByUser(q.getCheckedByUser());
            nq.setName(q.getName().get());
            nq.setPassQc(q.isPassQc());
            nq.setQctype(q.getQctype());
            nq.setId(q.getId());
            StringProperty changed=new SimpleStringProperty();
            changed.bind(nq.passQcProperty());
            changed.addListener(qcTableSelectionChangedListener);
            changedProperty.add(changed);
            this.qcmatrix.add(nq);
        }
        this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
        childrenHaveComments=0;
        for(QcTableSequence child:children){
           
            child.setQcmatrix(qcmatrix,qcmatrixRowSubQcTableMap);
          
        }
        
        
        /**
         * during loading
         **/
        for(QcMatrixRowModelParent qmr:qcmatrix){
            int indeterminate=0;
            int selected=0;
            int qindex=qcmatrix.indexOf(qmr);
            for(QcTableSequence child:children){
                indeterminate+=child.getQcmatrix().get(qindex).getIndeterminateProperty().get()?1:0;
                selected+=child.getQcmatrix().get(qindex).getCheckUncheckProperty().get()?1:0;
                
            }
            //System.out.println("fend.job.table.qctable.seq.QcTableSequence.setQcmatrix(): indeterminate: "+indeterminate+" selected: "+selected);
            
            if(indeterminate > 0){
                String passQC=QcMatrixRowModelParent.INDETERMINATE;
                
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(true);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }else if(indeterminate==0 && selected==children.size()){
                String passQC=QcMatrixRowModelParent.SELECTED;
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(false);
                this.qcmatrix.get(qindex).getCheckUncheckProperty().set(true);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }else{
                String passQC=QcMatrixRowModelParent.UNSELECTED;
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(false);
                this.qcmatrix.get(qindex).getCheckUncheckProperty().set(false);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }
        }
        horizontalQc();
        
       
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }
    

    
    Map<QcMatrixRowModelParent,Map<String,HashMap<String,User>>> raceMap=new HashMap<>();
    final String IND="ind";
    final String CHK="chk";
    final String UNCHK="uchk";
    
    public Map<QcMatrixRowModelParent,Map<String,HashMap<String,User>>> getRaceMap(){
        
        return raceMap;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public ObservableList<QcTableSequence> getChildren() {
        if(children==null) return children=FXCollections.observableArrayList();
        return children;
    }

    public void setChildren(ObservableList<QcTableSequence> children) {
        this.children = children;
    }

    
    public void addToRaceMap(Boolean res,QcMatrixRowModelParent q,User u,String time){
        
    }
    
    public User getWinnerForQcMatrixFromChildren(QcMatrixRowModelParent q,String winningtime){
         Map<QcMatrixRowModelParent,Map<String,HashMap<String,User>>> temp=new HashMap<>();
         Map<String,Map<String,User>> winners=new HashMap<>();
         winners.put(IND, new HashMap<>());
         winners.put(UNCHK,new HashMap<>());
         winners.put(CHK,new HashMap<>());
         try{
         
        for(QcTableSequence child:children){
            temp=child.getRaceMap();
            if(temp.get(q).get(IND).values().size()>0){
                winners.get(IND).putAll(temp.get(q).get(IND));
            }else if(temp.get(q).get(UNCHK).values().size()>0){
                winners.get(UNCHK).putAll(temp.get(q).get(UNCHK));
            }else{
                winners.get(CHK).putAll(temp.get(q).get(CHK));
            }
            
        }
        
        if(winners.get(IND).values().size()>0){
            Set<String> times=winners.get(IND).keySet();
            List<String> sortedTimes=new ArrayList<>(times);
            Collections.sort(sortedTimes);
            String reqKey=sortedTimes.get(sortedTimes.size()-1);
            winningtime=reqKey;
            return winners.get(IND).get(reqKey);
            
        }else if(winners.get(UNCHK).values().size()>0){
            Set<String> times=winners.get(UNCHK).keySet();
            List<String> sortedTimes=new ArrayList<>(times);
            Collections.sort(sortedTimes);
            String reqKey=sortedTimes.get(sortedTimes.size()-1);
            winningtime=reqKey;
            return winners.get(UNCHK).get(reqKey);
        }else{
             Set<String> times=winners.get(CHK).keySet();
            List<String> sortedTimes=new ArrayList<>(times);
            Collections.sort(sortedTimes);
            String reqKey=sortedTimes.get(sortedTimes.size()-1);
            winningtime=reqKey;
            return winners.get(CHK).get(reqKey);
        }
         }catch(Exception e){
             winningtime=AppProperties.timeNow();
             return AppProperties.getCurrentUser();
         }
    }
    
    

    public QcTableSequence getParent() {
        return this;
    }

    public Boolean isChild() {
        return isChild;
    }

    public Boolean isParent() {
        return isParent;
    }

    public void setParent(QcTableSequence seqtreeroot) {
    }
    
    
    /***
     * Listeners
     */
    
    private ChangeListener<String> qcTableSelectionChangedListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println(".changed(): on SequenceLevel");
        }
    };

    private StringProperty labelProperty=new SimpleStringProperty(QcMatrixRowModelParent.UNSELECTED);
    
    public String getLabel(){
        return labelProperty.get();
    }
    
    public void setLabel(String label){
        labelProperty.set(label);
    }
    
   public StringProperty labelProperty(){
       return labelProperty;
   }
    
    public void horizontalQc(){
         int indeterminate=0;
         int selected=0;
        for(QcTableSequence child:children){
            indeterminate += child.getLabel().equals(QcMatrixRowModelParent.INDETERMINATE)?1:0;
            selected += child.getLabel().equals(QcMatrixRowModelParent.SELECTED)?1:0;
        }
      //  System.out.println("fend.job.table.qctable.seq.QcTableSequence.horizontalQc(): indeterminate: "+indeterminate+" selected: "+selected+" no of children: "+children.size());
        if (indeterminate > 0) {
            String label = QcMatrixRowModelParent.INDETERMINATE;
            setLabel(label);
        } else if (indeterminate ==0 && selected == children.size()) {
            String label = QcMatrixRowModelParent.SELECTED;
            setLabel(label);
        } else {
            String label = QcMatrixRowModelParent.UNSELECTED;
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

    public void calculateWinners() {
     //   User u=getWinnerForQcMatrixFromChildren(q, updateTime)
    }

  String append=" (See subline comments)";
          
          private ChangeListener<Boolean> CHILDREN_HAVE_COMMENTS=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
          //  System.out.println(" recalculate the system comments");
            int i=0;
            for(QcTableSequence child:children){
                i++;
                if(child.hasComments()){
               //     System.out.println(".changed(): child "+child.getSubsurface().getSubsurface()+" has comments");
                    if(commentProperty.get().contains(append)){
                        //do nothing
               //         System.out.println(".changed(): append found ..doing nothing: "+commentProperty.get()+" ? "+commentProperty.get().contains(append));
                    }else{
               //          System.out.println(".changed(): append NOT found ..appending: "+commentProperty.get()+" ? "+commentProperty.get().contains(append));
                        commentProperty.set(commentProperty.get()+append);
                    }
                    break;
                }
            }
         //   System.out.println(".changed(): value of i == children.size-1 "+(i==children.size())+"  "+i+"=="+children.size());
            if(i==children.size()){
        //        System.out.println(".changed(): no comments from children  i= "+i);
              if(commentProperty.get().contains(append)){
         //         System.out.println(".changed(): parent comment contains append? "+commentProperty.get()+" ? "+commentProperty.get().contains(append));
                  String newstring=commentProperty.get().substring(0,commentProperty.get().indexOf(append));
                  commentProperty.set(newstring);
              }else{
           //       System.out.println(".changed(): parent comment DOES NOT  contains append? "+commentProperty.get()+" ? "+commentProperty.get().contains(append));
              }
            }
          }
    };
    
    public boolean hasComments(){
        return !commentProperty.get().trim().isEmpty();
    }

    public void redoComments() {
        redoCommentsProperty().set(!redoComments.get());
    }
}

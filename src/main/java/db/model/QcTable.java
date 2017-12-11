/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="QcTable",schema="obpmanager")
public class QcTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
   
    @ManyToOne
    @JoinColumn(name="qcmatrix_fk",nullable=false)
    private QcMatrixRow qcMatrixRow;
    
    /*@ManyToOne
    @JoinColumn(name="headers_fk",nullable=false)
    private Headers headers;*/
    
    @ManyToOne
    @JoinColumn(name="subsurface_fk",nullable=false)
    private Subsurface subsurface;
    
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;
    
    
    @Column(name="time")
    private String time;
    
    @Column(name="result")
    private Boolean result;
    
    @Column(name="comment",length=100000)
    private String comment;

   
    
    @Column(name="UpdateTime")
    private String updateTime;
    
    @Column(name="SummaryTime")
    private String summaryTime;
    
    
    
     public QcTable() {
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }
    
    
    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

   
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

   
    public QcMatrixRow getQcMatrixRow() {
        return qcMatrixRow;
    }

    public void setQcMatrixRow(QcMatrixRow qcMatrixRow) {
        this.qcMatrixRow = qcMatrixRow;
    }

    public Long getId() {
        return id;
    }

    /*public Headers getHeaders() {
    return headers;
    }
    
    public void setHeaders(Headers headers) {
    this.headers = headers;
    }*/

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    
    
    
    
    
}

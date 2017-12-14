/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

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
@Table(name="VariableArgument",schema="obpmanager")
public class VariableArgument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="variable")
    private String variable;
    
    @ManyToOne
    @JoinColumn(name="argument_job_fk",nullable=false)
    private Job argument;
    
    @ManyToOne
    @JoinColumn(name="dot_fk",nullable=false)
    private Dot dot;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Job getArgument() {
        return argument;
    }

    public void setArgument(Job argument) {
        this.argument = argument;
    }

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    public Long getId() {
        return id;
    }
    
    
} 

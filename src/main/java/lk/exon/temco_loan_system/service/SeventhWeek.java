/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@SessionScoped
@Named
public class SeventhWeek implements Serializable {

    private boolean checkbox10Selected;
    private boolean checkbox11Selected;
    private boolean checkbox12Selected;

    private boolean checkbox13Selected;
    private boolean checkbox14Selected;
    private boolean checkbox15Selected;
    private boolean checkbox16Selected;
    private boolean checkbox17Selected;
    private boolean checkbox18Selected;

    private boolean checkbox19Selected;
    private boolean checkbox20Selected;
    private boolean checkbox21Selected;

    private boolean allChecked;

    @PostConstruct
    public void init() {
        initializedMethod();
    }

    public void initializedMethod() {

    }

    public void checkDiv3() {
        System.out.println("checkDiv1");
        allChecked = checkbox13Selected && checkbox14Selected && checkbox15Selected && checkbox16Selected && checkbox17Selected && checkbox18Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public boolean isCheckbox10Selected() {
        return checkbox10Selected;
    }

    public void setCheckbox10Selected(boolean checkbox10Selected) {
        this.checkbox10Selected = checkbox10Selected;
    }

    public boolean isCheckbox11Selected() {
        return checkbox11Selected;
    }

    public void setCheckbox11Selected(boolean checkbox11Selected) {
        this.checkbox11Selected = checkbox11Selected;
    }

    public boolean isCheckbox12Selected() {
        return checkbox12Selected;
    }

    public void setCheckbox12Selected(boolean checkbox12Selected) {
        this.checkbox12Selected = checkbox12Selected;
    }

    public boolean isCheckbox13Selected() {
        return checkbox13Selected;
    }

    public void setCheckbox13Selected(boolean checkbox13Selected) {
        this.checkbox13Selected = checkbox13Selected;
    }

    public boolean isCheckbox14Selected() {
        return checkbox14Selected;
    }

    public void setCheckbox14Selected(boolean checkbox14Selected) {
        this.checkbox14Selected = checkbox14Selected;
    }

    public boolean isCheckbox15Selected() {
        return checkbox15Selected;
    }

    public void setCheckbox15Selected(boolean checkbox15Selected) {
        this.checkbox15Selected = checkbox15Selected;
    }

    public boolean isCheckbox16Selected() {
        return checkbox16Selected;
    }

    public void setCheckbox16Selected(boolean checkbox16Selected) {
        this.checkbox16Selected = checkbox16Selected;
    }

    public boolean isCheckbox17Selected() {
        return checkbox17Selected;
    }

    public void setCheckbox17Selected(boolean checkbox17Selected) {
        this.checkbox17Selected = checkbox17Selected;
    }

    public boolean isCheckbox18Selected() {
        return checkbox18Selected;
    }

    public void setCheckbox18Selected(boolean checkbox18Selected) {
        this.checkbox18Selected = checkbox18Selected;
    }

    public boolean isCheckbox19Selected() {
        return checkbox19Selected;
    }

    public void setCheckbox19Selected(boolean checkbox19Selected) {
        this.checkbox19Selected = checkbox19Selected;
    }

    public boolean isCheckbox20Selected() {
        return checkbox20Selected;
    }

    public void setCheckbox20Selected(boolean checkbox20Selected) {
        this.checkbox20Selected = checkbox20Selected;
    }

    public boolean isCheckbox21Selected() {
        return checkbox21Selected;
    }

    public void setCheckbox21Selected(boolean checkbox21Selected) {
        this.checkbox21Selected = checkbox21Selected;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

}

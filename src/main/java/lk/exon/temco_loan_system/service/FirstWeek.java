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
public class FirstWeek implements Serializable {

    private boolean checkbox1Selected;
    private boolean checkbox2Selected;
    private boolean checkbox3Selected;

    private boolean allChecked;

    @PostConstruct
    public void init() {
        initializedMethod();
    }

    public void initializedMethod() {

    }

    public void checkDiv1() {
        System.out.println("checkDiv1");
        allChecked = checkbox1Selected && checkbox2Selected && checkbox3Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public boolean isCheckbox1Selected() {
        return checkbox1Selected;
    }

    public void setCheckbox1Selected(boolean checkbox1Selected) {
        this.checkbox1Selected = checkbox1Selected;
    }

    public boolean isCheckbox2Selected() {
        return checkbox2Selected;
    }

    public void setCheckbox2Selected(boolean checkbox2Selected) {
        this.checkbox2Selected = checkbox2Selected;
    }

    public boolean isCheckbox3Selected() {
        return checkbox3Selected;
    }

    public void setCheckbox3Selected(boolean checkbox3Selected) {
        this.checkbox3Selected = checkbox3Selected;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

}

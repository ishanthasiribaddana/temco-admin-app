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
public class ThirdWeek implements Serializable {

    private boolean checkbox5Selected;
    private boolean checkbox6Selected;
    private boolean checkbox7Selected;
    private boolean checkbox8Selected;
    private boolean checkbox9Selected;
    private boolean checkbox10Selected;

    private boolean allChecked;

    @PostConstruct
    public void init() {
        initializedMethod();
    }

    public void initializedMethod() {

    }

    public void checkDiv3() {
        System.out.println("checkDiv1");
        allChecked = checkbox5Selected && checkbox6Selected && checkbox7Selected && checkbox8Selected && checkbox9Selected && checkbox10Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public boolean isCheckbox7Selected() {
        return checkbox7Selected;
    }

    public void setCheckbox7Selected(boolean checkbox7Selected) {
        this.checkbox7Selected = checkbox7Selected;
    }

    public boolean isCheckbox5Selected() {
        return checkbox5Selected;
    }

    public void setCheckbox5Selected(boolean checkbox5Selected) {
        this.checkbox5Selected = checkbox5Selected;
    }

    public boolean isCheckbox6Selected() {
        return checkbox6Selected;
    }

    public void setCheckbox6Selected(boolean checkbox6Selected) {
        this.checkbox6Selected = checkbox6Selected;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public boolean isCheckbox8Selected() {
        return checkbox8Selected;
    }

    public void setCheckbox8Selected(boolean checkbox8Selected) {
        this.checkbox8Selected = checkbox8Selected;
    }

    public boolean isCheckbox9Selected() {
        return checkbox9Selected;
    }

    public void setCheckbox9Selected(boolean checkbox9Selected) {
        this.checkbox9Selected = checkbox9Selected;
    }

    public boolean isCheckbox10Selected() {
        return checkbox10Selected;
    }

    public void setCheckbox10Selected(boolean checkbox10Selected) {
        this.checkbox10Selected = checkbox10Selected;
    }

}

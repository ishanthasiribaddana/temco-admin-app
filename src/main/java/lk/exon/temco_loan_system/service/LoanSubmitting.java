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
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@SessionScoped
@Named
public class LoanSubmitting implements Serializable {

    private boolean checkbox1Selected;
    private boolean checkbox2Selected;
    private boolean checkbox3Selected;
    private boolean checkbox4Selected;
    private boolean checkbox5Selected;
    private boolean checkbox6Selected;

    private boolean checkbox7Selected;
    private boolean checkbox8Selected;
    private boolean checkbox9Selected;
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

    public void moveToNextPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            // Get the context path to build the correct URL
            String contextPath = externalContext.getRequestContextPath();
            // Redirect to the specific page using the context path
            externalContext.redirect(contextPath + "/tasks/first-week-document-submission.xhtml");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    public void checkDiv1() {
        System.out.println("checkDiv1");
        allChecked = checkbox1Selected && checkbox2Selected && checkbox3Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public void checkDiv2() {
        System.out.println("checkDiv1");
        allChecked = checkbox2Selected && checkbox3Selected && checkbox4Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public void checkDiv3() {
        System.out.println("checkDiv1");
        allChecked = checkbox5Selected && checkbox6Selected && checkbox7Selected;
        if (!allChecked) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check the checkboxes", "Read each of intstructions and confirm by selecting checkbox");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public void checkDiv4() {
        System.out.println("checkDiv1");
        allChecked = checkbox8Selected && checkbox9Selected && checkbox10Selected;
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

    public boolean isCheckbox4Selected() {
        return checkbox4Selected;
    }

    public void setCheckbox4Selected(boolean checkbox4Selected) {
        this.checkbox4Selected = checkbox4Selected;
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

    public boolean isCheckbox7Selected() {
        return checkbox7Selected;
    }

    public void setCheckbox7Selected(boolean checkbox7Selected) {
        this.checkbox7Selected = checkbox7Selected;
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

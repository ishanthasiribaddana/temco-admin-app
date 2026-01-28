/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanManager;

/**
 *
 * @author USER
 */
@Named
@RequestScoped
public class EmailUnsubscribe {

    private String securityCode;
    private String loanIdPara;
    private LoanManager loanManager;
    Date date;

    @EJB
    UniDBLocal uniDB;

    @PostConstruct
    public void init() {
        System.out.println("Email Unsubsribe successful");
        getVerificationToken();
        unsubscribeEmail();
    }

    public void unsubscribeEmail() {
        System.out.println("loan id " + securityCode);
        List<LoanCustomer> loanCustomerList = uniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.verificationToken='" + securityCode + "' AND g.isSubscribe='1' ");
        System.out.println("loanCustomerList.isEmpty() "+loanCustomerList.isEmpty());
        if (!loanCustomerList.isEmpty()) {
            LoanCustomer obj = loanCustomerList.get(0);
            obj.setIsSubscribe(Short.decode("0"));
            uniDB.update(obj);
        }
    }

    public void getVerificationToken() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        securityCode = params.get("en");

        if (securityCode != null) {
            System.out.println("in if");
            getVerificationToken(securityCode);
        }
    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = uniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {

            return null;
        } else {
            System.out.println("returned object");
            return l.get(0);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service.user.view;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberBankAccounts;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author USER
 */
@ViewScoped
@LocalBean
@Named
public class AccountSummary implements Serializable {

    private MemberBankAccounts memberLoanAccount = null;

    String securityCode = "";

    @Inject
    private UniDBLocal uni;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        securityCode = params.get("en");

        if (securityCode != null) {

            getMemberLoanAccount(securityCode);
        } else {
            System.out.println("in else 2");
        }
    }

    private void getMemberLoanAccount(String securityCode) {
        System.out.println("A");
        List<GeneralUserProfile> generalUserProfile = uni.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + securityCode + "' ");
        System.out.println("B");
        if (!generalUserProfile.isEmpty()) {
            System.out.println("C");
            List<Member1> member = uni.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + generalUserProfile.get(0).getId() + "'");
            System.out.println("D");
            if (!member.isEmpty()) {
                System.out.println("E");
                List<MemberBankAccounts> bankaccount = uni.searchByQuery("SELECT g FROM MemberBankAccounts g WHERE g.memberId.id='" + member.get(0).getId() + "' AND g.accountTypeId.id='1' ");
                if (!bankaccount.isEmpty()) {
                    memberLoanAccount = bankaccount.get(0);
                    System.out.println("F");
                }
            }
        }

    }

    public MemberBankAccounts getMemberLoanAccount() {
        return memberLoanAccount;
    }

    public void setMemberLoanAccount(MemberBankAccounts memberLoanAccount) {
        this.memberLoanAccount = memberLoanAccount;
    }

}

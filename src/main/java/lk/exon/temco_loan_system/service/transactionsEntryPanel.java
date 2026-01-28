/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.UniDBLocal;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class transactionsEntryPanel implements Serializable {

    private String accountNumberOrNIC = "";
    private List<SelectItem> transactionTypesList = new ArrayList<>();
    ;
    private String selectedTransActionTypeId;

    @EJB
    private UniDBLocal UniDB;

    private ComLib ComLib;

    @PostConstruct
    public void init() {
        System.out.println("in it");
        intializeMethod();
    }

    private void intializeMethod() {
        System.out.println("intializeMethod");
        loadTransactionType();
    }

    private void loadTransactionType() {
        try {
            getTransactionTypesList().clear();
            getTransactionTypesList().add(new SelectItem("0", "Select"));
//            List<lk.exon.temco_loan_system.entity.TransactionType> transactionTypesEntity = UniDB.searchByQuery("SELECT g FROM TransactionType g ");
//            System.out.println("transactionTypes size" + transactionTypesEntity.size());
//
//            for (lk.exon.temco_loan_system.entity.TransactionType transactionType : transactionTypesEntity) {
//                System.out.println(transactionType.getId());
//                getTransactionTypesList().add(new SelectItem(transactionType.getId(), transactionType.getType()));
//            }
            System.out.println(getTransactionTypesList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> loadNic() {
        List<String> results = new ArrayList<String>();

        return results;
    }

    private void setSearchedNumberORNic() {

    }

    class TransactionType implements Serializable {

        public int id;
        public String type;

        public TransactionType(int id, String type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    public String getAccountNumberOrNIC() {
        return accountNumberOrNIC;
    }

    public void setAccountNumberOrNIC(String accountNumberOrNIC) {
        this.accountNumberOrNIC = accountNumberOrNIC;
    }

    public List<SelectItem> getTransactionTypesList() {
        return transactionTypesList;
    }

    public void setTransactionTypesList(List<SelectItem> transactionTypesList) {
        this.transactionTypesList = transactionTypesList;
    }

    public String getSelectedTransActionTypeId() {
        return selectedTransActionTypeId;
    }

    public void setSelectedTransActionTypeId(String selectedTransActionTypeId) {
        this.selectedTransActionTypeId = selectedTransActionTypeId;
    }

}

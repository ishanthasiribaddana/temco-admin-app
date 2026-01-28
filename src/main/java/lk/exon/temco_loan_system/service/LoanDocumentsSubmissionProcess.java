/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
@Named
@LocalBean
@ViewScoped
public class LoanDocumentsSubmissionProcess implements Serializable {

    private List<FirstWeekDocumnets> firstWeekDocumnetsList;
    private List<SecondWeekDocumnets> secondWeekDocumnetsLists;
    private List<ThirdWeekDocumnets> thirdWeekDocumnets;
    private List<FourthWeekDocumnets> fourthWeekDocumnets;
    private List<FifthhWeekDocumnets> fifthhWeekDocumnets;

    @PostConstruct
    public void init() {
        initialize();
    }

    public void initialize() {
        firstWeek();
        secondtWeek();
        thirdWeek();
        fourhWeek();
        fifthWeek();
    }

    public void firstWeek() {
        firstWeekDocumnetsList = new ArrayList<>();
        firstWeekDocumnetsList.add(new FirstWeekDocumnets("", ""));
    }

    public void secondtWeek() {

    }

    public void thirdWeek() {

    }

    public void fourhWeek() {

    }

    public void fifthWeek() {

    }

    class FirstWeekDocumnets implements Serializable {

        private String info;
        private String checked;

        public FirstWeekDocumnets(String info, String checked) {
            this.info = info;
            this.checked = checked;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

    }

    class SecondWeekDocumnets implements Serializable {

        private String info;
        private String checked;

        public SecondWeekDocumnets(String info, String checked) {
            this.info = info;
            this.checked = checked;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

    }

    class ThirdWeekDocumnets implements Serializable {

        private String info;
        private String checked;

        public ThirdWeekDocumnets(String info, String checked) {
            this.info = info;
            this.checked = checked;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

    }

    class FourthWeekDocumnets implements Serializable {

        private String info;
        private String checked;

        public FourthWeekDocumnets(String info, String checked) {
            this.info = info;
            this.checked = checked;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

    }

    class FifthhWeekDocumnets implements Serializable {

        private String info;
        private String checked;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

    }

    public List<FirstWeekDocumnets> getFirstWeekDocumnetsList() {
        return firstWeekDocumnetsList;
    }

    public void setFirstWeekDocumnetsList(List<FirstWeekDocumnets> firstWeekDocumnetsList) {
        this.firstWeekDocumnetsList = firstWeekDocumnetsList;
    }

    public List<SecondWeekDocumnets> getSecondWeekDocumnetsLists() {
        return secondWeekDocumnetsLists;
    }

    public void setSecondWeekDocumnetsLists(List<SecondWeekDocumnets> secondWeekDocumnetsLists) {
        this.secondWeekDocumnetsLists = secondWeekDocumnetsLists;
    }

    public List<ThirdWeekDocumnets> getThirdWeekDocumnets() {
        return thirdWeekDocumnets;
    }

    public void setThirdWeekDocumnets(List<ThirdWeekDocumnets> thirdWeekDocumnets) {
        this.thirdWeekDocumnets = thirdWeekDocumnets;
    }

    public List<FourthWeekDocumnets> getFourthWeekDocumnets() {
        return fourthWeekDocumnets;
    }

    public void setFourthWeekDocumnets(List<FourthWeekDocumnets> fourthWeekDocumnets) {
        this.fourthWeekDocumnets = fourthWeekDocumnets;
    }

    public List<FifthhWeekDocumnets> getFifthhWeekDocumnets() {
        return fifthhWeekDocumnets;
    }

    public void setFifthhWeekDocumnets(List<FifthhWeekDocumnets> fifthhWeekDocumnets) {
        this.fifthhWeekDocumnets = fifthhWeekDocumnets;
    }

}

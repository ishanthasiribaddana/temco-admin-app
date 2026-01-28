/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.exon.temco_loan_system.common;

import jakarta.ejb.Local;
import java.util.List;
import java.util.Map;
import lk.exon.temco_loan_system.entity.InterestManager;
import lk.exon.temco_loan_system.entity.LoanStatusManager;

/**
 *
 * @author nilupulnethmina
 */
@Local
public interface UniDBLocal {

    void create(Object obj);

    void update(Object obj);

    List findAll(Class c);

    Object find(int id, Class c);

    void remove(int id, Class c);

    List searchByQuery(String query);

    List searchByQuerySingle(String query);

    List searchByQueryLimit(String query, int limit);

    public List<Object> searchByQuery(String query, String parameterOne, String parameterTwo, String parameterThree, String parameterFour);

    public List<Object> searchByQuery(String query, String parameterOne, String parameterTwo);

    public List<InterestManager> searchByQueryInterestManager(String query, Map<String, Object> kv);

    public List<LoanStatusManager> searchByQueryLoanStatusManager(String query, Map<String, Object> kv);
}

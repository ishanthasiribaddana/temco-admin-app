/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.common;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lk.exon.temco_loan_system.entity.InterestManager;
import lk.exon.temco_loan_system.entity.LoanStatusManager;

/**
 *
 * @author nilupulnethmina
 */
@Stateless
public class UniDB implements UniDBLocal {

    @PersistenceContext(unitName = "lk.exon_temco_loan_system_war_1.0PU")
    EntityManager em;

    @Override
    public void create(Object obj) {
        try {
            em.persist(obj);
            em.flush();
        } catch (ConstraintViolationException e) {
            System.out.println("e is>>>>>>>>>>>>>>>>>>>>>>>>>" + e);
        }
    }

    @Override
    public void update(Object obj) {
        try {
            em.merge(obj);
        } catch (ConstraintViolationException e) {
            System.out.println("e is>>>>>>>>>>>>>>>>>>>>>>>>>" + e);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);

            if (constraintViolations.size() > 0) {
                System.out.println("Constraint Violations occurred..");
                for (ConstraintViolation<Object> contraints : constraintViolations) {
                    System.out.println(contraints.getRootBeanClass().getSimpleName()
                            + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                }
            }

        }

    }

    @Override
    public List findAll(Class c) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(c));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Object find(int id, Class c) {
        try {
            return em.find(c.newInstance().getClass(), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(int id, Class c) {
        em.remove(find(id, c));
    }

    @Override
    public List searchByQuery(String query) {
        try {
            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List searchByQuerySingle(String query) {
        return em.createQuery(query).setMaxResults(1).getResultList();
    }

    @Override
    public List searchByQueryLimit(String query, int limit) {
        return em.createQuery(query).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Object> searchByQuery(String query, String parameterOne, String parameterTwo, String parameterThree, String parameterFour) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Object> searchByQuery(String query, String parameterOne, String parameterTwo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<InterestManager> searchByQueryInterestManager(String query, Map<String, Object> kv) {
        try {
            // Create typed query for InterestManager
            var typedQuery = em.createQuery(query, InterestManager.class);

            // Set parameters safely
            if (kv != null) {
                for (Map.Entry<String, Object> entry : kv.entrySet()) {
                    typedQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return typedQuery.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();  // return empty list instead of null
        }
    }

    @Override
    public List<LoanStatusManager> searchByQueryLoanStatusManager(String query, Map<String, Object> kv) {
        try {
            // Create typed query for LoanStatusManager
            var typedQuery = em.createQuery(query, LoanStatusManager.class);

            // Set parameters safely
            if (kv != null) {
                for (Map.Entry<String, Object> entry : kv.entrySet()) {
                    typedQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return typedQuery.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();  // return empty list instead of null
        }
    }

}

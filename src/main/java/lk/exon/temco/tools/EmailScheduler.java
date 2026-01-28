/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco.tools;

import jakarta.annotation.PreDestroy;
import jakarta.ejb.Startup;
import jakarta.ejb.TimerService;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lk.exon.temco.templates.OfferInformEmailTemplateOne;
import lk.exon.temco_loan_system.service.EmailSendingBean;

/**
 *
 * @author USER
 */
@Singleton
public class EmailScheduler implements Serializable {

    private static ScheduledExecutorService scheduler;

    public static Queue<EmailSendingBean.LoanExpectingStudents> emailQueue;

    public EmailScheduler() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.emailQueue = new ConcurrentLinkedQueue<>();
    }

    public static void scheduleEmailSending() {
        long delay = 5; // Delay in minutes
        int index = 0;

        // Schedule sending for each email in the queue
        while (!emailQueue.isEmpty()) {
            final EmailSendingBean.LoanExpectingStudents student = emailQueue.poll(); // Retrieve and remove the head of the queue
            if (student != null) {
                scheduler.schedule(() -> sendEmail(student), delay * (index + 1), TimeUnit.MINUTES);
                index++;
            }
        }
    }

    private static void sendEmail(EmailSendingBean.LoanExpectingStudents student) {
        try {
            // Logic to send email
            boolean success = new NewMailSender().sendM(
                    student.getEmail(),
                    "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute",
                    new OfferInformEmailTemplateOne().emailTemplate(student.getStudentName(), student.getVerificationToken())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

}

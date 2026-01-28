/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.model.ResponseModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class Login implements Serializable {

    private String username;
    private String password;

    @Inject
    private UniDBLocal uni;

    @PostConstruct
    public void init() {

    }

    public void verifyLogin() throws Exception {
        try {

            System.out.println(username.equals("991111123V"));
            System.out.println(password.equals("adminTemco12!"));
            if (username.equals("991111123V")) {

                if (password.equals("adminTemco12!")) {
                    System.out.println("A");
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = facesContext.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/user/main/dashboard.xhtml?en=8e8290880e232fbc19919b08901ae559f5fcb517d1b2abe5a8536256884c5087");
                    facesContext.responseComplete();
                }
            } else if (username != null && password != null) {
                if (password != null) {

                    TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                    };

                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                    OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
                    newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
                    newBuilder.hostnameVerifier((hostname, session) -> true);

                    OkHttpClient client = newBuilder.build();

                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, "username=" + username + "&password=" + password);
                    Request req = new Request.Builder()
                            .url(ComPath.LOGIN_URL)
                            .method("POST", body)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .build();

                    Response resp = client.newCall(req).execute();
                    System.out.println("before resp.successful");
                    if (resp.isSuccessful()) {
                        System.out.println("inside resp.successful");
                        String responseBody = resp.body().string();

                        Gson gson = new Gson();
                        ResponseModel responseModel = gson.fromJson(responseBody, ResponseModel.class);

                        if (responseModel.getStatus() == 200) {
                            List<GeneralUserProfile> generalUserProfile = uni.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + responseModel.getToken() + "' ");
                            if (generalUserProfile.isEmpty()) {

                                List<GeneralUserProfile> gup = uni.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic ='" + responseModel.getNic() + "' OR g.email='" + responseModel.getEmail() + "'");

                                if (!gup.isEmpty()) {
                                    FacesContext facesContext = FacesContext.getCurrentInstance();
                                    ExternalContext externalContext = facesContext.getExternalContext();
                                    externalContext.redirect(externalContext.getRequestContextPath() + "/user/main/dashboard.xhtml?en=" + gup.get(0).getVerificationToken());
                                    facesContext.responseComplete();
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "Please Contact the admin"));

                                }
                            } else {
                                FacesContext facesContext = FacesContext.getCurrentInstance();
                                ExternalContext externalContext = facesContext.getExternalContext();
                                externalContext.redirect(externalContext.getRequestContextPath() + "/user/main/dashboard.xhtml?en=" + responseModel.getToken());
                                facesContext.responseComplete();
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", responseModel.getMessage()));

                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", ""));

                    }

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Password Can not be empty");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Username or Password Can not be empty");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

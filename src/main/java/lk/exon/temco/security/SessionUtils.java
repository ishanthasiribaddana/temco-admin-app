/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.security;

import jakarta.faces.context.FacesContext;

/**
 *
 * @author USER
 */
public class SessionUtils {

    public static Object getAdminUser() {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("adminUser");
    }
}

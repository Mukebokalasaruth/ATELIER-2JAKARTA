package com.jakarta.udbl.jakartamission.business; 

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class SessionManager {


    public void createSession(String key, String value) {
    
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
}

    public String getValueFromSession(String key) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            return (String) session.getAttribute(key);
        }
        return null;
    }

    public void invalidateSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }


}

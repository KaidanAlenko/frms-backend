package hr.eestec_zg.frmsbackend.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    private int timeoutMin = 60;

    public SessionListener(int timeoutMin) {
        this.timeoutMin = timeoutMin;
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(timeoutMin * 60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}

package hr.eestec_zg.frmsbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {AppConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        ctx.setInitParameter("spring.profiles.active", "production");
        ctx.addListener(new ServletContextListener() {
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("\n\n******************* Servlet Context initialized **************************\n");
            }
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("\n\n******************* Servlet Context destroyed **************************\n");
            }
        });
        super.onStartup(ctx);
    }
}

package hr.eestec_zg.frmsbackend.config.security;

import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.services.JacksonService;
import hr.eestec_zg.frmsbackend.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JacksonService jacksonService;

    public AjaxAuthenticationSuccessHandler(UserService userService, JacksonService jacksonService) {
        this.userService = userService;
        this.jacksonService = jacksonService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getUserByEmail(authentication.getName());

        response.getWriter().print(jacksonService.asJson(UserDetails.createDetailsFromUser(loggedUser)));
    }
}

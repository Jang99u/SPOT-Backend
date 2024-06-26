package ice.spot.security.handler.logout;

import ice.spot.exception.ErrorCode;
import ice.spot.security.info.AuthenticationResponse;
import ice.spot.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomLogoutResultHandler implements LogoutSuccessHandler {

    @Value("${server.domain}")
    private String domain;

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        if (authentication == null) {
            log.info("인증 정보가 존재하지 않습니다. authentication is null.");
            AuthenticationResponse.makeFailureResponse(response, ErrorCode.NOT_FOUND_USER);
        }
        CookieUtil.logoutCookie(request, response, domain);
        AuthenticationResponse.makeSuccessResponse(response);
    }
}

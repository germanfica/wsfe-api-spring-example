package com.germanfica.wsfe_api.provider;

import com.germanfica.wsfe.param.FEAuthParams;
import com.germanfica.wsfe.provider.CredentialsProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * Permite pasar Token/Sign/Cuit por headers:
 *   X-WSFE-Token, X-WSFE-Sign, X-WSFE-Cuit (configurable en application.yml)
 */
@Component
public class HeaderFeAuthParamsProvider implements CredentialsProvider<FEAuthParams> {

    @Value("${security.headers.token:X-WSFE-Token}")
    private String tokenHeader;

    @Value("${security.headers.sign:X-WSFE-Sign}")
    private String signHeader;

    @Value("${security.headers.cuit:X-WSFE-Cuit}")
    private String cuitHeader;

    @Override
    public Optional<FEAuthParams> resolve() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (!(attrs instanceof ServletRequestAttributes sra)) return Optional.empty();

        HttpServletRequest req = sra.getRequest();
        String token = req.getHeader(tokenHeader);
        String sign = req.getHeader(signHeader);
        String cuit = req.getHeader(cuitHeader);

        if (token == null || token.isBlank() || sign == null || sign.isBlank() || cuit == null || cuit.isBlank()) {
            return Optional.empty();
        }

        try {
            return Optional.of(
                FEAuthParams.builder()
                    .setToken(token.trim())
                    .setSign(sign.trim())
                    .setCuit(Long.parseLong(cuit.trim()))
                    .build()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

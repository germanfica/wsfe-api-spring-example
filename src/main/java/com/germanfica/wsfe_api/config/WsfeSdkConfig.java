package com.germanfica.wsfe_api.config;

import com.germanfica.wsfe.WsaaClient;
import com.germanfica.wsfe.exception.ApiException;
import com.germanfica.wsfe.net.*;
import com.germanfica.wsfe.provider.CredentialsProvider;
import com.germanfica.wsfe.provider.ProviderChain;
import com.germanfica.wsfe.provider.feauth.ApplicationPropertiesFeAuthParamsProvider;
import com.germanfica.wsfe.provider.feauth.FEAuthProvider;
import com.germanfica.wsfe.provider.feauth.StaticAuthProvider;
import com.germanfica.wsfe.provider.feauth.RefreshingAuthProvider;
import com.germanfica.wsfe.param.FEAuthParams;
import com.germanfica.wsfe.service.WsfeService;
import lombok.Data;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WsfeSdkConfig {

    @Bean
    @ConfigurationProperties(prefix = "wsfe")
    public WsfeProps wsfeProps() {
        return new WsfeProps();
    }

    @Data
    public static class WsfeProps {
        private ApiEnvironment environment = ApiEnvironment.HOMO;
        private String urlBase; // opcional
        private Proxy proxy = new Proxy();

        @Data
        public static class Proxy {
            private boolean enabled = false;
            private String host;
            private Integer port;
            private String username;
            private String password;
        }
    }

    @Bean
    public ProxyOptions proxyOptions(WsfeProps props) {
        if (!props.getProxy().isEnabled()) {
            return null;
        }
        return ProxyOptions.builder()
            .setHost(props.getProxy().getHost())
            .setPort(props.getProxy().getPort())
            .setUsername(props.getProxy().getUsername())
            .setPassword(props.getProxy().getPassword())
            .build();
    }

    /**
     * Opciones SOAP para WSFE. Usamos el environment del SDK; si urlBase se deja null, el SDK
     * resuelve el endpoint adecuado por port (WSAA vs WSFE). :contentReference[oaicite:1]{index=1}
     */
    @Bean
    public SoapResponseGetterOptions wsfeSoapOptions(WsfeProps props, ObjectProvider<ProxyOptions> proxy) {
        return new SoapResponseGetterOptions() {
            @Override public String getUrlBase() { return props.getUrlBase(); }
            @Override public ApiEnvironment getApiEnvironment() { return props.getEnvironment(); }
            @Override public ProxyOptions getProxyOptions() { return proxy.getIfAvailable(); }
            @Override public HttpTransportMode getHttpTransportMode() { return HttpTransportMode.HTTP; }
        };
    }

    @Bean
    public SoapRequestHandler wsfeSoapRequestHandler(SoapResponseGetterOptions wsfeSoapOptions) {
        return new DefaultSoapRequestHandler(wsfeSoapOptions);
    }

    /**
     * WsaaClient para refrescar TA cuando haga falta (RefreshingAuthProvider). :contentReference[oaicite:2]{index=2}
     */
    @Bean
    public WsaaClient wsaaClient(WsfeProps props, ObjectProvider<ProxyOptions> proxy) {
        WsaaClient.WsaaClientBuilder b = WsaaClient.builder()
            .setApiEnvironment(props.getEnvironment());
        if (proxy.getIfAvailable() != null) {
            b.setProxyOptions(proxy.getIfAvailable());
        }
        return b.build();
    }

    /**
     * FEAuthProvider con cadena de proveedores:
     * 1) Headers HTTP (HeaderFeAuthParamsProvider – se inyecta si está definido)
     * 2) application.properties del SDK (ApplicationPropertiesFeAuthParamsProvider)
     * 3) RefreshingAuthProvider (usa WSAA si no hay TA disponible)
     */
    @Bean
    public FEAuthProvider feAuthProvider(
        WsaaClient wsaaClient,
        ObjectProvider<CredentialsProvider<FEAuthParams>> headerProviderOpt
    ) {
        ProviderChain<FEAuthParams> chain = ProviderChain.<FEAuthParams>builder()
            .addProvider(headerProviderOpt.getIfAvailable()) // puede ser null; ProviderChain ignora nulls si no los agregás
            .addProvider(new ApplicationPropertiesFeAuthParamsProvider())
            .build();

        return chain.resolve()
            .<FEAuthProvider>map(StaticAuthProvider::new)
            .orElseGet(() -> new RefreshingAuthProvider(wsaaClient));
    }

    @Bean
    public WsfeService wsfeService(SoapRequestHandler wsfeSoapRequestHandler, FEAuthProvider feAuthProvider) throws ApiException {
        return new WsfeService(wsfeSoapRequestHandler, feAuthProvider);
    }
}

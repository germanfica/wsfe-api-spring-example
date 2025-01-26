package com.germanfica.wsfe_api.controller;

import com.germanfica.wsfe_api.service.ArcaWSAAClientService;

//import com.stripe.StripeClient;
import com.germanfica.wsfe.dto.LoginCmsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.germanfica.wsfe.dto.LoginCmsResponseDto;

@RestController
@RequestMapping
public class ApiController {
    // == fields ==
    private final ArcaWSAAClientService arcaWSAAClientService;

    // == constructors ==
    @Autowired
    public ApiController(ArcaWSAAClientService arcaWSAAClientService) {
        this.arcaWSAAClientService = arcaWSAAClientService;
    }

    @GetMapping("/wsaa/invoke")
    //public String invokeWsaa() {
    public ResponseEntity<LoginCmsResponseDto> invokeWsaa() {
        //StripeClient stripe = new StripeClient("");
        //return "HOLAAA";
        return ResponseEntity.ok(arcaWSAAClientService.invokeWsaa());
    }
}

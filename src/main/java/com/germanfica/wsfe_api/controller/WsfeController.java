package com.germanfica.wsfe_api.controller;

import com.germanfica.wsfe.exception.ApiException;
import com.germanfica.wsfe_api.service.WsfeApiService;
import fev1.dif.afip.gov.ar.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wsfe")
@RequiredArgsConstructor
public class WsfeController {

    private final WsfeApiService service;

    // Salud
    @GetMapping("/dummy")
    public ResponseEntity<DummyResponse> feDummy() throws ApiException {
        return ResponseEntity.ok(service.feDummy());
    }

    // Parámetros
    @GetMapping("/param/tipos-cbte")
    public ResponseEntity<CbteTipoResponse> tiposCbte() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposCbte());
    }

    @GetMapping("/param/tipos-concepto")
    public ResponseEntity<ConceptoTipoResponse> tiposConcepto() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposConcepto());
    }

    @GetMapping("/param/tipos-doc")
    public ResponseEntity<DocTipoResponse> tiposDoc() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposDoc());
    }

    @GetMapping("/param/actividades")
    public ResponseEntity<FEActividadesResponse> actividades() throws ApiException {
        return ResponseEntity.ok(service.feParamGetActividades());
    }

    @GetMapping("/param/condicion-iva-receptor")
    public ResponseEntity<CondicionIvaReceptorResponse> condicionIvaReceptor(@RequestParam(required = false) String claseCmp) throws ApiException {
        return ResponseEntity.ok(service.feParamGetCondicionIvaReceptor(claseCmp));
    }

    @GetMapping("/param/paises")
    public ResponseEntity<FEPaisResponse> paises() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposPaises());
    }

    @GetMapping("/param/ptos-venta")
    public ResponseEntity<FEPtoVentaResponse> ptosVenta() throws ApiException {
        return ResponseEntity.ok(service.feParamGetPtosVenta());
    }

    @GetMapping("/param/monedas")
    public ResponseEntity<MonedaResponse> monedas() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposMonedas());
    }

    @GetMapping("/param/iva")
    public ResponseEntity<IvaTipoResponse> iva() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposIva());
    }

    @GetMapping("/param/tributos")
    public ResponseEntity<FETributoResponse> tributos() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposTributos());
    }

    @GetMapping("/param/opcional")
    public ResponseEntity<OpcionalTipoResponse> opcional() throws ApiException {
        return ResponseEntity.ok(service.feParamGetTiposOpcional());
    }

    @GetMapping("/cotizacion")
    public ResponseEntity<FECotizacionResponse> cotizacion(@RequestParam String monId,
                                                           @RequestParam String fchCotiz) throws ApiException {
        return ResponseEntity.ok(service.feParamGetCotizacion(monId, fchCotiz));
    }

    // Comprobantes
    @GetMapping("/comp/ultimo-autorizado")
    public ResponseEntity<FERecuperaLastCbteResponse> ultimoAutorizado(@RequestParam int ptoVta,
                                                                       @RequestParam int cbteTipo) throws ApiException {
        return ResponseEntity.ok(service.feCompUltimoAutorizado(ptoVta, cbteTipo));
    }

    @PostMapping("/comp/consultar")
    public ResponseEntity<FECompConsultaResponse> compConsultar(@RequestBody FECompConsultaReq body) throws ApiException {
        return ResponseEntity.ok(service.feCompConsultar(body));
    }

    @PostMapping("/fecae/solicitar")
    public ResponseEntity<FECAEResponse> fecaeSolicitar(@RequestBody FECAERequest body) throws ApiException {
        return ResponseEntity.ok(service.fecaeSolicitar(body));
    }

    // CAEA
    @GetMapping("/caea/consultar")
    public ResponseEntity<FECAEAGetResponse> caeaConsultar(@RequestParam int periodo,
                                                           @RequestParam short orden) throws ApiException {
        return ResponseEntity.ok(service.fecaeaConsultar(periodo, orden));
    }

    @PostMapping("/caea/solicitar")
    public ResponseEntity<FECAEAGetResponse> caeaSolicitar(@RequestParam int periodo,
                                                           @RequestParam short orden) throws ApiException {
        return ResponseEntity.ok(service.fecaeaSolicitar(periodo, orden));
    }

    @PostMapping("/caea/reg-informativo")
    public ResponseEntity<FECAEAResponse> caeaRegInformativo(@RequestBody FECAEARequest body) throws ApiException {
        return ResponseEntity.ok(service.fecaeaRegInformativo(body));
    }

    @GetMapping("/caea/sin-mov/consultar")
    public ResponseEntity<FECAEASinMovConsResponse> caeaSinMovConsultar(@RequestParam String caea,
                                                                        @RequestParam int ptoVta) throws ApiException {
        return ResponseEntity.ok(service.fecaeaSinMovimientoConsultar(caea, ptoVta));
    }

    @PostMapping("/caea/sin-mov/informar")
    public ResponseEntity<FECAEASinMovResponse> caeaSinMovInformar(@RequestParam int ptoVta,
                                                                   @RequestParam String caea) throws ApiException {
        return ResponseEntity.ok(service.fecaeaSinMovimientoInformar(ptoVta, caea));
    }
}

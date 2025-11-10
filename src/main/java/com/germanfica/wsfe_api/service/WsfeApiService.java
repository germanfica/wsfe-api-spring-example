package com.germanfica.wsfe_api.service;

import com.germanfica.wsfe.exception.ApiException;
import com.germanfica.wsfe.service.WsfeService;
import fev1.dif.afip.gov.ar.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WsfeApiService {

    private final WsfeService wsfe;

    public DummyResponse feDummy() throws ApiException {
        return wsfe.feDummy();
    }

    public CbteTipoResponse feParamGetTiposCbte() throws ApiException {
        return wsfe.feParamGetTiposCbte();
    }

    public ConceptoTipoResponse feParamGetTiposConcepto() throws ApiException {
        return wsfe.feParamGetTiposConcepto();
    }

    public DocTipoResponse feParamGetTiposDoc() throws ApiException {
        return wsfe.feParamGetTiposDoc();
    }

    public FEActividadesResponse feParamGetActividades() throws ApiException {
        return wsfe.feParamGetActividades();
    }

    public CondicionIvaReceptorResponse feParamGetCondicionIvaReceptor(String claseCmp) throws ApiException {
        return wsfe.feParamGetCondicionIvaReceptor(claseCmp);
    }

    public FEPaisResponse feParamGetTiposPaises() throws ApiException {
        return wsfe.feParamGetTiposPaises();
    }

    public FEPtoVentaResponse feParamGetPtosVenta() throws ApiException {
        return wsfe.feParamGetPtosVenta();
    }

    public FERecuperaLastCbteResponse feCompUltimoAutorizado(int ptoVta, int cbteTipo) throws ApiException {
        return wsfe.feCompUltimoAutorizado(ptoVta, cbteTipo);
    }

    public FERegXReqResponse feCompTotXRequest() throws ApiException {
        return wsfe.feCompTotXRequest();
    }

    public FETributoResponse feParamGetTiposTributos() throws ApiException {
        return wsfe.feParamGetTiposTributos();
    }

    public IvaTipoResponse feParamGetTiposIva() throws ApiException {
        return wsfe.feParamGetTiposIva();
    }

    public MonedaResponse feParamGetTiposMonedas() throws ApiException {
        return wsfe.feParamGetTiposMonedas();
    }

    public OpcionalTipoResponse feParamGetTiposOpcional() throws ApiException {
        return wsfe.feParamGetTiposOpcional();
    }

    public FECotizacionResponse feParamGetCotizacion(String monId, String fchCotiz) throws ApiException {
        return wsfe.feParamGetCotizacion(monId, fchCotiz);
    }

    public FECompConsultaResponse feCompConsultar(FECompConsultaReq req) throws ApiException {
        return wsfe.feCompConsultar(req);
    }

    public FECAEResponse fecaeSolicitar(FECAERequest req) throws ApiException {
        return wsfe.fecaeSolicitar(req);
    }

    public FECAEAGetResponse fecaeaConsultar(int periodo, short orden) throws ApiException {
        return wsfe.fecaeaConsultar(periodo, orden);
    }

    public FECAEAGetResponse fecaeaSolicitar(int periodo, short orden) throws ApiException {
        return wsfe.fecaeaSolicitar(periodo, orden);
    }

    public FECAEAResponse fecaeaRegInformativo(FECAEARequest req) throws ApiException {
        return wsfe.fecaeaRegInformativo(req);
    }

    public FECAEASinMovConsResponse fecaeaSinMovimientoConsultar(String caea, int ptoVta) throws ApiException {
        return wsfe.fecaeaSinMovimientoConsultar(caea, ptoVta);
    }

    public FECAEASinMovResponse fecaeaSinMovimientoInformar(int ptoVta, String caea) throws ApiException {
        return wsfe.fecaeaSinMovimientoInformar(ptoVta, caea);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_gasto;

import java.util.Date;
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.objeto_gasto.ObjetoGasto;
import py.com.aplicacion.tipo_comprobante.TipoComprobante;

/**
 *
 * @author hugo
 */
public class RendicionGasto {
    
    private Integer rendicion;
    private Integer transferencia;
    private String resolucion_numero;
    private TipoComprobante tipo_comprobante;
    private String comprobante_numero;
    private ObjetoGasto objeto;
    private String concepto;
    private Date fecha;
    private Long importe;
    private String observacion;
    private ConsejoSalud consejo;
    private String ruc_factura;
    private Date timbrado_venciomiento;
    private Date timbrado_iniciovigencia;



    public Integer getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Integer transferencia) {
        this.transferencia = transferencia;
    }

    public TipoComprobante getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(TipoComprobante tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public ObjetoGasto getObjeto() {
        return objeto;
    }

    public void setObjeto(ObjetoGasto objeto) {
        this.objeto = objeto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getImporte() {
        return importe;
    }

    public void setImporte(Long importe) {
        this.importe = importe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getRendicion() {
        return rendicion;
    }

    public void setRendicion(Integer rendicion) {
        this.rendicion = rendicion;
    }

    public String getComprobante_numero() {
        return comprobante_numero;
    }

    public void setComprobante_numero(String comprobante_numero) {
        this.comprobante_numero = comprobante_numero;
    }

    public ConsejoSalud getConsejo() {
        return consejo;
    }

    public void setConsejo(ConsejoSalud consejo) {
        this.consejo = consejo;
    }

    public String getResolucion_numero() {
        return resolucion_numero;
    }

    public void setResolucion_numero(String resolucion_numero) {
        this.resolucion_numero = resolucion_numero;
    }

    public String getRuc_factura() {
        return ruc_factura;
    }

    public void setRuc_factura(String ruc_factura) {
        this.ruc_factura = ruc_factura;
    }

    public Date getTimbrado_venciomiento() {
        return timbrado_venciomiento;
    }

    public void setTimbrado_venciomiento(Date timbrado_venciomiento) {
        this.timbrado_venciomiento = timbrado_venciomiento;
    }

    public Date getTimbrado_iniciovigencia() {
        return timbrado_iniciovigencia;
    }

    public void setTimbrado_iniciovigencia(Date timbrado_iniciovigencia) {
        this.timbrado_iniciovigencia = timbrado_iniciovigencia;
    }
    
    
}



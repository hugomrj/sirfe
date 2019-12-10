/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package py.com.aplicacion.transferencia_fondo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import py.com.aplicacion.consejosalud.ConsejoSalud;
import py.com.aplicacion.rendicion_gasto.RendicionGasto;
import py.com.aplicacion.rendicion_gasto.RendicionGastoExt;
import py.com.aplicacion.tipo_transferencia.TipoTransferencia;



public class TransferenciaFondo {
    
    private Integer transferencia;
    private String resolucion_numero;
    private Long saldo_anterior;
    private TipoTransferencia tipo_transferencia;
    private Integer origen_ingreso;
    private Integer recibo_numero;
    private Integer comprobante_numero;
    private Date deposito_fecha;
    private Long total_depositado;
    private ConsejoSalud consejo;
    private Long total_rendicion;
    
    
    private List<RendicionGastoExt> rendiciones = new ArrayList<>();    

    
    
    
    
    public Integer getOrigen_ingreso() {
        return origen_ingreso;
    }

    public void setOrigen_ingreso(Integer origen_ingreso) {
        this.origen_ingreso = origen_ingreso;
    }

    public Integer getRecibo_numero() {
        return recibo_numero;
    }

    public void setRecibo_numero(Integer recibo_numero) {
        this.recibo_numero = recibo_numero;
    }

    public Integer getComprobante_numero() {
        return comprobante_numero;
    }

    public void setComprobante_numero(Integer comprobante_numero) {
        this.comprobante_numero = comprobante_numero;
    }

    public Date getDeposito_fecha() {
        return deposito_fecha;
    }

    public void setDeposito_fecha(Date deposito_fecha) {
        this.deposito_fecha = deposito_fecha;
    }



    public Long getTotal_depositado() {
        return total_depositado;
    }

    public void setTotal_depositado(Long total_depositado) {
        this.total_depositado = total_depositado;
    }

    public ConsejoSalud getConsejo() {
        return consejo;
    }

    public void setConsejo(ConsejoSalud consejo) {
        this.consejo = consejo;
    }


    public Integer getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Integer transferencia) {
        this.transferencia = transferencia;
    }

    public Long getTotal_rendicion() {
        return total_rendicion;
    }

    public void setTotal_rendicion(Long total_rendicion) {
        this.total_rendicion = total_rendicion;
    }

    public String getResolucion_numero() {
        return resolucion_numero;
    }

    public void setResolucion_numero(String resolucion_numero) {
        this.resolucion_numero = resolucion_numero;
    }

    public Long getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(Long saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public TipoTransferencia getTipo_transferencia() {
        return tipo_transferencia;
    }

    public void setTipo_transferencia(TipoTransferencia tipo_transferencia) {
        this.tipo_transferencia = tipo_transferencia;
    }

    public List<RendicionGastoExt> getRendiciones() {
        return rendiciones;
    }

    public void setRendiciones(List<RendicionGastoExt> rendiciones) {
        this.rendiciones = rendiciones;
    }

    
}






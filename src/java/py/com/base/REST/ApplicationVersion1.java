/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.REST;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hugo
 */


@javax.ws.rs.ApplicationPath("api")
public class ApplicationVersion1 extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

            resources.add(py.com.aplicacion.consejosalud.ConsejoSaludWS.class);
            resources.add(py.com.aplicacion.consejosalud.ConsejoSaludWS_query.class);
            resources.add(py.com.aplicacion.departamento.DepartamentoWS.class);
            resources.add(py.com.aplicacion.departamento.DepartamentoWS_query.class);
            resources.add(py.com.aplicacion.objeto_gasto.ObjetoGastoWS.class);
            resources.add(py.com.aplicacion.objeto_gasto.ObjetoGastoWS_query.class);
        resources.add(py.com.aplicacion.rendicion_gasto.RendicionGastoWS_session.class);
        resources.add(py.com.aplicacion.rendicion_gasto.RendicionGastoWS_verificador.class);
        resources.add(py.com.aplicacion.rendicion_verificacion.RendicionVerificacionWS.class);
        resources.add(py.com.aplicacion.resolucion.ResolucionWS.class);
        resources.add(py.com.aplicacion.tipo_comprobante.TipoComprobanteWS.class);
        resources.add(py.com.aplicacion.tipo_transferencia.TipoTransferenciaWS.class);
        resources.add(py.com.aplicacion.transferencia_fondo.TransferenciaFondoWS_admin.class);
        resources.add(py.com.aplicacion.transferencia_fondo.TransferenciaFondoWS_sesion.class);
        resources.add(py.com.aplicacion.verificacion_estado.VerificacionEstadoWS.class);
        resources.add(py.com.base.sistema.rol.RolWS.class);
        resources.add(py.com.base.sistema.rol_selector.RolSelectorWS.class);
        resources.add(py.com.base.sistema.selector.SelectorWS.class);
        resources.add(py.com.base.sistema.usuario.UsuarioWS.class);
        resources.add(py.com.base.sistema.usuario_rol.UsuarioRolWS.class);
        resources.add(py.com.consulta.ConsultaWS.class);
        
        
            

    }
    
}

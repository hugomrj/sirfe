
package py.com.base.sistema.selector;

/**
 *
 * @author hugo
 */
public class Selector {
    
    private Integer selector;
    private Integer superior;
    private String  descripcion;
    
    private Integer ord;
    private String link;
    
    /*
    private Integer nivel ;
    private String codigo;
    */
    


    public Integer getSuperior() {
        return superior;
    }

    public void setSuperior(Integer superior) {
        this.superior = superior;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Integer getSelector() {
        return selector;
    }

    public void setSelector(Integer selector) {
        this.selector = selector;
    }

    
    
}





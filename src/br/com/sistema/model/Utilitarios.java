
package br.com.sistema.model;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author perei
 */
public class Utilitarios {
    
    //metodo para limpar os campos
    public void LimpaTela(JPanel container){
    Component components[]= container.getComponents();
    for(Component component : components){
    if(component instanceof JTextField){
       ((JTextField)component).setText(null);
    }
    }
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dot.formulaField;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FormulaFieldNode extends AnchorPane{
    private FXMLLoader fXMLLoader;
    private final URL location;
    private FormulaFieldController formulaFieldController;
    
    public FormulaFieldNode(FormulaFieldModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/formulafield.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                formulaFieldController=(FormulaFieldController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                formulaFieldController.setModel(lsm);
                formulaFieldController.setView(this) ;
                
               
                System.out.println("landing.reporter.ReporterNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}
}

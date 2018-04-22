/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix.qctype.addQcType;


import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AddQcTypeNode extends AnchorPane{
     private FXMLLoader fXMLLoader;
    private final URL location;
    private AddQcTypeController isc;
    
    public AddQcTypeNode(AddQcTypeModel ism){
         this.location=getClass().getClassLoader().getResource("fxml/job3/definitions/qcmatrix/addqctype/addQcType.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                isc=(AddQcTypeController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                isc.setModel(ism);
                isc.setView(this) ;
                
                System.out.println("fend.job.job3.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode.<init>()");
               
                //System.out.println("fend.session.node.volumes.qctable.qcCheckBox.qcCheckListNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}

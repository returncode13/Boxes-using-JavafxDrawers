/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edge.parentchildedge;

import anchor.AnchorModel;
import boxes.BoxModel;
import dot.DotModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeModel {
    BoxModel parentBoxModel;
    DotModel dotModel;
    AnchorModel childAnchorModel;               //Anchor dropped on the child box
    BoxModel childBoxModel;

    public BoxModel getParentBoxModel() {
        return parentBoxModel;
    }

    public void setParentBoxModel(BoxModel parentBoxModel) {
        this.parentBoxModel = parentBoxModel;
    }

    public DotModel getDotModel() {
        if(dotModel==null){
            dotModel=new DotModel();
            dotModel.addToParents(parentBoxModel);
        }
        return dotModel;
    }

    public void setDotModel(DotModel dotModel) {
        this.dotModel = dotModel;
        this.dotModel.addToParents(parentBoxModel);
    }

    public AnchorModel getChildAnchorModel() {
        if(childAnchorModel==null){
            childAnchorModel=new AnchorModel();
            
        }
        return childAnchorModel;
    }

    public void setChildAnchorModel(AnchorModel childAnchorModel) {
        this.childAnchorModel = childAnchorModel;
    }

    public BoxModel getChildBoxModel() {
        return childBoxModel;
    }

    public void setChildBoxModel(BoxModel childBoxModel) {
        this.childBoxModel = childBoxModel;
        this.dotModel.addToChildren(this.childBoxModel);
    }
    
    
    
}

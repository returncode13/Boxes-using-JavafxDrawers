/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dot;

import boxes.BoxModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
class Link {
    BoxModel parent;
    BoxModel child;

    public BoxModel getParent() {
        return parent;
    }

    public void setParent(BoxModel parent) {
        this.parent = parent;
    }

    public BoxModel getChild() {
        return child;
    }

    public void setChild(BoxModel child) {
        this.child = child;
    }
    
    
}

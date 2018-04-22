/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.listener;

import db.model.Doubt;
import db.model.DoubtStatus;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */

public class DoubtUpdateListener implements PostUpdateEventListener{

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if(event.getEntity() instanceof DoubtStatus){
            DoubtStatus doubtStatus=(DoubtStatus) event.getEntity();
            System.out.println("db.listener.DoubtUpdateListener.onPostUpdate(): doubtStatus id: "+doubtStatus.getId()+" was updated " );
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        System.out.println("db.listener.DoubtUpdateListener.requiresPostCommitHanding(): returning blind true");
       return true;
    }
    
}

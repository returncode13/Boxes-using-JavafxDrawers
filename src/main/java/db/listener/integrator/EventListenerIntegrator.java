/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.listener.integrator;

import db.listener.DoubtUpdateListener;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class EventListenerIntegrator implements Integrator{

    @Override
    public void integrate(Configuration c, SessionFactoryImplementor sfi, SessionFactoryServiceRegistry sfsr) {
        EventListenerRegistry eventListenerRegistry=sfsr.getService(EventListenerRegistry.class);
        eventListenerRegistry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(new DoubtUpdateListener());
    }

    @Override
    public void integrate(MetadataImplementor mi, SessionFactoryImplementor sfi, SessionFactoryServiceRegistry sfsr) {
        EventListenerRegistry eventListenerRegistry=sfsr.getService(EventListenerRegistry.class);
        eventListenerRegistry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(new DoubtUpdateListener());
        
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sfi, SessionFactoryServiceRegistry sfsr) {
        
    }
    
}

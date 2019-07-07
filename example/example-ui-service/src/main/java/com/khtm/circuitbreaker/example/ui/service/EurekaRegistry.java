package com.khtm.circuitbreaker.example.ui.service;

import com.khtm.eureka.impl.EurekaService;
import org.xml.sax.SAXException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EurekaRegistry implements ServletContextListener {

    private static EurekaService eurekaService = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if(eurekaService == null) {
            eurekaService = new EurekaService("http://10.12.47.125", 8761, "10.12.46.147");
            try {
                eurekaService.registerServiceInEurekaService(
                        "example-client-ui",
                        9090,
                        "/example-ui-service/health",
                        "/example-ui-service/status",
                        "/example-ui-service/client-service");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            eurekaService.unregisterServiceFromEurekaService(eurekaService.applicationName, eurekaService.instanceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

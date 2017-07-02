package io.sato;

import io.sato.api.SatoRestApplication;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.test.TestPortProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Sato {

    private Logger logger = Logger.getLogger(Sato.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(Sato.class.getClassLoader().getResourceAsStream("logging.properties"));
        Sato sato = new Sato();
        sato.start();
    }

    private void start() throws IOException {

        long time = System.currentTimeMillis();
        UndertowJaxrsServer server = new UndertowJaxrsServer();

        logger.info("server created: " + (System.currentTimeMillis() - time) + " milli seconds");
        time = System.currentTimeMillis();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass(SatoRestApplication.class.getName());

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/api");
        deploymentInfo.setDisplayName("sato");
        deploymentInfo.setClassLoader(Sato.class.getClassLoader());
        deploymentInfo.setContextPath("/sato");
        deploymentInfo.setDeploymentName("SATO System Administration Tool");
        deploymentInfo.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(deploymentInfo);
        logger.info("server deployed: " + (System.currentTimeMillis() - time) + " milli seconds");

        server.start();

        time = System.currentTimeMillis();
        Client client = ClientBuilder.newClient();
        logger.info("client created: " + (System.currentTimeMillis() - time) + " milli seconds");

        time = System.currentTimeMillis();
        String response = client.target(TestPortProvider.generateURL("/cdi/api/inject/hi/JOHN")).request().get(String.class);
        logger.info("request completed: " + (System.currentTimeMillis() - time));
        logger.info("RESPONSE: " + response);

//        time = System.currentTimeMillis();
//        client.close();
//        logger.info("client closed: " + (System.currentTimeMillis() - time) + " milli seconds");
//
//        time = System.currentTimeMillis();
//        server.stop();
//        logger.info("server stopped: " + (System.currentTimeMillis() - time) + " milli seconds");
    }


}

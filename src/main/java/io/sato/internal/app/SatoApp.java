package io.sato.internal.app;

import com.fererlab.dispatch.service.EventDispatcher;
import com.fererlab.dispatch.util.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SatoApp {

    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static SatoApp instance = null;
    private Configuration configuration;

    private SatoApp() {
    }

    public static SatoApp getInstance() {
        if (instance == null) {
            synchronized (SatoApp.class) {
                if (instance == null) {
                    instance = new SatoApp();
                    instance.configure();
                    instance.start();
                }
            }
        }
        return instance;
    }

    private void configure() {
        // discover service classes
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configuration.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            configuration = (Configuration) jaxbUnmarshaller.unmarshal(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        EventDispatcher eventDispatcher = new EventDispatcher(configuration);
        executorService.execute(eventDispatcher);
    }

}

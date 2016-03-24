package io.sato.internal.app;

public class SatoApp {

    private SatoApp instance = null;

    private SatoApp() {
    }

    public SatoApp getInstance() {
        if (instance == null) {
            instance = new SatoApp();
            instance.configure();
            instance.start();
        }
        return instance;
    }

    private void configure() {

    }

    private void start() {

    }

}

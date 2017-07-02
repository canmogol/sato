package io.sato.api;

import io.sato.metrics.MetricsController;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class SatoRestApplication extends Application {

    public SatoRestApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("QMenu API");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("localhost:8090");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.eigonic.qmenu.core.api.rest");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        resources.add(MetricsController.class);
        return resources;
    }


}

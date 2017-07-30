package io.sato.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.sato.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by Can A MOGOL on 12.07.2017
 */
@Configuration
@EnableSwagger2
@PropertySource("swagger.properties")
public class SwaggerConfiguration {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${swagger.contact}")
    private String contact;

    @Value("${swagger.license}")
    private String license;

    @Value("${swagger.licenseUrl}")
    private String licenseUrl;

    @Value("${swagger.version}")
    private String version;


    @Bean
    public Docket swaggerDocket() {
        @SuppressWarnings("unchecked")
        Predicate<RequestHandler> or = Predicates.or(
          RequestHandlerSelectors.basePackage(
            BaseController.class.getPackage().getName()
          )
        );
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(or)
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
          .title(title)
          .description(description)
          .termsOfServiceUrl(termsOfServiceUrl)
          .contact(contact)
          .license(license)
          .licenseUrl(licenseUrl)
          .version(version)
          .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(
          null,
          null,
          null,
          null,
          "",
          ApiKeyVehicle.HEADER,
          "X-API-Key",
          ","
        );
    }


}
/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Value("${swagger.external.path}")
	private String externalPath;

	@Value("${swagger.is.enabled}")
	private boolean isEnabled;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(externalPath)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("mx.com.anzen.app.abanking.fake.user.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				.enable(isEnabled);		
	}

	/**
	 * <p>TODO [Add comments of method]</p>
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ApiInfo metaData() {
		Collection<VendorExtension> vendor = new ArrayList<>();
		
		return new ApiInfo("Abanking fake user ", "Get fake user when login is wrong",
				"1.0.0", "http://www.anzen.com.mx",
				new Contact("Anzen Digital", "https://www.anzen.com.mx/", "contacto@anzen.com.mx"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", vendor);
	}

}

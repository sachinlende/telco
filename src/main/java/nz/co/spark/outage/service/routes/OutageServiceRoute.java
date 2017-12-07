package nz.co.spark.outage.service.routes;

import nz.co.spark.data.model.Person;
import nz.co.spark.outage.service.beans.ResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OutageServiceRoute extends RouteBuilder {


    @Autowired
    ResponseProcessor responseProcessor;


    private static final DataFormat jacksonDataFormat = new JacksonDataFormat(Person.class);


    public void configure() throws Exception {



        restConfiguration().component("jetty")
                .scheme("{{common.http_protocol}}")
                .bindingMode(RestBindingMode.off)
                .host("{{common.http_host}}")
                .port("{{common.http_port}}")
                .contextPath("/")
        ;


        from("timer://foo?period=15000")
                .id("timer-routes")
                .log(">>> ${body}");



        rest("/outage/{entityId}")
                .get()
                .id("sayRoute")
                .consumes("application/json")
                .produces("application/json")
                .param().name("entityId").type(RestParamType.path).endParam()
                .route()
                    .log("log ${body}")
                    .bean(responseProcessor, "transformResponse")
                    .marshal(jacksonDataFormat)
                .endRest();
        ;
    }
}

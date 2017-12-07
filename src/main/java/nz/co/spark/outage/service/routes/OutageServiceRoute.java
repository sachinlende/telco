package nz.co.spark.outage.service.routes;

import nz.co.spark.data.model.Person;
import nz.co.spark.outage.service.beans.ResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
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
                .scheme("http")
                .bindingMode(RestBindingMode.off)
                .host("localhost")
                .port("9082")
                .contextPath("/")
        ;


        from("timer://foo?period=15000")

                .id("timer-routes")
                .log(">>> ${body}")

                .log(">>> ${body}");


        rest("/outage")
                .get()
                .id("sayRoute")
                .consumes("application/json")
                .produces("application/json")
                .route()
                    .log("log ${body}")
                    .bean(responseProcessor, "transformResponse")
                    .marshal(jacksonDataFormat)
                    //.setBody(simple("{\"hello\":\"telecom\"}"))
                    //.transform(method("myBean", "saySomething"))
                // .to(DIRECT_MAIN_ROUTE)
                .endRest();
        ;
    }
}

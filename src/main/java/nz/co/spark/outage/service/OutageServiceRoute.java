package nz.co.spark.outage.service;

import nz.co.spark.data.OracleRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutageServiceRoute extends RouteBuilder {

    @Autowired
    OracleRepository oracleRepository;

    public void configure() throws Exception {
        from("timer://foo?period=10000")

                .id("timer-route")
                .log(">>> ${body}")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        oracleRepository.invokeSP();

                    }
                })
                //.to("sql-stored:GETPERSONPCKG.GETPERSON(OUT OracleTypes.CURSOR outheader1)")
               /* .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("hello");

                    }
                })*/
                .log(">>> ${body}");
    }
}

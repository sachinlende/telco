package nz.co.spark.outage.service.beans;


import nz.co.spark.data.access.OracleRepository;
import nz.co.spark.data.model.Person;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class ResponseProcessor {


    @Autowired
    OracleRepository oracleRepository;

    public void transformResponse(Exchange ex) throws Exception{
        int idRequest = (Integer.parseInt((String) ex.getIn().getHeader("entityId")));
        List<Object[]> storedProcedureResults = oracleRepository.invokeSP(idRequest);

        List<Person> personList = new ArrayList<>();

        //for(Object[] currentRecord : storedProcedureResults){
        Object[] record = storedProcedureResults.get(0);
            Person person = new Person();
            BigDecimal id = (BigDecimal) record[0];
            String idString = id.toString();
            String firstName = (String) record[1];
            String lastName = (String) record[2];

            //System.out.println(name);
            person.setId(idString);
            person.setFirstname(firstName);
            person.setLastname(lastName);

        //}


        ex.getIn().setBody(person);

    }

}

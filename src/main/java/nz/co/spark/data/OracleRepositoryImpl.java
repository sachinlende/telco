package nz.co.spark.data;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class OracleRepositoryImpl implements OracleRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void invokeSP() {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("GETPERSONPCKG.GETPERSON");

        // Set the parameters of the stored procedure.
        storedProcedure.registerStoredProcedureParameter("p_ref"  , Class.class, ParameterMode.REF_CURSOR);

        // Call the stored procedure.
        List<Object[]> storedProcedureResults = storedProcedure.getResultList();

        for(Object[] myrecord : storedProcedureResults){
            String name = (String) myrecord[1];

            System.out.println(name);
        }

        //Object[]  myrecord = storedProcedureResults.get(0);

        //storedProcedureResults.isEmpty();

        // Use Java 8's cool new functional programming paradigm to map the objects from the stored procedure results
            /*return storedProcedureResults.stream().map(result -> new MyObject(
                    (Integer) result[0],
                    (String) result[1]
            )).collect(Collectors.toList());*/

    }
}


package nz.co.spark.data.access;

import org.omg.PortableInterceptor.INACTIVE;
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

    public List<Object[]> invokeSP(int id) {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("GETPERSONPCKG.GETPERSON");
        // Set the parameters of the stored procedure.
        storedProcedure.registerStoredProcedureParameter("p_ref"  , Class.class, ParameterMode.REF_CURSOR);
        storedProcedure.registerStoredProcedureParameter("p_id", Integer.class , ParameterMode.IN);

        storedProcedure.setParameter("p_id", id);

        // Call the stored procedure.
        List<Object[]> storedProcedureResults = storedProcedure.getResultList();

        return storedProcedureResults;
    }
}


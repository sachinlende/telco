package nz.co.spark.data.access;

import java.util.List;

public interface OracleRepository {


    public List<Object[]> invokeSP(int id);
}

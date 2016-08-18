package runner;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.TabularResult;

public class Sandbox {
  
  public static void main(String[] args) {
    QuandlSession session = QuandlSession.create();
    TabularResult tabularResult = session.getDataSet(
      DataSetRequest.Builder.of("ZILL/N01663_A").build());
    System.out.println(tabularResult.toPrettyPrintedString());
  }
  
}

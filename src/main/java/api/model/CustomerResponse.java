package api.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CustomerResponse {

  private String buildVersion;
  private String endpointVersion;
  private List<ResponseError> errors;
  private List<ResponseInfo> infos;
  private List<Customer> results;

}

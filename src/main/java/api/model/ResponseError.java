package api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResponseError {

  private String code;
  private String detail;
  private String stackTrace;
  private String summary;
  private String traceId;

}

package testdata;

import lombok.Getter;
import lombok.Setter;

public class CustomerNumber {

  private static CustomerNumber instance = null;

  @Getter
  @Setter
  public String customerNumber;

  public static CustomerNumber getInstance() {
    if (instance == null) {
      instance = new CustomerNumber();
    }

    return instance;
  }

}

package demo.loan.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LoanDTO {

  private long id;
  private String name;
  private BigDecimal amount;
  private double interest;
  private String startDate;
  private String endDate;
  private long days;
  private long daysLeft;

}

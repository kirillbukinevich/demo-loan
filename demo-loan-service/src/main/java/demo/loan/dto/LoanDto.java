package demo.loan.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {

  private long id;
  private String name;
  private BigDecimal amount;
  private double interest;
  private String startDate;
  private String endDate;
  private long days;
  private long daysLeft;

}

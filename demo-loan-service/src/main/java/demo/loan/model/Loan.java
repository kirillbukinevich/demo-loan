package demo.loan.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOAN", schema="SYSTEM")
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "NAME")
  @NotNull(message = "Name is mandatory")
  private String name;
  
  @Column(name = "AMOUNT")
  @NotNull(message = "Amount is mandatory")
  private BigDecimal amount;
  
  @Column(name = "INTEREST")
  @NotNull(message = "Interest is mandatory")
  private double interest;

  @Column(name = "START_DATE")
  @NotNull(message = "Start date is mandatory")
  private Date startDate;

  @Column(name = "END_DATE")
  @NotNull(message = "End date is mandatory")
  private Date endDate;

}

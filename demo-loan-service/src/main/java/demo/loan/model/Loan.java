package demo.loan.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "LOAN", schema="SYSTEM")
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "NAME")
  @NonNull
  private String name;
  
  @Column(name = "AMOUNT")
  @NonNull
  private BigDecimal amount;
  
  @Column(name = "INTEREST")
  private double interest;

  @Column(name = "START_DATE")
  @NonNull
  private Date startDate;

  @Column(name = "END_DATE")
  @NonNull
  private Date endDate;

}

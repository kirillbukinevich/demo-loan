package demo.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.loan.model.Loan;

public interface LoanDAO extends JpaRepository<Loan, Long> {

}

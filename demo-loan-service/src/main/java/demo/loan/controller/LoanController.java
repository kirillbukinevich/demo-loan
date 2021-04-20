package demo.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.loan.dto.LoanDTO;
import demo.loan.model.Loan;
import demo.loan.service.LoanService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/loan")
public class LoanController {

  @Autowired private LoanService loanService;

  @GetMapping
  public List<LoanDTO> getAllLoans() {
    log.info("find all loan");
    List<LoanDTO> result = loanService.getAllLoans();
    return result;
  }

  @GetMapping("/{id}")
  public LoanDTO getLoanById(@PathVariable Long id) {
    log.info("getLoanBy Id: " + id);
    return loanService.getLoanById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Loan save(@RequestBody Loan loan) {
    log.info("save loan: " + loan);
    return loanService.saveLoan(loan);
  }
  
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public Loan update(@RequestBody Loan loan) {
    log.info("update loan: " + loan);
    return loanService.saveLoan(loan);
  }
  
  
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteLoanById(@PathVariable Long id) {
    log.info("delete:" + id);
    loanService.deleteLoanById(id);
  }
}

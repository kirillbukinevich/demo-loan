package demo.loan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.loan.dto.LoanDTO;
import demo.loan.model.Loan;
import demo.loan.service.LoanService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin(origins = "*")
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

  @PostMapping("/save-or-update")
  public ResponseEntity<Loan> saveOfUpdateLoan(@RequestBody Loan loan) {
    log.info("save-or-update loan: " + loan);
    return ResponseEntity.ok(loanService.saveOfUpdateLoan(loan));
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteLoanById(@PathVariable Long id) {
    log.info("delete:" + id);
    loanService.deleteLoanById(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}

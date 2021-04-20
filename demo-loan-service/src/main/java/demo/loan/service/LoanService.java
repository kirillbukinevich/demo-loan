package demo.loan.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import demo.loan.dao.LoanDAO;
import demo.loan.dto.LoanDTO;
import demo.loan.model.Loan;
import demo.loan.util.DateUtil;

@Service
public class LoanService {

  @Autowired private LoanDAO loanDAO;

  private ModelMapper modelMapper = new ModelMapper();

  public List<LoanDTO> getAllLoans() {
    List<LoanDTO> result =
        loanDAO.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    return result;
  }

  public LoanDTO getLoanById(@PathVariable Long id) {
    return this.convertToDto(loanDAO.getOne(id));
  }

  public Loan saveOrUpdateLoan(@RequestBody Loan loan) {
    return loanDAO.save(loan);
  }

  public void deleteLoanById(@PathVariable Long id) {
    loanDAO.deleteById(id);
  }

  private LoanDTO convertToDto(Loan loan) {
    LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
    loanDTO.setStartDate(DateUtil.getYYYYMMDDString(loan.getStartDate()));
    loanDTO.setEndDate(DateUtil.getYYYYMMDDString(loan.getEndDate()));
    loanDTO.setDays(DateUtil.getDiffDaysBetweenDate(loan.getStartDate(), loan.getEndDate()));
    loanDTO.setDaysLeft(DateUtil.getDiffDaysBetweenDate(new Date(), loan.getEndDate()));
    return loanDTO;
  }
}

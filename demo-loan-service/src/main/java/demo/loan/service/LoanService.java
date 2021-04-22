package demo.loan.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.loan.dao.LoanDAO;
import demo.loan.dto.LoanDto;
import demo.loan.model.Loan;
import demo.loan.util.DateUtil;

@Service
public class LoanService {

  @Autowired private LoanDAO loanDAO;

  private ModelMapper modelMapper = new ModelMapper();

  public List<LoanDto> getAllLoans() {
    List<LoanDto> result =
        loanDAO.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    return result;
  }

  public LoanDto getLoanById(Long id) {
    return this.convertToDto(loanDAO.findById(id).get());
  }

  public Loan saveLoan(Loan loan) {
    return loanDAO.save(loan);
  }

  public void deleteLoanById(Long id) {
    loanDAO.deleteById(id);
  }

  private LoanDto convertToDto(Loan loan) {
    LoanDto loanDTO = modelMapper.map(loan, LoanDto.class);
    loanDTO.setStartDate(DateUtil.getYYYYMMDDString(loan.getStartDate()));
    loanDTO.setEndDate(DateUtil.getYYYYMMDDString(loan.getEndDate()));
    loanDTO.setDays(DateUtil.getDiffDaysBetweenDate(loan.getStartDate(), loan.getEndDate()));
    loanDTO.setDaysLeft(DateUtil.getDiffDaysBetweenDate(new Date(), loan.getEndDate()));
    return loanDTO;
  }
}

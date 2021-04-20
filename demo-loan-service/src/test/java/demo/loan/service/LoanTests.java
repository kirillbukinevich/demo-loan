package demo.loan.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import demo.loan.dao.LoanDAO;
import demo.loan.model.Loan;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoanTests {

    @Autowired
    private LoanDAO loanDAO;
    
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetAllLoans(){
    	Loan loan = new Loan(999999999998L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
    	Loan loan2 = new Loan(999999999999L,"Test2",BigDecimal.ONE,5.2,new Date(),new Date());
        
    	loanDAO.save(loan);
    	loanDAO.save(loan2);

        Assert.assertNotNull(loanDAO.findAll());
    }
    
    @Test
    public void testGetLoanById(){
    	Loan loan = new Loan(999999999998L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
    	Loan loan2 = new Loan(999999999999L,"Test2",BigDecimal.ONE,5.2,new Date(),new Date());
        
    	loanDAO.save(loan);
    	loanDAO.save(loan2);

    	loanDAO.findById(999999999999L)
                .map(newLoan ->{
                   Assert.assertSame(5.2,newLoan.getInterest());
                   return true;
                });
    }
    
    @Test
    public void testSaveLoan(){
        Loan loan = new Loan(999999999999L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
        loanDAO.save(loan);
        loanDAO.findById(999999999999L).map(
        		newLoan -> {
                    Assert.assertEquals("Test",newLoan.getName());
                    return true;
        		});
    }
    
    @Test
    public void testDeleteLoanById(){
    	Loan loan = new Loan(0L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
    	
    	Long id = entityManager.persistAndGetId(loan, Long.class);
    	entityManager.flush();
    	
    	loanDAO.deleteById(id);

    	Assert.assertTrue(loanDAO.findById(id).isEmpty());
    }
    


}

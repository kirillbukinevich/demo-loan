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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoanTests {

    @Autowired
    private LoanDAO loanDAO;
    
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getAllLoans(){
    	Loan loan = new Loan(0L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
    	Loan loan2 = new Loan(0L,"Test2",BigDecimal.ONE,5.2,new Date(),new Date());
    	
    	entityManager.persist(loan);
    	entityManager.persist(loan2);
    	entityManager.flush();
    	
        Assert.assertNotNull(loanDAO.findAll());
    }
    
    @Test
    public void getLoanById(){
    	Loan loan = new Loan(0L,"Test",BigDecimal.ONE,5.2,new Date(),new Date());
        
    	Long id = entityManager.persistAndGetId(loan, Long.class);
    	entityManager.flush();
    	
    	loanDAO.findById(id)
                .map(newLoan ->{
                   Assert.assertEquals(5.2,newLoan.getInterest(),0.001);
                   return true;
                });
    }
    
    @Test
    public void saveLoan(){
        Loan loan = new Loan(0L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
        loan = loanDAO.saveAndFlush(loan);
        loanDAO.findById(loan.getId()).map(
        		newLoan -> {
                    Assert.assertEquals("Test",newLoan.getName());
                    return true;
        		});
    }

    @Test
    public void updateLoan(){
        Loan loan = new Loan(0L,"Test",BigDecimal.ONE,3.2,new Date(),new Date());
        Long id = entityManager.persistAndGetId(loan, Long.class);
    	entityManager.flush();
    	
        Loan loanForUpdate = loanDAO.findById(id).get();
        loanForUpdate.setName("NewName");
        
        entityManager.persist(loanForUpdate);
    	entityManager.flush();
    	
        
        loanDAO.findById(id).map(
        		updatedLoan -> {
                    Assert.assertEquals("NewName",updatedLoan.getName());
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

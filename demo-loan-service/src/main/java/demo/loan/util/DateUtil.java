package demo.loan.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateUtil {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	
	private DateUtil() {}
	
    public static long getDiffDaysBetweenDate(Date fromDate, Date toDate) {
    	LocalDate fromLocalDate = fromDate.toInstant()
    		      .atZone(ZoneId.systemDefault())
    		      .toLocalDate();
    	LocalDate toLocalDate = toDate.toInstant()
  		      .atZone(ZoneId.systemDefault())
  		      .toLocalDate();
    	return ChronoUnit.DAYS.between(fromLocalDate,toLocalDate);
    }
    
    public static String getYYYYMMDDString(Date date) {
    	LocalDate localDate = date.toInstant()
    		      .atZone(ZoneId.systemDefault())
    		      .toLocalDate();
    	return localDate.format(formatter);
    }
    
}

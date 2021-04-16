package demo.loan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins = "*")
@RestController
@Log4j2
public class WebController {

  @Autowired HttpServletRequest httpServletRequest;

  @Autowired HttpServletResponse httpServletResponse;

  @GetMapping(path = "/login")
  public void index() {
    try {
      httpServletResponse.sendRedirect("http://localhost:3000");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

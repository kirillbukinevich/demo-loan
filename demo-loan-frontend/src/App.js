import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListLoanComponent from './components/ListLoanComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateLoanComponent from './components/CreateLoanComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListLoanComponent}></Route>
                          <Route path = "/loans" component = {ListLoanComponent}></Route>
                          <Route path = "/add-loan/:id" component = {CreateLoanComponent}></Route>
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;

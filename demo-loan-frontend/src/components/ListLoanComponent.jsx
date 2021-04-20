import React, { Component } from 'react'
import LoanService from '../services/LoanService'
import { withKeycloak } from '@react-keycloak/web'
import { withRouter } from 'react-router-dom'

class ListLoanComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                loans: []
        }
        this.addLoan = this.addLoan.bind(this);
        this.editLoan = this.editLoan.bind(this);
        this.deleteLoan = this.deleteLoan.bind(this);
    }

    deleteLoan(id){
        const { keycloak } = this.props

        LoanService.deleteLoan(id, keycloak.token).then( res => {
            this.setState({loans: this.state.loans.filter(loan => loan.id !== id)});
        });
    }
    editLoan(id){
        this.props.history.push(`/add-loan/${id}`);
    }

    async componentDidMount(){   
        const { keycloak } = this.props
        
        LoanService.getLoans(keycloak.token).then((res) => {
            console.log(Array.isArray(res.data));
            if(Array.isArray(res.data)) {
                this.setState({ loans: res.data});
            } else {
                window.location.href = 'http://localhost:8081/login'; 
            }
        });
    }

    addLoan(){
        this.props.history.push('/add-loan/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Loans List</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addLoan}> Add Loan</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Name</th>
                                    <th> Amount</th>
                                    <th> Interest</th>
                                    <th> Start date</th>
                                    <th> End date</th>
                                    <th> Amount of days</th>
                                    <th> Amount of days left</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.loans.map(
                                        loan => 
                                        <tr key = {loan.id}>
                                             <td> { loan.name} </td>   
                                             <td> {loan.amount}</td>
                                             <td> {loan.interest}</td>
                                             <td> {loan.startDate}</td>
                                             <td> {loan.endDate}</td>
                                             <td> {loan.days}</td>
                                             <td> {loan.daysLeft}</td>
                                             <td>
                                                 <button onClick={ () => this.editLoan(loan.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteLoan(loan.id)} className="btn btn-danger">Delete </button>
                                                </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default withRouter(withKeycloak(ListLoanComponent))

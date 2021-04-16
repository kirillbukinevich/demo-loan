import React, { Component } from 'react'
import LoanService from '../services/LoanService';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class CreateLoanComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            name: '',
            amount: '',
            interest: '',
            startDate: '',
			endDate: ''
        }
        
    }


    componentDidMount() {

        if (this.state.id === '_add') {
            return
        } else {
            LoanService.getLoanById(this.state.id).then((res) => {
                console.log(res);
                console.log(res.data);
                let loan = res.data;
                if(!loan.name) {
                    window.location.href = 'http://localhost:8081/login'; 
                }
                console.log(loan);
                console.log(this.convertStringDateToDate(loan.startDate));
                this.setState({
                    id: this.state.id,
                    name: loan.name,
                    amount: loan.amount,
                    interest: loan.interest,
                    startDate: this.convertStringDateToDate(loan.startDate),
                    endDate: this.convertStringDateToDate(loan.endDate)
                });
            });
        }
    }
    saveOrUpdateLoan = (e) => {
        e.preventDefault();
        if (this.state.id === '_add') {
            this.state.id = 0;
        }
        let loan = { id: this.state.id,  name: this.state.name, amount: this.state.amount, interest: this.state.interest, 
            startDate: this.state.startDate, endDate: this.state.endDate };
        console.log('loan => ' + JSON.stringify(loan));

        LoanService.saveOrUpdateLoan(loan).then(res => {
            this.props.history.push('/loans');
        });

    }

     convertStringDateToDate = (date) => {
        let splittedDate = date.split(".");
        console.log(splittedDate)
        return new Date(splittedDate[2],--splittedDate[1],splittedDate[0],);
        }

    changeNameHandler = (event) => {
        this.setState({ name: event.target.value });
    }

    changeAmountHandler = (event) => {
        this.setState({ amount: event.target.value });
    }

    changeInterestHandler = (event) => {
        this.setState({ interest: event.target.value });
    }

    changeStartDateHandler = (date) => {
        this.setState({ startDate: date });
    }

    changeEndDateHandler = (date) => {
        this.setState({ endDate: date });
    }

    cancel() {
        this.props.history.push('/loans');
    }

    getTitle() {
        if (this.state.id === '_add') {
            return <h3 className="text-center">Add Loan</h3>
        } else {
            return <h3 className="text-center">Update Loan</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {
                                this.getTitle()
                            }
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> Name: </label>
                                        <input placeholder="Name" name="name" className="form-control"
                                            value={this.state.name} onChange={this.changeNameHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> Loan amount: </label>
                                        <input placeholder="Loan amount" name="amount" className="form-control"
                                            value={this.state.amount} onChange={this.changeAmountHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> Loan interest: </label>
                                        <input placeholder="Loan interest" name="interest" className="form-control"
                                            value={this.state.interest} onChange={this.changeInterestHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> Start date: </label>
                                        <br/>
                                        <DatePicker dateFormat="dd.MM.yyyy" placeholder="Start date" name="startDate" className="form-control" 
                                            selected={this.state.startDate} onChange={date =>this.changeStartDateHandler(date)} />
                                    </div>
                                    <div className="form-group">
                                        <label> End date: </label>
                                        <br/>
                                        <DatePicker dateFormat="dd.MM.yyyy" placeholder="End date" name="endDate" className="form-control" 
                                            selected={this.state.endDate} onChange={date =>this.changeEndDateHandler(date)} />
                                    </div>


                                    <button className="btn btn-success" onClick={this.saveOrUpdateLoan}>Save</button>
                                    <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{ marginLeft: "10px" }}>Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default CreateLoanComponent

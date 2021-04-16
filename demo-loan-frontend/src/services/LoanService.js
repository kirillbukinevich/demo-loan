import axios from 'axios';

const LOAN_API_BASE_URL = "http://localhost:8081";

class LoanService {

    getLoans(){
        return axios.get(LOAN_API_BASE_URL + "/api/loan");
    }

    getLoanById(loanId){
        return axios.get(LOAN_API_BASE_URL + '/api/loan/' + loanId);
    }

    saveOrUpdateLoan(loan){
        return axios.post(LOAN_API_BASE_URL + '/api/loan/save-or-update', loan);
    }

    deleteLoan(loanId){
        return axios.post(LOAN_API_BASE_URL + '/api/loan/delete/' + loanId);
    }

    getLogin() {
        return axios.get(LOAN_API_BASE_URL + "/login")
    }
}

export default new LoanService()
import axios from 'axios';
import { config } from '../Constants'

class LoanService {

    getLoans(token) {
        return instance.get(config.url.API_BASE_URL + "/api/loan", {
            headers: {
                'Content-type': 'application/json',
                'Authorization': bearerAuth(token)
            }
        })
    }

    getLoanById(loanId, token) {
        return instance.get(config.url.API_BASE_URL + '/api/loan/' + loanId, {
            headers: {
                'Content-type': 'application/json',
                'Authorization': bearerAuth(token)
            }
        })
    }

    saveOrUpdateLoan(loan, token) {
        return instance.post(config.url.API_BASE_URL + '/api/loan/save-or-update', loan, {
            headers: {
                'Content-type': 'application/json',
                'Authorization': bearerAuth(token)
            }
        })
    }

    deleteLoan(loanId, token) {
        return instance.delete(config.url.API_BASE_URL + '/api/loan/delete/' + loanId, {
            headers: { 'Authorization': bearerAuth(token) }
        })
    }

}

// -- Axios

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
  })
  
//   instance.interceptors.response.use(response => {
//     return response;
//   }, function (error) {
//     if (error.response.status === 404) {
//       return { status: error.response.status };
//     }
//     return Promise.reject(error.response);
//   });

// -- Helper functions

function bearerAuth(token) {
    return `Bearer ${token}`
}

export default new LoanService()
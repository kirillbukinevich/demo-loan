import React, { Component } from 'react'
import LoanService from '../services/LoanService'

class HeaderComponent extends Component {
    constructor(props) {
        super(props)
        LoanService.getLogin().then((res) => {
            console.log(res);
        });

        this.state = {
                 
        }
    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div className="navbar-brand">Demo loans project</div>
                    </nav>
                </header>
            </div>
        )
    }
}

export default HeaderComponent

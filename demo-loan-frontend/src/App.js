import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListLoanComponent from './components/ListLoanComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateLoanComponent from './components/CreateLoanComponent';
import { config } from './Constants'
import Keycloak from 'keycloak-js'
import { Dimmer, Header, Icon } from 'semantic-ui-react'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import PrivateRoute from './components/misc/PrivateRoute'

function App() {
  const keycloak = new Keycloak({
    url: `${config.url.KEYCLOAK_BASE_URL}/auth`,
    realm: "company-services",
    clientId: "login-app"
  })
  const initOptions = { pkceMethod: 'S256' }

  const handleOnEvent = async (event, error) => {
    if (event === 'onAuthSuccess') {
      if (keycloak.authenticated) {
      }
    }
  }

  const loadingComponent = (
    <Dimmer inverted active={true} page>
      <Header style={{ color: '#4d4d4d' }} as='h2' icon inverted>
        <Icon loading name='cog' />
        <Header.Content>Keycloak is loading
          <Header.Subheader style={{ color: '#4d4d4d' }}>or running authorization code flow with PKCE</Header.Subheader>
        </Header.Content>
      </Header>
    </Dimmer>
  )

  return (
    <div>
      <ReactKeycloakProvider
        authClient={keycloak}
        initOptions={initOptions}
        LoadingComponent={loadingComponent}
        onEvent={(event, error) => handleOnEvent(event, error)}
      >
        <Router>
          <HeaderComponent />
          <div className="container">
            <Switch>
              <PrivateRoute path="/add-loan/:id" component={CreateLoanComponent} />
              <PrivateRoute path="/" component={ListLoanComponent} />
              <PrivateRoute path="/loans" component={ListLoanComponent} />
            </Switch>
          </div>
          <FooterComponent />
        </Router>
      </ReactKeycloakProvider>
    </div>

  );
}

export default App;

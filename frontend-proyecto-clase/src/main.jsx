import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { Auth0Provider } from '@auth0/auth0-react';

import App from './App';
import './index.css';
import './App.css';
import { auth0Config, buildLoginScope } from './config/auth0';

const container = document.getElementById('root');
const root = createRoot(container);

const loginScope = buildLoginScope();

if (!auth0Config.domain || !auth0Config.clientId) {
  console.warn('Auth0 configuration is missing VITE_AUTH0_DOMAIN and/or VITE_AUTH0_CLIENT_ID.');
}
root.render(
  <React.StrictMode>
    <Auth0Provider
      domain={auth0Config.domain}
      clientId={auth0Config.clientId}
      authorizationParams={{
        redirect_uri: window.location.origin,
        ...(auth0Config.audience ? { audience: auth0Config.audience } : {}),
        scope: loginScope,
      }}
      useRefreshTokens
      cacheLocation="localstorage"
      useRefreshTokensFallback
    >
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </Auth0Provider>
  </React.StrictMode>,
);

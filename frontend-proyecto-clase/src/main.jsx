import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import App from './App';

// estilos globales (asegúrate de tener estos archivos)
import './index.css';
import './App.css';

/**
 * Punto de entrada de la app.
 * Usamos BrowserRouter aquí para que toda la app tenga routing.
 */
const container = document.getElementById('root');
const root = createRoot(container);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);

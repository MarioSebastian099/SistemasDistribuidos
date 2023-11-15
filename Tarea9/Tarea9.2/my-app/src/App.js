import React, { useState } from 'react';
import 'materialize-css/dist/css/materialize.min.css';
import CaptureItems from './components/CaptureItems';
import PurchaseItems from './components/PurchaseItems';

const App = () => {
  const [currentScreen, setCurrentScreen] = useState('');

  const handleCaptureButtonClick = () => {
    setCurrentScreen('captureItems');
  };

  const handlePurchaseButtonClick = () => {
    setCurrentScreen('purchaseItems');
  };

  const renderCurrentScreen = () => {
    switch (currentScreen) {
      case 'captureItems':
        return <CaptureItems />;
      case 'purchaseItems':
        return <PurchaseItems />;
      default:
        return null;
    }
  };

  return (
    <div className="container">
      <h1>Bienvenido al Carrito de Compras</h1>
      <div className="row">
        <div className="col s6">
          <div className="card blue-grey darken-1">
            <div className="card-content white-text">
              <span className="card-title">Captura de Artículo</span>
              <p>Aquí puedes capturar un nuevo artículo.</p>
            </div>
            <div className="card-action">
              <button className="btn" onClick={handleCaptureButtonClick}>
                Captura de Artículo
              </button>
            </div>
          </div>
        </div>
        <div className="col s6">
          <div className="card blue-grey darken-1">
            <div className="card-content white-text">
              <span className="card-title">Compra de Artículos</span>
              <p>Aquí puedes buscar y comprar artículos.</p>
            </div>
            <div className="card-action">
              <button className="btn" onClick={handlePurchaseButtonClick}>
                Compra de Artículos
              </button>
            </div>
          </div>
        </div>
      </div>
      {renderCurrentScreen()}
    </div>
  );
};

export default App;

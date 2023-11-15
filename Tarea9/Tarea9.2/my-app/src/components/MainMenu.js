import React, { useState } from 'react';
import CaptureItems from './CaptureItems';
import PurchaseItems from './PurchaseItems';

const MainMenu = () => {
  const [currentPage, setCurrentPage] = useState('');

  const handleOptionClick = (option) => {
    setCurrentPage(option);
  };

  const renderOptions = () => {
    return (
      <div>
        <button onClick={() => handleOptionClick('capture')}>Captura de artículo</button>
        <button onClick={() => handleOptionClick('purchase')}>Compra de artículos</button>
      </div>
    );
  };

  const renderCurrentPage = () => {
    // Implementa la lógica para renderizar la página actual según la opción seleccionada
  };

  return (
    <div>
      <h1>Carrito de Compras</h1>
      {renderCurrentPage()}
    </div>
  );
};

export default MainMenu;

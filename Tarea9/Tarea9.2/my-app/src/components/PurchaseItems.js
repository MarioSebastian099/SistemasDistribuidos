import React, { useState, useEffect } from 'react';
import M from 'materialize-css';


const PurchaseItems = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [itemQuantity, setItemQuantity] = useState(1);
  const [showDescription, setShowDescription] = useState(false);
  const [showCart, setShowCart] = useState(false);

  const handleSearch = () => {
    // Aquí puedes implementar la lógica para buscar los artículos en la base de datos
    // utilizando la instrucción SELECT con LIKE en la tabla "articulos"
    // y actualizar los resultados en el estado `searchResults`
  };

  const handleItemSelect = (item) => {
    setSelectedItem(item);
    setItemQuantity(1);
    setShowDescription(false);
  };

  const handlePurchase = () => {
    // Aquí puedes implementar la lógica para agregar el artículo al carrito de compra
    // e insertar el registro correspondiente en la tabla "carrito_compra"
    // y actualizar la cantidad en la tabla "articulos"
  };

  const handleDescription = () => {
    setShowDescription(true);
  };

  const handleQuantityChange = (event) => {
    setItemQuantity(parseInt(event.target.value));
  };

  const handleViewCart = () => {
    setShowCart(true);
  };

  // Inicializar los componentes de Materialize CSS
  useEffect(() => {
    M.AutoInit();
  }, []);

  return (
    <div>
      <h2>Compra de artículos</h2>
      <input
        type="text"
        placeholder="Buscar artículo por nombre o descripción"
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
      />
      <button className="waves-effect waves-light btn" onClick={handleSearch}>
        Buscar
      </button>

      <div>
        {searchResults.map((item) => (
          <div key={item.id}>
            <h3>{item.name}</h3>
            <p>{item.description}</p>
            <p>Precio: {item.price}</p>
            <button
              className="waves-effect waves-light btn"
              onClick={() => handleItemSelect(item)}
            >
              Comprar
            </button>
          </div>
        ))}
      </div>

      {selectedItem && (
        <div>
          <h3>{selectedItem.name}</h3>
          <p>{selectedItem.description}</p>
          <p>Precio: {selectedItem.price}</p>
          <input
            type="number"
            value={itemQuantity}
            onChange={handleQuantityChange}
          />
          <button
            className="waves-effect waves-light btn"
            onClick={handlePurchase}
          >
            Agregar al carrito
          </button>
          <button
            className="waves-effect waves-light btn"
            onClick={handleDescription}
          >
            Descripción
          </button>
        </div>
      )}

      {showDescription && selectedItem && (
        <div>
          <h3>{selectedItem.name}</h3>
          <p>{selectedItem.description}</p>
          <button
            className="waves-effect waves-light btn"
            onClick={() => setShowDescription(false)}
          >
            Cerrar
          </button>
        </div>
      )}

      {showCart && (
        <div>
          <h2>Artículos en el carrito</h2>
          {/* Aquí puedes mostrar la lista de artículos en el carrito de compra */}
          <button
            className="waves-effect waves-light btn"
            onClick={() => setShowCart(false)}
          >
            Seguir comprando
          </button>
        </div>
      )}

      {!showCart && (
        <button
          className="waves-effect waves-light btn"
          onClick={handleViewCart}
        >
          Carrito de compra
        </button>
      )}
    </div>
  );
};

export default PurchaseItems;

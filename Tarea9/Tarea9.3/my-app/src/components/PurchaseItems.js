import React, { useState, useEffect } from 'react';
import M from 'materialize-css';

const PurchaseItems = ({ articles, setArticles }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [itemQuantity, setItemQuantity] = useState(1);
  const [showCart, setShowCart] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const [selectedDescription, setSelectedDescription] = useState('');
  const [continueShopping, setContinueShopping] = useState(true);

  const handleSearch = () => {
    if (!articles) return;

    const results = articles.filter(
      (article) =>
        article.name.includes(searchQuery) ||
        article.description.includes(searchQuery)
    );
    setSearchResults(results);
  };

  const handlePurchase = (article) => {
    if (itemQuantity <= 0) {
      alert("La cantidad debe ser mayor a 0")
      M.toast({ html: 'La cantidad debe ser mayor a cero' });
      return;
    }

    const checkItems = cartItems.map((item) => {
      if (itemQuantity > item.quantity || itemQuantity === 0) {
        alert("Error, no hay suficientes existencias");
        M.toast({ html: 'Error, no hay suficientes existencias' });
        return;
      }
    });

    const existingCartItem = cartItems.find((item) => item.id === article.id);

    if (existingCartItem) {
      const updatedCartItems = cartItems.map((item) => {
        if (item.id === article.id) {
          return { ...item, quantity: item.quantity + itemQuantity };
        }
        return item;
      });

      setCartItems(updatedCartItems);
    } else {
      const newCartItem = { ...article, quantity: itemQuantity };
      setCartItems([...cartItems, newCartItem]);
    }

    const updatedArticles = articles.map((art) => {
      if (art.id === article.id) {
        return { ...art, quantity: art.quantity - itemQuantity };
      }
      return art;
    });

    setArticles(updatedArticles);

    M.toast({ html: 'Artículo agregado al carrito de compra' });
    alert("Artículo agregado al carrito de compra");
  };

  const handleQuantityChange = (itemId, increment) => {
    const updatedCartItems = cartItems.map((item) => {
      if (item.id === itemId) {
        const newQuantity = item.quantity + increment;
        if (newQuantity < 1) {
          return item; // Evita tener una cantidad menor a 1
        }
        return { ...item, quantity: newQuantity };
      }
      return item;
    });
    setCartItems(updatedCartItems);
  };

  const handleViewCart = () => {
    setShowCart(!showCart);
    setContinueShopping(!showCart);
  };

  const handleRemoveItemConfirmation = (item) => {
    const confirmation = window.confirm(
      `¿Estás seguro de eliminar "${item.name}" del carrito de compra?`
    );
    if (confirmation) {
      handleRemoveItem(item.id);
    }
  };

  const handleRemoveItem = (itemId) => {
    const updatedCartItems = cartItems.filter((item) => item.id !== itemId);
    setCartItems(updatedCartItems);

    const removedItem = cartItems.find((item) => item.id === itemId);
    const updatedArticles = articles.map((art) => {
      if (art.id === itemId) {
        return { ...art, quantity: art.quantity + removedItem.quantity };
      }
      return art;
    });
    setArticles(updatedArticles);

    M.toast({ html: 'Artículo eliminado del carrito de compra' });
  };

  const handleClearCart = () => {
    const confirmation = window.confirm(
      '¿Estás seguro de eliminar el carrito de compra?'
    );
    if (confirmation) {
      const updatedArticles = articles.map((art) => {
        const cartItem = cartItems.find((item) => item.id === art.id);
        if (cartItem) {
          return { ...art, quantity: art.quantity + cartItem.quantity };
        }
        return art;
      });
      setArticles(updatedArticles);

      setCartItems([]);

      M.toast({ html: 'Carrito de compra eliminado' });
    }
  };

  const handleOpenDescription = (description) => {
    setSelectedDescription(description);
    const modal = document.getElementById('description-modal');
    const instance = M.Modal.init(modal);
    instance.open();
  };

  useEffect(() => {
    M.AutoInit();
  }, []);

  return (
    <div>
      <h2>Compra de artículos</h2>
      <div className="row">
        <div className="col s6">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Buscar artículo..."
          />
        </div>
        <div className="col s6">
          <button className="btn" onClick={handleSearch}>
            Buscar
          </button>
        </div>
      </div>

      {searchResults.length > 0 && (
        <ul className="collection">
          {searchResults.map((item) => (
            <li className="collection-item" key={item.id}>
              <div className="row">
                <div className="col s3">
                  <img
                    src={item.image}
                    alt={item.name}
                    className="responsive-img"
                  />
                </div>
                <div className="col s6">
                  <h5>{item.name}</h5>
                  <p>Precio: ${item.price}</p>
                </div>
                <div className="col s3">
                  <input
                    type="number"
                    value={itemQuantity}
                    onChange={(e) => setItemQuantity(parseInt(e.target.value))}
                    min="1"
                    max={item.quantity}
                  />
                  <button
                    className="btn"
                    onClick={() => handlePurchase(item)}
                  >
                    Agregar al carrito
                  </button>
                  <button
                    className="btn"
                    onClick={() => handleOpenDescription(item.description)}
                  >
                    Descripción
                  </button>
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}

      {showCart && (
        <div>
          <h3>Artículos en el carrito de compras</h3>
          {cartItems.length === 0 && <p>No hay artículos en el carrito</p>}
          {cartItems.length > 0 && (
            <ul className="collection">
              {cartItems.map((item) => (
                <li className="collection-item" key={item.id}>
                  <div className="row">
                    <div className="col s3">
                      <img
                        src={item.image}
                        alt={item.name}
                        className="responsive-img"
                      />
                    </div>
                    <div className="col s6">
                      <h5>{item.name}</h5>
                      <p>Cantidad: {item.quantity}</p>
                      <div>
                        <button
                          className="btn"
                          onClick={() => handleQuantityChange(item.id, -1)}
                        >
                          -
                        </button>
                        <button
                          className="btn"
                          onClick={() => handleQuantityChange(item.id, 1)}
                        >
                          +
                        </button>
                      </div>
                    </div>
                    <div className="col s6">
                      <p>Precio: {item.price}</p>
                    </div>
                    <div className="col s6">
                      <h5>Costo: ${item.quantity * item.price}</h5>
                    </div>
                    <div className="col s3">
                      <button
                        className="btn"
                        onClick={() => handleRemoveItemConfirmation(item)}
                      >
                        Eliminar
                      </button>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          )}
          {cartItems.length > 0 && (
            <div>
              <button className="btn" onClick={handleClearCart}>
                Eliminar carrito
              </button>
            </div>
          )}
        </div>
      )}

      <div id="description-modal" className="modal">
        <div className="modal-content">
          <h4>Descripción</h4>
          <p>{selectedDescription}</p>
        </div>
        <div className="modal-footer">
          <button className="modal-close waves-effect waves-green btn-flat">
            Cerrar
          </button>
        </div>
      </div>

      <button className="btn" onClick={handleViewCart}>
        {showCart ? 'Seguir comprando' : 'Ver carrito'}
      </button>
    </div>
  );
};

export default PurchaseItems;
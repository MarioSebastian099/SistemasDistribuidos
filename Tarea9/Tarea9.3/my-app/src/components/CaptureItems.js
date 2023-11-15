import React, { useState } from 'react';
import M from 'materialize-css';

  const CaptureItems = ({ articles, setArticles, getItemQuantity}) => {
  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [itemPrice, setItemPrice] = useState('');
  const [itemQuantity, setItemQuantity] = useState('');
  const [itemImage, setItemImage] = useState(null);

  const handleFormSubmit = (event) => {
    event.preventDefault();

    // Crear un nuevo artículo con los datos capturados
    const newArticle = {
      name: itemName,
      description: itemDescription,
      price: itemPrice,
      quantity: itemQuantity,
      image: URL.createObjectURL(itemImage), // Obtener la URL de la imagen seleccionada
    };

    // Agregar el nuevo artículo a la lista de artículos
    setArticles([...articles, newArticle]);

    // Luego, puedes limpiar los campos del formulario
    setItemName('');
    setItemDescription('');
    setItemPrice('');
    setItemQuantity('');
    setItemImage(null);

    M.toast({ html: 'Artículo guardado' });
    alert("Artículo guardado")
  };

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    setItemImage(file);
  };

  return (
    <div>
      <h2>Captura de Artículos</h2>
      <form onSubmit={handleFormSubmit}>
        <div>
          <label>Nombre del Artículo:</label>
          <input
            type="text"
            value={itemName}
            onChange={(event) => setItemName(event.target.value)}
          />
        </div>
        <div>
          <label>Descripción del Artículo:</label>
          <input
            type="text"
            value={itemDescription}
            onChange={(event) => setItemDescription(event.target.value)}
          />
        </div>
        <div>
          <label>Precio:</label>
          <input
            type="number"
            value={itemPrice}
            onChange={(event) => setItemPrice(event.target.value)}
          />
        </div>
        <div>
          <label>Cantidad en Almacén:</label>
          <input
            type="number"
            value={itemQuantity}
            onChange={(event) => setItemQuantity(event.target.value)}
          />
        </div>
        <div>
          <div className="file-field input-field">
            <div className="btn">
              <span>Seleccionar Archivo</span>
              <input
                type="file"
                accept="image/*"
                onChange={handleImageChange}
              />
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text" />
            </div>
          </div>
        </div>
        <button className="btn waves-effect waves-light" type="submit">
          Guardar Artículo
          <i className="material-icons right"></i>
        </button>
      </form>

      <h3>Artículos guardados:</h3>
      <ul>
        {articles.map((article, index) => (
          <li key={index}>
            <p>Nombre: {article.name}</p>
            <p>Descripción: {article.description}</p>
            <p>Precio: {article.price}</p>
            <p>Cantidad en Almacén: {article.quantity}</p>
            {article.image && <img src={article.image} alt="" />}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CaptureItems;

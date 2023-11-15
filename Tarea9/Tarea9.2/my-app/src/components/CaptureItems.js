import React, { useState } from 'react';

const CaptureItems = () => {
  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [itemPrice, setItemPrice] = useState('');
  const [itemQuantity, setItemQuantity] = useState('');
  const [itemImage, setItemImage] = useState(null);

  const handleFormSubmit = (event) => {
    event.preventDefault();

    // Aquí puedes agregar la lógica para guardar los datos en la tabla "articulos"
    // Puedes utilizar los valores de las variables itemName, itemDescription, itemPrice, itemQuantity y itemImage
    // También puedes enviar el archivo de imagen al servidor para guardarlo y obtener su URL

    // Luego, puedes limpiar los campos del formulario
    setItemName('');
    setItemDescription('');
    setItemPrice('');
    setItemQuantity('');
    setItemImage(null);
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
          <label>Fotografía del Artículo:</label>
          <input
            type="file"
            accept="image/*"
            onChange={handleImageChange}
          />
        </div>
        <button type="submit">Guardar Artículo</button>
      </form>
    </div>
  );
};

export default CaptureItems;

// Variables para almacenar los datos en memoria
let items = [];
let cart = [];

// Función para mostrar una pantalla y ocultar las demás
function showScreen(screenId) {
  const screens = document.querySelectorAll('.screen');
  screens.forEach(screen => screen.classList.add('hidden'));

  const screenToShow = document.getElementById(screenId);
  screenToShow.classList.remove('hidden');
}

// Función para mostrar la pantalla de captura de artículo
function showCaptureScreen() {
  showScreen('captureScreen');
}

// Función para mostrar la pantalla de compra de artículos
function showPurchaseScreen() {
  showScreen('purchaseScreen');
}

// Función para mostrar la pantalla del carrito de compra
function showCart() {
  showScreen('cartScreen');
  updateCartItems();
  updateCartTotal();
}

// Función para buscar artículos
function searchItems() {
  const searchInput = document.getElementById('searchInput').value.toLowerCase();
  const searchResults = document.getElementById('searchResults');
  searchResults.innerHTML = '';

  const results = items.filter(item =>
    item.name.toLowerCase().includes(searchInput) || item.description.toLowerCase().includes(searchInput)
  );

  if (results.length === 0) {
    searchResults.innerHTML = '<p>No se encontraron resultados.</p>';
    return;
  }

  results.forEach(item => {
    const itemElement = createItemElement(item);
    searchResults.appendChild(itemElement);
  });
}

// Función para crear el elemento HTML de un artículo
function createItemElement(item) {
  const itemElement = document.createElement('div');
  itemElement.classList.add('item');

  const imageElement = document.createElement('img');
  imageElement.src = item.photo;
  itemElement.appendChild(imageElement);

  const nameElement = document.createElement('h3');
  nameElement.textContent = item.name;
  itemElement.appendChild(nameElement);

  const priceElement = document.createElement('p');
  priceElement.textContent = `Precio: $${item.price}`;
  itemElement.appendChild(priceElement);

  const descriptionButton = document.createElement('button');
  descriptionButton.textContent = 'Descripción';
  descriptionButton.addEventListener('click', () => showDescription(item));
  itemElement.appendChild(descriptionButton);

  const buyButton = document.createElement('button');
  buyButton.textContent = 'Compra';
  buyButton.addEventListener('click', () => addToCart(item));
  itemElement.appendChild(buyButton);

  return itemElement;
}

// Función para mostrar la descripción de un artículo en una ventana modal
function showDescription(item) {
  const modal = document.getElementById('descriptionModal');
  const modalTitle = document.getElementById('modalTitle');
  const modalDescription = document.getElementById('modalDescription');

  modalTitle.textContent = item.name;
  modalDescription.textContent = item.description;
  modal.classList.remove('hidden');
}

// Función para cerrar la ventana modal
function closeModal() {
  const modal = document.getElementById('descriptionModal');
  modal.classList.add('hidden');
}

// Función para guardar un artículo capturado
function saveItem() {
  const itemName = document.getElementById('itemName').value;
  const itemDescription = document.getElementById('itemDescription').value;
  const itemPrice = parseFloat(document.getElementById('itemPrice').value);
  const itemQuantity = parseInt(document.getElementById('itemQuantity').value);
  const itemPhoto = document.getElementById('itemPhoto').value; // Aquí debes implementar la lógica para subir la imagen a tu servidor y obtener su URL

  const newItem = {
    id: items.length + 1,
    name: itemName,
    description: itemDescription,
    price: itemPrice,
    quantity: itemQuantity,
    photo: itemPhoto
  };

  items.push(newItem);

  // Aquí debes implementar la lógica para guardar el artículo en la base de datos

  showPurchaseScreen();
}

// Función para agregar un artículo al carrito de compra
function addToCart(item) {
  const quantity = parseInt(prompt('Ingrese la cantidad:', '1'));
  if (isNaN(quantity) || quantity <= 0) {
    return;
  }

  if (quantity > item.quantity) {
    alert('No hay suficientes artículos disponibles.');
    return;
  }

  const cartItem = cart.find(cartItem => cartItem.id === item.id);
  if (cartItem) {
    cartItem.quantity += quantity;
  } else {
    cart.push({ ...item, quantity });
  }

  // Aquí debes implementar la lógica para insertar el artículo en la tabla "carrito_compra" y restar la cantidad del artículo en la tabla "articulos" dentro de una transacción

  updateCartItems();
}

// Función para actualizar los elementos del carrito de compra en la pantalla correspondiente
function updateCartItems() {
  const cartItems = document.getElementById('cartItems');
  cartItems.innerHTML = '';

  cart.forEach(cartItem => {
    const itemElement = createItemElement(cartItem);

    const quantityInput = document.createElement('input');
    quantityInput.type = 'number';
    quantityInput.value = cartItem.quantity;
    quantityInput.addEventListener('change', () => updateCartItemQuantity(cartItem, quantityInput.value));
    itemElement.appendChild(quantityInput);

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Eliminar artículo';
    deleteButton.addEventListener('click', () => removeCartItem(cartItem));
    itemElement.appendChild(deleteButton);

    cartItems.appendChild(itemElement);
  });
}

// Función para actualizar la cantidad de un artículo en el carrito
function updateCartItemQuantity(cartItem, newQuantity) {
  newQuantity = parseInt(newQuantity);
  if (isNaN(newQuantity) || newQuantity <= 0) {
    return;
  }

  if (newQuantity > cartItem.quantity) {
    const availableQuantity = items.find(item => item.id === cartItem.id).quantity;
    if (newQuantity > availableQuantity) {
      alert('No hay suficientes artículos disponibles.');
      return;
    }
  }

  cartItem.quantity = newQuantity;

  // Aquí debes implementar la lógica para actualizar la cantidad del artículo en la tabla "carrito_compra" y en la tabla "articulos" dentro de una transacción

  updateCartTotal();
}

// Función para eliminar un artículo del carrito de compra
function removeCartItem(cartItem) {
  const itemIndex = cart.findIndex(item => item.id === cartItem.id);
  if (itemIndex > -1) {
    cart.splice(itemIndex, 1);
  }

  // Aquí debes implementar la lógica para eliminar el artículo de la tabla "carrito_compra" dentro de una transacción

  updateCartItems();
  updateCartTotal();
}

// Función para actualizar el total de la compra en el carrito
function updateCartTotal() {
  const cartTotal = document.getElementById('cartTotal');
  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  cartTotal.textContent = `$${total}`;
}

// Función para eliminar todos los artículos del carrito de compra
function clearCart() {
  cart = [];

  // Aquí debes implementar la lógica para eliminar todos los artículos de la tabla "carrito_compra" dentro de una transacción

  updateCartItems();
  updateCartTotal();
}

// Ejemplo de datos en memoria (reemplazar con los datos de tu base de datos)
items = [
  {
    id: 1,
    name: 'Camiseta',
    description: 'Una camiseta de algodón',
    price: 20,
    quantity: 10,
    photo: 'https://example.com/images/camiseta.jpg'
  },
  {
    id: 2,
    name: 'Pantalón',
    description: 'Un pantalón de mezclilla',
    price: 30,
    quantity: 5,
    photo: 'https://example.com/images/pantalon.jpg'
  },
  {
    id: 3,
    name: 'Zapatos',
    description: 'Un par de zapatos de cuero',
    price: 50,
    quantity: 2,
    photo: 'https://example.com/images/zapatos.jpg'
  }
];

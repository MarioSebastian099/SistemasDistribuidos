// Variables globales
let items = [];
let cart = [];

// Función para mostrar la pantalla de captura de artículo
function showCaptureScreen() {
  document.getElementById('menu').classList.add('hidden');
  document.getElementById('captureScreen').classList.remove('hidden');
}

// Función para mostrar la pantalla de compra de artículos
function showPurchaseScreen() {
  document.getElementById('menu').classList.add('hidden');
  document.getElementById('purchaseScreen').classList.remove('hidden');
}

// Función para guardar un artículo capturado
function saveItem() {
  const itemName = document.getElementById('itemName').value;
  const itemDescription = document.getElementById('itemDescription').value;
  const itemPrice = document.getElementById('itemPrice').value;
  const itemQuantity = document.getElementById('itemQuantity').value;
  const itemPhoto = document.getElementById('itemPhoto').value;

  const newItem = {
    id: items.length + 1,
    name: itemName,
    description: itemDescription,
    price: parseFloat(itemPrice),
    quantity: parseInt(itemQuantity),
    photo: itemPhoto
  };

  items.push(newItem);

  document.getElementById('itemName').value = '';
  document.getElementById('itemDescription').value = '';
  document.getElementById('itemPrice').value = '';
  document.getElementById('itemQuantity').value = '';
  document.getElementById('itemPhoto').value = '';

  showPurchaseScreen();
}

// Función para buscar artículos
function searchItems() {
  const searchTerm = document.getElementById('searchInput').value.toLowerCase();
  const searchResults = items.filter(item => {
    const itemName = item.name.toLowerCase();
    const itemDescription = item.description.toLowerCase();
    return itemName.includes(searchTerm) || itemDescription.includes(searchTerm);
  });

  displaySearchResults(searchResults);
}

// Función para mostrar los resultados de búsqueda
function displaySearchResults(results) {
  const searchResultsContainer = document.getElementById('searchResults');
  searchResultsContainer.innerHTML = '';

  if (results.length === 0) {
    searchResultsContainer.textContent = 'No se encontraron resultados.';
    return;
  }

  results.forEach(item => {
    const itemElement = document.createElement('div');
    itemElement.classList.add('item');

    const itemPhoto = document.createElement('img');
    itemPhoto.src = item.photo;
    itemPhoto.alt = item.name;
    itemPhoto.classList.add('item-photo');
    itemElement.appendChild(itemPhoto);

    const itemName = document.createElement('p');
    itemName.textContent = item.name;
    itemElement.appendChild(itemName);

    const itemPrice = document.createElement('p');
    itemPrice.textContent = `$${item.price.toFixed(2)}`;
    itemElement.appendChild(itemPrice);

    const itemDescriptionButton = document.createElement('button');
    itemDescriptionButton.textContent = 'Descripción';
    itemDescriptionButton.addEventListener('click', () => openModal(item));
    itemElement.appendChild(itemDescriptionButton);

    const itemQuantityInput = document.createElement('input');
    itemQuantityInput.type = 'number';
    itemQuantityInput.min = 1;
    itemQuantityInput.value = 1;
    itemElement.appendChild(itemQuantityInput);

    const addItemButton = document.createElement('button');
    addItemButton.textContent = 'Agregar al carrito';
    addItemButton.addEventListener('click', () => addToCart(item, parseInt(itemQuantityInput.value)));
    itemElement.appendChild(addItemButton);

    searchResultsContainer.appendChild(itemElement);
  });
}

// Función para agregar un artículo al carrito de compra
function addToCart(item, quantity) {
  if (quantity <= item.quantity) {
    const cartItem = {
      id: item.id,
      name: item.name,
      price: item.price,
      quantity: quantity
    };

    cart.push(cartItem);

    // Aquí debes implementar la lógica para insertar en la tabla "carrito_compra" el ID del artículo y la cantidad, y restar la cantidad solicitada de la cantidad en la tabla de "artículos" dentro de una transacción

    updateCartItems();
    showCartScreen();
  } else {
    alert('No hay suficientes artículos disponibles.');
  }
}

// Función para actualizar los elementos en la pantalla del carrito de compra
function updateCartItems() {
  const cartItemsContainer = document.getElementById('cartItems');
  cartItemsContainer.innerHTML = '';

  let total = 0;

  cart.forEach(cartItem => {
    const item = items.find(item => item.id === cartItem.id);

    const cartItemElement = document.createElement('div');
    cartItemElement.classList.add('cart-item');

    const cartItemPhoto = document.createElement('img');
    cartItemPhoto.src = item.photo;
    cartItemPhoto.alt = item.name;
    cartItemPhoto.classList.add('cart-item-photo');
    cartItemElement.appendChild(cartItemPhoto);

    const cartItemName = document.createElement('p');
    cartItemName.textContent = item.name;
    cartItemElement.appendChild(cartItemName);

    const cartItemQuantityInput = document.createElement('input');
    cartItemQuantityInput.type = 'number';
    cartItemQuantityInput.min = 1;
    cartItemQuantityInput.value = cartItem.quantity;
    cartItemQuantityInput.addEventListener('change', () => updateCartItemQuantity(item, parseInt(cartItemQuantityInput.value)));
    cartItemElement.appendChild(cartItemQuantityInput);

    const cartItemPrice = document.createElement('p');
    cartItemPrice.textContent = `$${item.price.toFixed(2)}`;
    cartItemElement.appendChild(cartItemPrice);

    const cartItemCost = document.createElement('p');
    cartItemCost.textContent = `$${(item.price * cartItem.quantity).toFixed(2)}`;
    cartItemElement.appendChild(cartItemCost);

    const removeItemButton = document.createElement('button');
    removeItemButton.textContent = 'Eliminar artículo';
    removeItemButton.addEventListener('click', () => removeCartItem(item));
    cartItemElement.appendChild(removeItemButton);

    cartItemsContainer.appendChild(cartItemElement);

    total += item.price * cartItem.quantity;
  });

  document.getElementById('cartTotal').textContent = `$${total.toFixed(2)}`;
}

// Función para eliminar un artículo del carrito de compra
function removeCartItem(item) {
  const confirmDelete = confirm(`¿Estás seguro de eliminar ${item.name} del carrito de compra?`);

  if (confirmDelete) {
    const cartItemIndex = cart.findIndex(cartItem => cartItem.id === item.id);
    if (cartItemIndex > -1) {
      cart.splice(cartItemIndex, 1);
    }

    // Aquí debes implementar la lógica para eliminar el artículo del carrito de compra, borrando el registro correspondiente de la tabla "carrito_compra" y agregando la cantidad de los artículos en la tabla "articulos" dentro de una transacción

    updateCartItems();
  }
}

// Función para actualizar la cantidad de un artículo en el carrito de compra
function updateCartItemQuantity(item, quantity) {
  const availableQuantity = item.quantity;

  if (quantity <= availableQuantity) {
    const cartItem = cart.find(cartItem => cartItem.id === item.id);
    if (cartItem) {
      cartItem.quantity = quantity;

      // Aquí debes implementar la lógica para actualizar la cantidad en la tabla "articulos" y en la tabla "carrito_compra" dentro de una transacción
    }

    updateCartItems();
  } else {
    alert('No hay suficientes artículos disponibles.');
  }
}

// Función para vaciar el carrito de compra
function clearCart() {
  const confirmClear = confirm('¿Estás seguro de eliminar el carrito de compra?');

  if (confirmClear) {
    cart = [];

    // Aquí debes implementar la lógica para vaciar el carrito de compra, eliminando todos los registros de la tabla "carrito_compra" dentro de una transacción

    updateCartItems();
  }
}

// Función para mostrar la pantalla del carrito de compra
function showCartScreen() {
  document.getElementById('purchaseScreen').classList.add('hidden');
  document.getElementById('cartScreen').classList.remove('hidden');

  updateCartItems();
}

// Función para abrir el modal con la descripción del artículo
function openModal(item) {
  const modalTitle = document.getElementById('modalTitle');
  const modalDescription = document.getElementById('modalDescription');

  modalTitle.textContent = item.name;
  modalDescription.textContent = item.description;

  document.getElementById('descriptionModal').style.display = 'block';
}

// Función para cerrar el modal
function closeModal() {
  document.getElementById('descriptionModal').style.display = 'none';
}


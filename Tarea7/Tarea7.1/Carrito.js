// Obtener el carrito de compra del almacenamiento local
const cart = JSON.parse(localStorage.getItem('cart')) || [];

// Función para abrir el modal del carrito de compra
function openCartModal() {
  const cartModal = document.getElementById('cartModal');
  const cartItemsContainer = document.getElementById('cartItemsContainer');
  const cartTotal = document.getElementById('cartTotal');

  // Limpiar el contenido previo del carrito
  cartItemsContainer.innerHTML = '';

  // Verificar si el carrito está vacío
  if (cart.length === 0) {
    cartItemsContainer.innerHTML = '<p>No hay artículos en el carrito</p>';
    cartTotal.textContent = '';
    cartModal.style.display = 'block';
    return;
  }

  let total = 0;

  // Recorrer los artículos en el carrito y mostrarlos en el modal
  cart.forEach((item) => {
    const cartItem = document.createElement('div');
    cartItem.classList.add('cartItem');

    const itemImage = document.createElement('img');
    itemImage.src = item.image;
    itemImage.alt = item.name;

    const itemInfo = document.createElement('div');
    const itemName = document.createElement('h3');
    itemName.textContent = item.name;
    const itemPrice = document.createElement('p');
    itemPrice.textContent = `Precio: $${item.price}`;
    const itemQuantity = document.createElement('p');
    itemQuantity.textContent = `Cantidad: ${item.quantity}`;

    itemInfo.appendChild(itemName);
    itemInfo.appendChild(itemPrice);
    itemInfo.appendChild(itemQuantity);

    const itemActions = document.createElement('div');
    const removeButton = document.createElement('button');
    removeButton.textContent = 'Eliminar artículo';
    removeButton.addEventListener('click', () => removeItemFromCart(item));
    const increaseButton = document.createElement('button');
    increaseButton.textContent = '+';
    increaseButton.addEventListener('click', () => increaseCartItemQuantity(item));
    const decreaseButton = document.createElement('button');
    decreaseButton.textContent = '-';
    decreaseButton.addEventListener('click', () => decreaseCartItemQuantity(item));

    itemActions.appendChild(removeButton);
    itemActions.appendChild(decreaseButton);
    itemActions.appendChild(increaseButton);

    cartItem.appendChild(itemImage);
    cartItem.appendChild(itemInfo);
    cartItem.appendChild(itemActions);

    cartItemsContainer.appendChild(cartItem);

    total += item.price * item.quantity;
  });

  cartTotal.textContent = `Total: $${total.toFixed(2)}`;
  cartModal.style.display = 'block';
}

// Función para eliminar un artículo del carrito
function removeItemFromCart(item) {
  const confirmation = confirm(`¿Estás seguro de eliminar "${item.name}" del carrito?`);

  if (!confirmation) {
    return;
  }

  // Eliminar el artículo del carrito
  const updatedCart = cart.filter((cartItem) => cartItem.id !== item.id);

  localStorage.setItem('cart', JSON.stringify(updatedCart));

  // Actualizar la visualización del carrito de compra
  openCartModal();
}

// Función para incrementar la cantidad de un artículo en el carrito
function increaseCartItemQuantity(item) {
  const updatedCart = cart.map((cartItem) => {
    if (cartItem.id === item.id) {
      return {
        ...cartItem,
        quantity: cartItem.quantity + 1,
      };
    }
    return cartItem;
  });

  localStorage.setItem('cart', JSON.stringify(updatedCart));

  // Actualizar la visualización del carrito de compra
  openCartModal();
}

// Función para decrementar la cantidad de un artículo en el carrito
function decreaseCartItemQuantity(item) {
  if (item.quantity === 1) {
    removeItemFromCart(item);
    return;
  }

  const updatedCart = cart.map((cartItem) => {
    if (cartItem.id === item.id) {
      return {
        ...cartItem,
        quantity: cartItem.quantity - 1,
      };
    }
    return cartItem;
  });

  localStorage.setItem('cart', JSON.stringify(updatedCart));

  // Actualizar la visualización del carrito de compra
  openCartModal();
}

// Función para eliminar todo el carrito de compra
function removeCart() {
  const confirmation = confirm('¿Estás seguro de eliminar todo el carrito de compra?');

  if (!confirmation) {
    return;
  }

  // Eliminar el carrito de compra
  localStorage.removeItem('cart');

  // Actualizar la visualización del carrito de compra
  openCartModal();
}

// Obtener referencias a los elementos del DOM
const cartButton = document.getElementById('cartButton');
const cartModal = document.getElementById('cartModal');
const closeCartButton = document.getElementById('closeCartButton');
const removeCartButton = document.getElementById('removeCartButton');
const continueShoppingButton = document.getElementById('continueShoppingButton');

// Abrir el modal del carrito de compra al hacer clic en el botón del carrito
cartButton.addEventListener('click', openCartModal);

// Cerrar el modal del carrito de compra al hacer clic en el botón de cerrar
closeCartButton.addEventListener('click', () => {
  cartModal.style.display = 'none';
});

// Eliminar todo el carrito de compra al hacer clic en el botón correspondiente
removeCartButton.addEventListener('click', removeCart);

// Continuar comprando al hacer clic en el botón correspondiente
continueShoppingButton.addEventListener('click', () => {
  cartModal.style.display = 'none';
});

document.addEventListener('DOMContentLoaded', function() {
    // Función para realizar la búsqueda de artículos
function searchArticles(keyword) {
    // Simulación de búsqueda en una base de datos
    const searchResults = [
      { id: 1, name: 'Artículo 1', description: 'Descripción del artículo 1', price: 10.99 },
      { id: 2, name: 'Artículo 2', description: 'Descripción del artículo 2', price: 19.99 },
      { id: 3, name: 'Artículo 3', description: 'Descripción del artículo 3', price: 5.99 },
    ];
  
    const searchResultsContainer = document.getElementById('searchResults');
    searchResultsContainer.innerHTML = '';
  
    if (keyword.trim() === '') {
      searchResultsContainer.innerHTML = '<p>No se ha ingresado una palabra clave</p>';
      return;
    }
  
    const filteredResults = searchResults.filter(
      (result) =>
        result.name.toLowerCase().includes(keyword.toLowerCase()) ||
        result.description.toLowerCase().includes(keyword.toLowerCase())
    );
    alert("SI SE ENCONTRÓ");
    if (filteredResults.length === 0) {
      searchResultsContainer.innerHTML = '<p>No se encontraron resultados</p>';
      return;
    }
  
    filteredResults.forEach((result) => {
      const articleElement = document.createElement('div');
      articleElement.classList.add('search-result');
  
      const imageElement = document.createElement('img');
      imageElement.src = `path/to/images/${result.id}.jpg`;
      imageElement.alt = result.name;
      articleElement.appendChild(imageElement);
  
      const nameElement = document.createElement('h3');
      nameElement.textContent = result.name;
      articleElement.appendChild(nameElement);
  
      const priceElement = document.createElement('p');
      priceElement.textContent = `Precio: $${result.price.toFixed(2)}`;
      articleElement.appendChild(priceElement);
  
      const addButton = document.createElement('button');
      addButton.textContent = 'Compra';
      addButton.addEventListener('click', () => addToCart(result));
      articleElement.appendChild(addButton);
  
      searchResultsContainer.appendChild(articleElement);
    });
  }
  
  // Función para mostrar la descripción del artículo en un modal
  function showDescription(description) {
    const modal = document.getElementById('itemDescriptionModal');
    const descriptionTitle = document.getElementById('itemDescriptionTitle');
    const descriptionContent = document.getElementById('itemDescription');
  
    descriptionTitle.textContent = 'Descripción del artículo';
    descriptionContent.textContent = description;
  
    modal.style.display = 'block';
  }
  
  // Función para agregar un artículo al carrito de compra
  function addToCart(article) {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
  
    const existingItem = cart.find((item) => item.id === article.id);
    if (existingItem) {
      existingItem.quantity++;
    } else {
      cart.push({ id: article.id, name: article.name, price: article.price, quantity: 1 });
    }
  
    localStorage.setItem('cart', JSON.stringify(cart));
    alert('El artículo se ha agregado al carrito de compra.');
  }
  
  // Función para abrir el modal del carrito de compra
  function openCartModal() {
    const cartModal = document.getElementById('cartModal');
    const cartItemsContainer = document.getElementById('cartItems');
    const cartTotal = document.getElementById('cartTotal');
  
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
  
    cartItemsContainer.innerHTML = '';
  
    if (cart.length === 0) {
      cartItemsContainer.innerHTML = '<p>No hay artículos en el carrito</p>';
      cartTotal.textContent = '';
      return;
    }
  
    let total = 0;
  
    cart.forEach((item) => {
      const cartItem = document.createElement('div');
      cartItem.classList.add('cart-item');
  
      const imageElement = document.createElement('img');
      imageElement.src = `path/to/images/${item.id}.jpg`;
      imageElement.alt = item.name;
      cartItem.appendChild(imageElement);
  
      const nameElement = document.createElement('h3');
      nameElement.textContent = item.name;
      cartItem.appendChild(nameElement);
  
      const quantityElement = document.createElement('input');
      quantityElement.type = 'number';
      quantityElement.value = item.quantity;
      quantityElement.min = 1;
      quantityElement.addEventListener('change', () => updateCartItemQuantity(item, quantityElement));
      cartItem.appendChild(quantityElement);
  
      const priceElement = document.createElement('p');
      priceElement.textContent = `Precio: $${item.price.toFixed(2)}`;
      cartItem.appendChild(priceElement);
  
      const costElement = document.createElement('p');
      const cost = item.price * item.quantity;
      costElement.textContent = `Costo: $${cost.toFixed(2)}`;
      cartItem.appendChild(costElement);
  
      const removeButton = document.createElement('button');
      removeButton.textContent = 'Eliminar artículo';
      removeButton.addEventListener('click', () => removeCartItem(item));
      cartItem.appendChild(removeButton);
  
      cartItemsContainer.appendChild(cartItem);
  
      total += cost;
    });
  
    cartTotal.textContent = `Total: $${total.toFixed(2)}`;
  
    cartModal.style.display = 'block';
  }
  
  // Función para actualizar la cantidad de un artículo en el carrito de compra
  function updateCartItemQuantity(item, quantityElement) {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
  
    const updatedCart = cart.map((cartItem) => {
      if (cartItem.id === item.id) {
        const newQuantity = parseInt(quantityElement.value, 10);
  
        if (newQuantity <= 0) {
          quantityElement.value = cartItem.quantity;
          return cartItem;
        }
  
        if (newQuantity > cartItem.quantity) {
          // Verificar si hay suficientes artículos disponibles en la base de datos
          if (newQuantity > cantidadDisponibleEnLaBaseDeDatos) {
            alert('No hay suficientes artículos disponibles.');
            quantityElement.value = cartItem.quantity;
            return cartItem;
          }
  
          // Realizar actualización en la base de datos
          actualizarCantidadEnLaBaseDeDatos(newQuantity - cartItem.quantity);
        } else {
          // Realizar actualización en la base de datos
          actualizarCantidadEnLaBaseDeDatos(-(cartItem.quantity - newQuantity));
        }
  
        cartItem.quantity = newQuantity;
      }
  
      return cartItem;
    });
  
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  
    // Actualizar el costo del artículo
    const costElement = quantityElement.parentNode.querySelector('p:last-child');
    const cost = item.price * item.quantity;
    costElement.textContent = `Costo: $${cost.toFixed(2)}`;
  
    // Actualizar el total del carrito
    const cartTotal = document.getElementById('cartTotal');
    const total = updatedCart.reduce((acc, cartItem) => acc + cartItem.price * cartItem.quantity, 0);
    cartTotal.textContent = `Total: $${total.toFixed(2)}`;
  }
  
  // Función para eliminar un artículo del carrito de compra
  function removeCartItem(item) {
    const confirmation = confirm(`¿Estás seguro de eliminar ${item.name} del carrito de compra?`);
  
    if (!confirmation) {
      return;
    }
  
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
  
    const updatedCart = cart.filter((cartItem) => cartItem.id !== item.id);
  
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  
    // Eliminar el artículo de la base de datos
    eliminarArticuloDeLaBaseDeDatos(item.id);
  
    // Actualizar la visualización del carrito de compra
    openCartModal();
  }
  
  // Función para eliminar el carrito de compra
  function removeCart() {
    const confirmation = confirm('¿Estás seguro de eliminar el carrito de compra?');
  
    if (!confirmation) {
      return;
    }
  
    localStorage.removeItem('cart');
  
    // Eliminar todos los artículos del carrito de compra en la base de datos
    eliminarTodosLosArticulosDelCarritoDeLaBaseDeDatos();
  
    // Actualizar la visualización del carrito de compra
    openCartModal();
  }
  
  // Función para cerrar el modal del carrito de compra
  function closeCartModal() {
    const cartModal = document.getElementById('cartModal');
    cartModal.style.display = 'none';
  }
  
  // Función para cerrar el modal de descripción del artículo
  function closeDescriptionModal() {
    const modal = document.getElementById('itemDescriptionModal');
    modal.style.display = 'none';
  }
  
  // Inicialización de la pantalla de "Compra de artículos"
  function initializePurchaseScreen() {
    //alert("ok");
    console.log('Función initializePurchaseScreen() ejecutada correctamente');
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    const cartButton = document.getElementById('cartButton');
    const closeCartButton = document.getElementById('closeCartButton');
    const closeDescriptionButton = document.getElementById('closeDescriptionButton');
    
    searchButton.addEventListener('click', () => searchArticles(searchInput.value));
    cartButton.addEventListener('click', openCartModal);
    closeCartButton.addEventListener('click', closeCartModal);
    closeDescriptionButton.addEventListener('click', closeDescriptionModal);
  
    searchInput.addEventListener('keydown', (event) => {
      if (event.key === 'Enter') {
        event.preventDefault();
        searchButton.click();
      }
    });
  }

  // Inicialización de la pantalla de "Artículos en el carrito"
  function initializeCartScreen() {
    const removeCartButton = document.getElementById('removeCartButton');
    const continueShoppingButton = document.getElementById('continueShoppingButton');
  
    removeCartButton.addEventListener('click', removeCart);
    continueShoppingButton.addEventListener('click', () => {
      const cartModal = document.getElementById('cartModal');
      cartModal.style.display = 'none';
    });
  }  
    initializePurchaseScreen();
    initializeCartScreen();
  });
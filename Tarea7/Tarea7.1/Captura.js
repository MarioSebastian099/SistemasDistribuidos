function showCaptureScreen() {
    // Limpiar el contenedor antes de cargar la pantalla de captura
    const container = document.getElementById("container");
    container.innerHTML = "";
  
    // Crear los elementos necesarios para la pantalla de captura
    const captureScreen = document.createElement("div");
    captureScreen.className = "capture-screen";
  
    const title = document.createElement("h2");
    title.textContent = "Captura de artículos";
  
    const form = document.createElement("form");
  
    const nameLabel = document.createElement("label");
    nameLabel.textContent = "Nombre del artículo:";
    const nameInput = document.createElement("input");
    nameInput.type = "text";
    nameInput.required = true;
  
    const descriptionLabel = document.createElement("label");
    descriptionLabel.textContent = "Descripción del artículo:";
    const descriptionInput = document.createElement("textarea");
    descriptionInput.required = true;
  
    const priceLabel = document.createElement("label");
    priceLabel.textContent = "Precio:";
    const priceInput = document.createElement("input");
    priceInput.type = "number";
    priceInput.required = true;
  
    const stockLabel = document.createElement("label");
    stockLabel.textContent = "Cantidad en almacén:";
    const stockInput = document.createElement("input");
    stockInput.type = "number";
    stockInput.required = true;
  
    const photoLabel = document.createElement("label");
    photoLabel.textContent = "Fotografía del artículo:";
    const photoInput = document.createElement("input");
    photoInput.type = "file";
    photoInput.accept = "image/*";
    photoInput.required = true;
  
    const addButton = document.createElement("button");
    addButton.type = "submit";
    addButton.textContent = "Agregar artículo";
  
    // Agregar los elementos al formulario y a la pantalla de captura
    form.appendChild(nameLabel);
    form.appendChild(nameInput);
    form.appendChild(descriptionLabel);
    form.appendChild(descriptionInput);
    form.appendChild(priceLabel);
    form.appendChild(priceInput);
    form.appendChild(stockLabel);
    form.appendChild(stockInput);
    form.appendChild(photoLabel);
    form.appendChild(photoInput);
    form.appendChild(addButton);
  
    captureScreen.appendChild(title);
    captureScreen.appendChild(form);
  
    container.appendChild(captureScreen);
  
    // Agregar el evento de envío del formulario
    form.addEventListener("submit", function (event) {
      event.preventDefault();
  
      // Obtener los valores ingresados por el usuario
      const name = nameInput.value;
      const description = descriptionInput.value;
      const price = priceInput.value;
      const stock = stockInput.value;
      const photo = photoInput.value; // En este caso, obtendrías la ruta del archivo seleccionado
  
      // Realizar las acciones necesarias con los valores capturados
      // ...
  
      // Limpiar el formulario después de agregar el artículo
      form.reset();
    });
  }
  
  function showPurchaseScreen() {
    // Aquí puedes agregar el código para mostrar la pantalla "Compra de artículos"
    console.log("Mostrando pantalla de Compra de artículos");
  }
  
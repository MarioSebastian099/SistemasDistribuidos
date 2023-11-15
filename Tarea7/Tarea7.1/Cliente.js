function showCaptureScreen() {
    // Realizar una petición AJAX para cargar el contenido HTML de la pantalla "Captura de artículos"
    $.ajax({
      url: "CapturaDeArticulos.html",
      dataType: "html",
      success: function (data) {
        // Cargar el contenido en un contenedor
        const container = document.getElementById("container");
        container.innerHTML = data;
      },
      error: function (error) {
        console.log("Error al cargar la pantalla de Captura de artículos:", error);
      }
    });
  }
  
  function showPurchaseScreen() {
    // Aquí puedes agregar el código para mostrar la pantalla "Compra de artículos"
    $.ajax({
        url: "CompraDeArticulos.html",
        dataType: "html",
        success: function (data) {
          // Cargar el contenido en un contenedor
          const container = document.getElementById("container");
          container.innerHTML = data;
        },
        error: function (error) {
          console.log("Error al cargar la pantalla de Captura de artículos:", error);
        }
      });
    console.log("Mostrando pantalla de Compra de artículos");
  }
  
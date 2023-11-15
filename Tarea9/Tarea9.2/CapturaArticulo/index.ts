interface CrearArticulo {
    nombre: string;
    descripcion?: string;
    precio: number;
    cantidad: number;
    foto?: string;
  }
  
  interface Articulo {
    id: number;
    nombre: string;
    descripcion?: string;
    precio: number;
    cantidad: number;
    foto?: string;
  }
  
  import { AzureFunction, Context, HttpRequest } from "@azure/functions";
  
  const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('Se ha recibido una petición HTTP para crear un artículo.');
    // Validamos que se haya recibido un body
    if (req.body) {
      // Se crea el artículo a partir del body
      const articulo: CrearArticulo = req.body;
      // Validamos los campos obligatorios
      if (articulo.nombre && articulo.precio && articulo.cantidad) {
        // Validamos que la información sea correcta
        let articuloValidado: boolean = true;
        if (articulo.nombre.length < 4 || articulo.nombre.length > 20) {
          articuloValidado = false;
        } else if (articulo.precio < 0) {
          articuloValidado = false;
        } else if (articulo.cantidad < 0) {
          articuloValidado = false;
        }
        // Se validan campos opcionales
        if (articulo.descripcion && articulo.descripcion.length > 100) {
          articuloValidado = false;
        }
        // En caso de que el artículo sea válido, lo agregamos a la base de datos
        if (articuloValidado) {
          try {
            // Se crea el objeto ORM
            const { Sequelize } = require('sequelize');
            // Configura los detalles de conexión a la base de datos
            const sequelize = new Sequelize('carritodecompras', 'root', 'Kadnees1511@', {
              host: 'localhost',
              port: 3006,
              dialect: 'mysql'
            });
  
            // Insertamos usando un query raw en mysql
            const { QueryTypes } = require('sequelize');
            await sequelize.query(
              `INSERT INTO articulos (nombre, descripcion, precio, cantidad, foto) VALUES (?, ?, ?, ?, ?);`, {
                type: QueryTypes.INSERT,
                replacements: [
                  articulo.nombre,
                  articulo.descripcion || null,
                  articulo.precio,
                  articulo.cantidad,
                  articulo.foto || null,
                ],
              }
            );
            // Obtenemos el artículo que acabamos de insertar.
            const articulos: Articulo[] = await sequelize.query(
              `SELECT * FROM articulos WHERE nombre =?;`,
              {
                type: QueryTypes.SELECT,
                replacements: [articulo.nombre],
              }
            );
            // Cerramos la conexión
            await sequelize.close();
            // Retornamos el código 1 (creado) y el artículo que se ha creado
            context.res = {
              status: 1,
              body: articulos[0],
            };
          } catch (error) {
            context.log(error);
            // Retornamos el código 2 (error en el servidor) y mensaje de error
            context.res = {
              status: 2,
              body: {
                mensaje: "No se pudo crear el artículo",
                error: error,
              },
            };
          }
        } else {
          // Retornamos el error cuando la información del artículo no sea válida
          context.res = {
            status: 3,
            body: "Los datos ingresados para el artículo no son válidos",
          };
        }
      } else {
        // Retornamos el error cuando la información del artículo no esté completa
        context.res = {
          status: 4,
          body: "Falta información del artículo",
        };
      }
    } else {
      // Retornamos el error cuando no se haya recibido ningún dato de información del artículo
      context.res = {
        status: 4,
        body: "No se han recibido datos",
      };
    }
  };
  
  export default httpTrigger;
  
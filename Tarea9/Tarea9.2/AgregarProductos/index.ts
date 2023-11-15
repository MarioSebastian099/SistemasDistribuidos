//Creamos las interfaz para el carrito de compras
interface CarritoDeCompras{
    articuloId: number;
    cantidad: number;
}

//Creamos la interfaz para los articulos
interface Articulo{
    id: number;
    nombre: string;
    precio: number;
    descripcion: string;
    cantidad: number;
    foto?: string;
}

//Creamos las interfaz para los articulos que almacenara el carrito de compras
interface CarritoArt{
    articuloId:number;
    cantidad: number;
}

import { AzureFunction, Context, HttpRequest } from "@azure/functions"

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('Se ha recibido una peticion HTTP para agregar un artículo al carrito');
    //const name = (req.query.name || (req.body && req.body.name));
    //const responseMessage = name
    //    ? "Hello, " + name + ". This HTTP triggered function executed successfully."
    //    : "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.";

    //context.res = {
        // status: 200, /* Defaults to 200 */
    //    body: responseMessage
    //};
    //Validamos que un body se haya recibido
    const agregarArticuloCarrito: CarritoDeCompras = req.body;

    const { Sequelize } = require('sequelize');
    const { QueryTypes } = require('sequelize');
    try{
        //Iniciamos la conexion a la base de datos
        const sequelize = new Sequelize('carritodecompras', 'root', 'Kadnees1511@', {
            host: 'localhost',
            port: 3306,
            dialect: 'mysql',
            dialectOptions: {
                multipleStatements: true,
            },
          });
        //Obtenemos el articulo del usuario
        const articuloSeleccionado: Articulo[] = await sequelize.query(
            "SELECT id, nombre, descrpcion, precio, cantidad, CONVERT(foto using utf8) as foto FROM articulos WHERE id = ?",
            {
                type: QueryTypes.SELECT,
                mapToModel: true,
                replacements: [agregarArticuloCarrito.articuloId],//agregamos el articulo que seleccionó el usuario
            }
        );
        //Verificamos que se haya encontrado el articulo seleccionado en la base de datos
        if(articuloSeleccionado.length === 0){//Si no hay articulos
            context.res = {
                status: 2,
                body:"Error, no se ha encontrado el artículo",
            };
            return;//Finaliza la función
        }

        const articulo: Articulo = articuloSeleccionado[0];
        //Ahora se procede a validar las existencias de los articulos
        if(articulo.cantidad < agregarArticuloCarrito.cantidad){//La cantidad pedida excede a las existencias
            context.res = {
                status: 3,
                body: "Error, No hay suficientes existencias para el artículo seleccionado",
            };
            return; //Finaliza la función
        }
        //Validamos que el artículo no se encuentre en el carrito
        const productos: CarritoArt[] = await sequelize.query(
            "SELECT id_articulo, cantidad FROM carrito_compra WHERE id_articulo = :articuloId",
            {
                type: QueryTypes.SELECT,
                mapToModel: true,
                replacements: { articuloId: articulo.id},
            }
        );

        //Posteriormente se verifica que se haya encontrado el articulo
        if(productos.length === 0){//Se crea la transaccion
            const transaccion = await sequelize.transaccion();

            //Se añade el articulo al carrito
            await sequelize.query(
                "INSERT INTO carrito_compra (id_articulo, cantidad) VALUES (:articuloId, cantidad)",
                {
                    type: QueryTypes.INSERT,
                    replacements: {
                        articuloId: articulo.id,
                        cantidad: agregarArticuloCarrito.cantidad,
                    },
                    transaccion,
                }
            );
            //Actualizamos la cantidad en el inventario
            await sequelize.query(
                "UPDATE articulos SET cantidad = cantidad - :cantidad WHERE id = :id",
                {
                    type: QueryTypes.UPDATE,
                    replacements: {
                        id: articulo.id,
                        cantidad: agregarArticuloCarrito.cantidad,
                    },
                    transaccion,
                }
            );
            //Se resta la cantidad de articulos del inventario
            articulo.cantidad -= agregarArticuloCarrito.cantidad;

            //Se realiza la transacción
            await transaccion.commit();
            
            //Se informa el resultado
            context.res = {
                status: 1,
                body: articulo,
            };
        }else{
            //Se informa al usuario que ya tiene el articulo en su carrito
            context.res = {
                status: 4,
                body: "El artículo ya se encuentra en el carrito",

            };
        }

        //Cerramos la conexion a la base de datos
        await sequelize.close();
    }catch(error){
        //Notificamos en caso de que se haya producido un error en la base de datos
        context.res ={
            status: 5,
            body:{
                mensaje: "Ocurrió un error al procesar la información",
                error: error,
            },
        };
    }

};

export default httpTrigger;
//Interfaz para la modificación
interface Modificacion {
    articuloId: number;
    cantidad: number;
}

//Interfaz para los articulos
interface Articulo {
    nombre: string;
    id: number;
    descripcion: string;
    precio: number;
    cantidad: number;
    foto?: string; 
}

//Interfaz para los articulos del carrito
interface Carrito {
    articuloId: number;
    cantidad: number;
    articulo?: Articulo;
}



import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import Query = require("mysql2/typings/mysql/lib/protocol/sequences/Query");

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('Se ha recibido una solicitud HTTP para modificar el carrito');
    //const name = (req.query.name || (req.body && req.body.name));
    //const responseMessage = name
    //    ? "Hello, " + name + ". This HTTP triggered function executed successfully."
    //    : "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.";

    //context.res = {
        // status: 200, /* Defaults to 200 */
    //    body: responseMessage
    //};
    //Procesamos los datos de la solicitud
    const modificacion: Modificacion = req.body;
    if(modificacion){
        const { Sequelize } = require('sequelize');
        const { QueryTypes } = require('sequelize');
        try{
            //Conectamos con la base de datos 
            const sequelize = new Sequelize('carritodecompras', 'root', 'Kadnees1511@', {
                host: 'localhost',
                port: 3306,
                dialect: 'mysql',
                dialectOptions: {
                    multipleStatements: true,
                },
            });
            //Procesamos el carrito de compras
            const carrito: Carrito[] = await sequelize.query(
                "SELECT * FROM carrito_compra WHERE id_articulo = :articuloId",
                {
                    replacements: { articuloId: modificacion.articuloId},
                    type: QueryTypes.SELECT,
                }
            );

            //Se valida que el articulo exista
            if(carrito.length <= 0){//Si no existe
                context.res = {
                    status: 2,
                    body: "El articulo no se ha agregado al carrito de compras",
                };

            }else{//Si existe el articulo
                //Obtenemos todo el carrito
                const productos: Carrito = carrito[0];
                //Obtenemos los articulos
                const articulos: Articulo[] = await sequelize.query(
                    "SELECT * FROM articulos WHERE id_articulo = :articuloId",
                    {
                        replacements: { articuloId: modificacion.articuloId},
                        type: QueryTypes.SELECT,
                    }
                );
                //Validamos la existencia del articulo
                if(articulos.length <= 0){//No existe
                    context.res = {
                        status: 3,
                        body: "Error, no existe el artículo",
                    };
                }else{// Si existe
                    //Obtenemos el articulo
                    const articulo: Articulo = articulos[0];
                    if(productos.cantidad < modificacion.cantidad){//El usuario agregó más cantidades de un mismo artículo
                        //Verificamos que haya suficientes existencias
                        if(articulo.cantidad < modificacion.cantidad){//No hay suficientes existencias
                            context.res = {
                                status: 4,
                                body: "Error, no hay más existencias del producto seleccionado",
                            };
                        }else{//Si hay existencias
                            //Iniciamos la transacción
                            const transaction = await sequelize.transaction();
                            //Se actualiza la cantidad de articulos en el carrito
                            await sequelize.query(
                                "UPDATE carrito_compra SET cantidad = :cantidad WHERE id_articulo = :id_articulo",
                                {
                                    replacements: {
                                        cantidad: modificacion.cantidad,
                                        id_articulo: modificacion.articuloId,
                                    },
                                    type: QueryTypes.UPDATE,
                                    transaction,
                                }
                            );
                            //Se actualiza la existencia de los articulos en la tabla articulos
                            await sequelize.query(
                                "UPDATE articulos SET cantidad_almacen = :cantidad WHERE id = :id",
                                {
                                    replacements: {
                                        cantidad: articulo.cantidad - (modificacion.cantidad - productos.cantidad),
                                        id: modificacion.articuloId,
                                    },
                                    type: QueryTypes.UPDATE,
                                    transaction,
                                }
                            );
                            //Se confirma la transacción
                            await transaction.commit();

                            //Posteriormente se obtiene el carrito de compras
                            const respuesta: Carrito = {
                                articuloId: modificacion.articuloId,
                                cantidad: modificacion.cantidad,
                                articulo: articulo,
                            };
                            //Se devuelve el carrito actualizado
                            context.res = {
                                status: 1,
                                body: respuesta,
                            };
                        }

                    }else{//El usuario quitó articulos del carrito de compras
                        //Iniciamos la transacción
                        const transaction = await sequelize.transaction();
                        //Se actualiza la cantidad de artículos
                        await sequelize.query(
                            "UPDATE carrito_compra SET cantidad = :cantidad WHERE id_articulo = : id_articulo",
                            {
                                replacements: {//Sustituimos los valores
                                    cantidad: modificacion.cantidad,
                                    id_articulo: modificacion.articuloId,
                                },
                                type: QueryTypes.UPDATE,
                                transaction,
                            }
                        );
                        //Se actualizan las existencias de los articulos en la tabla articulos
                        await sequelize.query(
                            "UPDATE articulos SET cantidad_almacen = :cantidad WHERE id_articulo = :id_articulo",
                            {
                                replacements: {
                                    cantidad: articulo.cantidad + (productos.cantidad - modificacion.cantidad),
                                    id: modificacion.articuloId,
                                },
                                type: QueryTypes.UPDATE,
                                transaction,
                            }
                        );
                        //Posteriormente se confirma la transacción
                        await transaction.commit();
                        //Leemos el carrito de compras
                        const Respuesta: Carrito = {
                            articuloId: modificacion.articuloId,
                            cantidad: modificacion.cantidad,
                            articulo: articulo,
                        };
                        //Devolvemos el carrito actualizado
                        context.res = {
                            status: 1,
                            body: Respuesta,
                        };
                    }
                }
            }
            //Cerramos la conexión con la base de datos
            await sequelize.close();

        }catch(error){
            context.res = {
                status: 5,
                body: "Error, no se pudo conectar con la base de datos",
            };
        }
    } else{
        context.res = {
            status: 6,
            body: "Error, no se han recibido datos",
        };
    }

};

export default httpTrigger;
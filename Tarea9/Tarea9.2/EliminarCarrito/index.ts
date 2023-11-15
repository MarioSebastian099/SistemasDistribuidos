//Creacion de la interfaz para el carrito
interface CarritoCompras {
    articuloId: number,
    cantidad: number;
}

//Interfaz para los articulos
interface Articulo {
    nombre: string;
    id: number;
    descripcion: string;
    precio: number;
    cantidad: number;
    foto: string;
}

import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { Sequelize, Transaction } from 'sequelize';

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('Se ha recibido una solicitud via HTTP para eliminar el carrito de compras.');
    const id = req.body.articuloId;

    if(!id){//No se encontró el ID
        context.res = {
            status: 2,
            body: "Falta el ID",
        };
    }else{//se encontró el ID
        //Conexion con la base de datos
        const { Sequelize } = require('sequelize');
        const { QueryTypes } = require('sequelize');
        try{
            const sequelize = new Sequelize('carritodecompras', 'root', 'Kadnees1511@', {
            host: 'localhost',
            port: 3306,
            dialect: 'mysql',
            dialectOptions: {
                multipleStatements: true,
            },
            });
            //Se obtiene el carrito de compras
            const carrito: CarritoCompras[] = await sequelize.query(
                "SELECT * FROM carrito_compra WHERE id_articulo = :id_articulo",
                {
                    replacements: {id_articulo: id},
                    type: QueryTypes.SELECT,
                }
            );
            //Se valida si existe el articulo
            if(carrito.length <= 0){//No existe el articulo en el carrito
                context.res = {
                    status: 3,
                    body: "Error, no se encuentra el articulo en el carrito de compras",
                };
            }else{//El articulo si se encuentra en el carrito de compras
                //Iniciamos la transacción
                const transaction: Transaction = await sequelize.transaction();

                //Se agregan de regreso las unidades del carrito a la tabla articulos en a base de datos
                await sequelize.query(
                    "UPDATE articulos SET cantidad_almacen = cantidad_almacen + :cantidad WHERE id_articulo = :id",
                    {
                        replacements: {
                            id: carrito[0].articuloId,
                            cantidad: carrito[0].cantidad,
                        },
                        type: QueryTypes.UPDATE,
                        transaction,
                    }
                );
                //Eliminamos el carrito de compras
                await sequelize.query(
                    "DELETE FROM carrito_compra WHERE id_articulo = :id_articulo",
                    {
                        replacements: {id_articulo: id},
                        type: QueryTypes.DELETE,
                        transaction,
                    }
                );
                //Ejecutamos la transacción
                await transaction.commit();
                //Mandamos la respuesta al usuario
                context.res = {
                    status: 1,
                    body: "Se ha eliminado el artículo correctamente de su carrito de compras",
                };
            }
            //Cerramos la conexión a la base de datos
            await sequelize.close();

        }catch(error){
            context.res = {
                status: 4,
                body:{
                    mensaje: "Error, no se pudo eliminar el artículo del carrito de compras",
                    error,
                },
            };
        }
    }

    //const name = (req.query.name || (req.body && req.body.name));
    //const responseMessage = name
    //    ? "Hello, " + name + ". This HTTP triggered function executed successfully."
    //    : "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.";

    //context.res = {
        // status: 200, /* Defaults to 200 */
    //    body: responseMessage
    //};

};

export default httpTrigger;
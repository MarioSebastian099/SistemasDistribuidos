//Creamos las interfaces para los articulos
interface Articulo {
    id: number;
    nombre: string;
    precio: number;
    cantidad: string;
    fotografia?: string;
}
import { AzureFunction, Context, HttpRequest } from "@azure/functions"

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('Se ha recibido una solicituda HTTP para la busqueda de artículos');
    //const name = (req.query.name || (req.body && req.body.name));
    //const responseMessage = name
    //    ? "Hello, " + name + ". This HTTP triggered function executed successfully."
    //    : "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.";

    //context.res = {
        // status: 200, /* Defaults to 200 */
    //    body: responseMessage
    //};
    //Se revisa si tenemos un nombre en la consulta
    const nombre: string = req.query.nombre || (req.body && req.body.nombre);//Verificamos que haya un nombre en el campo
    if(nombre){//si hay nombre procedemos a buscar en la base de datos
        context.log("El nombre a buscar en la query es: " + nombre);
    }

    try{
        //Abrimos la conexion a la base de datos
        const { Sequelize } = require('sequelize');
        const { QueryTypes } = require('sequelize');
        const sequelize = new Sequelize('carritodecompras', 'root', 'Kadnees1511@', {
        host: 'localhost',
        port: 3306,
        dialect: 'mysql',
});
    let articulosEncontrados: Articulo[] = [];
    if (nombre){
        articulosEncontrados = await sequelize.query(
            "SELECT id, nombre, descripcion, precio, cantidad, CONVERT(foto using utf8) as foto FROM articulos WHERE nombre LIKE : nombre OR descripcion LIKE : nombre",
            {
                type: QueryTypes.SELECT,
                //Vinculamos los datos de los articulos a la interface que se ha creado
                mapToModel: true,
                //Se reemplaza el nombre por el valor recibido
                replacements: {nombre: `%${nombre}%`},
            }
        );
    }else{
        articulosEncontrados = await sequelize.query(
            "SELECT id, nombre, descripcion, precio, cantidad, CONVERT(foto using utf8) as foto FROM articulos",
            {
                type: QueryTypes.SELECT,
                //Vinculamos los resultados a la interface de los articulos
                mapToModel: true,
            }
        );
    }
    //Cerramos la conexion 
    await sequelize.close();
    //Mandamos la respuesta
    context.res = {
        status: 1,
        body: articulosEncontrados,
    };
    }catch (error){
        context.res ={
            status: 2,
            body: {mensaje: "No se han encontrado artículos con el nombre especificado",error},
        };
    }
};

export default httpTrigger;
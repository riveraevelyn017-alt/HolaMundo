/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.holamundo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author river
 */
public class HolaMundo {

    public static void main(String[] args)throws SQLException {
        System.out.println("===Ejecutando operaciones en tabla Alumnos ===");
        //obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if(cn == null){
            System.out.println("No se puede establecer la conexión");
            return;
        }
        //Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;
        try{
            System.out.println("\n 1. Intentando insertar nuevo Alumno...");
            String psInsert = "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";
            psInsertar = cn.prepareStatement(psInsert);
            
            //asignaremos valores a nuestros parametros
            psInsertar.setString(1, "Juan Lopez");
            psInsertar.setString(2, "juan.lopez@uth.hn");
            // ejecutar la inserción
            int filasAfectadas = psInsertar.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("!Alumno insertado con exito!");
            }

            //Parte B Seleccionar y visualizar datos en pantalla
            System.out.println("\n2. Consulta de lista de Alumnos en la Base de Datos");
            String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            System.out.println("\n========================================================");
            System.out.println("  ID        | Nombre                |Correo            ");
            System.out.println("========================================================");
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                // Imprimimos la tabla virtual
                System.out.printf("%-3d | %-20s | %s%n", id, nombre, correo);
            }
            System.out.println("========================================================");

            // Actualizar un Alumno
            System.out.println("\n3. Actualizando correo de un Alumno...");
            String sqlUpdate = "UPDATE Alumnos SET correo = ? WHERE nombre = ?";
            psActualizar = cn.prepareStatement(sqlUpdate);
            psActualizar.setString(1, "maria.jesus@uth.hn");
            psActualizar.setString(2, "Maria Jesus");
            int filasActualizadas = psActualizar.executeUpdate();
            if(filasActualizadas > 0){
                System.out.println("!Alumno actualizado con exito!");
            }

            // Eliminar un Alumno
            System.out.println("\n4. Eliminando un Alumno...");
            String sqlDelete = "DELETE FROM Alumnos WHERE nombre = ?";
            psEliminar = cn.prepareStatement(sqlDelete);
            psEliminar.setString(1, "");
            int filasEliminadas = psEliminar.executeUpdate();
            if(filasEliminadas > 0){
                System.out.println("!Alumno eliminado con exito!");
            }

        }catch(SQLException e){
            System.err.println("Ocurrio un error con la base de datos" + e.getMessage());
        }finally{
            //cerrar la conexion de manera obligatoria
            if(rs != null){
                rs.close();
            }
            if(psInsertar != null)psInsertar.close();
            if(psSeleccionar != null)psSeleccionar.close();
            if(psActualizar != null)psActualizar.close();
            if(psEliminar != null)psEliminar.close();
            ConexionDB.cerrarConexion();
        }
    }
}
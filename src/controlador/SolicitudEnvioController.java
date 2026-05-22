/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.SolicitudEnvioDAO;
import modelo.SolicitudEnvio;
import javax.swing.JOptionPane;

/**
 *
 * @author camil
 */
public class SolicitudEnvioController {

    SolicitudEnvioDAO dao = new SolicitudEnvioDAO(); // conectamos a sql

    public boolean registrarSolicitud( // registramos solicitud de envio
            String remitente,
            String destinatario,
            String ciudadOrigen,
            String ciudadDestino,
            String direccion,
            String pesoTexto,
            String tipoEnvio
    ) {

        if (remitente.isEmpty()            // validamos los campos vacios
                || destinatario.isEmpty()
                || direccion.isEmpty()
                || pesoTexto.isEmpty()
                || ciudadOrigen.equals("Seleccionar...")
                || ciudadDestino.equals("Seleccionar...")
                || tipoEnvio.equals("Seleccionar...")) {

            JOptionPane.showMessageDialog(
                    null,
                    "Complete todos los campos"
            );

            return false;
        }

        try {

            double peso = Double.parseDouble(
                    pesoTexto.replace(",", ".")
            ); // convertimos el peso a double

            double costoEnvio = 5000;


            if (!ciudadOrigen.equals(ciudadDestino)) {  // si las ciudades son diferentes aumenta 10.000

                costoEnvio += 10000;
            }


            costoEnvio += peso * 2000;   // costo segun el peso

            SolicitudEnvio solicitud = new SolicitudEnvio(); // objeto para la solicitud

            solicitud.setRemitente(remitente);  // enviamos el DAO al modelo

            solicitud.setDestinatario(destinatario);

            solicitud.setCiudadOrigen(ciudadOrigen);

            solicitud.setCiudadDestino(ciudadDestino);

            solicitud.setDireccionEntrega(direccion);

            solicitud.setPeso(peso);

            solicitud.setCostoEnvio(costoEnvio);

            solicitud.setTipoEnvio(tipoEnvio);

            solicitud.setEstado("PENDIENTE"); // el estado actual automaticamente/por defecto sera (PENDIENTE)

            return dao.registrarSolicitud(solicitud); // guardamos en sql

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "El peso debe ser numérico"   // validamos que el peso sea numerico, no acepta letras
            );

            return false;
        }
    }
}
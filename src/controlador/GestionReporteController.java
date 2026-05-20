/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.GestionReporteDAO;
import modelo.GestionReporte;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author camil
 */
public class GestionReporteController {

    GestionReporteDAO dao
            = new GestionReporteDAO();

    public DefaultTableModel mostrarReportes() {

        String[] titulos = {
            "ID",
            "Guía",
            "Motivo",
            "Descripción",
            "Evidencia",
            "Fecha",
            "Estado"
        };

        DefaultTableModel modelo
                = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        List<GestionReporte> lista
                = dao.listarReportes();

        Object[] fila = new Object[7];

        for (GestionReporte gr : lista) {

            fila[0] = gr.getIdReporte();

            fila[1] = gr.getGuiaPaquete();

            fila[2] = gr.getMotivo();

            fila[3] = gr.getDescripcion();

            fila[4] = gr.getRutaEvidencia();

            fila[5] = gr.getFechaRegistro();

            fila[6] = gr.getEstado();

            modelo.addRow(fila);
        }

        return modelo;
    }

    public boolean actualizarEstadoGestionReporte(
            int idReporte,
            String nuevoEstado
    ) {

        return dao.actualizarEstadoReporte(
                idReporte,
                nuevoEstado
        );
    }

    public boolean existeReporteActivo(String guia) {

        return dao.existeReporteActivo(guia);
    }
    public String obtenerNombreEvidencia(String guia) {
        return dao.obtenerArchivoPorGuia(guia); // Asegúrate de usar la variable de tu DAO (ej: 'dao' o 'grDao')
    }

}

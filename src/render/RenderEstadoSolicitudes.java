
package render;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author camil
 */

public class RenderEstadoSolicitudes
        extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component celda
                = super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                );

        String estado = value.toString();


        if (estado.equals("PENDIENTE")) {

            celda.setBackground(Color.YELLOW);
            celda.setForeground(Color.BLACK);

        } else if (estado.equals("ACEPTADA")) {

            celda.setBackground(Color.GREEN);
              celda.setForeground(Color.BLACK);

        } else if (estado.equals("RECHAZADA")) {

            celda.setBackground(Color.RED);
              celda.setForeground(Color.BLACK);

        } else {

            celda.setBackground(Color.WHITE);
        }

        return celda;
    }
}

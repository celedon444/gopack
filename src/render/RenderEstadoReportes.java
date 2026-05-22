
package render;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author camil
 */

public class RenderEstadoReportes
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

        } else if (estado.equals("EN REVISIÓN")) {

            celda.setBackground(Color.ORANGE);
              celda.setForeground(Color.BLACK);

        } else if (estado.equals("RESUELTO")) {

            celda.setBackground(Color.GREEN);
              celda.setForeground(Color.BLACK);

        } else {

            celda.setBackground(Color.WHITE);
        }

        return celda;
    }
}

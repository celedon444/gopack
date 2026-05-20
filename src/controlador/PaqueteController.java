    package controlador;

    import dao.PaqueteDAO;
    import dao.MovimientoDAO;
    import modelo.Paquete;
    import modelo.MovimientoPaquete;
    import javax.swing.JOptionPane;
    import javax.swing.table.DefaultTableModel;
    import java.util.List;
    import java.sql.Timestamp;

    public class PaqueteController {

        private PaqueteDAO dao;
        private MovimientoDAO movimientoDAO;

        public PaqueteController() {

            dao = new PaqueteDAO();

            movimientoDAO = new MovimientoDAO();
        }

        public DefaultTableModel consultarInventario() { // consultar el inventario

            String[] titulos = {
                "Guía",
                "Ciudad Origen",
                "Ciudad Destino",
                "Remitente",
                "Destinatario",
                "Dirección",
                "Peso (kg)",
                "Tipo",
                "Estado",
                "Ubicación Actual",
                "Fecha Registro"
            };

            DefaultTableModel modeloTabla
                    = new DefaultTableModel(null, titulos) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            List<Paquete> lista = dao.listarTodos();

            Object[] fila = new Object[11];

            for (Paquete p : lista) {

                fila[0] = p.getGuia();
                fila[1] = p.getCiudadOrigen();
                fila[2] = p.getCiudadDestino();
                fila[3] = p.getRemitente();
                fila[4] = p.getDestinatario();
                fila[5] = p.getDireccion();
                fila[6] = p.getPeso();
                fila[7] = p.getTipo();
                fila[8] = p.getEstado();
                fila[9] = p.getUbicacionActual();
                fila[10] = p.getFechaSistema();

                modeloTabla.addRow(fila);
            }

            return modeloTabla;
        }


        public boolean guardarPaquete(  // guardar los paquetes
                String guia,
                String rem,
                String dest,
                String dir,
                String pesoStr,
                String tipo,
                String ciudadOrigen,
                String ciudadDestino) {

            if (guia.isEmpty()
                    || rem.isEmpty()
                    || dest.isEmpty()
                    || pesoStr.isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Error: Faltan datos obligatorios."
                );

                return false;
            }

            try {

                double peso = Double.parseDouble(
                        pesoStr.trim().replace(",", ".")
                );

                Paquete nuevoPaquete = new Paquete(); // crar objeto paquete

                nuevoPaquete.setGuia(guia);
                nuevoPaquete.setRemitente(rem);
                nuevoPaquete.setDestinatario(dest);
                nuevoPaquete.setDireccion(dir);
                nuevoPaquete.setPeso(peso);
                nuevoPaquete.setTipo(tipo);
                nuevoPaquete.setEstado("En Bodega"); // estado inicial de los paquetes es automatico/predeterminado siempre inicia en (Bodega)
                nuevoPaquete.setCiudadOrigen(ciudadOrigen);
                nuevoPaquete.setCiudadDestino(ciudadDestino);


                boolean paqueteRegistrado        // se registra el paquete
                        = dao.registrar(nuevoPaquete);


                if (paqueteRegistrado) {       // si el paquete se registra bien... 

                    MovimientoPaquete movimiento   // crea el primer movimiento
                            = new MovimientoPaquete();

                    movimiento.setGuiaRastreo(guia);

                    movimiento.setEstado("En Bodega");

                    movimiento.setUbicacion(ciudadOrigen);

                    movimiento.setDescripcion(
                            "Paquete registrado en el sistema"
                    );

                    movimientoDAO.registrarMovimiento(    // guardamos el movimiento
                            movimiento
                    );
                }

                return paqueteRegistrado;

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "El peso ingresado no es un número válido."
                );

                return false;
            }
        }


        public String calcularEstadoPorTiempo(  // calculamos el estado automatico
                Timestamp fechaSistema) {

            if (fechaSistema == null) {

                return "En Bodega";
            }

            long ahora = System.currentTimeMillis();

            long registro = fechaSistema.getTime();

            long diferenciaMillis = ahora - registro;

            long segundosPasados
                    = diferenciaMillis / 1000;

            if (segundosPasados < 10) {

                return "En Bodega";
            }

            if (segundosPasados < 20) {

                return "En Despacho";
            }

            if (segundosPasados < 30) {

                return "En Ruta";
            }

            return "Entregado";
        }


        public Paquete buscarPaquetePorGuia(  // rastreamos el paquete por la guia
                String guia) {

            return dao.buscarPorGuia(guia);
        }


public boolean actualizarEstadoPaquete(String guia, String nuevoEstado) {
        PaqueteDAO dao = new PaqueteDAO();
        return dao.actualizarEstado(guia, nuevoEstado);
    }


        public boolean existeGuia(String guia) { // validamos si la guia existe

            Paquete paquete = dao.buscarPorGuia(guia);
            return paquete != null;
        }
    }

package gt.joaquin.renombrararchivos.vista;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;

/**
 *
 * @author joaquinlinares
 */
class RenombrarFactory {

    public inicio ths = null;
    public String path = null;
    private ArchivoFactory af = new ArchivoFactory();

    public String clickAbrir() {
        String d = null;
        JFileChooser chooser = new JFileChooser();
        if (path != null) {
            chooser.setCurrentDirectory(new File(path));
        }
        chooser.setDialogTitle("Busca la carpeta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(ths) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            d = f.getAbsolutePath();
        }
        return d;
    }

    public ArrayList<String> getArchivos() {
        ArrayList<String> nombreArchivos = new ArrayList<>();
        af.path = path;
        af.cargarArchivos();
        af.archivos.stream().forEachOrdered(_item -> {
            nombreArchivos.add(_item.getName());
        });

        return nombreArchivos;
    }

    public ArrayList<String> getArchivosRenombrados(String posicionTexto, String texto, boolean numeracion, String posicionNumeracion, int comienzo) {
        ArrayList<String> nombres = new ArrayList();
        final AtomicInteger count = new AtomicInteger();
        count.set(comienzo);

        af.archivos.stream().forEachOrdered(_item -> {
            if (posicionTexto.equals("Antes")) {
                if (numeracion == true) {
                    if (posicionNumeracion.equals("Antes")) {
                        String nuevoNombre = texto + count.incrementAndGet()+" "  + _item.getName();
                        nombres.add(nuevoNombre);
                    } else if (posicionTexto.equals("Despues")) {
                        String nuevoNombre = texto + _item.getName() + " "+count.incrementAndGet();
                        nombres.add(nuevoNombre);
                    }
                } else {
                    String nuevoNombre = texto + _item.getName();
                    nombres.add(nuevoNombre);
                }
            } else if (posicionTexto.equals("Despues")) {
                if (numeracion == true) {
                    if (posicionNumeracion.equals("Antes")) {
                        String nuevoNombre = Integer.toString(count.incrementAndGet())+" " + _item.getName() + texto;

                        nombres.add(nuevoNombre);
                    } else if (posicionTexto.equals("Despues")) {
                        String nuevoNombre = _item.getName() +  texto+  Integer.toString(count.incrementAndGet());
                        nombres.add(nuevoNombre);
                    }
                } else {
                    String nuevoNombre = texto + _item.getName();
                    nombres.add(nuevoNombre);
                }
            }

        });
        return nombres;
    }

    public void crearCarpetaDestino() {
        try {
            File directorio = new File(path + "/archivosRenombrados");
            boolean mkdirs = directorio.mkdirs();
        } catch (Exception e) {
            throw new UnsupportedOperationException( e.getMessage() );
        }
    }
    
    public void guardarArchivosRenombrados(ArrayList<String> archivosRenombrados){
        af.GuardarFile(archivosRenombrados);
    }

}

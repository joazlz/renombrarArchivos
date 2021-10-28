/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.joaquin.renombrararchivos.vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author joaquinlinares
 */
public class ArchivoFactory {

    public String path;

    public ArrayList<File> archivos = new ArrayList<>();

    public void cargarArchivos() {
        archivos.removeAll(archivos);
        try {
            File fPath = new File(path);
            for (File archivo : fPath.listFiles()) {
                if (archivo.isFile()) {
                    archivos.add(archivo);
                }
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Sin Path.");
        }

    }

    public void GuardarFile(ArrayList<String> archivosRenombrados) {
        try {
            final AtomicInteger count = new AtomicInteger();
            archivos.stream().forEachOrdered(x->{
                String fromFile = x.getPath();
                String toFile = x.getParentFile()+"/archivosRenombrados/"+archivosRenombrados.get(count.getAndIncrement());
                copyFile(fromFile, toFile);
            });
            crearLista(archivosRenombrados,archivos.get(0).getParentFile()+"/archivosRenombrados/");
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
    
    /*Gracias https://codigoxules.org/java-copiar-ficheros-java-io/*/
     public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
     
    public void crearLista(ArrayList<String> archivosRenombrados,String ruta) {
        try {
            String contenido = "";
            
            File file = new File(ruta+"/listaArchivos.txt");
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            for (String archivosRenombrado : archivosRenombrados) {
                contenido+=archivosRenombrado+"\n";
            }
            
            FileWriter fw = new FileWriter(file);
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(contenido);
            
            bw.close();
            
        } catch (IOException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

}

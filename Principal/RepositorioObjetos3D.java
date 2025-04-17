package Principal;

import javax.media.j3d.SharedGroup;
import java.util.ArrayList;

public class RepositorioObjetos3D {
    public static SharedGroup mesa;
    public static SharedGroup silla;
    public static SharedGroup puerta;
    public static ArrayList<Puerta> todasLasPuertas = new ArrayList<>();

    public static void inicializar() {
        mesa = new SharedGroup();
        mesa.addChild(new Mesa());

        silla = new SharedGroup();
        silla.addChild(new Silla());

        puerta = new SharedGroup();
        // No agregamos una puerta directamente aquí
    }
}
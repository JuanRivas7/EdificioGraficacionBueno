package Principal;

import javax.media.j3d.SharedGroup;
import edificioD.Mesa;
import edificioD.Silla;

public class RepositorioObjetos3D {
    public static SharedGroup mesa;
    public static SharedGroup silla;

    // Inicializa y carga todos los modelos una vez
    public static void inicializar() {
        // Crear una mesa y agregarla al SharedGroup
        mesa = new SharedGroup();
        mesa.addChild(new Mesa());

        // Crear una silla y agregarla al SharedGroup
        silla = new SharedGroup();
        silla.addChild(new Silla());
    }
}


package Principal;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.SharedGroup;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

public class RepositorioObjetos3D {
    public static SharedGroup mesa;
    public static SharedGroup silla;
    public static SharedGroup puerta;
    public static ArrayList<Puerta> todasLasPuertas = new ArrayList<>();
    public static SharedGroup peldaño;
    public static void inicializar() {
        
        mesa = new SharedGroup();
        mesa.addChild(new Mesa());

        silla = new SharedGroup();
        silla.addChild(new Silla());

        puerta = new SharedGroup();
        // No agregamos una puerta directamente aquí
        
         peldaño = crearBox(0.2f, 0.05f, 0.3f, new Color3f(0.6f, 0.3f, 0.1f)); // color madera
    }
    
    public static SharedGroup crearBox(float ancho, float alto, float profundo, Color3f color) {
    Appearance apariencia = new Appearance();

    Material material = new Material();
    material.setDiffuseColor(color);
    material.setLightingEnable(true);
    apariencia.setMaterial(material);

    Box box = new Box(ancho, alto, profundo, Primitive.GENERATE_NORMALS, apariencia);
    SharedGroup sg = new SharedGroup();
    sg.addChild(box);

    return sg;
}

}

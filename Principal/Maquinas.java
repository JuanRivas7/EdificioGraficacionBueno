package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;

public class Maquinas {
    private static Textura textura = new Textura();
    private static int paraTextura = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    private static final float ESCALA = 0.2f; // 1/5 del tamaño original
    
    public static SharedGroup crearAmasadora() {
        SharedGroup amasadora = new SharedGroup();
        
        // Base de la amasadora (reducida 5 veces)
        Box base = new Box(0.08f, 0.02f, 0.08f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tBase = new Transform3D();
        tBase.setTranslation(new Vector3f(0f, 0.02f, 0f));
        TransformGroup tgBase = new TransformGroup(tBase);
        tgBase.addChild(base);
        
        // Cuerpo principal (reducido 5 veces)
        Cylinder cuerpo = new Cylinder(0.06f, 0.1f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tCuerpo = new Transform3D();
        tCuerpo.setTranslation(new Vector3f(0f, 0.12f, 0f));
        TransformGroup tgCuerpo = new TransformGroup(tCuerpo);
        tgCuerpo.addChild(cuerpo);
        
        // Tapa superior (reducida 5 veces)
        Box tapa = new Box(0.07f, 0.01f, 0.07f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tTapa = new Transform3D();
        tTapa.setTranslation(new Vector3f(0f, 0.23f, 0f));
        TransformGroup tgTapa = new TransformGroup(tTapa);
        tgTapa.addChild(tapa);
        
        // Brazo mezclador (reducido 5 veces)
        Cylinder brazo = new Cylinder(0.01f, 0.08f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tBrazo = new Transform3D();
        tBrazo.setTranslation(new Vector3f(0f, 0.18f, 0f));
        TransformGroup tgBrazo = new TransformGroup(tBrazo);
        tgBrazo.addChild(brazo);
        
        // Paletas mezcladoras (reducidas 5 veces)
        Box paleta1 = new Box(0.03f, 0.004f, 0.01f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tPaleta1 = new Transform3D();
        tPaleta1.setTranslation(new Vector3f(0f, 0.14f, 0f));
        TransformGroup tgPaleta1 = new TransformGroup(tPaleta1);
        tgPaleta1.addChild(paleta1);
        
        Box paleta2 = new Box(0.01f, 0.004f, 0.03f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tPaleta2 = new Transform3D();
        tPaleta2.setTranslation(new Vector3f(0f, 0.14f, 0f));
        TransformGroup tgPaleta2 = new TransformGroup(tPaleta2);
        tgPaleta2.addChild(paleta2);
        
        // Panel de control (reducido 5 veces)
        Box panel = new Box(0.03f, 0.02f, 0.002f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tPanel = new Transform3D();
        tPanel.setTranslation(new Vector3f(0.08f, 0.12f, 0f));
        TransformGroup tgPanel = new TransformGroup(tPanel);
        tgPanel.addChild(panel);
        
        amasadora.addChild(tgBase);
        amasadora.addChild(tgCuerpo);
        amasadora.addChild(tgTapa);
        amasadora.addChild(tgBrazo);
        amasadora.addChild(tgPaleta1);
        amasadora.addChild(tgPaleta2);
        amasadora.addChild(tgPanel);
        
        return amasadora;
    }
    
    public static SharedGroup crearRefrigerador() {
        SharedGroup refrigerador = new SharedGroup();
        
        // Cuerpo principal (reducido 5 veces)
        Box cuerpo = new Box(0.1f, 0.16f, 0.08f, paraTextura, textura.crearTexturas("refigerador.jpg"));
        Transform3D tCuerpo = new Transform3D();
        tCuerpo.setTranslation(new Vector3f(0f, 0.16f, 0f));
        TransformGroup tgCuerpo = new TransformGroup(tCuerpo);
        tgCuerpo.addChild(cuerpo);
        
        // Puerta (reducida 5 veces)
        Box puerta = new Box(0.09f, 0.15f, 0.004f, paraTextura, textura.crearTexturas("refigerador.jpg"));
        Transform3D tPuerta = new Transform3D();
        tPuerta.setTranslation(new Vector3f(0f, 0.16f, 0.08f));
        TransformGroup tgPuerta = new TransformGroup(tPuerta);
        tgPuerta.addChild(puerta);
        
        // Manija (reducida 5 veces)
        Box manija = new Box(0.01f, 0.02f, 0.004f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tManija = new Transform3D();
        tManija.setTranslation(new Vector3f(0.08f, 0.16f, 0.084f));
        TransformGroup tgManija = new TransformGroup(tManija);
        tgManija.addChild(manija);
        
        // Base (reducida 5 veces)
        Box base = new Box(0.11f, 0.01f, 0.09f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tBase = new Transform3D();
        tBase.setTranslation(new Vector3f(0f, 0.01f, 0f));
        TransformGroup tgBase = new TransformGroup(tBase);
        tgBase.addChild(base);
        
        refrigerador.addChild(tgCuerpo);
        refrigerador.addChild(tgPuerta);
        refrigerador.addChild(tgManija);
        refrigerador.addChild(tgBase);
        
        return refrigerador;
    }
    
    public static SharedGroup crearHorno() {
        SharedGroup horno = new SharedGroup();
        
        // Cuerpo principal (reducido 5 veces)
        Box cuerpo = new Box(0.12f, 0.14f, 0.1f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tCuerpo = new Transform3D();
        tCuerpo.setTranslation(new Vector3f(0f, 0.14f, 0f));
        TransformGroup tgCuerpo = new TransformGroup(tCuerpo);
        tgCuerpo.addChild(cuerpo);
        
        // Puerta (reducida 5 veces)
        Box puerta = new Box(0.11f, 0.13f, 0.004f, paraTextura, textura.crearTexturas("vidrio_horno.jpg"));
        Transform3D tPuerta = new Transform3D();
        tPuerta.setTranslation(new Vector3f(0f, 0.14f, 0.1f));
        TransformGroup tgPuerta = new TransformGroup(tPuerta);
        tgPuerta.addChild(puerta);
        
        // Manija (reducida 5 veces)
        Box manija = new Box(0.02f, 0.004f, 0.004f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tManija = new Transform3D();
        tManija.setTranslation(new Vector3f(0.09f, 0.1f, 0.104f));
        TransformGroup tgManija = new TransformGroup(tManija);
        tgManija.addChild(manija);
        
        // Panel de control (reducido 5 veces)
        Box panel = new Box(0.03f, 0.02f, 0.002f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tPanel = new Transform3D();
        tPanel.setTranslation(new Vector3f(0.1f, 0.24f, 0f));
        TransformGroup tgPanel = new TransformGroup(tPanel);
        tgPanel.addChild(panel);
        
        // Chimenea (reducida 5 veces)
        Cylinder chimenea = new Cylinder(0.02f, 0.06f, paraTextura, textura.crearTexturas("acero_oxidado.jpg"));
        Transform3D tChimenea = new Transform3D();
        tChimenea.setTranslation(new Vector3f(0f, 0.28f, -0.08f));
        TransformGroup tgChimenea = new TransformGroup(tChimenea);
        tgChimenea.addChild(chimenea);
        
        horno.addChild(tgCuerpo);
        horno.addChild(tgPuerta);
        horno.addChild(tgManija);
        horno.addChild(tgPanel);
        horno.addChild(tgChimenea);
        
        return horno;
    }
    
    public static SharedGroup crearCortadora() {
        SharedGroup cortadora = new SharedGroup();
        
        // Base (reducida 5 veces)
        Box base = new Box(0.1f, 0.02f, 0.08f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tBase = new Transform3D();
        tBase.setTranslation(new Vector3f(0f, 0.02f, 0f));
        TransformGroup tgBase = new TransformGroup(tBase);
        tgBase.addChild(base);
        
        // Cuerpo (reducido 5 veces)
        Box cuerpo = new Box(0.08f, 0.06f, 0.06f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tCuerpo = new Transform3D();
        tCuerpo.setTranslation(new Vector3f(0f, 0.08f, 0f));
        TransformGroup tgCuerpo = new TransformGroup(tCuerpo);
        tgCuerpo.addChild(cuerpo);
        
        // Banda transportadora (reducida 5 veces)
        Box banda = new Box(0.07f, 0.01f, 0.05f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tBanda = new Transform3D();
        tBanda.setTranslation(new Vector3f(0f, 0.04f, 0f));
        TransformGroup tgBanda = new TransformGroup(tBanda);
        tgBanda.addChild(banda);
        
        // Cuchilla (reducida 5 veces)
        Box cuchilla = new Box(0.08f, 0.002f, 0.05f, paraTextura, textura.crearTexturas("acero_pulido.jpg"));
        Transform3D tCuchilla = new Transform3D();
        tCuchilla.setTranslation(new Vector3f(0f, 0.14f, 0f));
        TransformGroup tgCuchilla = new TransformGroup(tCuchilla);
        tgCuchilla.addChild(cuchilla);
        
        // Soporte cuchilla (reducido 5 veces)
        Box soporte = new Box(0.01f, 0.04f, 0.01f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tSoporte = new Transform3D();
        tSoporte.setTranslation(new Vector3f(0f, 0.12f, 0.04f));
        TransformGroup tgSoporte = new TransformGroup(tSoporte);
        tgSoporte.addChild(soporte);
        
        // Panel de control (reducido 5 veces)
        Box panel = new Box(0.03f, 0.02f, 0.01f, paraTextura, textura.crearTexturas("metal.jpg"));
        Transform3D tPanel = new Transform3D();
        tPanel.setTranslation(new Vector3f(0.08f, 0.1f, 0f));
        TransformGroup tgPanel = new TransformGroup(tPanel);
        tgPanel.addChild(panel);
        
        cortadora.addChild(tgBase);
        cortadora.addChild(tgCuerpo);
        cortadora.addChild(tgBanda);
        cortadora.addChild(tgCuchilla);
        cortadora.addChild(tgSoporte);
        cortadora.addChild(tgPanel);
        
        return cortadora;
    }
}
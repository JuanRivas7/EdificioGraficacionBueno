package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class BandaTransportadora extends TransformGroup {
    
    // Texturas comunes (deberías tenerlas en tu carpeta img)
    private static final String TEXTURA_METAL = "metal.jpg";
    private static final String TEXTURA_MADERA = "madera.jpg";
    private static final String TEXTURA_ACERO = "acero.jpg";
    
    public BandaTransportadora(float posX, float posY, float posZ, 
                             float largo, float ancho, float altura) {
        // Configurar transformación para la posición
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3f(posX, posY, posZ));
        this.setTransform(posicion);
        
        // Crear componentes estáticos con dimensiones personalizadas
        crearComponentes(largo, ancho, altura);
    }
    
    private void crearComponentes(float largo, float ancho, float altura) {
        // 1. Base metálica
        Appearance apBase = crearAparienciaMetalica(new Color3f(0.4f, 0.4f, 0.4f));
        Box base = new Box(largo/2, altura/4, ancho/2, Primitive.GENERATE_NORMALS, apBase);
        
        TransformGroup tgBase = new TransformGroup();
        Transform3D transformBase = new Transform3D();
        transformBase.setTranslation(new Vector3f(0.0f, -altura/2, 0.0f));
        tgBase.setTransform(transformBase);
        tgBase.addChild(base);
        
        // 2. Superficie de la banda (color negro)
        Appearance apBanda = new Appearance();
        apBanda.setMaterial(new Material(
            new Color3f(0.1f, 0.1f, 0.1f), // Color negro
            new Color3f(0.1f, 0.1f, 0.1f),
            new Color3f(0.2f, 0.2f, 0.2f),
            new Color3f(0.0f, 0.0f, 0.0f),
            5.0f
        ));
        
        Box banda = new Box(largo/2, 0.02f, ancho/2, Primitive.GENERATE_NORMALS, apBanda);
        
        // 3. Muros laterales
        Appearance apMuros = crearAparienciaMetalica(new Color3f(0.5f, 0.5f, 0.5f));
        float grosorMuro = 0.1f; // Grosor fijo para los muros
        
        // Muro lateral izquierdo
        Box muroIzquierdo = new Box(largo/2, altura/2, grosorMuro/2, Primitive.GENERATE_NORMALS, apMuros);
        TransformGroup tgMuroIzq = new TransformGroup();
        Transform3D transformMuroIzq = new Transform3D();
        transformMuroIzq.setTranslation(new Vector3f(0.0f, 0.0f, ancho/2 + grosorMuro/2));
        tgMuroIzq.setTransform(transformMuroIzq);
        tgMuroIzq.addChild(muroIzquierdo);
        
        // Muro lateral derecho
        Box muroDerecho = new Box(largo/2, altura/2, grosorMuro/2, Primitive.GENERATE_NORMALS, apMuros);
        TransformGroup tgMuroDer = new TransformGroup();
        Transform3D transformMuroDer = new Transform3D();
        transformMuroDer.setTranslation(new Vector3f(0.0f, 0.0f, -ancho/2 - grosorMuro/2));
        tgMuroDer.setTransform(transformMuroDer);
        tgMuroDer.addChild(muroDerecho);
        
        this.addChild(tgBase);
        this.addChild(banda);
        this.addChild(tgMuroIzq);
        this.addChild(tgMuroDer);
    }
    
 // ========== MÁQUINAS SIMPLIFICADAS ==========
    
    public static TransformGroup crearAmasadora(float posX, float posY, float posZ) {
        TransformGroup tg = new TransformGroup();
        Transform3D pos = new Transform3D();
        pos.setTranslation(new Vector3f(posX, posY, posZ));
        tg.setTransform(pos);
        
        // Solo 2 componentes básicos
        Appearance ap = crearAparienciaMetalica(new Color3f(0.5f, 0.5f, 0.5f));
        
        // Base (nueva instancia)
        Box base = new Box(0.5f, 0.1f, 0.5f, Primitive.GENERATE_NORMALS, ap);
        tg.addChild(base);
        
        // Cilindro principal (nueva instancia)
        Cylinder cuerpo = new Cylinder(0.3f, 0.6f, Primitive.GENERATE_NORMALS, ap);
        TransformGroup tgCuerpo = new TransformGroup();
        Transform3D posCuerpo = new Transform3D();
        posCuerpo.setTranslation(new Vector3f(0f, 0.6f, 0f));
        tgCuerpo.setTransform(posCuerpo);
        tgCuerpo.addChild(cuerpo);
        tg.addChild(tgCuerpo);
        
        return tg;
    }
    
    public static TransformGroup crearHorno(float posX, float posY, float posZ) {
        TransformGroup tg = new TransformGroup();
        Transform3D pos = new Transform3D();
        pos.setTranslation(new Vector3f(posX, posY, posZ));
        tg.setTransform(pos);
        
        // Solo 1 componente básico
        Appearance ap = crearAparienciaMetalica(new Color3f(0.6f, 0.6f, 0.6f));
        
        // Caja principal (nueva instancia)
        Box horno = new Box(0.6f, 0.8f, 0.5f, Primitive.GENERATE_NORMALS, ap);
        tg.addChild(horno);
        
        return tg;
    }
    
    public static TransformGroup crearCortadora(float posX, float posY, float posZ) {
        TransformGroup tg = new TransformGroup();
        Transform3D pos = new Transform3D();
        pos.setTranslation(new Vector3f(posX, posY, posZ));
        tg.setTransform(pos);
        
        Appearance ap = crearAparienciaMetalica(new Color3f(0.4f, 0.4f, 0.4f));
        
        // Base (nueva instancia)
        Box base = new Box(0.5f, 0.1f, 0.5f, Primitive.GENERATE_NORMALS, ap);
        tg.addChild(base);
        
        // Cuchilla (nueva instancia)
        Box cuchilla = new Box(0.4f, 0.02f, 0.4f, Primitive.GENERATE_NORMALS, ap);
        TransformGroup tgCuchilla = new TransformGroup();
        Transform3D posCuchilla = new Transform3D();
        posCuchilla.setTranslation(new Vector3f(0f, 0.3f, 0f));
        tgCuchilla.setTransform(posCuchilla);
        tgCuchilla.addChild(cuchilla);
        tg.addChild(tgCuchilla);
        
        return tg;
    }
    
    public static TransformGroup crearEnfriadora(float posX, float posY, float posZ) {
        TransformGroup tg = new TransformGroup();
        Transform3D pos = new Transform3D();
        pos.setTranslation(new Vector3f(posX, posY, posZ));
        tg.setTransform(pos);
        
        Appearance ap = crearAparienciaMetalica(new Color3f(0.7f, 0.7f, 0.7f));
        
        // Solo 1 componente básico (nueva instancia)
        Box enfriadora = new Box(0.5f, 0.7f, 0.5f, Primitive.GENERATE_NORMALS, ap);
        tg.addChild(enfriadora);
        
        return tg;
    }
    
    // Métodos auxiliares (se mantienen igual)
     public TransformGroup crearCilindroPresion(float posX, float posY, float posZ, 
                                               float radio, float altura) {
         // Crear el grupo de transformación para el cilindro
         TransformGroup tgCilindro = new TransformGroup();
         Transform3D transformCilindro = new Transform3D();
         transformCilindro.setTranslation(new Vector3f(posX, posY, posZ));
         tgCilindro.setTransform(transformCilindro);
         
         // Apariencia del cilindro (metal plateado con detalles)
         Appearance apCilindro = new Appearance();
         Material material = new Material();
         material.setAmbientColor(new Color3f(0.8f, 0.8f, 0.8f));
         material.setDiffuseColor(new Color3f(0.9f, 0.9f, 0.9f));
         material.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
         material.setShininess(80.0f);
         apCilindro.setMaterial(material);
         
         // Crear el cilindro principal
         Cylinder cilindro = new Cylinder(radio, altura, 
                                        Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 
                                        apCilindro);
         
         // Crear la tapa superior (indicador de presión)
         Appearance apTapa = new Appearance();
         Material materialTapa = new Material();
         materialTapa.setAmbientColor(new Color3f(0.9f, 0.1f, 0.1f)); // Rojo
         materialTapa.setDiffuseColor(new Color3f(0.9f, 0.2f, 0.2f));
         apTapa.setMaterial(materialTapa);
         
         // Tapa superior
         Cylinder tapaSuperior = new Cylinder(radio*0.8f, altura*0.05f, 
                                            Primitive.GENERATE_NORMALS, apTapa);
         TransformGroup tgTapaSuperior = new TransformGroup();
         Transform3D transformTapa = new Transform3D();
         transformTapa.setTranslation(new Vector3f(0.0f, altura/2 + altura*0.05f/2, 0.0f));
         tgTapaSuperior.setTransform(transformTapa);
         tgTapaSuperior.addChild(tapaSuperior);
         
         // Base del cilindro (soporte)
         Appearance apBase = crearAparienciaMetalica(new Color3f(0.3f, 0.3f, 0.3f));
         Cylinder base = new Cylinder(radio*1.2f, altura*0.1f, 
                                    Primitive.GENERATE_NORMALS, apBase);
         TransformGroup tgBase = new TransformGroup();
         Transform3D transformBase = new Transform3D();
         transformBase.setTranslation(new Vector3f(0.0f, -altura/2 - altura*0.1f/2, 0.0f));
         tgBase.setTransform(transformBase);
         tgBase.addChild(base);
         
         // Agregar todos los componentes del cilindro
         tgCilindro.addChild(cilindro);
         tgCilindro.addChild(tgTapaSuperior);
         tgCilindro.addChild(tgBase);
         
         return tgCilindro;
     }
    
    private static Appearance crearAparienciaMetalica(Color3f color) {
        Appearance appearance = new Appearance();
        Material material = new Material();
        material.setAmbientColor(color);
        material.setDiffuseColor(new Color3f(0.7f, 0.7f, 0.7f));
        material.setSpecularColor(new Color3f(0.9f, 0.9f, 0.9f));
        material.setShininess(50.0f);
        appearance.setMaterial(material);
        return appearance;
    }
}
package Principal;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import java.io.File;
import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class Textura {
    private static Textura instance = new Textura();
    
    public static Textura getInstance() {
        return instance;
    }
    
    public Appearance crearTexturas(String nomarch) {
        String ruta = new File("").getAbsolutePath() + "/src/img/" + nomarch;
        TextureLoader loader = new TextureLoader(ruta, new Container());
        Texture texture = loader.getTexture();
        
        Appearance app = new Appearance();
        app.setTexture(texture);
        
        // Configuración para doble cara
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(polyAttr);
        
        return app;
    }
    
    public static void rotateTG(TransformGroup tg, float x, float y, float z) {
        Transform3D transform = new Transform3D();
        tg.getTransform(transform);
        
        Transform3D rotation = new Transform3D();
        rotation.rotX(Math.toRadians(x));
        transform.mul(rotation);
        
        rotation.rotY(Math.toRadians(y));
        transform.mul(rotation);
        
        rotation.rotZ(Math.toRadians(z));
        transform.mul(rotation);
        
        tg.setTransform(transform);
    }
    
    public static void moveTG(TransformGroup tg, float x, float y, float z) {
        Transform3D transform = new Transform3D();
        tg.getTransform(transform);
        
        Vector3f translation = new Vector3f(x, y, z);
        transform.setTranslation(translation);
        
        tg.setTransform(transform);
    }
}
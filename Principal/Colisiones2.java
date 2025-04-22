package Principal;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class Colisiones2 {
    
    public boolean hayColision(TransformGroup tg1, Box caja1, TransformGroup tg2, Box caja2) {
        // Obtener transformaciones absolutas
        Transform3D t3d1 = obtenerTransformacionAbsoluta(tg1);
        Transform3D t3d2 = obtenerTransformacionAbsoluta(tg2);
        
        // Obtener posiciones centrales (forma correcta)
        Vector3d centro1 = new Vector3d();
        Vector3d centro2 = new Vector3d();
        t3d1.get(centro1);  // Esto obtiene la componente de traslación
        t3d2.get(centro2);
        
        // Obtener dimensiones considerando escala
        double[] dimensiones1 = obtenerDimensionesReales(caja1, t3d1);
        double[] dimensiones2 = obtenerDimensionesReales(caja2, t3d2);
        
        // Calcular límites
        double[] limites1 = {
            centro1.x - dimensiones1[0]/2, // minX
            centro1.x + dimensiones1[0]/2, // maxX
            centro1.y - dimensiones1[1]/2, // minY
            centro1.y + dimensiones1[1]/2, // maxY
            centro1.z - dimensiones1[2]/2, // minZ
            centro1.z + dimensiones1[2]/2  // maxZ
        };
        
        double[] limites2 = {
            centro2.x - dimensiones2[0]/2,
            centro2.x + dimensiones2[0]/2,
            centro2.y - dimensiones2[1]/2,
            centro2.y + dimensiones2[1]/2,
            centro2.z - dimensiones2[2]/2,
            centro2.z + dimensiones2[2]/2
        };
        
        // Verificar superposición en los tres ejes
        boolean colisionX = limites1[0] < limites2[1] && limites1[1] > limites2[0];
        boolean colisionY = limites1[2] < limites2[3] && limites1[3] > limites2[2];
        boolean colisionZ = limites1[4] < limites2[5] && limites1[5] > limites2[4];
        
        return colisionX && colisionY && colisionZ;
    }
    
    private double[] obtenerDimensionesReales(Box caja, Transform3D transform) {
        Vector3d escala = new Vector3d();
        transform.getScale(escala);
        
        return new double[] {
            caja.getXdimension() * Math.abs(escala.x),
            caja.getYdimension() * Math.abs(escala.y),
            caja.getZdimension() * Math.abs(escala.z)
        };
    }
    
    private Transform3D obtenerTransformacionAbsoluta(TransformGroup tg) {
        Transform3D result = new Transform3D();
        if (tg == null) return result;
        
        tg.getTransform(result);
        
        // Acumular transforms de los padres
        while (tg.getParent() instanceof TransformGroup) {
            tg = (TransformGroup) tg.getParent();
            Transform3D parentTransform = new Transform3D();
            tg.getTransform(parentTransform);
            result.mul(parentTransform, result);
        }
        return result;
    }
}

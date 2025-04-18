/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.*;

public class Colisiones {
    
    public boolean hayColision(TransformGroup tg1, Box caja1, TransformGroup tg2, Box caja2) {
        // Obtener transformaciones absolutas
        Transform3D t3d1 = obtenerTransformacionAbsoluta(tg1);
        Transform3D t3d2 = obtenerTransformacionAbsoluta(tg2);
        
        // Obtener posiciones centrales
        Vector3d centro1 = new Vector3d();
        Vector3d centro2 = new Vector3d();
        t3d1.get(centro1);
        t3d2.get(centro2);
        
        // Obtener dimensiones
        Vector3d dim1 = new Vector3d(
            caja1.getXdimension(),
            caja1.getYdimension(),
            caja1.getZdimension()
        );
        
        Vector3d dim2 = new Vector3d(
            caja2.getXdimension(),
            caja2.getYdimension(),
            caja2.getZdimension()
        );
        
        // Calcular límites en los tres ejes
        double minX1 = centro1.x - dim1.x/2;
        double maxX1 = centro1.x + dim1.x/2;
        double minY1 = centro1.y - dim1.y/2;
        double maxY1 = centro1.y + dim1.y/2;
        double minZ1 = centro1.z - dim1.z/2;
        double maxZ1 = centro1.z + dim1.z/2;
        
        double minX2 = centro2.x - dim2.x/2;
        double maxX2 = centro2.x + dim2.x/2;
        double minY2 = centro2.y - dim2.y/2;
        double maxY2 = centro2.y + dim2.y/2;
        double minZ2 = centro2.z - dim2.z/2;
        double maxZ2 = centro2.z + dim2.z/2;
        
        // Verificar superposición en los tres ejes
        boolean colisionX = (maxX1 > minX2) && (minX1 < maxX2);
        boolean colisionY = (maxY1 > minY2) && (minY1 < maxY2);
        boolean colisionZ = (maxZ1 > minZ2) && (minZ1 < maxZ2);
        
        // Solo hay colisión si hay superposición en los tres ejes
        return colisionX && colisionY && colisionZ;
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
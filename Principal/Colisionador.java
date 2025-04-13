package Principal;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;

public class Colisionador {
    private List<Shape3D> objetosColisionables;
    private TransformGroup tgPersonaje;
    private BoundingSphere boundsPersonaje;
    private double radioColision = 0.3;

    public Colisionador(TransformGroup tgPersonaje) {
        this.tgPersonaje = tgPersonaje;
        this.boundsPersonaje = new BoundingSphere(new Point3d(), radioColision);
        this.objetosColisionables = new ArrayList<>();
    }

    public void agregarObjetoColisionable(Shape3D objeto) {
        objetosColisionables.add(objeto);
    }

    public boolean verificarColision(Vector3d movimiento) {
        Transform3D t3d = new Transform3D();
        tgPersonaje.getTransform(t3d);
        
        Vector3d posicionActual = new Vector3d();
        t3d.get(posicionActual);
        
        Point3d nuevaPosicion = new Point3d(
            posicionActual.x + movimiento.x,
            posicionActual.y + movimiento.y,
            posicionActual.z + movimiento.z
        );
        
        boundsPersonaje.setRadius(radioColision);
        boundsPersonaje.setCenter(nuevaPosicion);
        
        for (Shape3D objeto : objetosColisionables) {
            Bounds boundsObjeto = objeto.getBounds();
            if (boundsObjeto != null && boundsObjeto.intersect(boundsPersonaje)) {
                return true;
            }
        }
        
        return false;
    }
}
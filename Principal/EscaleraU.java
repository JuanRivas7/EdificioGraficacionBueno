package Principal;
 
 import com.sun.j3d.utils.geometry.Box;
 import com.sun.j3d.utils.geometry.Primitive;
 import javax.media.j3d.*;
 import javax.vecmath.*;
 
 import java.util.ArrayList;
 
 public class EscaleraU {
 
     private EscenaGrafica escena;
     private TransformGroup tgEscalera;
     private ArrayList<TransformGroup> peldañosTG = new ArrayList<>();
     private ArrayList<Box> peldañosBox = new ArrayList<>();
     private Colisiones2 colisiones;
 
     public EscaleraU(EscenaGrafica escena, Colisiones2 colisiones) {
         this.escena = escena;
         this.colisiones = colisiones;
         tgEscalera = new TransformGroup();
         tgEscalera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
         escena.tgMundo.addChild(tgEscalera);
     }
 
    public void construir(float xInicio, float yInicio, float zInicio) {
     int numPeldaños = 5;
     float ancho = 0.3f, alto = 0.05f, profundo = 0.2f;
     float separacion = 0.25f;
 
     Appearance visible = crearAparienciaVisible();
 
     // Primer tramo (sube en Y y Z negativo)
     for (int i = 0; i < numPeldaños; i++) {
         float y = yInicio + (i * alto);
         float z = zInicio - (i * separacion);
         boolean esColisionable = (i == 0 || i == numPeldaños - 1); // Solo el primero y último
         agregarPeldaño(xInicio, y, z, ancho, alto, profundo, visible, esColisionable);
     }
 
     // Descansillo (sin colisión)
     agregarPeldaño(
         xInicio,
         yInicio + (numPeldaños * alto),
         zInicio - (numPeldaños * separacion) - 0.3f,
         ancho,
         alto,
         0.4f,
         visible,
         false
     );
 
     // Segundo tramo (sube en Y y Z positivo, pero desplazado en X)
     float desplazamientoX = -0.6f;
     for (int i = 1; i <= numPeldaños; i++) {
         float y = yInicio + (numPeldaños + i) * alto;
         float z = zInicio - (numPeldaños * separacion) - 0.3f + (i * separacion);
         float x = xInicio + desplazamientoX;
         boolean esColisionable = (i == 1 || i == numPeldaños); // Solo el primero y último del segundo tramo
         agregarPeldaño(x, y, z, ancho, alto, profundo, visible, esColisionable);
     }
 }
 
 
     private void agregarPeldaño(float x, float y, float z, float ancho, float alto, float profundo, Appearance app, boolean esColisionable) {
     Transform3D t3d = new Transform3D();
     t3d.setTranslation(new Vector3f(x, y, z));
     TransformGroup tg = new TransformGroup(t3d);
     tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 
     Box peldaño = new Box(ancho, alto, profundo, Primitive.GENERATE_NORMALS, app);
     tg.addChild(peldaño);
     tgEscalera.addChild(tg);
 
     peldañosTG.add(tg);
     peldañosBox.add(peldaño);
 
     // 🔥 Solo registramos colisión si es necesario
     if (esColisionable) {
         escena.listaTransform.add(tg);
         escena.listaBoxs.add(peldaño);
     }
 }
 
 
     private Appearance crearAparienciaVisible() {
         Appearance app = new Appearance();
         ColoringAttributes color = new ColoringAttributes(new Color3f(1f, 0f, 0f), ColoringAttributes.SHADE_FLAT); // Rojo
         app.setColoringAttributes(color);
 
         Material material = new Material();
         material.setDiffuseColor(new Color3f(1f, 0f, 0f)); // Rojo brillante
         app.setMaterial(material);
 
         return app;
     }
 }
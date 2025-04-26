/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

//import com.fazecast.jSerialComm.SerialPort;
//import com.fazecast.jSerialComm.SerialPort;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import steve.crearEscenaGrafica;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Link;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SharedGroup;
import javax.media.j3d.Texture;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

/**
 *
 * @author jriva
 */
public class EscenaGrafica {

    SalonDeClasesBuilder salonBuilder;
    InstanciadorObjetos3D instanciador;

    ArrayList<TransformGroup> listaTransform = new ArrayList<>();
    ArrayList<Box> listaBoxs = new ArrayList<>();
    ArrayList<Ventana> listaVentanas = new ArrayList<>();
    ArrayList<Puerta> listaPuerta = new ArrayList<>();
    ArrayList<PuertasDobles> listaPuertaD = new ArrayList<>();
    BranchGroup objRaiz = new BranchGroup();
    static Textura textura = new Textura();
    int paraTextura = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    color c = new color();
    TransformGroup tgMundo;
    TransformGroup tgPiso;
    crearEscenaGrafica steve = new crearEscenaGrafica();
    Point3d posPersonaje = new Point3d(0, 0, 0);
    Colisiones Colisiones = new Colisiones();
    Colisiones2 Colisiones2 = new Colisiones2();
//    SerialPort puerto;

    public EscenaGrafica(java.awt.Component canvas) {
        RepositorioObjetos3D.inicializar();
        //------- MUNDO--------
        Box bxMundo = new Box(-16.0f, 20.0f, 20.0f, paraTextura, textura.crearTexturas("cielo_1.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dMundo = new Transform3D();
        t3dMundo.set(new Vector3d(0.0f, -0.15f, 0.0f));
        tgMundo = new TransformGroup(t3dMundo);
        //--------PISO-----------
        Box bxPiso = new Box(2.f, 0.05f, 2.0f, paraTextura, textura.crearTexturas("piso_1.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dPiso = new Transform3D();
        t3dPiso.set(new Vector3d(0.0f, -0.28f, 0.0f));
        tgPiso = new TransformGroup(t3dPiso);
        EscalarTG(tgPiso, 5.0f);
        //------------SALON 1-------
        tgMundo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgPiso.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //||--------------MOVER EL MOUSE----------||
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tgMundo);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        //------------Termina pa que se mueva-------------
        //----------ADD CHILD----------
        tgMundo.addChild(bxMundo);
        tgPiso.addChild(bxPiso);
        listaBoxs.add(bxPiso);
        //objRaiz.addChild(tgPiso);
        objRaiz.addChild(myMouseRotate);
        objRaiz.addChild(tgMundo);
        steve.Posicion(0, 0.1f, 2f);
        tgMundo.addChild(tgPiso);
        steve.girarTG(steve.obtenerPanza(), 180, "Y");
        objRaiz.addChild(steve.obtenerCuerpo());
        configurarIluminacion(objRaiz);
        instanciador = new InstanciadorObjetos3D(tgMundo, listaTransform, listaBoxs);
//        conectarPuerto();
        registrarControlesPorTeclado(canvas);
//         //Mesa 60cm x 40cm x 60cm aprox (en metros: 0.3f, 0.2f, 0.3f)
        //agregarInstanciaConColision(RepositorioObjetos3D.mesa, 1.5f, 0f, 0f, 0.3f, 0.2f, 0.3f);

        BandaTransportadora banda = new BandaTransportadora(3, 0.2f, -4, 4, 0.28f, 0.45f);

        // Crear y agregar un cilindro
        TransformGroup cilindro = banda.crearCilindroPresion(
                -0.3f, // posX
                0.3f, // posY
                -3.0f, // posZ
                0.5f, // radio
                0.55f // altura
        );
  
agregarInstancia(Maquinas.crearAmasadora(), -3.5f, 0.0f, -3.0f);
//    agregarInstancia(Maquinas.crearRefrigerador(), -2.5f, 0.0f, -3.0f);
//    agregarInstancia(Maquinas.crearHorno(), -1.5f, 0.0f, -3.0f);
//    agregarInstancia(Maquinas.crearCortadora(), -0.5f, 0.0f, -3.0f);

        tgMundo.addChild(cilindro);
        tgMundo.addChild(banda);
        //----------PISO 1-----------
        //Salon Izquierda y entrada--------------
        //Entrada Principal
        crearPuertaDoble(0.5f, 0.38f, 6.0f, 0.8f, 0.8f, 0.05f, 0);//Puerta Entrada PDerecha
        crearPuertaDoble(-0.5f, 0.38f, 6.0f, 0.8f, 0.8f, 0.05f, 0);//Puerta Entrada PIzq
        //Entrada ALimentarias
        crearPuertaDoble(0.5f, 0.38f, -0.4f, 0.8f, 0.8f, 0.05f, 0);//Puerta Entrada Alimentarias
        crearVentanaCerrada(-0.1f, 0.18f, -0.4f, 0.4f, 0.4f, 0.05f, 0);//Ventana chica Abajp Izq
        crearVentanaCerrada(-0.1f, 0.58f, -0.4f, 0.4f, 0.4f, 0.05f, 0);//Ventana Chica Arriba Izq
        crearVentanaCerrada(1.15f, 0.18f, -0.4f, 0.5f, 0.4f, 0.05f, 0);//Ventana chica Abajp Der
        crearVentanaCerrada(1.15f, 0.58f, -0.4f, 0.5f, 0.4f, 0.05f, 0);//Ventana Chica Arriba Der
        crearPisoSalon(0.0f, -0.02f, 4.06f, 1.0f, 0.002f, 2.05f);//Piso Entrada Principal
        crearParedCompleta(1.0f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y para estar sobre piso
        crearParedCompleta(0.0f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y para estar sobre piso
        crearParedCompleta(-1.0f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muto salon 1
        crearParedCompleta(-1.4f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 1
        crearParedCompleta(-1.8f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 2
        crearParedCompleta(-2.2f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 2
        crearParedCompleta(-2.6f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 3
        crearParedCompleta(-3.0f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 3
        crearParedCompleta(-3.4f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 4
        crearParedCompleta(-3.65f, 0.18f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 4
        crearVentana(-1.55f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre1
        crearVentanaCerrada(-1.25f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre1
        crearVentana(-2.35f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre2
        crearVentanaCerrada(-2.05f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre2
        crearVentana(-3.15f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre3
        crearVentanaCerrada(-2.85f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre3
        crearVentanaCerrada(-3.65f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre4
        crearParedCompleta(-4.2f, 0.38f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0);//pared final
        crearParedCompleta(-4.65f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90);//pared VerticalFinal
        crearParedCompleta(-1.0f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90);//pared VerticalFinal
        crearPuerta(-1.3f, 0.38f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-1.7f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y muro salon adentro 1
        crearParedCompleta(-2.2f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 1
        crearParedCompleta(-2.8f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearParedCompleta(-3.4f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCompleta(-4.0f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearParedCompleta(-4.4f, 0.38f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

//<<<<<<< HEAD
        //------------CUBICULOS IZQUIERDA----------
        crearParedCompleta(-4.65f, 0.18f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared salon adentro 2
        crearVentanaCerrada(-1.55f, 0.58f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 0.58f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 0.58f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearParedCompleta(-2.6f, 0.38f, -0.45f, 2.1f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCompleta(1.5f, 0.38f, -0.45f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//Puerta Entrar Laboratorio Alimentarias1
        crearParedCompleta(-0.4f, 0.38f, -0.45f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//Puerta Entrar Laboratorio Alimentarias2
        crearParedCubiculo(-3.3f, 0.18f, 1.4f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearParedCubiculo(-1.6f, 0.18f, 1.4f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo2
        crearPuerta(-3.65f, 0.38f, 0.9f, 0.7f, 0.8f, 0.05f, 0);//Puerta Hazta el final 1
        crearPuerta(-1.95f, 0.38f, 0.9f, 0.7f, 0.8f, 0.05f, 0);//Puerta mas Cerca entrada2
        crearParedCubiculo(-4.3f, 0.18f, 0.9f, 0.6f, 0.4f, 0.05f, 0);//pared A un lado Puerta1
        crearParedCubiculo(-2.79f, 0.18f, 0.9f, 0.98f, 0.4f, 0.05f, 0);//pared A un lado Puerta2
        crearVentanaCerrada(-4.3f, 0.58f, 0.9f, 0.6f, 0.4f, 0.05f, 0);//Ventana Fondo
        crearVentanaCerrada(-2.79f, 0.58f, 0.9f, 0.98f, 0.4f, 0.05f, 0);//Ventana Principio
        crearVentanaCerrada(-1.4f, 0.58f, -3.3f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearVentanaCerrada(-1.4f, 0.58f, -1.6f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearPisoSalon(-2.8f, -0.02f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso salon Piso1 Izquierda
        crearPisoSalon(-3.1f, -0.02f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Fondo
        crearPisoSalon(-0.1f, -0.02f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Mas cerca entrada Salon

//=======
        //------------ZonaEscaleras-------------
        crearParedCompleta(2.3f, 0.38f, 6.0f, 1.2f, 0.4f, 0.1f, 255, 167, 38, 0);//0.38Y para estar sobre pis
        crearParedCompleta(1.0f, 0.38f, 4.0f, 2.0f, 0.4f, 0.08f, 255, 167, 38, 90);//Muro naranja Pasillo Entrada
        crearParedCompleta(2.5f, 0.38f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro Pasillo Entrada
        crearParedCompleta(1.0f, 0.38f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro2 Pasillo Entrada
        crearParedCompleta(3.08f, 0.38f, 2.0f, 0.48f, 0.4f, 0.05f, 255, 167, 38, 0);//Falta Ajustar
        EscaleraU escalera = new EscaleraU(this, Colisiones2);
        escalera.construir(1.7f, 0.0f, 2.0f, 1.2f, 0.8f, 1.6f, 180);
        crearPisoSalon2(2.3f, -0.009f, 3.8f, 1.25f, 0.001f, 1.9f);
        // agregarCajitaTeletransporte(0.0f, -0.03f, -1.0f);//Falta Posicionar
        //agregarCajitaTeletransporte(-0.6f, 0.2f, -3.0f);//Falta Posicionar
        //-------------Salon derecha-----------
        crearParedCompleta(3.6f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 1
        crearParedCompleta(4.0f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 1
        crearParedCompleta(4.4f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 2
        crearParedCompleta(4.8f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(5.2f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 3
        crearParedCompleta(5.6f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 3
        crearParedCompleta(6.0f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 4
        crearParedCompleta(6.25f, 0.18f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 4

        // Ventanas 
        crearVentana(4.15f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre1
        crearVentanaCerrada(3.85f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre1
        crearVentana(4.95f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre2
        crearVentanaCerrada(4.65f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre2
        crearVentana(5.75f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre3
        crearVentanaCerrada(5.45f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre3
        crearVentanaCerrada(6.25f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre4

        // Paredes 
        crearParedCompleta(6.8f, 0.38f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0); //pared final
        crearParedCompleta(7.25f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
        crearParedCompleta(3.6f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)

        // Puerta y paredes internas 
        crearPuerta(3.9f, 0.38f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(4.3f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 1
        crearParedCompleta(4.8f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 1
        crearParedCompleta(5.4f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(6.0f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearParedCompleta(6.6f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(7.0f, 0.38f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2

        //-------------Salon derecha-----------
        crearParedCompleta(3.6f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 1
        crearParedCompleta(4.0f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 1
        crearParedCompleta(4.4f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 2
        crearParedCompleta(4.8f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(5.2f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 3
        crearParedCompleta(5.6f, 0.18f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 3
        crearParedCompleta(6.0f, 0.38f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 4
        crearParedCompleta(6.25f, 0.18f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 4

        // Ventanas 
        crearVentana(4.15f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre1
        crearVentanaCerrada(3.85f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre1
        crearVentana(4.95f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre2
        crearVentanaCerrada(4.65f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre2
        crearVentana(5.75f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre3
        crearVentanaCerrada(5.45f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre3
        crearVentanaCerrada(6.25f, 0.58f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre4

        // Paredes 
        crearParedCompleta(6.8f, 0.38f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0); //pared final
        crearParedCompleta(7.25f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
        crearParedCompleta(3.6f, 0.38f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)

        // Puerta y paredes internas 
        crearPuerta(3.9f, 0.38f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(4.3f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 1
        crearParedCompleta(4.8f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 1
        crearParedCompleta(5.4f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(6.0f, 0.38f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearParedCompleta(6.6f, 0.38f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(7.0f, 0.38f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearPisoSalon(5.4f, -0.02f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso Salon Derecha

        //------------CUBICULOS DERECHA----------
        crearParedCompleta(7.25f, 0.18f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared Externa 1
        crearVentanaCerrada(-1.55f, 0.58f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 0.58f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 0.58f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearParedCompleta(4.45f, 0.38f, -0.45f, 2.85f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCubiculo(5.75f, 0.18f, 1.5f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearParedCubiculo(4.25f, 0.18f, 1.5f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo2
        crearPuerta(6.12f, 0.38f, 1.0f, 0.7f, 0.8f, 0.05f, 0);//Puerta Hazta el final 1
        crearPuerta(4.62f, 0.38f, 1.0f, 0.7f, 0.8f, 0.05f, 0);//Puerta mas Cerca entrada2
        crearParedCubiculo(6.82f, 0.18f, 1.0f, 0.7f, 0.4f, 0.05f, 0);//pared A un lado Puerta1
        crearParedCubiculo(5.35f, 0.18f, 1.0f, 0.78f, 0.4f, 0.05f, 0);//pared A un lado Puerta2
        crearVentanaCerrada(6.82f, 0.58f, 1.0f, 0.7f, 0.4f, 0.05f, 0);//Ventana Fondo
        crearVentanaCerrada(5.35f, 0.58f, 1.0f, 0.78f, 0.4f, 0.05f, 0);//Ventana Principio
        crearVentanaCerrada(-1.5f, 0.58f, 5.75f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearVentanaCerrada(-1.5f, 0.58f, 4.25f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Cerca entrada
        crearPisoSalon(5.75f, -0.02f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso Hasta el fondo
        crearPisoSalon(2.75f, -0.02f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso mas cerca entrada salon

        //----------PISO 2-------------
        //Salon Izquierda y entrada--------------
        crearPisoSalon(0.0f, 0.78f, 4.06f, 1.0f, 0.002f, 2.05f);//Piso Entrada Principal
        crearParedCompleta(1.0f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y para estar sobre piso
        crearParedCompleta(0.0f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y para estar sobre piso
        crearParedCompleta(-1.0f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muto salon 1
        crearParedCompleta(-1.4f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 1
        crearParedCompleta(-1.8f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 2
        crearParedCompleta(-2.2f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 2
        crearParedCompleta(-2.6f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 3
        crearParedCompleta(-3.0f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 3
        crearParedCompleta(-3.4f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 4
        crearParedCompleta(-3.65f, 0.98f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 4
        crearVentana(-1.55f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre1
        crearVentanaCerrada(-1.25f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre1
        crearVentana(-2.35f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre2
        crearVentanaCerrada(-2.05f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre2
        crearVentana(-3.15f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre3
        crearVentanaCerrada(-2.85f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre3
        crearVentanaCerrada(-3.65f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre4
        crearParedCompleta(-4.2f, 1.18f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0);//pared final
        crearParedCompleta(-4.65f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90);//pared VerticalFinal
        crearParedCompleta(-1.0f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90);//pared VerticalFinal
        crearPuerta(-1.3f, 1.18f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-1.7f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y muro salon adentro 1
        crearParedCompleta(-2.2f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 1
        crearParedCompleta(-2.8f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearParedCompleta(-3.4f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCompleta(-4.0f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearParedCompleta(-4.4f, 1.18f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

        //Cuarto Arriba escaleras
        crearVentana(-0.71f, 1.38f, 6.0f, 0.40f, 0.4f, 0.05f, 0);//Ventana se abre3
        crearVentanaCerrada(-0.31f, 1.38f, 6.0f, 0.40f, 0.4f, 0.05f, 0);//Ventana no se abre3
        crearParedCompleta(-0.71f, 0.98f, 6.0f, 0.61f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

        crearVentana(0.3f, 1.38f, 6.0f, 0.40f, 0.4f, 0.05f, 0);//Ventana se abre3
        crearVentanaCerrada(0.7f, 1.38f, 6.0f, 0.40f, 0.4f, 0.05f, 0);//Ventana no se abre3
        crearParedCompleta(0.3f, 0.98f, 6.0f, 0.61f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

        crearPuerta(0.6f, 1.18f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-0.35f, 1.18f, 2.0f, 0.65f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCompleta(0.3f, 1.18f, 1.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//0.38Y muro salon adentro 1
        crearParedCompleta(-0.61f, 1.18f, 1.0f, 1.0f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

        //------------CUBICULOS IZQUIERDA----------
        crearParedCompleta(-4.65f, 0.98f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared salon adentro 2
        crearVentanaCerrada(-1.55f, 1.38f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 1.38f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 1.38f, -4.65f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        //Viendo Laboratorio Alimentarias Izquierda
        crearParedCompleta(-2.6f, 0.9f, -0.45f, 2.1f, 0.15f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearParedCompleta(-2.6f, 1.48f, -0.45f, 2.1f, 0.1f, 0.05f, 255, 167, 38, 0);//pared Externa ArribaCentro Viendo Alimentarias
        crearParedCompleta(-4.3f, 1.22f, -0.45f, 0.4f, 0.17f, 0.05f, 255, 167, 38, 0);//Minipared Pegada al exterior Lado Derecho
        crearVentanaCerrada(-3.4f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1
        crearVentanaCerrada(-2.4f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha2
        crearVentanaCerrada(-1.4f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha3
        crearVentanaCerrada(-0.7f, 1.22f, -0.45f, 0.4f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha4

        crearParedCompleta(1.5f, 1.18f, -0.45f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//Muro Blanco Viendo Centro Laboratorio Alimentarias1
        crearParedCompleta(-0.4f, 1.18f, -0.45f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//Muro Blanco Viendo Laboratorio Alimentarias2
        crearParedCubiculo(-3.3f, 0.98f, 1.4f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearParedCubiculo(-1.6f, 0.98f, 1.4f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo2
        crearPuerta(-3.65f, 1.18f, 0.9f, 0.7f, 0.8f, 0.05f, 0);//Puerta Hazta el final 1
        crearPuerta(-1.95f, 1.18f, 0.9f, 0.7f, 0.8f, 0.05f, 0);//Puerta mas Cerca entrada2
        crearParedCubiculo(-4.3f, 0.98f, 0.9f, 0.6f, 0.4f, 0.05f, 0);//pared A un lado Puerta1
        crearParedCubiculo(-2.79f, 0.98f, 0.9f, 0.98f, 0.4f, 0.05f, 0);//pared A un lado Puerta2
        crearVentanaCerrada(-4.3f, 1.38f, 0.9f, 0.6f, 0.4f, 0.05f, 0);//Ventana Fondo
        crearVentanaCerrada(-2.79f, 1.38f, 0.9f, 0.98f, 0.4f, 0.05f, 0);//Ventana Principio
        crearVentanaCerrada(-1.4f, 1.38f, -3.3f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearVentanaCerrada(-1.4f, 1.38f, -1.6f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearPisoSalon(-2.8f, 0.78f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso salon Piso1 Izquierda
        crearPisoSalon(-3.1f, 0.78f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Fondo
        crearPisoSalon(-0.1f, 0.78f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Mas cerca entrada Salon

        //------------ZonaEscaleras-------------
        crearParedCompleta(2.3f, 1.18f, 6.0f, 1.2f, 0.4f, 0.1f, 255, 167, 38, 0);//0.38Y para estar sobre pis
        crearParedCompleta(1.0f, 1.18f, 4.0f, 2.0f, 0.4f, 0.08f, 255, 167, 38, 90);//Muro naranja Pasillo Entrada
        crearParedCompleta(2.5f, 1.18f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro Pasillo Entrada
        crearParedCompleta(1.0f, 1.18f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro2 Pasillo Entrada
        crearParedCompleta(3.08f, 1.18f, 2.0f, 0.48f, 0.4f, 0.05f, 255, 167, 38, 0);//Falta Ajustar

        escalera.construir(1.7f, 0.8f, 2.0f, 1.2f, 0.8f, 1.6f, 180);
        //agregarCajitaTeletransporte(2.0f, 0.8f, 1.9f);//Falta Posicionar
        agregarCajitaTeletransporte(1.4f, 0.0f, 1.2f);        //-------------Salon derecha-----------
        crearParedCompleta(3.6f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 1
        crearParedCompleta(4.0f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 1
        crearParedCompleta(4.4f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 2
        crearParedCompleta(4.8f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(5.2f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 3
        crearParedCompleta(5.6f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 3
        crearParedCompleta(6.0f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 4
        crearParedCompleta(6.25f, 0.98f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 4

        // Ventanas 
        crearVentana(4.15f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre1
        crearVentanaCerrada(3.85f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre1
        crearVentana(4.95f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre2
        crearVentanaCerrada(4.65f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre2
        crearVentana(5.75f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre3
        crearVentanaCerrada(5.45f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre3
        crearVentanaCerrada(6.25f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre4

        // Paredes 
        crearParedCompleta(6.8f, 1.18f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0); //pared final
        crearParedCompleta(7.25f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
        crearParedCompleta(3.6f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)

        // Puerta y paredes internas 
        crearPuerta(3.9f, 1.18f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(4.3f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 1
        crearParedCompleta(4.8f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 1
        crearParedCompleta(5.4f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(6.0f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearParedCompleta(6.6f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(7.0f, 1.18f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2

        //-------------Salon derecha-----------
        crearParedCompleta(3.6f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 1
        crearParedCompleta(4.0f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 1
        crearParedCompleta(4.4f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 2
        crearParedCompleta(4.8f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(5.2f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 3
        crearParedCompleta(5.6f, 0.98f, 6.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 3
        crearParedCompleta(6.0f, 1.18f, 6.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 4
        crearParedCompleta(6.25f, 0.98f, 6.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 4

        // Ventanas 
        crearVentana(4.15f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre1
        crearVentanaCerrada(3.85f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre1
        crearVentana(4.95f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre2
        crearVentanaCerrada(4.65f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre2
        crearVentana(5.75f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre3
        crearVentanaCerrada(5.45f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre3
        crearVentanaCerrada(6.25f, 1.38f, 6.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre4

        // Paredes 
        crearParedCompleta(6.8f, 1.18f, 6.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0); //pared final
        crearParedCompleta(7.25f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
        crearParedCompleta(3.6f, 1.18f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)

        // Puerta y paredes internas 
        crearPuerta(3.9f, 1.18f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(4.3f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 1
        crearParedCompleta(4.8f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 1
        crearParedCompleta(5.4f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(6.0f, 1.18f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearParedCompleta(6.6f, 1.18f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(7.0f, 1.18f, 2.0f, 0.3f, 0.4f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearPisoSalon(5.4f, 0.78f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso Salon Derecha

        //------------CUBICULOS DERECHA----------
        crearParedCompleta(7.25f, 0.98f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared Externa 1
        crearVentanaCerrada(-1.55f, 1.38f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 1.38f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 1.38f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        //Vista Laboratorio Alimentarias
        crearParedCompleta(4.45f, 0.9f, -0.45f, 2.85f, 0.15f, 0.05f, 255, 167, 38, 0);//pared Externa Viendo Alimentarias
        crearParedCompleta(6.9f, 1.22f, -0.45f, 0.4f, 0.17f, 0.05f, 255, 167, 38, 0);//Minipared Pegada al exterior Lado Derecho
        crearVentanaCerrada(2.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1
        crearVentanaCerrada(3.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha2
        crearVentanaCerrada(4.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha3
        crearVentanaCerrada(5.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha4
        crearVentanaCerrada(6.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha5
        crearParedCompleta(4.45f, 1.48f, -0.45f, 2.85f, 0.1f, 0.05f, 255, 167, 38, 0);//pared Externa Viendo Alimentarias
        crearVentanaCerrada(1.0f, 1.22f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1//Ventana Viendo Alimentarias PtCentral
        crearVentanaCerrada(0.05f, 1.22f, -0.45f, 0.9f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1//Ventana Viendo Alimentarias PtCentra2
        crearParedCompleta(0.55f, 0.9f, -0.45f, 0.85f, 0.15f, 0.05f, 255, 167, 38, 0);//pared Externa AbajoCentro Viendo Alimentarias

        crearParedCompleta(0.55f, 1.48f, -0.45f, 0.85f, 0.1f, 0.05f, 255, 167, 38, 0);//pared Externa ArribaCentro Viendo Alimentarias

        crearParedCubiculo(5.75f, 0.98f, 1.5f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearParedCubiculo(4.25f, 0.98f, 1.5f, 1.0f, 0.4f, 0.05f, 90);//pared Division cubiculo2
        crearPuerta(6.12f, 1.18f, 1.0f, 0.7f, 0.8f, 0.05f, 0);//Puerta Hazta el final 1
        crearPuerta(4.62f, 1.18f, 1.0f, 0.7f, 0.8f, 0.05f, 0);//Puerta mas Cerca entrada2
        crearParedCubiculo(6.82f, 0.98f, 1.0f, 0.7f, 0.4f, 0.05f, 0);//pared A un lado Puerta1
        crearParedCubiculo(5.35f, 0.98f, 1.0f, 0.78f, 0.4f, 0.05f, 0);//pared A un lado Puerta2
        crearVentanaCerrada(6.82f, 1.38f, 1.0f, 0.7f, 0.4f, 0.05f, 0);//Ventana Fondo
        crearVentanaCerrada(5.35f, 1.38f, 1.0f, 0.78f, 0.4f, 0.05f, 0);//Ventana Principio
        crearVentanaCerrada(-1.5f, 1.38f, 5.75f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Fondo
        crearVentanaCerrada(-1.5f, 1.38f, 4.25f, 1.0f, 0.4f, 0.05f, 90);//Ventana Horizontal Cerca entrada
        crearPisoSalon(5.75f, 0.78f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso Hasta el fondo
        crearPisoSalon(2.75f, 0.78f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso mas cerca entrada salon

        //----------PISO 3-------------
//Salon Izquierda y entrada--------------
        crearPisoSalon(0.0f, 1.58f, 4.06f, 1.0f, 0.002f, 2.05f);//Piso Entrada Principal
        crearParedCompleta(1.0f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//1.98Y para estar sobre piso
        crearParedCompleta(0.4f, 1.78f, 5.0f, 0.5f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente -1
        crearParedCompleta(0.0f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//1.98Y para estar sobre piso
        crearParedCompleta(-0.4f, 1.78f, 5.0f, 0.5f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 0
        crearParedCompleta(-1.0f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 1
        crearPuerta(-1.4f, 1.98f, 5.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-1.8f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 2
        crearParedCompleta(-2.2f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 2
        crearParedCompleta(-2.6f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 3
        crearParedCompleta(-3.0f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 3
        crearParedCompleta(-3.4f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon 4
        crearParedCompleta(-3.75f, 1.78f, 5.0f, 0.25f, 0.2f, 0.05f, 255, 167, 38, 0);//pared enfrente 4
        crearVentana(0.3f, 2.18f, 5.0f, 0.4f, 0.4f, 0.05f, 0);//Ventana se abre-1
        crearVentanaCerrada(0.7f, 2.18f, 5.0f, 0.4f, 0.4f, 0.05f, 0);//Ventana no se abre-1

        crearVentana(-0.7f, 2.18f, 5.0f, 0.4f, 0.4f, 0.05f, 0);//Ventana se abre0
        crearVentanaCerrada(-0.3f, 2.18f, 5.0f, 0.4f, 0.4f, 0.05f, 0);//Ventana no se abre0

        crearVentana(-2.35f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre2
        crearVentanaCerrada(-2.05f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre2
        crearVentana(-3.15f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana se abre3
        crearVentanaCerrada(-2.85f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0);//Ventana no se abre3
        crearVentanaCerrada(-3.75f, 2.18f, 5.0f, 0.5f, 0.4f, 0.05f, 0);//Ventana no se abre4
        crearPuerta(-4.3f, 1.98f, 5.0f, 0.6f, 0.8f, 0.05f, 0);//Puerta Acceso terrazita2
        crearParedCompleta(-4.65f, 1.98f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90);//pared VerticalFinal
        crearParedCompleta(-1.8f, 1.98f, 3.5f, 1.4f, 0.4f, 0.05f, 255, 167, 38, 90);//pared Division Salones

//crearPuerta(-1.3f, 1.98f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-1.8f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//1.98Y muro salon adentro 1
        crearParedCompleta(-2.25f, 1.78f, 2.0f, 0.45f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 1
        crearVentanaCerrada(-2.3f, 2.18f, 2.0f, 0.8f, 0.4f, 0.05f, 0);
        crearParedCompleta(-2.8f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearPuerta(-3.2f, 1.98f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(-3.6f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//muro salon adentro 2
        crearParedCompleta(-4.2f, 1.98f, 2.0f, 0.5f, 0.4f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

//Pared Final Externa
        crearParedCompleta(-1.8f, 1.78f, 6.0f, 2.8f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
//
        crearPuerta(-1.4f, 1.98f, 2.0f, 0.6f, 0.8f, 0.05f, 0);//Puerta Salon falta Configurar
        crearParedCompleta(0.35f, 1.78f, 2.0f, 0.55f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2
        crearVentana(0.63f, 2.18f, 2.0f, 0.55f, 0.4f, 0.05f, 0);//Ventana Murito Pegado a escalera1
        crearVentanaCerrada(0.08f, 2.18f, 2.0f, 0.56f, 0.4f, 0.05f, 0);//Ventana Murito Pegado a escalera2
        crearVentanaCerrada(-0.75f, 2.18f, 2.0f, 0.7f, 0.4f, 0.05f, 0);//Ventana Pegada Puerta Entrada
        crearParedCompleta(-0.3f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0);//1.98Y muro salon adentro 1
        crearParedCompleta(-0.71f, 1.78f, 2.0f, 0.4f, 0.2f, 0.05f, 255, 167, 38, 0);//pared salon adentro 2

//------------CUBICULO IZQUIERDA----------
        crearVentanaCerrada(-1.50f, 2.18f, -3.55f, 0.80f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.70f, 2.18f, -3.55f, 0.80f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearParedCubiculo(-3.55f, 1.78f, 1.1f, 1.6f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearPuerta(-3.55f, 1.98f, -0.05f, 0.7f, 0.8f, 0.05f, 90);//Puerta Hazta el final 1

        //------------CUBICULO DERECHA -------------------
        crearVentanaCerrada(-1.50f, 2.18f, 5.4f, 0.80f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.70f, 2.18f, 5.4f, 0.80f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearParedCubiculo(5.4f, 1.78f, 1.1f, 1.6f, 0.4f, 0.05f, 90);//pared Division cubiculo1
        crearPuerta(5.4f, 1.98f, -0.05f, 0.7f, 0.8f, 0.05f, 90);//Puerta Hazta el final 1

        //-------------TECHO----------------------
        crearParedCompleta(1.3f, 2.4f, 2.8f, 6f, 0.02f, 3.3f, 255, 167, 38, 0);//pared salon adentro 2

        crearPisoSalon(-2.8f, 1.58f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso salon Piso3 Izquierda
        crearPisoSalon(-3.1f, 1.58f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Fondo
        crearPisoSalon(-0.1f, 1.58f, 0.8f, 1.5f, 0.002f, 1.2f);//Piso Mas cerca entrada Salon

//------------ZonaEscaleras-------------
        crearParedCompleta(2.3f, 1.98f, 6.0f, 1.25f, 0.4f, 0.1f, 255, 167, 38, 0);//1.98Y para estar sobre pis
        crearParedCompleta(1.0f, 1.98f, 4.0f, 2.0f, 0.4f, 0.08f, 255, 167, 38, 90);//Muro naranja Pasillo Entrada
        crearParedCompleta(2.5f, 1.98f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro Pasillo Entrada
        crearParedCompleta(1.0f, 1.98f, 2.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro2 Pasillo Entrada
        crearParedCompleta(3.08f, 1.98f, 2.0f, 0.48f, 0.4f, 0.05f, 255, 167, 38, 0);//Falta Ajustar

        //agregarCajitaTeletransporte(2.0f, 1.6f, 1.9f);//Falta Posicionar
        agregarCajitaTeletransporte(1.4f, 0.8f, 1.7f);//Falta Posicionar
//-------------Salon derecha-----------
        crearParedCompleta(5.4f, 1.78f, 6.0f, 1.89f, 0.2f, 0.05f, 255, 167, 38, 0); //pared Balcon1
        crearParedCompleta(3.6f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 1
        crearParedCompleta(4.0f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(4.4f, 1.98f, 5.0f, 0.1f, 0.4f, 0.05f, 255, 253, 208, 0);//muro2 Salon Izquierda
        crearParedCompleta(4.8f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 2
        crearParedCompleta(5.2f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 3
        crearParedCompleta(5.6f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 3
        crearParedCompleta(6.0f, 1.98f, 5.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon 4
        crearParedCompleta(6.2f, 1.78f, 5.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared enfrente 4
        crearPuerta(6.8f, 1.98f, 5.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(7.2f, 1.78f, 5.0f, 0.1f, 0.6f, 0.05f, 255, 167, 38, 0); //pared enfrente 4

//// Ventanas 
        crearVentana(4.15f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre1
        crearVentanaCerrada(3.85f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre1
        crearVentana(4.95f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre2
        crearVentanaCerrada(4.65f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre2
        crearVentana(5.75f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana se abre3
        crearVentanaCerrada(5.45f, 2.18f, 5.0f, 0.3f, 0.4f, 0.05f, 0); //Ventana no se abre3
        crearVentanaCerrada(6.3f, 2.18f, 5.0f, 0.4f, 0.4f, 0.05f, 0); //Ventana no se abre4
//
//// Paredes 
//crearParedCompleta(6.8f, 1.98f, 5.0f, 0.4f, 0.4f, 0.05f, 255, 167, 38, 0); //pared final
        crearParedCompleta(7.25f, 1.98f, 4.0f, 2.05f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
//crearParedCompleta(3.6f, 1.98f, 4.0f, 2.1f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)
//
//// Puerta y paredes internas 
        crearPuerta(3.9f, 1.98f, 2.0f, 0.6f, 0.8f, 0.05f, 0);
        crearParedCompleta(4.3f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 1
        crearParedCompleta(4.8f, 1.78f, 2.0f, 0.5f, 0.2f, 0.05f, 255, 167, 38, 0); //pared salon adentro 1
        crearVentanaCerrada(4.63f, 2.18f, 2.0f, 0.45f, 0.4f, 0.05f, 0);//Ventana1 encima Muro1
        crearVentana(5.08f, 2.18f, 2.0f, 0.45f, 0.4f, 0.05f, 0);//Ventana2 encima Muro1 
        crearParedCompleta(5.4f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(6.0f, 1.78f, 2.0f, 0.5f, 0.2f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearVentanaCerrada(5.73f, 2.18f, 2.0f, 0.45f, 0.4f, 0.05f, 0);//Ventana1 encima Muro2
        crearVentana(6.23f, 2.18f, 2.0f, 0.55f, 0.4f, 0.05f, 0);//Ventana2 encima Muro2 
        crearParedCompleta(6.6f, 1.98f, 2.0f, 0.1f, 0.4f, 0.1f, 255, 253, 208, 0); //muro salon adentro 2
        crearParedCompleta(7.0f, 1.78f, 2.0f, 0.3f, 0.2f, 0.05f, 255, 167, 38, 0); //pared salon adentro 2
        crearVentanaCerrada(6.96f, 2.18f, 2.0f, 0.51f, 0.4f, 0.05f, 0);//Ventana1 encima Pared 2

        crearParedCompleta(7.25f, 1.98f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal
        crearParedCompleta(3.6f, 1.98f, 4.0f, 2.0f, 0.4f, 0.05f, 255, 167, 38, 90); //pared VerticalFinal (ajustada)
        crearPisoSalon(5.4f, 1.58f, 4.0f, 1.8f, 0.002f, 2.0f);//Piso Salon Derecha

//------------CUBICULOS DERECHA----------
        crearParedCompleta(7.25f, 1.78f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared Externa 1
        crearVentanaCerrada(-1.55f, 2.18f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 2.18f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 2.18f, 7.25f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
//Vista Laboratorio Alimentarias

        crearParedCompleta(1.3f, 1.7f, -0.45f, 6.0f, 0.15f, 0.05f, 255, 167, 38, 0);//pared Externa Viendo Alimentarias
        crearParedCompleta(6.9f, 2.02f, -0.45f, 0.4f, 0.17f, 0.05f, 255, 167, 38, 0);//Minipared Pegada al exterior Lado Derecho
        crearVentanaCerrada(2.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1
        crearVentanaCerrada(3.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha2
        crearVentanaCerrada(4.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha3
        crearVentanaCerrada(5.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha4
        crearVentanaCerrada(6.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha5
        crearVentanaCerrada(1.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha6
        crearVentanaCerrada(0.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha7
        crearVentanaCerrada(-1.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha8
        crearVentanaCerrada(-2.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha9
        crearVentanaCerrada(-3.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha10
        crearVentanaCerrada(-4.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha11
        crearParedCompleta(-4.61f, 2.02f, -0.45f, 0.085f, 0.2f, 0.05f, 255, 167, 38, 0);//pared Externa Viendo Alimentarias
        crearParedCompleta(1.3f, 2.28f, -0.45f, 6.0f, 0.1f, 0.05f, 255, 167, 38, 0);//pared Externa Viendo Alimentarias
        crearVentanaCerrada(1.0f, 2.02f, -0.45f, 1.0f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1//Ventana Viendo Alimentarias PtCentral
        crearVentanaCerrada(0.05f, 2.02f, -0.45f, 0.9f, 0.325f, 0.05f, 0);//Ventana Viendo Alimentarias PtDerecha1//Ventana Viendo Alimentarias PtCentra2
        crearParedCompleta(0.55f, 2.28f, -0.45f, 0.85f, 0.1f, 0.05f, 255, 167, 38, 0);//pared Externa ArribaCentro Viendo Alimentarias
        crearParedCompleta(-4.61f, 1.78f, 0.8f, 1.2f, 0.2f, 0.05f, 255, 167, 38, 90);//pared Externa 1
        crearVentanaCerrada(-1.55f, 2.18f, -4.61f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(-0.75f, 2.18f, -4.61f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1
        crearVentanaCerrada(0.05f, 2.18f, -4.61f, 0.8f, 0.4f, 0.05f, 90);//Ventana no se abre1

        crearPisoSalon(5.75f, 1.58f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso Hasta el fondo
        crearPisoSalon(2.75f, 1.58f, 0.75f, 1.5f, 0.002f, 1.2f);//Piso mas cerca entrada salon

        //--------------LABORATORIO DE ALIMENTARIAS-----------------
        crearParedCompleta(-4.65f, 0.30f, -2.5f, 2.0f, 0.3f, 0.05f, 255, 167, 38, 90);//pared VerticalIzq
        crearParedCompleta(1.3f, 0.30f, -4.5f, 6.0f, 0.3f, 0.05f, 255, 167, 38, 0);//pared HorizontalFinal
        crearParedCompleta(7.25f, 0.30f, -2.5f, 2.0f, 0.3f, 0.05f, 255, 167, 38, 90);//pared VerticalIzq
        crearTechoTriangularPrisma(0.5f, 0.6f, -4.65f, 4.1f, 0.4f, 12.0f, 120, 120, 120, 90);
        crearPisoSalon2(-2.65f, -0.01f, -2.5f, 2.0f, 0.001f, 2.0f);
        crearPisoSalon2(1.35f, -0.01f, -2.5f, 2.0f, 0.001f, 2.0f);
        crearPisoSalon2(5.25f, -0.01f, -2.5f, 2.0f, 0.001f, 2.0f);

        float[][] posiciones = {
            // ========== SILLAS ORIGINALES ==========
            // Salon 1 Tercer piso
            {-3.5f, 1.6f, 3.5f, 90.0f},
            {-3.5f, 1.6f, 4.0f, 90.0f},
            {-3.5f, 1.6f, 3.0f, 90.0f},
            {-2.8f, 1.6f, 3.8f, 90.0f},
            {-2.8f, 1.6f, 3.5f, 90.0f},
            {-2.8f, 1.6f, 3.0f, 90.0f},
            {-4.2f, 1.6f, 3.0f, 90.0f},
            {-4.2f, 1.6f, 4.0f, 90.0f},
            {-4.2f, 1.6f, 3.5f, 90.0f},
            // Salon 2 Tercer piso
            {-0.7f, 1.6f, 3.5f, 90.0f},
            {-0.7f, 1.6f, 4.0f, 90.0f},
            {-0.7f, 1.6f, 3.0f, 90.0f},
            {0.0f, 1.6f, 3.8f, 90.0f},
            {0.0f, 1.6f, 3.5f, 90.0f},
            {0.0f, 1.6f, 3.0f, 90.0f},
            {-1.4f, 1.6f, 3.0f, 90.0f},
            {-1.4f, 1.6f, 4.0f, 90.0f},
            {-1.4f, 1.6f, 3.5f, 90.0f},
            // Salon 3 Tercer piso
            {5.3f, 1.6f, 3.5f, 90.0f},
            {5.3f, 1.6f, 4.0f, 90.0f},
            {5.3f, 1.6f, 3.0f, 90.0f},
            {6.0f, 1.6f, 3.8f, 90.0f},
            {6.0f, 1.6f, 3.5f, 90.0f},
            {6.0f, 1.6f, 3.0f, 90.0f},
            {4.6f, 1.6f, 3.0f, 90.0f},
            {4.6f, 1.6f, 4.0f, 90.0f},
            {4.6f, 1.6f, 3.5f, 90.0f},
            // ========== SILLAS ADICIONALES PISO 1 ==========
            {4.8f, 0.0f, 4.8f, 90.0f},
            {4.8f, 0.0f, 4.2f, 90.0f},
            {4.8f, 0.0f, 3.6f, 90.0f},
            {5.4f, 0.0f, 4.8f, 90.0f},
            {5.4f, 0.0f, 4.2f, 90.0f},
            {5.4f, 0.0f, 3.6f, 90.0f},
            // ========== SILLAS ADICIONALES PISO 2 ==========
            {4.8f, 0.8f, 4.8f, 90.0f},
            {4.8f, 0.8f, 4.2f, 90.0f},
            {4.8f, 0.8f, 3.6f, 90.0f},
            {5.4f, 0.8f, 4.8f, 90.0f},
            {5.4f, 0.8f, 4.2f, 90.0f},
            {5.4f, 0.8f, 3.6f, 90.0f}

        };

        for (float[] pos : posiciones) {
            Transform3D transform = new Transform3D();
            transform.setTranslation(new Vector3f(pos[0], pos[1], pos[2]));
            TransformGroup tg = new TransformGroup(transform);
            tg.addChild(new Silla(pos[3]));  // Pasa la rotación al constructor
            tgMundo.addChild(tg);
        }
        // Crear múltiples mesas en distintas posiciones y rotaciones
        float[][] posicionesMesas = {
            // Ya tenías:
            {-2.12f, 1.6f, 4.0f, 90.0f}, // Mesa piso 3 - salón 1
            {0.6f, 1.6f, 4.0f, 90.0f}, // Mesa piso 3 - salón 2
            {6.5f, 1.6f, 4.0f, 90.0f}, // Mesa piso 3 - salón 3

            // Nuevas mesas para salones derecha
            {6.5f, 0.0f, 2.8f, 90.0f}, // Piso 1 - salón derecho
            {6.5f, 0.8f, 2.8f, 90.0f} // Piso 2 - salón derecho
        };

        for (float[] pos : posicionesMesas) {
            Transform3D transform = new Transform3D();
            transform.setTranslation(new Vector3f(pos[0], pos[1], pos[2]));
            TransformGroup tg = new TransformGroup(transform);
            tg.addChild(new Mesa(pos[3]));  // Pasa la rotación al constructor
            tgMundo.addChild(tg);
        }
        //Computadoras Piso 1
        crearMuebleComputadora(-4.1f, 0.0f, -2.1f, 2.0f, 0.2f, 0.15f, 90);//Inicio
        crearPC(-2.1f, 0.18f, 3.45f, 0.3f, 0.15f, 0.03f, 90);//PC1 Entrada>Ventanas
        crearPC(-2.1f, 0.18f, 4.1f, 0.3f, 0.15f, 0.03f, 90);//PC2 Entrada>Ventanas
        crearPC(-2.1f, 0.18f, 4.75f, 0.3f, 0.15f, 0.03f, 90);//PC3 Entrada>Ventanas
        crearMuebleComputadora(-4.1f, 0.0f, -3.95f, 2.0f, 0.2f, 0.15f, 90);//Fondo
        crearPC(-3.95f, 0.18f, 3.45f, 0.3f, 0.15f, 0.03f, 90);//PC1 Entrada>Ventanas
        crearPC(-3.95f, 0.18f, 4.1f, 0.3f, 0.15f, 0.03f, 90);//PC2 Entrada>Ventanas
        crearPC(-3.95f, 0.18f, 4.75f, 0.3f, 0.15f, 0.03f, 90);//PC3 Entrada>Ventanas
        //Computadoras Piso 2
        crearMuebleComputadora(-4.1f, 0.78f, -2.1f, 2.0f, 0.2f, 0.15f, 90);//Inicio
        crearPC(-2.1f, 0.98f, 3.45f, 0.3f, 0.15f, 0.03f, 90);//PC1 Entrada>Ventanas
        crearPC(-2.1f, 0.98f, 4.1f, 0.3f, 0.15f, 0.03f, 90);//PC2 Entrada>Ventanas
        crearPC(-2.1f, 0.98f, 4.75f, 0.3f, 0.15f, 0.03f, 90);//PC3 Entrada>Ventanas
        crearMuebleComputadora(-4.1f, 0.78f, -3.95f, 2.0f, 0.2f, 0.15f, 90);//Fondo
        crearPC(-3.95f, 0.98f, 3.45f, 0.3f, 0.15f, 0.03f, 90);//PC1 Entrada>Ventanas
        crearPC(-3.95f, 0.98f, 4.1f, 0.3f, 0.15f, 0.03f, 90);//PC2 Entrada>Ventanas
        crearPC(-3.95f, 0.98f, 4.75f, 0.3f, 0.15f, 0.03f, 90);//PC3 Entrada>Ventanas
        
        // Ejemplo: crear una caja en (1, 0.5, 2) con tamaño 0.5x0.5x0.5
crearCaja(-3.5f, 0.0f, -4.2f, 1.0f, 0.5f, 0.5f);
crearCaja(-4.2f, 0.19f, -1.5f, 0.5f, 0.5f, 1.5f);
    }
    
    public void crearCaja(float x, float y, float z, float ancho, float alto, float profundidad) {
    // Crear la apariencia con la textura de la caja
    Appearance aparienciaCaja = textura.crearTexturas("cajas.jpeg");
    
    // Crear la caja con generación de normales y coordenadas de textura
    Box caja = new Box(ancho/2, alto/2, profundidad/2, 
                      Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS, 
                      aparienciaCaja);
    
    // Configurar la transformación (posición)
    Transform3D t3d = new Transform3D();
    t3d.setTranslation(new Vector3f(x, y, z));
    
    // Crear el grupo de transformación y añadir la caja
    TransformGroup tg = new TransformGroup(t3d);
    tg.addChild(caja);
    
    // Añadir a la escena principal
    tgMundo.addChild(tg);
    
    // Opcional: Registrar para detección de colisiones si es necesario
    listaTransform.add(tg);
    listaBoxs.add(caja);
}

    public void crearMuebleComputadora(float x, float y, float z, float ancho, float alto, float profundo, float rotacionY) {
        // Obtener apariencia con textura de madera
        Appearance aparienciaMadera = textura.crearTexturas("madera.jpg");

        // Crear el mueble como un Box (se centra en su posición)
        Box mueble = new Box(ancho / 2, alto / 2, profundo / 2,
                Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS, aparienciaMadera);

        // Crear rotación en Y
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(rotacionY));

        // Posicionamiento (ajustamos en Y para que toque el suelo con base)
        Transform3D traslacion = new Transform3D();
        traslacion.setTranslation(new Vector3f(x, y + alto / 2, z));

        // Combinar rotación y traslación
        rotacion.mul(traslacion);

        // Grupo de transformación final
        TransformGroup tg = new TransformGroup(rotacion);
        tg.addChild(mueble);
        tgMundo.addChild(tg);
        listaBoxs.add(mueble);
        listaTransform.add(tg);
    }

    public void agregarCajitaTeletransporte(float x, float y, float z) {
        // 1. Crear la apariencia de la cajita
        Appearance apariencia = new Appearance();
        Material material = new Material();
        material.setDiffuseColor(new Color3f(1.0f, 0.0f, 0.0f));
        material.setEmissiveColor(new Color3f(0.3f, 0.0f, 0.0f));
        apariencia.setMaterial(material);

        // 2. Crear la caja de teletransporte
        Box cajita = new Box(0.2f, 0.01f, 0.05f, apariencia);
        // 3. Posicionar la cajita
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(x, y, z));
        TransformGroup tg = new TransformGroup(t3d);
        tg.addChild(cajita);

        // 4. Comportamiento de teletransporte
        Behavior comportamiento = new Behavior() {
            private WakeupOnElapsedTime timer = new WakeupOnElapsedTime(100);
            private boolean teletransportado = false;

            public void initialize() {
                wakeupOn(timer);
            }

            public void processStimulus(Enumeration criteria) {
                // Usar el sistema de colisiones mejorado
                if (Colisiones.hayColision(steve.obtenerPanza(), steve.cajaColisionPersonaje(), tg, cajita)) {
                    if (!teletransportado) {
                        Transform3D t3dMundo = new Transform3D();
                        tgMundo.getTransform(t3dMundo);
                        Vector3d posicion = new Vector3d();
                        t3dMundo.get(posicion);

                        posicion.y -= 0.8;
                        posicion.z += 0.0;
                        t3dMundo.setTranslation(posicion);
                        tgMundo.setTransform(t3dMundo);

                        posPersonaje.set(posicion);
                        teletransportado = true;
                        System.out.println("¡Teletransportado!");
                    }
                } else {
                    teletransportado = false;
                }
                wakeupOn(timer);
            }
        };

        comportamiento.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        tg.addChild(comportamiento);
        tgMundo.addChild(tg);

        // Registrar para detección de colisiones
        listaTransform.add(tg);
        listaBoxs.add(cajita);
    }

    public void agregarVentanaInteractiva(float x, float y, float z, float ancho, float alto, float profundo, float rotYGrados, boolean esInteractiva) {
        Ventana ventana = new Ventana(x, y, z, ancho, alto, profundo, rotYGrados);
        tgMundo.addChild(ventana);
        listaVentanas.add(ventana); // Solo las interactuables las vamos a escanear

        // Si es interactiva, agregar detector de proximidad
        if (esInteractiva) {
            crearDetectorProximidadVentamaVentama(ventana, 0.6f); // Detecta cuando el personaje está cerca
        }
    }

    public void agregarInstanciaConColision(SharedGroup objeto, float x, float y, float z, float ancho, float alto, float profundo) {
        // 1. Transformación de posición
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(x, y, z));

        // 2. Crear el grupo de transformación
        TransformGroup tg = new TransformGroup(t3d);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // Para colisiones

        // 3. Agregar objeto visual
        tg.addChild(new Link(objeto));
        tgMundo.addChild(tg);

        // 4. Crear caja de colisión (invisible)
        Appearance invisible = new Appearance();
        TransparencyAttributes t = new TransparencyAttributes(TransparencyAttributes.NICEST, 1.0f);
        invisible.setTransparencyAttributes(t);

        Box boxColision = new Box(ancho, alto, profundo, invisible); // tamaño aproximado del objeto
        tg.addChild(boxColision);

        // 5. Registrar para detección de colisiones
        listaTransform.add(tg);
        listaBoxs.add(boxColision);
    }

    public void agregarInstancia(SharedGroup objeto, float x, float y, float z) {
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(x, y, z));

        TransformGroup tg = new TransformGroup(t3d);
        tg.addChild(new Link(objeto));
        tgMundo.addChild(tg);
    }

    /**
     * Configura los controles de teclado incluyendo movimiento vertical libre
     *
     * @param canvas Componente donde se registrarán los eventos de teclado
     */
    public void registrarControlesPorTeclado(java.awt.Component canvas) {
        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int keyCode = e.getKeyCode();
                float velocidadVertical = 0.1f; // Ajusta esta velocidad según necesites

                switch (keyCode) {
                    // Movimiento horizontal y rotación
                    case java.awt.event.KeyEvent.VK_W:
                        MoverAdelante(tgMundo, steve.obtenerPanza(), 0.8);
                        steve.caminar();
                        break;
                    case java.awt.event.KeyEvent.VK_S:
                        MoverAtras(tgMundo, steve.obtenerPanza(), 0.8);
                        steve.caminar();
                        break;
                    case java.awt.event.KeyEvent.VK_A:
                        rotarTG(tgMundo, -5, "Y", posPersonaje);
                        steve.caminar();
                        break;
                    case java.awt.event.KeyEvent.VK_D:
                        rotarTG(tgMundo, 5, "Y", posPersonaje);
                        steve.caminar();
                        break;

                    // Movimiento vertical libre
                    case java.awt.event.KeyEvent.VK_UP:
                        moverEnY(-velocidadVertical); // Subir
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        moverEnY(velocidadVertical); // Bajar
                        break;

                    // Tecla para alternar vuelo/modo escaleras (opcional)
                }
            }

        });
    }

    /**
     * Mueve al personaje en el eje Y sin restricciones de colisión
     *
     * @param deltaY Cantidad de movimiento (positivo para bajar, negativo para
     * subir)
     */
    private void moverEnY(double deltaY) {
        Transform3D t3d = new Transform3D();
        tgMundo.getTransform(t3d);

        Vector3d posicionActual = new Vector3d();
        t3d.get(posicionActual);

        // Aplicar movimiento directamente sin verificar colisiones
        posicionActual.y += deltaY;
        t3d.setTranslation(posicionActual);
        tgMundo.setTransform(t3d);

        // Actualizar posición del personaje para otros cálculos
        posPersonaje.y = posicionActual.y;
    }

    private boolean verificarColisionConTransformacion(Transform3D nuevaTransform) {
        // Guardar transformación actual del mundo
        Transform3D transformacionOriginal = new Transform3D();
        tgMundo.getTransform(transformacionOriginal);

        // Aplicar transformación temporal
        tgMundo.setTransform(nuevaTransform);

        // Verificar colisión con todos los objetos
        boolean colisionDetectada = false;
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones.hayColision(
                    steve.obtenerPanza(),
                    steve.cajaColisionPersonaje(),
                    listaTransform.get(i),
                    listaBoxs.get(i)
            )) {
                colisionDetectada = true;
                break;
            }
        }
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones2.hayColision(
                    steve.obtenerPanza(),
                    steve.cajaColisionPersonaje(),
                    listaTransform.get(i),
                    listaBoxs.get(i)
            )) {
                colisionDetectada = true;
                break;
            }
        }

        // Restaurar transformación original
        tgMundo.setTransform(transformacionOriginal);

        return colisionDetectada;
    }

    public void crearParedCubiculo(float x, float y, float z,
            float ancho, float alto, float profundidad,
            float anguloRotacionGrados) {
        ParedCubiculo paredCubiculo = new ParedCubiculo(x, y, z, ancho, alto, profundidad, "madera.jpg", anguloRotacionGrados);
        tgMundo.addChild(paredCubiculo);
        listaTransform.add(paredCubiculo.tg);
        listaBoxs.add(paredCubiculo.boxColision);

    }

    public void crearPisoSalon(float x, float y, float z,
            float ancho, float alto, float profundidad) {
        Box bxPisoSalon1 = new Box(ancho, alto, profundidad, paraTextura, textura.crearTexturas("mosaico.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dPisoSalon1 = new Transform3D();
        t3dPisoSalon1.set(new Vector3d(x, y, z));
        TransformGroup tgPisoSalon1 = new TransformGroup(t3dPisoSalon1);
        tgPisoSalon1.addChild(bxPisoSalon1);
        tgMundo.addChild(tgPisoSalon1);
    }

    public void crearPisoSalon2(float x, float y, float z,
            float ancho, float alto, float profundidad) {
        Box bxPisoSalon1 = new Box(ancho, alto, profundidad, paraTextura, textura.crearTexturas("piso2.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dPisoSalon1 = new Transform3D();
        t3dPisoSalon1.set(new Vector3d(x, y, z));
        TransformGroup tgPisoSalon1 = new TransformGroup(t3dPisoSalon1);
        tgPisoSalon1.addChild(bxPisoSalon1);
        tgMundo.addChild(tgPisoSalon1);
    }

    public void crearParedCompleta(float x, float y, float z,
            float ancho, float alto, float profundidad,
            int R, int G, int B,
            float anguloRotacionGrados) {
        // Crear la caja (pared)
        Box bxPared = new Box(ancho, alto, profundidad, paraTextura, c.setColor(R, G, B));

        // Crear transformación compuesta (traslación + rotación)
        Transform3D t3dPared = new Transform3D();

        // 1. Primero aplicar rotación (en el origen)
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(anguloRotacionGrados)); // Rotación alrededor del eje Y

        // 2. Luego aplicar traslación
        Transform3D traslacion = new Transform3D();
        traslacion.set(new Vector3d(x, y, z));

        // Combinar ambas transformaciones (primero rotación, luego traslación)
        t3dPared.mul(traslacion, rotacion);

        // Crear grupo de transformación y añadir a la escena
        TransformGroup tgPared = new TransformGroup(t3dPared);
        listaTransform.add(tgPared);
        listaBoxs.add(bxPared);
        tgMundo.addChild(tgPared);
        tgPared.addChild(bxPared);
    }

    public void crearTechoTriangularPrisma(float x, float y, float z,
            float ancho, float alto, float profundidad,
            int R, int G, int B,
            float anguloRotacionGrados) {

        // === VÉRTICES DEL PRISMA TRIANGULAR ===
        Point3f[] vertices = new Point3f[]{
            new Point3f(0, 0, 0), // V0: base inferior izquierda
            new Point3f(ancho, 0, 0), // V1: base inferior derecha
            new Point3f(0, alto, 0), // V2: techo izquierdo
            new Point3f(0, 0, profundidad), // V3: atrás inferior izquierda
            new Point3f(ancho, 0, profundidad), // V4: atrás inferior derecha
            new Point3f(0, alto, profundidad) // V5: atrás techo izquierdo
        };

        // === CARAS LATERALES RECTANGULARES ===
        QuadArray carasRectas = new QuadArray(12,
                GeometryArray.COORDINATES | GeometryArray.NORMALS);

        // Lado derecho (rectángulo entre V1, V4)
        carasRectas.setCoordinate(0, vertices[1]);
        carasRectas.setCoordinate(1, vertices[4]);
        carasRectas.setCoordinate(2, vertices[3]);
        carasRectas.setCoordinate(3, vertices[0]);

        // Lado techo inclinado (V2 a V5)
        carasRectas.setCoordinate(4, vertices[2]);
        carasRectas.setCoordinate(5, vertices[5]);
        carasRectas.setCoordinate(6, vertices[4]);
        carasRectas.setCoordinate(7, vertices[1]);

        // Fondo (V3-V4-V5-V2)
        carasRectas.setCoordinate(8, vertices[3]);
        carasRectas.setCoordinate(9, vertices[4]);
        carasRectas.setCoordinate(10, vertices[5]);
        carasRectas.setCoordinate(11, vertices[2]);

        for (int i = 0; i < 12; i++) {
            carasRectas.setNormal(i, new Vector3f(0, 0, 1)); // simplificado
        }

        // === TRIÁNGULOS: Frontal y Trasero (techo) ===
        TriangleArray triangulos = new TriangleArray(6,
                GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2);

        // Cara frontal triangular
        triangulos.setCoordinate(0, vertices[0]);
        triangulos.setCoordinate(1, vertices[1]);
        triangulos.setCoordinate(2, vertices[2]);

        // Textura para el frente
        triangulos.setTextureCoordinate(0, 0, new TexCoord2f(0, 0));
        triangulos.setTextureCoordinate(0, 1, new TexCoord2f(1, 0));
        triangulos.setTextureCoordinate(0, 2, new TexCoord2f(0, 1));

        // Cara trasera triangular
        triangulos.setCoordinate(3, vertices[3]);
        triangulos.setCoordinate(4, vertices[4]);
        triangulos.setCoordinate(5, vertices[5]);

        triangulos.setTextureCoordinate(0, 3, new TexCoord2f(0, 0));
        triangulos.setTextureCoordinate(0, 4, new TexCoord2f(1, 0));
        triangulos.setTextureCoordinate(0, 5, new TexCoord2f(0, 1));

        // Normales hacia fuera
        for (int i = 0; i < 6; i++) {
            triangulos.setNormal(i, new Vector3f(0, 1, 0));
        }

        // === APARIENCIA ===
        Appearance aparTecho = textura.crearTexturas("techo.jpg");
        PolygonAttributes paTex = new PolygonAttributes();
        paTex.setCullFace(PolygonAttributes.CULL_NONE); // que no desaparezca
        aparTecho.setPolygonAttributes(paTex);

        Appearance aparLaterales = new Appearance();
        Color3f naranja = new Color3f(255f / 255f, 167f / 255f, 38f / 255f);
        ColoringAttributes ca = new ColoringAttributes(naranja, ColoringAttributes.SHADE_FLAT);
        aparLaterales.setColoringAttributes(ca);
        PolygonAttributes paColor = new PolygonAttributes();
        paColor.setCullFace(PolygonAttributes.CULL_NONE);
        aparLaterales.setPolygonAttributes(paColor);

        // === SHAPES ===
        Shape3D shapeTriangulos = new Shape3D(triangulos, aparLaterales);
        Shape3D shapeLados = new Shape3D(carasRectas, aparTecho);

        // === TRANSFORMACIÓN FINAL ===
        Transform3D rotY = new Transform3D();
        rotY.rotY(Math.toRadians(anguloRotacionGrados));
        Transform3D transl = new Transform3D();
        transl.setTranslation(new Vector3f(x, y, z));
        rotY.mul(transl);

        TransformGroup tg = new TransformGroup(rotY);
        tg.addChild(shapeTriangulos);
        tg.addChild(shapeLados);

        tgMundo.addChild(tg);
        listaTransform.add(tg);
    }

    public void crearVentana(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        Ventana ventana = new Ventana(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(ventana);
        listaVentanas.add(ventana);
    }

    public void crearVentanaCerrada(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        VentanaCerrada ventana1 = new VentanaCerrada(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(ventana1);
    }

    public void crearPuerta(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        Puerta Puerta1 = new Puerta(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(Puerta1);
        listaPuerta.add(Puerta1);
    }

    public void crearPuertaDoble(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        PuertasDobles Puerta1 = new PuertasDobles(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(Puerta1);
        listaPuertaD.add(Puerta1);
    }

    public void crearPC(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        EscritorioConOrdenador pc = new EscritorioConOrdenador(x, y, z, rotYGrados, ancho, profundidad, alto);
        tgMundo.addChild(pc);
    }

    private void crearDetectorProximidadVentamaVentama(final Ventana ventana, final float umbral) {
        Behavior detector = new Behavior() {
            WakeupOnElapsedTime tiempo = new WakeupOnElapsedTime(200);

            public void initialize() {
                wakeupOn(tiempo);
            }

            public void processStimulus(java.util.Enumeration criteria) {
                Vector3f posVentana = ventana.getPosicion();

                // Convertimos posición del personaje (Point3d) a Vector3f
                Vector3f posPersonajeVec = new Vector3f((float) posPersonaje.x, (float) posPersonaje.y, (float) posPersonaje.z);

                // Calculamos distancia
                float dx = posVentana.x - posPersonajeVec.x;
                float dy = posVentana.y - posPersonajeVec.y;
                float dz = posVentana.z - posPersonajeVec.z;
                float distancia = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distancia < umbral) {
                    ventana.toggle(); // Abre o cierra
                }

                wakeupOn(tiempo);
            }
        };

        detector.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        objRaiz.addChild(detector);
    }

    public void EscalarTG(TransformGroup tg, float x) {
        Transform3D leer = new Transform3D();
        Transform3D mover = new Transform3D();
        tg.getTransform(leer);
        mover.setScale(x);
        leer.mul(mover);
        tg.setTransform(leer);
    }

    private void configurarIluminacion(BranchGroup escena) {
        // 1. Luz ambiental general (para iluminación base)
        AmbientLight luzAmbiental = new AmbientLight();
        luzAmbiental.setColor(new Color3f(0.3f, 0.3f, 0.3f));
        luzAmbiental.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzAmbiental);

        // 2. Luz direccional desde arriba (simula el sol)
        DirectionalLight luzDesdeArriba = new DirectionalLight();
        luzDesdeArriba.setColor(new Color3f(0.7f, 0.7f, 0.7f));
        luzDesdeArriba.setDirection(new Vector3f(0.0f, -1.0f, -0.3f)); // Apuntando hacia abajo
        luzDesdeArriba.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzDesdeArriba);

        // 3. Luz direccional de relleno (para reducir sombras muy oscuras)
        DirectionalLight luzRelleno = new DirectionalLight();
        luzRelleno.setColor(new Color3f(0.4f, 0.4f, 0.4f));
        luzRelleno.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f));
        luzRelleno.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzRelleno);
    }

    public void moverBehaivor() {
        KeyNavigatorBehavior keyNav = new KeyNavigatorBehavior(tgMundo);
        keyNav.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));

        //-----Behavior para animacion del personaje(que se mueva Pz-----
        Behavior animacionBehavior = new Behavior() {
            private WakeupOnElapsedTime wakeup;
            private Transform3D lastTransform = new Transform3D();

            public void initialize() {
                wakeup = new WakeupOnElapsedTime(50); // Verificar cada 50ms
                wakeupOn(wakeup);
                tgMundo.getTransform(lastTransform);
            }

            public void processStimulus(Enumeration criteria) {
                Transform3D currentTransform = new Transform3D();
                tgMundo.getTransform(currentTransform);

                // Comprobar si hubo movimiento
                if (!currentTransform.epsilonEquals(lastTransform, 0.01f)) {
                    steve.caminar(); // Activar animación
                }

                lastTransform.set(currentTransform);
                wakeupOn(wakeup);
            }
        };
        animacionBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        objRaiz.addChild(animacionBehavior);
        tgMundo.addChild(keyNav);
    }

    private void rotarTG(TransformGroup tg, int grados, String eje, Point3d personajePosition) {
        Transform3D transformacionMundo = new Transform3D();
        tg.getTransform(transformacionMundo);

        // Crear una copia para verificación
        Transform3D nuevaTransform = new Transform3D(transformacionMundo);

        // Calcular la rotación
        Transform3D traslacionAlOrigen = new Transform3D();
        traslacionAlOrigen.setTranslation(new Vector3d(
                -personajePosition.x,
                -personajePosition.y,
                -personajePosition.z
        ));

        Transform3D rotacion = new Transform3D();
        switch (eje) {
            case "X":
                rotacion.rotX(Math.PI / 180 * grados);
                break;
            case "Y":
                rotacion.rotY(Math.PI / 180 * grados);
                break;
            case "Z":
                rotacion.rotZ(Math.PI / 180 * grados);
                break;
            default:
                break;
        }

        Transform3D traslacionDeVuelta = new Transform3D();
        traslacionDeVuelta.setTranslation(new Vector3d(
                personajePosition.x,
                personajePosition.y,
                personajePosition.z
        ));

        nuevaTransform.mul(traslacionDeVuelta, nuevaTransform);
        nuevaTransform.mul(rotacion, nuevaTransform);
        nuevaTransform.mul(traslacionAlOrigen, nuevaTransform);

        // Verificar colisión antes de rotar
        if (!verificarColisionConTransformacion(nuevaTransform)) {
            tg.setTransform(nuevaTransform);
        }
    }

    private boolean verificarColisiones(Vector3d movimiento) {
        // Crear transformación temporal
        Transform3D tempTransform = new Transform3D();
        tgMundo.getTransform(tempTransform);

        // Aplicar movimiento temporal
        Vector3d posActual = new Vector3d();
        tempTransform.get(posActual);
        posActual.add(movimiento);
        tempTransform.setTranslation(posActual);

        // Guardar transformación original
        Transform3D originalTransform = new Transform3D();
        tgMundo.getTransform(originalTransform);

        // Aplicar temporalmente
        tgMundo.setTransform(tempTransform);

        // Verificar colisiones
        boolean colisionDetectada = false;
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones.hayColision(steve.obtenerPanza(), steve.cajaColisionPersonaje(),
                    listaTransform.get(i), listaBoxs.get(i))) {
                colisionDetectada = true;
                break;
            }
        }
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones2.hayColision(steve.obtenerPanza(), steve.cajaColisionPersonaje(),
                    listaTransform.get(i), listaBoxs.get(i))) {
                colisionDetectada = true;
                break;
            }
        }

        // Restaurar transformación
        tgMundo.setTransform(originalTransform);

        return colisionDetectada;
    }

    private void MoverAdelante(TransformGroup tgMundo, TransformGroup tgPersonaje, double velocidad) {
        Transform3D t3dPersonaje = new Transform3D();
        tgPersonaje.getTransform(t3dPersonaje);

        Matrix3d rotacion = new Matrix3d();
        t3dPersonaje.getRotationScale(rotacion);

        Vector3d direccion = new Vector3d(0, 0, -velocidad);
        rotacion.transform(direccion);

        if (!verificarColisiones(direccion)) {
            Transform3D t3dMundo = new Transform3D();
            tgMundo.getTransform(t3dMundo);
            Vector3d posicion = new Vector3d();
            t3dMundo.get(posicion);
            posicion.add(direccion);
            t3dMundo.setTranslation(posicion);
            tgMundo.setTransform(t3dMundo);
        }
    }

//    private void conectarPuerto() {
//        puerto = SerialPort.getCommPort("COM5");
//        puerto.setBaudRate(9600);
//        puerto.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
//
//        if (puerto.openPort()) {
//            System.out.println("Puerto abierto");
//
//            Thread hilo;
//            hilo = new Thread(() -> {
//                try {
//                    InputStream entrada = puerto.getInputStream();
//                    StringBuilder mensaje = new StringBuilder();
//
//                    while (true) {
//                        int dato = entrada.read();
//                        if (dato == -1) {
//                            continue;
//                        }
//
//                        char caracter = (char) dato;
//                        if (caracter == '\n') {
//                            String linea = mensaje.toString().trim();
//                            mensaje.setLength(0); // Limpia el buffer
//                            System.out.println("Recibido: " + linea);
//                            if (linea.equals("Presionado  Boton")) {
//                                verificarVentanasCercanasYTogglear();
//                                verificarPuertasCercanasYTogglear();
//                                verificarPuertasDCercanasYTogglear();
//                            }
//                            if (linea.equals("Suelto  Arriba") || linea.equals("Presionado  Arriba")) {
//                                MoverAdelante(tgMundo, steve.obtenerPanza(), 0.8);
//                                steve.caminar();
//                            } else if (linea.equals("Suelto  Abajo") || linea.equals("Presionado  Abajo")) {
//                                MoverAtras(tgMundo, steve.obtenerPanza(), 0.8);
//                                steve.caminar();
//                            } else if (linea.equals("Suelto  Derecha") || linea.equals("Presionado  Derecha")) {
//                                rotarTG(tgMundo, 5, "Y", posPersonaje);
//                                steve.caminar();
//                            } else if (linea.equals("Suelto  Izquierda") || linea.equals("Presionado  Izquierda")) {
//                                rotarTG(tgMundo, -5, "Y", posPersonaje);
//                                steve.caminar();
//                            }
//                        } else {
//                            mensaje.append(caracter);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            hilo.start();
//
//        } else {
//            System.out.println("No se pudo abrir el puerto");
//        }
//    }

    private boolean verificarColisionConMovimiento(Vector3d direccion) {
        // 1. Guardar transformación actual
        Transform3D transformOriginal = new Transform3D();
        tgMundo.getTransform(transformOriginal);

        // 2. Aplicar movimiento temporal
        Transform3D transformTemporal = new Transform3D(transformOriginal);
        Vector3d posicionTemporal = new Vector3d();
        transformOriginal.get(posicionTemporal);
        posicionTemporal.add(direccion);
        transformTemporal.setTranslation(posicionTemporal);
        tgMundo.setTransform(transformTemporal);

        // 3. Verificar colisiones
        boolean colisionDetectada = false;
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones.hayColision(
                    steve.obtenerPanza(),
                    steve.cajaColisionPersonaje(),
                    listaTransform.get(i),
                    listaBoxs.get(i)
            )) {
                colisionDetectada = true;
                break;
            }
        }
        for (int i = 0; i < listaTransform.size(); i++) {
            if (Colisiones2.hayColision(
                    steve.obtenerPanza(),
                    steve.cajaColisionPersonaje(),
                    listaTransform.get(i),
                    listaBoxs.get(i)
            )) {
                colisionDetectada = true;
                break;
            }
        }

        // 4. Restaurar transformación original
        tgMundo.setTransform(transformOriginal);

        return colisionDetectada;
    }

    private void MoverAtras(TransformGroup tgMundo, TransformGroup tgPersonaje, double velocidad) {
        // 1. Obtener transformación y rotación actual del personaje
        Transform3D t3dPersonaje = new Transform3D();
        tgPersonaje.getTransform(t3dPersonaje);

        Matrix3d rotacion = new Matrix3d();
        t3dPersonaje.getRotationScale(rotacion);

        // 2. Calcular dirección de movimiento (inversa a MoverAdelante)
        Vector3d direccion = new Vector3d(0, 0, velocidad); // Positivo en Z para mover atrás
        rotacion.transform(direccion);

        // 3. Verificar colisiones antes de mover
        if (!verificarColisionConMovimiento(direccion)) {
            // 4. Aplicar movimiento si no hay colisión
            Transform3D t3dMundo = new Transform3D();
            tgMundo.getTransform(t3dMundo);

            Vector3d posicionActual = new Vector3d();
            t3dMundo.get(posicionActual);
            posicionActual.add(direccion);

            t3dMundo.setTranslation(posicionActual);
            tgMundo.setTransform(t3dMundo);
        } else {
            // Opcional: Feedback de colisión (sonido/vibración)
            System.out.println("Colisión detectada al mover atrás");
        }
    }

    private void verificarVentanasCercanasYTogglear() {
        for (Ventana ventana : listaVentanas) {
            ventana.toggle(); // Abre/cierra todas las ventanas sin verificar distancia
        }
    }

    private void verificarPuertasCercanasYTogglear() {
        for (Puerta puerta : listaPuerta) {
            puerta.toggle(); // Abre/cierra todas las ventanas sin verificar distancia
        }
    }

    private void verificarPuertasDCercanasYTogglear() {
        for (PuertasDobles puertaD : listaPuertaD) {
            puertaD.toggle(); // Abre/cierra todas las ventanas sin verificar distancia
        }
    }

    //----------------Arboles----------------
    public Shape3D crearPlanoArbolDobleCara(float x, float y, float z, float ancho, float altura, String tex) {
        Appearance apariencia = Textura.getInstance().crearTexturas(tex);

        // Configurar transparencia
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        ta.setTransparency(0.1f); // Ajustar según necesidad
        apariencia.setTransparencyAttributes(ta);

        // Configurar material para ambas caras
        Material material = new Material();
        material.setLightingEnable(true);
        material.setDiffuseColor(new Color3f(1f, 1f, 1f));
        material.setSpecularColor(new Color3f(0.2f, 0.2f, 0.2f));
        apariencia.setMaterial(material);

        // Crear geometría con 8 vértices (4 frontales + 4 traseros)
        QuadArray geometria = new QuadArray(8,
                QuadArray.COORDINATES
                | QuadArray.TEXTURE_COORDINATE_2
                | QuadArray.NORMALS);

        float mitadAncho = ancho / 2;

        // Cara frontal
        geometria.setCoordinate(0, new Point3f(x - mitadAncho, y, z));
        geometria.setCoordinate(1, new Point3f(x + mitadAncho, y, z));
        geometria.setCoordinate(2, new Point3f(x + mitadAncho, y + altura, z));
        geometria.setCoordinate(3, new Point3f(x - mitadAncho, y + altura, z));

        // Cara trasera (orden inverso)
        geometria.setCoordinate(4, new Point3f(x - mitadAncho, y + altura, z));
        geometria.setCoordinate(5, new Point3f(x + mitadAncho, y + altura, z));
        geometria.setCoordinate(6, new Point3f(x + mitadAncho, y, z));
        geometria.setCoordinate(7, new Point3f(x - mitadAncho, y, z));

        // Coordenadas de textura para ambas caras
        TexCoord2f[] texCoords = {
            new TexCoord2f(0.0f, 0.0f), new TexCoord2f(1.0f, 0.0f),
            new TexCoord2f(1.0f, 1.0f), new TexCoord2f(0.0f, 1.0f),
            new TexCoord2f(0.0f, 1.0f), new TexCoord2f(1.0f, 1.0f),
            new TexCoord2f(1.0f, 0.0f), new TexCoord2f(0.0f, 0.0f)
        };

        for (int i = 0; i < 8; i++) {
            geometria.setTextureCoordinate(0, i, texCoords[i]);
        }

        // Normales para ambas caras
        Vector3f normalFrontal = new Vector3f(0f, 0f, 1f);
        Vector3f normalTrasera = new Vector3f(0f, 0f, -1f);
        for (int i = 0; i < 4; i++) {
            geometria.setNormal(i, normalFrontal);
            geometria.setNormal(i + 4, normalTrasera);
        }

        return new Shape3D(geometria, apariencia);
    }

    public TransformGroup crearArbolCompleto(float x, float y, float z, float size, float height, String tex) {
        TransformGroup tgArbol = new TransformGroup();

        // Primer plano (frontal)
        Shape3D plano1 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        tgArbol.addChild(plano1);

        // Segundo plano (rotado 45° para mejor cobertura)
        Shape3D plano2 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        TransformGroup tgPlano2 = new TransformGroup();
        Transform3D rot45 = new Transform3D();
        rot45.rotY(Math.toRadians(45));
        tgPlano2.setTransform(rot45);
        tgPlano2.addChild(plano2);
        tgArbol.addChild(tgPlano2);

        // Tercer plano (rotado -45° para mejor cobertura)
        Shape3D plano3 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        TransformGroup tgPlano3 = new TransformGroup();
        Transform3D rotNeg45 = new Transform3D();
        rotNeg45.rotY(Math.toRadians(-45));
        tgPlano3.setTransform(rotNeg45);
        tgPlano3.addChild(plano3);
        tgArbol.addChild(tgPlano3);

        // Posicionamiento final
        TransformGroup tgPosicionado = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3f(x, y, z));
        tgPosicionado.setTransform(posicion);
        tgPosicionado.addChild(tgArbol);

        return tgPosicionado;
    }

    public void agregarArbol(float posX, float posY, float posZ) {
        float ancho = 0.5f;
        float altura = 0.7f;
        String textura = "pino.png"; // Textura por defecto
        TransformGroup arbol = crearArbolCompleto(posX, posY, posZ, ancho, altura, textura);
        tgMundo.addChild(arbol);
    }
}

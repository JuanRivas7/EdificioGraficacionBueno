package steve;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import javax.media.j3d.BranchGroup;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.awt.Color;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class crearEscenaGrafica {

    Box cajaPersonaje;
    int paraTextura = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    Textura textura = new Textura();
    int pasos = 0;
    TransformGroup moverMouse;
    BranchGroup objRaiz = new BranchGroup();
    TransformGroup tgCabeza;
    TransformGroup tgSpCabeza;
    TransformGroup tgPanza;
    TransformGroup tgMundo;
    TransformGroup tgSpHomDer;// HOMBRO DERECHO
    TransformGroup tgSpHomIzq;// HOMBRO IZQ
    TransformGroup tgSpCodoDer;// CODO DERECHO
    TransformGroup tgSpCodoIzq; // CODO IZQ
    TransformGroup tgHomIzq;
    TransformGroup tgSpMusloDer;
    TransformGroup tgSpMusloIzq;
    TransformGroup tgSpPiernaDer;
    TransformGroup tgSpPiernaIzq;

    Appearance cara = textura.crearTexturas("cara.png");
    Appearance caraAtras = textura.crearTexturas("caraAtras.png");
    Appearance hombro = textura.crearTexturas("hombro.png");
    Appearance mano = textura.crearTexturas("mano.png");
    Appearance manoAtras = textura.crearTexturas("manoAtras.png");
    Appearance musloAtras = textura.crearTexturas("musloAtras.png");
    Appearance pechoAtras = textura.crearTexturas("pechoAtras.png");
    Appearance pieAtras = textura.crearTexturas("pieAtras.png");
    Appearance pechoFrente = textura.crearTexturas("pechofrente.png");

    public crearEscenaGrafica() {
        DarLuz(cara);
        DarLuz(caraAtras);
        DarLuz(hombro);
        DarLuz(mano);
        DarLuz(manoAtras);
        DarLuz(musloAtras);
        DarLuz(pechoAtras);
        DarLuz(pechoFrente);
        DarLuz(pieAtras);
        color c = new color(); //Objeto para todas las apariencias de color 
        Box bxPanza = new Box(0.20f, 0.3f, 0.1f, paraTextura, pechoAtras);
        bxPanza.setAppearance(0, pechoFrente);
        //Box bxMundo = new Box(-4.0f, 2.0f, 12.0f, paraTextura, textura.crearTexturas("gobcdmx.jpg"));
        //bxMundo.setAppearance(1, textura.crearTexturas("palacio.jpeg"));
        //bxMundo.setAppearance(3, textura.crearTexturas("catedral.png"));
        //bxMundo.setAppearance(4, textura.crearTexturas("cielo.jpg"));
        //bxMundo.setAppearance(5, textura.crearTexturas("piso.jpg"));
        Box bxCabeza = new Box(0.24f, 0.20f, 0.14f, paraTextura, caraAtras); // CABEZA de Steve
        bxCabeza.setAppearance(0, cara);
        Sphere spCabeza = new Sphere(0.05f, c.setColor(0, 0, 0));
        //------------------------BRAZO DE LA DERECHA -----------------------
        Box bxHomDer = new Box(0.1f, 0.15f, 0.1f, paraTextura, hombro); // HOMBRO DERECHO de steve
        Box bxBraDer = new Box(0.1f, 0.15f, 0.1f, paraTextura, manoAtras); // BRAZO DERECHO de steve
        bxBraDer.setAppearance(0, mano);
        Sphere spHomDer = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que lo se le safe el hombro a Steve
        Sphere spCodoDer = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que gire el CODO

        //-----------------------BRAZO DE LA IZQUIERDA------------------------
        Box bxHomIzq = new Box(0.1f, 0.15f, 0.1f, paraTextura, hombro); // HOMBRO IZQ de steve
        Box bxBraIzq = new Box(0.1f, 0.15f, 0.1f, paraTextura, manoAtras); // BRAZO IZQ de steve
        bxBraIzq.setAppearance(0, mano);
        Sphere spHomIzq = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que lo se le safe el hombro a Steve
        Sphere spCodoIzq = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que gire el CODO IZQ

        //------------------------PIERNA DERECHA-----------------------
        Box bxMusloDer = new Box(0.1f, 0.15f, 0.1f, paraTextura, musloAtras); // HOMBRO DERECHO de steve
        Box bxPiernaDer = new Box(0.1f, 0.15f, 0.1f, paraTextura, pieAtras); // BRAZO DERECHO de steve
        Sphere spMusloDer = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que lo se le safe el hombro a Steve
        Sphere spPiernaDer = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que gire el CODO

        //-----------------------PIERNA IZQUIERDA------------------------
        Box bxMusloIzq = new Box(0.1f, 0.15f, 0.1f, paraTextura, musloAtras); // HOMBRO IZQ de steve
        Box bxPiernaIzq = new Box(0.1f, 0.15f, 0.1f, paraTextura, pieAtras); // BRAZO IZQ de steve
        Sphere spMusloIzq = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que lo se le safe el hombro a Steve
        Sphere spPiernaIzq = new Sphere(0.05f, c.setColor(0, 0, 0)); //Esfera para que gire el CODO IZQ

        Transform3D t3dCabeza = new Transform3D(); // Objeto 3D CABEZA
        t3dCabeza.set(new Vector3d(0.0f, 0.5f, 0.0f));// Mover la cabeza 50y 
        Transform3D t3dSpCabeza = new Transform3D(); // Objeto para Girar
        t3dCabeza.set(new Vector3d(0.0f, 0.5f, 0.0f));// Mover la cabeza 50y 

        //-------------------- BRAZO DERECHO------------------------
        Transform3D t3dHomDer = new Transform3D(); //HOMBRO DERECHO
        t3dHomDer.set(new Vector3d(0.10f, -0.15f, 0.0f));
        Transform3D t3dBraDer = new Transform3D(); // BRAZO DERECHO
        t3dBraDer.set(new Vector3d(-0.01f, -0.15f, 0.0f));
        Transform3D t3dSpHomDer = new Transform3D(); // Esfera para rotar el HOMBRO DERECHO
        t3dSpHomDer.set(new Vector3d(0.20f, 0.30f, 0.0f));
        Transform3D t3dSpCodoDer = new Transform3D(); // Esfera para rotar el BRAZO DERECHO
        t3dSpCodoDer.set(new Vector3d(0.01f, -0.15f, 0.0f));

        //--------------------BRAZO IZQUIERDO------------------------
        Transform3D t3dHomIzq = new Transform3D(); //HOMBRO DERECHO
        t3dHomIzq.set(new Vector3d(-0.10f, -0.15f, 0.0f));// Mover el hombro
        Transform3D t3dBraIzq = new Transform3D(); // BRAZO DERECHO
        t3dBraIzq.set(new Vector3d(-0.01f, -0.15f, 0.0f));// Mover la
        Transform3D t3dSpHomIzq = new Transform3D(); // Esfera para rotar el HOMBRO IZQUIERDO
        t3dSpHomIzq.set(new Vector3d(-0.20f, 0.30f, 0.0f));
        Transform3D t3dSpCodoIzq = new Transform3D(); // Esfera para rotar el BRAZO IZQUIERDO
        t3dSpCodoIzq.set(new Vector3d(0.01f, -0.15f, 0.0f));

        //-------------------- PIERNA DERECHA------------------------
        Transform3D t3dMusloDer = new Transform3D(); //MUSLO DERECHA
        t3dMusloDer.set(new Vector3d(0.0f, -0.15f, 0.0f));
        Transform3D t3dPiernaDer = new Transform3D(); // PIERNA DERECHA
        t3dPiernaDer.set(new Vector3d(0.0f, -0.15f, 0.0f));
        Transform3D t3dSpMusloDer = new Transform3D(); // Esfera para rotar la CADERA DERECHA
        t3dSpMusloDer.set(new Vector3d(-0.10f, -0.30f, 0.0f));
        Transform3D t3dSpPiernaDer = new Transform3D(); // Esfera para rotar la RODILLA DERECHA
        t3dSpPiernaDer.set(new Vector3d(0.0f, -0.15f, 0.0f));

        //--------------------PIERNA IZQUIERDA------------------------
        Transform3D t3dMusloIzq = new Transform3D(); //MUSLO  DERECHO
        t3dMusloIzq.set(new Vector3d(0.0f, -0.15f, 0.0f));
        Transform3D t3dPiernaIzq = new Transform3D(); // PIERNA DERECHA
        t3dPiernaIzq.set(new Vector3d(-0.01f, -0.15f, 0.0f));
        Transform3D t3dSpMusloIzq = new Transform3D(); // Esfera para rotar LA CADERA IZQUIERA
        t3dSpMusloIzq.set(new Vector3d(0.10f, -0.30f, 0.0f));
        Transform3D t3dSpPiernaIzq = new Transform3D(); // Esfera para rotar LA RODILLA IZQUIERDO
        t3dSpPiernaIzq.set(new Vector3d(0.01f, -0.15f, 0.0f));

        ///Transform3D t3dMundo = new Transform3D();
        ///t3dMundo.set(new Vector3d(0.0f, 0.0f, 0.0f));
        //tgMundo = new TransformGroup(t3dMundo);
        Transform3D t3dPanza = new Transform3D();
        t3dPanza.set(new Vector3d(0.0f, -0.5f, -8.0f));
        tgPanza = new TransformGroup(t3dPanza); // Mover Panza
        tgCabeza = new TransformGroup(t3dCabeza); // Mover Cabeza
        //-----------------------BRAZOS----------------------
        TransformGroup tgHomDer = new TransformGroup(t3dHomDer); // Mostrar Hombro derecho
        tgHomIzq = new TransformGroup(t3dHomIzq);
        TransformGroup tgBraDer = new TransformGroup(t3dBraDer);
        TransformGroup tgBraIzq = new TransformGroup(t3dBraIzq);
        TransformGroup tgMusloDer = new TransformGroup(t3dMusloDer);
        TransformGroup tgMusloIzq = new TransformGroup(t3dMusloIzq);
        TransformGroup tgPiernaDer = new TransformGroup(t3dPiernaDer);
        TransformGroup tgPiernaIzq = new TransformGroup(t3dPiernaIzq);

        tgSpCabeza = new TransformGroup(t3dSpCabeza);

        tgSpHomDer = new TransformGroup(t3dSpHomDer); // Mover Esfera del Brazo derecho
        tgSpCodoDer = new TransformGroup(t3dSpCodoDer); // Mover Esfera del Brazo derecho
        tgSpHomIzq = new TransformGroup(t3dSpHomIzq);
        tgSpCodoIzq = new TransformGroup(t3dSpCodoIzq);
        //------------------------PIERNAS------------------------
        tgSpMusloDer = new TransformGroup(t3dSpMusloDer);
        tgSpMusloIzq = new TransformGroup(t3dSpMusloIzq);
        tgSpPiernaDer = new TransformGroup(t3dSpPiernaDer);
        tgSpPiernaIzq = new TransformGroup(t3dSpPiernaIzq);

        //tgMundo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgPanza.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgCabeza.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpCabeza.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //-------------------BRAZOS--------------------
        tgSpHomDer.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);// DAR permiso para reescribirse
        tgSpHomIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpCodoDer.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpCodoIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //-------------------PIERNAS--------------------
        tgSpMusloDer.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgHomIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpMusloIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpPiernaDer.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgSpPiernaIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        EscalarTG(tgPanza, 0.01f);

        MouseRotate myMouseRotate = new MouseRotate(); //permite utilizar el comportamiento que tiene el Mouse
        myMouseRotate.setTransformGroup(tgPanza);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        //-------Caja COLISION PANZA---------
        // En el constructor, modificar esta parte:
//-------Caja COLISION PANZA---------
        Appearance invisibleApp = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        ta.setTransparency(1.0f); // 1.0 = totalmente invisible
        invisibleApp.setTransparencyAttributes(ta);
        cajaPersonaje = new Box(0.4f, 0.8f, 0.14f, paraTextura, invisibleApp);
        tgPanza.addChild(cajaPersonaje);
        //-------------------------DIAGRAMA--------------------------
        objRaiz.addChild(myMouseRotate);
        objRaiz.addChild(tgPanza);
        //objRaiz.addChild(tgMundo);
        //tgMundo.addChild(bxMundo);

        // -------------Cabeza--------------
        tgCabeza.addChild(bxCabeza);
        tgPanza.addChild(tgSpCabeza);
        tgSpCabeza.addChild(spCabeza);
        tgSpCabeza.addChild(tgCabeza);
        // -------------Panza--------------
        tgPanza.addChild(bxPanza);
        //--------------------------------------------BRAZOS
        // --------------Hombro Derecho--------------
        tgHomDer.addChild(bxHomDer);
        tgPanza.addChild(tgSpHomDer);
        tgSpHomDer.addChild(spHomDer);
        tgSpHomDer.addChild(tgHomDer);
        // --------------Brazo Derecho--------------
        tgBraDer.addChild(bxBraDer);
        tgHomDer.addChild(tgSpCodoDer);
        tgSpCodoDer.addChild(spCodoDer);
        tgSpCodoDer.addChild(tgBraDer);

        // --------------Hombro IZQUIERDO--------------
        tgHomIzq.addChild(bxHomIzq);
        tgPanza.addChild(tgSpHomIzq);
        tgSpHomIzq.addChild(spHomIzq);
        tgSpHomIzq.addChild(tgHomIzq);
        // --------------Brazo IZQUIERDO--------------
        tgBraIzq.addChild(bxBraIzq);
        tgHomIzq.addChild(tgSpCodoIzq);
        tgSpCodoIzq.addChild(spCodoIzq);
        tgSpCodoIzq.addChild(tgBraIzq);
        //--------------------------------------------PIERNAS
        // --------------Muslo Derecho--------------
        tgMusloDer.addChild(bxMusloDer);
        tgPanza.addChild(tgSpMusloDer);
        tgSpMusloDer.addChild(spMusloDer);
        tgSpMusloDer.addChild(tgMusloDer);
        // --------------Pierna Derecho--------------
        tgPiernaDer.addChild(bxPiernaDer);
        tgMusloDer.addChild(tgSpPiernaDer);
        tgSpPiernaDer.addChild(spPiernaDer);
        tgSpPiernaDer.addChild(tgPiernaDer);

        // --------------Muslo IZQUIERDO--------------
        tgMusloIzq.addChild(bxMusloIzq);
        tgPanza.addChild(tgSpMusloIzq);
        tgSpMusloIzq.addChild(spMusloIzq);
        tgSpMusloIzq.addChild(tgMusloIzq);
        // --------------Pierna IZQUIERDO--------------
        tgPiernaIzq.addChild(bxPiernaIzq);
        tgMusloIzq.addChild(tgSpPiernaIzq);
        tgSpPiernaIzq.addChild(spPiernaIzq);
        tgSpPiernaIzq.addChild(tgPiernaIzq);
    }

    public void girarTG(TransformGroup tg, int grados, String eje) {
        Transform3D leerBrazo = new Transform3D();
        Transform3D moverBrazo = new Transform3D();
        if (tg != null) {
            tg.getTransform(leerBrazo);
        } else {
            System.out.println("TransformGroup tg es null");
        }

        switch (eje) {
            case "X":
                moverBrazo.rotX(Math.PI / 180 * grados);
                break;
            case "Y":
                moverBrazo.rotY(Math.PI / 180 * grados);
                break;
            case "Z":
                moverBrazo.rotZ(Math.PI / 180 * grados);
                moverBrazo.set(new Vector3d(0.0f, 0.0f, -0.05f));
                break;
            default:
                break;
        }

        leerBrazo.mul(moverBrazo);
        tg.setTransform(leerBrazo);
    }

    public void EscalarTG(TransformGroup tg, float x) {
        Transform3D leer = new Transform3D();
        Transform3D mover = new Transform3D();
        tg.getTransform(leer);
        mover.setScale(x);
        leer.mul(mover);
        tg.setTransform(leer);

    }

    public void caminar() {
        if (pasos < 10) {
            girarTG(tgSpMusloIzq, -5, "X");
            girarTG(tgSpPiernaIzq, 5, "X");
            girarTG(tgSpMusloDer, 5, "X");
            girarTG(tgSpPiernaDer, 5, "X");
            girarTG(tgSpHomDer, 5, "X");
            girarTG(tgSpCodoDer, -5, "X");
            girarTG(tgSpHomIzq, -5, "X");
            girarTG(tgSpCodoIzq, -5, "X");
        } else {
            if (pasos < 20) {
                girarTG(tgSpMusloIzq, 5, "X");
                girarTG(tgSpPiernaIzq, -5, "X");
                girarTG(tgSpMusloDer, -5, "X");
                girarTG(tgSpPiernaDer, -5, "X");
                girarTG(tgSpHomDer, -5, "X");
                girarTG(tgSpCodoDer, 5, "X");
                girarTG(tgSpHomIzq, 5, "X");
                girarTG(tgSpCodoIzq, 5, "X");
            } else {
                if (pasos < 30) {
                    girarTG(tgSpMusloIzq, 5, "X");
                    girarTG(tgSpPiernaIzq, 5, "X");
                    girarTG(tgSpMusloDer, -5, "X");
                    girarTG(tgSpPiernaDer, 5, "X");
                    girarTG(tgSpHomDer, -5, "X");
                    girarTG(tgSpCodoDer, -5, "X");
                    girarTG(tgSpHomIzq, 5, "X");
                    girarTG(tgSpCodoIzq, -5, "X");

                } else {
                    if (pasos < 40) {
                        girarTG(tgSpMusloIzq, -5, "X");
                        girarTG(tgSpPiernaIzq, -5, "X");
                        girarTG(tgSpMusloDer, 5, "X");
                        girarTG(tgSpPiernaDer, -5, "X");
                        girarTG(tgSpHomDer, 5, "X");
                        girarTG(tgSpCodoDer, 5, "X");
                        girarTG(tgSpHomIzq, -5, "X");
                        girarTG(tgSpCodoIzq, 5, "X");
                    } else {
                        pasos = -1;
                    }
                }
            }
        }
        //girarTG(tgMundo, 0, "Z");
        pasos++;

    }

    public void marchar() {
        if (pasos < 10) {
            girarTG(tgSpMusloIzq, -5, "X");
            girarTG(tgSpPiernaIzq, 2, "X");
            girarTG(tgSpMusloDer, 5, "X");
            girarTG(tgSpPiernaDer, 2, "X");
            girarTG(tgSpHomDer, 5, "X");
            girarTG(tgSpHomIzq, -5, "X");
        } else {
            if (pasos < 20) {
                girarTG(tgSpMusloIzq, 5, "X");
                girarTG(tgSpPiernaIzq, -2, "X");
                girarTG(tgSpMusloDer, -5, "X");
                girarTG(tgSpPiernaDer, -2, "X");
                girarTG(tgSpHomDer, -5, "X");
                girarTG(tgSpHomIzq, 5, "X");
            } else {
                if (pasos < 30) {
                    girarTG(tgSpMusloIzq, 5, "X");
                    girarTG(tgSpPiernaIzq, 2, "X");
                    girarTG(tgSpMusloDer, -5, "X");
                    girarTG(tgSpPiernaDer, 2, "X");
                    girarTG(tgSpHomDer, -5, "X");
                    girarTG(tgSpHomIzq, 5, "X");

                } else {
                    if (pasos < 40) {
                        girarTG(tgSpMusloIzq, -5, "X");
                        girarTG(tgSpMusloDer, 5, "X");
                        girarTG(tgSpPiernaDer, -2, "X");
                        girarTG(tgSpPiernaIzq, -2, "X");
                        girarTG(tgSpHomDer, 5, "X");
                        girarTG(tgSpHomIzq, -5, "X");
                    } else {
                        if (pasos < 50) {
                            girarTG(tgSpMusloIzq, 5, "X");
                            girarTG(tgSpMusloDer, -9, "X");
                            //Saludar
                            girarTG(tgSpHomIzq, -9, "X");
                            girarTG(tgSpCodoIzq, -9, "X");
                            girarTG(tgSpCodoIzq, 5, "Y");
                            girarTG(tgCabeza, -3, "Y");
                        } else {
                            if (pasos < 60) {
                                girarTG(tgSpMusloIzq, -5, "X");
                                girarTG(tgSpMusloDer, 9, "X");
                            } else {
                                if (pasos < 70) {
                                    girarTG(tgSpMusloIzq, 5, "X");
                                    girarTG(tgSpMusloDer, -5, "X");
                                    girarTG(tgSpPiernaDer, 2, "X");
                                    girarTG(tgSpPiernaIzq, 2, "X");
                                } else {
                                    if (pasos < 80) {
                                        girarTG(tgSpMusloIzq, -5, "X");
                                        girarTG(tgSpMusloDer, 5, "X");
                                        girarTG(tgSpPiernaDer, -2, "X");
                                        girarTG(tgSpPiernaIzq, -2, "X");
                                    } else {
                                        if (pasos < 90) {
                                            girarTG(tgSpMusloIzq, 5, "X");
                                            girarTG(tgSpMusloDer, -5, "X");
                                            //Regreso saludar
                                            girarTG(tgSpHomIzq, 9, "X");
                                            girarTG(tgSpCodoIzq, 9, "X");
                                            girarTG(tgSpCodoIzq, -5, "Y");
                                            girarTG(tgCabeza, 3, "Y");
                                            girarTG(tgSpPiernaDer, 2, "X");
                                            girarTG(tgSpPiernaIzq, 2, "X");
                                        } else {
                                            if (pasos < 100) {
                                                girarTG(tgSpMusloIzq, -5, "X");
                                                girarTG(tgSpMusloDer, 5, "X");
                                                girarTG(tgSpPiernaDer, -2, "X");
                                                girarTG(tgSpPiernaIzq, -2, "X");
                                            } else {
                                                pasos = -1;
                                            }

                                        }
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
        //girarTG(tgMundo, 0, "Z");
        pasos++;
    }//final marchar

    public void Posicion(float x, float y, float z) {
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3f(x, y, z));
        tgPanza.setTransform(posicion);
        EscalarTG(tgPanza, 0.3f);
    }

    public void DarLuz(Appearance a) {
        Material material = new Material();
        material.setAmbientColor(new Color3f(Color.DARK_GRAY));
        material.setDiffuseColor((new Color3f(Color.RED)));
        material.setSpecularColor((new Color3f(Color.WHITE)));
        material.setEmissiveColor((new Color3f(Color.BLACK)));
        material.setShininess(20.0f);
        a.setMaterial(material);
        Color3f colorAmbiente = new Color3f(Color.DARK_GRAY);
        AmbientLight luzAmbiente = new AmbientLight(colorAmbiente);
        luzAmbiente.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
        Color3f colorLuz = new Color3f(Color.DARK_GRAY);
        Vector3f luzdir = new Vector3f(-1.0f, -1.0f, -1.0f);
        DirectionalLight luz = new DirectionalLight(colorLuz, luzdir);
        luz.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
        objRaiz.addChild(luz);
        objRaiz.addChild(luzAmbiente);
    }

    public BranchGroup obtenerCuerpo() {
        return objRaiz;
    }

    public TransformGroup obtenerPanza() {
        return tgPanza;
    }

    public Box cajaColisionPersonaje() {
        return cajaPersonaje;
    }
}//final clase

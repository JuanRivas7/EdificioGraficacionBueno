package Principal;

import javax.media.j3d.SharedGroup;
import java.util.ArrayList;

public class SalonDeClasesBuilder {

    private final EscenaGrafica escena;
    private final InstanciadorObjetos3D instanciador;

    public SalonDeClasesBuilder(EscenaGrafica escena, InstanciadorObjetos3D instanciador) {
        this.escena = escena;
        this.instanciador = instanciador;
    }

    public void construirSalon(float origenX, float origenZ) {
        // Agregar 4 ventanas (2 interactivas)
        escena.agregarVentanaInteractiva(origenX + 1.5f, 0.3f, origenZ + 2.0f, 0.4f, 0.4f, 0.05f, 0f, true);
        escena.agregarVentanaInteractiva(origenX - 1.5f, 0.3f, origenZ + 2.0f, 0.4f, 0.4f, 0.05f, 0f, true);
        escena.agregarVentanaInteractiva(origenX + 1.5f, 0.3f, origenZ - 2.0f, 0.4f, 0.4f, 0.05f, 180f, false);
        escena.agregarVentanaInteractiva(origenX - 1.5f, 0.3f, origenZ - 2.0f, 0.4f, 0.4f, 0.05f, 180f, false);

         //Agregar mesas en grilla
        InstanciadorObjetos3D.Posicion3D[] posicionesMesas = instanciador.generarGrilla(
            origenX - 1.0f, origenZ - 2.0f,
            1, 1, 1.0f, 1.0f
        );
        instanciador.agregarMultiplesInstanciasConColision(
            RepositorioObjetos3D.mesa,
            posicionesMesas,
            0.3f, 0.2f, 0.3f
        );

        // Agregar sillas por cada mesa (opcional: desplazadas hacia atrás)
        ArrayList<InstanciadorObjetos3D.Posicion3D> posicionesSillas = new ArrayList<>();
        for (InstanciadorObjetos3D.Posicion3D posMesa : posicionesMesas) {
            posicionesSillas.add(new InstanciadorObjetos3D.Posicion3D(
                posMesa.x, posMesa.y, posMesa.z + 0.4f
            ));
        }
        instanciador.agregarMultiplesInstanciasConColision(
            RepositorioObjetos3D.silla,
            posicionesSillas.toArray(new InstanciadorObjetos3D.Posicion3D[0]),
            0.15f, 0.25f, 0.15f
        );

        // Opcional: Agregar paredes, puertas u otros objetos decorativos aquí
    }
}

package com.example.LibrosCatalogo.config;

import com.example.LibrosCatalogo.model.Autor;
import com.example.LibrosCatalogo.model.Categoria;
import com.example.LibrosCatalogo.model.Libro;
import com.example.LibrosCatalogo.repository.AutorRepository;
import com.example.LibrosCatalogo.repository.CategoriaRepository;
import com.example.LibrosCatalogo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LibroRepository libroRepository;

    @Autowired
    public DataLoader(AutorRepository autorRepository, 
                     CategoriaRepository categoriaRepository, 
                     LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        cargarDatosIniciales();
        System.out.println("=== DATOS DEL CATÁLOGO CARGADOS EXITOSAMENTE ===");
    }

    private void cargarDatosIniciales() {
        if (categoriaRepository.count() == 0) {
            cargarCategorias();
        }
        if (autorRepository.count() == 0) {
            cargarAutores();
        }
        if (libroRepository.count() == 0) {
            cargarLibros();
        }
    }

    private void cargarCategorias() {
        Categoria ficcion = new Categoria("Ficción", "Novelas de ficción y literatura imaginativa");
        Categoria cienciaFiccion = new Categoria("Ciencia Ficción", "Literatura basada en supuestos logros científicos");
        Categoria misterio = new Categoria("Misterio", "Novelas de suspense y misterio");
        Categoria romance = new Categoria("Romance", "Novelas románticas");
        Categoria biografia = new Categoria("Biografía", "Historias de vida reales");
        Categoria historia = new Categoria("Historia", "Libros sobre eventos históricos");

        categoriaRepository.save(ficcion);
        categoriaRepository.save(cienciaFiccion);
        categoriaRepository.save(misterio);
        categoriaRepository.save(romance);
        categoriaRepository.save(biografia);
        categoriaRepository.save(historia);
    }

    private void cargarAutores() {
        Autor gabrielGarcia = new Autor("Gabriel", "García Márquez", "Colombiano", "Premio Nobel de Literatura 1982");
        Autor isabelAllende = new Autor("Isabel", "Allende", "Chilena", "Una de las escritoras más leídas del mundo");
        Autor julioCortazar = new Autor("Julio", "Cortázar", "Argentino", "Maestro del relato corto");
        Autor georgeOrwell = new Autor("George", "Orwell", "Británico", "Escritor y periodista político");
        Autor agathaChristie = new Autor("Agatha", "Christie", "Británica", "Reina del misterio");
        Autor janeAusten = new Autor("Jane", "Austen", "Británica", "Clásica de la literatura inglesa");

        autorRepository.save(gabrielGarcia);
        autorRepository.save(isabelAllende);
        autorRepository.save(julioCortazar);
        autorRepository.save(georgeOrwell);
        autorRepository.save(agathaChristie);
        autorRepository.save(janeAusten);
    }

    private void cargarLibros() {
        Categoria ficcion = categoriaRepository.findByNombre("Ficción").orElseThrow();
        Categoria cienciaFiccion = categoriaRepository.findByNombre("Ciencia Ficción").orElseThrow();
        Categoria misterio = categoriaRepository.findByNombre("Misterio").orElseThrow();
        Categoria romance = categoriaRepository.findByNombre("Romance").orElseThrow();

        Autor gabrielGarcia = autorRepository.findByNombreAndApellido("Gabriel", "García Márquez").orElseThrow();
        Autor isabelAllende = autorRepository.findByNombreAndApellido("Isabel", "Allende").orElseThrow();
        Autor georgeOrwell = autorRepository.findByNombreAndApellido("George", "Orwell").orElseThrow();
        Autor agathaChristie = autorRepository.findByNombreAndApellido("Agatha", "Christie").orElseThrow();
        Autor janeAusten = autorRepository.findByNombreAndApellido("Jane", "Austen").orElseThrow();

        // Libros de ejemplo
        Libro cienAnios = new Libro(
            "Cien Años de Soledad",
            "978-8437604947",
            "Una obra maestra de la literatura hispanoamericana",
            1967,
            "Sudamericana",
            "Español",
            471,
            10,
            gabrielGarcia,
            ficcion
        );
        cienAnios.setPortadaUrl("https://images.example.com/cien-anios.jpg");

        Libro casaEspiritus = new Libro(
            "La Casa de los Espíritus",
            "978-8401337208",
            "Novela que narra la vida de la familia Trueba",
            1982,
            "Plaza & Janés",
            "Español",
            499,
            8,
            isabelAllende,
            ficcion
        );

        Libro rebelionGranja = new Libro(
            "Rebelión en la Granja",
            "978-8499890944",
            "Sátira sobre el totalitarismo",
            1945,
            "Debolsillo",
            "Español",
            144,
            15,
            georgeOrwell,
            cienciaFiccion
        );

        Libro asesinatoOrientExpress = new Libro(
            "Asesinato en el Orient Express",
            "978-8490325782",
            "Uno de los casos más famosos de Hércules Poirot",
            1934,
            "Espasa",
            "Español",
            256,
            12,
            agathaChristie,
            misterio
        );

        Libro orgulloPrejuicio = new Libro(
            "Orgullo y Prejuicio",
            "978-8491059258",
            "Clásico de la literatura romántica",
            1813,
            "Alma",
            "Español",
            432,
            20,
            janeAusten,
            romance
        );

        libroRepository.save(cienAnios);
        libroRepository.save(casaEspiritus);
        libroRepository.save(rebelionGranja);
        libroRepository.save(asesinatoOrientExpress);
        libroRepository.save(orgulloPrejuicio);
    }
}
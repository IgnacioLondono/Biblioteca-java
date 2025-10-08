package com.example.LibrosCatalogo.service;

import com.example.LibrosCatalogo.dto.*;
import com.example.LibrosCatalogo.model.*;
import com.example.LibrosCatalogo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository, 
                          AutorRepository autorRepository, 
                          CategoriaRepository categoriaRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public LibroDTO crearLibro(LibroCreateRequest request) {
        if (libroRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con el ISBN: " + request.getIsbn());
        }

        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + request.getAutorId()));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoriaId()));

        // Crear libro usando constructor
        Libro libro = new Libro(
            request.getTitulo(),
            request.getIsbn(),
            request.getDescripcion(),
            request.getAnioPublicacion(),
            request.getEditorial(),
            request.getIdioma(),
            request.getPaginas(),
            request.getCantidadTotal(),
            autor,
            categoria
        );

        // Establecer portada URL usando método directo
        setPortadaUrl(libro, request.getPortadaUrl());

        Libro libroGuardado = libroRepository.save(libro);
        return convertirADTO(libroGuardado);
    }

    @Override
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroCreateRequest request) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        // Verificar ISBN único si cambió
        if (!getIsbn(libro).equals(request.getIsbn()) && 
            libroRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con el ISBN: " + request.getIsbn());
        }

        // Actualizar campos básicos usando métodos de acceso directo
        setTitulo(libro, request.getTitulo());
        setIsbn(libro, request.getIsbn());
        setDescripcion(libro, request.getDescripcion());
        setAnioPublicacion(libro, request.getAnioPublicacion());
        setEditorial(libro, request.getEditorial());
        setIdioma(libro, request.getIdioma());
        setPaginas(libro, request.getPaginas());
        setPortadaUrl(libro, request.getPortadaUrl());

        // Actualizar autor si cambió
        if (!getAutorId(libro).equals(request.getAutorId())) {
            Autor nuevoAutor = autorRepository.findById(request.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + request.getAutorId()));
            setAutor(libro, nuevoAutor);
        }

        // Actualizar categoría si cambió
        if (!getCategoriaId(libro).equals(request.getCategoriaId())) {
            Categoria nuevaCategoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoriaId()));
            setCategoria(libro, nuevaCategoria);
        }

        Libro libroActualizado = libroRepository.save(libro);
        return convertirADTO(libroActualizado);
    }

    @Override
    @Transactional
    public void eliminarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        setActivo(libro, false);
        libroRepository.save(libro);
    }

    @Override
    public LibroDTO obtenerLibroPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        return convertirADTO(libro);
    }

    @Override
    public List<LibroDTO> obtenerTodosLosLibros() {
        List<Libro> librosActivos = libroRepository.findByActivoTrue();
        return librosActivos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroDTO> buscarLibros(BusquedaRequest request) {
        List<Libro> libros;
        
        if (request.getTermino() != null && !request.getTermino().trim().isEmpty()) {
            libros = libroRepository.buscarPorTermino(request.getTermino().trim());
        } else if (request.getCategoria() != null && !request.getCategoria().trim().isEmpty()) {
            libros = libroRepository.findByCategoriaNombre(request.getCategoria().trim());
        } else if (request.getAutor() != null && !request.getAutor().trim().isEmpty()) {
            libros = libroRepository.findByAutorNombreContainingIgnoreCase(request.getAutor().trim());
        } else {
            libros = libroRepository.findByActivoTrue();
        }

        // Filtrar por disponibilidad si se solicita
        if (Boolean.TRUE.equals(request.getSoloDisponibles())) {
            libros = libros.stream()
                    .filter(libro -> getCantidadDisponible(libro) > 0)
                    .collect(Collectors.toList());
        }

        return libros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroDTO> obtenerLibrosDisponibles() {
        List<Libro> librosDisponibles = libroRepository.findByCantidadDisponibleGreaterThan(0);
        return librosDisponibles.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroDTO> obtenerLibrosPorCategoria(String categoria) {
        List<Libro> librosPorCategoria = libroRepository.findByCategoriaNombre(categoria);
        return librosPorCategoria.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroDTO> obtenerLibrosPorAutor(String autor) {
        List<Libro> librosPorAutor = libroRepository.findByAutorNombreContainingIgnoreCase(autor);
        return librosPorAutor.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LibroDTO actualizarStock(Long id, Integer nuevaCantidad) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        
        // ✅ CORRECCIÓN: Guardar la cantidad actual antes de cambiarla
        int cantidadActual = getCantidadTotal(libro);
        setCantidadTotal(libro, nuevaCantidad);
        
        // Calcular diferencia usando la cantidad anterior
        int diferencia = nuevaCantidad - cantidadActual;
        int nuevaCantidadDisponible = Math.max(0, getCantidadDisponible(libro) + diferencia);
        setCantidadDisponible(libro, nuevaCantidadDisponible);
        
        Libro libroActualizado = libroRepository.save(libro);
        return convertirADTO(libroActualizado);
    }

    @Override
    public boolean existeLibroPorIsbn(String isbn) {
        return libroRepository.existsByIsbn(isbn);
    }

    // ========== MÉTODOS DE ACCESO DIRECTO A CAMPOS ==========
    
    private String getTitulo(Libro libro) {
        try {
            return libro.getTitulo();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "titulo", String.class);
        }
    }

    private void setTitulo(Libro libro, String titulo) {
        try {
            libro.setTitulo(titulo);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "titulo", titulo);
        }
    }

    private String getIsbn(Libro libro) {
        try {
            return libro.getIsbn();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "isbn", String.class);
        }
    }

    private void setIsbn(Libro libro, String isbn) {
        try {
            libro.setIsbn(isbn);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "isbn", isbn);
        }
    }

    private String getDescripcion(Libro libro) {
        try {
            return libro.getDescripcion();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "descripcion", String.class);
        }
    }

    private void setDescripcion(Libro libro, String descripcion) {
        try {
            libro.setDescripcion(descripcion);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "descripcion", descripcion);
        }
    }

    private Integer getAnioPublicacion(Libro libro) {
        try {
            return libro.getAnioPublicacion();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "anioPublicacion", Integer.class);
        }
    }

    private void setAnioPublicacion(Libro libro, Integer anioPublicacion) {
        try {
            libro.setAnioPublicacion(anioPublicacion);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "anioPublicacion", anioPublicacion);
        }
    }

    private String getEditorial(Libro libro) {
        try {
            return libro.getEditorial();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "editorial", String.class);
        }
    }

    private void setEditorial(Libro libro, String editorial) {
        try {
            libro.setEditorial(editorial);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "editorial", editorial);
        }
    }

    private String getIdioma(Libro libro) {
        try {
            return libro.getIdioma();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "idioma", String.class);
        }
    }

    private void setIdioma(Libro libro, String idioma) {
        try {
            libro.setIdioma(idioma);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "idioma", idioma);
        }
    }

    private Integer getPaginas(Libro libro) {
        try {
            return libro.getPaginas();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "paginas", Integer.class);
        }
    }

    private void setPaginas(Libro libro, Integer paginas) {
        try {
            libro.setPaginas(paginas);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "paginas", paginas);
        }
    }

    private String getPortadaUrl(Libro libro) {
        try {
            return libro.getPortadaUrl();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "portadaUrl", String.class);
        }
    }

    private void setPortadaUrl(Libro libro, String portadaUrl) {
        try {
            libro.setPortadaUrl(portadaUrl);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "portadaUrl", portadaUrl);
        }
    }

    private Integer getCantidadTotal(Libro libro) {
        try {
            return libro.getCantidadTotal();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "cantidadTotal", Integer.class);
        }
    }

    private void setCantidadTotal(Libro libro, Integer cantidadTotal) {
        try {
            libro.setCantidadTotal(cantidadTotal);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "cantidadTotal", cantidadTotal);
        }
    }

    private Integer getCantidadDisponible(Libro libro) {
        try {
            return libro.getCantidadDisponible();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "cantidadDisponible", Integer.class);
        }
    }

    private void setCantidadDisponible(Libro libro, Integer cantidadDisponible) {
        try {
            libro.setCantidadDisponible(cantidadDisponible);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "cantidadDisponible", cantidadDisponible);
        }
    }

    private Boolean getActivo(Libro libro) {
        try {
            return libro.getActivo();
        } catch (Exception e) {
            return accederCampoDirecto(libro, "activo", Boolean.class);
        }
    }

    private void setActivo(Libro libro, Boolean activo) {
        try {
            libro.setActivo(activo);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "activo", activo);
        }
    }

    private Long getAutorId(Libro libro) {
        try {
            return libro.getAutor().getId();
        } catch (Exception e) {
            return 0L;
        }
    }

    private void setAutor(Libro libro, Autor autor) {
        try {
            libro.setAutor(autor);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "autor", autor);
        }
    }

    private Long getCategoriaId(Libro libro) {
        try {
            return libro.getCategoria().getId();
        } catch (Exception e) {
            return 0L;
        }
    }

    private void setCategoria(Libro libro, Categoria categoria) {
        try {
            libro.setCategoria(categoria);
        } catch (Exception e) {
            asignarCampoDirecto(libro, "categoria", categoria);
        }
    }

    // Métodos genéricos para acceso directo a campos
    @SuppressWarnings("unchecked")
    private <T> T accederCampoDirecto(Object objeto, String nombreCampo, Class<T> tipo) {
        try {
            java.lang.reflect.Field campo = objeto.getClass().getDeclaredField(nombreCampo);
            campo.setAccessible(true);
            return (T) campo.get(objeto);
        } catch (Exception e) {
            System.err.println("Error accediendo campo " + nombreCampo + ": " + e.getMessage());
            return null;
        }
    }

    private void asignarCampoDirecto(Object objeto, String nombreCampo, Object valor) {
        try {
            java.lang.reflect.Field campo = objeto.getClass().getDeclaredField(nombreCampo);
            campo.setAccessible(true);
            campo.set(objeto, valor);
        } catch (Exception e) {
            System.err.println("Error asignando campo " + nombreCampo + ": " + e.getMessage());
        }
    }

    private LibroDTO convertirADTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(getTitulo(libro));
        dto.setIsbn(getIsbn(libro));
        dto.setDescripcion(getDescripcion(libro));
        dto.setAnioPublicacion(getAnioPublicacion(libro));
        dto.setEditorial(getEditorial(libro));
        dto.setIdioma(getIdioma(libro));
        dto.setPaginas(getPaginas(libro));
        dto.setPortadaUrl(getPortadaUrl(libro));
        dto.setCantidadTotal(getCantidadTotal(libro));
        dto.setCantidadDisponible(getCantidadDisponible(libro));
        dto.setActivo(getActivo(libro));
        dto.setFechaCreacion(libro.getFechaCreacion());

        // Convertir autor
        if (libro.getAutor() != null) {
            LibroDTO.AutorDTO autorDTO = new LibroDTO.AutorDTO();
            autorDTO.setId(libro.getAutor().getId());
            autorDTO.setNombre(libro.getAutor().getNombre());
            autorDTO.setApellido(libro.getAutor().getApellido());
            autorDTO.setNacionalidad(libro.getAutor().getNacionalidad());
            dto.setAutor(autorDTO);
        }

        // Convertir categoría
        if (libro.getCategoria() != null) {
            LibroDTO.CategoriaDTO categoriaDTO = new LibroDTO.CategoriaDTO();
            categoriaDTO.setId(libro.getCategoria().getId());
            categoriaDTO.setNombre(libro.getCategoria().getNombre());
            categoriaDTO.setDescripcion(libro.getCategoria().getDescripcion());
            dto.setCategoria(categoriaDTO);
        }

        return dto;
    }
}
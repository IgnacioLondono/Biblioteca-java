package com.example.LibrosCatalogo.service;

import com.example.LibrosCatalogo.dto.LibroCreateRequest;
import com.example.LibrosCatalogo.dto.LibroDTO;
import com.example.LibrosCatalogo.dto.BusquedaRequest;

import java.util.List;

public interface LibroService {
    LibroDTO crearLibro(LibroCreateRequest request);
    LibroDTO actualizarLibro(Long id, LibroCreateRequest request);
    void eliminarLibro(Long id);
    LibroDTO obtenerLibroPorId(Long id);
    List<LibroDTO> obtenerTodosLosLibros();
    List<LibroDTO> buscarLibros(BusquedaRequest request);
    List<LibroDTO> obtenerLibrosDisponibles();
    List<LibroDTO> obtenerLibrosPorCategoria(String categoria);
    List<LibroDTO> obtenerLibrosPorAutor(String autor);
    LibroDTO actualizarStock(Long id, Integer nuevaCantidad);
    boolean existeLibroPorIsbn(String isbn);
}
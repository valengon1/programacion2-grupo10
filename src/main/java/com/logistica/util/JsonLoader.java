package com.logistica.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistica.modelo.Paquete;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonLoader {

    public static List<Paquete<String>> cargarPaquetes(String ruta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(
                    new File(ruta),
                    new TypeReference<List<Paquete<String>>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
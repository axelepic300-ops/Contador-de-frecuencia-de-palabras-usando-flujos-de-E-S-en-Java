import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContadorPalabras {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ContadorPalabras <archivo>");
            return;
        }

        Path archivo = Path.of(args[0]);
        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }

        // ENTRADA + PROCESAMIENTO
        Map<String, Integer> frecuencias = new LinkedHashMap<>();

        try (BufferedReader lector = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.toLowerCase()
                             .replaceAll("[^\\p{L}\\p{N}\\s]", "");

                if (linea.isBlank()) {
                    continue;
                }

                for (String palabra : linea.trim().split("\\s+")) {
                    frecuencias.merge(palabra, 1, Integer::sum);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
            return;
        }

        // SALIDA en consola
        for (Map.Entry<String, Integer> e : frecuencias.entrySet()) {
            System.out.printf("%-15s %d%n", e.getKey(), e.getValue());
        }

        // SALIDA en archivo
        try {
            Path dirSalida = Path.of("salida");
            Files.createDirectories(dirSalida);
            Path archivoSalida = dirSalida.resolve("frecuencias.txt");

            try (PrintWriter escritor =
                     new PrintWriter(Files.newBufferedWriter(archivoSalida))) {
                escritor.printf("%-20s %s%n", "PALABRA", "FRECUENCIA");
                escritor.println("-------------------------------");
                for (Map.Entry<String, Integer> e : frecuencias.entrySet()) {
                    escritor.printf("%-20s %d%n", e.getKey(), e.getValue());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir resultados: " + e.getMessage());
        }
    }
}

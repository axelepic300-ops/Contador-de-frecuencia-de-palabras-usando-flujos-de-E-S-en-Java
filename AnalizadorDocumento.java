import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class AnalizadorDocumento {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java AnalizadorDocumento <archivo>");
            return;
        }

        Path archivo = Path.of(args[0]);
        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }

        // TreeMap mantiene las palabras en orden alfabético
        Map<String, Integer> frecuencias = new TreeMap<>();
        int totalLineas = 0;
        int totalPalabras = 0;

        // ENTRADA + PROCESAMIENTO
        try (BufferedReader lector = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                totalLineas++;

                linea = linea.toLowerCase()
                             .replaceAll("[^\\p{L}\\p{N}\\s]", "");

                if (linea.isBlank()) {
                    continue;
                }

                for (String palabra : linea.trim().split("\\s+")) {
                    frecuencias.merge(palabra, 1, Integer::sum);
                    totalPalabras++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
            return;
        }

        // Palabra más frecuente
        String masFrecuente = "";
        int frecuenciaMaxima = 0;
        for (Map.Entry<String, Integer> e : frecuencias.entrySet()) {
            if (e.getValue() > frecuenciaMaxima) {
                masFrecuente = e.getKey();
                frecuenciaMaxima = e.getValue();
            }
        }

        // SALIDA
        try {
            Path dirSalida = Path.of("salida");
            Files.createDirectories(dirSalida);
            Path archivoSalida = dirSalida.resolve("reporte-documento.txt");

            try (PrintWriter escritor =
                     new PrintWriter(Files.newBufferedWriter(archivoSalida))) {
                escritor.println("ANÁLISIS DEL DOCUMENTO");
                escritor.println("======================");
                escritor.println();
                escritor.println("Archivo: " + archivo.getFileName());
                escritor.println();
                escritor.println("Total de líneas: " + totalLineas);
                escritor.println("Total de palabras: " + totalPalabras);
                escritor.println("Palabras diferentes: " + frecuencias.size());
                escritor.println();
                escritor.println("Palabra más frecuente:");
                escritor.println(masFrecuente + " (" + frecuenciaMaxima + ")");
                escritor.println();
                escritor.println("FRECUENCIA DE PALABRAS");
                escritor.println("----------------------");
                escritor.println();

                for (Map.Entry<String, Integer> e : frecuencias.entrySet()) {
                    escritor.printf("%-20s %d%n", e.getKey(), e.getValue());
                }
            }

            System.out.println("Reporte generado: " + archivoSalida);
        } catch (IOException e) {
            System.err.println("Error al escribir el reporte: " + e.getMessage());
        }
    }
}

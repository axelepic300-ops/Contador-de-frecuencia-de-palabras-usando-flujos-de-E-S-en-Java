# Contador de frecuencia de palabras en Java

Desarrollo de Sistemas III – Flujos de E/S en Java

## Compilar

```bash
javac -d out src/*.java
```

## Ejecutar

```bash
java -cp out ContadorPalabras datos/texto.txt
java -cp out AnalizadorDocumento datos/documento.txt
```

Genera `salida/frecuencias.txt` y `salida/reporte-documento.txt`.

## Respuestas a las preguntas de la guía

1. **¿Bytes o caracteres?** Caracteres: el archivo es texto, y los flujos de
   caracteres (`BufferedReader`, `PrintWriter`) manejan la codificación y
   permiten leer línea por línea.
2. **¿Por qué recibir el nombre como argumento?** Evita modificar y recompilar
   el código para cada archivo, y hace el programa reutilizable.
3. **¿Y si no normalizamos?** `Java`, `JAVA` y `java.` se contarían como
   palabras distintas, y las frecuencias serían incorrectas.

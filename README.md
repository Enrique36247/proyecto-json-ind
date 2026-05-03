# Proyecto JSON IND - Laboratorio de Robots

## 1. Tematica del proyecto (explicacion divulgativa)
Este proyecto simula la gestion de un laboratorio de robots como si fuera la gestion diaria de un trastero bien organizado:
- cada robot es como una "caja" con etiqueta unica (ID),
- cada dato del robot (nombre, modelo, estado, ubicacion...) es como la informacion escrita en esa etiqueta,
- y el archivo `laboratorio.json` funciona como el cuaderno maestro donde se guarda todo.

La aplicacion permite hacer operaciones CRUD (crear, leer, actualizar y eliminar) desde una interfaz grafica en Java Swing, de forma que el usuario no necesita editar JSON manualmente.

## 2. Estructura del JSON
La informacion se guarda en un objeto raiz con una lista llamada `robots`.

Ejemplo real de estructura:

```json
{
  "robots": [
    {
      "id": "R0001",
      "nombre": "acebott",
      "modelo": "chino",
      "funcion": "aprendizaje de electronica",
      "añoFabricacion": 2024,
      "estado": "ACTIVO",
      "ubicacion": "area sur"
    }
  ]
}
```

Significado de cada clave:
- `id`: identificador unico autogenerado (formato `R0001`, `R0002`, ...).
- `nombre`: nombre del robot.
- `modelo`: tipo o modelo de hardware/plataforma.
- `funcion`: tarea principal del robot.
- `añoFabricacion`: anio de fabricacion (validado entre 1900 y 2100).
- `estado`: `ACTIVO`, `MANTENIMIENTO` o `INACTIVO`.
- `ubicacion`: zona fisica dentro del laboratorio.

## 3. Modelo de datos
El modelo se organiza en tres piezas:
- `Robot`: entidad principal con los campos del robot.
- `DatosLaboratorio`: contenedor de la lista `robots` para mapear correctamente el JSON.
- `GestorJSON`: capa de persistencia que carga, valida y guarda datos con Gson.

En la interfaz (`InterfazLaboratorio`) se usa una lista en memoria (`List<Robot>`) sincronizada con el archivo JSON al aniadir, modificar o eliminar.

## 4. Misma informacion en XML y relacion con bases de datos relacionales
La misma informacion en XML podria verse asi:

```xml
<laboratorio>
  <robots>
    <robot id="R0001">
      <nombre>acebott</nombre>
      <modelo>chino</modelo>
      <funcion>aprendizaje de electronica</funcion>
      <anioFabricacion>2024</anioFabricacion>
      <estado>ACTIVO</estado>
      <ubicacion>area sur</ubicacion>
    </robot>
  </robots>
</laboratorio>
```

Comparacion clara:
- JSON es mas compacto y sencillo de leer para aplicaciones modernas.
- XML es mas verboso, pero permite validaciones estructurales avanzadas con esquemas.

Relacion con modelo relacional (BD):
- Tabla: `robots`
- Registros (filas): cada robot (R0001, R0002, ...)
- Campos (columnas): `id`, `nombre`, `modelo`, `funcion`, `anio_fabricacion`, `estado`, `ubicacion`
- Relaciones: en este proyecto no hay tablas secundarias, pero podrian existir (por ejemplo, tabla `mantenimientos` relacionada por `id_robot`).

Analogía cotidiana:
- JSON/XML son como distintos formatos de lista de la compra (uno mas corto, otro mas detallado).
- Una base de datos relacional es como una estanteria con cajones etiquetados (tablas), donde cada cajon tiene fichas (registros) con casillas fijas (campos).

## 5. Manual de usuario
1. Abre la aplicacion.
2. Revisa la tabla central para ver los robots cargados desde `laboratorio.json`.
3. Para aniadir:
   - completa `Nombre`, `Modelo`, `Funcion`, `Año`, `Estado` y `Ubicacion`,
   - pulsa `Añadir`.
4. Para modificar:
   - selecciona una fila de la tabla,
   - edita campos en el formulario,
   - pulsa `Modificar`.
5. Para eliminar:
   - selecciona un robot en la tabla,
   - pulsa `Eliminar` y confirma.
6. Para buscar:
   - escribe criterio en el cuadro de busqueda (nombre, modelo, funcion, ID o ubicacion),
   - pulsa `Buscar` (formulario/tabla segun flujo).
7. Usa `Mostrar Todos` o `Actualizar` para refrescar la vista completa.
8. Usa `Limpiar` para reiniciar el formulario.

## 6. Manual de instalacion
Requisitos:
- JDK 17 instalado.
- Maven 3.8+ instalado.
- Sistema operativo con soporte para Java Swing.

Pasos recomendados:
1. Clona el repositorio.
2. Entra en la carpeta del proyecto Maven:
   - `proyecto-json-ind/proyecto-json-ind`
3. Compila dependencias:
   - `mvn clean package`
4. Ejecuta la aplicacion:
   - desde IDE, ejecutando `com.example.Main`, o
   - por terminal, ejecutando la clase principal con classpath de `target` y dependencias.
5. Verifica que se cree/actualice `laboratorio.json` en el directorio de ejecucion.

## 7. Conclusión personal
En este proyecto he aprendido que JSON no es solo "texto con llaves", sino una forma practica de modelar informacion real de manera ordenada y reutilizable. Tambien he visto que una interfaz grafica sencilla puede mejorar mucho la usabilidad frente a editar archivos a mano.

La IA influye en el desarrollo porque acelera tareas tecnicas (estructura, validaciones, ideas de organizacion), pero no sustituye el criterio del desarrollador: hay que revisar logica, probar flujos y adaptar el resultado al contexto real del proyecto.

La relacion entre JSON, XML y bases de datos relacionales la entiendo como tres formas de guardar "la misma historia":
- JSON: formato ligero para intercambio.
- XML: formato estructurado y mas formal.
- BD relacional: almacenamiento tabular con reglas y relaciones.

Ejemplo cotidiano: una agenda de contactos puede guardarse en JSON para una app movil, en XML para integrarse con otro sistema, o en tablas SQL para consultas y reportes.

## 8. Bibliografia y recursos consultados
- IA utilizada: ChatGPT (OpenAI) como apoyo para redaccion tecnica, organizacion documental y revision de consistencia.
- RFC 8259 (JSON): https://www.rfc-editor.org/rfc/rfc8259
- W3C XML 1.0 (Fifth Edition): https://www.w3.org/TR/xml/
- Documentacion Oracle sobre tablas, filas y columnas: https://docs.oracle.com/html/E25494_01/tables001.htm
- Documentacion oficial de Java Swing: https://docs.oracle.com/javase/tutorial/uiswing/
- Gson (serializacion y deserializacion JSON en Java): https://github.com/google/gson

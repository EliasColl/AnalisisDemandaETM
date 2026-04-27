<div align="center">

  <img src="Logo_Bus_Proyecto_AMPROG.svg" alt="Logo del proyecto" width="180" />

  # Análisis de Demanda EMT Madrid 
 **(Java 21 · Streams · NIO)**

  <a href="https://openjdk.org/projects/jdk/21/">
    <img src="https://img.shields.io/badge/Java-21-informational?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21 badge" />
  </a>
  <a href="LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-success?style=for-the-badge" alt="MIT License badge" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge" alt="Build Passing badge" />
  </a>

</div>

Proyecto Java 21 orientado al **análisis funcional de demanda de transporte público** a partir de datos abiertos de **EMT Madrid**. El objetivo es transformar un CSV real de demanda diaria en un modelo inmutable y, a partir de ahí, aplicar un pipeline de análisis reproducible que permita **filtrar, ordenar, resumir y validar hipótesis** con resultados comprensibles y accionables.

El enfoque se apoya en tres pilares técnicos: **ingesta robusta con `java.nio`**, **modelado con POJOs inmutables** y **procesamiento declarativo con Streams** (operaciones intermedias + terminales). El resultado es una herramienta ligera, rápida y fácil de mantener, diseñada con mentalidad de “data product”: datos → modelo → análisis → visualización.

> Nota: el proyecto está desarrollado en **Java 21** y está pensado para ejecutarse desde consola, con salidas orientadas a facilitar la revisión y la presentación de resultados.

---

## Tecnologías utilizadas

- **Java 21 (OpenJDK)** — base del runtime y APIs modernas.
- **Stream API** — programación funcional (filter/map/sorted/reduce, etc.).
- **Java.nio** — lectura de ficheros eficiente y segura (Paths/Files/streams).

---

## Arquitectura por fases (pipeline de datos)

El proyecto sigue un flujo claro, desde la ingesta del dataset hasta la validación de hipótesis con una visualización inmediata en consola. Cada fase está diseñada para ser **componible**, **reutilizable** y **fácil de auditar**.

### Fase 1 — Ingesta & Modelado (NIO + Inmutabilidad)
El pipeline comienza con la carga del CSV de EMT Madrid mediante **NIO** y un flujo de lectura controlado. Cada línea del archivo se **parsea** y se convierte en un **POJO inmutable** (campos finales), garantizando:
- Integridad del modelo (sin estados intermedios inconsistentes).
- Trazabilidad (cada registro representa una fila del dataset).
- Seguridad y simplicidad al operar con Streams (sin efectos colaterales).

**Piezas clave:**
- `DemandaEMT`: representación inmutable de un registro.
- `LectorEMT`: lectura desde ruta NIO, procesamiento del stream de líneas, transformación a lista de objetos.

---

### Fase 2 — Procesamiento intermedio (filtros, ordenación y Top-N)
Una vez modelados los datos, se aplican operaciones **intermedias** de Stream para responder preguntas típicas de demanda:
- Filtrado de registros con **alta demanda** mediante `Predicate`.
- Ordenación por criterios cuantitativos (`sorted`).
- Extracción de Top-N (`limit`) y consumo final (`forEach`) con `Consumer`.

Esta fase está enfocada en generar **insights inmediatos** (por ejemplo, “los 5 viajes con mayor demanda”) manteniendo una sintaxis declarativa y legible.

---

### Fase 3 — Agrupación & Estadísticas (terminales y conversiones)
Aquí se consolidan métricas y verificaciones mediante operaciones **terminales** y conversiones:
- `toList()` / `collect()` para materializar resultados.
- `anyMatch()` para comprobaciones rápidas.
- `count()` para cuantificación de subconjuntos.
- `findFirst()` para búsquedas seguras con `Optional`.
- `reduce()` para agregaciones (sumatorios y acumulaciones).
- `mapToInt()` + `average()` para estadísticas optimizadas sobre primitivas.

Además, se incorporan operadores reutilizables con interfaces estándar (`Function`, `Supplier`, `UnaryOperator`, etc.) para evitar lógica duplicada y fomentar consistencia.

---

### Fase 4 — Validación visual (hipótesis + renderizado en consola)
La fase final conecta el análisis con una capa de salida orientada a interpretación:
- Se calculan métricas asociadas a hipótesis (medias, porcentajes, comparaciones).
- Se renderiza un **gráfico de barras en consola** escalado proporcionalmente.
- Se emite un veredicto claro: **VALIDADA / RECHAZADA**.

El objetivo es que los resultados sean **comunicables** sin herramientas externas: el informe se puede leer directamente en terminal.

---

## Quick Start (compilar y ejecutar)

> Ajusta el classpath/estructura según tu layout real (Maven/Gradle o proyecto plano). Si el repositorio usa estructura estándar `src`, estos comandos te servirán como punto de partida.

```bash
# Compilar (ejemplo para proyecto sin build tool, usando javac directamente)
mkdir -p out
javac -source 21 -target 21 -d out $(find src -name "*.java")

# Ejecutar clase principal
java -cp out LectorEMT
```

La ejecución incluye un **menú interactivo en consola** para seleccionar qué análisis mostrar, facilitando tanto la exploración como la presentación de resultados.

---

## Estructura del proyecto

```text
.
├─ data/
│  └─ demanda_emt_2025.csv
└─ src/
   ├─ DemandaEMT.java
   ├─ LectorEMT.java
   └─ AnalizadorEMT.java
```

- **`DemandaEMT.java`**: modelo inmutable de cada registro del CSV.
- **`LectorEMT.java`**: punto de entrada; carga el dataset y coordina la ejecución/menú.
- **`AnalizadorEMT.java`**: lógica funcional (operadores reutilizables + análisis con Streams).
- **`data/`**: dataset en CSV (EMT Madrid).

---

## Hipótesis y conclusiones (highlights)

A partir del dataset analizado, el sistema permite validar hipótesis de comportamiento de demanda con criterios medibles:

- **La demanda cae de forma notable en fines de semana** frente a días laborables, mostrando una diferencia clara en medias y una visualización inmediata en consola (barra proporcional + veredicto).
- **La demanda entre primera y segunda quincena es prácticamente equivalente**, lo que sugiere estabilidad mensual y rechaza diferencias significativas atribuibles únicamente al “efecto nómina” (según las métricas observadas).
- **Existe un porcentaje relevante de registros con demanda muy baja** (< 500 viajeros), lo que apunta a rutas/servicios con uso minoritario (potencialmente por cobertura, refuerzos específicos o baja eficiencia).

---

## Licencia

Este repositorio puede distribuirse bajo **MIT** (recomendado para Open Source) o una **licencia educativa** si aplica a la naturaleza académica del proyecto.

- Si vas a publicar como Open Source: crea un archivo `LICENSE` con MIT.
- Si necesitas restricciones académicas: añade una licencia educativa y acláralo aquí.

---

## Dataset

Los datos provienen del portal de datos abiertos de EMT Madrid (demanda diaria de viajeros). Revisa las condiciones de uso del proveedor y atribuye adecuadamente cuando corresponda.

---
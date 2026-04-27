> Información y contenido del proyecto de ampliación de programación.
# Fase 1: Selección y Validación del Dataset (30 mins)
> Se dividirá en 2 pasos:
> - **Buscar los datos:** Tengo que elegir un conjunto de datos abiertos del portal del Ayuntamiento de Madrid (como tráfico, calidad del aire o actividades culturales) en formato CSV o JSON.
> - **Validación:** Una vez lo tengo y sepa qué hipótesis quiero analizar, debo ponerme en contacto con la profesora. Es un trabajo individual y ellla debe corroborar que mi conjunto de datos es diferente al del resto de mis compañeros.

- [x] Elegir conjunto de datos: ✅ 2026-03-21
	Datos a Utilizar: [Demanda diaria Viajeros Autobus - Conjunto de datos - EMT data](https://datos.emtmadrid.es/dataset/demanda-diaria-viajeros-autobus)
	Usaré la información del año 2025 y en formato CSV
- [x] Confirmación de datos no duplicados por parte de la profesora ✅ 2026-03-22



---
# Fase 2: Lectura de datos y Modelado (2 horas)

- [x] Creación del proyecto y añadido del datos. ✅ 2026-03-22

> Se dividirá en 2 pasos:
> - Diseñar objetos Java inmutables (POJOs con campos finales) que representen la información del dataset.
> - Implementar la lectura del archivo seleccionado utilizando exclusivamente las clases del paquete.

## Paso 1: Diseñar objetos Java Inmutables
- Creación de `DemandaEMT`
	- [x] Añadido de datos del CSV (`LocalDate`, `String`, `int`) ✅ 2026-03-22
	- [x] Añadido del `Constructor` con todos los atributos, `getters` y `toString`. ✅ 2026-03-22

## Paso 2: Implementar la lectura
-  Creación de `LectorEMT` paso a paso
	- [x] Implementación de la ruta con paquete Java NIO ✅ 2026-03-22
	- [x] Uso de **try-with-resources** para leer el archivo y añadir excepciones ✅ 2026-03-22
		- [x] Añadir la Excepción con un mensaje. ✅ 2026-03-22
	- [x] Procesamos el Stream con `List` para encadenar el flujo de texto (lineas). ✅ 2026-03-22
	- [x] Ignoramos `.skip` la primera línea ya que es la cabecera. ✅ 2026-03-22
	- [x] Mapeamos `.map` para transformar cada Stream en un objeto `DemandaETM`. ✅ 2026-03-22
	- [x] Añadimos el separador `.split` que es "`;`" ✅ 2026-03-22
	- [x] Convertimos el texto a datos reales y guardamos el resultado en una lista. ✅ 2026-03-22
	- [x] Imprimimos y cargamos los resultados ✅ 2026-03-22



---
# Fase 3: Procesamiento Funcional con Streams (4 horas)
> Se dividirá en 3 pasos:
> - **- **[Interfaces Funcionales](app://obsidian.md/index.html#Paso%201%20Definir%20interfaces%20funcionales%20est%C3%A1ndar%20reutilizables):** Definiremos operadores reutilizables haciendo uso de las interfaces estándar de Java como `Predicate`, `Function`, `Consumer`, `UnaryOperator` y `Supplier`.
> - **Operaciones de Stream:** Aplicaremos operaciones intermedias (`filter`, `map`, `sorted`, etc.), terminales (`forEach`, `reduce`, `count`, etc.) y de conversión (`toList()`, `toArray()`).
> - **Referencias a métodos:** Mejoraremos la legibilidad del código utilizando referencias a métodos en las lambdas siempre que sea posible.


## Paso 1: Definir interfaces funcionales estándar reutilizables
> En lugar de escribir las condiciones directamente dentro de los Streams cada vez, la práctica requiere que <u>definir operadores reutilizables</u> utilizando **las interfaces estándar de Java**:

- [x] Crear nueva clase `AnalizadorEMT` y añadir los siguientes detalles:
	- Definición de interfaces funcionales: 
	- [x] `Predicate`: Filtrará los viajes que superen los 5000 pasajeros. ✅ 2026-04-12
	- [x] `Function`: Extrae el numero de viajeros usando una referencia al método. ✅ 2026-04-12
	- [x] `Consumer`: Imprime por consola usando una referencia a método. ✅ 2026-04-12
	- [x] `Supplier`: Devolver un viaje "vacío" o de "error" por si la lista estuviera vacía. ✅ 2026-04-12
	- [x] `UnaryOperator`: Simula como serían los datos si la demanda creciera un 20%. ✅ 2026-04-12


## Paso 2: Aplicar operaciones intermedias
> Las operaciones intermedias transforman o filtran el Stream y devuelven un nuevo Stream, lo que permite encadenarlas. El proyecto pide utilizar operaciones como `filter`, `map`, `sorted`, `distinct`, `limit`, y `skip`.

- [x] Crear nueva función **`mostrarTop5ViajesAltaDemanda`**: ✅ 2026-04-13
    - **Objetivo:** Identificar y mostrar los 5 viajes con mayor demanda.
    - **Operaciones utilizadas:**
		- [x] **`filter`:** Deja pasar solo los elementos que cumplen un `Predicate` (en este caso usámos el anterior `ALTA_DEMANDA`). ✅ 2026-04-13
		- [x] **`sorted`:** Ordena los elementos según un criterio. ✅ 2026-04-13
		- [x] **`limit`:** Trunca el Stream para quedarse solo con un número máximo de elementos
		- [x] `forEach`: Una operación terminal que consume (imprime) cada viaje, utilizando un `Consumer` (`IMPRIMIR_VIAJE`). ✅ 2026-04-13
    - [x] **Integración:** añadir los datos a `LectorEMT` para mostrar el **TOP 5 Viajes de Alta Demanda**. ✅ 2026-04-13

- [x] Crear nueva función **`simularAumentoPasajeros`**: ✅ 2026-04-20
    - **Objetivo:** Simular un aumento del 20% en los pasajeros para los 3 primeros registros de una línea específica.
    - **Operaciones utilizadas:**
        - [x] `filter`: Selecciona los viajes de la `lineaBuscada`. ✅ 2026-04-20
        - [x] `limit`: Limita el procesamiento a los 3 primeros registros encontrados. ✅ 2026-04-20
        - [x] `map`: Transforma los datos de los viajes, aplicando un `UnaryOperator` (`SIMULAR_CRECIMIENTO`) para simular el crecimiento de pasajeros. ✅ 2026-04-20
        - [x] `toList()`: Una operación terminal que convierte el Stream resultante en una nueva lista de `DemandaEMT`. ✅ 2026-04-20
    - [x] **Integración:** añadir los datos a `LectorEMT` para simular un **crecimiento del 20% en los 3 primeros registros de la línea**. ✅ 2026-04-13


> [!faq]+ ¿Qué significa el error de `jcmd` que aparece al ejecutar?
> `[0.504s][error][attach] failure (232) writing result of operation jcmd to pipe \\.\pipe\javatool-114911725`
> Es un "falso positivo" muy común en IntelliJ IDEA al usar versiones modernas de Java en Windows. Lo que pasa es que mi programa con Streams es tan rápido que termina de hacer los cálculos en tan poco (**0.504s**) que cuando IntelliJ intenta conectar su cable de diagnóstico mi programa ya ha terminado, por lo que IntelliJ indica un error diciendo "*no he podido conectarme*"


## Paso 3: Aplicar operaciones terminales o de conversión
> Un Stream no se ejecuta hasta que se invoca una operación terminal. Estas operaciones producen un resultado final (un número, un booleano, un objeto) o realizan una acción final.

- [x] `forEach`: Se usa para recorrer una colección o un stream y hacer una acción con cada elemento. ✅ 2026-04-20
	- [x] Aplicado **anteriormente** en `mostrarTop5ViajesAltaDemanda`.
- `toList` (forma moderna y recomendada desde JDK 16) y `collect` :Recoge todos los elementos del stream y te los devuelve empaquetados en una `List`. ✅ 2026-04-20
	- [x] Aplicado en Método 1 - `obtenerLineasUnicas`
	- [x] Aplicado en Método 6 - `obtenerViajesVaciosConCollect`
- `anyMatch`: Evalúa una condición y devuelve `true` en el instante que encuentra _un solo elemento_ que la cumpla. ✅ 2026-04-20
	- [x] Aplicado en Método 2 - `huboViajesVacios`.
- `count`: Sirve para saber cuántos elementos han sobrevivido a un filtro, devolviendo un `long`. ✅ 2026-04-20
	- [x] Aplicado en Método 3 - `contarViajesAltaDemanda`.
- `findFirst`: Se queda con el primer elemento que encuentre en el Stream y corta la ejecución (muy eficiente). Devuelve un `Optional` porque podría darse el caso de que no encuentre nada y así evitamos un error de programa. ✅ 2026-04-20
	- [x] Aplicado en Método 4 - `buscarPrimerViajeLinea`.
- `reduce`: Acumula los elementos del Stream aplicando una operación matemática para combinarlos en un único resultado. Es el rey para hacer sumatorios. ✅ 2026-04-20
	- [x] Aplicado en Método 5 - `obtenerLineasUnicas`.
- `Supplier`: Se usa cuando quieres generar u obtener un dato “bajo demanda” , por ejemplo crear un valor por defecto, aplazar una creación costosa hasta que realmente haga falta, pasarlo como estrategia de construcción. ✅ 2026-04-20
	- [x] Aplicado en Método 7 - `obtenerViajeSeguro`


---
# Fase 4: Análisis, Resultados y Validación (5 horas)
> Se dividirá en **3 pasos por estadística** en los que:
> - Exploraré estadísticas y filtraré los datos para validar la hipótesis inicial o responder a las preguntas que te hayas planteado sobre el dataset.
> - Generaré informes o visualizaciones sencillos por consola para mostrar estos resultados.
> - Deberé de usar las herrmaientas de la [[#Fase 3 Procesamiento Funcional con Streams (4 horas)|Fase 3]] para responder a preguntas de los resultados. Al combinar las interfaces funcionales con las operaciones de Stream, se pueden resolver cuestiones complejas en pocas líneas de código.


## Estadística 1: Demanda Fines de semana VS Laborables

> [!important]+ Estadistica a Comprobar
> "La demanda de autobuses de la EMT disminuye considerablemente los fines de semana en comparación con los días laborables."

### **Paso 1: Exploración y Estadísticas**
**Pasos a Realizar:**
- *Calcular la media de pasajeros de Lunes (1) a Viernes (5)* 
	- [x] **Crear el flujo de datos:** Convertir la lista `datos` en un `stream()` para procesar los objetos `DemandaEMT` uno a uno. ✅ 2026-04-21
	- [x] **Filtrar por días laborables:** Aplicar un `.filter()` utilizando la fecha del objeto. Se extrae el día de la semana (`getDayOfWeek()`) y se comprueba que su valor numérico sea menor o igual a 5 (Lunes a Viernes). ✅ 2026-04-21
	- [x] **Extraer el dato numérico:** usar un conversor (`mapToInt`) para realizar operaciones matemáticas optimizadas. ✅ 2026-04-21
	- [x] **Calcular el promedio:** Invocar la operación terminal `.average()`. Esta operación tiene que devolver un `OptionalDouble` porque el stream podría estar vacío. ✅ 2026-04-21
	- [x] **Gestionar el resultado nulo:** para asegurar que, si no hay datos que cumplan el filtro, el método devuelva un valor por defecto (en este caso, `0.0`) en lugar de un error. ✅ 2026-04-21

- *Calcular la media de pasajeros de Sábado (6) y Domingo (7)* 
    - [x] **Crear el flujo de datos:** Convertir la lista `datos` en un `stream()` para procesar los objetos `DemandaEMT` uno a uno. ✅ 2026-04-21
    - [x] **Filtrar por días de fin de semana:** Aplicar un `.filter()` utilizando la fecha del objeto. Se extrae el día de la semana (`getDayOfWeek()`) y se comprueba que su valor numérico sea mayor o igual a 6 (Sábado y Domingo). ✅ 2026-04-21
    - [x] **Extraer el dato numérico:** usar un conversor (`mapToInt`) para extraer el total de viajeros (`getTotalViajeros`) y realizar operaciones matemáticas optimizadas. ✅ 2026-04-21
    - [x] **Calcular el promedio:** Invocar la operación terminal `.average()`. Esta operación tiene que devolver un `OptionalDouble` porque el stream podría estar vacío. ✅ 2026-04-21
    - [x] **Gestionar el resultado nulo:** para asegurar que, si no hay datos que cumplan el filtro, el método devuelva un valor por defecto (en este caso, `0.0`) en lugar de un error. ✅ 2026-04-21

### **Paso 2: Visualización e Informes**
**Pasos a Realizar:**
- [x] **Obtener medias desde el Analizador:** Invocar los métodos `mediaLaborables` y `mediaFinesDeSemana` de la clase `AnalizadorEMT` para obtener los valores `mediaLab` y `mediaFin`. ✅ 2026-04-22
- [x] **Indicar inicio del informe:** Imprimir el encabezado de la hipótesis sobre la disminución de demanda. ✅ 2026-04-22
- [x] **Calcular valor máximo para escalado:** Determinar `maxH1` comparando ambas medias para que el gráfico sea proporcional. ✅ 2026-04-22

- **Implementar método auxiliar `imprimirBarraConsola`:** Crear un método estático para centralizar la lógica del gráfico:
    - [x] **Definir límite de caracteres:** Establecer un `limiteCaracteres` de 40 para el ancho de la barra. ✅ 2026-04-22
    - [x] **Calcular longitud proporcional:** Realizar el cálculo `(int) ((valor / maximo) * limiteCaracteres)` para obtener el tamaño de la barra. ✅ 2026-04-22
    - [x] **Generar la cadena visual:** Utilizar el método `.repeat()` sobre el carácter "█" asegurando al menos un bloque visible. ✅ 2026-04-22
    - [x] **Imprimir con formato:** Usar `printf` con el formato `%-12s | %s (%d)\n` para alinear perfectamente las etiquetas y los valores redondeados. ✅ 2026-04-22

- [x] **Evaluar y mostrar resultado de la hipótesis:** Realizar la comparación lógica final para imprimir si la hipótesis es `VALIDADA` o `RECHAZADA`. ✅ 2026-04-22

### **Paso 3: Gráfico Visual + Conclusiones**
``` Java
--- HIPÓTESIS 1: El uso de autobuses disminuye drásticamente los fines de semana.
Laborables   | ████████████████████████████████████████ (7025)
Fin de Sem.  | ██████████████████████ (3867)
✅ VALIDADA: La demanda cae durante el fin de semana.
```

Al observar el gráfico generado por el método `imprimirBarraConsola`, se pueden extraer las <u>siguientes conclusiones técnicas y estadísticas</u>:

- **Reducción Significativa de la Demanda:** Existe una caída drástica en el uso del transporte. Numéricamente, la demanda en *fines de semana* (3867) es aproximadamente un **55%** de la demanda en *días laborables* (7025). Esto representa una <u>disminución de casi el 45%</u>.
- **Proporcionalidad Visual:** La barra de *días Laborables* actúa como el **100% de la escala** (ocupando el `limiteCaracteres`). La barra del *Fin de Semana* ocupa visualmente poco más de la mitad (22 bloques), lo que <u>permite apreciar la diferencia de un solo vistazo sin necesidad de analizar los números</u>.
- **Validación de la Hipótesis:** El flujo de control `if (mediaLab > mediaFin)` confirma estadísticamente lo que la gráfica sugiere: <u>la hipótesis</u> de que la demanda disminuye drásticamente los fines de semana <u>es correcta y queda validada</u>.
- **Interpretación del Comportamiento:** Este resultado refleja el comportamiento de los viajeros, que está fuertemente ligado a las rutinas laborales y académicas de lunes a viernes, reduciéndose a viajes de ocio o servicios mínimos durante el sábado y domingo. España lo levantamos los pobr... no he dicho nada 🤐.


## Estadística 2: Demanda la Primera vs Segunda Quincena

> [!important]+ Estadistica a Comprobar
> "Se viaja más durante la primera quincena del mes debido a la disponibilidad de ingresos (cobro de nóminas/pensiones) que incentiva el movimiento."

### **Paso 1: Exploración y Estadísticas**
**Pasos a Realizar:**
- [x] **Crear el flujo de datos:** Convertir la lista `datos` en un `stream()` para procesar los registros de `DemandaEMT`. ✅ 2026-04-23
- **Segmentar por días del mes:**
    - [x] **Primera Quincena:** Aplicar un `.filter()` utilizando `getDayOfMonth() <= 15` para capturar los datos del inicio de mes. ✅ 2026-04-23
    - [x] **Segunda Quincena:** Aplicar un `.filter()` utilizando `getDayOfMonth() > 15` para capturar los datos del resto del mes. ✅ 2026-04-23
- [x] **Extraer flujo numérico:** Utilizar `.mapToInt(DemandaEMT::getTotalViajeros)` para transformar el Stream de objetos en un flujo de valores enteros (pasajeros). ✅ 2026-04-23
- [x] **Calcular promedio estadístico:** Invocar la operación terminal `.average()` que calcula automáticamente la media aritmética de los viajeros filtrados. ✅ 2026-04-23
- [x] **Gestionar flujos vacíos:** Implementar `.orElse(0.0)` para garantizar que el método devuelva un valor seguro en caso de que no existan datos para el periodo filtrado. ✅ 2026-04-23

### **Paso 2: Visualización e Informes**
**Pasos a Realizar:**
- [x] **Recuperar cálculos del Analizador:** Obtener los valores de `mediaQ1` y `mediaQ2` invocando los métodos correspondientes de la instancia `analizador`. ✅ 2026-04-23
- [x] **Presentar la hipótesis:** Imprimir en consola el enunciado de la Hipótesis 2 para contextualizar los resultados. ✅ 2026-04-23
- [x] **Calcular factor de escala:** Determinar `maxH2` comparando ambas medias para asegurar que la barra más larga represente el 100% del ancho visual. ✅ 2026-04-23

-  **Representación gráfica:**
    - [x] Llamar a `imprimirBarraConsola` para la "1ª Quincena". ✅ 2026-04-23
    - [x] Llamar a `imprimirBarraConsola` para la "2ª Quincena". ✅ 2026-04-23

- **Lógica de validación:** Implementar el condicional `if (mediaQ1 > mediaQ2)` para imprimir el veredicto final:
    - [x] Mostrar mensaje de éxito si la primera quincena es superior. ✅ 2026-04-23
    - [x] Mostrar mensaje de rechazo si la segunda quincena es igual o superior. ✅ 2026-04-23

### **Paso 3: Gráfico Visual + Conclusiones**
``` Java
--- HIPÓTESIS 2: Se viaja más en la primera quincena del mes.
1ª Quincena  | ███████████████████████████████████████ (6155)
2ª Quincena  | ████████████████████████████████████████ (6159)
❌ RECHAZADA: La segunda mitad del mes registra más o igual demanda.
```

Al observar el gráfico impreso para la segunda hipótesis, se pueden extraer las siguientes conclusiones técnicas y estadísticas:

- **Diferencia Mínima en la Demanda:** Contrariamente a la hipótesis, la demanda media en la segunda quincena (6159 viajeros) es ligeramente superior a la de la primera quincena (6155 viajeros). La diferencia es de tan solo **4 viajeros** en promedio, lo que es estadísticamente insignificante para validar la hipótesis en el sentido propuesto.
- **Proporcionalidad Visual Engañosa (por la mínima diferencia):** Aunque la barra de la "2ª Quincena" es ligeramente más larga (ocupando los 40 caracteres al ser el `maxH2`), la diferencia visual entre ambas barras es casi imperceptible. Esto demuestra que, si bien el método `imprimirBarraConsola` es preciso, una diferencia tan pequeña no se traduce en una distinción visual clara, lo que podría llevar a una interpretación errónea si solo se observara el gráfico sin los valores numéricos.
- **Rechazo de la Hipótesis:** La condición `if (mediaQ1 > mediaQ2)` es la que determina la validación. Dado que `6155` no es mayor que `6159`, la hipótesis es **rechazada**. Esto indica que la premisa de que "se viaja más en la primera quincena del mes debido a la disponibilidad de ingresos" no se sostiene con los datos analizados.
- **Interpretación del Comportamiento:** El rechazo de la hipótesis sugiere que el factor de la "disponibilidad de ingresos" (cobro de nóminas/pensiones) no tiene un impacto significativo o directo en el uso del transporte público en Madrid a lo largo del mes. La demanda se mantiene relativamente estable entre ambas quincenas, lo que podría indicar que otros factores (como las rutinas diarias, el clima, eventos específicos, etc.) tienen un peso mayor o que el impacto de los ingresos se distribuye de manera más uniforme a lo largo del mes.


## Estadística 3: Demanda en Líneas Minoritarias

> [!important]+ Estadistica a Comprobar
> "Más del 5% de los registros diarios de la EMT mueven menos de 500 pasajeros, indicando rutas con baja eficiencia o servicios de refuerzo muy específicos."

### **Paso 1: Exploración y Estadísticas**
**Pasos a Realizar:**
- [x] **Crear el flujo de datos:** Convertir la lista `datos` en un `stream()` para procesar los objetos `DemandaEMT`. ✅ 2026-04-23
- **Definición y filtrado de "Viaje Fantasma":**
    - [x] Aplicar un `.filter()` para seleccionar los viajes donde `getTotalViajeros() < 500`. ✅ 2026-04-23
- [x] **Conteo de viajes filtrados:** Utilizar la operación terminal `.count()` para obtener el número de estos registros. ✅ 2026-04-23
- [x] **Cálculo de representatividad:** Calcular el porcentaje de `viajesFantasma` sobre el total de registros (`viajes.size()`) mediante la fórmula `(double) viajesFantasma / viajes.size() * 100`. ✅ 2026-04-23

### **Paso 2: Visualización e Informes**
**Pasos a Realizar:**
- [x] **Presentar la hipótesis:** Imprimir en consola el enunciado de la Hipótesis 3 para contextualizar los resultados. ✅ 2026-04-23

- **Mostrar datos clave:**
    - [x] Imprimir el total de viajes analizados (`viajes.size()`). ✅ 2026-04-23
    - [x] Imprimir el conteo de "viajes fantasma" (`viajesFantasma`) y su porcentaje (`porcentajeFantasma`) formateado a dos decimales usando `printf`. ✅ 2026-04-23

-  **Lógica de validación:** Implementar el condicional `if (porcentajeFantasma > 5.0)` para determinar el veredicto final:
    - [x] Mostrar mensaje de validación si el porcentaje supera el 5%. ✅ 2026-04-23
    - [x] Mostrar mensaje de rechazo si el porcentaje es igual o inferior al 5%. ✅ 2026-04-23

### **Paso 3: Gráfico Visual + Conclusiones**
``` Java
--- HIPÓTESIS 3: Más del 5% de los registros diarios de la EMT mueven menos de 500 pasajeros.
-> Viajes totales analizados: 83169
-> Viajes con < 500 pasajeros: 8351 (10,04% del total)
✅ VALIDADA: Existe un volumen significativo de rutas con demanda extremadamente baja.
```

Al observar el resultado generado para la tercera hipótesis, se pueden extraer las siguientes conclusiones técnicas y estadísticas:

- **Validación Directa de la Hipótesis:** La hipótesis "Más del 5% de los registros diarios de la EMT mueven menos de 500 pasajeros" ha sido **VALIDADA**.
- **Superación del Umbral:** El porcentaje calculado (**10,04% del total**) es significativamente mayor que el umbral del 5% establecido en la hipótesis (`if (porcentajeFantasma > 5.0)`). Esta clara superación del umbral es lo que lleva a la validación. Los datos muestran que de un total de **83169 viajes analizados**, **8351** de ellos registraron menos de 500 pasajeros.
- **Implicaciones de Eficiencia y Servicio:** La validación de esta hipótesis sugiere que una parte considerable de las operaciones diarias de la EMT corresponden a rutas o servicios con una demanda muy baja. Esto podría indicar:
    - **Rutas con baja eficiencia:** Posibles líneas que no están siendo utilizadas al máximo de su capacidad.
    - **Servicios específicos:** Rutas que atienden a zonas muy concretas, horarios de baja afluencia, o servicios de refuerzo que por su naturaleza no esperan una gran cantidad de pasajeros.
    - **Potencial de optimización:** Podría ser un punto de partida para un análisis más profundo sobre la viabilidad o la necesidad de ajustar estas rutas, o para entender mejor el propósito de estos "viajes fantasma".


# Añadidos Extra
He mejorado muchos campos del documento, como es:
- [x] Mejorado de las aclaraciones de los divisiones de las fases. ✅ 2026-04-26
- [x] Añadido de Pasos en la Fase 2. ✅ 2026-04-26
- [x] Añadido de fechas en muchos puntos faltantes. ✅ 2026-04-26
- [x] Añadido de segundo método sin mencionar en la Fase 3, Punto 2. ✅ 2026-04-26
- [x] Organización y mejor estructuración en Paso 3. ✅ 2026-04-26
- [x] Añadir un commit con cases para escribir que tipo de datos quieres sacar en pantalla según la fase. ✅ 2026-04-27
- [x] Añadir documentación (este documento) + README basándome en este. ✅ 2026-04-27


---
# Fase 5: Documentación y Presentación (6 horas)
> Se dividirá en 2 apartados que tendré que realizar externamente:
> - **Memoria:** Redactaremos un documento de máximo 20 hojas detallando la elección del dataset, el uso de `java.nio`, las ventajas de las clases inmutables, el uso de lambdas/streams y el significado de los resultados obtenidos.
> - **Defensa oral:** Prepararemos la presentación necesaria para el día de la exposición, donde defenderás el diseño y la solución técnica.

## Memoria (Entregable)
> Mi memoria está realizada mientras trabajaba en el proyecto (de ahí las fechas de trabajo), sin embargo traspasaré toda la información (a excepción de la Fase 5) a un documento en **Canva** por su facilidad, haciéndolo más organizado, legible y evitando todo tipo de errores.

- [x] Añadido de Documentación y Herramientas Utilizadas en el Proyecto. ✅ 2026-04-26
- [x] Añadido de Índice con todos los apartados. ✅ 2026-04-26
- [x] Añadir Titular y mejorar Apartados ✅ 2026-04-26
- [x] Mejorar el Diseño ✅ 2026-04-26

## Presentación para Defensa Oral (Entregable)
> He decidido realizar esta presentación mediante **Canva** y usaré un guion para recordar partes destacables que son importantes recordar.

- [x] Realización de la Presentación ✅ 2026-04-28
- [x] Realización del Guion ✅ 2026-04-28
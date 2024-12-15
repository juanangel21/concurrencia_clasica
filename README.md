# Documentación del Problema de la Cena de los Filósofos

## Descripción de los mecanismos de sincronización empleados
Se utiliza el mecanismo de **Semáforos** de la biblioteca `java.util.concurrent.Semaphore` para sincronizar el acceso a los tenedores. Cada tenedor está representado por un semáforo binario, lo que asegura que solo un filósofo pueda tomar un tenedor en un momento dado. Los métodos `acquire()` y `release()` del semáforo controlan la adquisición y liberación de los recursos compartidos.

Adicionalmente, se emplea un límite de ciclos para cada hilo (filósofo), lo que garantiza que el programa termine tras un número definido de iteraciones.

---

## Lógica de negocio o línea de pensamiento para arreglar el problema
El problema clásico de la Cena de los Filósofos plantea posibles situaciones de **deadlock** y ejecución indefinida. La solución actual emplea las siguientes estrategias:

1. **Uso de Semáforos Binarios**:
    - Cada tenedor está protegido por un semáforo binario que limita el acceso concurrente a un solo filósofo.

2. **Límite de Ejecución**:
    - Cada filósofo tiene un límite definido de ciclos (`limiteCiclos`), lo que asegura que el programa termine tras completar las iteraciones definidas.

3. **Evitación de Deadlock**:
    - Los filósofos adquieren los tenedores en orden secuencial: primero el izquierdo y luego el derecho.
    - En caso de necesitar una mejora adicional, podría implementarse una asimetría: el último filósofo podría tomar primero el tenedor derecho y luego el izquierdo.

4. **Starvation**:
    - Si bien esta implementación no aborda directamente el problema de starvation (que puede ocurrir si un filósofo no puede acceder a los recursos durante mucho tiempo), éste podría mitigarse con técnicas adicionales, como asignar prioridades a los recursos.

---

## Identificación del estado compartido
### Variables y estructuras globales:
1. **Tenedores**:
    - Cada tenedor es un objeto de la clase `Tenedor` con un semáforo binario interno que gestiona su estado (ocupado o libre).

2. **Lista de Filósofos**:
    - Cada filósofo es un hilo independiente que interactúa con los tenedores compartidos y tiene su propio límite de ciclos (`limiteCiclos`).

3. **Recursos Compartidos**:
    - Los tenedores son los recursos compartidos que los hilos intentan adquirir simultáneamente.

---

## Descripción algorítmica del avance de cada hilo/proceso
A continuación se describe el comportamiento de un filósofo:

1. **Pensar**:
    - El filósofo "piensa" durante un tiempo aleatorio antes de intentar comer.
    - Esta acción no interactúa con los recursos compartidos.

2. **Adquirir Recursos**:
    - Toma el tenedor izquierdo usando el semáforo asociado (llamada a `acquire()`).
    - Luego, intenta tomar el tenedor derecho (llamada a `acquire()`).

3. **Comer**:
    - Una vez que el filósofo tiene ambos tenedores, "come" durante un tiempo aleatorio.

4. **Liberar Recursos**:
    - Suelta el tenedor derecho primero (`release()`).
    - Luego, suelta el tenedor izquierdo (`release()`).

5. **Finalización del Ciclo**:
    - Repite este ciclo hasta que alcance el número definido de iteraciones (`limiteCiclos`).
    - Una vez completado, el hilo termina su ejecución.

---

## Descripción de la interacción entre hilos/procesos
La interacción ocurre principalmente a través de los **tenedores compartidos**. Los semáforos garantizan que:

- Solo un filósofo pueda tomar un tenedor a la vez.
- Los tenedores sean liberados adecuadamente después de ser usados, permitiendo que otros filósofos los adquieran.

Adicionalmente, el límite de ciclos asegura que cada filósofo complete un número finito de iteraciones, evitando una ejecución infinita. Al finalizar, los hilos no interactúan más con los recursos compartidos.

---

## Descripción del entorno de desarrollo

### Lenguaje y versión
- **Lenguaje**: Java
- **Versión**: JDK 22

### Bibliotecas necesarias
- Se utiliza la biblioteca estándar de Java (`java.util.concurrent`).
- No se requiere ninguna biblioteca adicional.

### Sistema operativo
- Fue ejecutado en Windows 11 y macOS.

### Entorno de desarrollo
- **IDE utilizado**: IntelliJ IDEA
- **Configuración del proyecto**: Proyecto de tipo "Java Application" configurado con el JDK 22.

### Ejemplo de Ejecución exitosa

![Inicio de la Ejecucion](./capturas/img.png)

![Finalizacion](./capturas/img_1.png)

---


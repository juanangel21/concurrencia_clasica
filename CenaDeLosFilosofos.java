import java.util.concurrent.Semaphore;

class Tenedor {
    private final Semaphore semaforo = new Semaphore(1); // Semáforo binario (mutex)

    public void tomar() throws InterruptedException {
        semaforo.acquire(); // Adquirir el tenedor
    }

    public void soltar() {
        semaforo.release(); // Liberar el tenedor
    }
}

class Filosofo implements Runnable {
    private final int id;
    private final Tenedor tenedorIzquierdo;
    private final Tenedor tenedorDerecho;
    private final int limiteCiclos;

    public Filosofo(int id, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, int limiteCiclos) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.limiteCiclos = limiteCiclos;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 1000)); // Simula el tiempo de pensamiento
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comiendo.");
        Thread.sleep((long) (Math.random() * 1000)); // Simula el tiempo de comer
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < limiteCiclos; i++) { // Limita la cantidad de ciclos
                pensar(); // Piensa antes de intentar comer
                System.out.println("Filósofo " + id + " intenta tomar el tenedor izquierdo.");
                tenedorIzquierdo.tomar(); // Adquiere el tenedor izquierdo
                System.out.println("Filósofo " + id + " tomó el tenedor izquierdo.");

                System.out.println("Filósofo " + id + " intenta tomar el tenedor derecho.");
                tenedorDerecho.tomar(); // Adquiere el tenedor derecho
                System.out.println("Filósofo " + id + " tomó el tenedor derecho.");

                comer(); // Come cuando tiene ambos tenedores

                tenedorDerecho.soltar(); // Libera el tenedor derecho
                System.out.println("Filósofo " + id + " soltó el tenedor derecho.");

                tenedorIzquierdo.soltar(); // Libera el tenedor izquierdo
                System.out.println("Filósofo " + id + " soltó el tenedor izquierdo.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class CenaDeLosFilosofos {
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        final int LIMITE_CICLOS = 10; // Límite de ciclos para cada filósofo
        Tenedor[] tenedores = new Tenedor[NUM_FILOSOFOS];
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
        Thread[] hilos = new Thread[NUM_FILOSOFOS];

        // Crear tenedores
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            tenedores[i] = new Tenedor();
        }

        // Crear filósofos y asignar tenedores
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            // Cada filósofo tiene un tenedor izquierdo y uno derecho
            Tenedor tenedorIzquierdo = tenedores[i];
            Tenedor tenedorDerecho = tenedores[(i + 1) % NUM_FILOSOFOS];
            filosofos[i] = new Filosofo(i, tenedorIzquierdo, tenedorDerecho, LIMITE_CICLOS);
            hilos[i] = new Thread(filosofos[i], "Filósofo " + i);
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("La cena ha terminado.");
    }
}

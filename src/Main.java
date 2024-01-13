import java.util.Random;
import java.util.Scanner;

class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

class ValorInvalidoException extends Exception {
    public ValorInvalidoException(String mensaje) {
        super(mensaje);
    }
}

class WalletNoGeneradaException extends Exception {
    public WalletNoGeneradaException(String mensaje) {
        super(mensaje);
    }
}

class TransferenciaFallidaException extends Exception {
    public TransferenciaFallidaException(String mensaje) {
        super(mensaje);
    }
}

public class Main {
    private static double saldoBitcoin = 0.0;
    private static String walletAddress = null;
    private static final double FEE_PORCENTAJE = 0.015; // 1.5% de comisión
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menú de Opciones:");
            System.out.println("1. Generar Wallet");
            System.out.println("2. Mostrar Saldo");
            System.out.println("3. Minar Bitcoins");
            System.out.println("4. Transferir Bitcoins");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        generarWallet();
                        break;
                    case 2:
                        mostrarSaldo();
                        break;
                    case 3:
                        minarBitcoins();
                        break;
                    case 4:
                        transferirBitcoins(scanner);
                        break;
                    case 5:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
            } catch (SaldoInsuficienteException | ValorInvalidoException | WalletNoGeneradaException |
                     TransferenciaFallidaException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void generarWallet() {
        walletAddress = "b1cqrt3" + random.nextInt(1000000);
        System.out.println("Wallet generada: " + walletAddress);
    }

    private static void mostrarSaldo() throws WalletNoGeneradaException {
        if (walletAddress == null) {
            throw new WalletNoGeneradaException("Debe generar una wallet antes de consultar el saldo.");
        }
        System.out.println("Saldo de Bitcoin: " + saldoBitcoin + " BTC");
    }

    private static void minarBitcoins() throws WalletNoGeneradaException {
        if (walletAddress == null) {
            throw new WalletNoGeneradaException("Debe generar una wallet antes de minar bitcoins.");
        }
        double bitcoinsMinados = 0.1 + (0.5 - 0.1) * random.nextDouble();
        saldoBitcoin += bitcoinsMinados;
        System.out.println("Has minado " + bitcoinsMinados + " Bitcoins.");
    }

    private static void transferirBitcoins(Scanner scanner) throws SaldoInsuficienteException, ValorInvalidoException, WalletNoGeneradaException, TransferenciaFallidaException {
        if (walletAddress == null) {
            throw new WalletNoGeneradaException("Debe generar una wallet antes de transferir bitcoins.");
        }
        System.out.print("Ingrese la wallet de destino: ");
        String walletDestino = scanner.nextLine();
        System.out.print("Ingrese el monto de Bitcoin a transferir: ");
        double monto = Double.parseDouble(scanner.nextLine());

        if (monto <= 0) {
            throw new ValorInvalidoException("El monto a transferir debe ser mayor a cero.");
        }

        double fee = monto * FEE_PORCENTAJE;
        double montoTotal = monto + fee;

        if (montoTotal > saldoBitcoin) {
            throw new SaldoInsuficienteException("Fondos insuficientes para realizar la transferencia incluyendo la comisión.");
        }

        System.out.println("Procesando en la red...");

        if (!procesoDeTransferencia()) {
            throw new TransferenciaFallidaException("Proceso fallido: en este momento, la red está muy congestionada.");
        }

        saldoBitcoin -= montoTotal;
        System.out.println("Transferencia de " + monto + " Bitcoins realizada con éxito a la wallet " + walletDestino + ".");
        System.out.println("Comisión de la transacción: " + fee + " BTC. Su nuevo saldo es: " + saldoBitcoin + " BTC");
    }
    private static boolean procesoDeTransferencia() {

        return random.nextBoolean();
    }
}

import data.session.GuestSession;
import processing.FinancialCalculatorCore;
import data.database.DAO;
import data.results.IRRResult;
import data.results.IRRResultPrecise;
import data.results.NPVResult;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestMain {
    static Scanner input = new Scanner(System.in);
    private static GuestSession session;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        greetings();
        createSession();
        choose();
        exit();
    }

    private static void greetings() {
        println("-------------------------------------------------------------\n" +
                "                                                              \n" +
                "(_, /? [- [- ~|~ | |\\| (_, _\\~   |^ /? () /= [- _\\~ _\\~ () /? \n" +
                "                                                              \n" +
                "-------------------------------------------------------------");
    }

    private static void createSession() {
        println("Cómo te llamas?");
        String name = askString();
        session = new GuestSession(name);

        println("Hola " + name + "      " + session.getStartTime());
    }

    private static void choose() {
        ArrayDeque<String> rawDataHolder;
        ArrayDeque<Double> cashFlow = null;
        boolean populated = false;

        try {
            while (true) {
                rawDataHolder = initRawDataHeader();

                if (!populated) {
                    cashFlow = populateArrayDeque();
                    populated = true;
                }

                printOptions();

                switch (askNumber().intValue()) {
                    case 1:
                        option1(cashFlow, rawDataHolder);
                        break;
                    case 2:
                        option2(cashFlow, rawDataHolder);
                        break;
                    case 3:
                        option3(cashFlow, rawDataHolder);
                        break;
                    case 4:
                        option4(cashFlow, rawDataHolder);
                        break;
                    case 5:
                        option5(cashFlow);
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8(DAO.getInstance(session.getFilePath()).readDataTableCSV());
                        break;
                    default:
                        println("Número no reconocido, saliendo el programa");
                        return;

                }

                println("Desea introducir de nuevo los flujos de caja? S/N");
                if (input.nextLine().equalsIgnoreCase("S")) {
                    populated = false;
                }
            }
        } catch (NumberFormatException e) {
            println("Hasta luego ;)");
        }


    }

    private static void printOptions() {
        println("Elige una opción por favor: ");
        println("1 - Calcular VAN");
        println("2 - Calcular TIR Schneider");
        println("3 - Calcular TIR Bolzano");
        println("4 - Calcular TIR Bolzano Preciso");
        println("5 - Comparar tres tipos de TIR");
        println("6 - Guardar mis resultados siguientes");
        println("7 - No guardar mis resultados siguientes");
        println("8 - Leer mis resultados");
        println("------------------------------------------------------");
        println("Cualquier carácter no numérico para salir");
    }

    private static void option8(List<String[]> dataTable) {
        for (String[] row : dataTable) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void option7() {
        session.setGuestPersistenceStatus(false);
        println("Listo! Todo configurado " + session.getUserName() + " persistencia: " + session.isGuestPersistenceStatus());
    }

    private static void option6() {
        session.setGuestPersistenceStatus(true);
        println("Listo! Todo configurado " + session.getUserName() + " persistencia: " + session.isGuestPersistenceStatus());
    }

    private static void option5(ArrayDeque<Double> cashFlow) {
        println("Schneider: " + FinancialCalculatorCore.getIRRSchneider(cashFlow));
        println("TIR Bolzano: " + FinancialCalculatorCore.getIRRBolzano(cashFlow));
        println("TIR Bolzano Preciso: " + FinancialCalculatorCore.getPreciseIRRBolzano(cashFlow));
    }

    private static void option4(ArrayDeque<Double> cashFlow, ArrayDeque<String> rawDataHolder) {
        double result = FinancialCalculatorCore.getPreciseIRRBolzano(cashFlow);
        println("El resultado TIR Bolzano Preciso buscado es: " + result);

        rawDataHolder.add("TIR Extreme precision");
        rawDataHolder.add(String.valueOf(result));
        rawDataHolder.addAll(cashFlow.stream().map(String::valueOf).collect(Collectors.toCollection(ArrayDeque<String>::new)));
        IRRResultPrecise irrResultPrecise = new IRRResultPrecise(rawDataHolder);
        session.shallPersist(irrResultPrecise.getRawData());
    }

    private static void option3(ArrayDeque<Double> cashFlow, ArrayDeque<String> rawDataHolder) {
        double result = FinancialCalculatorCore.getIRRBolzano(cashFlow);
        println("El resultado TIR Bolzano buscado es: " + result);

        rawDataHolder.add("TIR Standard precision");
        rawDataHolder.add(String.valueOf(result));
        rawDataHolder.addAll(cashFlow.stream().map(String::valueOf).collect(Collectors.toCollection(ArrayDeque<String>::new)));
        IRRResult irrResult2 = new IRRResult(rawDataHolder);
        session.shallPersist(irrResult2.getRawData());
    }

    private static void option2(ArrayDeque<Double> cashFlow, ArrayDeque<String> rawDataHolder) {
        double result = FinancialCalculatorCore.getIRRSchneider(cashFlow);
        println("El resultado TIR Schneider buscado es: " + result);

        rawDataHolder.add("TIR Schneider");
        rawDataHolder.add(String.valueOf(result));
        rawDataHolder.addAll(cashFlow.stream().map(String::valueOf).collect(Collectors.toCollection(ArrayDeque<String>::new)));
        IRRResult irrResult = new IRRResult(rawDataHolder);
        session.shallPersist(irrResult.getRawData());
    }

    private static void option1(ArrayDeque<Double> cashFlow, ArrayDeque<String> rawDataHolder) {
        println("Introduce la tasa de descuento por favor: ");

        Double discount = askNumber();
        double result = FinancialCalculatorCore.getNPV(cashFlow, discount);

        println("El resultado VAN buscado es: " + result);


        rawDataHolder.add("VAN");
        rawDataHolder.add(String.valueOf(result));
        rawDataHolder.add(String.valueOf(discount));
        rawDataHolder.addAll(cashFlow.stream().map(String::valueOf).collect(Collectors.toCollection(ArrayDeque<String>::new)));
        NPVResult npvResult = new NPVResult(rawDataHolder);
        session.shallPersist(npvResult.getRawData());
    }

    private static ArrayDeque<String> initRawDataHeader() {
        ArrayDeque<String> tmp = new ArrayDeque<>();
        tmp.add(session.getUserName());
        return tmp;
    }


    private static void exit() {
        session.closeSession();
        println("");
        println(session.getUserName() + " estuvo aquí del " +
                session.getStartTime() + " a " + session.getEndTime());
        println("-------------------------------------------------------------------------");
        println("Bye ;)");
    }

    public static ArrayDeque<Double> populateArrayDeque() {
        var values = new ArrayDeque<Double>();

        println("");
        println("Por favor introduzca los valores a analizar, el primero es \n" +
                "la inversión inicial, los siguientes son los correspondientes \n" +
                "flujos de caja de cada año: ");

        while (true) {
            println("Listo para recibir valor, para parar, 'stop': ");
            String str = input.nextLine();
            if (str.contentEquals("stop")) {
                break;
            }
            values.add(Double.valueOf(str));
        }
        println("Has introducido: ");
        values.forEach(TestMain::println);
        return values;
    }

    public static Double askNumber() throws NumberFormatException {
        return Double.valueOf(input.nextLine());
    }

    public static String askString() {
        return input.nextLine();
    }

    public static <T> void println(T msg) {
        System.out.println(msg);
    }

    public static <T> void print(T msg) {
        System.out.print(msg);
    }
}

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.JSONObject;
import java.util.Scanner;

public class Main {

    private static final String API_KEY = "a7cda5a4f84aebcc147386bb";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Conversor de divisas");
        System.out.println("1. Dólares a Pesos Argentinos");
        System.out.println("2. Reales Brasileños a Dólares");
        System.out.println("Elige una opción:");

        int opcion = scanner.nextInt(); // Leer la opción elegida
        System.out.print("Ingresa la cantidad que deseas convertir: ");
        double cantidad = scanner.nextDouble();

        if (opcion == 1) {
            double resultado = convertir("USD", "ARS", cantidad);
            System.out.println(cantidad + " dólares equivalen a " + resultado + " pesos argentinos.");
        } else if (opcion == 2) {
            double resultado = convertir("BRL", "USD", cantidad);
            System.out.println(cantidad + " reales brasileños equivalen a " + resultado + " dólares.");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public static double convertir(String fromCurrency, String toCurrency, double amount) {
        try {
            String urlStr = BASE_URL + API_KEY + "/latest/" + fromCurrency;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            double exchangeRate = jsonResponse.getJSONObject("conversion_rates").getDouble(toCurrency);

            return amount * exchangeRate;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener los datos de la API.");
            return 0;
        }
    }
}

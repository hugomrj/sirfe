package nebuleuse.util;



import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

public class Convercion {
     private static final String[] UNIDADES_U = {"", "UNO", "DOS", "TRES",
        "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ",
        "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISEIS",
        "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"};

    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ",
        "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
        "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ",
        "DIECISIETE ", "DIECIOCHO ", "DIECINUEVE ", "VEINTE "};
    private static final String[] DECENAS = {"", "", "VEINTE ", "TREINTA ", "CUARENTA ",
        "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA "};
    private static final String[] CENTENAS = {"", "CIENTO ", "DOSCIENTOS ",
        "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
        "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};


                

    public String numeroaLetras(Object numero) {
        
                
        //NumberFormat formato = NumberFormat.getInstance();
        NumberFormat formato = NumberFormat.getInstance(Locale.ENGLISH);
        
        
        
        long data = Long.parseLong(dividePuntos(numero.toString())[0]);
        String valores = formato.format(data);        
        valores = valores.replaceAll(",",".");
        
        
        valores = dame0(valores.length()) + valores;        
        String[] conPuntos = dividePuntos(valores);        
        int cantidad = conPuntos.length;
        String numeroEnLetras = "";
        
        
        switch (cantidad) {
            case 1:
                numeroEnLetras = numeroEnLetras + menorAMIL(conPuntos[0]);
                break;
            case 2:
                numeroEnLetras = numeroEnLetras + menorAMILLON(conPuntos[0]) + menorAMIL(conPuntos[1]);
                break;
            case 3:
                numeroEnLetras = numeroEnLetras + menorAMILMILLON(conPuntos[0]) + menorAMILLON(conPuntos[1]) + menorAMIL(conPuntos[2]);
                break;
            case 4:
                numeroEnLetras = numeroEnLetras + menorAMILLON(conPuntos[0]) + menorAMILMILLON(conPuntos[1]) + menorAMILLON(conPuntos[2]) + menorAMIL(conPuntos[3]);
                break;
            default:
                break;
        }

        return numeroEnLetras.toLowerCase();
    }

    private static String menorAMILLON(String num) {
        String num_letras = "";
        int valor = Integer.parseInt(num);
        if (valor < 20) {
            num_letras =  num_letras + UNIDADES[valor];

        } else if (valor == 100) {
            num_letras = "CIEN ";
        } else {
            num_letras = CENTENAS[Integer.parseInt(num.substring(0, 1))] + (Integer.parseInt(num.substring(1)) > 19 ? (DECENAS[Integer.parseInt(num.substring(1, 2))] + (Integer.parseInt(num.substring(2, 3)) > 0 ? "Y " : "") + UNIDADES[Integer.parseInt(num.substring(2, 3))]) : UNIDADES[Integer.parseInt(num.substring(1, 3))]);

        }

        return num_letras + (valor != 0 ? "MIL " : "");
    }

    private static String menorAMIL(String num) {
        String num_letras = "";
        int valor = Integer.parseInt(num);
        if (valor < 20) {
            num_letras =UNIDADES_U[valor];
        } else if (valor != 100) {
            num_letras = CENTENAS[Integer.parseInt(num.substring(0, 1))] + (Integer.parseInt(num.substring(1)) > 19 ? (DECENAS[Integer.parseInt(num.substring(1, 2))] + (Integer.parseInt(num.substring(2, 3)) > 0 ? "Y " : " ") + UNIDADES_U[Integer.parseInt(num.substring(2, 3))]) : UNIDADES_U[Integer.parseInt(num.substring(1, 3))]);
        } else {
            num_letras = "CIEN";
        }

        return num_letras;
    }

    private static String menorAMILMILLON(String num) {
        String num_letras = "";
        int valor = Integer.parseInt(num);
        if (valor < 20) {
            num_letras = num_letras + UNIDADES[valor];
        } else if(valor != 100){
            num_letras = num_letras = CENTENAS[Integer.parseInt(num.substring(0, 1))] + (Integer.parseInt(num.substring(1)) > 19 ? (DECENAS[Integer.parseInt(num.substring(1, 2))] + (Integer.parseInt(num.substring(2, 3)) > 0 ? "Y " : " ") + UNIDADES[Integer.parseInt(num.substring(2, 3))]) : UNIDADES[Integer.parseInt(num.substring(1, 3))]);
            //CENTENAS[Integer.parseInt(num.substring(0, 1))] + (Integer.parseInt(num.substring(1)) > 19 ? (DECENAS[Integer.parseInt(num.substring(1, 2))] + (Integer.parseInt(num.substring(2, 3)) > 0 ? "Y " : "") + UNIDADES[Integer.parseInt(num.substring(2, 3))]) : UNIDADES[Integer.parseInt(num.substring(2, 3))]);
        }else{
            num_letras = "CIEN ";
        }

        num_letras = num_letras + (valor == 1 ? "MILLON " : "MILLONES ");

        return num_letras;
    }

    private static String[] dividePuntos(String monto) {
        String montoLetras = monto.toString();
        //List<String> lista= new LinkedList<String>();
        StringTokenizer tk = new StringTokenizer(montoLetras, "."); // Cambia aqu� el separador
        int x = 0;
        while (tk.hasMoreTokens()) {
            x = x + 1;
            tk.nextToken();

        }


        String[] valores = new String[x];
        tk = new StringTokenizer(monto, "."); // Cambia aqu� el separador
        x = 0;
        DecimalFormat d = new DecimalFormat("000");
        while (tk.hasMoreTokens()) {
             String val = tk.nextToken();
            try {
                val = d.format(d.parse(val));
            } catch (ParseException ex) {               
            }
            valores[x] = val;
            x++;
        }
        /*
        for(int i = 0 ; i < valores.length ; i++){
            //System.out.println("V " + valores[i]);
        }
        */
        return valores;
    }

    private static String dame0(int length) {
        if (length == 1 || length == 5 || length == 9 || length == 12) {
            return "00";
        } else if (length == 2 || length == 6 || length == 10 || length == 13) {
            return "0";
        } else {
            return "";
        }
    }
}

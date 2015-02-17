/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p1cript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import model.BigSquareRoot;
import model.Pareja;

/**
 *
 * @author javiyesares
 */
public class Main {

        
        // Algoritmo de potencia (Teorema de Fermat)
    private static BigInteger potencia(BigInteger a,BigInteger m,BigInteger n)
    {
            BigInteger b = new BigInteger("1"); 
            
            while(!m.equals(BigInteger.ONE)){
                if((m.mod(BigInteger.valueOf(2))).equals(BigInteger.ONE)){
                    b = b.multiply(a);
                    b = b.mod(n);
                }
                a = a.pow(2);
                a = a.mod(n);
                m = m.divide(BigInteger.valueOf(2)); 
                
            }
            b = a.multiply(b);
            b = b.mod(n);
            
            return b;
    }

    // Algoritmo de Shanks (Paso enano - Paso gigante)
    // Creando la Tabla S y la Tabla T
    private static BigInteger logaritmo_v1(BigInteger a, BigInteger m, BigInteger n)
    {
            int s;
            ArrayList<Pareja> tabla_s, tabla_t;
            BigSquareRoot raizc = new BigSquareRoot();
            s = (raizc.get(n).setScale(0, RoundingMode.CEILING)).intValue();
            
            tabla_s = new ArrayList<>(s);
            tabla_t = new ArrayList<>(s+1);
            
            // Tabla S
            Pareja p = new Pareja(0,m);
            tabla_s.add(0, p);
            
            for(int i=1; i<s; i++){
                    tabla_s.add(i,new Pareja(i, ((tabla_s.get(i-1).getValor()).multiply(a)).mod(n)));
                    
            }
            

            // Tabla T
            tabla_t.add(0,new Pareja(0,BigInteger.valueOf(0))); // Parche para manejo de array sin fallos. ??
            tabla_t.add(1,new Pareja(1, (a.pow(s)).mod(n)));

            for(int i=2; i<=s; i++){
                tabla_t.add(i,new Pareja(i,(((tabla_t.get(i-1).getValor()).multiply(tabla_t.get(1).getValor())).mod(n))));    
            }
            

            // Buscamos coincidencias
            for(int i=0; i<s; i++){
                    for(int j=1; j<=s; j++){
                            if((tabla_s.get(i).getValor()).equals(tabla_t.get(j).getValor())){                      
                                    return BigInteger.valueOf(j * s - i);  // Encontramos y damos una solucion cualquiera
                            }
                    }
            }

            return BigInteger.valueOf(-1); // No hay solucion
    }

    // Algoritmo de Shanks (Paso enano - Paso gigante)
    // Creando solo la Tabla S
    private static BigInteger logaritmo_v2(BigInteger a, BigInteger m, BigInteger n)
    {
            int s;
            BigInteger tabla_s[];
            BigInteger tabla_t, tabla_t_2;
            BigSquareRoot raizc = new BigSquareRoot();
            
            s = (raizc.get(n).setScale(0, RoundingMode.CEILING)).intValue();
            
            tabla_s = new BigInteger[s];

            // Tabla S
            tabla_s[0] = m;

            for(int i=1; i<s; i++){
                    tabla_s[i] = (tabla_s[i-1].multiply(a)).mod(n);
            }

            // Tabla T (sin crearla en memoria)
            tabla_t = tabla_t_2 = (a.pow(s)).mod(n);
            
            // Buscamos coincidencias
            for(int j = 1; j<=s; j++){
                    for(int i=0; i<s; i++){
                            if(tabla_t.equals(tabla_s[i])) {
                                return BigInteger.valueOf(j * s - i);
                        }
                    }
                    tabla_t = (tabla_t.multiply( tabla_t_2 )).mod(n);
            }

            return BigInteger.valueOf(-1); // No hay solucion
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        int ej;
        BigInteger a, b, p, pot, loga;
	long t;
        
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);

        
	// Menu
	while(true){
		System.out.println( "\nProblema: ");
		System.out.println("\n 1. Potencia");
		System.out.println("\n 2. Logaritmo v1");
		System.out.println("\n 3. Logaritmo v2");
		System.out.println("\n 4. Todos");
		System.out.println("\n 5. Salir");
		System.out.println("\n Introduzca opcion: ");
		ej = Integer.parseInt(br.readLine());

		if(ej == 5) {
                    System.exit(0); 
               }	// Salir del programa


		switch(ej){

		// Potencia
		case 1:
                        System.out.println("\n\na^b mod p");
                        System.out.println("\n\nIntroduzca numero 'a': ");
                        a = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'b': ");
                        b = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'p': ");
                        p = new BigInteger(br.readLine());
                        System.out.println("\n");
			t = System.currentTimeMillis();
			pot = potencia(a,b,p);
                        t = System.currentTimeMillis() - t;
               
			System.out.println("\nResultado potencia: ");
                        System.out.println(pot);
			System.out.println("\nTiempo potencia: ");
                        System.out.println(((float)t /1000) + " segundos\n\n");
			break;

		// Logaritmo v1
		case 2:
                        System.out.println("\n\nIntroduzca numero 'a': ");
                        a = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'b': ");
                        b = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'p': ");
                        p = new BigInteger(br.readLine());
                        System.out.println("\n");
			t = System.currentTimeMillis();
			loga = logaritmo_v1(a,b,p);
			t = System.currentTimeMillis() - t;

			if(loga.equals(BigInteger.valueOf(-1))) {
                            System.out.println("\nResultado logaritmo v1: No tiene solucion.");
                        }
			else {
                            System.out.println("\nResultado logaritmo v1: "+ loga);
                        }

			System.out.println("\nTiempo logaritmo v1: " + ((float)t/1000)+" segundos\n\n");
			break;

		// Logaritmo v2
		case 3:
                        System.out.println("\n\nIntroduzca numero 'a': ");
                        a = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'b': ");
                        b = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'p': ");
                        p = new BigInteger(br.readLine());
                        System.out.println("\n");
			t = System.currentTimeMillis();
			loga = logaritmo_v2(a,b,p);
			t = System.currentTimeMillis() - t;

			if(loga.equals(BigInteger.valueOf(-1))) {
                            System.out.println("\nResultado logaritmo v2: No tiene solucion.");
                        }
			else {
                            System.out.println("\nResultado logaritmo v2: "+loga);
                        }

			System.out.println("\nTiempo logaritmo v2: "+((float)t/1000)+ " segundos\n\n");
			break;

		// Hacer todos
		case 4:
                    
                        System.out.println("\n\nIntroduzca numero 'a': ");
                        a = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'b': ");
                        b = new BigInteger(br.readLine());
                        System.out.println("\nIntroduzca numero 'p': ");
                        p = new BigInteger(br.readLine());
                        System.out.println("\n");
			t = System.currentTimeMillis();
			pot = potencia(a,b,p);
			t = System.currentTimeMillis() - t;
                        
			System.out.println("\nResultado potencia: "+pot);
			System.out.println("\nTiempo potencia: " + ((float)t/1000) + " segundos\n\n");
			t = System.currentTimeMillis();
			loga = logaritmo_v1(a,b,p);
			t = System.currentTimeMillis() - t;

			if(loga.equals(BigInteger.valueOf(-1))) {
                            System.out.println("\nResultado logaritmo v1: No tiene solucion.");
                        }
			else {
                           System.out.println("\nResultado logaritmo v1: " + loga);
                        }
			System.out.println("\nTiempo logaritmo v1: " + ((float)t/1000)+ " segundos\n\n");
			t = System.currentTimeMillis();
			loga = logaritmo_v2(a,b,p);
			t = System.currentTimeMillis() - t;

			if(loga.equals(BigInteger.valueOf(-1))){
				System.out.println("\nResultado logaritmo v2: No tiene solucion.");
                        }else{
				System.out.println("\nResultado logaritmo v2: "+loga);
                        }
			System.out.println("\nTiempo logaritmo v2: " + ((float)t/1000) + " segundos\n\n");
			break;
		}//fin switch
	}//fin while
        
    }
    
}

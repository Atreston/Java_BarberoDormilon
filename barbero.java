package pruebasConcurrencia;
import java.util.concurrent.Semaphore;

public class pruebasConcurrencia {
	static Semaphore barberoListo = new Semaphore(1);
    static Semaphore accesoAsientos = new Semaphore(1);
    static Semaphore clienteListo = new Semaphore(4);
    static int asientosLibres = 4;
    
    static class Barbero extends Thread{
    	static String clienteActual;
    	public static void clienteSentado(String nom) {
    		clienteActual = nom;
    	}
        @Override
        public void run(){        	
        	try {
        		while(true) {
//        			barberoListo.acquire();
//        			if(asientosLibres >= 4) {
//        				barberoListo.release();
//        			}else {
////        				clienteListo.acquire();
//        				accesoAsientos.acquire();
//        				asientosLibres++;
//        				accesoAsientos.release();
//        				System.out.println("Cortando cabello a "+clienteActual);
//
//        				sleep(3000);
////        				clienteListo.release();
//        				barberoListo.release();
//        			}
        			
        			clienteListo.acquire();
        			accesoAsientos.acquire();
        			asientosLibres++;
        			barberoListo.release();
        			accesoAsientos.release();
        			sleep(3000);
        		}
    			
    			//System.out.println("No hay clientes. El barbero duerme.");
        	}catch(InterruptedException error) {}
        	System.out.println("Fin del hilo barbero");
        }
    }
    
    static class Cliente extends Thread{
    	String nombre;
    	Cliente(String n){
    		nombre = n;
    	}
        @Override
        public void run(){
        	try {
//        		clienteListo.acquire();
//        		System.out.println("El cliente "+nombre+" entro a la barberia.");
//        		accesoAsientos.acquire();
//        		asientosLibres--;
//        		accesoAsientos.release();
//        		System.out.println("Asientos libres: "+asientosLibres);
//        		System.out.println("El cliente "+nombre+" desperto al barbero.");
//        		Barbero.clienteSentado(nombre);
        		//recibiendo corte
        		
        		accesoAsientos.acquire();
        		if( asientosLibres > 0 ) {
        			System.out.println("El cliente "+nombre+" entro a la barberia.");
        			asientosLibres--;
        			System.out.println("Asientos libres: "+asientosLibres);
        			System.out.println("El cliente "+nombre+" desperto al barbero.");
        			clienteListo.release();
        			accesoAsientos.release();
        			barberoListo.acquire();
        			System.out.println("Cortando cabello a "+nombre);
        		}else {
        			accesoAsientos.release();
        		}
        	}catch(InterruptedException error) {}
        	
        }
    }
	public static void main(String[] args) {
		Barbero barbero = new Barbero();
        Cliente cliente1 = new Cliente("Gandalf");
        Cliente cliente2 = new Cliente("Aragon");
        Cliente cliente3 = new Cliente("Frodo");
        Cliente cliente4 = new Cliente("Bilbo");
        barbero.start();
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class  Distributore { 

	Carburanti carburanti = new Carburanti("carburanti.csv");
	LinkedList<String> listaElencoNuova = new LinkedList<String>();
	LinkedList<Carburanti> elencocarburanti = new LinkedList<Carburanti>();
	
	static void scegliCarburanti(LinkedList<Carburanti> elencocarburanti, double saldo,LinkedList<String> listaElencoNuova, Carburanti carburante, boolean admin) throws IOException {
		
			
		Scanner input = new Scanner(System.in);

		int index = 0;
		boolean interruttore = true;
		
		
		while (interruttore==true) {
			
		for(int i=0; i<elencocarburanti.size();i++) {
				System.out.println("Pompa" + " " + elencocarburanti.get(i).getPompa() + " - " + " " + elencocarburanti.get(i).getNome() + " " + elencocarburanti.get(i).getPrezzo() + "€ al litro");
		}
				String risposta = input.next();
		for (int j =0; j<elencocarburanti.size();j++) {
			
			if (elencocarburanti.get(j).getPompa().equals(risposta)) {
				interruttore=false;
				index = j;
				rifornimento(elencocarburanti, saldo, index, listaElencoNuova, carburante, admin);
				input.close();
			}
			
			
			if (j==elencocarburanti.size()) {
				System.out.println("Selezionare una pompa disponibile, riprova!");
			}
				
			}
		
		
			
			}
			}

	private static void rifornimento(LinkedList<Carburanti> elencocarburanti, double saldo, int index,
			LinkedList<String> listaElencoNuova, Carburanti carburante, boolean admin) throws IOException {
		
		Scanner tastiera = new Scanner(System.in);
		System.out.println("Indica quanti soldi vuoi spendere per il rifornimento: ");
		double importo = tastiera.nextDouble();
		//SE IL SALDO DELL'UTENTE E' SUPERIORE ALL'IMPORTO SI AVVIA IL CICLO
		
		if ( saldo >= importo) {
		
			//CICLO TUTTI I CARBURANTI FINO A TROVARE QUELLO GIUSTO
				
				//CREO UNA VARIABILE CHE ACCOGLIERA' IL PREZZO DEL CARBURANTE
				double prezzocarburante = elencocarburanti.get(index).getPrezzo();
				//CREO UNA VARIABILE CHE ACCOGLIERA' I LITRI ACQUISTATI E MI SERVIRA' POI PER AGGIORNARE IL CSV
				double litricarburante = importo/prezzocarburante;
				Double litri = Math.round(litricarburante*100.0)/100.0;
				if (litri <= elencocarburanti.get(index).getDisponibilita()) {
				System.out.println("Hai rifornito " + litri + " litri");
				saldo = saldo - importo;
				//CREO UNA VARIABILE CHE CONTERA' IL CARBURANTE RIMANETE IN DEPOSITO
				Double carburanterimanente = elencocarburanti.get(index).getDisponibilita() - litricarburante;
				Double carburanterim = Math.round(carburanterimanente*100.0)/100.0;
				//VADO A MODIFICARE IL VALORE NELLA LINKEDLIST
				elencocarburanti.get(index).setDisponibilita(carburanterim);
				Carburanti.aggiornaCsv(listaElencoNuova, elencocarburanti, carburante );
				
				System.out.println("");
				System.out.println("Vuoi effettuare un altro rifornimento?");
				System.out.println("");
				scegliCarburanti(elencocarburanti, saldo, listaElencoNuova, carburante, admin);
				
				} else {
					System.out.println("");
					System.out.println("Ci dispiace ma il quantitativo di carburante da te desiderato non � disponibile");
					System.out.println("");
					System.out.println("Puoi rifornirti di massimo " + elencocarburanti.get(index).getDisponibilita() + " Litri.");
					System.out.println("");
				}
				
		} else { 
			System.out.println("Saldo non sufficiente per l'operazione");
		}
	
	
	}	
	}


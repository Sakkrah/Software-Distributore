import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class  Distributore { 

	Carburanti carburanti = new Carburanti("carburanti.csv");
	LinkedList<String> listaElencoNuova = new LinkedList<String>();
	LinkedList<Carburanti> elencocarburanti = new LinkedList<Carburanti>();
	
		
		

	
	static void scegliCarburanti(LinkedList<Carburanti> elencocarburanti, double saldo,LinkedList<String> listaElencoNuova, Carburanti carburante, boolean admin, Carta bancomat, int numerocartapassato, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuovaCarta, Carburanti leggifilecarburanti, Carburanti menuadmin ) throws IOException {
		Scanner input = new Scanner(System.in);
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Menù rifornimento cisterna");
		menu.add("Menù aggiunta carburante");

		int index = 0;
		boolean interruttore = true;
		
		
		while (interruttore==true) {
			if (admin == true) {
			Carburanti menuprincipale = new Carburanti(menu);
			switch (menuprincipale.getScelta()) {
			
			case 1:
				
				System.out.println(" ");
				System.out.println("Menù rifornimento cisterne");
				System.out.println(" ");
				System.out.println("Seleziona la cisterna: ");
				System.out.println(" ");
				for(int i=0; i<elencocarburanti.size();i++) {
					System.out.println("Cisterna" + " " + elencocarburanti.get(i).getPompa() + " - " + " " + elencocarburanti.get(i).getNome() + " " + elencocarburanti.get(i).getDisponibilita() + " litri rimanenti");
				}
				String risposta2 = input.next();
				for (int j =0; j<elencocarburanti.size();j++) {
					
					if (elencocarburanti.get(j).getPompa().equals(risposta2)) {
						interruttore=false;
						index = j;
						ricaricadistributori(elencocarburanti, saldo, carburante, index, listaElencoNuova,  admin, menuadmin, leggifilecarburanti, bancomat);
						input.close();
			}
					if (j==elencocarburanti.size()) {
						System.out.println("Selezionare una pompa disponibile, riprova!");
					}
				}
				break;
				
			case 2:
						inserisciInCarburanti(leggifilecarburanti, elencocarburanti, listaElencoNuova, carburante, saldo, admin, menuadmin, index, elencocarte, listaElencoNuovaCarta, bancomat );
				break;
				
			
			
			case 0:
						interruttore=false;
				break;
			}
			
				
				
				
				} else {
					System.out.println(" ");
					System.out.println("Menù rifornimento");
					System.out.println(" ");
					System.out.println("Seleziona la pompa di carburante: ");
					System.out.println(" ");
		for(int i=0; i<elencocarburanti.size();i++) {
				System.out.println("Pompa" + " " + elencocarburanti.get(i).getPompa() + " - " + " " + elencocarburanti.get(i).getNome() + " " + elencocarburanti.get(i).getPrezzo() + "€ al litro");
		}
				String risposta = input.next();
		for (int x =0; x<elencocarburanti.size();x++) {
			
			if (elencocarburanti.get(x).getPompa().equals(risposta)) {
				interruttore=false;
				index = x;
				rifornimento(elencocarburanti, saldo, index, listaElencoNuova, carburante, admin, leggifilecarburanti, menuadmin, bancomat, index, elencocarte, listaElencoNuovaCarta);
				input.close();
			}
			
			
			if (x==elencocarburanti.size()) {
				System.out.println("Selezionare una pompa disponibile, riprova!");
			}
		}
				
			}
		
		
			
			}
	}

	public Carburanti getCarburanti() {
		return carburanti;
	}

	public void setCarburanti(Carburanti carburanti) {
		this.carburanti = carburanti;
	}

	public LinkedList<String> getListaElencoNuova() {
		return listaElencoNuova;
	}

	public void setListaElencoNuova(LinkedList<String> listaElencoNuova) {
		this.listaElencoNuova = listaElencoNuova;
	}

	public LinkedList<Carburanti> getElencocarburanti() {
		return elencocarburanti;
	}

	public void setElencocarburanti(LinkedList<Carburanti> elencocarburanti) {
		this.elencocarburanti = elencocarburanti;
	}

	private static void rifornimento(LinkedList<Carburanti> elencocarburanti, double saldo, int index,
			LinkedList<String> listaElencoNuova, Carburanti carburante, boolean admin, Carburanti leggifilecarburanti, Carburanti menuadmin, Carta bancomat, int numerocartapassato, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuovaCarta) throws IOException {
		
		Scanner tastiera = new Scanner(System.in);
		System.out.println("Inserire banconote: ");
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
				System.out.println("Vuoi effettuare un altro rifornimento? (y/n");
				System.out.println("");
				char scelta = tastiera.next().charAt(0);
				
				if (scelta != 'y') {
					System.out.println("Arrivederci!");
					System.exit(0);
				}
				
				else {
					Carburanti.leggifilecarburanti(carburante.getListaElenco());
					scegliCarburanti(elencocarburanti, saldo, listaElencoNuova, carburante, admin, bancomat, numerocartapassato, elencocarte, listaElencoNuova, leggifilecarburanti, menuadmin);
					 }
				
				//static void scegliCarburanti(LinkedList<Carburanti> elencocarburanti, double saldo,LinkedList<String> listaElencoNuova, Carburanti carburante, boolean admin, Carta bancomat, int numerocartapassato, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuovaCarta, Carburanti leggifilecarburanti, Carburanti menuadmin ) throws IOException {
					

				
				} else {
					System.out.println("");
					System.out.println("Ci dispiace ma il quantitativo di carburante da te desiderato non è disponibile");
					System.out.println("");
					System.out.println("Puoi rifornirti di massimo " + elencocarburanti.get(index).getDisponibilita() + " Litri.");
					System.out.println("");
				}
				
		} else { 
			System.out.println("Saldo non sufficiente per l'operazione");
		}
	
	
	}	
	
	public static void ricaricadistributori (LinkedList<Carburanti> elencocarburanti, double saldo, Carburanti carburante, int index,  LinkedList<String> listaElencoNuova, boolean admin, Carburanti menuadmin, Carburanti leggifilecarburanti, Carta bancomat) throws IOException {
		Scanner tastiera = new Scanner(System.in);
		System.out.println("Quanti litri vuoi rifornire?: ");
		double litri = tastiera.nextDouble();
		Double nuovascortacarburante = elencocarburanti.get(index).getDisponibilita() + litri;
		Double scorta = Math.round(nuovascortacarburante*100.0)/100.0;
		System.out.println("La scorta di " + elencocarburanti.get(index).getNome()+ " è ora di " + scorta + " litri");
		//VADO A MODIFICARE IL VALORE NELLA LINKEDLIST
		elencocarburanti.get(index).setDisponibilita(scorta);
		Carburanti.aggiornaCsv(listaElencoNuova, elencocarburanti, carburante );
	
		System.out.println("");
		System.out.println("Vuoi effettuare un'altra operazione? (y/n)");
		System.out.println("");
		char scelta = tastiera.next().charAt(0);
		
		if (scelta != 'y') {
			System.out.println("Arrivederci!");
			System.exit(0);
		}
		
		else {
			Carburanti.leggifilecarburanti(carburante.getListaElenco());
			scegliCarburanti(elencocarburanti, saldo, listaElencoNuova, carburante, admin, bancomat, index, null, listaElencoNuova, menuadmin, leggifilecarburanti );
			 }
				
	}		
			
			 
	
	
	
	private static void inserisciInCarburanti( Carburanti leggifilecarburanti, LinkedList<Carburanti> elencocarburanti, LinkedList<String> listaElencoNuova, Carburanti carburante, double saldo, boolean admin, Carburanti menuadmin, int numerocartapassato, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuovaCarta, Carta bancomat) throws IOException {
	Scanner tastiera = new Scanner(System.in);
	System.out.println("Inserisci il nome del carburante: ");
	String nome = tastiera.nextLine();
	System.out.println("Inserisci il prezzo del carburante: ");
	double prezzo = tastiera.nextDouble();
	System.out.println("Inserisci i litri disponibili del carburante: ");
	double disponibilita = tastiera.nextDouble();
	System.out.println("Inserisci la pompa del carburante: ");
	String pompa = tastiera.next();
	elencocarburanti.add(new Carburanti(nome, prezzo, disponibilita, pompa));
	Carburanti.aggiornaCsv(listaElencoNuova, elencocarburanti, carburante );
	System.out.println("Inserimento eseguito con successo!");
	System.out.println("");
	System.out.println("Vuoi rifornire un'altra cisterna? (y/n)");
	System.out.println("");
	char scelta = tastiera.next().charAt(0);
	
	if (scelta != 'y') {
		System.out.println("Arrivederci!");
		System.exit(0);
	} else {
		Carburanti.leggifilecarburanti(carburante.getListaElenco());
		scegliCarburanti(elencocarburanti, saldo, listaElencoNuova, carburante, admin, bancomat, numerocartapassato, elencocarte, listaElencoNuova, leggifilecarburanti, menuadmin);
		 }
	}




	
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Carta {


	//ATTRIBUTI
	int numerocarta;
	String nome;
	String cognome;
	String pin;
	double saldo;
	int saldopunti;
	boolean admin;
	private BufferedReader Elenco = null;
	private PrintWriter filemodificato = null;
	private LinkedList<String> ListaElenco = new LinkedList<String>();
	LinkedList<Carta> elencocarte = new LinkedList<Carta>();
	
	//roba per prodotti
	LinkedList<String> ListaElencoProdotti = new LinkedList<String>();
	LinkedList<Prodotti> elencoprodotti = new LinkedList<Prodotti>();
	LinkedList<String> listaElencoNuova = new LinkedList<String>();
	
	//COSTRUTTORI
	public Carta( int numerocarta,String pin, String nome, String cognome, double saldo, int saldopunti, boolean admin){
		this.numerocarta=numerocarta;
		this.pin = pin;
		this.nome=nome;
		this.cognome=cognome;
		this.saldo=saldo;
		this.saldopunti=saldopunti;
		this.admin=admin;

	}
	
	

	//METODI
	public Carta(String nomefile) throws IOException {
		String s = " ";
		
		Elenco = new BufferedReader(new FileReader(nomefile));
		
		while (s != null) {
			s = Elenco.readLine();
			if(s!=null) {
			ListaElenco.add((s));
			}
			}
			Elenco.close();
	}

	// LEGGE FILE CSV E RIEMPIE LA LISTA
	public void leggiFileCsv(LinkedList<Carta> elencocarte) throws IOException {
			//SVUOTA LISTA PER EVITARE CHE CREI DOPPIONI
			elencocarte.clear();
			// CREA NUOVA ISTANZA DI GESTIONECSV PER RILEGGERE IL FILE AGGIORNATO
			Carta filecsv = new Carta("carte.csv");
			//CICLO PER CARICARE LA LINKED LIST
			for (int i = 1; i < filecsv.getListaElenco().size(); i++) {
				String[] rigo = filecsv.getListaElenco().get(i).toString().split(";");
				elencocarte.add(new Carta(Integer.parseInt(rigo[0]), (rigo[1]), (rigo[2]), (rigo[3]),
						Double.parseDouble(rigo[4]), Integer.parseInt(rigo[5]), Boolean.parseBoolean(rigo[6])));
				
			}

		}
	
	public void mostraMenuCarta(Prodotti macchinetta, Carburanti carburante) throws IOException {
		
		System.out.println("*******************************");
		System.out.println("*         PROGRAMMA           *");
		System.out.println("*******************************");
		System.out.println();
		

		
		leggiFileCsv(elencocarte);
		checkNumCarta(elencocarte,macchinetta,carburante);
	}
	
	

	public void checkNumCarta(LinkedList<Carta> elencocarte, Prodotti macchinetta, Carburanti carburante) throws IOException {
		System.out.println("Inserisci il tuo numero di carta: ");
		Scanner input = new Scanner(System.in);

		boolean interruttore = true;
		while (interruttore) {
			int numerocarta = input.nextInt();
			for (int i = 0; i < elencocarte.size(); i++) {
				if (elencocarte.get(i).getNumerocarta() == numerocarta) {
				}
				if (i == elencocarte.size()) {
					System.out
							.println("Numero carta errato! Per favore, inserisci nuovamente il tuo numero di carta: ");
				}

				//++++++togli qui metti in controllo pin boolean admin = elencocarte.get(i).getAdmin();
			}
			interruttore = false;
			controlloPin(numerocarta, elencocarte, interruttore, macchinetta, carburante);
			input.close();

		}

	}
		
	public void controlloPin(int numerocarta,LinkedList<Carta> elencocarte, boolean admin, Prodotti macchinetta,Carburanti carburante) throws IOException {
		Scanner tastiera = new Scanner(System.in);
		boolean cartaOK = true;
		int numtentativi = 0;
		int numtentativimax = 3;
		String nome = "";
		String pinOk = "";
	
		// SCORRE LA LISTA E SALVA NOME E PIN IN BASE AL NUMERO CARTA INSERITO
		int i= trovaIndex(numerocarta, elencocarte);
		nome = (elencocarte.get(i).getNome());
		pinOk = (elencocarte.get(i).getPin());
		admin = (elencocarte.get(i).getAdmin());
		saldo = (elencocarte.get(i).getSaldo());
	
		// CICLA FINCHE' NON SUPERA IL NUMERO MAX DI TENTATIVI (3)
		while ((numtentativi < numtentativimax)) {

			// PRIMO TENTATIVO
			if (numtentativi == 0) {
				System.out.println("Ciao " + nome + "! Inserisci il tuo pin: ");
				
			} else {
				System.out.println("Per favore, inserisci nuovamente il tuo pin");
			}

			// INPUT UTENTE
			 String pin = tastiera.next();	
			 System.out.println();
			// INCREMENTA TENTATIVI
			 numtentativi++;
			
			// SE PIN E' CORRETTO
			if (pin.equals(pinOk)) {
				numtentativi = 0;
				System.out.println("Pin corretto!");
				System.out.println();
				break;
				
			}
		 	else {
			// SE PIN NON CORRETTO, CONTROLLA NUMERO TENTATIVI
			if (numtentativi <= numtentativimax ) {
				if (numtentativi == numtentativimax) {
					System.out.println("Pin non corretto!");
					System.out.println("Hai superato il numero massimo di tentativi!");
					System.out.println("La tua carta � bloccata. Contatta il numero verde");
					System.exit(0);
				} else
					System.out.println(
							"Pin non corretto, hai ancora " + (numtentativimax - numtentativi) + " tentativi!");
				  {
					  
				  }
				}
				}

				} 
				// chiude while
				System.out.println("Seleziona il servizio desiderato:");
				sceltaServizio(admin, macchinetta,carburante, saldo);
		

				}
		
	private void sceltaServizio(boolean admin, Prodotti macchinetta, Carburanti carburante, double saldo) throws IOException {
		 Scanner input = new Scanner(System.in);
		 
		  System.out.println("1) Macchinetta");
		  System.out.println("2) Distributore di benzina");
		  System.out.println("3) Bancomat");
		  
		  int scelta = input.nextInt();
		  
		  switch (scelta) {
		  
		  	 case 1:
	  		 	Prodotti.creaLinkedListdiArrayDatabase(macchinetta.getElencoprodotti());
	  		 	Prodotti.adminCheck(macchinetta.getElencoprodotti(), macchinetta.getListaElencoNuova(), macchinetta, saldo, 0, admin);
		  		break;
		  
		  	 case 2:
		  		System.out.println("Scegli una pompa (da 1 a 5) : ");
		  		Carburanti.leggifilecarburanti(carburante.getListaElenco());
		  		Distributore.scegliCarburanti(carburante.getListaElenco(), 100, listaElencoNuova, carburante, admin);
	     	 	break;
      	 
	        case 3:
	        	bancomatBenvenuto(admin);
	      		break;		 
		  }
		
	}

	
	public void bancomatBenvenuto(boolean admin) throws IOException{
		System.out.println(admin);
	
		if (admin==true) {
			// ISTANZIO SCANNER PER OTTENERE INPUT UTENTE
			Scanner tastiera = new Scanner(System.in);
		
			System.out.println("*******************************");
			System.out.println("*   	PROGRAMMA ADMIN       *");
			System.out.println("*******************************");
			System.out.println();
			

			// SE ADMIN INSERISCO VOCI MENU'ADMIN IN ARRAYLIST (GESTITA DALLA CLASSEMENU)
			ArrayList<String> vocidimenu = new ArrayList<String>();
			vocidimenu.add("Visualizza dati carta");
			vocidimenu.add("Modifica carta");
			vocidimenu.add("Aggiungi carta");
			vocidimenu.add("Elimina carta");
			// INSERISCO LE VOCI DEL MENU ATTRAVERSO IL COSTRUTTORE DELLA CLASSE
			ClasseMenu menuprincipale = new ClasseMenu(vocidimenu);
			
			boolean interruttore = true;
			
			
		
			
			while(interruttore) {
				switch(menuprincipale.getScelta()) {
				case 1:
					System.out.println("***Hai selezionato: visualizza dati carta***");
					mostraSaldo(numerocarta, elencocarte,false);
					break;
				case 2:
					System.out.println("***Hai selezionato: modifica dati carta***");
					break;
				case 3:
					System.out.println("***Hai selezionato: aggiungi nuova carta carta***");
					break;
				case 4:
					System.out.println("***Hai selezionato: elimina carta***");
					break;
				case 0:
					interruttore = false;
					break;
					default:
						System.out.println("Scelta non valida");
				}//CHIUDE SWITCH
			}//CHIUDE WHILE
			
		}
		
		else {
		
		
		// ISTANZIO SCANNER PER OTTENERE INPUT UTENTE
				Scanner tastiera = new Scanner(System.in);
			
				System.out.println("*******************************");
				System.out.println("*  PROGRAMMA GESTIONE CARTA   *");
				System.out.println("*******************************");
				System.out.println();
				

				// INSERISCO VOCI MENU' IN ARRAYLIST (GESTITA DALLA CLASSEMENU)
				ArrayList<String> vocidimenu = new ArrayList<String>();
				vocidimenu.add("Versamento");
				vocidimenu.add("Prelievo");
				vocidimenu.add("Pagamento");
				vocidimenu.add("Mostra saldo");
				vocidimenu.add("Mostra saldo punti");
				
				boolean interruttore = true;
				while (interruttore) {
					
					//SERVE PER CHIAMATA A MOSTRASALDO: INDICA SE SALDOPUNTI (TRUE) O SALDO(FALSE)
					boolean flagSaldoPunti = false;

					// INSERISCO LE VOCI DEL MENU ATTRAVERSO IL COSTRUTTORE DELLA CLASSE
					ClasseMenu menuprincipale = new ClasseMenu(vocidimenu);

					// SWITCH SU SCELTA UTENTE
					switch (menuprincipale.getScelta()) {
					case 1:
						System.out.println("***Hai selezionato: Versamento contanti***");
						mostraSaldo(numerocarta, elencocarte,flagSaldoPunti);
						System.out.println("Quanto vuoi versare: ");
						double versamento = tastiera.nextDouble();
						versaPreleva(numerocarta, 0, versamento, elencocarte, listaElencoNuova);

						break;
					case 2:
						System.out.println("***Hai selezionato: Prelievo***");
						mostraSaldo(numerocarta, elencocarte, flagSaldoPunti);
						System.out.println("Quanto vuoi prelevare? ");
						double prelievo = tastiera.nextDouble();
						versaPreleva(numerocarta, prelievo, 0, elencocarte, listaElencoNuova);
						break;
					case 3:
						System.out.println("***Hai selezionato: Pagamento***");
						System.out.println("Inserisci la cifra da pagare: ");
						double pagamento = tastiera.nextDouble();
						faiPagamento(pagamento, numerocarta, elencocarte, listaElencoNuova);
						break;
					case 4:
						System.out.println("***Hai selezionato: Mostra saldo***");
						
						mostraSaldo(numerocarta, elencocarte, flagSaldoPunti);
						break;
					case 5:
						System.out.println("***Hai selezionato: Mostra saldo punti***");
						flagSaldoPunti = true;
						mostraSaldo(numerocarta, elencocarte, flagSaldoPunti);
						break;
					case 0:
						interruttore = false;
						break;
					default:
						System.out.println("Scelta non valida");
					}// CHIUDE SWITCH

				} // CHIUDE WHILE
				
				
		}
	}
	
	
	
	// SCORRE LA LISTA E SALVA INDICE IN BASE AL NUMERO CARTA INSERITO
	public int trovaIndex(int numerocarta, LinkedList<Carta> elencocarte) {
			int index =0;
			for(int i = 0; i < elencocarte.size(); i++) {
				if(elencocarte.get(i).getNumerocarta() == numerocarta) {
					index =i;
				}
			}
			return index;
			
		}
	

		// AGGIORNA CSV PRENDENDO I DATI DALLA LISTA AGGIORNATA
	public void aggiornaCsv(LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuova)
				throws IOException {
			listaElencoNuova.clear();
			listaElencoNuova.addFirst("Numero;Pin;Nome;Cognome;Saldo;SaldoPunti");
			for (int i = 0; i < elencocarte.size(); i++) {
				String elemento = elencocarte.get(i).getNumerocarta() + ";" + elencocarte.get(i).getPin() + ";"
						+ elencocarte.get(i).getNome() + ";" + elencocarte.get(i).getCognome() + ";"
						+ elencocarte.get(i).getSaldo() + ";" + elencocarte.get(i).getSaldopunti();
				listaElencoNuova.add(elemento);
				PrintWriter filemodificato = null;
				filemodificato = new PrintWriter(new FileWriter("carte.csv", false));
				for (int x = 0; x < listaElencoNuova.size(); x++) {
					filemodificato.println(listaElencoNuova.get(x));
					// System.out.println("filemodificato " + listaElencoNuova.get(i));
				}

				filemodificato.close();

			}

		}

		// GESTISCE VERSAMENTO-PRELIEVO E CHIAMA AGGIORNA CSV
	public void versaPreleva(int numerocarta, double prelievo, double versamento, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuova) throws IOException {
				double saldo = 0;
				//double saldoDec = 0;
				int i= trovaIndex(numerocarta, elencocarte);
				
				//saldo = (elencocarte.get(i).getSaldo());
				 BigDecimal bd = new BigDecimal(saldo).setScale(2, RoundingMode.HALF_UP);
				 
			    saldo = (elencocarte.get(i).getSaldo());
			    bd.doubleValue();
				
					// SE PRELEVO CONTROLLO IL SALDO
					if (prelievo > 0) {
						if (prelievo > saldo) {
							System.out.println("Siamo spiacenti, non hai abbastanza soldi per prelevare quella cifra");
							System.out.println();
						} else {
							// SOTTRAGGO PRELIEVO
							saldo -= prelievo;
							elencocarte.get(i).setSaldo(saldo);
							//+++System.out.println("PIPPOOOOOOOO Il tuo nuovo saldo � di � " + saldo + "VERSAMENTO= "+ versamento);
						//+++System.out.println("Prelievo=" + prelievo + "Saldo="  + saldo + (prelievo <= saldo));
						}
						
					}// SE VERSAMENTO AGGIUNGO VERSAMENTO
					if (versamento > 0) {
						saldo += versamento;
						elencocarte.get(i).setSaldo(saldo);
					}


			
			//+++ PROBLEMA:IN CASO DI PRELIEVO, CON SALDO <= PRELIEVO NON SCRIVE MESSAGGIO PERCHE' L'AND E' FALSE (IL VERSAMENTO IN QUESTO CASO E' 0)	
			//+++ ESEMPIO ERRORE: SALDO PRE 200; PRELEVO 100; NUOVO SALDO 100. OPPURE CON SALDO 100 PRELEVO 100
			
			//IF PER ESCLUDERE MESSAGGIO NEL CASO PRELIEVO>SALDO
			if ((prelievo <= saldo && prelievo > 0) || ( versamento > 0)) {	
				System.out.println("Il tuo nuovo saldo � di � " + saldo);
				//+++++System.out.println("1 -Il tuo nuovo saldo � di � " + saldo + "VERSAMENTO= "+ versamento);
				System.out.println();
				
				
			} //+++TENTATIVO FALLITO DI RISOLUZIONE
			/*else if((prelievo <= saldo) && (versamento == 0)) {
				System.out.println("Condizione 1: " + (prelievo < saldo));
				System.out.println("Condizione 2: " + (versamento ==0));
				System.out.println("2 -Il tuo nuovo saldo � di � " + saldo + "VERSAMENTO= "+ versamento + "Prelievo " + prelievo);
				
				
			}*/
			
			// AGGIORNO CSV
			aggiornaCsv(elencocarte, listaElencoNuova);
		}// chiude il metodo versaPreleva

		// GESTISCE PAGAMENTO E SALDO PUNTI
	public double faiPagamento(double pagamento, int numerocarta, LinkedList<Carta> elencocarte,
				LinkedList listaElencoNuova) throws IOException {
			double saldo = 0;
			String nome = "";
			int saldoPunti = 0;
			int puntiFatti = 0;
			int puntiTot = 0;
			
			int i= trovaIndex(numerocarta, elencocarte);
			saldo = (elencocarte.get(i).getSaldo());
			nome = (elencocarte.get(i).getNome());
			saldoPunti = (elencocarte.get(i).getSaldopunti());
			
			if (pagamento > saldo) {
				System.out.println("Il tuo saldo attuale � di � " + saldo
						+ ". Siamo spiacenti. Non hai un saldo sufficente per effettuare il pagamento!");
			} else {
				saldo -= pagamento;
				// TRASFORMA DOUBLE PAGAMENTO IN INT VALUE
				int value = (int) pagamento;
				// CALCOLA PUNTI
				puntiFatti = (value / 2);
				saldoPunti += puntiFatti;
				System.out.println("Il tuo pagamento � andato a buon fine!");
				System.out.println("Hai un nuovo saldo di  " + saldo);

				// +++++TEST PUNTI
				// System.out.println(saldoPunti);
				// System.out.println(puntiFatti);

				elencocarte.get(i).setSaldo(saldo);
				elencocarte.get(i).setSaldopunti(saldoPunti);
				aggiornaCsv(elencocarte, listaElencoNuova);

			}

			return saldo;

		}

		//MOSTRA SALDO E SALDO CARTA, SULLA BASE DEL FLAG SALDO PUNTI (SE TRUE MOSTRA SALDO PUNTI, ELSE MOSTRA SALDO)
	public void mostraSaldo(int numerocarta, LinkedList<Carta> elencocarte, boolean flagSaldoPunti) {
			double saldo = 0;
			String nome = "";
			int saldoPunti = 0;
		
			int i= trovaIndex(numerocarta, elencocarte);
			saldo = (elencocarte.get(i).getSaldo());
			nome = elencocarte.get(i).getNome();
			saldoPunti = (elencocarte.get(i).getSaldopunti());
				
			if(flagSaldoPunti) {
				System.out.println(nome + ", il tuo saldo � di " + saldoPunti + " punti");
				System.out.println();
				System.out.println("Ti ricordiamo che otterrai un punto ogni 2 � di spesa!");
				System.out.println("Cerca i distributori convenzionati per accumulare punti!");
				System.out.println();
			}else {
				System.out.println(nome + ", il tuo saldo � di " + saldo);
				System.out.println();
			}
		}

	
	// GETTERS AND SETTERS
		public LinkedList<String> getListaElenco() {
			return ListaElenco;
		}

		public void setListaElenco(LinkedList<String> listaElenco) {
			ListaElenco = listaElenco;
		}

		public PrintWriter getFilemodificato() {
			return filemodificato;
		}

		public void setFilemodificato(PrintWriter filemodificato) {
			this.filemodificato = filemodificato;
		}
		
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public int getNumerocarta() {
			return numerocarta;
		}

		public void setNumerocarta(int numerocarta) {
			this.numerocarta = numerocarta;
		}

		public double getSaldo() {
			return saldo;
		}

		public void setSaldo(double saldo) {
			this.saldo = saldo;
		}

		public int getSaldopunti() {
			return saldopunti;
		}

		public void setSaldopunti(int saldopunti) {
			this.saldopunti = saldopunti;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public boolean getAdmin() {
			return admin;
		}

		public void setAdmin(boolean admin) {
			this.admin = admin;
		}



		

}

	
	


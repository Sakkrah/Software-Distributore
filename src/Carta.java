import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	Scanner tastiera = new Scanner(System.in);
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
	
	public void mostraMenuCarta(Prodotti macchinetta, Carburanti carburante, Carta bancomat, Distributore distributore) throws IOException {
		
		System.out.println("*******************************");
		System.out.println("*         BENVENUTO           *");
		System.out.println("*******************************");
		System.out.println();
		

		
		leggiFileCsv(elencocarte);
		checkNumCarta(elencocarte,macchinetta,carburante,bancomat, distributore);
	}
	
	

	public void checkNumCarta(LinkedList<Carta> elencocarte, Prodotti macchinetta, Carburanti carburante, Carta bancomat, Distributore distributore) throws IOException {
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
			controlloPin(numerocarta, elencocarte, interruttore, macchinetta, carburante, bancomat, distributore);
			input.close();

		}

	}
		
	public void controlloPin(int numerocarta,LinkedList<Carta> elencocarte, boolean admin, Prodotti macchinetta,Carburanti carburante, Carta bancomat, Distributore distributore) throws IOException {
		Scanner tastiera = new Scanner(System.in);
		int numtentativi = 0;
		int numtentativimax = 3;
		String nome = "";
		String pinOk = "";
		int numerocartapassato;
	
		// SCORRE LA LISTA E SALVA NOME E PIN IN BASE AL NUMERO CARTA INSERITO
		int i= trovaIndex(numerocarta, elencocarte);
		nome = (elencocarte.get(i).getNome());
		pinOk = (elencocarte.get(i).getPin());
		admin = (elencocarte.get(i).getAdmin());
		saldo = (elencocarte.get(i).getSaldo());
		numerocartapassato = elencocarte.get(i).getNumerocarta();
	
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
					System.out.println("La tua carta e' bloccata. Contatta il numero verde");
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
				sceltaServizio(admin, macchinetta,carburante, saldo, bancomat,numerocartapassato, distributore);
		
				tastiera.close();
				}
		
	private void sceltaServizio(boolean admin, Prodotti macchinetta, Carburanti carburante, double saldo, Carta bancomat, int numerocartapassato, Distributore distributore) throws IOException {
		 Scanner input = new Scanner(System.in);
		 
		  System.out.println("1) Macchinetta");
		  System.out.println("2) Distributore di benzina");
		  System.out.println("3) Bancomat");
		  
		  int scelta = input.nextInt();
		  
		  switch (scelta) {
		  
		  	 case 1:
	  		 	Prodotti.creaLinkedListdiArrayDatabase(macchinetta.getElencoprodotti());
	  		 	Prodotti.adminCheck(macchinetta.getElencoprodotti(), macchinetta.getListaElencoNuova(), macchinetta, saldo, 0, admin,bancomat,numerocartapassato, elencocarte, listaElencoNuova);
		  		break;
		  
		  	 case 2:
		  		System.out.println("Scegli una pompa (da 1 a 5) : ");
		  		Carburanti.leggifilecarburanti(carburante.getListaElenco());
		  		Distributore.scegliCarburanti(carburante.getListaElenco(), saldo, distributore.getListaElencoNuova(), carburante, admin, bancomat,numerocartapassato, elencocarte, listaElencoNuova, carburante, carburante);
	     	 	break;
      	 
	        case 3:
	        	startBancomat(admin,macchinetta,carburante, numerocarta, bancomat, listaElencoNuova, numerocartapassato, distributore);
	      		break;		 
		  }
		
		  input.close();
	}

	
	public void startBancomat(boolean admin, Prodotti macchinetta, Carburanti carburante,int numerocarta, Carta bancomat, LinkedList<String>listaElencoNuova, int numerocartapassato, Distributore distributore) throws IOException{	
		
		if (admin==true) {
			boolean interruttore1 = true;
		
			System.out.println("*******************************");
			System.out.println("*   	PROGRAMMA ADMIN       *");
			System.out.println("*******************************");
			System.out.println();
			


			
			while(interruttore1 == true) { 
				
				System.out.println("1 - Visualizza elenco carte");
				System.out.println("2 - Visualizza dati carta");
				System.out.println("3 - Modifica carta");
				System.out.println("4 - Aggiungi carta");
				System.out.println("5 - Elimina carta");
				System.out.println("0 - Torna al mene' principale");
			
				int scelta1 = tastiera.nextInt();
			
				switch(scelta1) {
				case 1:
					System.out.println("***Hai selezionato: visualizza elenco carte***");
					visualizzaCarte(elencocarte, 0);
					break;
				case 2:
					System.out.println("***Hai selezionato: visualizza dati carta***");
					System.out.println("Per favore, inserici il numero di carta da visualizzare: ");
					numerocarta = tastiera.nextInt();
					visualizzaCarte(elencocarte, numerocarta);
					break;
				case 3:
					System.out.println("***Hai selezionato: modifica dati carta***");
					System.out.println("Per favore, inserici il numero di carta da modificare: ");
					int numerocartaModifica = tastiera.nextInt();
					trovaIndex(numerocartaModifica, elencocarte);
					while(trovaIndex(numerocartaModifica, elencocarte)==0) {
						
						System.out.println("Numero carta non corretto");
						System.out.println("Per favore, inserici il numero di carta da modificare: ");
						
						numerocartaModifica = tastiera.nextInt();
						trovaIndex(numerocartaModifica, elencocarte);
						
					}
					modificaCarta(elencocarte, numerocartaModifica, bancomat);
				//	System.out.println("Numero carta:" + numerocartaModifica);
					break;
				case 4:
					System.out.println("***Hai selezionato: aggiungi carta***");
					aggiungiCarta(elencocarte, bancomat, listaElencoNuova);
							
					break;
				case 0:
					sceltaServizio(admin, macchinetta, carburante, numerocarta, bancomat, numerocartapassato, distributore);
					//interruttore1 = false;
					break;
				default:
					System.out.println("pppppp");			
				}//chiude switch
			
			}//chiude while
		}
		
		else {
			
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
				
				boolean interruttore2 = true;
				while (interruttore2) {
					
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
						versaPreleva(numerocarta, 0, versamento, elencocarte, listaElencoNuova, bancomat);

						break;
					case 2:
						System.out.println("***Hai selezionato: Prelievo***");
						mostraSaldo(numerocarta, elencocarte, flagSaldoPunti);
						System.out.println("Quanto vuoi prelevare? ");
						double prelievo = tastiera.nextDouble();
						versaPreleva(numerocarta, prelievo, 0, elencocarte, listaElencoNuova,bancomat);
						break;
					case 3:
						System.out.println("***Hai selezionato: Pagamento***");
						System.out.println("Inserisci la cifra da pagare: ");
						double pagamento = tastiera.nextDouble();
						//faiPagamento(pagamento, numerocarta, elencocarte, listaElencoNuova, bancomat);
						faiPagamento(pagamento, numerocarta, elencocarte, listaElencoNuova, bancomat);
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
						sceltaServizio(admin, macchinetta, carburante, numerocarta, bancomat,numerocartapassato, distributore);
						interruttore2 = false;
						break;
					default:
						System.out.println("Scelta non valida");
					}// CHIUDE SWITCH

				} // CHIUDE WHILE
				
				
				
		}
	}
	
	private void aggiungiCarta(LinkedList<Carta> elencocarte, Carta bancomat, LinkedList<String> listaElencoNuova) throws IOException {
		
		System.out.println("Inserisci numero di carta: ");
		int newcartaNum = tastiera.nextInt();
		System.out.println("Inserisci pin di 4 cifre: ");
		String newcartaPin = tastiera.next();
		System.out.println("Inserisci nome: ");
		String newcartaNome = tastiera.next();
		System.out.println("Inserisci cognome: ");
		String newcartaCognome = tastiera.next();
		System.out.println("Inserisci saldo: ");
		double newcartaSaldo = tastiera.nextDouble();
		System.out.println("Inserisci saldo punti: ");
		int newcartaSaldopunti = tastiera.nextInt();
		System.out.println("inserisci admin si(y) o no(n)");
		String newcartaAdmin = tastiera.next();
		if(newcartaAdmin.equals("y")) {
		 elencocarte.add(new Carta(newcartaNum, newcartaPin, newcartaNome, newcartaCognome, newcartaSaldo, newcartaSaldopunti, true));
		}else if(newcartaAdmin.equals("n")){
			elencocarte.add(new Carta(newcartaNum, newcartaPin, newcartaNome, newcartaCognome, newcartaSaldo, newcartaSaldopunti, false));}
		
		aggiornaCsv(elencocarte, bancomat, listaElencoNuova);
		
	}
	
	



	//MOSTRA TUTTE LE CARTE/UNA CARTA
	private void visualizzaCarte(LinkedList<Carta> elencocarte, int numerocarta) {
		//SE NESSUN NUMERO CARTA 
		int index = 0;
			
		if(numerocarta==0) {
			//CON CICLO FOR MOSTRO L'ELENCO DELLE CARTE
			for(int i=0; i<elencocarte.size();i++) {
				System.out.println("Numero carta: "+  elencocarte.get(i).getNumerocarta());
				System.out.println("Nome: " + elencocarte.get(i).getNome());
				System.out.println("Cognome: "+ elencocarte.get(i).getCognome());
				System.out.println("Saldo e': " + elencocarte.get(i).getSaldo());
				System.out.println("Saldo punti: " + elencocarte.get(i).getSaldopunti());
				System.out.println("Amministratore: " + elencocarte.get(i).getAdmin());
				System.out.println("***************************");
			}
		}
		else {
			index = trovaIndex(numerocarta, elencocarte);
			System.out.println("Numero carta: "+  elencocarte.get(index).getNumerocarta());
			System.out.println("Nome: " + elencocarte.get(index).getNome());
			System.out.println("Cognome: "+ elencocarte.get(index).getCognome());
			System.out.println("Saldo e': " + elencocarte.get(index).getSaldo());
			System.out.println("Saldo punti: " + elencocarte.get(index).getSaldopunti());
			System.out.println("Amministratore: " + elencocarte.get(index).getAdmin());
			System.out.println("***************************");
		}		
		
	}
	
	//MODIFICA CARTA
	
	public void modificaCarta(LinkedList<Carta> elencocarte, int numerocarta, Carta bancomat) throws IOException {
		int index =0;
		
		
		for (int i = 0; i < elencocarte.size(); i++) {
			if (elencocarte.get(i).getNumerocarta() == numerocarta) {
				index =i;
			}
		}

		System.out.println("Vuoi modificare:");
		System.out.println("1 - Numero carta");
		System.out.println("2 - Pin");
		System.out.println("3 - Nome");
		System.out.println("4 - Cognome");
		System.out.println("5 - Saldo punti");
		System.out.println("6 - Admin");
		int scelta =tastiera.nextInt();
		
	
		switch(scelta) {
		case 1:
			System.out.println("Inserisci nuovo numero carta: ");
			int newnum = tastiera.nextInt();
			elencocarte.get(index).setNumerocarta(newnum);
			break;
		case 2:
			System.out.println("Inserisci nuovo pin: ");
			String newpin = tastiera.next();
			elencocarte.get(index).setPin(newpin);
			break;
		case 3: 
			System.out.println("Inserisci nuovo nome: ");
			String newnome = tastiera.next();
			elencocarte.get(index).setNome(newnome);
			break;
		case 4:
			System.out.println("Inserisci nuovo cognome: ");
			String newcognome = tastiera.next();
			elencocarte.get(index).setCognome(newcognome);
			break;
		case 5: 
			System.out.println("Inserisci nuovo saldo punti: ");
			int newsaldo = tastiera.nextInt();
			elencocarte.get(index).setSaldopunti(newsaldo);
			break;
		case 6:
			System.out.println("Digita Y per abilitare admin, N per disabilitare: ");
			String newadmin = tastiera.next();
			
			if(newadmin.equals("y")) {
				elencocarte.get(index).setAdmin(true);
			 
			}else if(newadmin.equals("n")){
				elencocarte.get(index).setAdmin(false);
			
			}
			break;
		default:
			System.out.println("Scelta non valida");
		}
		aggiornaCsv(elencocarte, bancomat, listaElencoNuova);
		System.out.println("Dati modificati con successo!");
		
		
		
		
		
		
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
	public void aggiornaCsv(LinkedList<Carta> elencocarte, Carta bancomat, LinkedList<String> listaElencoNuova)
				throws IOException {
			listaElencoNuova.clear();
			listaElencoNuova.addFirst("Numero;Pin;Nome;Cognome;Saldo;SaldoPunti;Admin");			
		for (int i = 0; i < elencocarte.size(); i++) {
				String elemento = elencocarte.get(i).getNumerocarta() + ";" + elencocarte.get(i).getPin() + ";"
						+ elencocarte.get(i).getNome() + ";" + elencocarte.get(i).getCognome() + ";"
						+ elencocarte.get(i).getSaldo() + ";" + elencocarte.get(i).getSaldopunti() + ";"+ elencocarte.get(i).getAdmin();
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
	public void versaPreleva(int numerocarta, double prelievo, double versamento, LinkedList<Carta> elencocarte, LinkedList<String> listaElencoNuova, Carta bancomat) throws IOException {
				double saldo = 0;
				
				int i= trovaIndex(numerocarta, elencocarte);
			    saldo = (elencocarte.get(i).getSaldo());

				
					// SE PRELEVO CONTROLLO IL SALDO
					if (prelievo > 0) {
						if (prelievo > saldo) {
							System.out.println("Siamo spiacenti, non hai abbastanza soldi per prelevare quella cifra");
							System.out.println();
						} else {
							// SOTTRAGGO PRELIEVO
							saldo -= prelievo;
							elencocarte.get(i).setSaldo(saldo);
							//+++System.out.println("PIPPOOOOOOOO Il tuo nuovo saldo e' di e' " + saldo + "VERSAMENTO= "+ versamento);
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
				System.out.println("Il tuo nuovo saldo e' di e' " + saldo);
				//+++++System.out.println("1 -Il tuo nuovo saldo e' di e' " + saldo + "VERSAMENTO= "+ versamento);
				System.out.println();
				
				
			} //+++TENTATIVO FALLITO DI RISOLUZIONE
			/*else if((prelievo <= saldo) && (versamento == 0)) {
				System.out.println("Condizione 1: " + (prelievo < saldo));
				System.out.println("Condizione 2: " + (versamento ==0));
				System.out.println("2 -Il tuo nuovo saldo e' di e' " + saldo + "VERSAMENTO= "+ versamento + "Prelievo " + prelievo);
				
				
			}*/
			
			// AGGIORNO CSV
			aggiornaCsv(elencocarte, bancomat, listaElencoNuova);
		}// chiude il metodo versaPreleva
	

		// GESTISCE PAGAMENTO E SALDO PUNTI
	public double faiPagamento(double pagamento, int numerocarta, LinkedList<Carta> elencocarte,
			LinkedList listaElencoNuova, Carta bancomat) throws IOException {
			int saldoPunti = 0;
			int puntiFatti = 0;
			int puntiTot = 0;
			
			int i= trovaIndex(numerocarta, elencocarte);
			double saldo = Math.round((elencocarte.get(i).getSaldo()*1000.0)/1000.0);
			String nome = (elencocarte.get(i).getNome());
			saldoPunti = (elencocarte.get(i).getSaldopunti());
			
			if (pagamento > saldo) {
				System.out.println("Il tuo saldo attuale e' di e' " + saldo
						+ ". Siamo spiacenti. Non hai un saldo sufficente per effettuare il pagamento!");
			} else {
				saldo -= pagamento;
				// TRASFORMA DOUBLE PAGAMENTO IN INT VALUE
				int value = (int) pagamento;
				// CALCOLA PUNTI
				puntiFatti = (value / 2);
				saldoPunti += puntiFatti;
				System.out.println("Il tuo pagamento e' andato a buon fine!");
				System.out.println("Hai un nuovo saldo di  " + saldo);

				// +++++TEST PUNTI
				// System.out.println(saldoPunti);
				// System.out.println(puntiFatti);

				elencocarte.get(i).setSaldo(saldo);
				elencocarte.get(i).setSaldopunti(saldoPunti);
				aggiornaCsv(elencocarte,bancomat,listaElencoNuova);

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
				System.out.println(nome + ", il tuo saldo e' di " + saldoPunti + " punti");
				System.out.println();
				System.out.println("Ti ricordiamo che otterrai un punto ogni 2 e' di spesa!");
				System.out.println("Cerca i distributori convenzionati per accumulare punti!");
				System.out.println();
			}else {
				System.out.println(nome + ", il tuo saldo e' di " + saldo);
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

	
	


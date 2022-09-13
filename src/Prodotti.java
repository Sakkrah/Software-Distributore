import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Prodotti {
	
	//attributi
	private String ID;
	private String nome;
	private double prezzo;
	private int qnt;
	int saldo = 100;
	int index= 0;
	boolean admin =true;
	boolean disponibilita;
	
	
	BufferedReader Elenco = null;
	PrintWriter filemodificato =null;
	LinkedList<String> ListaElencoProdotti = new LinkedList<String>();
	LinkedList<Prodotti> elencoprodotti = new LinkedList<Prodotti>();
	LinkedList<String> listaElencoNuova = new LinkedList<String>();
	
	


	


	//metodo per leggere il file csv e aggiungerlo ad una LinkedList di stringhe.
	public Prodotti(String nomefile) throws IOException{
		String s = " ";
		
		Elenco = new BufferedReader(new FileReader(nomefile));
		
		while (s != null) {
			s = Elenco.readLine();
			if(s!=null) {
			ListaElencoProdotti.add((s));
			}
			}
			Elenco.close();
		}
	
	//metodo che riempie una LinkedList di <Prodotti> con array contenenti le informazioni dei prodotti.
	static void creaLinkedListdiArrayDatabase(LinkedList<Prodotti> elencoprodotti) throws IOException {
		
		elencoprodotti.clear();
		Prodotti macchinetta = new Prodotti("distributore.csv");
		
		for(int i=1; i<macchinetta.getListaElencoProdotti().size();i++) {
			String [] s = macchinetta.getListaElencoProdotti().get(i).toString().split(";");
			elencoprodotti.add(new Prodotti(s[0], s[1], Double.parseDouble(s[2]), Integer.parseInt(s[3])));
		}
		

	}
	
	//metodo che aggiunge alla LinkedList di stringhe le informazioni aggiornate della LinkedList di prodotti, passandole come stringhe.
	//La LinkedList di stringhe viene poi printata (in sovrascrizione) al file/database csv.
	static void aggiornaDatabase(LinkedList<String> listaElencoNuova, LinkedList<Prodotti> elencoprodotti, Prodotti macchinetta) throws IOException {

			listaElencoNuova.clear();
			listaElencoNuova.addFirst("ID;Nome;Prezzo;Quantita");
			
			for(int i=0; i<elencoprodotti.size();i++) {
				String elemento = elencoprodotti.get(i).getID() + ";"+ elencoprodotti.get(i).getNome() + ";" + elencoprodotti.get(i).getPrezzo() + ";" +elencoprodotti.get(i).getQnt();
				listaElencoNuova.add(elemento);
				PrintWriter filemodificato = null;
				filemodificato=new PrintWriter(new FileWriter("distributore.csv",false));
				
			for (int j=0; j<listaElencoNuova.size(); j++) {
				
				filemodificato.println(listaElencoNuova.get(j));
				}
			
				filemodificato.close();
			
			}
			
		}

	//metodo che mostra il menu della macchinetta all'utente
	static void mostraFileProdotti(LinkedList<Prodotti> elencoprodotti) {
		System.out.println("***************************");
		System.out.println("Buongiorno!");
		System.out.println("Fai la tua selezione!");
			for(int i=0; i<elencoprodotti.size();i++) {
			if (i==0) {System.out.println("***************************");}
			if (elencoprodotti.get(i).getQnt()==0) {
				System.out.println("Non disponibile!");
				i++;
			}
			
			else {
			System.out.print(elencoprodotti.get(i).getID()+" ");
			System.out.print(elencoprodotti.get(i).getNome()+": ");
			System.out.println(elencoprodotti.get(i).getPrezzo()+"€");
			}
			
			
			System.out.println("***************************");
		}
		
	}
	
	//metodo che accoglie l'input dell'utente e lo ridireziona all'acquisto, oppure alla modalita' admin
	static void scegliProdotto(LinkedList<Prodotti> elencoprodotti, double saldo,LinkedList<String> listaElencoNuova, Prodotti macchinetta) throws IOException {
		Scanner input = new Scanner(System.in);
		int index = 0;
		boolean interruttore = true;
		
		
		while (interruttore==true) {
			
		String risposta = input.next().toUpperCase();
		for (int i =0; i<elencoprodotti.size();i++) {
			if (elencoprodotti.get(i).getID().equals(risposta)) {
				interruttore=false;
				index = i;
				acquistoProdotto(elencoprodotti, saldo, index, listaElencoNuova, macchinetta);
				input.close();
			}
			
			
			if (i==elencoprodotti.size()) {
				System.out.println("ID sbagliato! Riprova!");
			}
				
			}
		
		
			
			}
			}
	
	//metodo che gestisce l'acquisto e l'inventario della macchinetta
	static void acquistoProdotto(LinkedList<Prodotti> elencoprodotti, double saldo, int index, LinkedList<String> listaElencoNuova, Prodotti macchinetta) throws IOException {
		Scanner input = new Scanner(System.in);
		

		System.out.println(elencoprodotti.get(index).getNome()+"!");
		System.out.println("Il prezzo è "+elencoprodotti.get(index).getPrezzo()+"€.");
		
		int value = elencoprodotti.get(index).getQnt();
		if (value==0) {
			System.out.println("Spiacente, non è disponibile!");
			System.out.println("Vuoi acquistare qualcos'altro? (y/n)");
			char scelta = input.next().charAt(0);
			
			if (scelta != 'y') {
				System.out.println("Arrivederci!");
				System.exit(0);
			}
			
			else {
				mostraFileProdotti(elencoprodotti);
				scegliProdotto(elencoprodotti, saldo, listaElencoNuova, macchinetta);
				 }
			
			}
		else if (value>0 && saldo > elencoprodotti.get(index).getPrezzo()) {
			System.out.println("Grazie!");
			value = value -1;
			Prodotti p = elencoprodotti.get(index);
			p.setQnt(value);
			
			saldo = saldo - elencoprodotti.get(index).getPrezzo();
				
			aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
			
			System.out.println("Vuoi acquistare qualcos'altro? (y/n)");
			char scelta = input.next().charAt(0);
			
			if (scelta != 'y') {
				System.out.println("Arrivederci!");
				System.exit(0);
			}
			
			else {
				mostraFileProdotti(elencoprodotti);
				scegliProdotto(elencoprodotti, saldo, listaElencoNuova, macchinetta);
				 }
			
		}
		
		input.close();
		}
	
	//metodo che gestisce l'accensione della macchinetta e l'eventualità di accesso con modalità amministrativa
	static void adminCheck(LinkedList<Prodotti> elencoprodotti,LinkedList<String> listaElencoNuova, Prodotti macchinetta, double saldo, int index, boolean admin) throws IOException {
		Scanner input = new Scanner(System.in);
		
		if (admin==true) {
			System.out.println("Benvenuto, amministratore!");
			System.out.println("Vuoi (1)gestire la macchinetta o (2)effettuare un acquisto?");
			String scelta = input.next();
			if (scelta.equals("1")) {
				adminMode(elencoprodotti, listaElencoNuova, macchinetta);
			}
			
			if (scelta.equals("2")) {
				mostraFileProdotti(elencoprodotti);
				scegliProdotto(elencoprodotti, saldo, listaElencoNuova, macchinetta);
			}
			
			
		}
		
		else
		
		System.out.println("(Premi un pulsante per accedere la macchinetta)");
		
		input.next();
	
		{
			mostraFileProdotti(elencoprodotti);
			scegliProdotto(elencoprodotti, saldo, listaElencoNuova, macchinetta);
		}
		input.close();
	}

	//metodo che permette di scegliere quale prodotto da gestire come amministratore, o se spegnere la macchinetta
	static void adminMode(LinkedList<Prodotti> elencoprodotti,LinkedList<String> listaElencoNuova, Prodotti macchinetta) throws IOException {
		Scanner input = new Scanner(System.in);
		int index = 0;
		
		mostraFileProdottiAdmin(elencoprodotti);
		System.out.println("Modalita' Admin Attivata");
		System.out.println("Scegliere Prodotto da aggiungere/rimuovere/cambiare:");
		System.out.println("Scegliere \"CREA\" per creare un nuovo prodotto in lista");
		//System.out.println("O scegliere \"CANCELLA\" per cancellare un prodotto in lista");
		System.out.println("Premi (OFF) per uscire dalla modalita' admin.");
		
		
		
		String scelta = input.next().toUpperCase();
	
		for (int i =0; i<elencoprodotti.size();i++) {
			if (elencoprodotti.get(i).getID().equals(scelta)) {
				index = i;
				gestioneProdottoAdmin(elencoprodotti,index,listaElencoNuova,macchinetta);
			}	
		}
		
		if (scelta.toUpperCase().equals("CREA")) {
			adminAggiungiProdotto(elencoprodotti, index, listaElencoNuova, macchinetta);
		}
		
		//if (scelta.toUpperCase().equals("CANCELLA")) {
		//	adminCancellaProdotto(elencoprodotti, listaElencoNuova, macchinetta);
		//}
		
		if (scelta.toUpperCase().equals("OFF")) {
			System.exit(0);
		}
		
		
		
		
		input.close();
	}
	
	//metodo che mostra il menu della macchinetta all'utente
	static void mostraFileProdottiAdmin(LinkedList<Prodotti> elencoprodotti) {
			for(int i=0; i<elencoprodotti.size();i++) {
				if (i==0) {System.out.println("***************************");}
				System.out.print(elencoprodotti.get(i).getID()+" ");
				System.out.print(elencoprodotti.get(i).getNome()+" ");
				System.out.println("(Quantita': "+elencoprodotti.get(i).getQnt()+")");
				System.out.println("***************************");
			}
			
		}
	
	//metodo che gestisce la quantità dei prodotti presenti nella macchinetta, come amministratore
	static void gestioneProdottoAdmin(LinkedList<Prodotti> elencoprodotti, int index,LinkedList<String> listaElencoNuova, Prodotti macchinetta) throws IOException {
		
		Scanner input = new Scanner(System.in);
		
		int value = elencoprodotti.get(index).getQnt();
		Prodotti p = elencoprodotti.get(index);
		
		System.out.println("Vuoi (1)aggiungere, (2)rimuovere alla quantita del prodotto o (3)cambiare il prezzo?");
		
		int valoreDifferenza;
		boolean interruttore=true;
		while (interruttore) {
		int scelta=input.nextInt();
		if (scelta==1) {
			System.out.println("Quanto vuoi aggiungere?");
			valoreDifferenza= input.nextInt();
			System.out.println("Sto aggiungendo "+valoreDifferenza+" ...");
			value = value + valoreDifferenza;
			p.setQnt(value);
			System.out.println("Operazione completata.");
			System.out.println("Vuoi fare un'altra modifica? (y/n)");
			char sceltaAdmin = input.next().charAt(0);
			
			if (sceltaAdmin != 'y') {
				System.out.println("Arrivederci!");
				System.exit(0);
			}
			
			else {
				mostraFileProdottiAdmin(elencoprodotti);
				adminMode(elencoprodotti, listaElencoNuova, macchinetta);
				
		}
			aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
		}
		
		else if (scelta==2) {
	
			System.out.println("Quanto vuoi rimuovere?");
			valoreDifferenza= input.nextInt();
			int valuePerRimozioneTotale = value;
			if (value - valoreDifferenza <= 0) {
				p.setQnt(0);
				System.out.println("Sono stati rimossi/e "+valuePerRimozioneTotale+" "+elencoprodotti.get(index).getNome()+"!");
				
				aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
				}
			else {
				
				value = value - valoreDifferenza;
				System.out.println("Rimossi/e "+valoreDifferenza+" "+elencoprodotti.get(index).getNome()+"!");
				
				p = elencoprodotti.get(index);
				p.setQnt(value);
				
				
				System.out.println("Operazione completata.");
				System.out.println("Vuoi fare un'altra modifica? (y/n)");
				char sceltaAdmin = input.next().charAt(0);
				
				if (sceltaAdmin != 'y') {
					System.out.println("Arrivederci!");
					System.exit(0);
				}
				
				else {
					mostraFileProdottiAdmin(elencoprodotti);
					adminMode(elencoprodotti, listaElencoNuova, macchinetta);
					
			}
				aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
			}
			
			}
		
		else if (scelta==3) {
			System.out.println("Quale prezzo vuoi impostare?");
			int prezzoNuovo= input.nextInt();
			p.setPrezzo(prezzoNuovo);
			System.out.println("Operazione completata.");
			System.out.println("Vuoi fare un'altra modifica? (y/n)");
			char sceltaAdmin = input.next().charAt(0);
			
			if (sceltaAdmin != 'y') {
				System.out.println("Arrivederci!");
				System.exit(0);
			}
			
			else {
				mostraFileProdottiAdmin(elencoprodotti);
				adminMode(elencoprodotti, listaElencoNuova, macchinetta);
				
				}
			aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
				}
				
		
				else { System.out.println("Scelta non valida."); }
		
			}
				input.close();
			}

	private static void adminAggiungiProdotto(LinkedList<Prodotti> elencoprodotti, int index,LinkedList<String> listaElencoNuova, Prodotti macchinetta) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Inserisci l'ID per il nuovo prodotto:");
		String ID = input.next().toUpperCase();
		System.out.println("Inserisci il nome per il nuovo prodotto:");
		String nome = "";
		input.nextLine();
		nome = input.nextLine();
		System.out.println("Inserisci il prezzo per il nuovo prodotto:");
		Double prezzo = input.nextDouble();
		System.out.println("Inserisci la quantita' per il nuovo prodotto:");
		int quantita = input.nextInt();
		elencoprodotti.add(new Prodotti(ID, nome, prezzo, quantita));
		aggiornaDatabase(listaElencoNuova, elencoprodotti, macchinetta);
		System.out.println("Prodotto aggiunto!");
		
		System.out.println("Vuoi creare un altro prodotto/tornare al menu principale o spegnere la macchinetta?");
		System.out.println("(CREA/TORNA/OFF)");
		String scelta = input.next().toUpperCase();
		if (scelta.toUpperCase().equals("CREA")) {
			adminAggiungiProdotto(elencoprodotti, index, listaElencoNuova, macchinetta);
		}
		
		if (scelta.toUpperCase().equals("TORNA")) {
			adminMode(elencoprodotti, listaElencoNuova, macchinetta);
		}
		
		if (scelta.toUpperCase().equals("OFF")) {
			input.close();
			System.exit(0);
		}
		
	}
	
	/*
	private static void adminCancellaProdotto(LinkedList<Prodotti> elencoprodotti,
		LinkedList<String> listaElencoNuova, Prodotti macchinetta) {
		Scanner input = new Scanner(System.in);
		boolean interruttore=true;
		System.out.println("Inserisci l'ID del prodotto da cancellare:");		
		while (interruttore==true) {
			
			String scelta = input.next().toUpperCase();
			System.out.println(elencoprodotti.get(0).getID());
			for (int i =0; i<elencoprodotti.size();i++) {
				if (elencoprodotti.get(i).getID().equals(scelta)) {
					interruttore=false;
					elencoprodotti.remove(i);
					input.close();
				}
				
				
				if (i==elencoprodotti.size()) {
					System.out.println("ID sbagliato! Riprova!");
				}
					
				}
			
			
				
				}
		
	}
*/
	//costruttore per la LinkedList di <Prodotti>
	Prodotti(String ID, String nome, double prezzo, int qnt){
		this.ID=ID;
		this.nome=nome;
		this.prezzo=prezzo;
		this.qnt=qnt;
		
}
	
	
	//getter setter
	

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
	
	public int getSaldo() {
		return saldo;
	}



	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}



	public BufferedReader getElenco() {
		return Elenco;
	}



	public void setElenco(BufferedReader elenco) {
		Elenco = elenco;
	}



	public PrintWriter getFilemodificato() {
		return filemodificato;
	}



	public void setFilemodificato(PrintWriter filemodificato) {
		this.filemodificato = filemodificato;
	}



	
	public LinkedList<Prodotti> getElencoprodotti() {
		return elencoprodotti;
	}



	public void setElencoprodotti(LinkedList<Prodotti> elencoprodotti) {
		this.elencoprodotti = elencoprodotti;
	}
	
	public LinkedList<String> getListaElencoNuova() {
		return listaElencoNuova;
	}



	public void setListaElencoNuova(LinkedList<String> listaElencoNuova) {
		this.listaElencoNuova = listaElencoNuova;
	}


	public LinkedList<String> getListaElencoProdotti() {
		return ListaElencoProdotti;
	}



	public void setListaElencoProdotti(LinkedList<String> listaElencoProdotti) {
		ListaElencoProdotti = listaElencoProdotti;
	}
}

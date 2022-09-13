import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Carburanti {
	//CREO UNA CLASSE PER RACCOGLIERE I TIPI DI CARBURANTI DISPONIBILI E LE LORO INFORMAZIONI
	
	//ATTRIBUTI
	
	private String nome;
	private double prezzo;
	private double disponibilita;
	private String pompa;
	
	//COSTRUTTORI
		//CREO UN COSTRUTTORE CHE ACCOGLIERA' I DATI DI OGNI CARBURANTE PRESENTE NEL FILE CSV
		Carburanti(String nome, double prezzo, double disponibilita , String pompa){
			this.setNome(nome);
			this.setPrezzo(prezzo);
			this.setDisponibilita(disponibilita);
			this.setPompa(pompa);
		}
		
	//GETTER AND SETTER

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

		

		public String getPompa() {
			return pompa;
		}

		public void setPompa(String pompa) {
			this.pompa = pompa;
		}

		public double getDisponibilita() {
			return disponibilita;
		}

		public void setDisponibilita(double disponibilita) {
			this.disponibilita = disponibilita;
		}







	
	//ATTRIBUTI
	public BufferedReader Elenco = null;//CREO UNA VARIABILE PER LEGGERE IL FILE
	public PrintWriter filemodificato =null;//CREO UNA VARIABILE PER MODIFICARE IL FILE
	public LinkedList ListaElenco = new LinkedList();//CREO UNA LISTA IN CUI ANDRO' A METTERE LE RIGHE DEL FILE
	
	
	
	//COSTRUTTORI
		
		//CREO UN COSTRUTTORE CHE SERVIRA' PER LEGGERE IL FILE CSV
		Carburanti(String nomefile){
			String s = " ";
			try {
				Elenco = new BufferedReader(new FileReader(nomefile));
				while (s != null) {
					s = Elenco.readLine();
					if(s!=null) {
						ListaElenco.add((s));
					}//CHIUDE IF
				}//CHIUDE WHILE
			Elenco.close();
			
			} catch (Exception e) {
				System.out.println("Errore nella lettura del file");
			}//CHIUDE TRY
		}
		
		//GETTER AND SETTER
		public LinkedList getListaElenco() {
			return ListaElenco;
		}


		public void setListaElenco(LinkedList listaElenco) {
			ListaElenco = listaElenco;
		}
		
		public PrintWriter getFilemodificato() {
			return filemodificato;
		}

		public void setFilemodificato(PrintWriter filemodificato) {
			this.filemodificato = filemodificato;
		}
		
		//METODI
		
		public static void leggifilecarburanti(LinkedList<Carburanti> elencocarburanti) {
			//DEVO AGGIORNARE LA LISTA DEI PRODOTTI LEGGENDO DAL FILE
					//MA LA MIA LISTA NON E' VUOTA QUINDI SE RILEGGO IL FILE, CREO DEI DOPPIONI
					//DEVO QUINDI PRIMA SVUOTARE LA LISTA POI RIRIEMPIRLA DAL FILE
					elencocarburanti.clear();//SVUOTA LISTA
					//CREO NUOVA ISTANZA DI FILECARBURANTE PER RILEGGERE IL FILE AGGIORNATO
					Carburanti filecarburante = new Carburanti("carburanti.csv");
					//FACCIO UN CICLO PER CARICARE LA LINKED LIST ELENCOCARBURANTI
					for(int i=1; i<filecarburante.getListaElenco().size();i++) {
						String [] rigo = filecarburante.getListaElenco().get(i).toString().split(";");
						elencocarburanti.add(new Carburanti(rigo[0], Double.parseDouble(rigo[1]), Double.parseDouble(rigo[2]), rigo[3]));
		}
		}
		
		
		
		public static void aggiornaCsv(LinkedList<String> listaElencoNuova, LinkedList<Carburanti> elencocarburanti, Carburanti filecarburanti) throws IOException {

			listaElencoNuova.clear();
			//VADO A RISCRIVERE L'INTESTAZIONE DEI VARI CAMPI
			listaElencoNuova.addFirst("Nome;Prezzo;Disponibilita;Pompa");
			
			for(int i=0; i<elencocarburanti.size();i++) {
				String elemento = elencocarburanti.get(i).getNome() + ";"+ elencocarburanti.get(i).getPrezzo() + ";" + elencocarburanti.get(i).getDisponibilita() + ";" +elencocarburanti.get(i).getPompa();
				listaElencoNuova.add(elemento);
				PrintWriter filemodificato = null;
				filemodificato=new PrintWriter(new FileWriter("carburanti.csv",false));
				
			for (int j=0; j<listaElencoNuova.size(); j++) {
				
				filemodificato.println(listaElencoNuova.get(j));
				}
			
				filemodificato.close();
			
			}
			
		}
		
			
		

		public BufferedReader getElenco() {
			return Elenco;
		}

		public void setElenco(BufferedReader elenco) {
			Elenco = elenco;
		}
}
		


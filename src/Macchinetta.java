import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

	public class Macchinetta {
	BufferedReader Elenco = null;
	PrintWriter filemodificato =null;
	LinkedList<String> ListaElenco = new LinkedList<String>();
	
	
	//metodo per leggere il file csv e aggiungerlo ad una LinkedList di stringhe.
	Macchinetta(String nomefile) throws IOException{
		
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
	
	//metodo che riempie una LinkedList di <Prodotti> con array contenenti le informazioni dei prodotti.
	static void creaLinkedListdiArrayDatabase(LinkedList<Prodotti> elencoprodotti) throws IOException {
		
		elencoprodotti.clear();
		Macchinetta macchinetta = new Macchinetta("distributore.csv");
		
		for(int i=1; i<macchinetta.getListaElenco().size();i++) {
			String [] s = macchinetta.getListaElenco().get(i).toString().split(",");
			elencoprodotti.add(new Prodotti(s[0], s[1], Double.parseDouble(s[2]), Integer.parseInt(s[3]) ));
		}
		

	}
	
	//metodo che aggiunge alla LinkedList di stringhe le informazioni aggiornate della LinkedList di prodotti, passandole come stringhe.
	//La LinkedList di stringhe viene poi printata (in sovrascrizione) al file/database csv.
	static void aggiornaDatabase(LinkedList<String> listaElencoNuova, LinkedList<Prodotti> elencoprodotti, Macchinetta macchinetta) throws IOException {

			listaElencoNuova.clear();
			listaElencoNuova.addFirst("ID,Nome,Prezzo,Quantita");
			
			for(int i=0; i<elencoprodotti.size();i++) {
				String elemento = elencoprodotti.get(i).getID() + ","+ elencoprodotti.get(i).getNome() + "," + elencoprodotti.get(i).getPrezzo() + "," +elencoprodotti.get(i).getQnt();
				listaElencoNuova.add(elemento);
				PrintWriter filemodificato = null;
				filemodificato=new PrintWriter(new FileWriter("distributore.csv",false));
				
			for (int j=0; j<listaElencoNuova.size(); j++) {
				
				filemodificato.println(listaElencoNuova.get(j));
				}
			
				filemodificato.close();
			
			}
			
		}


	//getter & setter

	private LinkedList<String> getListaElenco() {
			return ListaElenco;
		}

		}


	

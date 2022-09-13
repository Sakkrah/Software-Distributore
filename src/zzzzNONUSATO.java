/*

import java.io.IOException;
import java.util.LinkedList;

public class Macchinetta {

	public static void main(String[] args) throws IOException  {
		
		//PROGRAMMA MACCHINETTA-DISTRIBUTORE
		
		//funzionalità :
		//1) Gestire un inventario(database) in modo dinamico e illimitato.
		//2) Modalità amministrativa per aggiornare l'inventario manualmente.
		
		
		Prodotti macchinetta = new Prodotti("distributore.csv");
		LinkedList<String> listaElencoNuova = new LinkedList<String>();
		LinkedList<Prodotti> elencoprodotti = new LinkedList<Prodotti>();		
		
		int saldo = 100;
		int index= 0;
		boolean admin =true;
				
		Prodotti.creaLinkedListdiArrayDatabase(elencoprodotti);
		
		Prodotti.adminCheck(elencoprodotti, listaElencoNuova, macchinetta, saldo, index, admin);
			
	}
	
		
	
	}

*/

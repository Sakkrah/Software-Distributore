import java.io.IOException;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws IOException  {
		
		Macchinetta macchinetta = new Macchinetta("distributore.csv");
		LinkedList<String> listaElencoNuova = new LinkedList<String>();
		LinkedList<Prodotti> elencoprodotti = new LinkedList<Prodotti>();		
		
		int saldo = 100;
		int index= 0;
		//saldo fittizio per test (if)
			
		Macchinetta.creaLinkedListdiArrayDatabase(elencoprodotti);
		
		Prodotti.adminCheck(elencoprodotti, listaElencoNuova, macchinetta, saldo, index);
		
		
	}	
	
	}



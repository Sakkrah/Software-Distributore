import java.io.IOException;

public class Main {

	
	//PROGRAMMA MACCHINETTA ALIMENTARE, BENZINAIO E BANCOMAT
	
	//FEATURE MACCHINETTA:
	//1) Acquisto multiplo di prodotti (gestiti da un database).
	//2) Riconoscienza automatica della disponibilita' dei prodotti.
	//3) Modalita' amministrativa.
	//4) Possibilita' di cambiare la quantita' di multipli prodotti in successione.
	//5) Possibilita' di cambiare il prezzo di multipli prodotti in successione.
	//6) Possibilita' di aggiungere multipli prodotti in successione.
	
	
	public static void main(String[] args) throws IOException  {
	
	Carburanti carburanti = new Carburanti("carburanti.csv");
	Carta bancomat = new Carta("carte.csv");
	Prodotti macchinetta = new Prodotti("distributore.csv");
	Distributore distributore = new Distributore();
	
	bancomat.mostraMenuCarta(macchinetta,carburanti,bancomat,distributore);
	
	}		
	
	}
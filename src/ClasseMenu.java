


import java.util.ArrayList;
import java.util.Scanner;

public class ClasseMenu {
	// CLASSE CHE GESTISCE IL MENU' E LA SCELTA DELL'UTENTE

	// ATTRIBUTI
	private Scanner tastiera = new Scanner(System.in);
	private int scelta;

	// COSTRUTTORI
	public ClasseMenu(ArrayList<String> vocidimenu) {

		boolean interruttore = true;
		//CICLO WHILE PER AGGIUNGERE LE SINGOLE VOCI
		while (interruttore) {

			for (int i = 0; i < vocidimenu.size(); i++) {
				System.out.println((i + 1) + " - " + vocidimenu.get(i).toString());
			}
			System.out.println("0 - Uscita");

			try {
				scelta = tastiera.nextInt();
				interruttore = false;
			} catch (Exception e) {
				System.out.println("Attenzione valore non valido");
				System.out.println("...Ripeti la scelta");
				tastiera.next();
			}

		}

	}
	
	//GETTERS AND SETTERS
	public int getScelta() {
		return scelta;
	}

	public void setScelta(int scelta) {
		this.scelta = scelta;
	}

}

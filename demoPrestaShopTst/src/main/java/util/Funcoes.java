package util;

public class Funcoes {

	
	public static Double tratarPrecoToDouble(String texto) {
		texto = texto.replace("$", "");
		Double retorno = Double.parseDouble(texto);
		return retorno;
	}
	
}

package util;

public class Funcoes {

	
	public static Double tratarPrecoToDouble(String texto) {
		texto = texto.replace("$", "");
		Double retorno = Double.parseDouble(texto);
		return retorno;
	}
	
	public static String limparTexto (String textoEntrada, String textoARetirar) {
		String textoTratado = textoEntrada.replace(textoARetirar, "");
		return textoTratado;
	}
	
}

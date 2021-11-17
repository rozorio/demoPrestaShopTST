package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalPage;
import pages.OrderPage;
import pages.ProdutoPage;
import util.Funcoes;

/* 
 * Classe HomePageTests: re�ne os testes realizados na home page
 */

public class HomePageTests extends BaseTests {

	/*
	 * 
	 * public void testContProds...: M�todo que ir� carregar p�g inicial e validar
	 * correta exibi��o da lista de produtos ** carregarPagInic(): M�todo da classe
	 * BaseTests ** assertThat...: compara��o do Hamcrest, compara se valor
	 * retornado pelo homepage.contProds() � 8
	 */

	@Test // anota��o JUnit, indica que s�o m�todos de teste.
	public void testContProds_oitoProds() {
		carregarPagInic();
		assertThat(homePage.contarProdsExibidos(), is(8));
	}

	@Test
	public void testValidCarVazio_zeroItem() {
		int prodCarrinho = homePage.validCarVazio();
		assertThat(prodCarrinho, is(0));
	}

	ProdutoPage produtoPage;
	String nomeProd_PP;
	String precoProd_PP;

	@Test
	public void testValidSelecProduto_descEPreco() {
		/*
		 * Navega � homePage; Recupera nome e pre�o do primeiro produto (�ndice 0) na
		 * homePage; Clica no primeiro produto; Recupera nome e pre�o na p�gina do
		 * produto; Realiza compara��o entre nomes e pre�os.
		 */
		int indice = 0;
		String nomeProd_HP = homePage.obterNomeProd(indice);
		String precoProd_HP = homePage.obterPrecoProd(indice);
		produtoPage = homePage.clicarProduto(indice); // cria inst�ncia da classe ProdutoPage
		nomeProd_PP = produtoPage.obterNomeProd();
		precoProd_PP = produtoPage.obterPrecoProd();
		assertThat(nomeProd_HP.toUpperCase(), is(nomeProd_PP.toUpperCase()));
		assertThat(precoProd_HP, is(precoProd_PP));
	}

	LoginPage loginPage;
	String cliente = "Raphael Ozorio";
	String email = "rozorio@teste.com";
	String password = "testeTeste";

	@Test
	public void testValidarLogin_logonComSucesso() {
		/*
		 * Clicar em Sign in na HomePage Preencher usu�rio e senha na LoginPage Clicar
		 * em Sign in na LoginPage Verificar usu�rio logado
		 */
		loginPage = homePage.clicarSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		loginPage.clicarSignIn();
		assertEquals(loginPage.validarLogOn(cliente), true);
	}
	
	/* TESTE PARAMETRIZADO
	 * - Possibilita uso de .csv como massa de teste
	 * - @ParameterizedTest --> define como teste parametrizado (anota��o JUnit);
	 * - @CsvFileSource (dir arquivo, linha a pular, delimitador) - Anota��o JUnit
	 * - Par�metros do teste na mesma ordem que aparecem no arquivo, sempre em formato String
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_LogadoComSucesso(String tipoTeste, String email, String password, String nomeUsuario, String resultado) {
		
		loginPage = homePage.clicarSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		loginPage.clicarSignIn();
		
		Boolean loginComSucesso;
		if(resultado.equals("positivo")) {
			loginComSucesso = true;
		} else {
			loginComSucesso = false;
		}
		
		assertEquals(loginPage.validarLogOn(nomeUsuario), loginComSucesso);
		
		//M�todo implementado na classe BaseTests
		capturarTela(tipoTeste, resultado);
		
		if(loginComSucesso) {
			homePage.clicarSignOut();
		}
	}
	

	ModalPage modalProduto;
	String selTam = "M";
	String selCor = "Black";
	Integer selQtd = 2;
	String subTotModal;

	@Test
	public void testIncluirProdCarrinho_carrinhoSucesso() {
		/*
		 * Procedimento: - Validar log on; - Selecionar produto; - Selecionar tamanho,
		 * cor e qtde - Adicionar ao carrinho - Validar carrinho.
		 */

		LoginPage logPg = homePage.clicarSignIn();

		// VALIDAR LOG ON
		if (!logPg.validarLogOn(cliente)) {
			testValidarLogin_logonComSucesso();
		}

		// SELECIONAR PRODUTO
		testValidSelecProduto_descEPreco();

		// SELECIONAR TAMANHO
		produtoPage.selecionarTamanho(selTam);
		// List<String> tamanhos = produtoPage.obteTamanhoSelecionado();

		// SELECIONAR COR
		produtoPage.selecionarCor("Black");

		// ALTERAR QUANTIDADE
		produtoPage.alterarQuantidade(selQtd);

		// ADICIONAR AO CARRINHO
		modalProduto = produtoPage.adicionarCarrinho();

		// VALIDA��ES CARRINHO
		assertTrue(modalProduto.validarMsgSucesso().endsWith("Product successfully added to your shopping cart"));
		assertThat(modalProduto.obterNomeProduto().toUpperCase(), is(nomeProd_PP.toUpperCase()));
		String precoProdModal = modalProduto.obterPrecoProduto();
		assertThat(precoProdModal, is(precoProd_PP));
		assertThat(modalProduto.obterTamanho(), is(selTam));
		assertThat(modalProduto.obterCor(), is(selCor));
		assertThat(modalProduto.obterQuantidade(), is(selQtd.toString()));

		precoProdModal = precoProdModal.replace("$", "");
		Double precoProdDouble = Double.parseDouble(precoProdModal);

		subTotModal = modalProduto.obterSubTotal();
		subTotModal = subTotModal.replace("$", "");
		Double subTotDouble = Double.parseDouble(subTotModal);

		assertThat(precoProdDouble * selQtd, is(subTotDouble));
	}

	CheckoutPage checkoutPage;
	Double subTotTPDbl;
	Double totTaxITPDbl;
	Double shipTPDbl;

	@Test
	public void testValidarCarrinho_infosPersists() {

		Double precoProd;
		Double subTotProd;
		Integer qtdeProd;

		// SELECIONAR PRODUTO E FAZER CHECKOUT
		testIncluirProdCarrinho_carrinhoSucesso();
		CarrinhoPage carrinhoPage = modalProduto.fazerChckOutPedido();

		// VALIDAR INFOS PROD
		assertThat(carrinhoPage.obterNomeProd().toUpperCase(), is(nomeProd_PP.toUpperCase()));
		assertThat(carrinhoPage.obterPrecoProd(), is(precoProd_PP));
		precoProd = Funcoes.tratarPrecoToDouble(carrinhoPage.obterPrecoProd());
		assertThat(carrinhoPage.obterTamProd(), is(selTam));
		assertThat(carrinhoPage.obterCorProd(), is(selCor));
		qtdeProd = carrinhoPage.obterQtdeProd();
		assertThat(qtdeProd, is(selQtd));
		subTotProd = Funcoes.tratarPrecoToDouble(carrinhoPage.obterSubTotProd());
		assertThat(subTotProd, is(qtdeProd * precoProd));

		// VALIDAR INFOS PAINEL TOTAL
		String qtdeTP = carrinhoPage.obterQtdeTP();
		qtdeTP = qtdeTP.replace(" items", "");
		Integer qtdeTPInt = Integer.parseInt(qtdeTP);
		subTotTPDbl = Funcoes.tratarPrecoToDouble(carrinhoPage.obterSubTotTP());
		String shipTPStr = "$7.00";
		Double taxesTP = 0.0;
		assertThat(qtdeTPInt, is(qtdeProd));
		assertThat(subTotTPDbl, is(subTotProd));
		assertThat(carrinhoPage.obterShipTP(), is(shipTPStr));
		shipTPDbl = Funcoes.tratarPrecoToDouble(shipTPStr);
		Double totTaxETPDbl = Funcoes.tratarPrecoToDouble(carrinhoPage.obterTotTaxETP());
		assertThat(totTaxETPDbl, is(subTotTPDbl + shipTPDbl));
		Double taxTPDbl = Funcoes.tratarPrecoToDouble(carrinhoPage.obtertaxesTP());
		totTaxITPDbl = Funcoes.tratarPrecoToDouble(carrinhoPage.obterTotTaxITP());
		assertThat(totTaxITPDbl, is(totTaxETPDbl + taxTPDbl));
		assertThat(taxTPDbl, is(taxesTP));

		checkoutPage = carrinhoPage.clicarBtnCheckout();
	}

	@Test
	public void testefetuarCheckout_checkoutRealizado() {

		testValidarCarrinho_infosPersists();

		// VALIDAR VALORES;
		Double subTotChkt = Funcoes.tratarPrecoToDouble(checkoutPage.obterSubTotal());
		assertThat(subTotChkt, is(subTotTPDbl));
		Double totalTIChkt = Funcoes.tratarPrecoToDouble(checkoutPage.obterTotalTI());
		assertThat(totalTIChkt, is(totTaxITPDbl));
		Double shippVal = Funcoes.tratarPrecoToDouble(checkoutPage.obterShippingValChkt());
		assertThat(shippVal, is(shipTPDbl));

		// VALIDAR ENDERE�O;
		assertTrue(checkoutPage.obterTituloAddresses().contains("ADDRESSES"));
		assertTrue(checkoutPage.obterAddrRadioChkd());
		assertTrue(checkoutPage.obterAddress().startsWith(cliente));
		checkoutPage.clicarAddressContinue();

		// VALIDAR FORMA DE ENVIO;
		assertTrue(checkoutPage.obterTitShipMthd().contains("SHIPPING METHOD"));
		assertThat(checkoutPage.obterTipoCarrier(), is("My carrier"));
		assertTrue(checkoutPage.obterTipoCarrChkd());
		String valCarStrg = checkoutPage.obterPrecoCarrier();
		valCarStrg = Funcoes.limparTexto(valCarStrg, " tax excl.");
		Double valCarDbl = Funcoes.tratarPrecoToDouble(valCarStrg);
		assertThat(valCarDbl, is(shippVal));
		checkoutPage.clicarShippingMthdCont();

		// SELECIONAR FORMA DE PAGAMENTO (CHEQUE);
		assertTrue(checkoutPage.obterTitPayment().contains("PAYMENT"));
		checkoutPage.clicarPaymentCheck();
		String valAmount = Funcoes.limparTexto(checkoutPage.obterAmountPayment(), " (tax incl.)");
		Double valAmountDbl = Funcoes.tratarPrecoToDouble(valAmount);
		assertThat(valAmountDbl, is(totalTIChkt));
		checkoutPage.clicarIAgree();
		assertTrue(checkoutPage.obterIAgreeChkd());
	}

	@Test
	public void testFinalizarPedido_finalizadoComSucesso() {

		// PR�-CONDI��O: VALIDAR CHECKOUT
		testefetuarCheckout_checkoutRealizado();

		// TESTES
		// EFETUAR O CHECKOUT
		OrderPage orderPage = checkoutPage.clicarBtnOrder();

		// VALIDAR INFOS ORDER PAGE
		assertTrue(orderPage.obterTitPedido().toUpperCase().endsWith("YOUR ORDER IS CONFIRMED"));
		assertThat(orderPage.obterEmail(), is(email));
		assertThat(orderPage.obterTotProds(), is(subTotTPDbl));
		assertThat(orderPage.obterTotTxInc(), is(totTaxITPDbl));
		assertThat(orderPage.obterPymntMethod(), is("check"));

	}

}

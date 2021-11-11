package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ModalProduto;
import pages.ProdutoPage;

/* 
 * Classe HomePageTests: re�ne os testes realizados na home page
 */

public class HomePageTests extends BaseTests {

	/*
	 * 
	 * public void testContProds...: M�todo que ir� carregar p�g inicial e validar correta exibi��o da lista de produtos
	 * ** carregarPagInic(): M�todo da classe BaseTests
	 * ** assertThat...: compara��o do Hamcrest, compara se valor retornado pelo homepage.contProds() � 8
	 */
	
	@Test     //anota��o JUnit, indica que s�o m�todos de teste.
	public void testContProds_oitoProds() {
		carregarPagInic();      
		assertThat(homePage.contProds(),is(8)); 
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
		/* Navega � homePage;
		 * Recupera nome e pre�o do primeiro produto (�ndice 0) na homePage;
		 * Clica no primeiro produto;
		 * Recupera nome e pre�o na p�gina do produto;
		 * Realiza compara��o entre nomes e pre�os.
		 */
		int indice = 0;
		String nomeProd_HP = homePage.obterNomeProd(indice);
		String precoProd_HP = homePage.obterPrecoProd(indice);
		produtoPage = homePage.clicarProduto(indice);  //cria inst�ncia da classe ProdutoPage
		nomeProd_PP = produtoPage.obterNomeProd();
		precoProd_PP = produtoPage.obterPrecoProd();
		assertThat(nomeProd_HP.toUpperCase(), is(nomeProd_PP.toUpperCase()));
		assertThat(precoProd_HP, is(precoProd_PP));
	}

	LoginPage loginPage;
	@Test
	public void testValidarLogin_logonComSucesso() {
		/*Clicar em Sign in na HomePage
		 * Preencher usu�rio e senha na LoginPage
		 * Clicar em Sign in na LoginPage
		 * Verificar usu�rio logado
		 */
		String email = "rozorio@teste.com";
		String password = "testeTeste";
		loginPage = homePage.clicarSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		loginPage.clicarSignIn();
		assertEquals(loginPage.validarLogOn("Raphael Ozorio"), true);
	}
	

	 
	@Test
	public void incluirProdCarrinho_carrinhoSucesso() {
		/* Procedimento:
		 * - Validar log on;
		 * - Selecionar produto;
		 * - Selecionar tamanho, cor e qtde
		 * - Adicionar ao carrinho
		 * - Validar carrinho.
		 */
		
		String selTam = "M";
		String selCor = "Black";
		Integer selQtd = 2;
		
		LoginPage logPg = homePage.clicarSignIn();
		
		//VALIDAR LOG ON
		if(!logPg.validarLogOn("Raphael Ozorio")) {
			testValidarLogin_logonComSucesso();
		}
		
		//SELECIONAR PRODUTO
		testValidSelecProduto_descEPreco();
		
		//SELECIONAR TAMANHO
		produtoPage.selecionarTamanho(selTam);
		//List<String> tamanhos = produtoPage.obteTamanhoSelecionado();
		
		//SELECIONAR COR
		produtoPage.selecionarCor();
		
		//ALTERAR QUANTIDADE
		produtoPage.alterarQuantidade(selQtd);
		
		//ADICIONAR AO CARRINHO
		ModalProduto modalProduto = produtoPage.adicionarCarrinho();
		
		//VALIDA��ES CARRINHO
		assertTrue(modalProduto.validarMsgSucesso().endsWith("Product successfully added to your shopping cart"));
		assertThat(modalProduto.obterNomeProduto().toUpperCase(), is(nomeProd_PP.toUpperCase()));
		String precoProdModal = modalProduto.obterPrecoProduto();
		assertThat(precoProdModal, is(precoProd_PP));
		assertThat(modalProduto.obterTamanho(), is(selTam));
		assertThat(modalProduto.obterCor(), is(selCor));
		assertThat(modalProduto.obterQuantidade(), is(selQtd.toString()));
		
		precoProdModal = precoProdModal.replace("$", "");
		Double precoProdDouble = Double.parseDouble(precoProdModal);
		
		String subTotModal = modalProduto.obterSubTotal();
		subTotModal = subTotModal.replace("$", "");
		Double subTotDouble = Double.parseDouble(subTotModal);
		
		assertThat(precoProdDouble * selQtd, is(subTotDouble));
	}

}

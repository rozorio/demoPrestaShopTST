#language: pt
Funcionalidade: Compra Produtos

  @validacaoinicial
  Cenario: Home Page ser exibida com listagem dos produtos disponiveis
    Dado usuario na homepage
    Quando usuario nao logado
    Entao exibido listagem de 8 produtos
    E verificado carrinho de compras com 0 item

  @fluxopadrao
  Esquema do Cenario: Usuario adiciona produtos ao carrinho com sucesso
    Dado usuario na homepage
    E usuario esta logado
    E usuario clica no produto de posicao <posicao>
    E nome do produto na homepage e na produtopage eh <nomeProd>
    E preco do produto na homepage e na produtopage eh <precoProd>
    E tamanho do produto selecionado na produtopage eh <tamProd>
    E cor do produto selecionado na produtopage eh <corProd>
    E quantidade informada para aquisicao na produtopage eh <qtdeProd>
    Entao eh exibido modalpage com <nomeProd>, <precoProd>, <tamProd>, <corProd>, <qtdeProd>

    Exemplos: 
      | posicao | nomeProd                      | precoProd | tamProd | corProd | qtdeProd |
      |       0 | "Hummingbird printed t-shirt" | "$19.12"  | "M"     | "Black" |        2 |
      |       1 | "Hummingbird printed sweater" | "$28.72"  | "XL"    | "N/A"   |        3 |
#Cen2: Log in de usuario cadastrado bem sucedido
#Cen3: Usuario seleciona produtos para o carrinho com sucesso
#Cen4: Usuario realiza procedimento de checkout com sucesso
#-----> Implementar para adicionar produto sem size

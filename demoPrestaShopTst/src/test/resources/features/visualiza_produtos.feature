#language: pt
Funcionalidade: Visualiza Produtos

  Cenario: Home Page ser exibida com listagem dos produtos disponiveis
    Dado usuario na homepage
    Quando usuario nao logado
    Entao exibido listagem de 8 produtos
    E verificado carrinho de compras com 0 item
    
#Cen2: Log in de usuario cadastrado bem sucedido
#Cen3: Usuario seleciona produtos para o carrinho com sucesso
#Cen4: Usuario realiza procedimento de checkout com sucesso

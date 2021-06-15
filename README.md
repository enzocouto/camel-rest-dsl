# camel-rest-dsl


Atualizar cardápio:
  Um Timer consulta o servico abaixo de 1 em 1 hora.Para cada item na lista atribui um desconto de 10% e persiste na tabela "Burguer".
  Servico do cardapio: https://rootsmanager.herokuapp.com/burguer/consulta


Buscar lista de Burguers com desconto de 10% no preco 
 através do endpoint abaixo, retorna os itens do cardápio com o desconto. A consulta é realizada na tabela Burguer.
 GET : http://localhost:8080/getCardapio

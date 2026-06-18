# LINK DO DEPLOY:
https://studycard-zeqj.onrender.com/


# Identificação
<b>Nome:</b> Davi Paiani Libardoni<br>
**Curso:** Sistemas de Informação

# Proposta
Desenvolvimento de um software de flashcards que segue os princípios de um SRS (Spaced Repetition System), que se baseia em repetição de conceitos em intervalos específicos para melhor fixação do aprendizado. O funcionamento básico é:

- O usuário cria/baixa um Deck de cartas sobre um tópico que deseja aprender. Ex: Língua Inglesa
- Esse baralho irá apresentar ao usuário X cartas novas e Y cartas que ele já viu, todos os dias.
- Em uma carta de revisão, o usuário terá que ler o enunciado da carta, e tentar lembrar do que está no fundo da carta. Ex: Apple -> Maçã
- Dependendo da facilidade que o usuário sentiu em lembrar corretamente, ele vai marcar o resultado dessa revisão como "Erro", "Difícil" ou "Fácil".
- Baseado nesse resultado, o sistema irá determinar uma data para que essa carta apareça novamente. Se o resultado foi "Erro", vai aparecer novamente em breve. Se foi fácil, vai voltar daqui a alguns dias ou semanas.


# Processo de desenvolvimento
Comecei fazendo apenas as classes comuns de Java, Card, Deck, ReviewQueue, com construtores getters e setters e alguns enums para propriedades mais específicas. Essa parte não teve complicações.

Depois, pensando no futuro, comecei a pesquisar sobre maneiras de manter persistência que preserva as relações dos objetos corretamente, e fui vendo sobre design patterns como Repository e DAO, mas ainda não implementei.

Com essa ideia de futuramente fazer os repositories, comecei as rotas do Javalin e segui implementando a fila de revisão, dando uma olhada sobre JPA (Jakarta Persistence API) para conseguir ordenar as cartas de um baralho por data como faria em um banco de dados. Porém, após pesquisar sobre essa API e ver que daria um overhead desnecessariamente grande para um projeto do escopo do meu, falei com o claude sobre alternativas e decidi usar JDBI, que apesar de ser um pouco mais "feio" (usa queries SQL diretamente), faz bem o trabalho que preciso.

Depois de instalar as dependências do JDBI, criei um banco de dados PostgreSQL no Render e já fiz a integração por meio de variáveis de ambiente para execução local enquanto o backend ainda não funcionava completamente.

Após, fui completando os repositories que lidam com a entrada e saída dos objetos no banco de dados. Primeiro completei o repositório de Decks, e depois o de cartas.
Enquanto implementava os repositories, busquei um modo de fazer a conversão entre a saída de uma query ao DB (uma Row) para o objeto correspondente, e o Claude me recomendou olhar sobre a biblioteca RowMapper, e eu então implementei as funções DeckMapper e CardMapper, ambas extendem RowMapper.

Depois disso, comecei a fazer os endpoints com Javalin no arquivo RoutesService para busca dos decks e cartas, criação e remoção, etc.
Então fui atrás de adicionar um Dockerfile para execução no Render e realizar o deploy para entrega parcial.

A próxima etapa da implementação é implementar o funcionamento principal do software, o review e avaliação das cartas.

Comecei tentando fazer um modelo chamado ReviewQueue, mas depois decidi que seria mais fácil apenas fazer um ReviewQueueService que constrói uma fila, baseado nas propriedades de dailyLimit e reviewedToday, e retornando essa fila no formato de uma ArrayList\<Cards> ordenada por data, e o percurso de uma carta para outra será feito direto no roteamento e no front-end, para evitar acessos desnecessários ao DB ou requisições HTTP extras.

Tive uma certa dificuldade ao fazer o roteamento para lidar com os repositórios que fazem o acesso ao DB, pois inicialmente eu havia feito as funções do repository estáticas, porém elas precisam de uma instância de JDBI (conexão ao banco), então tive que remover a keyword static e adicionar os repositorios já instanciados como parâmetros para o RouterService.init(), instanciando esses repositórios na função Main que inicia o backend.

# Diagrama de classes

# Instruções para execução

# Resultado final

# Referências:

-- Artigos que li buscando entender como conciliar a relação entre as classes de OOP e a persistência que preciso pro sistema de flashcards (esses dados devem durar meses/anos e não apenas o tempo de uma sessão http)  
https://www.baeldung.com/java-dao-pattern
https://medium.com/@pererikbergman/repository-design-pattern-e28c0f3e4a30
https://java-design-patterns.com/patterns/repository/#detailed-explanation-of-repository-pattern-with-real-world-examples
https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html
https://www.dio.me/articles/jakarta-persistence-api-jpa
https://jdbi.org/
https://jdbi.org/#_statement_types

-- Documentação do javalin para entender o funcionamento das rotas e como ligar uma rota a um arquivo html
https://javalin.io/documentation#getting-started

-- Prompt que usei para geração do front-end
```
I'm doing a simple flashcard SRS web app with Javalin for a uni project. I need a simple frontend page. The functions that need to be accessible through this frontend are:
    - Home page, that shows your decks with the amount of cards to review
    - Deck overview, that shows the cards that a deck contains
    - Review session, that shows one card at a time, with the number of cards remaining being shown somewhere in the screen. The review screen must have buttons for "Miss", "Hard" and "Easy".

The Classes are currently:

    Deck
        Attributes: id, cards, dailyReviewLimit
    Card
        Attributes: id, front, back, cardType (enum: Review or Recognition), status (new card, review, suspended), score (to determine next review date), nextReview (date)

The data will be fetched from the backend through javalin routes, make only the page templates.
```

-- Artigo que li para entender mais sobre o algoritmo de SRS (spaced repetition)
https://dev.to/umangsinha12/how-spaced-repetition-actually-works-the-sm-2-algorithm-1ge3

-- Documentação da Mozilla sobre códigos HTTP para resposta às requisições
https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status



[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/8MfjtJ-y)

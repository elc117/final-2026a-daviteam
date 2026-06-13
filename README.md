# Identificação
<b>Nome:</b> Davi Paiani Libardoni<br>
**Curso:** Sistemas de Informação

# Proposta

# Processo de desenvolvimento

# Diagrama de classes

# Instruções para execução

# Resultado final

# Referências:

-- Artigos que li buscando entender como conciliar a relação entre as classes de OOP e a persistência que preciso pro sistema de flashcards (esses dados devem durar meses/anos e não apenas o tempo de uma sessão http)  
https://www.baeldung.com/java-dao-pattern
https://medium.com/@pererikbergman/repository-design-pattern-e28c0f3e4a30

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


[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/8MfjtJ-y)

# Referência cíclica

**O que é:** o objeto A aponta para B e B aponta de volta para A (A→B→A). Quem percorre o grafo sem controle entra em loop.

**Como aconteceu aqui:** `ImovelModel.bairro` (`@ManyToOne`) e `BairroModel.imoveis` (`@OneToMany(mappedBy = "bairro")`). Ao serializar `GET /bairros`, o Jackson faz Bairro → imoveis → Imovel → bairro → imoveis → … sem fim. No Jackson 2 isso vira `Infinite recursion (StackOverflowError)`; no Jackson 3 (Spring Boot 4, este projeto) ele para antes: `HttpMessageNotWritableException: Document nesting depth (501) exceeds the maximum allowed (500)`. Em ambos os casos o JSON sai truncado.

**Diferença para ciclo de beans do Spring:** aquele é um ciclo de *dependências na inicialização* (bean A injeta B por construtor e B injeta A). O container não consegue criar nenhum dos dois e a aplicação nem sobe (`BeanCurrentlyInCreationException`). O ciclo daqui é de *dados*, só aparece em runtime ao serializar.

**Correções possíveis (não aplicadas):**
1. `@JsonIgnore` em `BairroModel.imoveis`: o bairro deixa de expor a lista.
2. `@JsonManagedReference` em `imoveis` + `@JsonBackReference` em `ImovelModel.bairro`: serializa pai→filhos e omite o caminho de volta.
3. DTO: o controller devolve objetos próprios da resposta (ex.: `BairroDTO` com `List<ImovelResumoDTO>` sem `bairro`), sem expor as entidades.

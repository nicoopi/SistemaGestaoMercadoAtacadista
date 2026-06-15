# Sistema de Gestão Atacadista - Java MVC 📦

## 📋 Informações Gerais sobre o Projeto
Este projeto é um sistema de gerenciamento de clientes e pedidos para o setor atacadista, desenvolvido inteiramente em **Java**. O objetivo principal da aplicação é aplicar conceitos avançados de Programação Orientada a Objetos (POO), arquitetura MVC estruturada e persistência de dados em disco.

### Funcionalidades Principais
* **CRUDs Modulares:** Criação, Leitura, Atualização e Remoção distribuídos em módulos (Clientes Físicos, Clientes Jurídicos, Produtos, etc.).
* **Persistência de Dados (Serialização):** O estado da aplicação é salvo automaticamente em arquivos binários (`.dat`). Coleções em memória são sincronizadas com o disco rígido, garantindo que nenhum dado seja perdido ao encerrar a execução.
* **Validação e Estabilidade:** Tratamento rigoroso de exceções customizadas (como `RegistroNaoEncontradoException`) e nativas (validações de `IllegalArgumentException` e `DateTimeParseException`) utilizando *multi-catch* para garantir a integridade do sistema.

---

## 🏗️ Informações sobre as Classes e suas Relações
O projeto foi desenhado sob o padrão arquitetural **MVC (Model-View-Controller)**, garantindo alta coesão e baixo acoplamento. Os relacionamentos da POO foram aplicados de forma sistêmica em toda a aplicação:

* **Associação (Relação de Uso):**
  * **Padrão Sistêmico View-Controller:** Todas as classes de interface (`*View`) se associam aos seus respectivos controladores (`*Controller`). Elas existem de forma independente, mas a View precisa conhecer o Controller para trafegar os inputs do usuário.
  * O `Pedido` se associa ao `Cliente` para registrar a autoria da compra, mantendo ciclos de vida totalmente independentes.

* **Composição (Dependência Forte / "Morte Junta"):**
  * **Padrão Sistêmico Controller-Utilitários:** Todos os Controllers possuem uma composição direta com a classe genérica `ArquivoUtil`. A ferramenta de persistência nasce e morre junto com o fluxo de controle de dados.
  * O `Pedido` é composto por instâncias de `ItemPedido`. A exclusão de um pedido obriga a destruição sistêmica de todos os itens atrelados a ele.

* **Agregação (Agrupamento):**
  * O `Estoque` e o `ItemPedido` agregam instâncias de `Produto`. Se o estoque zerar ou um pedido for cancelado, a entidade `Produto` permanece intacta no catálogo global.
  * Os Controllers agregam os objetos de modelo (ex: `ClienteFisico`) em estruturas de dados como `HashMap`, atuando como motores de busca em memória.

### Outros Componentes Arquiteturais Importantes
Além dos relacionamentos estruturais, o sistema faz uso de outros pilares da Orientação a Objetos e boas práticas:

* **Herança e Abstração:** A superclasse abstrata `Cliente` centraliza os atributos comuns, repassando o comportamento e a obrigatoriedade de serialização para as subclasses (como `ClienteFisico`).
* **Interfaces e Polimorfismo:** Implementação de interfaces (como `Descontavel`) para garantir contratos de métodos customizados, permitindo que diferentes tipos de clientes tenham lógicas de desconto específicas.
* **Exceções Customizadas:** Criação de pacotes de erro dedicados (ex: `RegistroNaoEncontradoException`) para isolar regras de negócio e fornecer um feedback limpo e controlado à camada de View.
---

## 🚀 Como Executar o Projeto

1. **Pré-requisitos:** Certifique-se de ter o [Java JDK](https://www.oracle.com/java/technologies/downloads/) (versão 19 ou superior) configurado no seu ambiente.
2. **Clonagem:**
    git clone https://github.com/SEU_USUARIO/NOME_DO_REPOSITORIO.git
3. **Ambiente:** Importe o projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse, VS Code).
4. **Execução:** Navegue até o pacote principal e inicie a aplicação através da classe `Main.java`.
5. **Persistência:** Não é necessário configurar banco de dados. O sistema gerará automaticamente um diretório `dados/` na raiz do projeto durante a primeira execução para abrigar os arquivos de persistência binária.

---

## 🤖 Uso de Inteligência Artificial
Durante o ciclo de desenvolvimento, o **Google Gemini** foi adotado como ferramenta de *pair programming* para otimização de código e validação de conceitos. A IA atuou nos seguintes cenários:
* **Resolução Guiada de Erros:** Auxílio na correção de bugs lógicos e estruturais. A IA foi estritamente instruída a não fornecer códigos prontos, atuando através de questionamentos para instigar o pensamento crítico e a resolução autônoma dos problemas.
* **Refatoração Arquitetural:** Estruturação da classe utilitária `ArquivoUtil` para atender a múltiplos Controllers via tipagem genérica (`Object`), aplicando o princípio DRY (Don't Repeat Yourself).
* **Debugging de Fluxo Binário:** Resolução de impasses técnicos na serialização profunda do Java, especificamente no mapeamento da interface `Serializable` em hierarquias de herança.
* **Revisão de Clean Code:** Validação da mecânica de *multi-catch* para tratamento unificado de exceções entre as camadas Model e View.
* **Documentação:** Auxílio na estruturação e redação final deste `README.md`, garantindo que a comunicação técnica refletisse com precisão a maturidade arquitetural alcançada no código.

---

## 📚 Referências e Recursos
* Aulas e materiais de apoio da disciplina de Desenvolvimento de Software.
* [Documentação Oficial do Java - Interface Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html)
* [Tutorial de Java I/O (Input/Output Streams)](https://docs.oracle.com/javase/tutorial/essential/io/streams.html)

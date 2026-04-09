# StudyScheduler 
Aplicação desktop desenvolvida com JavaFX que permite organizar tarefas acadêmicas, acompanhar prazos e filtrar atividades de forma simples, objetiva e funcional. 

## Descrição do Problema Real 

A organização acadêmica é um desafio constante para muitos estudantes. A dificuldade em visualizar prioridades, prazos e demandas pode gerar sensação de sobrecarga, perda de foco e queda de produtividade. 

Embora existam ferramentas amplas de produtividade, como o Notion, muitas vezes elas oferecem múltiplas funcionalidades que acabam tornando o ambiente mais complexo do que necessário para quem deseja apenas organizar tarefas, provas, trabalhos e prazos de entrega de forma objetiva. 

A ausência de uma ferramenta simples, focada exclusivamente na organização de atividades acadêmicas com filtros claros e visualização direta das pendências, evidencia uma necessidade prática e recorrente no cotidiano estudantil. 

## Proposta da Solução 

O StudyScheduler foi desenvolvido como uma solução enxuta e direcionada à organização de tarefas de estudo. A aplicação oferece um ambiente simples, focado exclusivamente no cadastro, acompanhamento e filtragem de atividades acadêmicas. 

Ao priorizar clareza, objetividade e usabilidade, o sistema permite que o usuário visualize rapidamente suas tarefas pendentes e concluídas, organize prazos e mantenha o controle sobre suas demandas acadêmicas sem distrações ou funcionalidades excessivas. 

A proposta central é transformar a organização em um processo direto e funcional, contribuindo para maior produtividade, previsibilidade e redução da sobrecarga mental associada à desorganização. 

## Público-Alvo 
- Estudantes do ensino médio
- Universitários
- Pessoas em preparação para provas, vestibulares, concursos
- Qualquer pessoa que deseje organizar tarefas de estudo

## Funcionalidades Principais 
- Cadastro de tarefas
- Visualização da lista de tarefas
- Marcar tarefas como concluídas
- Filtrar tarefas (pendentes, concluídas ou atrasadas)
- Interface gráfica intuitiva

## Tecnologias Utilizadas 
- Java 21 - linguagem principal do projeto
- JavaFX 21 – criação da interface gráfica
- Maven – gerenciamento de dependências e build
- JUnit 5 – testes automatizados
- Checkstyle – análise estática / lint
- GitHub Actions – integração contínua e CI

## Instruções de Instalação 

### 1 - Instalar o Java 21 
O projeto foi desenvolvido em Java 21, então essa versão é necessária. 
- Como verificar se você já tem Java:
1. **Windows:** pressione Win + R, digite cmd e aperte Enter / **macOS/Linux:** abra o aplicativo “Terminal”
2. Digite:<br>
java -version.
- O resultado esperado é algo como: java version "21"
- Caso não apareça, acesse o [site](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) e baixe o instalador para o seu sistema:
1. **Windows:** .exe
2. **macOS:** .dmg
3. **Linux:** .tar.gz ou gerenciador de pacotes
- Execute o instalador e siga os passos padrão (Next → Next → Finish)
- Após instalar, feche e abra o terminal novamente e rode: java -version

### 2 - Instalar o Git 
O Git é necessário para baixar o projeto. 
- Para verificar se já tem:<br>
git --version
- Se não tiver, baixe [aqui](https://git-scm.com/downloads). Instale com as opções padrão

### 3 - Clonar o projeto 
- Existem duas formas de baixar o projeto: pelo Terminal ou pelo Git Bash (Windows) 
1. **Terminal:** Navegue até a pasta onde deseja salvar o projeto. Exemplo: cd bootcamp
2. **Git Bash:** Entre na pasta onde deseja salvar o projeto e clique com o botão direito do mouse. Escolha a opção “Mostrar mais opções” e depois em “Open Git Bash here”
- Digite o comando abaixo e copie o link https://github.com/anne-cgm/study-scheduler.git daqui, ou pelo link fornecido ao clicar no botão verde "code" acima do repositório no GitHub:<br>
git clone
- Resultado esperado: uma pasta chamada "study-scheduler" será criada com todos os arquivos do projeto dentro da pasta escolhida

### 4 - Baixar dependências e compilar o projeto
É preciso baixar todas as bibliotecas necessárias (JavaFX, JUnit, etc.), compilar o código, executar os testes e gerar o arquivo .jar. 
- Entre na pasta do projeto dentro do terminal (cd study-scheduler) e digite:
1. **Windows:** <br>
mvnw.cmd clean install
2. **macOS/Linux:** <br>
./mvnw clean install
- Se tudo der certo, você verá: BUILD SUCCESS
- Possíveis problemas:
1. “java não reconhecido”: Java não instalado corretamente
2. erro de permissão no Linux/macOS: rode: <br>
chmod +x mvnw

### 5 - Verificar se o projeto foi compilado 
- Após o build, verifique se existe o arquivo "studyscheduler-1.0.0.jar" dentro da pasta: "target". Isso significa que o projeto foi compilado com sucesso.

## Instruções de Execução 
- Ainda na pasta do projeto, dentro do terminal, digite:
1. **Windows:** <br>
mvnw.cmd javafx:run
2. **macOS/Linux:** <br>
./mvnw javafx:run
- O que vai acontecer?
1. O Maven vai iniciar o JavaFX
2. A aplicação será carregada
3. A janela do StudyScheduler será exibida 
- Problemas comuns:
1. “java não reconhecido”: Java não instalado corretamente
2. erro de permissão (Linux/macOS): <br>
chmod +x mvnw
3. erro relacionado a JavaFX: rode novamente: <br>
mvnw.cmd clean install

## Instruções para Rodar os Testes 
- Execute no terminal, dentro da pasta do projeto:
1. **Windows:** <br>
mvnw.cmd test
2. **macOS/Linux:** <br>
./mvnw test
- O que vai acontecer?
1. O Maven vai procurar arquivos na pasta: src/test/java
2. Vai compilar os testes
3. Vai executar cada teste automaticamente
- Resultado esperado: aparecerá algo como: Tests run: 5, Failures: 0 BUILD SUCCESS
- Caso apareça "FAILURE", indica que alguma funcionalidade não está funcionando como esperado
- O que está sendo testado?
1. Criação de tarefas
2. Marcar tarefa como concluída
3. Verificar se tarefa está atrasada
4. Comportamento com data nula

## Instruções para Rodar o Lint  

- Comandos:
1. **Windows:** <br>
mvnw.cmd checkstyle:check
2. **macOS/Linux:** <br>
./mvnw checkstyle:check
- O que vai acontecer:
1. O Maven analisa todo o código do projeto
2. Verifica se está seguindo padrões de escrita, organização e boas práticas definidos
3. Mostra avisos ou problemas no terminal
- Resultado esperado: BUILD SUCCESS
- Caso haja algo fora do padrão, vai listar arquivos e linhas com problemas. Exemplo: Line 10: Missing a Javadoc comment
- **Obs.:** Esses avisos não impedem o projeto de funcionar, apenas servem para melhorar a qualidade do código

## Versão Atual 

**1.0.0** – versão inicial funcional (MAJOR.MINOR.PATCH) 

## Autor 

Anne Caroline Gonçalves de Mesquita 

## Link do Repositório Público 

https://github.com/anne-cgm/study-scheduler

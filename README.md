# ShelfManager
### Trabalho para a disciplina de Banco de Dados 2 do curso de Análise e Desenvolvimento de Sistemas da UDESC.
#### Desenvolvido em Java 22, utilizando PostgreSQL.
## Recursos utilizados para o desenvolvimento do projeto
- Neo4j Desktop (versão 5.21.0);
- IDE IntelliJ IDEA Community.

## Criando o Banco de Dados no PostgreSQL
Para criar o banco de dados "ShelfManager" no Neo4j, siga os passos abaixo:
1. Abra o Neo4j Desktop, clique em New na aba Projects e em Create New Project.
2. Edite o nome de seu projeto e clique em Add - Local DBMS.
3. Caso queria, informe uma senha.
4. Após o projeto criado, caso queria começar com alguns dados, adicione o script do arquivo ShelfManager_backup.sql.

## Configurar o projeto no IntelliJ IDEA
Obtenha a URL do Repositório:
- Clique no botão verde "Code" e copie a URL fornecida. Certifique-se de selecionar a opção "Clone with HTTPS" se você estiver usando HTTPS ou "Clone with SSH" se estiver usando SSH.

### Clone o Repositório:

- Abra o IntelliJ IDEA.
- Na tela de inicialização, escolha "Get from Version Control" ou vá para "File" -> "New" -> "Project from Version Control" -> "Git".
- Na caixa de diálogo que aparecer, cole a URL que você copiou anteriormente.
- Escolha o diretório onde deseja clonar o repositório localmente.
- Clique em "Clone" para iniciar o processo de clonagem.

### Importe o Projeto para o IntelliJ IDEA:

- Após o processo de clonagem ser concluído, o IntelliJ IDEA abrirá automaticamente o projeto clonado.
- Se não abrir automaticamente, você pode importá-lo manualmente selecionando "File" -> "Open" e navegando até o diretório onde o repositório foi clonado.

### Configurar o Classpath
#### Observação:
- Ao criar um projeto em Java que possui uma classe principal, a IDE copia, automaticamente, todos os arquivos JAR na pasta do projeto para a pasta dist/lib do mesmo. A IDE também adiciona cada um dos arquivos JAR ao elemento Class-Path na aplicação (MANIFEST.MF).

## IntelliJ IDEA

- No IntelliJ IDEA, vá para File -> Project Structure.
- Na janela que abrir, selecione Modules no lado esquerdo.
- Na aba Dependencies, clique no + e selecione JARs or directories....
- Selecione o arquivo JAR do driver que está na pasta libs do seu projeto e clique em OK.
- Clique em Apply e depois em OK.

## Eclipse

- Clique com o botão direito no projeto no "Project Explorer".
- Selecione Build Path -> Configure Build Path.
- Na janela que abrir, vá para a aba Libraries.
- Clique em Add JARs... ou Add External JARs... (se o JAR estiver fora do seu projeto).
- Selecione o arquivo JAR do driver que está na pasta libs do seu projeto e clique em Open ou OK.
- Clique em Apply and Close para aplicar as alterações.

## NetBeans

- Clique com o botão direito no seu projeto na janela Projects.
- Selecione Properties.
- Na janela que abrir, vá para Libraries.
- Clique em Add JAR/Folder....
- Selecione o arquivo JAR do driver que está na pasta libs do seu projeto e clique em Add JAR/Folder.
- Clique em OK.

## Testando a Conexão

- Após criar o banco de dados, importar o projeto, adicionar o arquivo JAR do driver e configurar o classpath corretamente na sua IDE, você pode executar o arquivo TesteConexao.java para verificar se a conexão com o banco de dados está funcionando.

### Rodar o Projeto

1. No IntelliJ IDEA, vá para File -> Project Structure.
2. Na janela que abrir, selecione Artifacts no lado esquerdo.
3. Clique em + e selecione JAR -> From Modules with Dependencies....
4. Selecione a Classe Main e clique em OK
5. Volte para tela inicial do IntelliJ e na parte superior selecione Build -> Build Artifacts...

Agora abra o prompt de comando e rode o seguinte codigo:

   ```
java-cp"C:\L`cal\Arquivo\ShelfManager\out\artifacts\ShelfManager_jar\ShelfManager.jar" Main
   ```

Alterando C:\Local\Arquivo para o local onde o projeto está localizado.

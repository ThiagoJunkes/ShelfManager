# ShelfManager
### Trabalho para a disciplina de Banco de Dados 2 do curso de Análise e Desenvolvimento de Sistemas da UDESC.
#### Desenvolvido em Java 22, utilizando PostgreSQL.

# Recursos utilizados para o desenvolvimento do projeto
- PostgreSQL (versão 16.2);
- pgAdmin4;
- IDE IntelliJ IDEA Community.

# Criando o Banco de Dados no PostgreSQL

Para criar o banco de dados "ShelfManager" no pgAdmin, siga os passos abaixo:

1. Abra o pgAdmin e conecte-se ao seu servidor PostgreSQL.
   - Para isso, nomeie seu Servidor como preferir.
   - Seu Host será o 'localhost';
   - A porta será a padrão '5432';
   - O nome de usuário será 'postgres';
   - A senha será 'admin';
2. Clique com o botão direito em "Databases" e escolha "Create" -> "Database...".
   - Nomeie o banco de dados como 'ShelfManager'.
   - Clique em "Save" ou "OK" para criar o banco de dados.

   #### Observação: Quaisquer modificações no nome do host, port, databaseName, username ou password deverão ser ajustadas na conexão com o banco no Intellij IDEA.

3. Agora que o banco de dados está criado, abra o Query Tool:
   - Clique com o botão direito no banco de dados "ShelfManager" e escolha "Query Tool...".
4. Na janela do Query Tool, cole o seguinte script SQL e execute para criar as tabelas:

   ```sql
   CREATE TABLE enderecos (
       cod_endereco SERIAL PRIMARY KEY,
       rua VARCHAR(100),
       cidade VARCHAR(100),
       estado VARCHAR(100),
       cep INT,
       complemento VARCHAR(100)
   );

   CREATE TABLE editoras (
       cod_editora SERIAL PRIMARY KEY,
       nome_editora VARCHAR(250),
       nome_contato VARCHAR(250),
       email_editora VARCHAR(250) UNIQUE,
       telefone_editora VARCHAR(15)
   );

   CREATE TABLE livros (
       cod_livro SERIAL PRIMARY KEY,
       titulo VARCHAR(250),
       genero VARCHAR(100),
       autor VARCHAR(250),
       isbn BIGINT,
       ano_publicacao DATE,
       preco REAL,
       cod_editora INT,
       FOREIGN KEY (cod_editora) REFERENCES editoras(cod_editora)
   );

   CREATE TABLE clientes (
       cod_cliente SERIAL PRIMARY KEY,
       nome VARCHAR(50) NOT NULL,
       sobrenome VARCHAR(250) NOT NULL,
       cpf BIGINT NOT NULL,
       email_cliente VARCHAR(100) UNIQUE,
       telefone_cliente VARCHAR(15),
       data_cadastro DATE DEFAULT CURRENT_DATE,
       cod_endereco INT,
       FOREIGN KEY (cod_endereco) REFERENCES enderecos(cod_endereco)
   );

   CREATE TABLE vendas (
       cod_venda SERIAL PRIMARY KEY,
       valor_venda REAL,
       data_venda DATE DEFAULT CURRENT_DATE,
       metodo_pag VARCHAR(50),
       cod_cliente INT,
       FOREIGN KEY (cod_cliente) REFERENCES clientes(cod_cliente)
   );

   CREATE TABLE estoque (
       cod_livro INT NOT NULL,
       qtd_estoque INT,
       FOREIGN KEY (cod_livro) REFERENCES livros(cod_livro)
   );

   CREATE TABLE itens_vendas (
       cod_pedido INT NOT NULL,
       cod_livro INT NOT NULL,
       qtd_livros INT,
       PRIMARY KEY (cod_pedido, cod_livro),
       FOREIGN KEY (cod_pedido) REFERENCES vendas(cod_venda),
       FOREIGN KEY (cod_livro) REFERENCES livros(cod_livro)
   );
   

# Configurar o projeto no IntelliJ IDEA

## Obtenha a URL do Repositório:
1. Clique no botão verde "Code" e copie a URL fornecida. Certifique-se de selecionar a opção "Clone with HTTPS" se você estiver usando HTTPS ou "Clone with SSH" se estiver usando SSH.

## Clone o Repositório:
1. Abra o IntelliJ IDEA.
2. Na tela de inicialização, escolha "Get from Version Control" ou vá para "File" -> "New" -> "Project from Version Control" -> "Git".
3. Na caixa de diálogo que aparecer, cole a URL que você copiou anteriormente.
4. Escolha o diretório onde deseja clonar o repositório localmente.
5. Clique em "Clone" para iniciar o processo de clonagem.

## Importe o Projeto para o IntelliJ IDEA:
1. Após o processo de clonagem ser concluído, o IntelliJ IDEA abrirá automaticamente o projeto clonado.
2. Se não abrir automaticamente, você pode importá-lo manualmente selecionando "File" -> "Open" e navegando até o diretório onde o repositório foi clonado.


# Configurar o Classpath 

### Observação: 
- Ao criar um projeto em Java que possui uma classe principal, a IDE copia, automaticamente, todos os arquivos JAR na pasta do projeto para a pasta dist/lib do mesmo. A IDE também adiciona cada um dos arquivos JAR ao elemento Class-Path na aplicação (MANIFEST.MF).

## IntelliJ IDEA
1. No IntelliJ IDEA, vá para `File` -> `Project Structure`.
2. Na janela que abrir, selecione `Modules` no lado esquerdo.
3. Na aba `Dependencies`, clique no `+` e selecione `JARs or directories...`.
4. Selecione o arquivo JAR do driver que está na pasta `libs` do seu projeto e clique em `OK`.
5. Clique em `Apply` e depois em `OK`.

## Eclipse
1. Clique com o botão direito no projeto no "Project Explorer".
2. Selecione `Build Path` -> `Configure Build Path`.
3. Na janela que abrir, vá para a aba `Libraries`.
4. Clique em `Add JARs...` ou `Add External JARs...` (se o JAR estiver fora do seu projeto).
5. Selecione o arquivo JAR do driver que está na pasta `libs` do seu projeto e clique em `Open` ou `OK`.
6. Clique em `Apply and Close` para aplicar as alterações.

## NetBeans
1. Clique com o botão direito no seu projeto na janela `Projects`.
2. Selecione `Properties`.
3. Na janela que abrir, vá para `Libraries`.
4. Clique em `Add JAR/Folder...`.
5. Selecione o arquivo JAR do driver que está na pasta `libs` do seu projeto e clique em `Add JAR/Folder`.
6. Clique em `OK`.

## Testando a Conexão
Após criar o banco de dados, importar o projeto, adicionar o arquivo JAR do driver e configurar o classpath corretamente na sua IDE, você pode executar o arquivo `TesteConexao.java` para verificar se a conexão com o banco de dados está funcionando.


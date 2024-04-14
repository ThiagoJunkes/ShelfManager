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

-- Trigger
CREATE OR REPLACE FUNCTION atualizar_estoque()
RETURNS TRIGGER AS $$
BEGIN
    -- Diminui a quantidade de livros vendidos do estoque
    UPDATE estoque
    SET qtd_estoque = qtd_estoque - NEW.qtd_livros
    WHERE cod_livro = NEW.cod_livro;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Associa a trigger a tabela de itens_vendas
CREATE TRIGGER atualizar_estoque_trigger
AFTER INSERT ON itens_vendas
FOR EACH ROW
EXECUTE FUNCTION atualizar_estoque();


------------------------------------------------------------------------------


INSERT INTO editoras (nome_editora, nome_contato, email_editora, telefone_editora)
VALUES
    ('Editora Estelar', 'Marcos Oliveira', 'contato@editoraestelar.com', '(11) 98765-4321'),
    ('Mundo dos Livros', 'Amanda Santos', 'contato@mundodoslivros.com', '(22) 12345-6789'),
    ('Páginas Encantadas', 'Rafaela Lima', 'contato@paginasencantadas.com', '(33) 87654-3210'),
    ('Terra Mágica', 'Felipe Almeida', 'contato@terramagica.com', '(44) 23456-7890');

INSERT INTO livros (titulo, genero, autor, isbn, ano_publicacao, preco, cod_editora)
VALUES
    ('Aventuras em Marte', 'Ficção Científica', 'João Silva', 9781234567890, '2022-05-15', 29.99, 1),
    ('O Segredo do Abismo', 'Suspense', 'Maria Santos', 9782345678901, '2020-10-20', 24.99, 2),
    ('Caminho das Estrelas', 'Fantasia', 'Carlos Oliveira', 9783456789012, '2019-03-12', 19.99, 3),
    ('A Arte da Guerra', 'Não Ficção', 'Ana Souza', 9784567890123, '2015-07-01', 14.99, 4),
    ('Histórias do Além', 'Horror', 'Pedro Alves', 9785678901234, '2021-12-05', 34.99, 1);

INSERT INTO enderecos (rua, cidade, estado, cep, complemento)
VALUES
    ('Rua das Flores', 'Cidade A', 'SC', 89217230, 'Apto 101'),
    ('Avenida Central', 'Cidade B', 'SC', 89217230, 'Casa 20'),
    ('Rua Principal', 'Cidade C', 'SP', 89217230, 'Bloco B'),
    ('Travessa das Palmeiras', 'Cidade D', 'SP', 89217230, 'Sala 05'),
    ('Alameda dos Ipês', 'Cidade E', 'RJ', 89217230, 'Casa 15'),
    ('Avenida das Águias', 'Cidade F', 'RJ', 89217230, 'Apartamento 301'),
    ('Praça da Liberdade', 'Cidade G', 'RG', 89217230, 'Bloco C, 2º Andar'),
    ('Rua dos Girassóis', 'Cidade H', 'RG', 89217230, 'Casa 30'),
    ('Avenida dos Pinheiros', 'Cidade I', 'BA', 89217230, 'Loja 02'),
    ('Rodovia do Sol', 'Cidade J', 'AM', 89217230, 'Galpão 10');


INSERT INTO clientes (nome, sobrenome, cpf, email_cliente, telefone_cliente, cod_endereco)
VALUES
    ('Ana', 'Silva', 12345678901, 'ana.silva@email.com', '(11) 98765-4321', 1),
    ('Pedro', 'Oliveira', 23456789012, 'pedro.oliveira@email.com', '(22) 12345-6789', 2),
    ('Carla', 'Santos', 34567890123, 'carla.santos@email.com', '(33) 87654-3210', 3),
    ('José', 'Lima', 45678901234, 'jose.lima@email.com', '(44) 23456-7890', 4),
    ('Mariana', 'Almeida', 56789012345, 'mariana.almeida@email.com', '(55) 11111-1111', 5),
    ('Rafael', 'Pereira', 67890123456, 'rafael.pereira@email.com', '(66) 22222-2222', 6),
    ('Amanda', 'Martins', 78901234567, 'amanda.martins@email.com', '(77) 33333-3333', 7),
    ('Luiz', 'Gomes', 89012345678, 'luiz.gomes@email.com', '(88) 44444-4444', 8),
    ('Fernanda', 'Rodrigues', 90123456789, 'fernanda.rodrigues@email.com', '(99) 55555-5555', 9),
    ('Gustavo', 'Ferreira', 12345678910, 'gustavo.ferreira@email.com', '(00) 66666-6666', 10);

-- Estoque Inicial de todos os livros para 10 unidades
INSERT INTO estoque (cod_livro, qtd_estoque)
SELECT cod_livro, 10
FROM livros;

-- Registra as vendas
INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)
VALUES (54.98, 'Cartão de Crédito', 1);

INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros)
VALUES (1, 1, 1), -- Aventuras em Marte
       (1, 2, 1); -- O Segredo do Abismo

INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)
VALUES (34.98, 'Boleto Bancário', 2);

INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros)
VALUES (2, 3, 1), -- Caminho das Estrelas
       (2, 4, 1); -- A Arte da Guerra

INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)
VALUES (64.98, 'Boleto Bancário', 3);

INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros)
VALUES (3, 5, 1), -- Histórias do Além
       (3, 1, 1); -- Aventuras em Marte

INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)
VALUES (39.98, 'Pix', 4);

INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros)
VALUES (4, 4, 1), -- A Arte da Guerra
       (4, 2, 1); -- O Segredo do Abismo

INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)
VALUES (69.97, 'Dinheiro', 5);

INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros)
VALUES (5, 3, 1), -- Caminho das Estrelas
       (5, 5, 1), -- Histórias do Além
       (5, 4, 1); -- A Arte da Guerra

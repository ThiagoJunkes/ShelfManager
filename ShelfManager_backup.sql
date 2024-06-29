// Create Editoras
CREATE (:Editora {nome: 'Editora Estelar', nome_contato: 'Marcos Oliveira', email: 'contato@editoraestelar.com', telefone: '(11) 98765-4321'});
CREATE (:Editora {nome: 'Mundo dos Livros', nome_contato: 'Amanda Santos', email: 'contato@mundodoslivros.com', telefone: '(22) 12345-6789'});
CREATE (:Editora {nome: 'Páginas Encantadas', nome_contato: 'Rafaela Lima', email: 'contato@paginasencantadas.com', telefone: '(33) 87654-3210'});
CREATE (:Editora {nome: 'Terra Mágica', nome_contato: 'Felipe Almeida', email: 'contato@terramagica.com', telefone: '(44) 23456-7890'});

// Create Livros
CREATE (:Livro {titulo: 'Aventuras em Marte', genero: 'Ficção Científica', autor: 'João Silva', isbn: 9781234567890, ano_publicacao: '15/05/2022', preco: 29.99, quantidade_estoque: 12});
CREATE (:Livro {titulo: 'O Segredo do Abismo', genero: 'Suspense', autor: 'Maria Santos', isbn: 9782345678901, ano_publicacao: '20/10/2020', preco: 24.99, quantidade_estoque: 12});
CREATE (:Livro {titulo: 'Caminho das Estrelas', genero: 'Fantasia', autor: 'Carlos Oliveira', isbn: 9783456789012, ano_publicacao: '12/03/2019', preco: 19.99, quantidade_estoque: 12});
CREATE (:Livro {titulo: 'A Arte da Guerra', genero: 'Não Ficção', autor: 'Ana Souza', isbn: 9784567890123, ano_publicacao: '01/07/2015', preco: 14.99, quantidade_estoque: 12});
CREATE (:Livro {titulo: 'Histórias do Além', genero: 'Horror', autor: 'Pedro Alves', isbn: 9785678901234, ano_publicacao: '05/12/2021', preco: 34.99, quantidade_estoque: 12});

// Create Endereços
CREATE (:Endereco {cpf_morador: '12345678901', rua: 'Rua das Flores', cidade: 'Cidade A', estado: 'SC', cep: 89217230, complemento: 'Apto 101'});
CREATE (:Endereco {cpf_morador: '23456789012', rua: 'Avenida Central', cidade: 'Cidade B', estado: 'SC', cep: 89217230, complemento: 'Casa 20'});
CREATE (:Endereco {cpf_morador: '34567890123', rua: 'Rua Principal', cidade: 'Cidade C', estado: 'SP', cep: 89217230, complemento: 'Bloco B'});
CREATE (:Endereco {cpf_morador: '45678901234', rua: 'Travessa das Palmeiras', cidade: 'Cidade D', estado: 'SP', cep: 89217230, complemento: 'Sala 05'});
CREATE (:Endereco {cpf_morador: '56789012345', rua: 'Alameda dos Ipês', cidade: 'Cidade E', estado: 'RJ', cep: 89217230, complemento: 'Casa 15'});
CREATE (:Endereco {cpf_morador: '67890123456', rua: 'Avenida das Águias', cidade: 'Cidade F', estado: 'RJ', cep: 89217230, complemento: 'Apartamento 301'});
CREATE (:Endereco {cpf_morador: '78901234567', rua: 'Praça da Liberdade', cidade: 'Cidade G', estado: 'RG', cep: 89217230, complemento: 'Bloco C, 2º Andar'});
CREATE (:Endereco {cpf_morador: '89012345678', rua: 'Rua dos Girassóis', cidade: 'Cidade H', estado: 'RG', cep: 89217230, complemento: 'Casa 30'});
CREATE (:Endereco {cpf_morador: '90123456789', rua: 'Avenida dos Pinheiros', cidade: 'Cidade I', estado: 'BA', cep: 89217230, complemento: 'Loja 02'});
CREATE (:Endereco {cpf_morador: '12345678910', rua: 'Rodovia do Sol', cidade: 'Cidade J', estado: 'AM', cep: 89217230, complemento: 'Galpão 10'});

// Create Clientes
CREATE (:Cliente {nome: 'Ana', sobrenome: 'Silva', cpf: '12345678901', email: 'ana.silva@email.com', telefone: '(11) 98765-4321', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Pedro', sobrenome: 'Oliveira', cpf: '23456789012', email: 'pedro.oliveira@email.com', telefone: '(22) 12345-6789', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Carla', sobrenome: 'Santos', cpf: '34567890123', email: 'carla.santos@email.com', telefone: '(33) 87654-3210', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'João', sobrenome: 'Souza', cpf: '45678901234', email: 'joao.souza@email.com', telefone: '(44) 23456-7890', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Marcos', sobrenome: 'Lima', cpf: '56789012345', email: 'marcos.lima@email.com', telefone: '(55) 34567-8901', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Fernanda', sobrenome: 'Carvalho', cpf: '67890123456', email: 'fernanda.carvalho@email.com', telefone: '(66) 45678-9012', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Lucas', sobrenome: 'Mendes', cpf: '78901234567', email: 'lucas.mendes@email.com', telefone: '(77) 56789-0123', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Juliana', sobrenome: 'Alves', cpf: '89012345678', email: 'juliana.alves@email.com', telefone: '(88) 67890-1234', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Ricardo', sobrenome: 'Pereira', cpf: '90123456789', email: 'ricardo.pereira@email.com', telefone: '(99) 78901-2345', data_cadastro: '09/06/2024'});
CREATE (:Cliente {nome: 'Camila', sobrenome: 'Ferreira', cpf: '12345678910', email: 'camila.ferreira@email.com', telefone: '(11) 89012-3456', data_cadastro: '09/06/2024'});

// Create Vendas and relationships with Clientes
CREATE (:Venda {codigo: 1, valor: 54.98, data: '09/06/2024', metodo_pag: 'Cartão de Crédito'});
CREATE (:Venda {codigo: 2, valor: 34.98, data: '09/06/2024', metodo_pag: 'Boleto Bancário'});
CREATE (:Venda {codigo: 3, valor: 64.98, data: '09/06/2024', metodo_pag: 'Boleto Bancário'});
CREATE (:Venda {codigo: 4, valor: 39.98, data: '09/06/2024', metodo_pag: 'Pix'});
CREATE (:Venda {codigo: 5, valor: 69.97, data: '09/06/2024', metodo_pag: 'Dinheiro'});

// Match Vendas with Clientes and create relationships
MATCH (c1:Cliente {cpf: '12345678901'}), (v1:Venda {codigo: 1}) CREATE (v1)-[:FEITA_POR]->(c1);
MATCH (c2:Cliente {cpf: '23456789012'}), (v2:Venda {codigo: 2}) CREATE (v2)-[:FEITA_POR]->(c2);
MATCH (c3:Cliente {cpf: '34567890123'}), (v3:Venda {codigo: 3}) CREATE (v3)-[:FEITA_POR]->(c3);
MATCH (c4:Cliente {cpf: '45678901234'}), (v4:Venda {codigo: 4}) CREATE (v4)-[:FEITA_POR]->(c4);
MATCH (c5:Cliente {cpf: '56789012345'}), (v5:Venda {codigo: 5}) CREATE (v5)-[:FEITA_POR]->(c5);

// Create relationships between Vendas e Livros
MATCH (v1:Venda {codigo: 1}), (l1:Livro {isbn: 9781234567890}) CREATE (v1)-[:vendido {qtd: 1}]->(l1);
MATCH (v1:Venda {codigo: 1}), (l2:Livro {isbn: 9782345678901}) CREATE (v1)-[:vendido {qtd: 1}]->(l2);
MATCH (v2:Venda {codigo: 2}), (l3:Livro {isbn: 9783456789012}) CREATE (v2)-[:vendido {qtd: 2}]->(l3);
MATCH (v2:Venda {codigo: 2}), (l4:Livro {isbn: 9784567890123}) CREATE (v2)-[:vendido {qtd: 1}]->(l4);
MATCH (v3:Venda {codigo: 3}), (l5:Livro {isbn: 9785678901234}) CREATE (v3)-[:vendido {qtd: 1}]->(l5);
MATCH (v3:Venda {codigo: 3}), (l1:Livro {isbn: 9781234567890}) CREATE (v3)-[:vendido {qtd: 2}]->(l1);
MATCH (v4:Venda {codigo: 4}), (l2:Livro {isbn: 9782345678901}) CREATE (v4)-[:vendido {qtd: 1}]->(l2);
MATCH (v4:Venda {codigo: 4}), (l3:Livro {isbn: 9783456789012}) CREATE (v4)-[:vendido {qtd: 3}]->(l3);
MATCH (v5:Venda {codigo: 5}), (l4:Livro {isbn: 9784567890123}) CREATE (v5)-[:vendido {qtd: 1}]->(l4);
MATCH (v5:Venda {codigo: 5}), (l5:Livro {isbn: 9785678901234}) CREATE (v5)-[:vendido {qtd: 2}]->(l5);

// Create relationships between Editoras e Livros
MATCH (l:Livro {isbn: 9781234567890}), (e:Editora {nome: 'Editora Estelar'}) CREATE (l)-[:publicado_por]->(e);
MATCH (l:Livro {isbn: 9782345678901}), (e:Editora {nome: 'Mundo dos Livros'}) CREATE (l)-[:publicado_por]->(e);
MATCH (l:Livro {isbn: 9783456789012}), (e:Editora {nome: 'Páginas Encantadas'}) CREATE (l)-[:publicado_por]->(e);
MATCH (l:Livro {isbn: 9784567890123}), (e:Editora {nome: 'Terra Mágica'}) CREATE (l)-[:publicado_por]->(e);
MATCH (l:Livro {isbn: 9785678901234}), (e:Editora {nome: 'Editora Estelar'}) CREATE (l)-[:publicado_por]->(e);

// Create relationships between Clientes e Endereços
MATCH (c1:Cliente {cpf: '12345678901'}), (e1:Endereco {cpf_morador: '12345678901'}) CREATE (c1)-[:mora_em]->(e1);
MATCH (c2:Cliente {cpf: '23456789012'}), (e2:Endereco {cpf_morador: '23456789012'}) CREATE (c2)-[:mora_em]->(e2);
MATCH (c3:Cliente {cpf: '34567890123'}), (e3:Endereco {cpf_morador: '34567890123'}) CREATE (c3)-[:mora_em]->(e3);
MATCH (c4:Cliente {cpf: '45678901234'}), (e4:Endereco {cpf_morador: '45678901234'}) CREATE (c4)-[:mora_em]->(e4);
MATCH (c5:Cliente {cpf: '56789012345'}), (e5:Endereco {cpf_morador: '56789012345'}) CREATE (c5)-[:mora_em]->(e5);
MATCH (c6:Cliente {cpf: '67890123456'}), (e6:Endereco {cpf_morador: '67890123456'}) CREATE (c6)-[:mora_em]->(e6);
MATCH (c7:Cliente {cpf: '78901234567'}), (e7:Endereco {cpf_morador: '78901234567'}) CREATE (c7)-[:mora_em]->(e7);
MATCH (c8:Cliente {cpf: '89012345678'}), (e8:Endereco {cpf_morador: '89012345678'}) CREATE (c8)-[:mora_em]->(e8);
MATCH (c9:Cliente {cpf: '90123456789'}), (e9:Endereco {cpf_morador: '90123456789'}) CREATE (c9)-[:mora_em]->(e9);
MATCH (c10:Cliente {cpf: '12345678910'}), (e10:Endereco {cpf_morador: '12345678910'}) CREATE (c10)-[:mora_em]->(e10);

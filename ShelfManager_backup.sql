--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Ubuntu 16.2-1.pgdg22.04+1)
-- Dumped by pg_dump version 16.2 (Ubuntu 16.2-1.pgdg22.04+1)

-- Started on 2024-04-17 19:10:39 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3420 (class 1262 OID 16388)
-- Name: ShelfManager; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "ShelfManager" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';


ALTER DATABASE "ShelfManager" OWNER TO postgres;

\connect "ShelfManager"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 24663)
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    cod_cliente integer NOT NULL,
    nome character varying(50) NOT NULL,
    sobrenome character varying(250) NOT NULL,
    cpf bigint NOT NULL,
    email_cliente character varying(100),
    telefone_cliente character varying(15),
    data_cadastro date DEFAULT CURRENT_DATE,
    cod_endereco integer
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24662)
-- Name: clientes_cod_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_cod_cliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clientes_cod_cliente_seq OWNER TO postgres;

--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 221
-- Name: clientes_cod_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_cod_cliente_seq OWNED BY public.clientes.cod_cliente;


--
-- TOC entry 218 (class 1259 OID 24638)
-- Name: editoras; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.editoras (
    cod_editora integer NOT NULL,
    nome_editora character varying(250),
    nome_contato character varying(250),
    email_editora character varying(250),
    telefone_editora character varying(15)
);


ALTER TABLE public.editoras OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24637)
-- Name: editoras_cod_editora_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.editoras_cod_editora_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.editoras_cod_editora_seq OWNER TO postgres;

--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 217
-- Name: editoras_cod_editora_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.editoras_cod_editora_seq OWNED BY public.editoras.cod_editora;


--
-- TOC entry 216 (class 1259 OID 24631)
-- Name: enderecos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enderecos (
    cod_endereco integer NOT NULL,
    rua character varying(100),
    cidade character varying(100),
    estado character varying(100),
    cep integer,
    complemento character varying(100)
);


ALTER TABLE public.enderecos OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24630)
-- Name: enderecos_cod_endereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enderecos_cod_endereco_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.enderecos_cod_endereco_seq OWNER TO postgres;

--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 215
-- Name: enderecos_cod_endereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enderecos_cod_endereco_seq OWNED BY public.enderecos.cod_endereco;


--
-- TOC entry 225 (class 1259 OID 24690)
-- Name: estoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estoque (
    cod_livro integer NOT NULL,
    qtd_estoque integer
);


ALTER TABLE public.estoque OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 24698)
-- Name: itens_vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itens_vendas (
    cod_pedido integer NOT NULL,
    cod_livro integer NOT NULL,
    qtd_livros integer
);


ALTER TABLE public.itens_vendas OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24649)
-- Name: livros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livros (
    cod_livro integer NOT NULL,
    titulo character varying(250),
    genero character varying(100),
    autor character varying(250),
    isbn bigint,
    ano_publicacao date,
    preco real,
    cod_editora integer
);


ALTER TABLE public.livros OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24648)
-- Name: livros_cod_livro_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.livros_cod_livro_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.livros_cod_livro_seq OWNER TO postgres;

--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 219
-- Name: livros_cod_livro_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.livros_cod_livro_seq OWNED BY public.livros.cod_livro;


--
-- TOC entry 224 (class 1259 OID 24678)
-- Name: vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendas (
    cod_venda integer NOT NULL,
    valor_venda real,
    data_venda date DEFAULT CURRENT_DATE,
    metodo_pag character varying(50),
    cod_cliente integer
);


ALTER TABLE public.vendas OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24677)
-- Name: vendas_cod_venda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vendas_cod_venda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vendas_cod_venda_seq OWNER TO postgres;

--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 223
-- Name: vendas_cod_venda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vendas_cod_venda_seq OWNED BY public.vendas.cod_venda;


--
-- TOC entry 3245 (class 2604 OID 24666)
-- Name: clientes cod_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN cod_cliente SET DEFAULT nextval('public.clientes_cod_cliente_seq'::regclass);


--
-- TOC entry 3243 (class 2604 OID 24641)
-- Name: editoras cod_editora; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.editoras ALTER COLUMN cod_editora SET DEFAULT nextval('public.editoras_cod_editora_seq'::regclass);


--
-- TOC entry 3242 (class 2604 OID 24634)
-- Name: enderecos cod_endereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecos ALTER COLUMN cod_endereco SET DEFAULT nextval('public.enderecos_cod_endereco_seq'::regclass);


--
-- TOC entry 3244 (class 2604 OID 24652)
-- Name: livros cod_livro; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros ALTER COLUMN cod_livro SET DEFAULT nextval('public.livros_cod_livro_seq'::regclass);


--
-- TOC entry 3247 (class 2604 OID 24681)
-- Name: vendas cod_venda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas ALTER COLUMN cod_venda SET DEFAULT nextval('public.vendas_cod_venda_seq'::regclass);


--
-- TOC entry 3258 (class 2606 OID 24671)
-- Name: clientes clientes_email_cliente_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_email_cliente_key UNIQUE (email_cliente);


--
-- TOC entry 3260 (class 2606 OID 24669)
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (cod_cliente);


--
-- TOC entry 3252 (class 2606 OID 24647)
-- Name: editoras editoras_email_editora_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.editoras
    ADD CONSTRAINT editoras_email_editora_key UNIQUE (email_editora);


--
-- TOC entry 3254 (class 2606 OID 24645)
-- Name: editoras editoras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.editoras
    ADD CONSTRAINT editoras_pkey PRIMARY KEY (cod_editora);


--
-- TOC entry 3250 (class 2606 OID 24636)
-- Name: enderecos enderecos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecos
    ADD CONSTRAINT enderecos_pkey PRIMARY KEY (cod_endereco);


--
-- TOC entry 3264 (class 2606 OID 24702)
-- Name: itens_vendas itens_vendas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_vendas
    ADD CONSTRAINT itens_vendas_pkey PRIMARY KEY (cod_pedido, cod_livro);


--
-- TOC entry 3256 (class 2606 OID 24656)
-- Name: livros livros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros
    ADD CONSTRAINT livros_pkey PRIMARY KEY (cod_livro);


--
-- TOC entry 3262 (class 2606 OID 24684)
-- Name: vendas vendas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT vendas_pkey PRIMARY KEY (cod_venda);


--
-- TOC entry 3271 (class 2620 OID 24714)
-- Name: itens_vendas atualizar_estoque_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER atualizar_estoque_trigger AFTER INSERT ON public.itens_vendas FOR EACH ROW EXECUTE FUNCTION public.atualizar_estoque();


--
-- TOC entry 3266 (class 2606 OID 24672)
-- Name: clientes clientes_cod_endereco_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_cod_endereco_fkey FOREIGN KEY (cod_endereco) REFERENCES public.enderecos(cod_endereco);


--
-- TOC entry 3268 (class 2606 OID 24693)
-- Name: estoque estoque_cod_livro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_cod_livro_fkey FOREIGN KEY (cod_livro) REFERENCES public.livros(cod_livro);


--
-- TOC entry 3269 (class 2606 OID 24708)
-- Name: itens_vendas itens_vendas_cod_livro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_vendas
    ADD CONSTRAINT itens_vendas_cod_livro_fkey FOREIGN KEY (cod_livro) REFERENCES public.livros(cod_livro);


--
-- TOC entry 3270 (class 2606 OID 24703)
-- Name: itens_vendas itens_vendas_cod_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_vendas
    ADD CONSTRAINT itens_vendas_cod_pedido_fkey FOREIGN KEY (cod_pedido) REFERENCES public.vendas(cod_venda);


--
-- TOC entry 3265 (class 2606 OID 24657)
-- Name: livros livros_cod_editora_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros
    ADD CONSTRAINT livros_cod_editora_fkey FOREIGN KEY (cod_editora) REFERENCES public.editoras(cod_editora);


--
-- TOC entry 3267 (class 2606 OID 24685)
-- Name: vendas vendas_cod_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT vendas_cod_cliente_fkey FOREIGN KEY (cod_cliente) REFERENCES public.clientes(cod_cliente);


-- Completed on 2024-04-17 19:10:40 -03

--
-- PostgreSQL database dump complete
--

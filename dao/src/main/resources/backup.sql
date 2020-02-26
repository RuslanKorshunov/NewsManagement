--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-02-24 13:01:50

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
-- TOC entry 2870 (class 1262 OID 16428)
-- Name: NewsDB; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "NewsDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';


ALTER DATABASE "NewsDB" OWNER TO postgres;

\connect "NewsDB"

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 16429)
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    id bigint NOT NULL,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL
);


ALTER TABLE public.author OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16486)
-- Name: author_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.author ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.author_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE
);


--
-- TOC entry 203 (class 1259 OID 16434)
-- Name: news; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news (
    id bigint NOT NULL,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL
);


ALTER TABLE public.news OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16442)
-- Name: news_author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL
);


ALTER TABLE public.news_author OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16490)
-- Name: news_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.news ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.news_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE
);


--
-- TOC entry 208 (class 1259 OID 16473)
-- Name: news_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE public.news_tag OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16460)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16468)
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id bigint NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16488)
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tag ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE
);


--
-- TOC entry 205 (class 1259 OID 16455)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id bigint NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 2855 (class 0 OID 16429)
-- Dependencies: 202
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (2, 'Anastasija', 'Lemba');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (3, 'Vladislav', 'Polyakov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (4, 'Evgenija', 'Golubeva');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (5, 'Maryia', 'Biaseda');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (6, 'Dziyana', 'Dzeraviankina');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (7, 'Kseniya', 'Perapechyna');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (9, 'Ali', 'Al-Iusefi');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (10, 'Aleh', 'Salavei');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (11, 'Andrei', 'Krupin');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (12, 'Uladzislau', 'Saldatsenkau');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (14, 'Maksim', 'Hlazunou');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (15, 'Nikita', 'Karchahin');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (16, 'Maksim', 'Borisevich');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (17, 'Irina', 'Shokal');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (18, 'Darya', 'Volakh');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (20, 'Marya', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (21, 'Tina', 'Karol');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (22, 'Andrej', 'Danilko');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (23, 'Evgenij', 'Burak');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (24, 'Bon', 'Jovi');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (1, 'Ruslan', 'Korshunoff');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (25, 'Mark', 'Petrov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (26, 'Mark', 'Petrov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (27, 'Mark', 'Molotov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (28, 'David', 'Li');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (30, 'Elsa', 'Frozen');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (8, 'Pavello', 'Shyshko');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (31, 'Ruslan', 'Korshunov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (32, 'Ludmila', 'Korshunova');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (33, 'Mari', 'Kuri');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (34, 'Mari', 'Kurli');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (35, 'Mari', 'Kurl');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (36, 'Maria', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (37, 'Anna', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (38, 'Ostap', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (39, 'Armen', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (40, 'Ivan', 'Abegyan');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (41, 'Anton', 'Petrov');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (13, 'Maks', 'Baravy');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (44, 'Viktor', 'Kornaev');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (19, 'Maksim', 'Kornaev');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (45, 'Artem', 'Karchahin');
INSERT INTO public.author OVERRIDING SYSTEM VALUE VALUES (46, 'Bagdan', 'Karchahin');


--
-- TOC entry 2856 (class 0 OID 16434)
-- Dependencies: 203
-- Data for Name: news; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news OVERRIDING SYSTEM VALUE VALUES (21, 'Two passengers dead', 'Two Japanese passengers have died.', 'One passenger died from Covid-19, while the other died from pneumonia, said local reports. Both were in their 80s and had underlying health conditions. They were being treated in hospitals after being taken off the Diamond Princess last week. At least 621 people on the ship tested positive for the virus, the biggest cluster outside China. Japan''s health minister said both passengers had been sent to medical facilities after showing symptoms. "I believe they received the best possible treatment", Katsunobu Kato added. The Diamond Princess was carrying 3,700 people in total and passengers who tested negative for the virus began leaving the ship on Wednesday after a 14-day quarantine. Hundreds have now disembarked from the cruise. Others are set to leave over the next two days.', '2020-02-20', '2020-02-20');
INSERT INTO public.news OVERRIDING SYSTEM VALUE VALUES (20, 'Passengers leave Diamond Prin', 'Passengers have begun leaving a quarantined cruise ship.', 'One Japanese health expert who visited the Diamond Princess at the port in Yokohama said the situation on board was "completely chaotic". US officials said moves to contain the virus "may not have been sufficient". Passengers have described the difficult quarantine situation on the vessel. At least 621 passengers and crew on the Diamond Princess have so far been infected by the Covid-19 virus - the biggest cluster outside mainland China. The ship was carrying 3,700 people in total.', '2020-02-19', '2020-02-20');
INSERT INTO public.news OVERRIDING SYSTEM VALUE VALUES (22, 'South Korea ''emergency'' measu', 'South Korea has stepped up measures to contain the spread of the deadly new coronavirus.', 'PM Chung Sye-kyun said it was now an emergency as 100 new cases and the country''s second death were confirmed. The southern cities of Daegu and Cheongdo have been declared "special care zones". The streets of Daegu are now largely abandoned. All military bases are in lockdown after three soldiers tested positive. About 9,000 members of a religious group were told to self quarantine, after the sect was identified as a coronavirus hotbed. The authorities suspect the current outbreak in South Korea originated in Cheongdo, pointing out that a large number of sect followers attended a funeral of the founder''s brother from 31 January to 2 February.', '2020-02-21', '2020-02-21');


--
-- TOC entry 2857 (class 0 OID 16442)
-- Dependencies: 204
-- Data for Name: news_author; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news_author VALUES (20, 46);
INSERT INTO public.news_author VALUES (21, 45);
INSERT INTO public.news_author VALUES (22, 31);


--
-- TOC entry 2861 (class 0 OID 16473)
-- Dependencies: 208
-- Data for Name: news_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news_tag VALUES (20, 44);
INSERT INTO public.news_tag VALUES (20, 47);
INSERT INTO public.news_tag VALUES (20, 45);
INSERT INTO public.news_tag VALUES (20, 46);
INSERT INTO public.news_tag VALUES (20, 48);
INSERT INTO public.news_tag VALUES (20, 49);
INSERT INTO public.news_tag VALUES (21, 44);
INSERT INTO public.news_tag VALUES (21, 47);
INSERT INTO public.news_tag VALUES (21, 45);
INSERT INTO public.news_tag VALUES (21, 46);
INSERT INTO public.news_tag VALUES (21, 48);
INSERT INTO public.news_tag VALUES (21, 49);
INSERT INTO public.news_tag VALUES (22, 51);
INSERT INTO public.news_tag VALUES (22, 52);
INSERT INTO public.news_tag VALUES (22, 45);


--
-- TOC entry 2859 (class 0 OID 16460)
-- Dependencies: 206
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2860 (class 0 OID 16468)
-- Dependencies: 207
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (1, 'europe');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (3, 'italy');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (4, 'belarus');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (5, 'russia');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (6, 'arcade');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (7, 'rotterdam');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (9, 'music');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (10, 'frozen');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (20, 'democratic');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (21, 'hampshire');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (22, 'uk');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (23, 'france');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (24, 'USA');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (25, 'usa');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (28, 'yovanovitch');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (29, 'ukraine');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (30, 'tramp');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (31, 'policy');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (2, 'eurovion');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (34, 'blagojevich');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (35, 'trump');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (36, 'sanders');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (37, 'democrate');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (39, 'maksimm');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (40, 'hanau');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (41, 'german');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (42, 'kurdish');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (43, 'terrorism');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (44, 'japan');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (45, 'coronavirus');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (46, 'quarantine');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (47, 'yokohama');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (48, 'diamond');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (49, 'princess');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (51, 'south');
INSERT INTO public.tag OVERRIDING SYSTEM VALUE VALUES (52, 'korea');


--
-- TOC entry 2858 (class 0 OID 16455)
-- Dependencies: 205
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 209
-- Name: author_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.author_id_seq', 47, true);


--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 211
-- Name: news_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.news_id_seq', 22, true);


--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 210
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tag_id_seq', 52, true);


--
-- TOC entry 2717 (class 2606 OID 16433)
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


--
-- TOC entry 2719 (class 2606 OID 16441)
-- Name: news news_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);


--
-- TOC entry 2723 (class 2606 OID 16472)
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- TOC entry 2721 (class 2606 OID 16459)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2725 (class 2606 OID 16497)
-- Name: news_author news_author_author_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.author(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2724 (class 2606 OID 16492)
-- Name: news_author news_author_news_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2727 (class 2606 OID 16502)
-- Name: news_tag news_tag_news_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2728 (class 2606 OID 16507)
-- Name: news_tag news_tag_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2726 (class 2606 OID 16463)
-- Name: roles roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2020-02-24 13:01:51

--
-- PostgreSQL database dump complete
--


--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.15
-- Dumped by pg_dump version 9.6.15

-- Started on 2020-03-12 14:53:31

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
-- TOC entry 2169 (class 1262 OID 24960)
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
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 24961)
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    id bigserial NOT NULL,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL
);


ALTER TABLE public.author OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 24964)
-- Name: news; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news (
    id bigserial NOT NULL,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL
);


ALTER TABLE public.news OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 24970)
-- Name: news_author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news_author (
    news_id bigint NOT NULL,
    author_id bigint NOT NULL
);


ALTER TABLE public.news_author OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 24973)
-- Name: news_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news_tag (
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE public.news_tag OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 24976)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 24979)
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id bigserial NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 32990)
-- Name: tag_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tag_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 99999999999
    CACHE 1;


ALTER TABLE public.tag_seq OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 24982)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id bigserial NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 2156 (class 0 OID 24961)
-- Dependencies: 185
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.author (id, name, surname) VALUES (2, 'Anastasija', 'Lemba');
INSERT INTO public.author (id, name, surname) VALUES (3, 'Vladislav', 'Polyakov');
INSERT INTO public.author (id, name, surname) VALUES (4, 'Evgenija', 'Golubeva');
INSERT INTO public.author (id, name, surname) VALUES (5, 'Maryia', 'Biaseda');
INSERT INTO public.author (id, name, surname) VALUES (6, 'Dziyana', 'Dzeraviankina');
INSERT INTO public.author (id, name, surname) VALUES (7, 'Kseniya', 'Perapechyna');
INSERT INTO public.author (id, name, surname) VALUES (9, 'Ali', 'Al-Iusefi');
INSERT INTO public.author (id, name, surname) VALUES (10, 'Aleh', 'Salavei');
INSERT INTO public.author (id, name, surname) VALUES (11, 'Andrei', 'Krupin');
INSERT INTO public.author (id, name, surname) VALUES (12, 'Uladzislau', 'Saldatsenkau');
INSERT INTO public.author (id, name, surname) VALUES (14, 'Maksim', 'Hlazunou');
INSERT INTO public.author (id, name, surname) VALUES (15, 'Nikita', 'Karchahin');
INSERT INTO public.author (id, name, surname) VALUES (16, 'Maksim', 'Borisevich');
INSERT INTO public.author (id, name, surname) VALUES (17, 'Irina', 'Shokal');
INSERT INTO public.author (id, name, surname) VALUES (18, 'Darya', 'Volakh');
INSERT INTO public.author (id, name, surname) VALUES (20, 'Marya', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (21, 'Tina', 'Karol');
INSERT INTO public.author (id, name, surname) VALUES (22, 'Andrej', 'Danilko');
INSERT INTO public.author (id, name, surname) VALUES (23, 'Evgenij', 'Burak');
INSERT INTO public.author (id, name, surname) VALUES (24, 'Bon', 'Jovi');
INSERT INTO public.author (id, name, surname) VALUES (25, 'Mark', 'Petrov');
INSERT INTO public.author (id, name, surname) VALUES (26, 'Mark', 'Petrov');
INSERT INTO public.author (id, name, surname) VALUES (27, 'Mark', 'Molotov');
INSERT INTO public.author (id, name, surname) VALUES (28, 'David', 'Li');
INSERT INTO public.author (id, name, surname) VALUES (30, 'Elsa', 'Frozen');
INSERT INTO public.author (id, name, surname) VALUES (8, 'Pavello', 'Shyshko');
INSERT INTO public.author (id, name, surname) VALUES (31, 'Ruslan', 'Korshunov');
INSERT INTO public.author (id, name, surname) VALUES (32, 'Ludmila', 'Korshunova');
INSERT INTO public.author (id, name, surname) VALUES (33, 'Mari', 'Kuri');
INSERT INTO public.author (id, name, surname) VALUES (34, 'Mari', 'Kurli');
INSERT INTO public.author (id, name, surname) VALUES (35, 'Mari', 'Kurl');
INSERT INTO public.author (id, name, surname) VALUES (36, 'Maria', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (37, 'Anna', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (38, 'Ostap', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (39, 'Armen', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (40, 'Ivan', 'Abegyan');
INSERT INTO public.author (id, name, surname) VALUES (41, 'Anton', 'Petrov');
INSERT INTO public.author (id, name, surname) VALUES (13, 'Maks', 'Baravy');
INSERT INTO public.author (id, name, surname) VALUES (44, 'Viktor', 'Kornaev');
INSERT INTO public.author (id, name, surname) VALUES (19, 'Maksim', 'Kornaev');
INSERT INTO public.author (id, name, surname) VALUES (45, 'Artem', 'Karchahin');
INSERT INTO public.author (id, name, surname) VALUES (46, 'Bagdan', 'Karchahin');


--
-- TOC entry 2157 (class 0 OID 24964)
-- Dependencies: 186
-- Data for Name: news; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news (id, title, short_text, full_text, creation_date, modification_date) VALUES (21, 'Two passengers dead', 'Two Japanese passengers have died.', 'One passenger died from Covid-19, while the other died from pneumonia, said local reports. Both were in their 80s and had underlying health conditions. They were being treated in hospitals after being taken off the Diamond Princess last week. At least 621 people on the ship tested positive for the virus, the biggest cluster outside China. Japan''s health minister said both passengers had been sent to medical facilities after showing symptoms. "I believe they received the best possible treatment", Katsunobu Kato added. The Diamond Princess was carrying 3,700 people in total and passengers who tested negative for the virus began leaving the ship on Wednesday after a 14-day quarantine. Hundreds have now disembarked from the cruise. Others are set to leave over the next two days.', '2020-02-20', '2020-02-20');
INSERT INTO public.news (id, title, short_text, full_text, creation_date, modification_date) VALUES (20, 'Passengers leave Diamond Prin', 'Passengers have begun leaving a quarantined cruise ship.', 'One Japanese health expert who visited the Diamond Princess at the port in Yokohama said the situation on board was "completely chaotic". US officials said moves to contain the virus "may not have been sufficient". Passengers have described the difficult quarantine situation on the vessel. At least 621 passengers and crew on the Diamond Princess have so far been infected by the Covid-19 virus - the biggest cluster outside mainland China. The ship was carrying 3,700 people in total.', '2020-02-19', '2020-02-20');
INSERT INTO public.news (id, title, short_text, full_text, creation_date, modification_date) VALUES (22, 'South Korea ''emergency'' measu', 'South Korea has stepped up measures to contain the spread of the deadly new coronavirus.', 'PM Chung Sye-kyun said it was now an emergency as 100 new cases and the country''s second death were confirmed. The southern cities of Daegu and Cheongdo have been declared "special care zones". The streets of Daegu are now largely abandoned. All military bases are in lockdown after three soldiers tested positive. About 9,000 members of a religious group were told to self quarantine, after the sect was identified as a coronavirus hotbed. The authorities suspect the current outbreak in South Korea originated in Cheongdo, pointing out that a large number of sect followers attended a funeral of the founder''s brother from 31 January to 2 February.', '2020-02-21', '2020-02-21');


--
-- TOC entry 2158 (class 0 OID 24970)
-- Dependencies: 187
-- Data for Name: news_author; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news_author (news_id, author_id) VALUES (20, 46);
INSERT INTO public.news_author (news_id, author_id) VALUES (21, 45);
INSERT INTO public.news_author (news_id, author_id) VALUES (22, 31);


--
-- TOC entry 2159 (class 0 OID 24973)
-- Dependencies: 188
-- Data for Name: news_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 44);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 47);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 45);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 46);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 48);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (20, 49);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 44);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 47);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 45);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 46);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 48);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (21, 49);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (22, 51);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (22, 52);
INSERT INTO public.news_tag (news_id, tag_id) VALUES (22, 45);


--
-- TOC entry 2160 (class 0 OID 24976)
-- Dependencies: 189
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2161 (class 0 OID 24979)
-- Dependencies: 190
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tag (id, name) VALUES (3, 'italy');
INSERT INTO public.tag (id, name) VALUES (4, 'belarus');
INSERT INTO public.tag (id, name) VALUES (5, 'russia');
INSERT INTO public.tag (id, name) VALUES (6, 'arcade');
INSERT INTO public.tag (id, name) VALUES (7, 'rotterdam');
INSERT INTO public.tag (id, name) VALUES (9, 'music');
INSERT INTO public.tag (id, name) VALUES (10, 'frozen');
INSERT INTO public.tag (id, name) VALUES (20, 'democratic');
INSERT INTO public.tag (id, name) VALUES (21, 'hampshire');
INSERT INTO public.tag (id, name) VALUES (22, 'uk');
INSERT INTO public.tag (id, name) VALUES (23, 'france');
INSERT INTO public.tag (id, name) VALUES (24, 'USA');
INSERT INTO public.tag (id, name) VALUES (25, 'usa');
INSERT INTO public.tag (id, name) VALUES (28, 'yovanovitch');
INSERT INTO public.tag (id, name) VALUES (29, 'ukraine');
INSERT INTO public.tag (id, name) VALUES (30, 'tramp');
INSERT INTO public.tag (id, name) VALUES (31, 'policy');
INSERT INTO public.tag (id, name) VALUES (2, 'eurovion');
INSERT INTO public.tag (id, name) VALUES (34, 'blagojevich');
INSERT INTO public.tag (id, name) VALUES (35, 'trump');
INSERT INTO public.tag (id, name) VALUES (36, 'sanders');
INSERT INTO public.tag (id, name) VALUES (37, 'democrate');
INSERT INTO public.tag (id, name) VALUES (39, 'maksimm');
INSERT INTO public.tag (id, name) VALUES (40, 'hanau');
INSERT INTO public.tag (id, name) VALUES (41, 'german');
INSERT INTO public.tag (id, name) VALUES (42, 'kurdish');
INSERT INTO public.tag (id, name) VALUES (43, 'terrorism');
INSERT INTO public.tag (id, name) VALUES (44, 'japan');
INSERT INTO public.tag (id, name) VALUES (45, 'coronavirus');
INSERT INTO public.tag (id, name) VALUES (46, 'quarantine');
INSERT INTO public.tag (id, name) VALUES (47, 'yokohama');
INSERT INTO public.tag (id, name) VALUES (48, 'diamond');
INSERT INTO public.tag (id, name) VALUES (49, 'princess');
INSERT INTO public.tag (id, name) VALUES (51, 'south');
INSERT INTO public.tag (id, name) VALUES (52, 'korea');
INSERT INTO public.tag (id, name) VALUES (1, 'europe');


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 192
-- Name: tag_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tag_seq', 1, false);


--
-- TOC entry 2162 (class 0 OID 24982)
-- Dependencies: 191
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2027 (class 2606 OID 24986)
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


--
-- TOC entry 2029 (class 2606 OID 24988)
-- Name: news news_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 24990)
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- TOC entry 2033 (class 2606 OID 24992)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2034 (class 2606 OID 24993)
-- Name: news_author news_author_author_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.author(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2035 (class 2606 OID 24998)
-- Name: news_author news_author_news_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_author
    ADD CONSTRAINT news_author_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2036 (class 2606 OID 25003)
-- Name: news_tag news_tag_news_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_news_id_fkey FOREIGN KEY (news_id) REFERENCES public.news(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2037 (class 2606 OID 25008)
-- Name: news_tag news_tag_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news_tag
    ADD CONSTRAINT news_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2038 (class 2606 OID 25013)
-- Name: roles roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2020-03-12 14:53:31

--
-- PostgreSQL database dump complete
--


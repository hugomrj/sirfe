--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.19
-- Dumped by pg_dump version 9.5.19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: aplicacion; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA aplicacion;


ALTER SCHEMA aplicacion OWNER TO postgres;

--
-- Name: sistema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA sistema;


ALTER SCHEMA sistema OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: actualiza_estado_resolucion(integer); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.actualiza_estado_resolucion(integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE

	transfe ALIAS FOR $1;

	resolucion character varying;
	estado_resolcion integer;

BEGIN
	-- con reparo 2
	estado_resolcion  := 2;

		
resolucion:=  (
SELECT resolucion_numero
  FROM aplicacion.transferencias_fondos
  where transferencia = transfe
);


  IF not EXISTS (

    SELECT resolucion_numero, rendiciones_verificacion.estado  
      FROM aplicacion.rendiciones_gastos, aplicacion.rendiciones_verificacion      
      where rendiciones_gastos.resolucion_numero =   (       
            SELECT resolucion_numero 
              FROM aplicacion.transferencias_fondos 
             where transferencia = transfe    
      ) 
    and  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion 
    and estado <> 2
  ) 
  THEN
	estado_resolcion  := 1;
  END IF;



-- update de resolucion
UPDATE aplicacion.resoluciones
   SET estado = estado_resolcion  
 WHERE numero like resolucion ;
 

 RETURN resolucion;
END;
$_$;


ALTER FUNCTION aplicacion.actualiza_estado_resolucion(integer) OWNER TO postgres;

--
-- Name: actualiza_estado_transferencia(integer); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.actualiza_estado_transferencia(integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE

transfe ALIAS FOR $1; 
est_transferencia integer;

BEGIN
-- con reparo 2
est_transferencia := 2;


  IF not EXISTS (


    SELECT consejo, rendiciones_verificacion.estado  
      FROM aplicacion.rendiciones_gastos, aplicacion.rendiciones_verificacion      
      where rendiciones_gastos.transferencia = transfe    
    and  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion 
    and estado <> 2  
  ) 
  THEN
est_transferencia  := 1;
  END IF;



-- update de transferencia

UPDATE aplicacion.transferencias_fondos
   SET
       estado_transferencia = est_transferencia
 WHERE transferencia = transfe;
 

 RETURN transfe;

END;
$_$;


ALTER FUNCTION aplicacion.actualiza_estado_transferencia(integer) OWNER TO postgres;

--
-- Name: generar_numeracion_timbrados_talonario(); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.generar_numeracion_timbrados_talonario() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

DECLARE        
	codini  character varying;
	str_factura  character varying;
	rep_factura  character varying;	
	int_factura bigint;
	

BEGIN

    IF (NEW.numero_inicio >=  NEW.numero_final ) THEN       
        RAISE EXCEPTION 'numero final no puede ser mayor a numero inicio';
    END IF;

	codini :=  substring( NEW.factura_inicio from 1 for 8);
	int_factura :=  substring( NEW.factura_inicio from 9 for 15);
        	
	rep_factura  :=   lpad( CAST ( int_factura AS character varying ) ,7, '0'); 	
	str_factura := codini || rep_factura ;    

   FOR nro_timbrado IN  NEW.numero_inicio..NEW.numero_final LOOP      

	INSERT INTO aplicacion.timbrados_boletas(
		timbrado_serie, timbrado_numero, factura_numero)
	VALUES ( NEW.id, nro_timbrado, str_factura );

	int_factura :=  int_factura  + 1;	
	rep_factura  :=   lpad( CAST ( int_factura AS character varying ) ,7, '0'); 	
	str_factura := codini || rep_factura   ;

   END LOOP;

	UPDATE aplicacion.timbrados
	SET factura_final = str_factura 
	where id = NEW.id;   
	
   RETURN NEW;
 
END;
$$;


ALTER FUNCTION aplicacion.generar_numeracion_timbrados_talonario() OWNER TO postgres;

--
-- Name: prueba2(integer); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.prueba2(integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE

	transfe ALIAS FOR $1;

	resolucion character varying;
	estado_resolcion integer;

BEGIN
	-- con reparo 2
	estado_resolcion  := 2;

		
resolucion:=  (
SELECT resolucion_numero
  FROM aplicacion.transferencias_fondos
  where transferencia = transfe
);


  IF not EXISTS (

    SELECT resolucion_numero, rendiciones_verificacion.estado  
      FROM aplicacion.rendiciones_gastos, aplicacion.rendiciones_verificacion      
      where rendiciones_gastos.resolucion_numero =   (       
            SELECT resolucion_numero 
              FROM aplicacion.transferencias_fondos 
             where transferencia = transfe    
      ) 
    and  rendiciones_verificacion.rendicion = rendiciones_gastos.rendicion 
    and estado <> 2
  ) 
  THEN
	estado_resolcion  := 1;
  END IF;



-- update de resolucion
UPDATE aplicacion.resoluciones
   SET estado = estado_resolcion  
 WHERE numero like resolucion ;
 

 RETURN resolucion;
END;
$_$;


ALTER FUNCTION aplicacion.prueba2(integer) OWNER TO postgres;

--
-- Name: rendiciones_suma_detalles(); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.rendiciones_suma_detalles() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

DECLARE        	
	suma_rendicion bigint;
	monto_transferencia  bigint;	

BEGIN

	monto_transferencia  := (
		SELECT  total_depositado
		FROM aplicacion.transferencias_fondos
		where transferencia = NEW.transferencia 		
		);

	suma_rendicion  :=  (
		SELECT sum(importe)
		FROM aplicacion.rendiciones_gastos
		where transferencia = NEW.transferencia 
		);
					
						
    IF ( suma_rendicion > monto_transferencia ) THEN       
        RAISE EXCEPTION 'Las suma total de rendicion es mayor al monto de transferencia';
    END IF;										
						
	UPDATE aplicacion.transferencias_fondos
		SET total_rendicion = suma_rendicion
		WHERE transferencia = NEW.transferencia ;						
						
	
   RETURN NEW;
 
END;
$$;


ALTER FUNCTION aplicacion.rendiciones_suma_detalles() OWNER TO postgres;

--
-- Name: rendiciones_suma_detalles_delete(); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.rendiciones_suma_detalles_delete() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

DECLARE        	
	suma_rendicion bigint;
	monto_transferencia  bigint;	

BEGIN

	monto_transferencia  := (
		SELECT  total_depositado
		FROM aplicacion.transferencias_fondos
		where transferencia = OLD.transferencia 		
		);

	suma_rendicion  :=  (
		SELECT sum(importe)
		FROM aplicacion.rendiciones_gastos
		where transferencia = OLD.transferencia 
		);				
			
	UPDATE aplicacion.transferencias_fondos
		SET total_rendicion = suma_rendicion
		WHERE transferencia = OLD.transferencia ;						
						
	
   RETURN NEW;
 
END;
$$;


ALTER FUNCTION aplicacion.rendiciones_suma_detalles_delete() OWNER TO postgres;

--
-- Name: resolucion_numero(integer); Type: FUNCTION; Schema: aplicacion; Owner: postgres
--

CREATE FUNCTION aplicacion.resolucion_numero(integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
DECLARE

	transfe ALIAS FOR $1;
	resolucion character varying;

BEGIN

	resolucion:=  (
	SELECT resolucion_numero
	  FROM aplicacion.transferencias_fondos
	  where transferencia = transfe
	);
	

  IF EXISTS (

	SELECT id, numero, estado
	  FROM aplicacion.resoluciones
	  where numero like resolucion

  ) 
  THEN

	UPDATE aplicacion.resoluciones
	   SET estado =  2
	WHERE numero like resolucion ; 

  ELSE

	INSERT INTO aplicacion.resoluciones(
            numero, estado)
		VALUES (resolucion, 2);

  

  END IF;


 RETURN resolucion;
END;
$_$;


ALTER FUNCTION aplicacion.resolucion_numero(integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: consejos_salud; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.consejos_salud (
    id integer NOT NULL,
    codstr character varying,
    cod integer,
    descripcion character varying,
    dpto integer,
    rol integer
);


ALTER TABLE aplicacion.consejos_salud OWNER TO postgres;

--
-- Name: consejos_salud_id_seq; Type: SEQUENCE; Schema: aplicacion; Owner: postgres
--

CREATE SEQUENCE aplicacion.consejos_salud_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aplicacion.consejos_salud_id_seq OWNER TO postgres;

--
-- Name: consejos_salud_id_seq; Type: SEQUENCE OWNED BY; Schema: aplicacion; Owner: postgres
--

ALTER SEQUENCE aplicacion.consejos_salud_id_seq OWNED BY aplicacion.consejos_salud.id;


--
-- Name: departamentos; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.departamentos (
    dpto integer NOT NULL,
    descripcion character varying,
    verificador integer DEFAULT 0
);


ALTER TABLE aplicacion.departamentos OWNER TO postgres;

--
-- Name: transferencias_fondos; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.transferencias_fondos (
    transferencia integer NOT NULL,
    resolucion_numero character varying,
    saldo_anterior bigint,
    origen_ingreso integer,
    recibo_numero bigint,
    comprobante_numero bigint,
    deposito_fecha date,
    total_depositado bigint,
    consejo integer,
    total_rendicion bigint DEFAULT 0,
    estado integer DEFAULT 1,
    tipo_transferencia integer DEFAULT 1,
    estado_transferencia integer DEFAULT 2
);


ALTER TABLE aplicacion.transferencias_fondos OWNER TO postgres;

--
-- Name: fondos_equidad_id_seq; Type: SEQUENCE; Schema: aplicacion; Owner: postgres
--

CREATE SEQUENCE aplicacion.fondos_equidad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aplicacion.fondos_equidad_id_seq OWNER TO postgres;

--
-- Name: fondos_equidad_id_seq; Type: SEQUENCE OWNED BY; Schema: aplicacion; Owner: postgres
--

ALTER SEQUENCE aplicacion.fondos_equidad_id_seq OWNED BY aplicacion.transferencias_fondos.transferencia;


--
-- Name: objetos_gastos; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.objetos_gastos (
    objeto integer NOT NULL,
    descripcion character varying,
    imputa character(1)
);


ALTER TABLE aplicacion.objetos_gastos OWNER TO postgres;

--
-- Name: origenes_ingresos; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.origenes_ingresos (
    origen integer NOT NULL,
    descripcion character varying
);


ALTER TABLE aplicacion.origenes_ingresos OWNER TO postgres;

--
-- Name: qry_resoluciones_transferencias; Type: VIEW; Schema: aplicacion; Owner: postgres
--

CREATE VIEW aplicacion.qry_resoluciones_transferencias AS
 SELECT transferencias_fondos.resolucion_numero
   FROM aplicacion.transferencias_fondos
  GROUP BY transferencias_fondos.resolucion_numero;


ALTER TABLE aplicacion.qry_resoluciones_transferencias OWNER TO postgres;

--
-- Name: rendiciones_gastos; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.rendiciones_gastos (
    rendicion integer NOT NULL,
    transferencia integer NOT NULL,
    resolucion_numero character varying,
    tipo_comprobante integer NOT NULL,
    comprobante_numero character varying,
    objeto integer NOT NULL,
    concepto character varying DEFAULT ''::character varying,
    fecha date,
    importe bigint,
    observacion character varying DEFAULT ''::character varying,
    consejo integer NOT NULL,
    ruc_factura character varying,
    timbrado_venciomiento date
);


ALTER TABLE aplicacion.rendiciones_gastos OWNER TO postgres;

--
-- Name: rendiciones_gastos_rendicion_seq; Type: SEQUENCE; Schema: aplicacion; Owner: postgres
--

CREATE SEQUENCE aplicacion.rendiciones_gastos_rendicion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aplicacion.rendiciones_gastos_rendicion_seq OWNER TO postgres;

--
-- Name: rendiciones_gastos_rendicion_seq; Type: SEQUENCE OWNED BY; Schema: aplicacion; Owner: postgres
--

ALTER SEQUENCE aplicacion.rendiciones_gastos_rendicion_seq OWNED BY aplicacion.rendiciones_gastos.rendicion;


--
-- Name: rendiciones_verificacion; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.rendiciones_verificacion (
    verificacion integer NOT NULL,
    rendicion integer,
    estado integer DEFAULT 0,
    comentario character varying
);


ALTER TABLE aplicacion.rendiciones_verificacion OWNER TO postgres;

--
-- Name: rendiciones_verificacion_verificacion_seq; Type: SEQUENCE; Schema: aplicacion; Owner: postgres
--

CREATE SEQUENCE aplicacion.rendiciones_verificacion_verificacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aplicacion.rendiciones_verificacion_verificacion_seq OWNER TO postgres;

--
-- Name: rendiciones_verificacion_verificacion_seq; Type: SEQUENCE OWNED BY; Schema: aplicacion; Owner: postgres
--

ALTER SEQUENCE aplicacion.rendiciones_verificacion_verificacion_seq OWNED BY aplicacion.rendiciones_verificacion.verificacion;


--
-- Name: resoluciones; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.resoluciones (
    id integer NOT NULL,
    numero character varying,
    estado integer
);


ALTER TABLE aplicacion.resoluciones OWNER TO postgres;

--
-- Name: resoluciones_estados; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.resoluciones_estados (
    estado integer NOT NULL,
    descripcion character varying
);


ALTER TABLE aplicacion.resoluciones_estados OWNER TO postgres;

--
-- Name: resoluciones_id_seq; Type: SEQUENCE; Schema: aplicacion; Owner: postgres
--

CREATE SEQUENCE aplicacion.resoluciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aplicacion.resoluciones_id_seq OWNER TO postgres;

--
-- Name: resoluciones_id_seq; Type: SEQUENCE OWNED BY; Schema: aplicacion; Owner: postgres
--

ALTER SEQUENCE aplicacion.resoluciones_id_seq OWNED BY aplicacion.resoluciones.id;


--
-- Name: tipos_comprobantes; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.tipos_comprobantes (
    tipo_comprobante integer NOT NULL,
    descripcion character varying
);


ALTER TABLE aplicacion.tipos_comprobantes OWNER TO postgres;

--
-- Name: tipos_transferencias; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.tipos_transferencias (
    tipo_transferencia integer NOT NULL,
    descripcion character varying
);


ALTER TABLE aplicacion.tipos_transferencias OWNER TO postgres;

--
-- Name: verificacion_estado; Type: TABLE; Schema: aplicacion; Owner: postgres
--

CREATE TABLE aplicacion.verificacion_estado (
    estado integer NOT NULL,
    descripcion character varying
);


ALTER TABLE aplicacion.verificacion_estado OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.roles (
    rol integer NOT NULL,
    nombre_rol character varying(140)
);


ALTER TABLE sistema.roles OWNER TO postgres;

--
-- Name: roles_rol_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.roles_rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.roles_rol_seq OWNER TO postgres;

--
-- Name: roles_rol_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.roles_rol_seq OWNED BY sistema.roles.rol;


--
-- Name: roles_x_selectores; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.roles_x_selectores (
    id integer NOT NULL,
    rol integer,
    selector integer
);


ALTER TABLE sistema.roles_x_selectores OWNER TO postgres;

--
-- Name: roles_x_selectores_id_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.roles_x_selectores_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.roles_x_selectores_id_seq OWNER TO postgres;

--
-- Name: roles_x_selectores_id_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.roles_x_selectores_id_seq OWNED BY sistema.roles_x_selectores.id;


--
-- Name: selectores; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.selectores (
    id integer NOT NULL,
    superior integer,
    descripcion character varying,
    ord integer,
    link character varying
);


ALTER TABLE sistema.selectores OWNER TO postgres;

--
-- Name: selectores_id_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.selectores_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.selectores_id_seq OWNER TO postgres;

--
-- Name: selectores_id_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.selectores_id_seq OWNED BY sistema.selectores.id;


--
-- Name: selectores_x_webservice; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.selectores_x_webservice (
    id integer NOT NULL,
    selector integer,
    wservice integer
);


ALTER TABLE sistema.selectores_x_webservice OWNER TO postgres;

--
-- Name: selectores_x_webservice_id_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.selectores_x_webservice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.selectores_x_webservice_id_seq OWNER TO postgres;

--
-- Name: selectores_x_webservice_id_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.selectores_x_webservice_id_seq OWNED BY sistema.selectores_x_webservice.id;


--
-- Name: usuarios; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.usuarios (
    usuario integer NOT NULL,
    cuenta character varying(100),
    clave character varying(150),
    token_iat character varying
);


ALTER TABLE sistema.usuarios OWNER TO postgres;

--
-- Name: usuarios_usuario_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.usuarios_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.usuarios_usuario_seq OWNER TO postgres;

--
-- Name: usuarios_usuario_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.usuarios_usuario_seq OWNED BY sistema.usuarios.usuario;


--
-- Name: usuarios_x_roles; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.usuarios_x_roles (
    id integer NOT NULL,
    usuario integer,
    rol integer
);


ALTER TABLE sistema.usuarios_x_roles OWNER TO postgres;

--
-- Name: usuarios_x_roles_id_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.usuarios_x_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.usuarios_x_roles_id_seq OWNER TO postgres;

--
-- Name: usuarios_x_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.usuarios_x_roles_id_seq OWNED BY sistema.usuarios_x_roles.id;


--
-- Name: webservice; Type: TABLE; Schema: sistema; Owner: postgres
--

CREATE TABLE sistema.webservice (
    wservice integer NOT NULL,
    path character varying,
    nombre character varying
);


ALTER TABLE sistema.webservice OWNER TO postgres;

--
-- Name: webservice_wservice_seq; Type: SEQUENCE; Schema: sistema; Owner: postgres
--

CREATE SEQUENCE sistema.webservice_wservice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema.webservice_wservice_seq OWNER TO postgres;

--
-- Name: webservice_wservice_seq; Type: SEQUENCE OWNED BY; Schema: sistema; Owner: postgres
--

ALTER SEQUENCE sistema.webservice_wservice_seq OWNED BY sistema.webservice.wservice;


--
-- Name: id; Type: DEFAULT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.consejos_salud ALTER COLUMN id SET DEFAULT nextval('aplicacion.consejos_salud_id_seq'::regclass);


--
-- Name: rendicion; Type: DEFAULT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos ALTER COLUMN rendicion SET DEFAULT nextval('aplicacion.rendiciones_gastos_rendicion_seq'::regclass);


--
-- Name: verificacion; Type: DEFAULT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_verificacion ALTER COLUMN verificacion SET DEFAULT nextval('aplicacion.rendiciones_verificacion_verificacion_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.resoluciones ALTER COLUMN id SET DEFAULT nextval('aplicacion.resoluciones_id_seq'::regclass);


--
-- Name: transferencia; Type: DEFAULT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.transferencias_fondos ALTER COLUMN transferencia SET DEFAULT nextval('aplicacion.fondos_equidad_id_seq'::regclass);


--
-- Name: rol; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.roles ALTER COLUMN rol SET DEFAULT nextval('sistema.roles_rol_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.roles_x_selectores ALTER COLUMN id SET DEFAULT nextval('sistema.roles_x_selectores_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.selectores ALTER COLUMN id SET DEFAULT nextval('sistema.selectores_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.selectores_x_webservice ALTER COLUMN id SET DEFAULT nextval('sistema.selectores_x_webservice_id_seq'::regclass);


--
-- Name: usuario; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios ALTER COLUMN usuario SET DEFAULT nextval('sistema.usuarios_usuario_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios_x_roles ALTER COLUMN id SET DEFAULT nextval('sistema.usuarios_x_roles_id_seq'::regclass);


--
-- Name: wservice; Type: DEFAULT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.webservice ALTER COLUMN wservice SET DEFAULT nextval('sistema.webservice_wservice_seq'::regclass);


--
-- Data for Name: consejos_salud; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.consejos_salud (id, codstr, cod, descripcion, dpto, rol) FROM stdin;
11	01.01	101	CLS de Concepción	1	57
13	01.03	103	CLS de Loreto	1	69
14	01.04	104	CLS de YbyYau	1	70
15	01.05	105	CLS de San Lázaro	1	71
64	03.19	319	CLS de Piribebuy	3	120
65	03.20	320	CLS de San José Obrero	3	121
66	04.00	400	CRS de  Guairá	4	122
68	04.02	402	CLS de José Fassardi	4	124
69	04.03	403	CLS de Paso Yobai	4	125
70	04.04	404	CLS de Iturbe	4	126
71	04.05	405	CLS de Mauricio José Troche	4	127
72	04.06	406	CLS de Colonia Independencia	4	128
73	04.07	407	CLS de Ñumi	4	129
74	04.08	408	CLS de Itape	4	130
75	04.09	409	CLS de Dr. Bottrell	4	131
76	04.10	410	CLS de Natalicio Talavera	4	133
77	04.11	411	CLS de Mbocayaty del Guaira	4	134
78	04.12	412	CLS de San Salvador	4	135
79	04.13	413	CLS de Félix Pérez Cardozo	4	136
80	04.14	414	CLS de Yataity del Guairá	4	137
81	04.15	415	CLS de Tebicuary	4	138
82	04.16	416	CLS de Garay	4	139
83	04.17	417	CLS de Cnel. Martínez	4	140
84	04.18	418	CLS de Borja	4	141
86	05.01	501	CLS de Cnel. Oviedo	5	143
87	05.02	502	CLS de Caaguazú	5	144
88	05.03	503	CLS de Dr. J. Eulogio Estigarribia	5	145
89	05.04	504	CLS de Juan Manuel Frutos	5	146
90	05.05	505	CLS de La Pastora	5	147
91	05.06	506	CLS de Gral Arsenio Oviedo	5	148
92	05.07	507	CLS de Nueva Londres	5	149
93	05.08	508	CLS de San Joaquín	5	150
94	05.09	509	CLS de Dr. Cecilio Báez	5	151
95	05.10	510	CLS de Carayao	5	152
96	05.11	511	CLS de RI 3 Corrales	5	153
97	05.12	512	CLS de Santa Rosa del Mbutuy	5	154
98	05.13	513	CLS de Tembiaporá	5	155
99	05.14	514	CLS de 3 de febrero	5	156
101	05.16	516	CLS de Vaquería	5	158
102	05.17	517	CLS de San José de los Arroyos	5	159
103	05.18	518	CLS de Yhu	5	160
104	05.19	519	CLS de Simon Bolivar	5	161
105	05.20	520	CLS de José Domingo Ocampos	5	162
106	05.21	521	CLS de Nueva Toledo	5	163
107	05.22	522	CLS de Repatriación	5	164
108	06.00	600	CRS de  Caazapá	6	165
109	06.01	601	CLS de Caazapá	6	166
110	06.02	602	CLS de San Juan Nepomuceno	6	167
111	06.03	603	CLS de Cnel. Maciel	6	168
112	06.04	604	CLS de Abaí	6	169
113	06.05	605	CLS de Fulgencio Yegros	6	170
114	06.06	606	CLS de Tavai	6	171
115	06.07	607	CLS de Buena Vista	6	172
116	06.08	608	CLS de Yuty	6	173
117	06.09	609	CLS de Gral. Higinio Morinigo	6	174
118	06.10	610	CLS de Moises Bertoni	6	175
119	06.11	611	CLS de 3 de Mayo	6	176
120	07.00	700	CRS de  Itapúa	7	177
121	07.01	701	CLS de Encarnación	7	178
122	07.02	702	CLS de Carmen del Paraná	7	179
123	07.03	703	CLS de Fram	7	180
124	07.04	704	CLS de Cnel. Bogado	7	181
126	07.06	706	CLS de Cambyreta	7	183
127	07.07	707	CLS de Bella Vista Sur	7	184
128	07.08	708	CLS de Tomás R. Pereira	7	185
129	07.09	709	CLS de Natalio	7	186
130	07.10	710	CLS de Capitán Meza	7	187
131	07.11	711	CLS de Capitán Miranda	7	188
132	07.12	712	CLS de Carlos Antonio López	7	189
133	07.13	713	CLS de Pirapo	7	190
134	07.14	714	CLS de Obligado	7	191
135	07.15	715	CLS de Yatytay	7	192
136	07.16	716	CLS de General Artigas	7	193
137	07.17	717	CLS de Mayor Julio D. Otaño	7	194
138	07.18	718	CLS de La Paz	7	195
139	07.19	719	CLS de Gral. Delgado	7	196
140	07.20	720	CLS de Nueva Alborada	7	197
141	07.21	721	CLS de José Leandro Oviedo	7	198
142	07.22	722	CLS de Jesús	7	199
143	07.23	723	CLS de Hohenau	7	200
144	07.24	724	CLS de Santísima Trinidad	7	201
145	07.25	725	CLS de San Juan del Paraná	7	202
147	07.27	727	CLS de Alto Vera	7	204
148	07.28	728	CLS de Itapúa Poty	7	205
149	07.29	729	CLS de Edelira	7	206
150	07.30	730	CLS de San Cosme y Damián	7	207
151	08.00	800	CRS de  Misiones	8	208
152	08.01	801	CLS de San Ignacio	8	209
153	08.02	802	CLS de Santa Rosa	8	210
154	08.03	803	CLS de San Miguel	8	211
155	08.04	804	CLS de San Juan Bautista Misiones	8	212
156	08.05	805	CLS de Ayolas	8	213
157	08.06	806	CLS de Santiago	8	214
158	08.07	807	CLS de San Patricio	8	215
159	08.08	808	CLS de Santa María Fe	8	217
160	08.09	809	CLS de Villa Florida	8	216
161	08.10	810	CLS de Yavebyry	8	218
162	09.00	900	CRS de  Paraguarí	9	219
163	09.01	901	CLS de Carapeguá	9	220
164	09.02	902	CLS de Quiindy	9	221
165	09.03	903	CLS de Paraguarí	9	222
1105	11.05	1105	CLS de Capiatá	11	62
1114	11.14	1114	CLS de Guarambaré	11	63
1115	11.15	1115	CLS de Lambaré	11	61
18	01.08	108	CLS de Sgto. José Félix López	1	74
19	01.09	109	CLS de Paso Barreto	1	75
20	01.10	110	CLS de San Alfredo	1	76
22	01.12	112	CLS de Arroyito	1	78
23	02.00	200	CRS de  San Pedro	2	79
24	02.01	201	CLS de San Pedro del Ycuamandyyu	2	80
25	02.02	202	CLS de Lima	2	81
26	02.03	203	CLS de Santa Rosa del Aguaray	2	82
27	02.04	204	CLS de Chore	2	83
28	02.05	205	CLS de Villa del Rosario	2	84
29	02.06	206	CLS de Itacurubí del Rosario	2	85
30	02.07	207	CLS de San Estanislao	2	86
166	09.04	904	CLS de Ybycuí	9	223
167	09.05	905	CLS de La Colmena	9	224
168	09.06	906	CLS de Acahay	9	225
169	09.07	907	CLS de Caapucú	9	226
171	09.09	909	CLS de Caballero	9	228
172	09.10	910	CLS de Yaguarón	9	229
173	09.11	911	CLS de Quyquyho	9	230
174	09.12	912	CLS de Pirayú	9	231
175	09.13	913	CLS de Tebicuarymí	9	232
176	09.14	914	CLS de Mbuyapey	9	233
177	09.15	915	CLS de Sapucai	9	234
178	09.16	916	CLS de Yvytimi	9	235
179	09.17	917	CLS de Escobar	9	236
180	09.18	918	CLS de Maria Antonia	9	237
181	10.00	1000	CRS de  Alto Paraná	10	238
191	10.10	1010	CLS de San Alberto	10	248
192	10.11	1011	CLS de Naranjal	10	249
193	10.12	1012	CLS de Juan E. O'leary	10	250
194	10.13	1013	CLS de Pdte. Franco	10	251
195	10.14	1014	CLS de Minga Guazu	10	252
196	10.15	1015	CLS de Santa Rosa del Monday	10	253
197	10.16	1016	CLS de Minga Porá	10	254
198	10.17	1017	CLS de Iruña	10	255
199	10.18	1018	CLS de San Cristóbal	10	256
201	10.20	1020	CLS de Yguazu	10	258
202	10.21	1021	CLS de Tavapy	10	259
203	10.22	1022	CLS de Raul Peña	10	260
204	11.00	1100	CRS de  Central	11	261
205	11.01	1101	CLS de Villeta	11	262
206	11.02	1102	CLS de Itauguá	11	263
207	11.03	1103	CLS de Villa Elisa	11	264
208	11.04	1104	CLS de Fernando de la Mora	11	265
210	11.06	1106	CLS de Aregua	11	266
211	11.07	1107	CLS de Limpio	11	267
212	11.08	1108	CLS de Ypacarai	11	268
213	11.09	1109	CLS de Ypane	11	270
215	11.11	1111	CLS de Luque	11	271
216	11.12	1112	CLS de Itá	11	273
217	11.13	1113	CLS de Mariano Roque Alonso	11	274
220	11.16	1116	CLS de San Antonio	11	275
222	11.18	1118	CLS de San Lorenzo	11	279
223	11.19	1119	CLS de Ñemby	11	280
224	12.00	1200	CRS de  Ñeembucu	12	281
225	12.01	1201	CLS de Pilar	12	297
226	12.02	1202	CLS de Mayor Martínez	12	282
227	12.03	1203	CLS de Isla Ombu	12	283
229	12.05	1205	CLS de Villa Oliva	12	285
230	12.06	1206	CLS de Paso de Patria	12	286
231	12.07	1207	CLS de Humaitá	12	287
232	12.08	1208	CLS de Alberdi	12	288
233	12.09	1209	CLS de San Juan del Ñeembucu	12	289
234	12.10	1210	CLS de Tacuaras	12	290
235	12.11	1211	CLS de Desmochados	12	291
236	12.12	1212	CLS de Laureles	12	292
237	12.13	1213	CLS de Villa Franca	12	293
238	12.14	1214	CLS de Gral. Diaz	12	294
239	12.15	1215	CLS de Guazucua	12	295
240	12.16	1216	CLS de Cerrito	12	296
241	13.00	1300	CRS de  Amambay	13	298
242	13.01	1301	CLS de Pedro Juan Caballero	13	299
243	13.02	1302	CLS de Bella Vista Norte	13	300
244	13.03	1303	CLS de Capitán Bado	13	301
245	13.04	1304	CLS de Zanja Pyta	13	302
246	13.05	1305	CLS de Karapaí	13	303
248	14.01	1401	CLS de Saltos del Guairá	14	305
249	14.02	1402	CLS de Curuguaty	14	306
250	14.03	1403	CLS de Ypejhu	14	307
252	14.05	1405	CLS de Corpus Christi	14	309
251	14.04	1404	CLS de Villa Ygatymi	14	308
253	14.06	1406	CLS de Katuete	14	310
254	14.07	1407	CLS de Nueva Esperanza	14	311
255	14.08	1408	CLS de Yasy Kañy	14	312
256	14.09	1409	CLS de Francisco C. Alvarez	14	313
257	14.10	1410	CLS de Ybyrarobana	14	314
258	14.11	1411	CLS de La Paloma	14	315
259	14.12	1412	CLS de Yby Pyta	14	316
260	14.13	1413	CLS de Itanara	14	317
261	14.14	1414	CLS de Maracana	14	318
262	14.15	1415	CLS de Puerto Adela	14	319
263	15.00	1500	CRS de  Pdte. Hayes	15	320
264	15.01	1501	CLS de Villa Hayes	15	321
265	15.02	1502	CLS de Nanawa	15	322
267	15.04	1504	CLS de José Falcón	15	324
268	15.05	1505	CLS de Tte. Esteban Martínez	15	325
269	15.06	1506	CLS de Tte. Irala Fernández	15	326
270	15.07	1507	CLS de Puerto Pinasco	15	327
271	15.08	1508	CLS de Gral. Bruguez	15	328
272	16.00	1600	CRS de  Boquerón	16	329
10	01.00	100	CRS de Concepción	1	56
1110	11.10	1110	CLS de Nueva Italia	11	60
12	01.02	102	CLS de Horqueta	1	68
16	01.06	106	CLS de Belén	1	72
17	01.07	107	CLS de Azotey	1	73
31	02.08	208	CLS de Guajayvi	2	87
32	02.09	209	CLS de San Pablo	2	88
33	02.10	210	CLS de Gral. Resquín	2	89
34	02.11	211	CLS de Gral. Aquino	2	90
35	02.12	212	CLS de Nueva Germania	2	91
36	02.13	213	CLS de Tacuati	2	92
37	02.14	214	CLS de Pto. Antequera	2	93
38	02.15	215	CLS de Capiibary	2	94
39	02.16	216	CLS de Yataity del Norte	2	95
40	02.17	217	CLS de Yrybucua	2	96
41	02.18	218	CLS de Unión	2	97
42	02.19	219	CLS de Liberación	2	98
43	02.20	220	CLS de 25 de diciembre	2	99
44	02.21	221	CLS de San Vicente Pancholo	2	100
45	03.00	300	CRS de  Cordillera	3	101
46	03.01	301	CLS de Altos	3	102
47	03.02	302	CLS de Atyrá	3	103
48	03.03	303	CLS de Emboscada	3	104
49	03.04	304	CLS de Eusebio Ayala	3	105
50	03.05	305	CLS de Caacupé	3	106
51	03.06	306	CLS de Nueva Colombia	3	107
52	03.07	307	CLS de Santa Elena	3	108
53	03.08	308	CLS de Itacurubí de la Cordillera	3	109
54	03.09	309	CLS de Mbocayaty del Yhaguy	3	110
55	03.10	310	CLS de Tobatí	3	111
56	03.11	311	CLS de San Bernardino	3	112
57	03.12	312	CLS de Arroyos y Estereo	3	113
58	03.13	313	CLS de Caraguatay	3	114
59	03.14	314	CLS de Juan de Mena	3	115
60	03.15	315	CLS de Loma Grande	3	116
61	03.16	316	CLS de Valenzuela	3	117
62	03.17	317	CLS de Isla Pucú	3	118
63	03.18	318	CLS de Primero de Marzo	3	119
67	04.01	401	CLS de Villarrica	4	123
85	05.00	500	CRS de  Caaguazú	5	142
100	05.15	515	CLS de Mcal. Francisco S. López	5	157
125	07.05	705	CLS de San Pedro del Paraná	7	182
146	07.26	726	CLS de San Rafael del Paraná	7	203
170	09.08	908	CLS de San Roque G. de Sta. Cruz	9	227
182	10.01	1001	CLS de Ciudad del Este	10	239
183	10.02	1002	CLS de Hernandarias	10	240
184	10.03	1003	CLS de Santa Fe del Paraná	10	241
185	10.04	1004	CLS de Santa Rita	10	242
186	10.05	1005	CLS de Juan León Mallorquín	10	243
187	10.06	1006	CLS de Los Cedrales	10	244
188	10.07	1007	CLS de Mbaracayu	10	245
189	10.08	1008	CLS de Ñacunday	10	246
190	10.09	1009	CLS de Itakyry	10	247
200	10.19	1019	CLS de Domingo Martínez de Irala	10	257
1117	11.17	1117	CLS de J. A. Saldivar	11	64
228	12.04	1204	CLS de Villalbín	12	284
247	14.00	1400	CRS de  Canindeyu	14	304
266	15.03	1503	CLS de Benjamín Aceval	15	323
273	16.01	1601	CLS de Filadelfia	16	330
274	16.02	1602	CLS de Mariscal Estigarribia	16	331
275	16.03	1603	CLS de Loma Plata	16	332
276	17.00	1700	CRS de  Alto Paraguay	17	333
277	17.01	1701	CLS de Carmelo Peralta	17	334
278	17.02	1702	CLS de Bahia Negra	17	335
279	17.03	1703	CLS de Fuerte Olimpo	17	336
280	17.04	1704	CLS de Puerto Casado	17	337
281	18.00	1800	Asunción	18	338
21	01.11	111	CLS de San Carlos del Apa	1	66
\.


--
-- Name: consejos_salud_id_seq; Type: SEQUENCE SET; Schema: aplicacion; Owner: postgres
--

SELECT pg_catalog.setval('aplicacion.consejos_salud_id_seq', 296, true);


--
-- Data for Name: departamentos; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.departamentos (dpto, descripcion, verificador) FROM stdin;
1	Concepción 	83
2	San Pedro 	355
3	Cordillera	356
4	Guairá	357
5	Caaguazú 	358
6	Caazapá	359
7	Itapúa 	360
8	Misiones 	361
9	Paraguarí 	362
10	Alto Paraná	363
11	Central 	364
12	Ñeembucú 	365
13	Amambay	366
14	Canindeyú 	367
15	Pdte. Hayes	368
16	Boquerón 	369
17	Alto Paraguay 	370
18	Capital 	371
\.


--
-- Name: fondos_equidad_id_seq; Type: SEQUENCE SET; Schema: aplicacion; Owner: postgres
--

SELECT pg_catalog.setval('aplicacion.fondos_equidad_id_seq', 99, true);


--
-- Data for Name: objetos_gastos; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.objetos_gastos (objeto, descripcion, imputa) FROM stdin;
100	SERVICIOS PERSONALES	N
110	REMUNERACIONES BÁSICAS	N
111	SUELDOS	S
112	DIETAS	S
114	AGUINALDO	S
120	REMUNERACIONES TEMPORALES	N
122	GASTOS DE RESIDENCIA	S
123	REMUNERACIONES EXTRAORDINARIAS	S
125	REMUNERACIÓN ADICIONAL	S
130	ASIGNACIONES COMPLEMENTARIAS	N
131	SUBSIDIO FAMILIAR	S
132	ESCALAFÓN DOCENTE	S
133	BONIFICACIONES Y GRATIFICACIONES	S
134	APORTE JUBILATORIO DEL EMPLEADOR	S
135	BONIFICACIONES POR VENTAS Y COBRANZAS	S
136	BONIFICACIÓN POR EXPOSICIÓN AL PELIGRO	S
137	GRATIFICACIONES POR SERVICIOS ESPECIALES	S
138	UNIDAD BÁSICA ALIMENTARIA - UBA	S
139	ESCALAFÓN DIPLOMÁTICO Y ADMINISTRATIVO	S
140	PERSONAL CONTRATADO	N
141	CONTRATACIÓN DE PERSONAL TECNICO	S
142	CONTRATACIÓN DE PERSONAL DE SALUD	S
143	CONTRATACIÓN OCASIONAL DEL PERSONAL DOCENTE Y DE BLANCO	S
144	JORNALES	S
145	HONORARIOS PROFESIONALES	S
146	CONTRATACION DE PERSONAL DEL SERVICIO EXTERIOR	S
147	CONTRATACIÓN DE PERSONAL PARA PROGRAMAS DE ALIMENTACIÓN ESCOLAR Y CONTROL SANITARIO	S
148	CONTRATACIÓN DE PERSONAL DOCENTE E INSTRUCTORES PARA CURSOS ESPECIALIZADOS, POR UNIDAD DE TIEMPO Y POR ORA CÁTEDRA	S
160	SUBSIDIO ANUAL PARA ADQUISICIÓN DE EQUIPOS Y VESTUARIOS DEL PERSONAL DE LAS FUERZAS	N
161	SUELDOS	S
162	GASTOS DE REPRESENTACIÓN	S
163	AGUINALDO	S
180	FONDO DE RESERVAS ESPECIALES	N
182	FONDO COMP. SALARIAL Y REINSERC. LAB.	S
183	FONDO DE RECATEG. SALARIAL P/ MERITOS	S
185	FONDO PARA CRECIMIENTO VEGETATIVO	S
190	OTROS GASTOS DEL PERSONAL	N
191	SUBSIDIO PARA LA SALUD	S
192	SEGURO DE VIDA	S
193	SUBSIDIO ANUAL PARA ADQUISICIÓN DE EQUIPOS Y VESTUARIOS DEL PERSONAL DE LAS FUERZAS PUBLCAS	S
194	SUBSIDIO PARA LA SALUD DEL PERSONAL DE LAS FUERZAS PUBLICAS	S
195	BONIFICACIÓN FAMILIAR PARA LOS EFECTIVOS DE LAS FUERZAS PUBLICAS	S
199	OTROS GASTOS DE PERSONAL	S
200	SERVICIOS NO PERSONALES	N
210	SERVICIOS BÁSICOS	N
211	ENERGÍA ELÉCTRICA	S
212	AGUA	S
214	TELÉFONOS, TELEFAX Y OTROS SERV. COMUNIC.	S
215	CORREOS Y OTROS SERV. POSTALES	S
219	SERVICIOS BÁSICOS VARIOS	S
220	TRANSPORTE Y ALMACENAJE	N
221	TRANSPORTE	S
222	ALMACENAJE	S
223	TRANSPORTE DE PERSONAS	S
229	TRANSPORTE Y ALMACENAJE, VARIOS	S
230	PASAJES Y VIÁTICOS	N
231	PASAJES	S
232	VIÁTICOS Y MOVILIDAD	S
233	GASTOS DE TRASLADO	S
239	PASAJES Y VIÁTICOS, VARIOS	S
240	GASTOS POR SERVICIOS DE ASEO, MANTENIMIENTO Y REPARACIÓN	N
241	MANT. Y REP. MENORES DE VIAS DE COMUNICACIÓN	S
242	MANT. Y REP. MENORES DE EDIFICIOS Y LOCALES	S
243	MANT. Y REP. MENORES DE MAQ. EQ. Y MUEBLES DE OFICINA	S
244	MANT. Y REP. MENORES DE VEHICULOS	S
245	SERVICIOS DE LIMPIEZA, ASEO Y FUMIGACIÓN	S
246	MANT. Y REP. MENORES DE INSTALACIONES	S
247	MANT. Y REP. MENORES DE OBRAS	S
248	OTROS MANT. Y REPARAC. MENORES	S
249	SERV., ASEO, MANT. Y REPARAC. MEN., VARIOS	S
250	ALQUILERES Y DERECHOS	N
251	ALQUILER DE EDIFICIOS Y LOCALES	S
252	ALQUILER DE MAQUINASRIAS Y EQUIPOS	S
253	DERECHOS DE BIENES INTANGIBLES	S
254	ALQUILER EQ. DE COMPUTACIÓN	S
255	ALQUILER DE FOTOCOPIADORAS	S
256	ARRENDAMIENTO DE TIERRAS Y TERRENOS	S
257	ALQUILER DE VIVIENDAS	S
258	ALQUILERES Y DERECHOS SIST. LEASING	S
259	ALQUILERES Y DERECHOS VARIOS	S
260	SERVICIOS TÉCNICOS Y PROFESIONALES	N
261	DE INFORMATICA Y SISTISTEMAS COMPUTARIZ.	S
262	IMPRENTA, PUBLICACIONES Y REPRODUCCIONES	S
263	SERVICIOS BANCARIOS	S
264	PRIMAS Y GASTOS DE SEGURO	S
265	PUBLICIDAD Y PROPAGANDA	S
266	CONSULTORIAS, ASESORÍAS E INVESTIG.	S
267	PROMOCIONES Y EXPOSICIONES	S
268	SERVICIOS DE COMUNICACIONES	S
269	SERVICIOS TECNICOS Y PROF., VARIOS	S
270	SERVICIO SOCIAL	N
271	SERVICIOS DE SEGURO MEDICO	S
272	ASISTENCIA SOCIAL	S
273	SERVICIOS DE SEPELIO	S
274	SERVICIOS MÉDICOS Y DE SEPELIO PARA JUVILADOS Y PENSIONADOS	S
275	SERVICIOS DE PRIMEROS AUXILIOS Y TERCERIZADOS	S
279	SERVICIO SOCIAL SIN DISCRIMINAR	S
280	OTROS SERVICIOS EN GENERAL	N
281	SERVICIOS DE CEREMONIAL	S
282	SERVICIOS DE VIGILANCIA	S
283	GASTOS DE PECULIO	S
284	SERVICIOS DE CATERING	S
288	SERVICIOS EN GENERAL	S
289	OTROS SERVICIOS VARIOS	S
290	SERV. CAPACITACIÓN Y ADIESTRAMIENTO	N
291	CAPACITACIÓN DEL PERSONAL DEL ESTADO	S
292	CAPACITACIÓN Y FORMACIÓN LABORAL	S
293	CAPACITACIÓN ESPECIALIZADA	S
294	CAPACITACIÓN INSTITUCIONAL A LA COMUNIDAD	S
299	CAPACITACIÓN Y ADIESTRAMIENTO VARIOS	S
300	BIENES DE CONSUMO E INSUMOS	N
310	PRODUCTOS ALIMENTICIOS	N
311	ALIMENTOS PARA PERSONAS	S
312	ALIMENTOS PARA ANIMALES	S
319	PRODUCTOS ALIMENTICIOS VARIOS	S
320	TEXTILES Y VESTUARIOS	N
321	HILADOS Y TELAS	S
322	PRENDAS DE VESTIR	S
323	CONFECCIONES TEXTILES	S
324	CALZADOS	S
325	CUEROS, CAUCHOS Y GOMAS	S
329	TEXTILES Y CONFECCIONES, VARIOS	S
330	PRODUC. PAPEL, CARTÓN E IMPRESOS	N
331	PAPEL DE ESCRITORIO Y CARTÓN	S
332	PAPEL PARA COMPUTACIÓN	S
333	PRODUCTOS DE ARTES GRAFICAS	S
334	PRODUCTOS DE PAPEL	S
335	LIBROS, REVISTAS, PERIÓDICOS	S
336	TEXTOS DE ENSEÐANZA	S
339	PRODUC.PAPEL, CARTON E IMPRESOS, VARIOS	S
340	BIENES DE CONSUMO OFIC.E INSUMOS	N
341	ELEMENTOS DE LIMPIEZA	S
342	ÚTILES DE ESCRITORIO, OFICINA Y ENSERES	S
343	ÚTILES Y MATERIALES ELÉCTRICOS	S
344	UTENSILIOS DE COCINA Y COMEDOR	S
345	PROD. DE VIDRIO, LOZA Y PORCELANA	S
346	REPUESTOS Y ACCESORIOS MENORES	S
347	ELEMENTOS Y ÚTILES DIVERSOS	S
349	BIENES DE CONSUMO, VARIOS	S
350	PRODUC. E INST. QUÍMICOS Y MEDICAMENTOS	N
351	COMPUESTOS QUÍMICOS	S
352	PROD. FARMACEUT. Y MEDIC.	S
353	ABONOS Y FERTILIZANTES	S
354	INSECTICIDAS, FUMIGANTES Y OTROS	S
355	TINTAS, PINTURAS Y COLORANTES	S
356	ESPECÍFICOS VETERINARIOS	S
357	PRODUCTOS DE MATERIAL PLÁSTICO	S
358	ÚTILES Y MAT. MÉDICO-QUIRÚRGICOS Y DE LAB.	S
359	PROD. E INST. QUÍMICOS Y MEDICINALES VARIOS	S
360	COMBUSTIBLES Y LUBRICANTES	N
361	COMBUSTIBLES	S
362	LUBRICANTES	S
369	COMBUSTIBLES Y LUBRICANTES VARIOS	S
390	OTROS BIENES DE CONSUMO	N
391	ARTÍCULOS DE CAUCHO	S
392	CUBIERTAS Y CÁMARAS DE AIRE	S
393	ESTRUCTURAS METÁLICAS ACABADAS	S
394	HERRAMIENTAS MENORES	S
395	MATERIAL DE SEGURIDAD Y ADIESTRAMIENTO	S
396	ARTÍCULOS DE PLÁSTICO	S
397	PRODUCTOS E INSUMOS METÁLICOS	S
398	PRODUCTOS E INSUMOS NO METÁLICOS	S
399	BIENES DE CONSUMO VARIOS	S
400	BIENES DE CAMBIO	N
410	INSUMOS DEL SECTOR AGROP. Y FORESTAL	N
411	PROD. PECUARIOS	N
412	PROD. AGROFORESTALES	N
413	MADERA, CORCHO Y SUS MANUFACTURAS	N
414	PROD. E INSUMOS AGROPECUARIOS	N
418	TRANSPORTE, ALMACENAJE Y OTROS GASTOS	N
419	INSUMOS DEL SECTOR AGROP. Y FORESTAL, VARIOS	N
420	MINERALES	N
421	PETRÓLEO CRUDO Y GAS NATURAL	N
422	PIEDRA, ARCILLA, CERÁMICA, ARENA Y DERIV.	N
423	MINERALES METALÍFEROS	N
424	CARBÓN MINERAL	N
425	CEMENTO, CAL, ASBESTO, YESO, Y SUS DERIV.	N
426	PRODUCTOS FERROSOS	N
427	PRODUCTOS NO FERROSOS	N
428	TRANSPORTE, ALMACENAJE Y OTROS GASTOS	N
429	MINERALES VARIOS	N
430	INSUMOS INDUSTRIALES	N
431	INSUMOS INDUSTRIALES	N
438	TRANSPORTE, ALMACENAJE Y OTROS GASTOS	N
439	INSUMOS INDUSTRIALES VARIOS	N
440	ENERGÍA Y COMBUSTIBLES	N
441	ENERGÍA	N
442	COMBUSTIBLES	N
443	LUBRICANTES	N
448	TRANSPORTE, ALMACENAJE Y OTROS GASTOS	N
449	ENERGÍA Y COMBUSTIBLES VARIOS	N
450	TIERRAS, TERRENOS Y EDIFICACIONES	N
451	TIERRAS, TERRENOS Y EDIFICACIONES	N
458	MENSURAS Y OTROS GASTOS	N
459	TIERRAS, TERRENOS Y EDIFICACIONES VARIOS	N
490	OTRAS MAT. PRIMAS Y PROD. SEMIELAB.	N
491	ESPECIES TIMBRADAS Y VALORES	N
492	INSUMOS QUÍMICOS Y DE LAB. INDUST.	N
498	TRANSPORTE, ALMACENAJE Y OTROS GASTOS	N
499	MATERIAS PRIMAS Y PROD. SEMIELAB. VARIOS	N
500	INVERSIÓN FÍSICA	N
510	ADQUISICIÓN DE INMUEBLES	N
511	TIERRAS Y TERRENOS	S
512	ADQUISICIÓN DE EDIFICIOS E INSTALACIONES	S
519	ADQUISICIONES DE INMUEBLES VARIOS	S
520	CONSTRUCCIONES	N
521	CONST. DE OBRAS DE USO PÚBLICO	S
522	CONST. DE OBRAS DE USO INSTITUC.	S
523	CONST. DE OBRAS DE USO MILITAR	S
524	CONST. DE OBRAS DE USO PRIVADO	S
525	CONST. DE OBRAS DE INFRAESTRUCTURA	S
526	OBRAS E INSTALAC. VARIAS DE INFRAEST.	S
528	ESCALAMIENTO DE COSTOS	S
529	CONSTRUCCIONES VARIAS	S
530	ADQ. DE MAQ. EQ. Y HERRAM. MAY.	N
531	MAQ. Y EQ. DE CONSTRUCCIÓN	S
532	MAQ. Y EQ. AGROPEC. E INDUSTRIALES	S
533	MAQ. Y EQ. INDUSTRIALES	S
534	EQ. EDUC. Y RECREAC.	S
535	EQ. DE SALUD Y LABORATORIO	S
536	EQ. COMUNICACIÓN Y SEÐALAM.	S
537	EQUIPOS DE TRANSPORTE	S
538	HERRAM., APARATOS E INST. EN GRAL.	S
539	MAQ. EQUIPOS Y HERRAM. MAYORES, VARIOS	S
540	ADQUISICIONES DE EQUIPOS DE OFICINA Y COMPUTACION	N
541	ADQ. DE MUEBLES Y ENCERES	S
542	ADQ. DE EQUIPOS DE OFICINA	S
543	ADQ. DE EQ. DE COMPUTACIÓN	S
544	ADQ. DE EQ. DE IMPRENTA	S
549	ADQUISICIONES DE EQUIPOS DE OFICINA Y COMPUTACIÓN VARIAS	S
550	ADQ. DE EQ. MILITAR Y DE SEGURIDAD	N
551	EQ. MILITAR Y DE SEGURIDAD	S
552	EQ. DE SEGURIDAD INSTITUC.	S
559	EQUIPOS MILITARES Y DE SEGURIDAD VARIOS	S
560	ADQ. DE SEMOVIENTES	N
569	ADQ. DE SEMOVIENTES	S
570	ADQ. DE ACTIVOS INTANGIBLES	N
579	ACTIVOS INTANGIBLES	S
580	ESTUDIOS PROY. INVERSIÓN	N
589	ESTUDIOS Y PROYECTOS DE INVERSIÓN VARIOS	S
590	OTROS GASTOS DE INV. Y REP. MAYORES	N
591	INV. EN REC. NAT. AL SECTOR PUB.	S
592	INV. EN REC. NAT. AL SECTOR PRIV.	S
593	INDEMNIZACIONES POR INMUEBLES	S
594	REPARAC. MAY. DE INMUEBLES	S
595	REPARAC. MAY. DE EQUIPOS	S
596	REPARAC. MAY. DE MAQ.	S
597	REPARAC. MAY. DE INSTALAC.	S
598	REPARAC. MAY. DE HERRAM. Y OTROS	S
599	REPARAC. MAYORES, VARIOS	S
600	INVERSIÓN FINANCIERA	N
610	ACCIONES Y PARTICIPACIONES DE CAPITAL	N
611	APORTES DE CAPITAL EN ENTIDADES NACIONALES	N
612	APORTES DE CAPITAL EN ENTIDADES BINACIONALES	N
613	APORTES DE CAPITAL EN ORGANISMOS	N
619	INTERNACIONALES	N
620	ACCIONES Y PARTICIPACIÓN DE CAPITAL VARIOS	N
621	PRESTAMOS A ENTIDADES DEL SECTOR PÚBLICO	N
629	PRÉSTAMOS A ENTIDADES DEL SECTOR PÚBLICO VARIOS	N
630	PRESTAMOS AL SECTOR PRIVADO	N
631	PRÉSTAMOS A FAMILIAS	N
632	PRÉSTAMOS A EMPRESAS PRIVADAS	N
633	GARANTÍA PARA PRÉSTAMOS	N
634	CONTRATO FIDUCIARIO	N
635	PRÉSTAMOS AL FONDO PARA VIVIENDAS COOPERATIVAS (FONCOOP)	N
639	PRÉSTAMOS AL SECTOR PRIVADO VARIOS	N
640	ADQUISICIONES DE TÍTULOS Y VALORES	N
641	TÍTULOS Y VALORES VARIOS	N
642	ADQUISICIÓN DE BONOS Y OTROS VALORES CON FONDOS DE JUBILACIONES	N
649	ADQUISICIONES DE TÍTULOS Y VALORES VARIOS	N
650	DEPÓSITOS A PLAZO FIJO	N
651	DEPÓSITOS A PLAZO FIJO	N
659	DEPÓSITOS A PLAZO FIJO VARIOS	N
660	PRESTAMOS A INSTITUCIONES FINANCIERAS INTERMEDIARIAS	N
661	PRESTAMOS A INSTITUCIONES FINANCIERAS INTERMEDIARIAS	N
669	PRÉSTAMOS A INSTITUCIONES FINANCIERAS INTERMEDIARIAS VARIAS	N
700	SERVICIO DE LA DEUDA PÚBLICA	N
710	INTERESES DE LA DEUDA PÚBLICA INTERNA	N
711	INTERESES DE LA DEUDA C/ SEC. PUB. FINANC.	N
712	INTERESES DE LA DEUDA C/ SEC. PUB. NO FIN.	N
713	INTERESES DE LA DEUDA C/ EL SEC. PRIV.	N
714	INTERESES DEL CRED. INTERNO DE PROV.	N
715	INTERESES POR DEUDA BONIFICADA	N
719	INTERESES DEUDA PÚBLICA INTERNA VARIOS	N
720	INTERESES DE LA DEUDA PÚB. EXTERNA	N
721	INTERESES DE LA DEUDA C/ ORG. MULTIL.	N
722	INTERESES DE LA DEUDA C/ GOB. EXT. Y AG. FIN.	N
723	INTERESES DE LA DEUDA C/ ENTES FIN. PRIV. EXT.	N
724	INTERESES DE LA DEUDA C/ PROV. DEL EXTERIOR	N
725	INTERESES P/ DEUDA EXTERNA BONIFICADA	N
729	INTERESES DE LA DEUDA PÚBLICA EXTERNA VARIOS	N
730	AMORTIZ. DEUDA PÚBLICA INTERNA	N
731	AMORTIZ. DEUDA C/ SEC. PUB. FINANC.	N
732	AMORTIZ. DEUDA C/ SEC. PUB. NO FINANC.	N
733	AMORTIZ. DEUDA C/ SEC. PRIVADO	N
734	AMORTIZ. CREDITO INTERNO A PROV.	N
735	AMORTIZ. POR DEUDA BONIF.	N
739	AMORTIZACIONES DE LA DEUDA PÚBLICA INTERNA VARIAS	N
740	AMORTIZ. DEUDA PÚBLICA EXTERNA	N
741	AMORTIZ. DEUDA C/ ORG. MULTILAT.	N
742	AMORTIZ. DEUDA C/ GOB. EXT. Y AG. FIN.	N
743	AMORTIZ. DEUDA C/ ENTES FIN. PRIV. EXT.	N
744	AMORTIZ. DEUDA C/ PROV. DEL EXTERIOR	N
745	AMORTIZ. DEUDA EXTERNA BONIFICADA	N
749	AMORTIZACIONES DE LA DEUDA PÚBLICA EXTERNA VARIAS	N
750	COMISIONES	N
751	COM. Y OTROS GASTOS DE LA DEUDA INT.	N
752	COM. Y OTROS GASTOS DE LA DEUDA EXT.	N
753	COM. Y OTROS GASTOS DE LA DEUDA INT. BONIF.	N
754	COM. Y OTROS GASTOS DE LA DEUDA EXT. BONIF.	N
759	COMISIONES VARIAS	N
760	OTROS GASTOS SERV. DEUDA PÚBLICA	N
761	AMORTIZ. DEUDA PÚBLICA EXTERNA	N
762	AMORTIZ. DEUDA PÚBLICA EXTERNA BONIF.	N
763	INTERESES DE LA DEUDA PÚB. EXTERNA	N
764	INTERESES DE LA DEUDA PÚB. EXTERNA BONIF.	N
765	COMIS. Y OTROS GASTOS DEUDA PÚB. EXT.	N
766	COMIS. Y OTROS GASTOS DEUDA PÚB. EXT. BONIF.	N
769	OTROS GASTOS DEL SERVICIO DE LA DEUDA PUBLICA VARIAS	N
800	TRANSFERENCIAS	N
810	TRANSFERENCIAS CONSOLIDABLES CORRIENTES AL SECTOR PUBLICO	N
811	TRANSF. CONSOLID. ADM. CENTRAL A ENT. DESC.	N
812	TRANSF. CONSOLID. ENT. DESC. A ADM. CTRAL.	N
813	TRANSF. CONSOLID. P/ COPARTIC. DE IVA	N
814	TRANSF. CONSOLID. P/ COPARTIC. DE JUEGOS DE AZAR	N
815	TRANSF. CONSOLID. P/ COPARTIC. DE ROYALTIES	N
816	TRANSF. CONSOLID. E/ ENT. DESCENT.	N
817	TRANSF. CONSOLID. E/ ORG. ADM. CENTRAL	N
818	TRANSF. CONSOLID. DE LAS E.D. A LA ADM. CENTRAL	N
819	OTRAS TRANSF. CONSOLID. CORRIENTES	N
820	TRANSFERENCIAS A JUBILADOS Y PENSIONADOS	N
821	JUBIL. Y PENS. DE FUNCIONARIOS Y EMP. SECTOR PUBLICO Y PRIVADO	N
822	JUBIL. Y PENS. DE MAGISTRADOS JUDIC.	N
823	JUBIL. Y PENS. DEL MAGISTERIO NACIONAL	N
824	JUBIL. Y PENS.DE DOCENTES UNIVERSITARIOS	N
825	JUBIL. Y PENS. DE LAS FUERZAS ARMADAS	N
826	JUBIL. Y PENS. DE LAS FUERZAS POLICIALES	N
827	PENSIONES GRACIABLES	N
828	PENSIONES A VETERANOS Y LISIADOS DE LA GUERRA DEL CHACO	N
829	OTRAS TRANSF. A JUBILADOS Y PENS.	N
830	OTRAS TRANSFERENCIAS CORRIENTES AL SECTOR PUBLICO O PRIVADO	N
831	APORTES A ENT. C/ FINES SOCIALES O EMERG. NACIONAL	N
832	APORTES DE LA TESORERÍA GENERAL	N
833	TRANSF. A MUNICIPALIDADES	N
834	OTRAS TRANSF. AL SECTOR PÚB.Y ORG. REGIONALES	N
835	OTROS APORTES DE LA TESORERÍA GENERAL	N
836	TRANSFERENCIAS A ORGANIZACIONES MUNICIPALES	N
837	TRANSFERENCIAS A ENTIDADES DE SEGURIDAD SOCIAL	N
838	SUBSIDIO POR TARIFA SOCIAL DE LA ANDE	N
839	OTRAS TRANSFERENCIAS CORRIENTES AL SECTOR PÚBLICO O PRIVADO VARIAS	N
840	TRANSFERENCIAS CORRIENTES AL SECTOR PRIVADO	N
841	BECAS	N
842	APORTES A ENTIDADES EDUCATIVAS E INSTITUCIONES SIN FINES DE LUCRO	N
843	APORTES A PARTIDOS POLÍTICOS	N
844	SUBSIDIOS A LOS PARTIDOS POLÍTICOS	N
845	INDEMNIZACIONES	N
846	SUBSIDIOS Y ASISTENCIA SOCIAL A PERSONAS Y FAMILIAS DEL SECTOR PRIVADO	N
847	APORTES DE PROGRAMAS DE EDUCACIÓN PÚBLICA	N
848	TRANSFERENCIAS PARA ALIMENTACIÓN ESCOLAR	N
849	OTRAS TRANSFERENCIAS CORRIENTES	N
850	TRANSFERENCIAS CORRIENTES AL SECTOR EXTERNO	N
851	TRANSFERENCIAS CORRIENTES AL SECTOR EXTERNO	N
852	TRANSFERENCIAS CORRIENTES A ENTIDADES DEL SECTOR PRIVADO, ACADÉMICO Y/O PÚBLICO DEL EXTERIOR	N
853	TRANSFERENCIAS CORRIENTES A ORGANISMOS Y AGENCIAS ESPECIALIZADAS	N
854	TRANSFERENCIAS CORRIENTES A REP. DIPLOMÁTICAS, CONSULARES Y OPERACIONES MILITARES A CARGO DE LAS NN.UU.	N
859	TRANSFERENCIAS CORRIENTES AL SECTOR EXTERNO VARIAS	N
860	TRANSFERENCIAS CONSOLIDABLES DE CAPITAL AL SECTOR PÚBLICO CAPITAL	N
861	TRANSFERENCIAS CONSOLIDABLES DE LA ADM. CENTRAL A ENTIDADES DESCENTRALIZADAS	N
862	TRANSFERENCIAS CONSOLIDABLES DE LAS ENTIDADES DESCENTRALIZADAS A LA TESORERÍA GENERAL	N
863	TRANSFERENCIAS CONSOLIDABLES POR COPARTICIPACIÓN DE IVA	N
864	TRANSFERENCIAS CONSOLIDABLES POR COPARTICIPACIÓN DE JUEGOS DE AZAR	N
865	TRANSFERENCIAS CONSOLIDABLES POR COPARTICIPACIÓN DE ROYALTIES	N
866	TRANSFERENCIAS CONSOLIDABLES ENTRE ENTIDADES DESCENTRALIZADAS	N
867	TRANSFERENCIAS CONSOLIDABLES ENTRE ORG. ADM. CENTRAL	N
868	TRANSFERENCIAS CONSOLIDABLES DE LAS ENTIDADES DESCENTRALIZADAS A LA ADM. CENTRAL	N
869	OTRAS TRANSF. CONSOLID. DE CAPITAL	N
870	TRANSFERENCIAS DE CAPITAL AL SECTOR PRIVADO	N
871	TRANSF. DE CAPITAL AL SECTOR PRIVADO	N
872	SUBSIDIOS DEL FONDO DE SERVICIOS UNIVERSALES	N
873	TRANSFERENCIAS A PRODUCTORES INDIVIDUALES Y/O ORGANIZACIONES DE PRODUCTORES AGROPECUARIOS, FORESTALES Y COMUNIDADES INDÍGENAS	N
874	APORTES A ENTIDADES EDUCATIVAS E INSTITUCIONES PRIVADAS SIN FINES DE LUCRO	N
875	SUBSIDIO HABITACIONAL DIRECTO	N
876	TRANSFERENCIAS AL SECTOR PRIVADO EMPRESARIAL	N
877	TRANSFERENCIAS AL FONDO NACIONAL DE LA VIVIENDA SOCIAL (FONAVIS)	N
878	APORTES O SUBSIDIOS AL TRANSPORTE PÚBLICO COLECTIVO Y OTROS SECTORES	N
879	TRANSF. DE CAPITAL AL SECTOR PRIVADO VARIAS	N
880	TRANSFERENCIAS DE CAPITAL AL SECTOR EXTERNO	N
881	TRANSF. DE CAPITAL AL SECTOR EXTERNO	N
882	TRANSFERENCIA A LA ENTIDAD ITAIPU BINACIONAL	N
889	TRANSFERENCIAS DE CAPITAL AL SECTOR EXTERNO VARIAS	N
890	OTRAS TRANSFERENCIAS DE CAPITAL AL SECTOR PUBLICO O PRIVADO	N
891	TRANSF. DE CAPITAL AL BANCO CENTRAL DEL PARAGUAY	N
892	APORTES DE LA TESORERÍA GENERAL	N
893	TRANSFERENCIAS A MUNICIPALIDADES	N
894	OTRAS TRANSFERENCIAS AL SECTOR PÚBLICO	N
895	OTROS APORTES DE LA TESORERÍA GENERAL	N
896	TRANSFERENCIAS A ORGANIZACIONES MUNICIPALES	N
897	TRANSFERENCIAS DE CAPITAL AL FONDO DE JUBILACIONES Y PENSIONES PARA MIEMBROS DEL PODER LEGISLATIVO	N
898	TRANSFERENCIAS DE CAPITAL DEL BANCO CENTRAL DEL PARAGUAY AL FONDO DE GARANTÍA DE DESARROLLO (FGD)	N
899	OTRAS TRANSFERENCIAS DE CAPITAL AL SECTOR PÚBLICO O PRIVADO VARIAS	N
900	OTROS GASTOS	N
910	PAGO DE IMPUESTOS, TASAS, G. JUDICIALES Y OTROS	N
911	IMPUESTOS DIRECTOS	N
912	IMPUESTOS INDIRECTOS	N
913	TASAS Y CONTRIBUCIONES	N
914	MULTAS Y RECARGOS	N
915	GASTOS JUDICIALES	N
916	ESTUDIO DE HISTOCOMPATIBILIDAD (HLA) E INMUNEGENÉTICA (ADN)	N
917	ADQUISICIONES SIMULADAS	N
918	PAGOS Y COMPENSACIONES ENTRE ENTIDADES DEL ESTADO	N
919	IMPUESTOS, TASAS Y GASTOS JUD. VARIOS	N
920	DEVOLUCIÓN DE IMPUESTOS Y OTROS INGRESOS NO TRIBUTARIOS	N
921	DEVOLUCIÓN DE IMPUESTOS, TASAS Y CONTRIBUCIONES	N
922	DEVOLUCIÓN DE INGRESOS NO TRIBUTARIOS	N
923	DEVOLUCIÓN DE ARANCELES	N
924	DEVOLUCIÓN DE DEPÓSITOS Y GARANTÍAS	N
929	DEVOLUCIONES VARIAS	N
939	INTERESES DE ENT. FINANC. PUBLICAS VARIOS	N
940	DESCUENTOS POR VENTAS	N
949	DESCUENTOS VARIOS	N
950	RESERVAS TÉCNICAS Y CAMBIARIAS	N
951	PREVISIÓN PARA DIFERENCIA DE CAMBIO	N
959	RESERVAS TÉCNICAS VARIAS	N
960	DEUDAS PENDIENTES PAGO DE GASTOS CORRIENTES DE EJERCICIOS ANTERIORES	N
961	SERVICIOS PERSONALES	N
962	SERVICIOS NO PERSONALES	N
963	BIENES DE CONSUMO E INSUMOS	N
964	SERVICIO DE LA DEUDA PÚBLICA	N
965	TRANSFERENCIAS	N
969	OTROS GASTOS	N
970	GASTOS RESERVADOS	N
979	GASTOS RESERVADOS	N
980	DEUDAS PENDIENTES PAGO DE GASTOS DE CAPITAL DE EJERCICIOS ANTERIORES	N
981	SERVICIOS PERSONALES	N
982	SERVICIOS NO PERSONALES	N
983	BIENES DE CONSUMO E INSUMOS	N
984	BIENES DE CAMBIO	N
985	INVERSIÓN FÍSICA	N
986	INVERSIÓN FINANCIERA	N
987	SERVICIO DE LA DEUDA PÚBLICA	N
988	TRANSFERENCIAS	N
989	OTROS GASTOS	N
\.


--
-- Data for Name: origenes_ingresos; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.origenes_ingresos (origen, descripcion) FROM stdin;
151	Transferencias a la Tesoreria Central
\.


--
-- Data for Name: rendiciones_gastos; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.rendiciones_gastos (rendicion, transferencia, resolucion_numero, tipo_comprobante, comprobante_numero, objeto, concepto, fecha, importe, observacion, consejo, ruc_factura, timbrado_venciomiento) FROM stdin;
158	47	61/19	1	001-001-00055	142	Contratación de personal de salud - Claudia Noelia Benítez Escobar - Dra. Odontóloga - Abril, Mayo 2019	2019-06-14	3000000	OPN° 883/2019	401		\N
164	47	61/19	1	001-001-0000003	142	Contratación de personal de salud - María Liz Martínez Verón - Licenciada en  Enfermería - Urgencias Adultos - Abril, Mayo 2019\n	2019-06-14	2000000	OPN° 887/2019	401		\N
159	47	61/19	1	001-001-000242	142	Contratación de personal de salud - Diana Raquel Aquino Lovatti - Dra. Odontóloga - Abril, Mayo 2019	2019-06-19	3600000	OPN° 884/2019	401		\N
160	47	61/19	3	001-001-0001439	142	Contratación de personal de salud - José De Jesús  Godoy Dávalos - Tec. Instrumentista - Quirófano - Abril, Mayo 2019	2019-06-14	2000000	OPN°885/2019	401		\N
134	46	61/19	3	001-001-1009	142	C.P.S.Lizza Romina Barrientos Aguirre**Enfermera de Guardia  Corresponde a ENERO Y FEBRERO 2019	2019-05-01	1600000	OP 269	402		\N
133	45	168/19	1	100-001-0000206	142	CPS - Odontologo - Jorge Derlis Gomez Miranda - PS Boqueron - Borja - Mayo 2019, Junio 2019, Julio 2019	2019-08-09	3000000		418		2020-06-30
135	47	61/19	1	001-001-0056	142	Contratación de personal de salud - Alba Victoria Maidana Encina - Lic en Enfermeria - Servicio de PAI Hospital  Regional - Febrero, Marzo 2019	2019-05-06	2000000	OP N° 810/2019	401		\N
136	47	61/19	1	001-001-000057	142	Contratación de personal de salud - Celsa Gonzalez Fariña - Lic. en Enfermería - Neonatologia - Febrero, Marzo 2019	2019-05-06	2000000	OPN°811/2019	401		\N
137	47	61/19	1	001-001-00054	142	Contratación de personal de salud - Claudia Noelia Benítez Escobar - Dra. Odontóloga - Febrero, Marzo 2019	2019-05-06	2850000	OPN°812/2019	401		\N
138	47	61/19	1	001-001-000239	142	Contratación de personal de salud - Diana Raquel Aquino Lovatti - Dra. Odontóloga - Febrero, Marzo 2019	2019-05-06	3600000	OPN°813/2019	401		\N
139	47	61/19	3	001-001-0001412	142	Contratación de personal de salud - José De Jesús  Godoy Dávalos - Tec. Instrumentista - Quirófano - Febrero, Marzo 2019	2019-05-06	2000000	OP N°814/2019	401		\N
140	47	61/19	1	001-001-0000202	142	Contratación de personal de salud - María Graciela Benítez Matto - Fisioterapeuta - Kinesiólogo - Clínica Médica - Febrero, Marzo 2019	2019-05-06	1306668	OPN°815	401		\N
141	47	61/19	1	001-001-0000001	142	Contratación de personal de salud - María Liz Martínez Verón - Licenciada en  Enfermería - Urgencias Adultos - Febrero, Marzo 2019	2019-05-07	1966667	OPN°816/19	401		\N
142	47	61/19	1	001-001-0000501	142	Contratación de personal de salud - Miguel Ángel García Martínez - Técnico Servicio de laboratorio de TBC - Febrero, Marzo 2019	2019-05-09	1120000	OPN°817/2019	401		\N
143	47	61/19	1	001-001-0000189	142	Contratación de personal de salud - Noelia Noemí  Reyes - Lic. Enfermería servicio Pediatría - Febrero, Marzo 2019\n	2019-05-06	2300000	OPN°818	401		\N
144	47	61/19	3	001-001-0001413	142	Contratación de personal de salud - Norma Estela Valdez de Benítez - Auxiliar de Enfermería - Servicio de Odontología - Febrero, Marzo 2019	2019-05-06	1400000	OPN°819/2019	401		\N
145	74	168/19	1	001-001-00005	142	C.P.S.Lizza Romina Barrientos Aguirre**Enfermera de Guardia  Corresponde a MARZO Y ABRIL 2019	2019-06-07	1600000	OP 280/2019	402		\N
146	47	61/19	3	001-001-0001414	142	Contratación de personal de salud - René Arturo Colman Maidana - Auxiliar Técnico - Servicio De Tomografía Hosp. Reg. - Febrero, Marzo 2019	2019-05-06	1770000	OPN° 820/2019	401		\N
147	74	168/19	3	001-00-0001151	142	C.P.S Laura Betania AcostaTec. en Enfermeria correspondiente a MARZO Y ABRIL 2019	2019-06-07	1600000	O.P N° ° 281/2019	402		\N
148	47	61/19	1	001-001-0000001	142	Contratación de personal de salud - Romina Aquino Álvarez  - Lic. en Enfermería - Quirófano - Febrero, Marzo 2019	2019-05-06	1000000	OPN°821/19	401		\N
149	74	168/19	1	001-001-0005	142	C.P.S.María Asunción Marin Acosta**Enfermera de Guardia  Corresponde a MARZO Y ABRIL 2019\n	2019-06-07	1600000	O.P N° 282/2019	402		\N
150	74	168/19	3	001-001-0001152	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a MARZO 2019\n	2019-06-07	1800000	O-P N° 283/2019	402		\N
151	74	168/19	3	001-001-0001153	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a ABRIL 2019\n	2019-06-07	1800000	O.P N| 284/2019	402		\N
152	47	61/19	1	001-001-0057	142	Contratación de personal de salud - Alba Victoria Maidana Encina - Lic en Enfermeria - Servicio de PAI Hospital  Regional - Abril, Mayo 2019\n	2019-06-14	2000000	OPN°880/2019	401		\N
153	74	168/19	3	001-001-0001154	144	JORNALES.Ramón Antonio Chamorro Acosta**Encargado de Informática  Corresponde a MARZO Y ABRIL  2019\n	2019-06-07	2000000	O.P N° 285/2019	402		\N
154	47	61/19	1	001-001-00102	142	Contratación de personal de salud - Carlos Pedrozo - Lic. en Enfermería - Servicio de Urgencias - Abril, Mayo 2019	2019-06-20	1933334	OPN°881/2019	401		\N
155	74	168/19	3	001-001-0001155	144	JORNALES.Fernando Ramón Galeano Villalba**Encargado de Farmacia  Corresponde a  MARZO Y ABRIL 2019\n	2019-06-07	1500000	O.P N° 286/2019	402		\N
156	47	61/19	1	001-001-0000058	142	Contratación de personal de salud - Celsa Gonzalez Fariña - Lic. en Enfermería - Neonatologia - Abril, Mayo 2019	2019-06-14	2000000	OPN°882/2019	401		\N
161	74	168/19	3	001-001-0001156	144	JORNALES.Ramona Analia Aguirre Garay**Admisión  Corresponde a MARZO Y ABRIL   2019\n	2019-06-07	1300000	O.P N°287/2019	402		\N
162	47	61/19	1	001-001-0000205	142	Contratación de personal de salud - María Graciela Benítez Matto - Fisioterapeuta - Kinesiólogo - Clínica Médica - Abril, Mayo 2019	2019-06-18	1650000	OPN° 886/2019	401		\N
163	74	168/19	3	001-001-0001157	144	JORNALES.Domitila López Pana**Limpiadora  Corresponde a MARZO Y ABRIL  2019\n	2019-06-07	1200000	O.P N° 288/2019	402		\N
165	74	168/19	1	001-001-0000103	145	HONORARIOS PROFESIONALES.Cinthia Mabel Venialgo Morel**Administrador- Contador  Corresponde a MARZO Y ABRIL 2019\n	2019-06-07	2600000	O.P N° 289/2019	402		\N
166	47	61/19	1	001-001-0000502	142	Contratación de personal de salud - Miguel Ángel García Martínez - Técnico Servicio de laboratorio de TBC - Abril, Mayo 2019	2019-06-18	980000	OPN°888/2019	401		\N
167	47	61/19	1	001-001-0000190	142	Contratación de personal de salud - Noelia Noemí  Reyes - Lic. Enfermería servicio Pediatría - Abril, Mayo 2019	2019-06-18	2300000	OPN° 889/2019	401		\N
169	47	61/19	3	001-001-0001440	142	Contratación de personal de salud - Norma Estela Valdez de Benítez - Auxiliar de Enfermería - Servicio de Odontología - Abril, Mayo 2019	2019-06-14	1400000	OPN° 890/2019	401		\N
170	47	61/19	3	001-001-0001441	142	Contratación de personal de salud - René Arturo Colman Maidana - Auxiliar Técnico - Servicio De Tomografía Hosp. Reg. - Abril, Mayo 2019	2019-06-14	1800000	OPN° 891/19	401		\N
171	47	61/19	1	001-001-0000002	142	Contratación de personal de salud - Romina Aquino Álvarez  - Lic. en Enfermería - Quirófano - Abril, Mayo 2019	2019-06-18	2000000	OPN° 892/2019	401		\N
172	76	168/19	1	001-001-0029	142	C.P.S-CARMEN LETICIA MAIDANA VELAZQUEZ**  Corresponde a  MAYO - 2019\n	2019-06-05	600000	O.P N°1109/2019	404		\N
174	47	61/19	3	001-001-0001415	144	Jornales  - Ana Vera Cabrera - Asist.. Administrativo - Febrero, Marzo 2019	2019-05-06	1600000	OPN°822/2019	401		\N
175	76	168/19	1	001-001-0333	142	C.P.S-HONORINA GONZALEZ DE ROJAS*ENF. DE GUARDIA.  Corresponde a  JUNIO - 2019\n	2019-07-01	1000000	O.P N° 1117/2019	404		\N
176	76	168/19	1	001-001-0030	142	C.P.S-CARMEN LETICIA MAIDANA VELAZQUEZ*ENF. DE GUARDIA*  Corresponde a  JUNIO - 2019\n	2019-07-01	600000	O.P N° 1118/2019	404		\N
177	76	168/19	1	001-001-003	142	C.P.S-JOANNA RAMONA ORTIGOZA*ENF. DE GUARDIA*  Corresponde a  JUNIO - 2019\n	2028-07-01	700000	O.P N° 1119/2019	404		\N
178	76	168/19	3	001-001-0510	142	C.P.S -OLGA GIMENEZ CABALLERO *ENF. DE GUARDIA*  Corresponde a  MAYO - 2019\n	2019-06-05	600000	O.P N° 1113/2019	404		\N
168	76	168/19	1	001-001-0330	142	C.P.S-HONORINA GONZALEZ DE ROJAS*ENF DE GUARDIA.  Corresponde a  MAYO - 2019\n	2019-06-05	1000000	O.P N° 1118/2019	404		\N
199	47	61/19	3	001-001-0001419	144	Jornales  - Ilda Mariela  Flor de Vega - Mucama - Servicio de Ropería - Confecciones  - Febrero, Marzo 2019	2019-05-06	1300000	OPN° 826/2019	401		\N
173	76	168/19	1	001-001-0002	142	C.P.S-JOANNA RAMONA ORTIGOZA*ENF. DE GUARDIA*  Corresponde a  MAYO - 2019\n	2019-06-18	700000	O.P N| 1110/2019	404		\N
179	76	168/19	3	001-001-0516	142	C.P.S -OLGA GIMENEZ CABALLERO *ENF. DE GUARDIA *  Corresponde a  JUNIO - 2019\n	2019-07-01	600000	O.P N° 1124/2019	404		\N
180	76	168/19	3	001-001-0514	144	JORNALES -ELSA ALEJANDRA ESCOBAR MACIEL**SECRETARIA ADMINISTRATIVA  Corresponde a  MAYO - 2019\n	2019-06-05	486933	OP N°1111/2019	404		\N
181	76	168/19	3	001-001-0509	144	JORNALES -LIDIA RIVAS ESPINOLA*ADMINISTRADORA*  Corresponde a  MAYO - 2019\n	2019-06-05	1500000	O.P N° 1112/2019	404		\N
182	76	168/19	3	001-001-0512	144	JORNALES -MARIA ANTONIA ORTIZ*COCINERA*  Corresponde a  MAYO - 2019\n	2019-06-05	477273	O.P N° 1114/2019	404		\N
183	76	168/19	3	001-001-0511	144	JORNALES -LOURDES MARGARITA ACHAR*ENCARGADA DE FARMACIA -Corresponde a  MAYO - 2019\n	2019-06-05	500000	O.P N° 1115/2019	404		\N
185	76	168/19	3	001-001-0519	144	JORNALES -LIZ MABEL COLINA*LIMPIADORA*  Corresponde a  JUNIO - 2019\n	2019-07-01	900000	O.P N° 1120/2019	404		\N
184	76	168/19	3	001-001-0513	144	JORNALES -LIZ MABEL COLINA*LIMPIADORA*  Corresponde a  MAYO - 2019\n	2019-06-05	123183	O.P N° 1116/2019	404		\N
186	47	61/19	3	001-001-0001416	144	Jornales  - Daniel Rodrigo Bernal Barquinero - Auxiliar Administrativo - RR.HH. - Hosp. Reg. de Vca. - Febrero, Marzo 2019	2019-05-06	1573333	OPN°823/19	401		\N
187	76	168/19	3	001-001-0518	144	JORNALES -LOURDES MARGARITA ACHAR*ENCARGADA DE FARMACIA*  Corresponde a  JUNIO - 2019\n	2019-07-01	500000	O.P N° 1121/2019	404		\N
188	76	168/19	3	001-001-0517	144	JORNALES -MARIA ANTONIA ORTIZ*COCINERA*  Corresponde a  JUNIO - 2019\n	2019-07-01	500000	O.P N° 1122/2019	404		\N
189	76	168/19	3	001-001-0515	144	JORNALES -LIDIA RIVAS ESPINOLA*ADMINISTRADORA*  Corresponde a  JUNIO - 2019\n	2019-07-01	1500000	O.P N° 1123/2019	404		\N
190	47	61/19	3	001-001-0001417	144	Jornales  - Eugenio Portillo Pereira  - Servicios Generales - Febrero, Marzo 2019	2019-05-06	1466665	OPN° 824/2019	401		\N
191	76	168/19	3	001-001-0520	144	JORNALES -ELSA ALEJANDRA ESCOBAR MACIEL**SECRETARIA ADMINISTRATIVA  Corresponde a  JUNIO - 2019\n	2019-07-01	812500	O.P N° 1125/2019	404		\N
193	76	168/19	1	001-001-0255	242	Mantenimiento y reparaciones menores de edificios y locales\n	2019-07-16	900111	O.P N° 1128/2019|	404		\N
194	76	168/19	1	001-001-0257	243	Mantenimientos y reparaciones menores de maquinarias, equipos y muebles de oficina\n	2019-07-02	400000	O.P N° 1126/2019	404		\N
195	76	168/19	1	001-002-1516	343	Útiles y materiales eléctricos\n	2019-07-16	600000	O.P N° 1127/2019	404		\N
197	71	168/19	1	001-001-0000106	142	Contratacion de personal de salud _Lic Nidia Ester Torres Cano Lic en Enfermeria  mes de mayo/junio/2019	2019-07-01	3200000	O.P N° 58/2019	405		\N
196	71	168/19	3	001-001-0000467	142	Contratacion de personal de salud _Milner Eudelio Gimenez Resquin Tecnico radiologo correspondiente al mes de mayo/junio 2019	2019-07-01	3000000	O.P N°  57/2019	405		\N
198	71	168/19	3	001-001-0000466	142	Contratacion de personal de salud _Ubaldina Veron Fariña Tecnico en Enfermeria correspondiente al mes de mayo /junio /2019	2019-07-01	3000000	O.P.N° 59/2019	405		\N
200	71	168/19	1	001-001-000520	144	Jornales Simone Caballero Administradora CLS MJ Troche correspondiente a mes de mayo/junio /2019	2019-07-01	4000000	O.P N° 61/2019	405		\N
201	47	61/19	3	001-001-0001420	144	Jornales  - Isabel Portillo Ortigoza - Mucama - Servicio de Clínica Medica - Febrero, Marzo 2019	2019-05-06	1300000	OPN° 827/19	401		\N
202	47	61/19	3	001-001-0001421	144	Jornales  - José Domingo Maidana Pereira - Servicios Generales - Peón de Patio - Febrero, Marzo 2019	2019-05-06	1900000	OPN°828/2019	401		\N
203	47	61/19	3	001-001-0001422	144	Jornales  - Liz Maricel Duarte - administrativo - Farmacia Interna Hospitalaria - Febrero, Marzo 2019	2019-05-06	1573333	OPN° 830/2019	401		\N
204	71	168/19	1	001_001_0000277	145	Honorarios profesionales_ Lic Blanca P Gonzalez Veron Contadora CLS MJTroche Correspondiente al mes de mayo/junio/2019	2019-07-01	600000	O.P.N° 60/2019	405		\N
205	71	168/19	1	011-00-0025800	221	Transporte	2019-06-25	25000	O.P N° 56/2019	405		\N
206	71	168/19	1	001-001-0000226	242	Mantenimiento y reparacion menores de edificios locales 	2019-07-02	405000	O.P N° 63/2019	405		\N
207	47	61/19	3	001-001-0001428	144	Jornales  - Julio Portillo Ortellado - Tec en Planta Tratadora de Agua - Hemodiálisis - Febrero, Marzo 2019	2019-05-06	3000000	OPN° 829/2019	401		\N
208	71	168/19	1	001-00-0000245	244	Mantenimento y reparacion nenores de vehiculos 	2019-07-11	300000	O.PN° 65/2019	405		\N
209	71	168/19	3	001-001-0000468	245	Servicio de limpieza ,aseo y fumigacion	2019-07-01	150000	O.P N° 62/2019	405		\N
192	47	61/19	3	001-001-0001418	144	Jornales  - Herminio Villaverde Mora - Servicios Generales - Peón de Patio - Febrero, Marzo 2019	2019-05-06	1093327	OPN° 825/19	401		\N
212	71	168/19	1	001-001-001376	323	Confecciones Textiles	2019-07-15	620000	O.P N° 69/2019	405		\N
213	71	168/19	1	001-001-0018474	342	Utiles de escritorio, oficina y enseñanzas	2019-07-11	70000	O.P N°68/2019	405		\N
214	47	61/19	3	001-001-0001423	144	Jornales  - Lucia Villalba de Giménez - Mucama Baños Públicos - Febrero, Marzo 2019	2019-05-06	2000000	OPN°831/2019	401		\N
215	71	168/19	1	001-001-0064420	343	Utiles y materiales electricos 	2019-07-06	480000	O.P N° 66/2019	405		\N
216	71	168/19	1	001-001-0001651	346	Repuestos y accesorios menores	2019-07-18	1350000	O.P N° 70/2019	405		\N
217	71	168/19	1	001-001-0064364	392	Cubiertas y camaras de aire 	2019-07-03	1400000	O.P.N °64/2019	405		\N
218	47	61/19	3	001-001-0001424	144	Jornales  - María Carolina Fernández Alarcón - Servicios Generales - Mucama - Febrero, Marzo 2019\n	2019-05-06	1300000	OPN°832/2019	401		\N
219	79	254/19	1	001-001-0058	142	Contratación de personal de salud - Alba Victoria Maidana Encina - Lic en Enfermeria - Servicio de PAI Hospital  Regional - Junio, 2019\n	2019-08-21	1000000	OP Nº 960/2019	401		\N
220	79	254/19	1	001-001-00076	142	Contratación de personal de salud - Claudia Noelia Benítez Escobar - Dra. Odontóloga - Junio, Julio 2019\n	2019-08-21	3000000	OP Nº 961/2019	401		\N
221	71	168/19	1	001-001-0064364	392	Cubiertas y camara de aire	2019-07-11	1400000	O.P N° 67/2019	405		\N
222	47	61/19	3	001-001-0001425	144	Jornales  - Ninfa Graciela Sánchez Odecino - Auxiliar Administrativo - RR.HH - Febrero, Marzo 2019	2019-05-06	1400000	OPN° 833/2019	401		\N
223	79	254/19	1	001-001-000248	142	Contratación de personal de salud - Diana Raquel Aquino Lovatti - Dra. Odontóloga - Junio, Julio 2019\n	2019-08-21	3600000	OP Nº 962/2019	401		\N
224	47	61/19	3	001-001-0001426	144	Jornales  - Raquel rodas Benítez - Servicios Generales - Febrero, Marzo 2019	2019-05-06	1600000	OPN° 834/2019	401		\N
225	79	254/19	1	001-001-0001464	142	Contratación de personal de salud - José De Jesús  Godoy Dávalos - Tec. Instrumentista - Quirófano - Junio, Julio 2019\n	2019-08-21	2000000	OP Nº 963/2019	401		\N
226	79	254/19	1	001-001-0000220	142	Contratación de personal de salud - María Graciela Benítez Matto - Fisioterapeuta - Kinesiólogo - Clínica Médica - Junio, Julio 2019\n	2019-08-21	1590000	OP Nº 964/19	401		\N
227	47	61/19	3	001-001-0001427	144	Jornales  - Wilma Raquel Villalba Báez  - Administradora CLS Vca. - Febrero, Marzo 2019	2019-05-06	3000000	OPN°835/2019	401		\N
228	79	254/19	1	001-001-0000191	142	Contratación de personal de salud - Noelia Noemí  Reyes - Lic. Enfermería servicio Pediatría - Junio, Julio 2019\n	2019-08-21	2300000	OP Nº 965/2019	401		\N
229	79	254/19	1	001-001-0001465	142	Contratación de personal de salud - Norma Estela Valdez de Benítez - Auxiliar de Enfermería - Servicio de Odontología - Junio, Julio 2019\n	2019-08-21	1900000	OP Nº 966/2019	401		\N
230	77	168/19	1	001-001-000228	142	Contratación de personal de salud - Odontologo  Arthur Adelio Rolón Barrios - mes de junio y julio del 2019\t\n	2019-08-05	5500000	O.P N° 475	409		\N
231	79	254/19	3	001-001-0001466	142	Contratación de personal de salud - René Arturo Colman Maidana - Auxiliar Técnico - Servicio De Tomografía Hosp. Reg. - Junio, Julio 2019\n	2019-08-21	1900000	OP Nº 967/2019	401		\N
232	77	168/19	3	001-001-0000402	144	Jornales - Jardinero - Cristian Gonzalez  - mes de junio del 2019\t\n	2019-07-09	500000	O.P N° 470	409		\N
233	79	254/19	1	001-001-0000003	142	Contratación de personal de salud - Romina Aquino Álvarez  - Lic. en Enfermería - Quirófano - Junio, Julio 2019\n	2019-08-21	1966667	OP Nº 968/2019	401		\N
235	47	61/19	3	001-001-0001442	144	Jornales  - Ana Vera Cabrera - Asist.. Administrativo - Abril, Mayo 2019	2019-06-14	1573333	OPN° 893/2019	401		\N
236	77	168/19	3	001-001-0000404	144	Jornales - Auxiliar Informática - Gricelda Martinez Gallardo  - mes de junio del 2019\t\n	2019-07-09	925000	O.P N° 472	409		\N
237	77	168/19	1	001-001-0000599	145	Honorarios Profesionales -Contador -Fernando D. Ramos Villasboa  - mes de mayo y junio del 2019\t\n	2019-07-09	2000000	O.P N° 473	409		\N
238	47	61/19	3	001-001-0001443	144	Jornales  - Daniel Rodrigo Bernal Barquinero - Auxiliar Administrativo - RR.HH. - Hosp. Reg. de Vca. - Abril, Mayo 2019	2019-06-14	1966667	OPN°894/2019	401		\N
239	47	61/19	3	001-001-0001444	144	Jornales  - Eugenio Portillo Pereira  - Servicios Generales - Abril, Mayo 2019\n	2019-06-14	1600000	OPN°895/2019	401		\N
241	77	168/19	1	001-001-0045207	323	Confecciones Textiles	2019-07-03	92000	O.P N° 466	409		\N
242	77	168/19	1	001-001-0045207	341	Elementos de limpieza	2019-07-03	25500	O.P N° 466	409		\N
243	77	168/19	1	001-001-045217	341	Elementos de limpieza 	2019-07-03	62500	OP N° 467	409		\N
244	47	61/19	3	001-001-0001445	144	Jornales  - Ilda Mariela  Flor de Vega - Mucama - Servicio de Ropería - Confecciones  - Abril, Mayo 2019	2019-06-14	1573333	OPN° 896/2019	401		\N
245	47	61/19	3	001-001-0001446	144	Jornales  - Isabel Portillo Ortigoza - Mucama - Servicio de Clínica Medica - Abril, Mayo 2019	2019-06-14	1600000	OPN°897/2019	401		\N
246	47	61/19	3	001-001-0001447	144	Jornales  - José Domingo Maidana Pereira - Servicios Generales - Peón de Patio - Abril, Mayo 2019	2019-06-14	1966667	OPN°898/19	401		\N
240	77	168/19	1	001-001-000132	243	Mantenimiento y reparaciones menores de maquinarias, equipos y muebles de oficina.\t\n	2019-07-20	200000	O.P N° 474	409		\N
247	47	61/19	3	001-001-0001448	144	Jornales  - Julio Portillo Ortellado - Tec en Planta Tratadora de Agua - Hemodiálisis - Abril, Mayo 2019\n	2019-06-14	3000000	OPN°899/2019	401		\N
248	47	61/19	3	001-001-0001449	144	Jornales  - Liz Maricel Duarte - administrativo - Farmacia Interna Hospitalaria - Abril, Mayo 2019	2019-06-14	1600000	OPN°900/2019	401		\N
249	47	61/19	3	001-001-0001450	144	Jornales  - Lucia Villalba de Giménez - Mucama Baños Públicos - Abril, Mayo 2019	2019-06-14	2000000	OPN°901	401		\N
250	79	254/19	3	001-001-0001476	142	Contratación de personal de salud - Alicia Martínez Iglesia - Auxiliar de Enfermería - Junio,  2019\n	2019-08-21	1000000	OP Nº 979/2019	401		\N
251	79	254/19	1	001-001-00109	142	Contratación de personal de salud - Carlos Pedrozo - Lic. en Enfermería - Servicio de Urgencias - Junio,  2019\n	2019-08-21	1000000	OP Nº 980/2019	401		\N
252	79	254/19	1	001-001-0000076	142	Contratación de personal de salud - Celsa Gonzalez Fariña - Lic. en Enfermería - Neonatologia - Junio,  2019\n	2019-08-21	1000000	OP Nº 981/2019	401		\N
253	79	254/19	1	001-001-0000004	142	Contratación de personal de salud - María Liz Martínez Verón - Licenciada en  Enfermería - Urgencias Adultos - Junio, Julio 2019\n	2019-08-21	1900001	OP Nº 982/2019	401		\N
254	79	254/19	1	001-001-0000503	142	Contratación de personal de salud - Miguel Ángel García Martínez - Técnico Servicio de laboratorio de TBC - Junio,  2019\n	2019-08-21	560000	OP Nº 983/2019	401		\N
255	79	254/19	1	001-001-0059	142	Contratación de personal de salud - Alba Victoria Maidana Encina - Lic en Enfermeria - Servicio de PAI Hospital  Regional - Julio, 2019\n	2019-08-26	1000000	OP Nº 988/2019	401		\N
256	79	254/19	3	001-001-0001467	144	Jornales  - Daniel Rodrigo Bernal Barquinero - Auxiliar Administrativo - RR.HH. - Hosp. Reg. de Vca. - Junio, Julio 2019\n	2019-08-21	1933334	OP Nº 969/2019	401		\N
257	79	254/19	3	001-001-0001468	144	Jornales  - Ilda Mariela  Flor de Vega - Mucama - Servicio de Ropería - Confecciones  - Junio, Julio 2019\n	2019-08-21	1800000	OP Nº 970/2019	401		\N
258	79	254/19	3	001-001-0001469	144	Jornales  - Isabel Portillo Ortigoza - Mucama - Servicio de Clínica Medica - Junio, Julio 2019\n	2019-08-21	1800000	OP Nº 971/2019	401		\N
259	79	254/19	3	001-001-0001470	144	Jornales  - José Domingo Maidana Pereira - Servicios Generales - Peón de Patio - Junio, Julio 2019\n	2019-08-21	1966667	OP Nº 972/2019	401		\N
260	79	254/19	3	001-001-0001471	144	Jornales  - Julio Portillo Ortellado - Tec en Planta Tratadora de Agua - Hemodiálisis - Junio, Julio 2019\n	2019-08-21	2950000	OP Nº 973/2019	401		\N
261	79	254/19	3	001-001-0001472	144	Jornales  - Liz Maricel Duarte - administrativo - Farmacia Interna Hospitalaria - Junio, Julio 2019\n	2019-08-21	1546666	OP Nº 974/2019	401		\N
262	79	254/19	3	001-001-0001473	144	Jornales  - Lucia Villalba de Giménez - Mucama Baños Públicos - Junio, Julio 2019\n	2019-08-21	2000000	OP Nº 975/2019	401		\N
263	79	254/19	3	001-001-0001474	144	Jornales  - María Carolina Fernández Alarcón - Servicios Generales - Mucama - Junio, Julio 2019\n	2019-08-21	1766667	OP Nº 976/2019	401		\N
264	79	254/19	3	001-001-0001475	144	Jornales  - Wilma Raquel Villalba Báez  - Administradora CLS Vca. - Junio, Julio 2019\n	2019-08-21	3000000	OP Nº 977/2019	401		\N
265	79	254/19	3	001-001-0001477	144	Jornales  - Ana Vera Cabrera - Asist.. Administrativo - Junio,  2019\n	2019-08-21	800000	OP Nº 984/2019	401		\N
266	79	254/19	3	001-001-0001478	144	Jornales  - Eugenio Portillo Pereira  - Servicios Generales - Junio,  2019\n	2019-08-21	800000	OP Nº 985/2019	401		\N
267	79	254/19	3	001-001-1479	144	Jornales  - Ninfa Graciela Sánchez Odecino - Auxiliar Administrativo - RR.HH - Junio,  2019\n	2019-08-21	800000	OP Nº 986/2019	401		\N
268	79	254/19	3	001-001-0001480	144	Jornales  - Raquel Rodas Benítez - Servicios Generales - Junio,  2019\n	2019-08-21	800000	OP Nº 987/2019	401		\N
269	79	254/19	3	001-001-0001481	144	 Jornales  - Ninfa Graciela Sánchez Odecino - Auxiliar Administrativo - RR.HH - Julio, 2019\n	2019-09-04	773333	OP Nº 999/2019	401		\N
270	79	254/19	1	001-003-000245	145	Honorarios profesionales - José Manuel Peralta Ayala - Contador CLS Vca. - Junio, Julio 2019\n	2019-08-21	4400000	OP Nº 978/2019	401		\N
271	79	254/19	1	001-001-0026394	221	Transporte	2019-07-23	32000	OP Nº 950/2019	401		\N
272	79	254/19	1	011-001-0027527	221	Transporte	2019-09-24	25000	OP Nº 1004/2019	401		\N
273	79	254/19	1	001-001-0000758	242	Mantenimiento y reparaciones menores de edificios y locales	2019-07-11	1300000	OP Nº 946/2019	401		\N
274	79	254/19	1	001-001-0000364	243	Mantenimientos y reparaciones menores de maquinarias, equipos y muebles de oficina\n	2019-06-27	120000	OP Nº 941/2019	401		\N
275	79	254/19	1	001-001-000958	243	Mantenimientos y reparaciones menores de maquinarias, equipos y muebles de oficina	2019-08-21	540000	OP Nº 956/2019	401		\N
276	79	254/19	1	001-001-001742	244	Mantenimientos y reparaciones menores de vehículos\n	2019-08-21	120000	OP Nº 957/2019	401		\N
277	79	254/19	1	001-002-0000005	261	De informática y sistemas computarizados	2019-09-25	1500000	OP Nº 1005/2019	401		\N
234	77	168/19	3	001-001-0000403	144	Jornales - Limpiadora - Antonia Vera Bogado  - mes de junio del 2019\t\n	2019-07-09	500000	O.P N° 471	409		\N
278	79	254/19	1	001-001-0000221	321	Hilados y telas	2019-06-28	114500	OP Nº 942/2019	401		\N
279	79	254/19	1	001-001-0000612	331	Papel de escritorio y cartón	2019-07-11	114000	OP Nº 949/2019	401		\N
280	77	168/19	1	001-001-0045207	344	Utensilios de cocina y comedor	2019-07-03	177000	OP N° 466	409		\N
281	79	254/19	1	001-001-000958	331	Papel de escritorio y cartón\n	2019-08-21	500000	OP Nº 956/2019	401		\N
282	79	254/19	1	001-001-0055149	331	Papel de escritorio y cartón	2019-09-21	46000	OP Nº 1003/2019	401		\N
283	79	254/19	1	001-001-0052195	334	Productos de papel y cartón	2019-06-21	35000	OP Nº 940/2019	401		\N
284	79	254/19	1	001-001-0055149	334	Productos de papel y cartón	2019-09-21	9900	OP Nº 1003/2019	401		\N
285	77	168/19	1	001-014-0012649	352	Productos farmaceuticos y medicinales	2019-07-04	2706464	OP N° 469	409		\N
286	79	254/19	1	001-002-0000808	341	ELEMENTOS DE LIMPIEZA	2019-08-16	580000	OP Nº 954/2019	401		\N
287	47	61/19	3	001-001-0001451	144	Jornales  - María Carolina Fernández Alarcón - Servicios Generales - Mucama - Abril, Mayo 2019	2019-06-14	1600000	OPN° 902/2019	401		\N
288	79	254/19	1	001-001-000958	342	Útiles de escritorio, oficina y enseñanza	2019-08-21	410000	OP Nº 956/2019	401		\N
289	77	168/19	1	001-014-0012728	352	Productos farmaceuticos y medicinales	2019-07-05	193536	OP N° 469	409		\N
290	77	168/19	1	001-001-0002675	355	Tintas, pinturas y colorantes	2019-06-27	25000		409	OP N°465	\N
291	47	61/19	3	001-001-0001452	144	Jornales  - Ninfa Graciela Sánchez Odecino - Auxiliar Administrativo - RR.HH - Abril, Mayo 2019	2019-06-14	1600000	OPN°903/2019	401		\N
292	79	254/19	1	001-001-0000025	342	Útiles de escritorio, oficina y enseñanza	2019-08-21	147000		401		\N
293	77	168/19	1	001-001-0002675	399	Bienes de consumos varios 	2019-06-27	253000	OP N° 465	409		\N
294	47	61/19	3	001-001-0001453	144	Jornales  - Raquel Rodas Benítez - Servicios Generales - Abril, Mayo 2019	2019-06-14	1546666	OPN°904/2019	401		\N
295	79	254/19	1	001-001-0003754	343	Útiles y materiales eléctricos	2019-07-08	45000	OP Nº 948/2019	401		\N
296	77	168/19	1	001-001-008227	535	Equipo de salud y de laboratorio	2019-06-27	700000	OP N° 464	409		\N
297	79	254/19	1	001-001-0020974	343	Útiles y materiales eléctricos	2019-09-04	52500	OP Nº 996/2019	401		\N
298	47	61/19	3	001-001-0001454	144	Jornales  - Wilma Raquel Villalba Báez  - Administradora CLS Vca. - Abril, Mayo 2019	2019-06-14	3000000	OPN°905/2019	401		\N
299	79	254/19	1	001-001-0020975	343	Útiles y materiales eléctricos	2019-01-04	672750	OP Nº 996/2019	401		\N
300	77	168/19	1	001-001-0003054	535	Equipo de salud y laboratorio	2019-07-03	750000	OP N° 468	409		\N
301	79	254/19	1	001-001-0020978	343	Útiles y materiales eléctricos	2019-09-05	196500	OP Nº 997/2019	401		\N
302	79	254/19	1	001-001-0020981	343	Útiles y materiales eléctricos	2019-09-05	319000	OP Nº 998/2019	401		\N
303	79	254/19	1	001-001-0020982	343	Útiles y materiales eléctricos	2019-09-05	59500	OP Nº 998/2019	401		\N
304	79	254/19	1	001-002-0066271	343	Útiles y materiales eléctricos	2019-09-17	151600	OP Nº 1001/2019	401		\N
306	79	254/19	1	001-001-0055149	343	Útiles y materiales eléctricos	2019-09-21	83000	OP Nº 1003/2019	401		\N
307	79	254/19	1	001-001-0020977	343	Útiles y materiales eléctricos	2019-09-04	131500	OP Nº 997/2019	401		\N
308	79	254/19	1	001-001-55136	346	Repuesos y Accesorios menores	2019-08-02	27000	OP Nº 952/2019	401		\N
309	79	254/19	1	001-002-0064344	347	Elemento y útiles diversos	2019-07-23	20000	OP Nº 951/2019	401		\N
310	79	254/19	1	001-001-0020974	355	Tintas, pinturas y colorantes	2019-09-04	40000	OP Nº 996/2019	401		\N
311	97	168/19	1	001-001-0000104	142	Contratación de personal de salud - Andrea Gisselle Giménez  - Odontóloga de Consultorio - Julio, 2019\n	2019-08-01	2000000	OP N° 898/2019	410		\N
312	79	254/19	1	001-001-0020982	355	Tintas, pinturas y colorantes	2019-09-05	20000	OP Nº 998/2019	401		\N
313	97	168/19	1	001-001-0000232	142	Contratación de personal de salud - Cristian Fernández Núñez. - Servicio de Lic. Enfermería - Julio, 2019\n	2019-07-31	1200000	OP N° 896/2019	410		\N
314	97	168/19	1	001-001-0000003	142	Contratación de personal de salud - Fatima Lorena Martinez Vazquez - Lic. Enfermería - Julio, 2019\n	2019-07-31	1200000	OP N° 898/2019	410		\N
315	79	254/19	2	001-001-0001646	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-08-06	900000	OP Nº 953/2019  Recibo de Dinero 0001 / 17-09-19	401		\N
316	97	168/19	3	001-001-0000226	144	Jornales  - Carmelo Giménez Rivero - Servicio de Sereno - Julio, 2019\n	2019-09-23	1100000	898/2019	410		\N
317	79	254/19	1	001-001-0007284	391	Articulos de caucho	2019-08-28	86000	OP Nº 990/2019	401		\N
318	97	168/19	1	001-001-0000244	145	Honorarios profesionales - José Manuel Peralta - Contador CLS NT - Julio, 2019\n	2019-08-21	1300000	OP N° 899/2019	410		\N
319	79	254/19	1	001-001-0020977	394	Herramientas menores	2019-09-04	25500	OP Nº 997/2019	401		\N
320	97	168/19	1	001-002-000004	261	De informática y sistemas computarizados\n	2019-09-02	1500000	OP N° 905/2019	410		\N
321	79	254/19	1	001-001-0020975	397	Productos e insumos metálicos	2019-09-04	23000	OP Nº 996/2019	401		\N
322	79	254/19	1	001-001-0020981	397	Productos e insumos metálicos	2019-09-05	79000	OP Nº 998/2019	401		\N
323	79	254/19	1	001-001-0036380	397	Productos e isnumos metálicos	2019-09-19	658000	OP Nº 1002/2019	401		\N
324	79	254/19	1	001-001-0020977	397	Productos e insumos metálicos	2019-09-04	16000	OP Nº 997/2019	401		\N
325	79	254/19	1	001-001-0003419	398	Productos e insumos no metálicos	2019-07-11	1400000	OP Nº 945/2019	401		\N
326	79	254/19	1	001-001-0005837	398	Producotos e insumos no metalicos	2019-08-27	330000	OP Nº 995/2019	401		\N
327	79	254/19	1	002-001-0028858	399	Bienes de consumos varios	2019-07-01	20000	OP Nº 947/2019	401		\N
328	79	254/19	1	001-001-0000062	399	Bienes de consumos varios	2019-08-14	10000	OP Nº 955/2019	401		\N
329	79	254/19	1	001-001-0020974	399	Bienes de consumos varios	2019-09-04	229500	OP Nº 996/2019	401		\N
330	79	254/19	1	001-001-0020975	399	Bienes de consumos varios	2019-09-04	23000	OP Nº 996/2019	401		\N
331	79	254/19	1	001-001-0020977	399	Bienes de consumos varios	2019-09-04	91000	OP Nº 997/2019	401		\N
332	79	254/19	1	001-001-0020978	399	Bienes de consumos varios	2019-09-05	26400	OP Nº 997/2019	401		\N
333	47	61/19	1	001-001-0000234	145	Honorarios profesionales - José Manuel Peralta Ayala - Contador CLS Vca. - Febrero, Marzo 2019	2019-05-06	4400000	OPN°836/19	401		\N
334	79	254/19	1	001-001-0020983	399	Bienes de consumos varios	2019-09-05	129500	OP Nº 997/2019	401		\N
335	79	254/19	1	001-001-0020981	399	Bienes de consumos varios	2019-09-05	25000	OP Nº 998/2019	401		\N
336	47	61/19	1	001-003-0000238	145	Honorarios profesionales - José Manuel Peralta Ayala - Contador CLS Vca. - Abril, Mayo 2019	2019-06-14	4400000	OPN°906/19	401		\N
337	79	254/19	1	001-001-0020982	399	Bienes de consumos varios	2019-09-05	85100	OP Nº 998/2019	401		\N
338	79	254/19	1	001-001-23688	399	Bienes de consumos varios	2019-09-10	250500	OP Nº 1000/2019	401		\N
339	79	254/19	1	001-002-0066271	399	Bienes de consumos varios	2019-09-17	5520	OP Nº 1001/2019	401		\N
340	47	61/19	1	001-001-0275	242	Mantenimiento y reparaciones menores de edificios y locales	2019-05-20	400000	OP N°864/2019	401		\N
343	97	168/19	1	001-001-0053813	331	Papel de escritorio y carton	2019-08-07	125000	OP N° 902/2019	410		\N
344	47	61/19	1	001-001-000929	243	Mantenimientos y reparaciones menores de maquinarias, equipos y muebles de oficina	2019-05-07	440000	OP N°800/2019	401		\N
348	97	168/19	1	001-001-00002924	342	Utiles de escritorio y oficina	2019-08-02	255000	900/2019	410		\N
349	47	61/19	1	001-001-0000940	311	Alimentos para personas	2019-06-25	250000	OP N°915/2019	401		\N
350	47	61/19	1	001-001-0020011	322	Prendas de vestir	2019-05-07	7000	OP N° 847/2019	401		\N
351	47	61/19	1	001-001-0020017	325	Cueros, cauchos y gomas	2019-05-08	37000	OP N°848/2019	401		\N
352	47	61/19	1	001-009-0041068	331	Papel de escritorio y cartón	2019-05-07	265500	OP N°845/2019	401		\N
353	47	61/19	1	001-001-0050605	331	Papel de escritorio y cartón	2019-05-13	745500	OP N°851/2019 	401		\N
354	47	61/19	1	001-001-0050606	331	Papel de escritorio y cartón	2019-05-13	66000	OP N° 851/2019	401		\N
355	97	168/19	1	001-001-0018802	342	Utiles de escritorio,oficina y enseñanza	2019-08-07	95000	OP N° 903/2019	410		\N
356	80	254/19	1	001-001-00006	142	C.P.S.Lizza Romina Barrientos Aguirre**Enfermera de Guardia  Corresponde a MAYO Y JUNIO 2019\n	2019-08-05	1600000	OP Nº 291/19	402		\N
357	80	254/19	3	001-001-0001160	142	C.P.S.Laura Betania Acosta Balsaldua**Tec. En Enfermería  Corresponde a MAYO Y JUNIO 2019	2019-08-05	1600000	OP Nº 292/2019	402		\N
358	97	168/19	1	001-001-0000073	343	Utiles y materiales electricos 	2019-07-30	270000	OP N° 891/2019	410		\N
359	47	61/19	1	001-001-0052329	331	Papel de escritorio y cartón	2019-06-26	1386000	OP N° 916/2019	401		\N
360	80	254/19	1	001-001-0006	142	C.P.S.María Asunción Marin Acosta**Enfermera de Guardia  Corresponde a MAYO Y JUNIO 2019\n	2019-08-05	1600000	OP Nº 293/2019	402		\N
361	97	168/19	1	001-001-0003069	351	Compustos quimicos	2019-07-09	250000	OP N°890/2019	410		\N
362	80	254/19	3	001-001-0001161	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a MAYO 2019	2019-08-05	1800000	OP Nº 294/2019	402		\N
363	80	254/19	3	001-001-0001162	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a JUNIO 2019	2019-08-05	1800000	OP Nº 295/2019	402		\N
364	97	168/19	1	001-001-0000380	352	Productos farmaceuticos y medicinales	2019-07-30	374000	OP N° 894/2019	410		\N
365	80	254/19	3	001-001-0001163	144	JORNALES.Ramón Antonio Chamorro Acosta**Encargado de Informática  Corresponde a MAYO Y JUNIO  2019	2019-08-05	2000000	OP Nº 296/2019	402		\N
366	97	168/19	1	001-001-0000381	358	Útiles y materiales médico-quirúrgicos y de laboratorio\n	2019-07-30	376000	OP N° 901/2019	410		\N
367	80	254/19	3	001-001-0001164	144	JORNALES.Fernando Ramón Galeano Villalba**Encargado de Farmacia  Corresponde a  MAYO Y JUNIO 2019	2019-08-05	1500000	OP Nº 297/2019	402		\N
368	80	254/19	3	001-001-0001165	144	JORNALES.Ramona Analia Aguirre Garay**Admisión  Corresponde a MAYO Y JUNIO   2019	2019-08-05	1278000	OP Nº 298/2019	402		\N
369	97	168/19	1	001-001-008408	358	Útiles y materiales médico-quirúrgicos y de laboratorio\n	2019-08-06	80000	OP N° 901/2019	410		\N
370	80	254/19	3	001-001-0001166	144	JORNALES.Domitila López Pana**Limpiadora  Corresponde a MAYO Y JUNIO  2019	2019-08-05	1200000	OP Nº 299/2019	402		\N
371	80	254/19	1	001-001-0000106	145	HONORARIOS PROFESIONALES.Cinthia Mabel Venialgo Morel**Administrador- Contador  Corresponde a MAYO Y JUNIO 2019	2019-08-05	2600000	OP Nº 300/2019	402		\N
372	97	168/19	1	001-001-0000512	359	Productos e instrumentales químicos y medicinales varios\n	2019-09-12	325000	OP N° 606/2019	410		\N
373	80	254/19	1	001-001-0810	341	Elementos de Limpieza	2019-08-08	22000	OP Nº 301/2019	402		\N
374	97	168/19	1	001-001-0000212	393	Estructuras metalicas acabadas	2019-07-30	1100000	OP N° 893/2019	410		\N
375	82	254/19	1	001-001-0334	142	C.P.S-HONORINA GONZALEZ DE ROJAS**ENF. DE GUARDIA  Corresponde a  JULIO-2019	2019-08-02	1000000	OP Nº 1130/2019	404		\N
376	97	168/19	1	001-001-008408	535	Equipo de salud  y de laboratorio	2019-08-06	600000	OP N°   901/2019	410		\N
377	82	254/19	1	001-001-0004	142	C.P.S-JOANNA RAMONA ORTIGOZA**ENF. DE GUARDIA  Corresponde a   JULIO - 2019	2019-08-02	700000	OP Nº 1131/2019	404		\N
378	97	168/19	1	001-001-0000106	535	Equipo de salud y de laboratorio\n	2019-08-12	700000	OP N° 904/2019	410		\N
379	82	254/19	1	001-001-0031	142	 C.P.S-CARMEN LETICIA MAIDANA VELAZQUEZ**ENF. DE GUARDIA  Corresponde a   JULIO - 2019	2019-08-02	600000	OP Nº 1132/2019	404		\N
380	97	168/19	1	001-002-0004911	541	Adquisicion de muebles y enseres	2019-07-30	2150000	OP N° 892/2019	410		\N
381	82	254/19	3	001-001-522	142	C.P.S-OLGA GIMENEZ CABALLERO **AUXILIAR. DE GUARDIA  Corresponde a   JULIO - 2019	2019-08-02	600000	OP Nº 1133/2019	404		\N
382	47	61/19	1	001-001-0052329	334	Producto de papel y carton	2019-06-26	175000	OP N° 916/2019	401		\N
383	82	254/19	1	001-001-0005	142	C.P.S-JOANNA RAMONA ORTIGOZA**ENF. DE GUARDIA  Corresponde a  AGOSTO - 2018	2019-09-04	700000	OP Nº 1139/2019	404		\N
384	82	254/19	1	001-001-0032	142	C.P.S-CARMEN LETICIA MAIDANA VELAZQUEZ**ENF. DE GUARDIA  Corresponde a  AGOSTO - 2018	2019-09-04	600000	OP Nº 1140/2019	404		\N
385	47	61/19	1	002-001-0001555	341	Elementos de limpieza	2019-05-31	105500	OP N° 875/2019	401		\N
386	82	254/19	3	001-001-0523	144	JORNALES -MARIA ANTONIA ORTIZ**COCINERA  Corresponde a   JULIO - 2019	2019-08-02	500000	OP Nº 1134/2019	404		\N
387	82	254/19	3	001-001-0523	144	JORNALES -LOURDES MARGARITA ACHAR**  Corresponde a   JULIO - 2019	2019-08-02	500000	OP Nº 1134/2019	404		\N
388	82	254/19	3	001-001-0525	144	JORNALES -LIZ MABEL COLINA**LIMPIADORA  Corresponde a   JULIO - 2019	2019-08-02	900000	OP Nº 1136/2019	404		\N
389	47	61/19	1	001-001-000929	342	Utiles de escritorio, oficina y enseñanza	2019-05-07	560000	OP N° 800/2019	401		\N
390	82	254/19	1	001-001-0526	144	JORNALES -ELSA ALEJANDRA ESCOBAR MACIEL**SECRETARIA ADMINISTRATIVA  Corresponde a   JULIO - 2019	2019-08-02	695654	OP Nº 1137/2019	404		\N
391	82	254/19	3	001-001-0521	144	JORNALES -LIDIA RIVAS ESPINOLA**ADMINISTRADORA  Corresponde a   JULIO - 2019	2019-08-02	1500000	OP Nº 1138/2019	404		\N
392	47	61/19	1	001-001-0050605	342	Utiles de escritorio, oficina y enseñanza	2019-05-13	354000	OP N°851/2019	401		\N
393	82	254/19	3	001-001-0527	144	JORNALES -LIDIA RIVAS ESPINOLA**ADMINISTRADORA  Corresponde a  AGOSTO - 2018	2019-09-04	1500000	OP Nº 1141/2019	404		\N
394	82	254/19	3	001-001-0528	144	JORNALES -MARIA ANTONIA ORTIZ**COCINERA  Corresponde a  AGOSTO - 2019	2019-09-04	500000	OP Nº 1142/2019	404		\N
395	47	61/19	1	001-001-0050606	342	Útiles de escritorio, oficina y enseñanza	2019-05-13	177750	OP N° 851/2019	401		\N
396	82	254/19	3	001-001-0529	144	JORNALES -LOURDES MARGARITA ACHAR**  Corresponde a  AGOSTO - 2019	2019-09-04	500000	OP Nº 1143/2019	404		\N
397	82	254/19	3	001-001-0530	144	JORNALES -LIZ MABEL COLINA**LIMPIADORA  Corresponde a  AGOSTO - 2019	2019-09-04	900000	OP Nº 1144/2019	404		\N
398	47	61/19	1	001-001-0001115	342	Útiles de escritorio, oficina y enseñanza	2019-05-23	420000	OP N°865/2019	401		\N
399	82	254/19	3	001-001-0531	144	JORNALES -ELSA ALEJANDRA ESCOBAR MACIEL**SECRETARIA ADMINISTRATIVA  Corresponde a  AGOSTO - 2019	2019-09-04	636368	OP Nº 1145/2019	404		\N
400	82	254/19	1	001-001-0216	242	Mantenimiento y reparaciones menores de edificios y locales	2019-09-16	667978	OP Nº 1148/2019	404		\N
401	82	254/19	1	001-001-0754	341	Elementos de limpieza	2019-09-16	1000000	OP Nº 1146/2019	404		\N
402	82	254/19	1	001-002-1624	343	Útiles y materiales eléctricos	2019-09-16	1000000	OP Nº 1147/2019	404		\N
403	47	61/19	1	001-001-0002861	342	Útiles de escritorio, oficina y enseñanza	2019-06-26	300000	OP N° 913/2019	401		\N
404	47	61/19	1	001-001-0052329	342	Útiles de escritorio, oficina y enseñanza	2019-06-26	30000	OP N° 916/2019	401		\N
405	47	61/19	1	001-001-0020011	343	Útiles y materiales eléctricos	2019-05-07	204500	O.P. N° 847/2019	401		\N
406	84	254/19	3	001-001-0001201	142	Contratación de personal de salud - Carlos Darío Peralta - Técnico en Laboratorio - Junio, Julio 2019	2019-10-23	1525000	OP Nº 2350/2019	406		\N
407	84	254/19	1	001-001-0000158	142	Contratación de personal de salud - Lic. Claudio Rubén Duarte Palma - Lic. En Enfermería - Guardia Urgencias  - Junio, Julio 2019	2019-08-19	1600000	OP Nº 2351/19	406		\N
408	47	61/19	1	001-001-0020013	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-07	198000	OP N° 847/2019	401		\N
409	84	254/19	3	001-001-0001202	142	Contratación de personal de salud - Mariela Isabel Resquin Duarte - Auxiliar de enfermería en consultorios - Junio, Julio 2019	2019-10-23	1468334	OP Nº 2352/2019	406		\N
410	84	254/19	3	001-001-001203	142	Contratación de personal de salud - Melissa Rocío Gavilán - Tec. De Enfermería Preparación de pacientes en consultorios - Junio, Julio 2019	2019-10-23	1600000	OP Nº 2353/2019	406		\N
411	84	254/19	3	001-001-0001204	142	Contratación de personal de salud - Mercedes Sachelaridi Figueredo - Auxiliar de Enfermeria - Urgencias Guardia - Junio, Julio 2019	2019-10-23	1600000	OP Nº 2354/2019	406		\N
412	84	254/19	1	001-001-0000128	142	Contratación de personal de salud - Lic. Olga Beatriz Rodríguez Zarate - Lic. En Enfermería. - Junio, Julio 2019	2019-08-19	1600000	OP Nº 2355/2019	406		\N
413	84	254/19	3	001-001-0001205	142	Contratación de personal de salud - Sady Lorena Peralta Mercado - Atención a pacientes en Farmacia Interna Hospitalaria - Junio, Julio 2019	2019-10-23	1571667	OP Nº 2356/2019	406		\N
414	84	254/19	1	001-001-0000026	142	Contratación de personal de salud - Walter Ramón Escobar Britez - Tec. Sup. de Radiología - Guardias Sábados - Junio, Julio 2019	2019-08-19	1600000	OP Nº 2357/2019	406		\N
415	84	254/19	3	001-001-0001206	144	Jornales  - Alba María Zarate Lovera - Auxiliar Administrativo - Carga de Estadisticas - Junio, Julio 2019	2019-10-23	1571667	OP Nº 2358/2019	406		\N
416	47	61/19	1	001-001-0020017	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-08	23000	OP N°848/2019	401		\N
417	47	61/19	1	001-001-0020018	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-09	415500	OP N° 849/2019	401		\N
418	47	61/19	1	001-001-0020019	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-09	21000	OP N°849/2019	401		\N
419	47	61/19	1	001-001-0020019	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-09	40000	OOP N°849/2019	401		\N
420	47	61/19	1	001-002-0062276	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-05-23	64960	OP N°867/2019	401		\N
421	47	61/19	2	001-001-0020366	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-20	213850	RECIBO 1831, FECHA 08/07/2019 OP N° 910/2019	401		\N
423	47	61/19	2	001-001-0020369	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-20	114500	RECIBO 1831 FECHA 08/07/2019 OP N° 910/2019	401		\N
422	47	61/19	2	001-001-0020367	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-20	381500	RECIBO 1831 FECHA 08/07/2019 OP N° 910/2019	401		\N
424	47	61/19	2	001-001-0020370	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-20	648000	RECIBO 1381 FECHA 08/07/2019 O.P. N° 910/2019	401		\N
426	47	61/19	2	001-001-0020378	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-21	202500	RECIBO 1830 FECHA 08/07/2019 O.P. N° 911/2019	401		\N
427	66	168/19	3	001-001-0000346	142	Contratación de personal de salud - María Candelaria Chamorro P. - Atención en Farmacia Interna  - Abril, Mayo 2019\n	2019-06-26	800000	OP N° 636/2019	412		\N
428	66	168/19	1	001-001-0000051	142	Contratación de personal de salud - Romina María Oviedo Silvero - Lic. En Enfermería - Abril, Mayo 2019\n	2019-06-26	1200000	OP N° 637/2019	412		\N
429	47	61/19	2	001-001-0020379	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-21	409500	RECIBO 1830 FECHA 08/07/2019 OP N° 911/2019	401		\N
425	66	168/19	1	001-001-0103	142	Contratación de personal de salud - María A Cabral R. - Lic. En Enfermería - Abril, Mayo 2019\n	2019-06-26	1200000	OP N°635/2019	412		\N
434	66	168/19	1	001-001-0000102	142	Contratación de personal de salud - Rumilda Chamorro Ramírez - Dra. Consultorio Odontología - Abril, Mayo 2019\n	2019-06-26	2400000	OP N° 638/2019	412		\N
435	66	168/19	1	001-001-0057	142	Contratación de personal de salud - Vilma Ramírez Amarilla - Lic. En Enfermería - Abril, Mayo 2019\n	2019-06-26	1200000	OP N° 1200000	412		\N
437	47	61/19	1	001-001-000930	346	REPUESTOS Y ACCESORIOS MENORES	2019-05-03	810000	OP N°800/2019	401		\N
438	47	61/19	1	001-001-51766	346	REPUESTOS Y ACCESORIOS MENORES	2019-05-13	13500	OP N°50/2019	401		\N
439	66	168/19	3	001-001-0000348	144	Jornales  - Faustino Rojas Barrios - Servicios Generales - Abril, Mayo 2019\n\n	2019-06-26	600000	OP N° 641/2019	412		\N
440	47	61/19	1	001-001-0000495	346	REPUESTOS Y ACCESORIOS MENORES	2019-06-03	1075000	OP N°876/2019	401		\N
436	66	168/19	3	001-001-0000347	144	Jornales  - Bernardo González Giménez - Servicios Generales - Abril, Mayo 2019\n	2019-06-26	600000	OP N° 640/2019	412		\N
441	47	61/19	1	001-001-0013274	351	Compuestos Quimicos	2019-05-23	540000	OP N°868/2019	401		\N
442	47	61/19	1	001-001-0005666	352	Productos farmaceuticos y medicinales	2019-06-25	175000	OP N° 914/2019	401		\N
444	47	61/19	1	001-001-0020011	355	Tintas, pinturas y colorantes	2019-05-07	48000	OP N°847/2019	401		\N
443	66	168/19	3	001-001-0000349	144	Jornales  - Leticia Natalia Verdecchia Mora - Asistentente Administrativo, área de Laboratorio y Odontología - Abril, Mayo 2019\n	2019-06-26	800000	OP N° 642/2019	412		\N
445	66	168/19	1	001-003-0000239	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Marzo, Abril, Mayo 2019\n	2019-07-01	3900000	OP N° 643/2019	412		\N
446	47	61/19	2	001-001-0020382	355	TINTAS, PINTURAS Y COLORANTES	2019-06-21	97500	RECIBO 1829 FECHA 08/07/2019 OP N° 912/2019	401		\N
447	66	168/19	1	001-001-0000276	343	Útiles y materiales eléctricos\n	2019-07-16	228000	OP N°650/2019	412		\N
448	47	61/19	2	001-007-0002601	358	ÚTILES Y MATERIALES MEDICOS QUIRURGICOS Y DE LABORATORIO	2019-04-29	448000	RECIBO 4854 FECHA 17/05/2019 OP N°799/2019	401		\N
449	66	168/19	1	001-001-0005929	343	Útiles y materiales eléctricos\n	2019-09-05	91600	OP N° 651/2019	412		\N
450	66	168/19	1	001-001-0000462	343	Útiles y materiales eléctricos\n	2019-09-05	156500	OP N° 653/2019	412		\N
451	66	168/19	1	060-002-0046914	358	Útiles y materiales médico-quirúrgicos y de laboratorio\n	2019-07-05	198000	OP N° 649/2019	412		\N
452	47	61/19	1	001-001-0000487	358	UTILES Y MATERIALES MEDICO QUIRURGICO Y DE LABORATORIO	2019-05-13	1100000	OP N° 853/2019	401		\N
453	66	168/19	1	001-001-0000511	359	Productos e instrumentales químicos y medicinales varios\n	2019-09-12	28900	OP N° 654/2019	412		\N
454	47	61/19	1	001-001-0005666	358	ÚTILES Y MATERIALES MÉDICOS  QUIRÚRGICOS Y DE LABORATORIO 	2019-06-25	103000	OP N° 914/2019	401		\N
455	47	61/19	1	001-001-0000487	359	Productos e instrumentales químicos y medicinales varios	2019-05-13	1125000	OP N° 853/2019	401		\N
456	47	61/19	1	001-001-0000495	359	Productos e instrumentales químicos y medicinales varios	2019-06-03	375000	OP N°876/2019	401		\N
457	47	61/19	1	001-001-0000487	393	ESTRUCTURA METÁLICAS ACABADAS	2019-05-13	150000	OP N° 853/2019	401		\N
458	47	61/19	1	001-001-0020019	394	HERRAMIENTAS MENORES	2019-05-09	5000	OP N°849/2019	401		\N
459	47	61/19	2	001-001-0020385	396	ARTICULO DE PLÁSTICOS	2019-06-21	8000	RECIBO 1829 FECHA 08/07/2019 OP N° 912/2019	401		\N
460	47	61/19	1	001-001-0020014	397	PRODUCTOS E INSUMOS METÁLICOS	2019-05-07	36500	OP N° 846/2019	401		\N
461	47	61/19	1	001-001-0020019	397	PRODUCTOS E INSTRUMENTALES METALICOS	2019-05-09	11000	OP N°849/2019	401		\N
462	47	61/19	2	001-001-0020383	397	PRODUCTOS E INSTRUMENTALES METALICOS	2019-06-21	88000	RECIBO 1829 FECHA 08/07/2019 OP N° 912/2019	401		\N
463	47	61/19	1	001-001-0020014	399	BIENES DE CONSUMO VARIOS	2019-05-07	181500	OP N°846/2019	401		\N
464	47	61/19	1	001-001-0020014	399	BIENES DE CONSUMO VARIOS	2019-05-07	181500	OP N° 846/2019	401		\N
465	47	61/19	1	001-001-001-0020011	399	BIENES DE CONSUMO VARIOS	2019-05-07	61800	OP N°847/2019 	401		\N
466	47	61/19	1	001-001-0020013	399	BIENES DE CONSUMO VARIOS	2019-05-07	120500	OP N° 847/2019	401		\N
467	47	61/19	1	001-001-0020016	399	BIENES DE CONSUMO VARIOS	2019-05-08	94000	OP N° 848/2019	401		\N
468	47	61/19	1	001-001-0020017	399	BIENES DE CONSUMO VARIOS	2019-05-08	103400	OP N° 848/2019	401		\N
469	47	61/19	1	001-001-0020018	399	BIENES DE CONSUMO VARIOS	2019-05-09	19000	OP N° 849/2019	401		\N
470	47	61/19	1	001-001-0020019	399	BIENES DE CONSUMO VARIOS	2019-05-09	59250	OP N° 849/2019	401		\N
471	47	61/19	1	001-001-5270	399	BIENES DE CONSUMO VARIOS	2019-05-13	28500	OP N° 852/2019	401		\N
472	47	61/19	1	001-001-0020366	399	BIENES DE CONSUMO VARIOS	2019-06-20	12000	RECIBO 1831 FECHA 08/07/2019 OP N° 910/2019	401		\N
473	47	61/19	2	001-001-0020378	399	BIENES DE CONSUMO VARIOS	2019-06-21	2000	RECIBO 1830 FECHA 08/07/2019 OP N° 911/2019	401		\N
474	47	61/19	2	001-001-0020381	399	BIENES DE CONSUMO VARIOS	2019-06-21	339000	RECIBO 1829 FECHA 08/07/2019 OP N° 912/2019	401		\N
475	47	61/19	2	001-001-0020385	399	BIENES DE CONSUMO VARIOS	2019-06-21	121500	RECIBO 1829 FECHA 08/07/2019 OP N°912/2019	401		\N
476	47	61/19	2	001-001-0020380	399	BIENES DE CONSUMO VARIOS	2019-06-21	122500	RECIBO 1830 FECHA 08/07/2019 OP N°911/2019	401		\N
477	47	61/19	1	001-002-0004622	541	ADQUISICION DE MUEBLES Y ENSERES	2019-05-23	1270000	OP N°866/2019	401		\N
478	84	254/19	3	001-001-0001207	144	Jornales  - Carmen Martínez de López - Personal de Servicios Generales - Mucama - Junio, Julio 2019	2019-10-23	1571667	OP Nº 2359/2019	406		\N
479	84	254/19	3	001-001-0001208	144	Jornales  - María Isabel Benítez Lobos - Auxiliar Administrativo - Admisión de pacientes. - Junio, Julio 2019	2019-10-23	1575000	OP Nº 2360/2019	406		\N
480	84	254/19	3	001-001-0001209	144	Jornales  - Mónica Raquel Álvarez Giménez - Auxiliar Administrativo - Carga de Estadísticas - Junio, Julio 2019	2019-10-23	1575000	OP Nº 2361/2019	406		\N
481	66	168/19	1	001-001-0046726	541	Adquisiciones de muebles y enceres 	2019-09-05	97000	OP N° 652/2019	412		\N
482	66	168/19	1	001-001-0001230	542	Adquisiciones de equipos de oficina 	2019-06-26	1000000	OP N° 648/2019	412		\N
483	63	168/19	1	001-001-0000151	142	CPS-Teresa Duarte -Lic en enfermeria-mes de mayo/2019\t\n	2019-07-23	1200000	OP N° 311	416		\N
484	63	168/19	1	001-001-0000133	142	CPS-Perla Maidana-Lic en enfermeria-mes de mayo/junio/2019\t\n	2019-07-18	2400000	OP N° 312	416		\N
485	63	168/19	3	001-001-0000510	142	CPS - Aldo Sanchez - Aux. Enfermeria-Mes de mayo/junio/2019\t\n	2019-06-30	2000000	OP N° 313	416		\N
486	63	168/19	3	001-001-0000511	142	CPS - Carlos Medina - Aux. Enfermeria-Mes de mayo/junio2019\t\n	2019-06-30	2000000	OP N°314	416		\N
487	63	168/19	3	001-001-0000512	144	Jornales - Sonia Martinez- Aux. Adminsitrativo-Mes de mayo/junio/2019\t\n	2019-06-30	2000000	OP N° 315	416		\N
488	63	168/19	3	001-001-0000513	144	Jornales - Carmen Pereira- Aux. Administrativo-Mes de mayo/junio/2019\t\n	2019-06-30	2000000	OP N° 316	416		\N
489	63	168/19	3	001-001-0000514	144	Jornales - Estelbina Silvero- Personal de Limpieza -Mes de mayo/junio/2019\t\n	2019-06-30	1200000	OP N° 317	416		\N
490	63	168/19	3	001-001-0000515	144	Jornales - Modesta Dominguez-Personal de Limpieza - Mes de mayo/junio/2019\t\n	2019-06-30	1200000	OP N° 318	416		\N
491	63	168/19	3	001-001-0000516	144	Jornales -Ilda Vera-Personal de limpieza-mes de mayo/junio/2019\t\n	2019-06-30	1200000	OPN °319	416		\N
492	63	168/19	1	001-001-000036	145	Honorarios Profesionales- Sergio Rojas-Administrador-Mes de mayo/jun/2019\t\n	2019-06-30	3600000	OP N°320	416		\N
493	63	168/19	1	001-002-0002141	341	Elementos de Limpieza\t\n	2019-05-06	6200000	OP N°321	416		\N
494	78	168/19	1	001-001-0000018	142	Contratación de personal de salud - Laura Rocio Gimenez Buscio - Lic en Enferméria - Junio, 2019\n	2019-06-30	1500000	OP N° 13/2019	415		\N
495	78	168/19	1	001-001-0000019	142	Contratación de personal de salud - Laura Rocio Gimenez Buscio - Lic en Enferméria - Julio, 2019\n	2019-07-30	1500000	OP N° 16/2019	415		\N
496	78	168/19	3	001-001-0000472	144	Jornales  - Dina Mariel Barrios Araujo - Operadora Inf.  - Junio, 2019\n	2019-06-30	1000000	OP N° 14/2019	415		\N
497	78	168/19	1	001-001-0000473	144	Jornales  - Constancia Benitez - Servicios Generales  - Junio, 2019\n	2019-06-30	500000	OP N° 15/2019	415		\N
498	78	168/19	3	001-001-0000474	144	Jornales  - Dina Mariel Barrios Araujo - Operadora Inf.  - Julio, 2019\n	2019-07-30	1000000	OP N° 17/2019	415		\N
499	78	168/19	3	001-001-0000475	144	Jornales  - Constancia Benitez - Servicios Generales  - Julio, 2019\n	2019-07-30	500000	OP N° 18/2019	415	|	\N
500	78	168/19	1	001-001-0000174	145	Honorarios profesionales - Vanessa M. Ovelar Prieto - Administradora CLS - Junio, 2019\n	2019-06-30	1500000	OP N° 19/2019	415		\N
501	84	254/19	1	001-003-0000246	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Junio, Julio 2019	2019-09-03	2750000	OP Nº 2362/2019	406		\N
502	84	254/19	1	001-001-0000061	242	Mantenimiento y reparaciones menores de edificios y locales	2019-08-19	300000	OP Nº 2368/2019	406		\N
503	78	168/19	1	001-001-0000175	145	Honorarios profesionales - Vanessa M. Ovelar Prieto - Administradora CLS - Julio, 2019\n	2019-07-30	1500000	OP N° 20/2019	415		\N
504	78	168/19	1	001-002-000002	261	De informatica y sistemas computarizados 	2019-08-12	1500000	OP N° 28/2019	415		\N
506	84	254/19	1	001-002-0000006	261	De informática y sistemas computarizados	2019-09-25	1500000	OP Nº 2376/2019	406		\N
507	84	254/19	1	001-001-0054026	331	Papel de escritorio y cartón	2019-08-19	231000	OP Nº 2370/2019	406		\N
509	84	254/19	1	001-001-0007910	341	Elementos de limpieza	2019-08-19	300000	OP Nº 2371/2019	406		\N
505	78	168/19	2	001-001-0051329	268	Servicios de comunicaciones	2019-06-27	160000	OP N° 22/2019	415		\N
652	92	254/19	1	001-001-0002192	399	Bienes de consumo varios\t	2019-09-28	16000	OP Nº 565	414		\N
510	84	254/19	1	001-001-0054026	342	Útiles de escritorio, oficina y enseñanza	2019-08-19	280000	OP Nº 2370/2019	406		\N
511	84	254/19	1	001-001-0008329	343	Útiles y materiales eléctricos	2019-08-19	672000	OP Nº 2373	406		\N
512	84	254/19	1	001-001-0001504	343	Útiles y materiales eléctricos	2019-08-22	120000	OP Nº 2375/2019	406		\N
513	84	254/19	1	001-001-0000517	343	Útiles y materiales eléctricos	2019-10-08	337000	OP Nº 2380/2019	406		\N
514	84	254/19	1	001-001-0008330	355	Tintas, pinturas y colorantes	2019-08-19	396500	OP Nº 2372/2019	406		\N
515	84	254/19	1	001-001-0000516	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-10-04	16500	OP Nº 2378/2019	406		\N
516	84	254/19	1	001-001-0000517	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-10-08	4665	OP Nº 2380/2019	406		\N
517	84	254/19	1	001-001-0000516	359	Productos e instrumentales químicos y medicinales varios	2019-10-04	900000	OP Nº 2378/2019	406		\N
518	84	254/19	1	001-001-0000489	397	Productos e insumos metálicos	2019-10-07	600000	OP Nº 2379/2019	406		\N
519	84	254/19	1	001-001-0000263	398	Productos e insumos no metálicos	2019-08-19	500000	OP Nº 2374/2019	406		\N
520	84	254/19	1	001-001-0000287	398	Productos e insumos no metálicos	2019-10-08	1410000	OP Nº 2377/2019	406		\N
521	84	254/19	1	001-001-0008330	399	Bienes de consumo varios	2019-08-19	24000	OP Nº 2372/2019	406		\N
522	84	254/19	1	001-002-0006111	535	Equipos de salud y de laboratorio	2019-08-19	250000	OP Nº 2369/2019	406		\N
523	85	254/19	1	001-001-0000006	142	Contratación de personal de salud - Esilda González Giménez - Lic Enfermería - Julio y Agosto 2019	2019-09-03	1950000	OP Nº 278/2019	407		\N
524	85	254/19	1	001-001-0000007	142	Contratación de personal de salud - Liz Mabel Barua Aguirre - Lic Enfermeria - Julio y Agosto 2019	2019-09-03	1950000	OP Nº 279/2019	407		\N
508	78	168/19	2	001-001-0052441	268	Servicios de comunicaciones	2019-07-29	160000	OP  N° 22/2019	415		\N
525	85	254/19	3	001-001-0000288	144	Jornales  - Americo Cristaldo Rojas - Servicios Generales - Peón de Patio - Julio y Agosto 2019	2019-09-03	1200000	OP Nº 280/2019	407		\N
526	85	254/19	3	001-001-0000289	144	Jornales  - Ana Fatima González - Servicios Generales - Mucama - Julio y Agosto 2019	2019-09-03	1200000	OP Nº 281/2019	407		\N
527	85	254/19	1	001-001-00008	144	Jornales  - Liz María Azuaga Acuña - Auxiliar Administrativo - Julio y Agosto 2019	2019-09-03	1950000	OP Nº 282/2019	407		\N
528	85	254/19	1	001-003-0000247	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Julio y Agosto 2019	2019-09-03	2600000	OP Nº 283/2019	407		\N
529	78	168/19	1	001-001-0053672	334	Productos de papel y carton	2019-08-05	49000	OP N° 23/2019	415		\N
530	78	168/19	1	001-001-0002530	341	Elementos de limpieza	2019-08-12	326000	OP N° 25/2019	415		\N
531	78	168/19	1	001-001-0053672	342	Útiles de escritorio, oficina y enseñanza\n	2019-08-05	246000	OP N° 23/2019	415		\N
532	78	168/19	1	001-001-0000830	342	Útiles de escritorio, oficina y enseñanza\n	2019-08-07	100000	OP N° 24/2019	415		\N
533	78	168/19	1	060-001-0030516	352	Productos farmacéuticos y medicinales\n	2019-08-18	304500	OP N° 21/2019	415		\N
534	78	168/19	1	004-007-0090186	352	Productos farmacéuticos y medicinales\n	2019-08-08	2811150	OP N° 26/2019	415		\N
535	85	254/19	1	001-001-0000017	323	Confecciones textiles	2019-10-01	100000	OP Nº 288/2019	407		\N
538	85	254/19	1	001-001-0002613	343	Útiles y materiales eléctricos	2019-09-26	45000	OP 284/2019	407		\N
539	85	254/19	1	001-001-0006004	352	Productos farmacéuticos y medicinales	2019-09-26	145000	OP Nº 286/2019	407		\N
540	85	254/19	1	001-001-0002613	355	Tintas, pinturas y colorantes	2019-09-26	480000	OP Nº 284/2019	407		\N
541	85	254/19	1	001-001-0000514	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-10-04	3000	OP Nº 290/2019	407		\N
542	85	254/19	1	001-001-0000514	359	Productos e instrumentales químicos y medicinales varios	2019-10-04	900000	OP Nº 290/2019	407		\N
543	85	254/19	1	001-001-000473	398	Productos e insumos no metálicos	2019-09-26	280000	OP Nº 285/2019	407		\N
544	85	254/19	1	001-001-0000475	398	Productos e insumos no metálicos	2019-09-30	40000	OP Nº 287/2019	407		\N
545	85	254/19	1	001-001-0002613	399	Bienes de consumo varios	2019-09-26	38000	OP Nº 284/2019	407		\N
546	85	254/19	1	001-001-0000473	399	Bienes de consumo varios	2019-09-26	10000	OP Nº 285/2019	407	 	\N
547	85	254/19	1	001-001-0000475	399	Bienes de consumo varios	2019-09-30	54000	OP Nº 287/2019	407		\N
548	85	254/19	2	001-002-167519	538	Herramientas, aparatos e instrumentos en general	2019-10-08	2055000	OP Nº 289/2019 Recibo de Dinero Nº 001-001-167519 - 08/10/2019	407		\N
549	65	168/19	1	001-001-0000034	142	Contratación de Personal en Salud/Cayetano Soto Fariña/ Enfermero/Mes Marzo y Abril 2019\t\n	2019-06-03	2200000	OPN°361/2019	413		\N
550	65	168/19	1	001-001-0000082	142	Contratación de Personal de Salud/Franca M. Monges Cañete/Enfermera/Mes  Marzo y Abril 2019\t\n	2019-06-03	2200000	OP N° 362/2019	413		\N
551	98	254/19	1	001-001-00360	142	Contratación Personal de Salud/Isidro Ortiz/ Auxiliar en Enfermeria/Mes Junio y Julio de 2019.\t	2019-08-08	3000000	OP Nº 857/2019	408		\N
552	65	168/19	3	001-001-000310	142	Contratación de Personal de Salud/Fredy Hirán Cristaldo Báez/Técnico en Obstetricia/Mes  Marzo y Abril 2019\t\n	2019-06-03	2000000	OP N° 363/2019	413		\N
553	98	254/19	1	001-002-0000014	142	Contratación Personal de Salud/ Miguel Angel Sanabria Arce/ Enfermero/Mes Junio y Julio de  2019.\t	2019-08-08	2100000	OP Nº 858/2019	408	 	\N
554	65	168/19	1	001-001-0000027	142	Contratación Personal en Salud/Rodolfo Reinaldo Ramirez Denis/Enfermero/Mes  Marzo y Abril 2019\t\n	2019-06-03	2200000	OP N° 364/2019	413		\N
556	65	168/19	1	001-001-000036	142	Contratación de Personal de Salud/Ross Maris Rodas Fariña/Enfermera/Mes Marzo y Abril 2019\t\n	2019-06-03	1000000	OP N°365/2019	413		\N
557	98	254/19	1	001-001-0000116	144	Jornales/Guido Ariel Rotela Rotela/Auxiliar Administrativo/Mes Junio y Julio de  2019.\t	2019-08-08	1200000	OP Nº 860/2019	408		\N
558	65	168/19	1	001-001-000077	142	Contratación de Personal de Salud/Teresa J. Fariña de Cardozo/Enfermera/Mes  Marzo y Abril 2019\t\n	2019-06-03	2200000	OP N° 366/2019	413		\N
560	65	168/19	3	001-001-000311	144	Jornales/Reinaldo Báez González/Limpiador de Patio/Mes  Marzo y Abril 2019\t\n	2019-06-03	1000000	OP N° 367/2019	413		\N
561	98	254/19	1	001-001-0000277	266	Consultoría, Asesoría e Investigaciones.\t	2019-08-08	3200000	OP Nº 862/2019	408		\N
555	98	254/19	3	001-001-000361	144	Jornales/Blanca Duarte Aquino/ Limpiadora - Cocinera/Mes Junio y Julio de  2019.	2019-08-08	2400000	OP Nº 859/2019	408		\N
559	98	254/19	3	001-001-00362	144	Jornales/Virginia  García Vera/ Encargada de Admisión/Mes Junio y Julio de 2019.\t	2019-08-08	3000000	OP Nº 861/2019	408		\N
562	65	168/19	1	001-001-0000269	266	 Consultoría,  Asesoría e Investigaciones.\t\n	2019-06-03	2200000	OP N° 368/2019	413		\N
563	64	168/19	3	001-001-0000355	142	CPS Nancy Basilia Roa Roa Auxiliar Enfermeria MARZO-ABRIL Puesto de Salud. P. Benegas\t\n	2019-06-04	1200000	OP N° 539	414		\N
565	64	168/19	3	001-001-0000357	142	CPS Sandra Lucia Sanchez Almeida Auxiliar Enfermeria MARZO-ABRIL Centro de Salud Yataity\t\n	2016-06-04	1200000	OP N° 541	414		\N
566	64	168/19	3	001-001-0000358	142	CPS Gladys Concepcion Armoa Villaverde Auxiliar Enfermeria MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	1200000	OP N° 542	414		\N
564	64	168/19	3	001-001-0000356	142	CPS Lourdes Natividad Rolon Melgarejo Auxiliar Enfermeria MARZO-ABRIL Puesto de Salud. P. Benegas\t\n	2019-06-04	1200000	OP N° 540	414		\N
568	99	254/19	1	001-001-000229	142	Contratación de personal de salud - Odontologo  Arthur Adelio Rolón Barrios - mes de agosto y septiembre del 2019\t	2019-10-01	5500000	OP Nº 482	409		\N
569	99	254/19	3	001-001-0000405	144	Jornales - Jardinero - Cristian Gonzalez  - mes de julio y agosto del 2019\t	2019-09-02	1000000	OP Nº 477	409		\N
570	99	254/19	3	001-001-0000406	144	Jornales - Limpiadora - Antonia Vera Bogado  - mes de julio y agosto del 2019\t	2019-09-02	1000000	OP Nº 478	409		\N
571	64	168/19	3	001-001-0000359	142	CPS Nestor Villaverde Espinola Auxiliar Enfermeria MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	1200000	OP N° 543	414		\N
572	99	254/19	3	001-001-0000407	144	Jornales - Auxiliar Informática - Gricelda Martinez Gallardo  - mes de julio del 2019\t	2019-09-02	925000	OP Nº 479	409		\N
573	99	254/19	3	001-001-0000408	144	Jornales - Auxiliar Informática - Gricelda Martinez Gallardo  - mes de agosto del 2019\t	2019-09-02	925000	OP  Nº 479	409		\N
574	64	168/19	1	001-003-0000068	142	CPS Gabriela Careaga Arias Bioquimica MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	1000000	OP N° 544	414		\N
575	99	254/19	1	001-001-0000716	145	Honorarios Profesionales -Contador -Fernando D. Ramos Villasboa  - mes de julio y agosto del 2019\t	2019-09-02	2000000	OP Nº 480	409		\N
576	64	168/19	1	001-001-000030	142	CPS Maria Jose Insaurralde Alvarenga Odontologa MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	2000000	OP N° 545	414		\N
577	99	254/19	1	001-014-0016666	352	Productos Farmacéuticos y Medicinales\t	2019-08-28	2667633	OP Nº 476	409		\N
578	99	254/19	1	001-010-0012889	352	Productos Farmacéuticos y Medicinales\t	2019-08-29	332370	OP Nº 476	409		\N
579	99	254/19	1	001-001-0000490	535	Equipos de salud y de laboratorio\t	2019-09-03	300000	OP Nº 481	409		\N
580	88	254/19	1	001-001-0000105	142	Contratación de personal de salud - Andrea Gisselle Giménez  - Odontóloga de Consultorio - Agosto, Septiembre / 2019	2019-10-01	4000000	OP Nº 912/2019	410		\N
581	88	254/19	1	001-001-0000251	142	Contratación de personal de salud - Cristian Fernández Núñez. - Servicio de Lic. Enfermería - Agosto, Septiembre / 2019	2019-10-07	2400000	OP Nº 913/2019	410		\N
582	88	254/19	1	001-001-0000004	142	Contratación de personal de salud - Fatima Lorena Martinez Vazquez - Lic. Enfermería - Agosto, Septiembre / 2019	2019-10-01	2400000	OP Nº 914/2019	410		\N
583	88	254/19	3	001-001-0000228	144	Jornales  - Carmelo Giménez Rivero - Servicio de Sereno - Agosto, Septiembre / 2019	2019-10-01	2080000	OP Nº 915/2019	410		\N
584	88	254/19	1	001-003-0000249	145	Honorarios profesionales - José Manuel Peralta - Contador CLS NT - Agosto, Septiembre / 2019	2019-10-01	2600000	OP Nº 916/2019	410		\N
585	88	254/19	1	001-001-0002912	343	Útiles y materiales eléctricos	2019-10-09	619000	OP Nº 918/2019	410		\N
586	88	254/19	1	001-001-0000403	352	Productos farmacéuticos y medicinales	2019-10-01	741000	OP Nº 917/2019	410		\N
587	88	254/19	1	001-001-0002912	397	Productos e insumos metálicos	2019-10-09	160000	OP Nº 918/2019	410		\N
588	64	168/19	3	001-001-0000360	142	CPS Cynthia Isabel Peralta Prieto Auxiliar Enfermeria MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	1200000	OP N° 546	414		\N
589	89	254/19	1	001-001-000330	142	Contrato Personal de Salud  - Deisi Liliana Leiva- Auxiliar de Enfermeria USF Mbocayaty-  Marzo y Abril 2019\t	2019-08-06	1400000		411		\N
590	64	168/19	3	001-001-0000361	144	JORNALES Gustavo Adolfo Davalos Goiris Limpiador de patio MARZO-ABRIL Centro de Salud Yataity\t\n	2019-06-04	1200000	OP N° 547	414		\N
591	64	168/19	1	001-001-0000258	266	Consultoria, asesoria e investigacion\t\n	2019-06-04	2400000	OP N° 548	414		\N
592	89	254/19	1	001-001-0000106	142	Contrato Personal de Salud . Norma Portillo - Auxiliar de Enfermeria USF Tacuarita-  Marzo y Abril 2019\t	2019-08-06	1400000	OP 000843	411		\N
593	89	254/19	3	001-001-000331	142	Contrato Personal de Salud  -Noelia Rolon- Auxiliar  Enfermeria USF Mbocayaty- Marzo y Abril 2019\t	2019-08-06	1400000	OP 000844	411		\N
595	89	254/19	1	001-001-000079	142	Contrato Personal de Salud - Rossana Vazquez - Auxiliar de Enfermeria  USF Mbocayaty- Marzo y Abril 2019\t	2019-08-06	1400000	OP 000845	411		\N
597	89	254/19	1	001-001-00027	142	Contrato Personal de Salud - Laura Noemi Fernadez  - Auxiliar de Enfermeria  USF Mbocayaty- Marzo y Abril 2019\t	2019-08-06	1400000	OP 000846	411		\N
598	89	254/19	1	001-001-00006	142	Contrato Personal de Salud . Clara Beatriz Roa Barrios - Auxiliar de Enfermeria USF Sta Barbara- Marzo y Abril 2019\t	2019-08-06	1400000	OP 000847	411		\N
599	89	254/19	3	001-001-0000335	144	Jornales - Yenni Fabiola Vega B. - Encarg Departamento de Informatica y Estadistica USF Mbocayaty- Marzo y Abril 2019\t	2019-08-06	1283335	OP 000848	411		\N
600	89	254/19	3	001-001-000332	144	Jornales- Limpiadora- María Hermenegilda Chamorro - USF Mbocayaty - Marzo y Abril 2019\t	2019-08-06	1000000	OP 000849	411		\N
601	89	254/19	3	001-001-000333	144	Jornales- Limpiadora- Delma Jazmin Chamorro Franco - USF Col. Jorge  Naville  Marzo y Abril 2019\t	2019-08-06	1000000	OP 000850	411		\N
602	89	254/19	1	001-001-00108	145	Honorario Profesional - Contador Luis Miguel Sánchez A. En el Consejo Local de Salud de Mbocayaty -Marzo y Abril 2019\t	2019-08-06	2000000	OP 000851	411		\N
603	89	254/19	1	001-001-04670	355	Tintas Pinturas  y Colorantes\t	2019-09-16	1000000	OP 000852	411		\N
604	89	254/19	1	001-001-0001653	541	Adquisicion de muebles y enseres\t	2019-09-18	316665	OP 000853	411		\N
605	90	254/19	1	001-001-0000126	142	Contratación de personal de salud - María A Cabral R. - Lic. En Enfermería - Junio, Julio y Agosto 2019	2019-09-25	1800000	OP Nº 670/2019	412		\N
606	90	254/19	3	001-001-0000372	142	Contratación de personal de salud - María Candelaria Chamorro P. - Atención en Farmacia Interna  - Junio y Julio 2019	2019-09-19	1000000	OP Nº 671/2019	412		\N
607	90	254/19	1	001-001-0000052	142	Contratación de personal de salud - Romina María Oviedo Silvero - Lic. En Enfermería - Junio y Julio 2019	2019-09-19	1200000	OP Nº 672/2019	412		\N
608	90	254/19	1	001-001-0000104	142	Contratación de personal de salud - Rumilda Chamorro Ramírez - Dra. Consultorio Odontología - Junio y Julio 2019	2019-09-19	2400000	OP Nº 673/2019	412		\N
609	90	254/19	1	001-001-0000076	142	Contratación de personal de salud - Vilma Ramírez Amarilla - Lic. En Enfermería - Junio, Julio y Agosto 2019	2019-09-19	1800000	OP Nº 674/2019	412		\N
610	90	254/19	3	001-001-0000373	144	Jornales  - Bernardo González Giménez - Servicios Generales - Junio, Julio y Agosto 2019	2019-09-19	900000	OP Nº 675/2019	412		\N
779	75	168/19	1	001-001-04701	333	Productos e Impresiones de Artes Gráficas\t\n	2019-07-08	125000	OP N°803/2019	403		\N
611	90	254/19	3	0001-001-0000374	144	Jornales  - Faustino Rojas Barrios - Servicios Generales - Junio, Julio y Agosto 2019	2019-09-19	900000	OP Nº 676/2019	412		\N
612	90	254/19	3	001-001-0000375	144	Jornales  - Leticia Natalia Verdecchia Mora - Asistentente Administrativo, área de Laboratorio y Odontología - Junio y Julio 2019	2019-09-19	800000	OP Nº 677/2019	412		\N
613	90	254/19	1	001-003-0000248	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Junio y Julio 2019	2019-10-01	2600000	OP Nº 678/2019	412		\N
614	90	254/19	1	001-002-0000007	261	De informática y sistemas computarizados	2019-10-01	1500000	OP Nº 679/2019	412		\N
617	91	254/19	1	001-001-0000001	142	Contratación de Personal en Salud/Cayetano Soto Fariña/ Enfermero/Mes Mayo y Junio 2019\t	2019-08-05	2200000	OP Nº 371/2019	413		\N
618	91	254/19	1	001-001-0000083	142	Contratación de Personal de Salud/Franca M. Monges Cañete/Enfermera/Mes  Mayo y Junio 2019\t	2019-08-02	2200000	OP Nº 372/2019	413		\N
619	91	254/19	3	001-001-00314	142	Contratación de Personal de Salud/Fredy Hirán Cristaldo Báez/Técnico en Obstetricia/Mes  Mayo y Junio 2019	2019-08-02	2000000	OP Nº 373/2019	413		\N
620	91	254/19	1	001-001-000051	142	Contratación Personal en Salud/Rodolfo Reinaldo Ramirez Denis/Enfermero/Mes  Mayo y Junio 2019\t	2019-08-02	2200000	OP Nº 374/2019	413		\N
621	91	254/19	1	001-001-0000051	142	Contratación de Personal de Salud/Ross Maris Rodas Fariña/Enfermera/Mes Mayo y Junio 2019\t	2019-08-02	1000000	OP Nº 375/2019	413		\N
622	62	168/19	1	001-001-000063	142	Contratación de personal de salud - NANCY REBECA MARZAL MERELES - Técnico en Obstetricia - ABRIL - MAYO 2019.-\n	2019-06-05	1600000	OP N| 627/2019	417		\N
623	91	254/19	1	001-001-0000078	142	Contratación de Personal de Salud/Teresa J. Fariña de Cardozo/Enfermera/Mes  Mayo y Junio 2019\t	2019-08-02	2200000	OP Nº 376/2019	413		\N
624	62	168/19	1	001-001-000062	142	Contratación de personal de salud - RAMONA CONCEPCION BRITEZ - ENFERMERA - ABRIL - MAYO 2019.-\n	2019-06-05	1600000	OP N° 628/2019	417		\N
625	91	254/19	3	001-001-00315	144	Jornales/Reinaldo Báez González/Limpiador de Patio/Mes  Mayo y Junio 2019\t	2019-08-02	1000000	OP Nº 377/2019	413		\N
626	62	168/19	1	001-001-00063	142	Contratación de personal de salud - MARIA ELIZABETH DOMINGUEZ - TECNICO EN ENFERMERIA - ABRIL - MAYO 2019.-\n	2019-06-05	1573333	OP N° 629/2019	417		\N
627	62	168/19	1	001-001-00004	142	Contratación de personal de salud - JULIANA DAVALOS DE LOPEZ - ENFERMERA - ABRIL - MAYO 2019.-\n	2019-06-05	1546666	OP N° 630/2019	417		\N
628	91	254/19	1	001-001-00000276	266	 Consultoría,  Asesoría e Investigaciones.\t	2019-08-02	2200000	OP Nº 378/2019	413		\N
629	62	168/19	1	001-001-0004	142	Contratación de personal de salud - LIZ YOHANA TROCHE ESPINOLA - ENFERMERA - ABRIL - MAYO 2019.-\n	2019-06-05	1600000	OP N° 631/2019	417		\N
630	92	254/19	3	001-001-00000362	142	CPS Nancy Basilia Roa Roa Auxiliar Enfermeria MAYO-JUNIO 2019 Puesto de Salud. P. Benegas\t	2019-08-02	1200000	OP Nº 553	414		\N
631	92	254/19	3	001-001-0000363	142	CPS Lourdes Natividad Rolon Melgarejo Auxiliar Enfermeria MAYO-JUNIO 2019 Puesto de Salud. P. Benegas\t	2019-08-02	1200000	OP Nº 554	414		\N
632	62	168/19	1	001-001-00003	142	Contratación de personal de salud - ROSA CONCEPCION CARDOZO - ENFERMERA - ABRIL - MAYO 2019.-\n	2019-06-05	1600000	OP N° 632/2019	417		\N
633	92	254/19	3	001-001-0000364	142	CPS Sandra Lucia Sanchez Almeida Auxiliar Enfermeria MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1200000	OP Nº 555	414		\N
634	92	254/19	3	001-001-0000365	142	CPS Gladys Concepcion Armoa Villaverde Auxiliar Enfermeria MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1200000	OP Nº 556	414		\N
635	62	168/19	3	001-001-0000559	144	Jornales  - ELVA AISSA MORA ALFONSO - AUXILIAR ADMINISTRATIVA - ABRIL - MAYO 2019.-\n	2019-06-05	1600000	OP N° 633/2019	417		\N
636	92	254/19	3	001-001-0000366	142	CPS Nestor Villaverde Espinola Auxiliar Enfermeria MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1200000	OP Nº 557	414		\N
637	62	168/19	3	001-00-0000560	144	Jornales  - RAMONA TERESITA ROMERO - FICHERA - ABRIL - MAYO 2019.-\n	2019-06-05	1200000	OP N° 634/2019	417		\N
638	92	254/19	1	001-003-0000075	142	CPS Gabriela Careaga Arias Bioquimica MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1000000	OP Nº 558	414		\N
639	92	254/19	1	001-001-0000031	142	CPS Maria Jose Insaurralde Alvarenga Odontologa MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	2000000	OP Nº 559	414		\N
640	92	254/19	3	001-001-0000367	142	CPS Cynthia Isabel Peralta Prieto Auxiliar Enfermeria MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1200000	OP Nº 560	414		\N
641	62	168/19	3	001-001-0000561	144	Jornales  - ANALIA ALEJANDRA TROCHE - FICHERA - ABRIL - MAYO 2019.-\n	2019-06-05	1160000	OP N° 635/2019	417		\N
642	92	254/19	3	001-001-0000368	144	JORNALES Gustavo Adolfo Davalos Goiris Limpiador de patio MAYO-JUNIO 2019 Centro de Salud Yataity\t	2019-08-02	1200000	OP Nº 561	414		\N
643	62	168/19	3	001-001-0000562	144	Jornales  - VERONICA BOGADO TROCHE - LIMPIADORA - ABRIL - MAYO 2019.-\n	2019-06-05	1000000	OP N° 636/2019	417		\N
644	92	254/19	2	001-001-0000260	266	Consultoria, asesoria e investigacion\t	2019-05-27	1200000	OP Nº 562 Recibo de Dinero Nº 32 - 10/08/2019	414		\N
645	92	254/19	2	001-001-0000262	266	Consultoria, asesoria e investigacion\t	2019-06-27	1200000	OP Nº 562 Recibo de Dinero Nº 32 - 10/08/2019	414		\N
646	92	254/19	1	001-001-0007601	333	Productos e impresiones de artes gráficas\t	2019-09-30	270000	OP Nº 566	414		\N
647	92	254/19	1	001-001-0000213	341	Elementos de limpieza	2019-08-16	230000	OP Nº 563	414		\N
648	92	254/19	1	001-001-0003007	342	Útiles de escritorio, oficina y enseñanza\t	2019-09-17	200000	OP Nº 564	414		\N
650	92	254/19	1	001-001-0002192	397	Productos e insumos metálicos \t	2019-09-28	100000	OP Nº 565	414		\N
651	92	254/19	1	001-001-0002192	398	Productos e insumos no metálicos\t	2019-09-28	384000	OP Nº 565	414		\N
653	69	168/19	1	001-002-000013	142	Contratación Personal de Salud/ Miguel Angel Sanabria Arce/ Enfermero/Mes Mayo de  2019.\t\n	2019-06-11	1050000	OP N° 837/2019	408		\N
654	69	168/19	3	001-001-00358	144	Jornales/Blanca Duarte Aquino/ Limpiadora - Cocinera/Mes Mayo de  2019.\t\n	2019-06-11	1200000	OP N| 838/2019	408		\N
655	69	168/19	1	001-001-0000113	144	Jornales/Guido Ariel Rotela Rotela/Auxiliar Administrativo/Mes Mayo de  2019.\t\n	2019-06-11	600000	OP N| 839/2019	408		\N
649	69	168/19	3	001-001-00357	142	Contratación Personal de Salud/Isidro Ortiz/ Auxiliar en Enfermeria/Mes Mayo de 2019.\t\n	2019-06-11	1500000	OP N° 836/2019	408		\N
656	69	168/19	3	001-001-00359	144	Jornales/Virginia  García Vera/ Encargada de Admisión/Mes Mayo de 2019.\t\n	2019-06-11	1500000	OP N° 840/2019	408		\N
657	69	168/19	1	001-001-0000270	266	Consultoría, Asesoría e Investigaciones.\t\n	2019-06-11	1600000	OP N|841/2019	408		\N
658	69	168/19	1	002-001-0001689	334	Productos de Papel y Cartón\t\n	2019-07-25	16000	OP N° 855/2019	408		\N
659	46	61/19	1	001-001-1009	142	C.P.S.Laura Betania Acosta Balsaldua**Tec. En Enfermería  Corresponde a ENERO Y FEBRERO 2019	2019-04-19	1600000	OPN°270/2019	402		\N
660	69	168/19	1	002-001-0001689	341	Elementos de Limpieza\t\n	2019-07-25	104000	OP N°855/2019	408		\N
661	69	168/19	1	001-001-0000198	393	Estructuras Metálicas Acabadas\t\n	2019-07-17	500000	847/2019	408		\N
662	93	254/19	1	001-001-0000026	142	Contratación de personal de salud - Laura Rocio Gimenez Buscio - Lic en Enferméria - Agosto, Setiembre 2019	2019-09-30	3000000	OP Nº 30/2019	415		\N
663	69	168/19	1	001-001-0000107	398	Productos e Insumos no Metálicos\t\n	2019-07-18	300100	OP N| 848/2019	408		\N
664	69	168/19	1	019002-0002429	533	Maquinarias y Equipos Industriales\t\n	2019-07-22	5981031	OP N° 854/2019	408		\N
665	93	254/19	3	001-001-000047	144	Jornales  - Dina Mariel Barrios Araujo - Operadora Inf.  - Agosto, Setiembre 2019	2019-09-30	2000000	OP Nº 31/2019	415		\N
666	46	61/19	1	001-001-0004	142	C.P.S.María Asunción Marin Acosta**Enfermera de Guardia  Corresponde a ENERO Y FEBRERO 2019	2019-05-01	1600000	OPN°271/2019	402		\N
668	93	254/19	3	001-001-0000478	144	Jornales  - Constancia Benitez - Servicios Generales  - Agosto, Setiembre 2019	2019-09-30	1000000	OP Nº 32/2019	415		\N
669	93	254/19	1	001-001-0000177	145	Honorarios profesionales - Vanessa M. Ovelar Prieto - Administradora CLS - Agosto, Setiembre 2019	2019-09-30	3000000	OP Nº 33/2019	415		\N
670	46	61/19	3	001-001-1010	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a ENERO2019	2019-04-19	1800000	OPN°272/2019	402		\N
671	93	254/19	1	001-001-0000366	242	Mantenimiento y reparaciones menores de edificios y locales	2019-10-07	1100000	OP Nº 35/2019	415		\N
672	93	254/19	1	001-002-0000510	243	Mantenimientos y reparaciones menores de maquinarias, equipos y muebles de oficina	2019-10-03	120000	OP Nº 34/2019	415		\N
673	93	254/19	2	001-001-0053453	268	Servicios de comunicaciones	2019-08-30	160000	OP Nº 38/2019 Recibo de Dinero Nº 15217 - 14/10/2019	415		\N
674	46	61/19	3	001-001-1011	144	JORNALES.WALTER JOEL VERA MACIEL**Chofer de Ambulancia  Corresponde a FEBRERO 2019	2019-04-19	1800000	OPN°273/2019	402		\N
675	46	61/19	3	01-001-1012	144	|JORNALES.Ramón Antonio Chamorro Acosta**Encargado de Informática  Corresponde a  ENERO Y FEBRERO 2019	2019-04-19	2000000	OPN° 274/2019	402		\N
676	93	254/19	2	001-001-0057686	268	Servicios de comunicaciones	2019-09-30	160000	OP Nº 38/2019 Recibo de Dinero Nº 15217 - 14/10/2019	415		\N
677	93	254/19	1	001-003-001987	311	Alimentos para personas	2019-10-14	300000	OP Nº 40/2019	415		\N
678	46	61/19	3	001-001-1014	144	JORNALES.Fernando Ramón Galeano Villalba**Encargado de Farmacia  Corresponde a  ENERO Y FEBRERO 2019	2019-04-19	1500000	OPN°275/2019	402		\N
679	93	254/19	1	001-001-0055770	331	Papel de escritorio y cartón	2019-10-10	94000	OP  Nº 36/2019	415		\N
680	46	61/19	3	001-001-1014	144	JORNALES.Ramona Analia Aguirre Garay**Admisión  Corresponde a  ENERO Y FEBRERO 2019	2019-04-19	1300000	OPN°276/2019	402		\N
681	72	168/19	3	001-001-0001087	142	Contratación de personal de salud - Carlos Darío Peralta - Técnico en Laboratorio - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2320/2019	406		\N
682	46	61/19	3	001-001-1015	144	JORNALES.Domitila López Pana**Limpiadora  Corresponde a  ENERO Y FEBRERO 2019	2019-04-19	1200000	OPN°277/2019	402		\N
683	93	254/19	1	001-001-0002628	341	Elementos de limpieza	2019-10-11	424000	OP Nº 37/2019	415		\N
684	93	254/19	1	001-002-0000510	342	Útiles de escritorio, oficina y enseñanza	2019-10-03	750000	OP Nº 34/2019	415		\N
685	46	61/19	1	001-001-0030	145	HONORARIOS PROFESIONALES.Cinthia Mabel Venialgo Morel**Administrador- Contador  Corresponde a  ENERO Y FEBRERO 2019	2019-04-19	2600000	OPN°278/2019	402		\N
686	93	254/19	1	001-001-0055770	342	Útiles de escritorio, oficina y enseñanza	2019-10-10	28000	OP Nº 36/2019	415		\N
687	93	254/19	1	014-002-000107	352	Productos farmacéuticos y medicinales	2019-10-16	2499999	OP Nº 41/2019	415		\N
688	72	168/19	1	001-001-0000154	142	Contratación de personal de salud - Lic. Claudio Rubén Duarte Palma - Lic. En Enfermería - Guardia Urgencias  - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2321/2019	406		\N
689	93	254/19	1	001-001-0001812	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-10-15	320000	OP Nº 39/219	415		\N
690	72	168/19	1	001-001-0001088	142	Contratación de personal de salud - Mariela Isabel Resquin Duarte - Auxiliar de enfermería en consultorios - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2322/2019	406		\N
691	72	168/19	3	001-001-0001089	142	Contratación de personal de salud - Melissa Rocío Gavilán - Tec. De Enfermería Preparación de pacientes en consultorios - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2323/2019	406		\N
692	72	168/19	3	001-001-0001090	142	Contratación de personal de salud - Mercedes Sachelaridi Figueredo - Auxiliar de Enfermeria - Urgencias Guardia - Marzo, Abril y Mayo 2019\n	2019-06-28	2225000	OP N° 2324/2019	406		\N
693	72	168/19	1	001-001-0000127	142	Contratación de personal de salud - Lic. Olga Beatriz Rodríguez Zarate - Lic. En Enfermería. - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2325/2019	406		\N
694	94	254/19	1	001-001-0000153	142	CPS-Teresa Duarte -Lic en enfermeria-mes de jun/jul/2019\t	2019-08-16	2400000	OP Nº 322	416		\N
695	94	254/19	1	001-001-0000134	142	CPS-Perla Maidana-Lic en enfermeria-mes de jul/agost/2019\t	2019-08-31	2400000	OP Nº 323	416		\N
696	94	254/19	3	001-001-0000551	142	CPS - Aldo Sanchez - Aux. Enfermeria-Mes de jul/agost/2019\t	2019-09-05	2000000	OP Nº 324	416		\N
697	94	254/19	3	001-001-0000560	142	CPS - Carlos Medina - Aux. Enfermeria-Mes de jul/2019\t	2019-09-05	1000000	OP 325	416		\N
698	94	254/19	3	001-001-0000553	144	Jornales - Sonia Martinez- Aux. Adminsitrativo-Mes de jul/agost/2019\t	2019-09-05	2000000	OP Nº 326	416		\N
699	94	254/19	3	001-001-0000554	144	Jornales - Carmen Pereira- Aux. Administrativo-Mes de jul/agost/2019\t	2019-09-05	2000000	OP 327	416		\N
700	94	254/19	3	001-001-0000557	144	Jornales - Estelbina Silvero- Personal de Limpieza -Mes de jul/agost/2019\t	2019-09-05	1200000	OP Nº 328	416		\N
701	94	254/19	3	001-001-0000558	144	Jornales - Modesta Dominguez-Personal de Limpieza - Mes de jul/agost/2019	2019-09-05	1200000	OP Nº 329	416		\N
702	94	254/19	3	001-001-0000559	144	Jornales -Ilda Vera-Personal de limpieza-mes de jul/agost/2019\t	2019-09-05	1200000	OP Nº 330	416		\N
703	94	254/19	1	001-001-000037	145	Honorarios Profesionales- Sergio Rojas-Administrador-Mes de jul/agost/2019\t	2019-09-05	3600000	OP Nº 331	416		\N
704	94	254/19	1	001-002-0002142	341	Elementos de Limpieza\t	2019-09-05	6000000	OP 332	416		\N
705	72	168/19	3	001-001-0001091	142	Contratación de personal de salud - Sady Lorena Peralta Mercado - Atención a pacientes en Farmacia Interna Hospitalaria - Marzo, Abril y Mayo 2019\n	2019-06-28	2225000	OP N° 2326/2019	406		\N
706	72	168/19	1	001-001-0000030	142	Contratación de personal de salud - Walter Ramón Escobar Britez - Tec. Sup. de Radiología - Guardias Sábados - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2327/2019	406		\N
707	72	168/19	3	001-001-0001092	144	Jornales  - Alba María Zarate Lovera - Auxiliar Administrativo - Carga de Estadísticas - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2328/2019	406		\N
708	72	168/19	3	001-001-0001093	144	Jornales  - Carmen Martínez de López - Personal de Servicios Generales - Mucama - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2329/2019	406		\N
709	95	254/19	1	001-001-000064	142	Contratación de personal de salud - NANCY REBECA MARZAL MERELES - Técnico en Obstetricia - JUNIO - JULIO 2019	2019-08-09	1600000	OP Nº 637/2019	417		\N
710	95	254/19	1	001-001-000064	142	Contratación de personal de salud - MARIA ELIZABETH DOMINGUEZ - TECNICO EN ENFERMERIA - JUNIO - JULIO 2019	2019-08-01	1573333	OP Nº 638/2019	417		\N
711	72	168/19	3	001-001-0001094	144	Jornales  - María Isabel Benítez Lobos - Auxiliar Administrativo - Admisión de pacientes. - Marzo, Abril y Mayo 2019\n	2019-06-28	2250000	OP N° 2330/2019	406		\N
712	95	254/19	1	001-001-00005	142	Contratación de personal de salud - JULIANA DAVALOS DE LOPEZ - ENFERMERA - JUNIO - JULIO 2019	2019-08-09	1600000	OP Nº 639/2019	417		\N
713	72	168/19	3	001-001-0001095	144	Jornales  - Mónica Raquel Álvarez Giménez - Auxiliar Administrativo - Carga de Estadísticas - Marzo, Abril y Mayo 2019\n	2019-06-28	2150000	OP N°2331/2019	406		\N
714	95	254/19	1	001-001-00007	142	Contratación de personal de salud - LIZ YOHANA TROCHE ESPINOLA - ENFERMERA - JUNIO - JULIO 2019	2019-08-09	1573333	OP Nº 640/2019	417		\N
715	95	254/19	1	001-001-00004	142	Contratación de personal de salud - ROSA CONCEPCION CARDOZO - ENFERMERA - JUNIO - JULIO 2019	2019-08-09	1600000	OP Nº 641/2019	417		\N
716	95	254/19	1	001-001-000076	142	Contratación de personal de salud - RAMONA CONCEPCION BRITEZ - ENFERMERA - JUNIO - JULIO 2019	2019-08-29	1600000	OP Nº 643/2019	417		\N
718	95	254/19	3	001-001-000601	144	Jornales  - ELVA AISSA MORA ALFONSO - AUXILIAR ADMINISTRATIVA - JUNIO - JULIO 2019	2019-10-02	1600000	OP Nº 647/2019	417		\N
720	95	254/19	3	001-001-0000602	144	Jornales  - RAMONA TERESITA ROMERO - FICHERA - JUNIO - JULIO 2019	2019-10-02	1200000	OP Nº 648/2019	417		\N
721	95	254/19	1	001-001-0000603	144	Jornales  - ANALIA ALEJANDRA TROCHE - FICHERA - JUNIO - JULIO 2019	2019-10-02	1200000	OP Nº 649/2019	417		\N
722	49	61/19	1	001-001-0329	142	C.P.S.HONORINA GONZALEZ DE ROJAS**ENF. AUXI.  Corresponde a  MARZO - ABRIL 2019	2019-04-22	2000000	OPN°1104/2019	404		\N
723	95	254/19	1	001-001-0000604	144	Jornales  - VERONICA BOGADO TROCHE - LIMPIADORA - JUNIO - JULIO 2019	2019-10-02	1000000	OP Nº 650/2019	417		\N
724	49	61/19	1	001-001-0028	142	C.P.S.CARMEN LETICIA MAIDANA VELAZQUEZ*ENF. DE GUARDIA*  Corresponde a  MARZO - ABRIL 2019	2019-04-22	1200000	OPN°1105/2019	404		\N
725	49	61/19	1	001-001-0001	142	C.P.S -JOANNA RAMONA ORTIGOZA*ENF. DE GUARDIA*  Corresponde a  MARZO - ABRIL 2019	2019-06-18	1400000	OPN°1096/2019	404		\N
726	95	254/19	1	001-001-0000508	269	Servicios técnicos y profesionales varios	2019-08-14	150000	OP Nº 642/2019	417		\N
728	95	254/19	1	001-001-0019339	333	Productos e impresiones de artes gráficas	2019-10-02	50000	OP Nº 646/2019	417		\N
727	67	168/19	3	001-001-000325	142	Contrat Personal de Salud  - Deisi Liliana Leiva- Aux  Enferm Puesto Salud Mbocayaty-  Enero y Febrero 2019\t\n	2019-06-06	1400000	OP N° 828	411		\N
729	67	168/19	1	001-001-0000104	142	Contrat Personal de Salud . Norma Portillo - Auxiliar Enfermeria Puesto Salud Tacuarita-  Enero y Febrero 2019\t\n	2019-06-06	1400000	OP N° 829	411		\N
730	95	254/19	1	274-001-0002137	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-09-05	94700	OP Nº 644/2019	417		\N
731	67	168/19	3	001-001-000326	142	Contrat Personal de Salud  -Noelia Rolon- Aux  Enferm Puesto Salud Mbocayaty-Enero y Febrero 2019\t\n	2019-06-06	1400000	OP N| 830	411		\N
732	67	168/19	1	001-001-000078	142	Contratac Personal de Salud - Rossana Vazquez - Aux Enferm  Pto Salud Mbocayaty- Enero y Febrero 2019\t\n	2019-06-06	1400000	OP N° 831	411		\N
734	67	168/19	1	001-001-00026	142	Contratac Personal de Salud - Laura Noemi Fernadnez  - Aux Enferm  Pto Salud Mbocayaty- Enero y Febrero 2019\t\n	2019-06-14	1400000	OP N° 832	411		\N
735	67	168/19	1	001-001-00005	142	Contrat Personal de Salud . Clara Beatriz Roa Barrios - Auxiliar de Enfermeria Pto Salud Sta Barbara- Enero y Febrero 2019\t\n	2019-06-06	1400000	OP N° 833	411		\N
736	49	61/19	3	001-001-0502	142	JORNALES -OLGA GIMENEZ CABALLERO *ENF. DE GUARDIA*  Corresponde a  MARZO - ABRIL 2019	2019-04-22	1200000	OPN°1099/2019	404		\N
737	67	168/19	3	001-001-000329	144	Jornales - Yenni Fabiola Vega B. - Encarg Informat y Estadist Puesto Salud Mbocayaty- Enero y Febrero 2019\t\n	2019-06-06	1183340	OP N° 834	411		\N
738	67	168/19	3	001-001-000327	144	Jornales- Limpiadora- María Hermenegilda Chamorro - Puesto Salud Mbocayaty - Enero y Febrero 2019\t\n	2019-06-06	1000000	OP N° 835	411		\N
740	67	168/19	3	001-001-000328	144	Jornales- Limpiadora- Delma Jazmin Chamorro Franco - Puesto Salud Col. Jorge  Naville Enero y Febrero 2019\t\n	2019-06-06	1000000	OP N° 836	411		\N
741	49	61/19	3	001-001-0506	144	JORNALES -LOURDES MARGARITA ACHAR*ENCARGADA DE FARMACIA *  Corresponde a  MARZO - ABRIL 2019\n	2019-04-22	1000000	OPN°1097/2019	404		\N
742	49	61/19	3	001-001-0503	144	JORNALES -LIZ MABEL COLINA*LIMPIADORA*  Corresponde a  MARZO - ABRIL 2019	2019-04-22	1800000	OPN°1098/2019	404		\N
743	96	254/19	1	001-001-0000209	142	CPS- ODONTOLOGO - JORGE DERLYS GOMEZ MIRANDA - P.S. BOQUERON BORJA - AGOSTO 2019.\t	2019-09-05	1000000	OP Nº 29/2019	418		\N
744	96	254/19	1	001-001-0000211	142	CPS- ODONTOLOGO - JORGE DERLYS GOMEZ MIRANDA - P.S. BOQUERON BORJA - SETIEMBRE 2019.\t	2019-10-31	1000000	OP Nº 33/2019	418		\N
745	96	254/19	3	001-001-0000190	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - AGOSTO 2019.\t	2019-09-05	1000000	OP Nº 32/2019	418		\N
746	49	61/19	1	001-001-0501	144	JORNALES -ELSA ALEJANDRA ESCOBAR MACIEL**SECRETARIA ADMINISTRATIVA  Corresponde a  MARZO - ABRIL 2019	2019-04-22	2000000	OPN°1100/2019	404		\N
747	96	254/19	3	001-001-0000188	144	JORNALES - MUCAMA - MAGDALENA BRIZUELA BAEZ - P.S. VALLE PE BORJA -  AGOSTO 2019.\t	2019-09-05	600000	OP Nº 30/2019	418		\N
748	49	61/19	3	001-001-0504	144	JORNALES -MARIA ANTONIA ORTIZ*COCINERA*  Corresponde a  MARZO - ABRIL 2019	2019-04-22	1000000	OPN°1101/2019	404		\N
749	96	254/19	3	001-001-0000189	144	JORNALES - MUCAMA - MARIA CONCEPCION BAREIRO DE OJEDA - P.S. BOQUERON BORJA - AGOSTO 2019.\t	2019-09-05	600000	OP Nº 31/2019	418		\N
750	49	61/19	3	001-001-0507	144	JORNALES -LIDIA RIVAS ESPINOLA*ADMINISTRADORA*  Corresponde a  MARZO - 2019	2019-04-22	1500000	OPN°1102/2019	404		\N
751	96	254/19	3	001-001-0000193	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - SETIEMBRE 2019\t	2019-10-31	1000000	OP Nº 36/2019	418		\N
752	96	254/19	3	001-001-0000191	144	JORNALES - MUCAMA - MAGDALENA BRIZUELA BAEZ - P.S. VALLE PE BORJA - SETIEMBRE 2019.\t	2019-10-31	600000	OP Nº 34/2019	418		\N
753	49	61/19	3	001-001-0508	144	JORNALES -LIDIA RIVAS ESPINOLA*ADMINISTRADORA*  Corresponde a  ABRIL 2019	2019-04-22	1500000	OPN°1103/2019	404		\N
754	96	254/19	3	001-001-0000192	144	JORNALES - MUCAMA - MARIA CONCEPCION BAREIRO DE OJEDA - P.S. BOQUERON BORJA - SETIEMBRE 2019.\t	2019-10-31	600000	OP Nº 35/2019	418		\N
755	49	61/19	1	001-002-1401	343	Útiles y materiales electricos	2019-05-07	400000	OPN°1106/2019	404		\N
756	67	168/19	1	001-001-00106	145	Honorario Profesional - Contador Luis Miguel Sánchez A. En el Consejo Local de Salud de Mbocayaty -Enero y Febrero 2019\t\n	2019-06-06	2000000	OP N° 837	411		\N
757	67	168/19	1	023-004-2197100	212	Agua	2019-06-13	150620	OP N° 838	411		\N
758	96	254/19	1	001-001-0000445	243	MANTENIMIENTOS Y REPARACIONES MENORES DE MAQUINARIAS, EQUIPOS Y MUEBLES DE OFICINA	2019-08-13	500000	OP Nº 28/2019	418		\N
759	67	168/19	1	001-001-000242	242	Mantenimiento y reparaciones\t\n  de edificios y locales\t\n	2019-07-12	700000	OP N° 839	411		\N
760	96	254/19	1	001-001-0000363	244	MANTENIMIENTO Y REPARACIONES MENORES DE VEHICULOS\t	2019-11-02	2362000	OP Nº 40/2019	418		\N
761	67	168/19	1	001-001-01611	341	Elementos de Limpieza\t\n	2019-07-19	566040	OP N| 840	411		\N
762	96	254/19	1	001-001-0000369	244	MANTENIMIENTO Y REPARACIONES MENORES DE VEHICULOS\t	2019-11-14	3750000	OP Nº 41/2019	418		\N
763	96	254/19	1	001-001-0000100	266	CONSULTORIAS, ASESORIAS E INVESTIGACION\t	2019-11-02	1820000	OP Nº 37/2019	418		\N
764	75	168/19	1	001-001-000102	142	Contratación Personal de Salud/Celia Aderete Benitez/ Enfermera/ Salario mes Mayo y Junio de  2019.\t\n\n	1979-07-05	2000000	OP N° 779/2019	403		\N
765	75	168/19	1	001-001-000009	142	Contratación Personal de Salud/Elva Rodríguez de González/ Enfermera/Salario/mes Mayo y Junio de 2019\t\n	2019-07-05	2000000	780/2019	403		\N
766	75	168/19	3	001-001-00939	142	Contratación Personal de Salud/Fidel Brítez Avalos/ Técnico en Enfermería/Salario/mes Mayo y Junio de  2019\t\n	2019-07-05	2000000	OP N° 781/2019	403		\N
767	75	168/19	1	001-001-00940	142	Contratación Personal de Salud/María L. Garay Villalba/Técnica en Obstetricia/Salario/Mes Mayo y Junio de  2019.\t\n	2019-07-05	2000000	OP N° 782/2019	403		\N
768	75	168/19	1	0019-002-0000026	142	Contratación Personal de Salud/Salustiana Espínola Colmán/Bioquímica/Salario/Mes Mayo y Junio de  2019.\t\n	2019-07-10	4000000	OP N° 783/2019	403		\N
769	75	168/19	3	001-001-00941	144	Jornales/Mauda Trinidad Sachelaridis / Técnica en Bioestadística/ Salario/Mes Mayo y Junio de  2019.\t\n	2019-07-05	2800000	OP N°784	403		\N
770	75	168/19	3	001-001-00942	144	Jornales/Olga Zoraida Rivas/Cocinera/Salario/Mes Mayo  y Junio de 2019.\t\n	2019-07-05	1600000	OP N| 785/2019	403		\N
771	75	168/19	3	001-001-00943	144	Jornales/Zacaria Ortiz Ayala/Chofer de Ambulancia/ Salario/Mes Mayo y Junio  de 2019.\t\n	2019-07-05	3000000	OP N°786/2019	403		\N
772	75	168/19	1	001-001-001199	212	Agua	2019-07-12	500000	OP N°815/2019	403		\N
773	75	168/19	1	001-001-0000163	242	Mantenimiento y Reparaciones Menores de Edificios y Locales\t\n	2019-07-24	220000	821/2019	403		\N
774	75	168/19	1	001-001-0000484	242	Mantenimiento y Reparaciones Menores de Edificios y Locales\t\n	2019-07-25	3507000	OP N°823/2019	403		\N
775	75	168/19	1	001-001-0000235	244	Mantenimiento y Reparaciones Menores de Vehículos\t\n	2019-09-17	160000	OP N°817/2019	403		\N
776	75	168/19	1	001-001-0000271	266	Consultoría, Asesoría e Investigaciones.\t\n	2019-07-05	3200000	OP N°787/2019	403		\N
777	75	168/19	1	001-001-0006138	311	Alimentos para Personas\t\n	2019-07-10	42000	OP N° 807/2019	403		\N
778	75	168/19	1	001-001-0005786	331	Papel de Escritorio y Cartón\t\n	2019-07-10	214000	OP N°808/2019	403		\N
780	75	168/19	1	001-001-0018864	333	Productos e Impresiones de Artes Gráficas\t\n	2019-07-18	680000	820/2019	403		\N
781	75	168/19	1	001-001-0005786	334	Productos de Papel y Cartón\t\n	2019-07-10	8000	OP N° 808/2019	403		\N
782	75	168/19	1	001-001-0006137	341	Elementos de limpieza	2019-07-10	70000	OP N°809/2019	403		\N
783	75	168/19	1	001-001-00005786	342	Útiles de Escritorio, Oficina y Enseñanza\t\n	2019-07-10	293500	OP N° 808/2019	403		\N
784	75	168/19	1	001-001-0006139	344	Utensilios de Cocina y Comedor\t\n	2019-07-10	65000	OP N°810/2019	403		\N
785	75	168/19	1	001-001-0000244	346	Repuestos y Accesorios Menores\t\n	2019-07-10	200000	OP N°819/2019	403		\N
786	75	168/19	1	001-001-0031739	346	Repuestos y Accesorios Menores\t\n	2019-07-25	220000	OP N°822/2019	403		\N
787	75	168/19	1	001-001-0003414	355	Tintas, Pinturas y Colorantes\t\n	2019-07-10	402000	OP N° 811/2019	403		\N
788	75	168/19	1	001-001-0001160	358	Útiles y Materiales Médico Quirúrgico y de Laboratorio\t\n	2019-07-10	320000	806/2019	403		\N
789	75	168/19	1	001-001-0064481	392	Cubiertas y Cámaras de Aire\t\n	2019-07-09	140000	OP N°804/2019	403		\N
791	45	168/19	1	001-001-0000186	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - MAYO 2019, JUNIO 2019\t\n	2019-08-09	2000000	OP N°23/2019	418		\N
792	45	168/19	1	001-001-0000187	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - JULIO 2019.\t\n	2019-08-09	1000000	OP N° 24/2019	418		\N
793	45	168/19	1	001-001-0000184	144	JORNALES - MUCAMA - MAGDALENA BRIZUELA BAEZ - P.S. VALLE PE BORJA -  MAYO 2019, JUNIO 2019, JULIO 2019.\t\n	2019-08-09	1800000	OP N°21/2019	418		\N
794	45	168/19	1	001-001-0000185	144	JORNALES - MUCAMA - MARIA CONCEPCION BAREIRO DE OJEDA - P.S. BOQUERON BORJA - MAYO 2019, JUNIO 2019, JULIO 2019.\t\n	2019-08-09	1800000	OP N°22/2019	418		\N
795	45	168/19	1	001-001-0000324	244	MANTENIMIENTO Y REPARACIONES MENORES D VEHICULOS\t\n	2019-08-08	1680000	OP N°26/2019	418		\N
796	45	168/19	1	001-001-0000080	266	CONSULTORIAS, ASESORIAS E INVESTIGACION\t\n	2019-06-28	1820000	OP N°20/2019	418		\N
797	45	168/19	1	001-001-0000325	346	REPUESTOS Y ACCESORIOS MENORES\t\n	2019-08-09	500000	OP N°27/2019	418		\N
798	45	168/19	1	001-001-0000325	346	REPUESTOS Y ACCESORIOS MENORES\t\n	2019-08-09	300000	OP N° 2019	418		\N
799	45	168/19	1	001-001-0000325	346	REPUESTOS Y ACCESORIOS MENORES\t\n\n	2019-08-09	100000	OP N° 27/2019	418		\N
800	45	168/19	1	001-001-0000325	362	Lubricantes	2019-08-09	300000	OP N° 27/2019	418		\N
801	64	168/19	1	001-001-0002132	343	Utiles y materiales electricos	2019-06-10	100000	OP N° 549	414		\N
802	64	168/19	1	001-001-0002209	343	ÚTILES Y MATERIALES ELÉCTRICOS	2019-06-14	552000	OP N°550	414		\N
803	64	168/19	1	001-001-0005667	358	ÚTILES Y MAT. MÉDICO-QUIRÚRGICOS Y DE LAB.	2019-06-14	48000	OP N°552	414		\N
804	64	168/19	1	001-001-0009866	535	EQUIPO. DE SALUD Y LABORATORIO	2019-06-14	500000	OP N°551	414		\N
805	48	61/19	1	001-001-000089	142	Contratación Personal de Salud/Celia Aderete Benitez/ Enfermera/ Salario mes Marzo y Abril de  2019.\t	2019-05-23	2000000	OPN°740/2019	403		\N
806	48	61/19	1	001-001-0000008	142	Contratación Personal de Salud/Elva Rodríguez de González/ Enfermera/Salario/mes Marzo y Abril de 2019\t	2019-05-22	2000000	OPN°741/2019	403		\N
807	48	61/19	3	001-001-00924	142	Contratación Personal de Salud/Fidel Brítez Avalos/ Técnico en Enfermería/Salario/mes Marzo y Abril de  2019\t	2019-05-22	2000000	OPN°742/2019	403		\N
808	48	61/19	3	001-001-00925	142	Contratación Personal de Salud/María L. Garay Villalba/Técnica en Obstetricia/Salario/Mes Marzo y Abril  de  2019.\t	2019-05-22	2000000	OPN°743/2019	403		\N
809	48	61/19	1	001-002-0000013	142	Contratación Personal de Salud/Salustiana Espínola Colmán/Bioquímica/Salario/Mes Marzo y Abril  de  2019.\t	2019-05-22	4000000	OPN°744/2019	403		\N
810	48	61/19	3	001-001-00938	144	Jornales/Mauda Trinidad Sachelaridis / Técnica en Bioestadística/ Salario/Mes Marzo y Abril de  2019.\t	2019-05-22	2400000	OPN°745/2019	403		\N
811	48	61/19	3	001-001-00927	144	Jornales/Olga Zoraida Rivas/Cocinera/Salario/Mes Marzo y Abril   de 2019.\t	2019-05-22	1600000	OPN°747/2019	403		\N
812	48	61/19	1	001-001-00928	144	Jornales/Zacaria Ortiz Ayala/Chofer de Ambulancia/ Salario/Mes Marzo y Abril de 2019.\t	2019-05-22	4000000	OPN°748/2019	403		\N
813	48	61/19	1	001-001-001343	244	Mantenimiento y Reparaciones Menores de Equipos de Transporte\t	2019-05-27	400000	OPN°766/2019	403		\N
814	48	61/19	1	001-001-0000218	244	Mantenimiento y Reparaciones Menores de Equipos de Transporte\t	2019-05-29	20000	OPN°774/2019	403		\N
815	48	61/19	1	001-001-0000218	245	Servicios de Limpieza, Aseo y Fumigación	2019-05-29	25000	OPN°774/2019	403		\N
816	48	61/19	1	001-001-0000268	266	Consultoría, Asesoría e Investigaciones.\t	2019-05-22	3200000	OPN°749/2019	403		\N
817	48	61/19	1	001-001-000402	281	Servicios de Ceremonial\t	2019-05-29	520000	OPN° 776/2019	403		\N
818	48	61/19	1	001-001-0004900	331	Papel de escritorio y carton	2019-05-27	94000	OPN° 768/2019	403		\N
819	48	61/19	1	001-001-0004900	334	Producto de papel y carton	2019-05-27	100000	OPN° 768/2019	403		\N
820	48	61/19	1	001-001-0006019	341	Elementos de limpieza	2019-05-27	40000	OPN°767/2019	403		\N
822	48	61/19	1	001-001-0006019	342	Útiles de escritorio, oficina y enseñanza	2019-05-27	34000	OPN°767/2019	403		\N
823	48	61/19	1	001-001-0004900	342	Útiles de Escritorio, Oficina y Enseñanza\t	2019-05-27	10000	OPN°768/2019	403		\N
824	48	61/19	1	001-001-0002015	342	Útiles de Escritorio, Oficina y Enseñanza\t	2019-05-28	340000	OPN°773/2019	403		\N
825	48	61/19	1	001-001-0003355	343	Útiles y materiales eléctricos	2019-05-28	55000	OPN°772/2019	403		\N
826	48	61/19	1	001-001-0003352	343	Útiles y materiales eléctricos	2019-05-27	100000	OPN°778/2019	403		\N
827	48	61/19	1	001-001-30843	346	Repuestos y accesorios menores	2019-05-27	300000	OPN°770/2019	403		\N
828	48	61/19	1	001-001-0003352	355	Tintas, Pinturas y Colorantes\t	2019-05-27	250000	OPN°788/2019	403		\N
829	48	61/19	1	001-001-0002015	358	Útiles y Materiales Médico Quirúrgicos  y de Laboratorio\t	2019-05-28	155000	OPN°773/2019	403		\N
830	48	61/19	1	001-001-0001086	358	Útiles y Materiales Médico Quirúrgicos  y de Laboratorios\t	2019-05-28	200000	OPN°775/2019	403		\N
831	48	61/19	1	001-001-0003352	397	Productos e Insumos Metálicos\t	2019-05-27	120000	OPN°778/2019	403		\N
832	48	61/19	1	001-001-0003352	399	Bienes de Consumo Varios\t	2019-05-27	125000	OPN°778/2019	403		\N
833	48	61/19	1	001-001-0002015	536	Equipos de Comunicaciones y Señalamientos\t	2019-05-28	110000	OPN°773/2019	403		\N
834	48	61/19	1	001-001-0003352	541	Adquisiciones de Muebles y Enseres\t	2019-05-27	350000	OPN°778/2019	403		\N
835	48	61/19	1	001-001-00932	541	Adquisiciones de Muebles y Enseres\t	2019-05-28	750000	OPN°777/2019	403		\N
836	50	61/19	3	001-001-0000465	142	Contratación de personal de salud-Milner Eudelio Gimenez Resquin **Técnico Radiologo Corresponde al mes de marzo y abril/2019	2019-05-07	1400000	OPN° 23/2019	405		\N
837	50	61/19	1	001-001-0000105	142	Contratación de personal de salud- Lic.Nidia Ester Torres Cano**Lic en Enfermeria mes de marzo y abril / 2019	2019-05-07	2000000	OPN°24/2019	405		\N
838	50	61/19	3	001-001-0000463	142	Contratación de personal de Salud-Ubaldina Verón Fariña**Técnico en Enfermería  Corresponde a mes de marzo y abril 2019	2019-05-07	1600000	OPN°25/2019	405		\N
839	50	61/19	1	001-001-000516	144	Jornales-Simone Caballero **Administradora CLS M.J.Troche  Corresponde a  mes de marzo y abril / 2019	2019-05-07	3316000	OPN°26/2019	405		\N
840	50	61/19	1	001-001-0000276	145	Honorarios Profesionales- Lic. Blanca P. González Verón**Contadora CLS M.J.Troche  Corresponde al mes de marzo y abril  /2019	2019-05-07	600000	OPN°27/2019	405		\N
841	50	61/19	1	001-001-0000219	242	Mantenimiento y reparación menores de edificios locales	2019-05-28	400000	OPN°50/2019	405		\N
842	50	61/19	1	001-001-0000252	242	Mantenimiento y reparación menores de edificios locales	2019-05-22	900000	OPN°41/2019	405		\N
843	50	61/19	1	001-001-0000218	243	MANTENIMIENTO Y REPARACIONES MENORES DE MAQUINARIAS EQUIPOS Y MUEBLES DE OFICINA	2019-05-10	230000	OPN°33/2019	405		\N
844	50	61/19	1	001-001-0000460	244	MANTENIMIENTO Y REPARACIONES MENORES DE VEHICULOS	2019-06-05	150000	OPN°55/2019	405		\N
845	50	61/19	1	001-001-0000464	245	Servicios de limpieza, aseo y fumigación	2019-05-07	150000	OPN°28/2019	405		\N
846	50	61/19	1	001-001-0052	311	ALIMENTOS PARA PERSONAS	2019-05-08	120000	OPN°31/2019	405		\N
847	50	61/19	1	001-001-0011608	323	Confecciones textiles\n	2019-05-07	120000	OPN°29/2019	405		\N
848	50	61/19	1	001-001-0002601	323	Confecciones textiles	2019-05-10	1066000	OPN°32/2019	405		\N
849	50	61/19	1	001-001-0002661	323	Confecciones textiles	2019-05-28	300000	OPN°51/2019	405		\N
850	50	61/19	1	001-001-0051054	331	Papel de escritorio y cartón	2019-05-28	346000	OPN°48/2019	405		\N
851	50	61/19	1	001-001-0017899	342	Utiles de escritorio de oficina y enseñanza\n	2019-05-28	90000	OPN°49/2019	405		\N
852	50	61/19	1	001-001-0000628	342	Utiles de escritorio de oficina y enseñanza\n	2019-05-27	650000	OPN°42/2019	405		\N
853	50	61/19	1	001-001-0004153	343	Útiles  y materiales eléctricos\n	2019-05-07	1561000	OPN°30/2019	405		\N
854	50	61/19	1	001-001-0000189	343	Útiles  y materiales eléctricos\n	2019-05-10	188500	OPN°34/2019	405		\N
855	50	61/19	1	001-001-0000547	343	Útiles  y materiales eléctricos\n	2019-05-10	200000	OPN°35/2019	405		\N
856	50	61/19	1	001-001-0004154	343	Útiles  y materiales eléctricos	2019-05-11	181500	OPN°38/2019	405		\N
857	50	61/19	1	001-007-0029489	343	Útiles  y materiales eléctricos	2019-05-16	15000	OPN°40/2019	405		\N
858	50	61/19	1	001-001-0000189	355	Tintas, pinturas y colorantes	2019-05-10	260000	OPN°34/2019	405		\N
859	50	61/19	1	001-001-0003288	358	Utiles y materiales medicos -quirúrgico y de laboratorio	2019-05-27	67000	OPN°43/2019	405		\N
860	50	61/19	1	001-003-0003911	358	Utiles y materiales medicos -quirúrgico y de laboratorio\n	2019-05-28	197000	OPN°45/2019	405		\N
861	50	61/19	1	001-001-0000191	397	Productos e insumos metalicos\n	2019-05-28	500000	OPN°44/2019	405		\N
862	50	61/19	1	001-001-0000192	398	Bienes e insumos no metalicos\n	2019-05-29	500000	OPN°53/2019	405		\N
863	50	61/19	1	001-001-0004154	398	Bienes e insumos no metalicos\n	2019-05-11	10500	OPN°38/2019	405		\N
864	50	61/19	1	001-001-0004154	399	Bienes de consumo varios	2019-05-11	8500	OPN°38/2019	405		\N
865	50	61/19	1	001-001-0004212	399	Bienes de consumo varios\n	2019-05-31	223000	OPN°52/2019	405		\N
866	50	61/19	1	001-001-0000494	535	Equipo de Salud y de Laboratorio	2019-06-03	650000	OPN°54/2019	405		\N
867	51	61/19	1	001-001-0000017	142	Contratación de personal de salud - Walter Ramón Escobar Britez - Tec. Sup. de Radiología - Guardias Sábados - Enero, Febrero 2019	2019-05-10	1500000	OPN° 2286/2019	406		\N
868	51	61/19	1	001-001-0000108	142	Contratación de personal de salud - Lic. Olga Beatriz Rodríguez Zarate - Lic. En Enfermería. - Enero, Febrero 2019\n	2019-05-10	1500000	OPN°2284/2019	406		\N
869	51	61/19	1	001-001-0000152	142	Contratación de personal de salud - Lic. Claudio Rubén Duarte Palma - Lic. En Enfermería - Guardia Urgencias  - Enero, Febrero 2019	2019-05-10	1500000	OPN°2280/2019	406		\N
871	51	61/19	3	001-001-0001078	142	Contratación de personal de salud - Mariela Isabel Resquin Duarte - Auxiliar de enfermería en consultorios - Enero, Febrero 2019	2019-05-10	1475000	OPN°2281/2019	406		\N
870	51	61/19	3	001-001-00001077	142	Contratación de personal de salud - Carlos Darío Peralta - Técnico en Laboratorio - Enero, Febrero 2019	2019-05-10	1400000	OPN°2278/2019	406		\N
872	51	61/19	3	001-001-0001079	142	Contratación de personal de salud - Melissa Rocío Gavilán - Tec. De Enfermería Preparación de pacientes en consultorios - Enero, Febrero 2019	2019-05-10	1500000	OPN°2282/2019	406		\N
873	51	61/19	3	001-001-0001080	142	Contratación de personal de salud - Mercedes Sachelaridi Figueredo - Auxiliar de Enfermeria - Urgencias Guardia - Enero, Febrero 2019	2019-05-10	1500000	OPN°2283/2019	406		\N
874	51	61/19	3	001-001-0001081	142	Contratación de personal de salud - Sady Lorena Peralta Mercado - Atención a pacientes en Farmacia Interna Hospitalaria - Enero, Febrero 2019	2019-05-10	1500000	OPN°2285/2019	406		\N
875	51	61/19	3	001-001-0001082	144	Jornales  - Alba María Zarate Lovera - Auxiliar Administrativo - Carga de Estadísticas - Enero, Febrero 2019	2019-05-10	1425000	OPN°2287/2019	406		\N
876	51	61/19	3	001-001-0001083	144	Jornales  - Carmen Martínez de López - Personal de Servicios Generales - Mucama - Enero, Febrero 2019	2019-05-10	1500000	OPN°2288/2019	406		\N
877	51	61/19	3	001-001-0001084	144	Jornales  - María Isabel Benítez Lobos - Auxiliar Administrativo - Admisión de pacientes. - Enero, Febrero 2019	2019-05-10	1500000	OPN°2289/2019	406		\N
878	51	61/19	3	001-001-0001085	144	Jornales  - Mónica Raquel Álvarez Giménez - Auxiliar Administrativo - Carga de Estadísticas - Enero, Febrero 2019	2019-05-10	1450000	OPN°2290/2019	406		\N
879	51	61/19	1	001-003-0000237	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Enero, Febrero 2019	2019-05-12	2500000	OPN°2291/2019	406		\N
880	51	61/19	1	001-001-0000222	242	Mantenimiento y reparaciones menores de edificios y locales	2019-06-21	500000	OPN°2304/2019	406		\N
881	51	61/19	3	001-001-0001086	245	Servicios de limpieza, aseo y fumigación	2019-06-20	501940	OPN°2307/2019	406		\N
882	51	61/19	1	001-001-0000775	261	De informática y sistemas computarizados	2019-05-16	450000	OPN°2299/2019	406		\N
883	51	61/19	1	001-001-0000428	321	Hilados y telas	2019-05-09	40000	OPN°2292/2019	406		\N
884	51	61/19	1	006-001-0088098	321	Hilados y telas	2019-05-10	75660	OPN°2295/2019	406		\N
885	51	61/19	1	001-001-0052133	331	Papel de escritorio y carton	2019-06-20	252000	OPN°2305/2019	406		\N
886	51	61/19	1	001-001-0052353	331	Papel de escritorio y carton	2019-06-20	113500	OPN°2305/2019	406		\N
887	51	61/19	1	001-001-0007529	341	Elementos de limpieza\n	2019-05-10	577000	OPN°2298/2019	406		\N
888	51	61/19	1	001-001-0002832	342	Útiles de escritorio, oficina y enseñanza	2019-06-05	140000	OPN°2301/2019	406		\N
889	51	61/19	1	001-001-7758	343	Útiles y materiales eléctricos	2019-05-10	864600	OPN°2296/2019	406		\N
890	51	61/19	1	001-001-0000069	343	Útiles y materiales eléctricos	2019-06-29	1320000	OPN°2309/2019	406		\N
891	51	61/19	1	002-001-1207	346	Repuestos y accesorios menores	2019-06-18	95000	OPN° 2303/2019	406		\N
892	51	61/19	1	001-001-0000220	346	Repuestos y accesorios menores	2019-06-19	200000	OPN° 2304/2019	406		\N
893	51	61/19	1	001-001-0014249	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-05-10	324000	OPN°2297/2019	406		\N
894	51	61/19	1	001-001-0005554	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-05-27	100000	OPN°2300/2019	406		\N
895	51	61/19	1	001-001-0000306	358	Útiles y materiales médico-quirúrgicos y de laboratorio	2019-06-20	156000	OPN°2306/2019	406		\N
896	51	61/19	1	001-001-0000499	359	Productos e instrumentales químicos y medicinales varios	2019-06-25	1300000	OPN°2308/2019	406		\N
897	51	61/19	1	001-002-0001029	398	Productos e insumos no metálicos	2019-06-13	300000	OPN°2302/2019	406		\N
898	51	61/19	1	001-001-7758	399	Bienes de consumo varios	2019-05-10	300	OPN°2296/2019	406		\N
899	51	61/19	1	001-001-0014249	535	Equipos de salud y de laboratorio\n	2019-05-10	840000	OPN°2297/2019	406		\N
900	52	61/19	1	001-001-0000004	142	Contratación de personal de salud - Esilda González Giménez - Lic Enfermería - Marzo, Abril, 2019	2019-05-12	1775000	OPN°236/2019	407		\N
901	52	61/19	1	001-001-0000004	142	Contratación de personal de salud - Liz Mabel Barua Aguirre - Lic Enfermeria - Marzo, Abril, 2019\n	2019-05-12	1775000	OPN°237/2019	407		\N
902	52	61/19	3	001-001-0000283	144	Jornales  - Americo Cristaldo Rojas - Servicios Generales - Peón de Patio - Marzo, Abril, 2019	2019-05-12	1060000	OPN°238/2019	407		\N
903	52	61/19	3	001-001-0000284	144	Jornales  - Ana Fatima González - Servicios Generales - Mucama - Abril, - 2019	2019-05-12	440000	OPN°239/2019	407		\N
904	52	61/19	1	001-001-000002	144	Jornales  - Liz María Azuaga Acuña - Auxiliar Administrativo - Marzo, Abril, 2019	2019-05-12	1875000	OPN° 240/2019	407		\N
905	52	61/19	1	001-003-0000235	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Marzo, Abril, 2019	2019-05-12	2600000	OPN°241/2019	407		\N
906	52	61/19	3	001-001-0000285	242	Mantenimiento y reparaciones menores de edificios y locales	2019-05-23	426550	OPN°244/2019	407		\N
907	52	61/19	1	001-001-0002851	342	Útiles de escritorio, oficina y enseñanza	2019-06-20	320000	OPN°243/2019	407		\N
908	52	61/19	1	001-001-0002474	399	Bienes de consumo varios	2019-05-07	187000	OPN°234/2019	407		\N
909	52	61/19	2	001-004-0000239	535	Equipos de salud y de laboratorio	2019-04-25	4041450	RECIBO 8242, FECHA 06/05/2019 EN DOS CUOTAS 	407		\N
910	53	61/19	3	001-001000354	142	Contratación Personal de Salud/Isidro Ortiz/ Auxiliar en Enfermeria/Mes Marzo y Abril 2019.\t	2019-05-07	3000000	OPN°823/2019	408		\N
911	53	61/19	1	001-002-0000011	142	Contratación Personal de Salud/ Miguel Angel Sanabria Arce/ Enfermero/Mes de Marzo y Abril 2019.\t	2019-05-07	2100000	OPN°824/2019	408		\N
912	53	61/19	3	001-001-000355	144	Jornales/Blanca Duarte Aquino/ Limpiadora - Cocinera/Mes de Marzo y Abril 2019.\t	2019-05-07	2400000	OPN°825/2019	408		\N
913	53	61/19	1	001-001-0099	144	Jornales/Guido Ariel Rotela Rotela/Auxiliar Administrativo/Mes de Marzo y Abril  2019.\t	2019-05-07	1200000	OPN°827/2019	408		\N
914	53	61/19	3	001-001-000356	144	Jornales/Virginia  García Vera/ Encargada de Admisión/Mes de Marzo y Abril 2019.\t	2019-05-07	3000000	OPN°828/2019	408		\N
915	53	61/19	1	001-001-0000267	266	Consultoría, Asesoría e Investigaciones.\t	2019-05-07	3200000	OPN°835/2019	408		\N
916	54	61/19	1	001-001-00226	142	Contratación de personal de salud - Odontologo  Arthur Adelio Rolón Barrios - mes de abril y mayo del 2019\t	2019-06-03	4033328	OPN° 459/2019	409		\N
917	54	61/19	3	001-001-0000398	144	Jornales - Jardinero - Cristian Gonzalez  - mes de abril y mayo del 2019\t	2019-06-03	966666	OPN°460	409		\N
919	54	61/19	3	001-001-0000400	144	Jornales - Auxiliar Informática - Gricelda Martinez Gallardo  - mes de abril del 2019	2019-06-03	832501	OPN°462/2019	409		\N
920	54	61/19	3	001-001-0000401	144	Jornales - Auxiliar Informática - Gricelda Martinez Gallardo  - mes de mayo del 2019\t	2019-06-03	925000	OPN°462	409		\N
921	54	61/19	1	001-001-0000590	145	Honorarios Profesionales -Contador -Fernando D. Ramos Villasboa  - mes de abril del 2019\t\n	2019-06-03	1000000	OPN°463/2019	409		\N
922	54	61/19	1	001-001-0000122	242	Mantenimiento y reparaciones menores de edificios y locales\t	2019-05-22	447000	OPN°457/2019	409		\N
923	54	61/19	1	001-001-0000121	343	Útiles y materiales eléctricos\t\n	2019-05-22	753000	OPN°457/2019	409		\N
924	54	61/19	1	003-001-0000286	352	Productos Farmacéuticos y Medicinales\t	2019-05-07	2500000	OPN°455/2019	409		\N
925	54	61/19	1	001-014-0008964	352	Productos Farmacéuticos y Medicinales\t	2019-05-08	2500002	OPN°456/2019	409		\N
918	54	61/19	3	001-001-0000399	144	Jornales - Limpiadora - Antonia Vera Bogado  - mes de abril y mayo del 2019\t	2019-06-03	833330	OPN°462/2019	409		\N
926	55	61/19	1	001-001-0000103	142	Contratación de personal de salud - Andrea Gisselle Giménez  - Odontóloga de Consultorio - Mayo, Junio / 2019	2019-07-03	4000000	OPN° 880/2019	410		\N
927	55	61/19	1	001-001-0000227	142	Contratación de personal de salud - Cristian Fernández Núñez. - Servicio de Lic. Enfermería - Mayo, Junio / 2019	2019-07-03	2400000	OPN°881/2019	410		\N
928	55	61/19	1	001-001-0000002	142	Contratación de personal de salud - Fatima Lorena Martinez Vazquez - Lic. Enfermería - Mayo, Junio / 2019	2019-07-03	2400000	OPN°882/2019	410		\N
929	55	61/19	3	001-001-0000161	144	Jornales  - Carmelo Giménez Rivero - Servicio de Sereno - Mayo, Junio / 2019	2019-06-28	2163333	OPN° 879/2019	410		\N
930	55	61/19	1	001-003-0000243	145	Honorarios profesionales - José Manuel Peralta - Contador CLS NT - Mayo, Junio / 2019\n	2019-07-16	2400000	OPN°883/2019	410		\N
931	55	61/19	3	001-001-0000162	245	Servicios de limpieza, aseo y fumigación	2019-06-28	36667	OPN°877/2019	410		\N
932	55	61/19	1	001-001-0000068	343	Útiles y materiales eléctricos	2019-06-29	950000	OPN°878/2019	410		\N
933	55	61/19	2	001-002-0003818	541	Adquisiciones de muebles y enseres\n	2019-02-06	650000	RECIBO N° 164648, FECHA 03/07/2019 OPN° 884/2019,  3ra CUOTA DE CANCELACION.	410		\N
934	56	61/19	1	001-001-0102	142	Contratación de personal de salud - María A Cabral R. - Lic. En Enfermería - Enero, Febrero y Marzo 2019	2019-05-11	1800000	OPN°615/2019	412		\N
935	56	61/19	3	001-001-0000341	142	Contratación de personal de salud - María Candelaria Chamorro P. - Atención en Farmacia Interna  - Enero, Febrero y Marzo 2019\n	2019-05-12	1200000	OPN°616/2019	412		\N
936	56	61/19	1	001-001-0004	142	Contratación de personal de salud - Romina María Oviedo Silvero - Lic. En Enfermería - Enero, Febrero y Marzo 2019\n	2019-05-11	1800000	OPN°617/2019	412		\N
937	56	61/19	1	001-001-0000101	142	Contratación de personal de salud - Rumilda Chamorro Ramírez - Dra. Consultorio Odontología - Enero, Febrero y Marzo 2019	2019-05-11	3600000	OPN°618/2019	412		\N
938	56	61/19	1	001-001-0056	142	Contratación de personal de salud - Vilma Ramírez Amarilla - Lic. En Enfermería - Enero, Febrero y Marzo 2019	2019-05-11	1800000	OPN°619/2019	412		\N
939	56	61/19	3	001-001-0000342	144	Jornales  - Bernardo González Giménez - Servicios Generales - Enero, Febrero y Marzo 2019	2019-05-12	900000	OPN°620/2019	412		\N
942	56	61/19	3	001-001-0000345	245	Servicios de limpieza, aseo y fumigación	2019-05-12	200000	OPN°624/2019	412		\N
941	56	61/19	1	001-003-0000236	145	Honorarios profesionales - Lic. José Manuel Peralta A. - Contador - CLS - Enero, Febrero / 2019	2019-05-12	2600000	OPN°623/2019	412		\N
944	57	61/19	1	2001-001-0000033	142	Contratación de Personal en Salud/Cayetano Soto Fariña/ Enfermero/Mes Enero y Febrero 2019\t	2019-04-17	2200000	OPN°349/2019	413		\N
945	57	61/19	1	001-001-0000081	142	Contratación de Personal de Salud/Franca M. Monges Cañete/Enfermera/Mes  Enero y Febrero 2019\t	2019-04-17	2200000	OPN°351/2019	413		\N
946	57	61/19	3	001-001-000307	142	Contratación de Personal de Salud/Fredy Hirán Cristaldo Báez/Técnico en Obstetricia/Mes  Enero y Febrero 2019\t	2019-04-17	2000000	OPN°351/2019	413		\N
947	57	61/19	1	001-001-0000026	142	Contratación Personal en Salud/Rodolfo Reinaldo Ramirez Denis/Enfermero/Mes  Enero y Febrero 2019\t	2019-04-17	2200000	OPN°352/2019	413		\N
940	56	61/19	3	001-001-000344	144	Jornales  - Leticia Natalia Verdecchia Mora - Asistentente Administrativo, área de Laboratorio y Odontología - Enero, Febrero y Marzo 2019	2019-05-12	1100000	OPN° 622/2019	412		\N
948	57	61/19	1	001-001-0000035	142	Contratación de Personal de Salud/Ross Maris Rodas Fariña/Enfermera/Mes  Enero y Febrero 2019\t\n	2019-04-17	1000000	OPN°353/2019	413		\N
949	57	61/19	1	001-001-000308	144	Contratación de Personal de Salud/Teresa J. Fariña de Cardozo/Enfermera/Mes  Enero y Febrero 2019\t	2019-04-17	2200000	OPN°354/2019	413		\N
950	57	61/19	3	001-001-000308	144	Jornales/Reinaldo Báez González/Limpiador de Patio/Mes  Enero y Febrero 2019\t	2019-04-17	1000000	OPN°355/2019	413		\N
951	57	61/19	1	001-001-0000265	266	Consultora, aseosias e investigaciones	2019-04-17	2200000	OPN N° 356/2019	413		\N
953	58	61/19	3	001-001-0000347	142	CPS Lourdes Natividad Rolon Melgarejo Auxiliar Enfermeria ENERO-FEBRERO Puesto de Salud. P. Benegas\t	2019-04-17	1200000	OPN°526/2019	414		\N
954	58	61/19	3	001-001-0000348	142	CPS Sandra Lucia Sanchez Almeida Auxiliar Enfermeria ENERO-FEBRERO Centro de Salud Yataity\t	2019-04-17	1200000	OPN°527/2019	414		\N
955	58	61/19	3	001-001-0000349	142	CPS Gladys Concepcion Armoa Villaverde Auxiliar Enfermeria ENERO-FEBRERO Centro de Salud Yataity\t	2019-04-17	1200000	OPN°528/2019	414		\N
956	58	61/19	3	001-001-0000353	142	CPS Nestor Villaverde Espinola Auxiliar Enfermeria ENERO-FEBRERO Centro de Salud Yataity\t\n	2019-05-18	940000	OPN°529/2019	414		\N
961	58	61/19	1	001-001-0000254	212	AGUA	2019-04-22	1100000	OPN°535/2019	414		\N
992	73	61/19	1	001-001-0000201	142	CPS- ODONTOLOGO - JORGE DERLYS GOMEZ MIRANDA - P.S. BOQUERON BORJA - FEBRERO 2019, MARZO 2019, ABRIL 2019.\t\n	2019-06-14	3000000	OPN°19/2019	418		\N
962	58	61/19	1	001-001-0000255	266	CONSULTORIA, ASESORIA E INVESTIGACION\t\n	2019-04-17	2400000	OPN°534/2019	414		\N
952	58	61/19	3	001-001-0000346	142	CPS Nancy Basilia Roa Roa Auxiliar Enfermeria ENERO-FEBRERO Puesto de Salud. P. Benegas\t	2019-04-17	1200000	OPN°525/2019	414		\N
957	58	61/19	1	001-003-0000061	142	CPS Gabriela Careaga Arias Bioquimica ENERO-FEBRERO Centro de Salud Yataity\t\n\n	2019-04-17	1000000	OPN°2019/2019	414		\N
958	58	61/19	1	001-001-0000027	142	CPS Maria Jose Insaurralde Alvarenga Odontologa ENERO-FEBRERO Centro de Salud Yataity\t\n	2019-05-23	1966667	OPN°531/2019	414		\N
959	58	61/19	1	001-001-0000354	142	CPS Cesar Gustavo Paredes Benitez Tecnico Fisioterapeuta ENERO-FEBRERO Centro de Salud Yataity\t\t	2019-05-18	510000	OPN°532/2019	414		\N
960	58	61/19	3	001-001-0000352	144	JORNAL Gustavo Adolfo Davalos Goiris Limpiador de patio ENERO-FEBRERO Centro de Salud Yataity\t	2019-05-18	1200000	OPN°533/2019	414		\N
964	58	61/19	1	001-001-0000539	334	Producto de papel y carton	2019-04-23	100000	OPN°536/2019	414		\N
965	58	61/19	1	001-001-0000539	341	ELEMENTOS DE LIMPIEZA	2019-04-23	147000	OPN°536/2019	414		\N
966	58	61/19	1	001-001-0000209	341	ELEMENTOS DE LIMPIEZA	2019-04-27	383333	OPN°538/2019	414		\N
967	58	61/19	1	001-001-0062270	352	Productos farmacéuticos y medicinales\t\n	2019-05-18	130000	OPN°537/2019	414		\N
968	58	61/19	1	001-001-0062270	358	Útiles y materiales médico-quirúrgicos y de laboratorio\t\n	2019-05-18	323000	OPN°537/2019	414		\N
969	60	61/19	1	001-001-0000132	142	CPS-Teresa Duarte -Lic en enfermeria-mes de mar/abr/2019\t	2019-05-08	2400000	OPN°300/2019	416		\N
970	60	61/19	1	001-001-0000132	142	CPS-Perla Maidana-Lic en enfermeria-mes de mar/abr/2019\t\n	2019-05-08	2400000	OPN°301/2019	416		\N
971	60	61/19	3	001-001-0000476	142	CPS - Aldo Sanchez - Aux. Enfermeria-Mes de mar/abr/2019\t	2019-05-08	2000000	OPN°302/2019	416		\N
972	60	61/19	3	001-001-0000477	142	CPS - Carlos Medina - Aux. Enfermeria-Mes de mar/abr/2019\t	2019-05-08	2000000	OPN°304/2019	416		\N
973	60	61/19	3	001-001-0000478	144	Jornales - Sonia Martinez- Aux. Adminsitrativo-Mes de mar/abr/2019\t	2019-05-08	2000000	OPN°304/2019	416		\N
974	60	61/19	3	001-001-0000479	144	Jornales - Carmen Pereira- Aux. Administrativo-Mes de mar/abr/2019\t\n	2019-05-08	2000000	OPN°305/2019	416		\N
975	60	61/19	3	001-001-0000480	144	Jornales - Estelbina Silvero- Personal de Limpieza -Mes de mar/abr/2019\t	2019-05-08	1200000	OPN° 306/2019	416		\N
976	60	61/19	3	001-001-0000481	144	Jornales - Modesta Dominguez-Personal de Limpieza - Mes de mar/abr/2019\t	2019-05-08	1200000	OPN°307/2019	416		\N
977	60	61/19	3	001-001-0000482	144	Jornales -Ilda Vera-Personal de limpieza-mes de mar/abr/2019\t	2019-05-08	1200000	OPN°308/2019	416		\N
978	60	61/19	1	001-001-000035	145	Honorarios Profesionales- Sergio Rojas-Administrador-Mes de mar/abr/2019\t	2019-05-08	3600000	OPN°309/2019	416		\N
979	60	61/19	1	001-002-0001692	341	ELEMENTOS DE LIMPIEZA	2019-05-18	5000000	OPN°310/2019	416		\N
980	61	61/19	1	001-001-000062	142	C.P.S. Nancy Rebeca Marzal - Enfermera, Febrero - Marzo 2019.-\n	2019-04-26	1600000	OPN°615/2019	417		\N
981	61	61/19	1	001-001-000061	142	C.P.S. Ramona Concepción Britez  -  Enfermera,  Febrero - Marzo 2019.-	2019-04-26	1600000	OPN°616/2019	417		\N
982	61	61/19	1	001-001-000062	142	C.P.S. María Dominguez - Enfermera,  Febrero - Marzo 2019.-\n	2019-04-29	1520000	OPN°617/2019	417		\N
983	61	61/19	1	001-001-00003	142	C.P:S Juliana Dávalos - Enfermera,  Febrero - Marzo 2019.-	2016-04-26	1600000	OPN°618/2019	417		\N
984	61	61/19	1	001-001-00003	142	C.P.S. Liz Yohana Troche -  Enfermera,  Febrero - Marzo 2019.-\n	2019-04-26	1600000	OPN°619/2019	417		\N
985	61	61/19	1	001-001-00002	142	C.P.S. Rosa Cardozo -  Enfermera,  Febrero - Marzo 2019.-	2019-04-26	1600000	OPN°620/2019	417		\N
986	61	61/19	3	001-001-0000551	144	JORNALES. Elva Aissa Mora - Aux Administrativa, Febrero - Marzo 2019.-\n	2019-04-29	1600000	OPN°621/2019	417		\N
987	61	61/19	3	001-001-0000553	144	JORNALES. Ramona Teresita Romero - Fichera, Febrero - Marzo 2019.-	2019-04-29	1200000	OPN°622/2019	417		\N
988	61	61/19	3	001-001-0000554	144	JORNALES. Analía Alejandra Troche - Fichera,  Febrero - Marzo 2019.-	2019-04-29	1200000	OPN°623/2019	417		\N
989	61	61/19	3	001-001-0000556	144	JORNALES. Veronica Bogado - Limpiadora,  Febrero - Marzo 2019.-	2019-04-29	1000000	OPN°624/2019	417		\N
990	61	61/19	3	001-001-0000558	144	JORNALES. María Cecilia Andino - Fichera,  Febrero - Marzo 2019.-\n	2019-04-29	400000	OPN°624/2019	417		\N
991	61	61/19	1	001-001-0017767	333	PRODUCTOS E IMPRESIONES DE ARTES GRAFICAS	2019-05-20	50000	OPN°626/2019	417		\N
993	73	61/19	3	001-001-0000182	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - FEBRERO 2019, MARZO 2019\t\n	2019-06-07	2000000	OPN°16/2019	418		\N
994	73	61/19	3	001-001-0000183	144	JORNALES - AUX. ADMINISTRATIVO DE ADMISION - MARIA ELIZABET BRITEZ GOMEZ - P.S. VALLE PE - BORJA - ABRIL 2019.\t	2019-06-07	1000000	OPN°17/2019	418		\N
995	73	61/19	3	001-001-0000177	144	JORNALES - MUCAMA - MAGDALENA BRIZUELA BAEZ - P.S. VALLE PE BORJA -  FEBRERO 2019, MARZO 2019, ABRIL 2019.\t	2019-06-07	1800000	OPN°12/2019	418		\N
996	73	61/19	3	001-001-0000178	144	JORNALES - MUCAMA - MARIA CONCEPCION BAREIRO DE OJEDA - P.S. BOQUERON BORJA - FEBRERO 2019, MARZO 2019, ABRIL 2019.\t\n	2019-06-07	1800000	OPN°13/2019	418		\N
997	73	61/19	3	001-001-0000180	144	JORNALES - CHOFER DE AMBULANCIA - OSCAR BRITEZ GOMEZ - P.S. VALLE PE - BORJA - FEBRERO 2019, MARZO 2019\t\n	2019-06-07	1900001	OPN°14/2019	418		\N
998	73	61/19	3	001-001-0000181	144	JORNALES - CHOFER DE AMBULANCIA - OSCAR BRITEZ GOMEZ - P.S. VALLE PE - BORJA - ABRIL 2019.\t\n	2019-06-07	900001	OPN°15/2019	418		\N
999	73	61/19	1	001-001-0000079	266	CONSULTORIAS, ASESORIAS E INVESTIGACION\t	2019-06-07	1820000	OPN°18/2019	418		\N
1000	59	61/19	1	001-001-0000017	142	C P S. Pago de salario a Aux. de Pre-Consulta  mes de Mayo/2019 Laura Rocio Gimenez 	2019-05-30	1500000	OPN°001/2019	415		\N
1001	59	61/19	3	001-00-0000470	144	Jornales mes de Mayo/2019 a Operadora de Bioestadistica Dina Mariel Barrios	2019-05-30	1000000	OPN°002/2019	415		\N
1002	59	61/19	3	001-001-0000471	144	 Jornales  mes de Mayo/2019 a Encargada de servicios generales Constancia Benitez	2019-05-30	500000	OPN°003/2019	415		\N
1003	59	61/19	1	001-001-0000172	145	Hon. Profesionales mes Mayo/2019 a Adm. Vanessa Ovelar Prieto.	2019-05-30	1500000	OPN°004/2019	415		\N
1004	59	61/19	1	001-001-0000960	242	Mantenimientos y reparaciones menores de edificios y locales	2019-06-06	2862250	OPN°010/2019	415		\N
1005	59	61/19	2	001-001-0049461	268	Servicios de comunicaciones	2019-04-27	160000	RECIBO N° 48302 FECHA13/06/2019, OPN° 009/2019	415		\N
1006	59	61/19	2	001-001-0050389	268	Servicios de comunicaciones	2019-05-27	160000	RECIBO N° 48392 FECHA 13/06/2019, OPN° 009/2019	415		\N
1007	59	61/19	1	001-003-0001727	311	Alimentos para personas	2019-06-03	300000	OPN° 006/2019	415		\N
1008	59	61/19	1	001-001-0011064	331	Papel de escritorio y cartón	2019-06-06	116000	OPN°007/2019	415		\N
1009	59	61/19	1	001-001-0011064	334	Productos de papel y cartón	2019-06-06	80000	OPN°007/2019	415		\N
1010	59	61/19	1	00-001-0000427	334	Productos de papel y cartón	2019-06-04	136000	OPN°008/2019	415		\N
1011	59	61/19	1	001-001-0000427	341	ELEMENTOS DE LIMPIEZA	2019-06-04	236000	OPN°008/2019	415		\N
1012	59	61/19	1	001-001-0000428	341	ELEMENTOS DE LIMPIEZA	2019-06-04	144000	OPN°008/2019	415		\N
1013	59	61/19	1	001-001-0011064	342	Útiles de escritorio, oficina y enseñanza.	2019-06-06	126000	OPN°007/2019	415		\N
1014	59	61/19	1	001-001-0000427	343	Utiles y materiales eléctricos\n	2019-06-04	90000	OPN°008/2019	415		\N
1015	59	61/19	1	001-001-000350	352	Productos farmacéuticos y medicinales	2019-05-28	592500	OPN°005/2019	415		\N
1016	59	61/19	1	004-007-0087248	352	Productos farmacéuticos y medicinales	2019-06-11	2351385	OPN°011/2019	415		\N
1017	59	61/19	1	003-011-0001596	352	Productos farmacéuticos y medicinales	2019-06-12	2400000	OPN°012/2019	415		\N
1018	59	61/19	1	001-001-0000427	354	Insecticidas, fumigantes y otros.	2019-06-04	44000	OPN°008/2019	415		\N
1019	59	61/19	1	001-001-000350	358	Útiles y materiales médico quirúrgicos y de laboratorio.	2019-05-28	510000	OPN°005/2019	415		\N
1020	59	61/19	1	001-001-0000428	361	Combustibles	2019-06-04	105000	OPN°008/2019	415		\N
\.


--
-- Name: rendiciones_gastos_rendicion_seq; Type: SEQUENCE SET; Schema: aplicacion; Owner: postgres
--

SELECT pg_catalog.setval('aplicacion.rendiciones_gastos_rendicion_seq', 1020, true);


--
-- Data for Name: rendiciones_verificacion; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.rendiciones_verificacion (verificacion, rendicion, estado, comentario) FROM stdin;
13	133	2	
211	333	2	
385	516	2	
14	134	2	
15	135	2	
16	136	2	
17	137	2	
18	138	2	
19	139	2	
20	140	2	
21	141	2	
22	142	2	
23	143	2	
24	144	2	
26	146	2	
28	148	2	
32	152	2	
34	154	2	
36	156	2	
38	158	2	
39	159	2	
40	160	2	
42	162	2	
44	164	2	
46	166	2	
47	167	2	
48	168	2	
49	169	2	
50	170	2	
51	171	2	
52	172	2	
53	173	2	
54	174	2	
55	175	2	
56	176	2	
57	177	2	
58	178	2	
59	179	2	
60	180	2	
61	181	2	
62	182	2	
63	183	2	
64	184	2	
65	185	2	
66	186	2	
67	187	2	
68	188	2	
69	189	2	
70	190	2	
71	191	2	
72	192	2	
73	193	2	
74	194	2	
75	195	2	
76	196	2	
744	887	2	
77	197	2	
78	198	2	
79	199	2	
80	200	2	
81	201	2	
82	202	2	
83	203	2	
84	204	2	
85	205	2	
86	206	2	
87	207	2	
88	208	2	
89	209	2	
90	212	2	
91	213	2	
92	214	2	
93	215	2	
94	216	2	
95	217	2	
96	218	2	
99	221	2	
100	222	2	
102	224	2	
105	227	2	
108	230	2	
110	232	2	
112	234	2	
113	235	2	
114	236	2	
115	237	2	
116	238	2	
117	239	2	
118	240	2	
119	241	2	
120	242	2	
121	243	2	
122	244	2	
123	245	2	
124	246	2	
125	247	2	
126	248	2	
127	249	2	
158	280	2	
163	285	2	
165	287	2	
167	289	2	
168	290	2	
169	291	2	
171	293	2	
172	294	2	
174	296	2	
176	298	2	
178	300	2	
189	311	2	
191	313	2	
192	314	2	
194	316	2	
196	318	2	
198	320	2	
27	147	2	
30	150	2	
31	151	2	
33	153	2	
35	155	2	
41	161	2	
43	163	2	
45	165	2	
97	219	2	
98	220	2	
101	223	2	
104	226	2	
106	228	2	
107	229	2	
109	231	2	
111	233	2	
128	250	2	
129	251	2	
130	252	2	
131	253	2	
132	254	2	
149	271	2	
150	272	2	
181	303	2	
135	257	2	
153	275	2	
199	321	2	
197	319	2	
195	317	2	
193	315	2	
190	312	2	
187	309	2	
186	308	2	
185	307	2	
184	306	2	
182	304	2	
134	256	2	
180	302	2	
179	301	2	
177	299	2	
175	297	2	
170	292	2	
166	288	2	
164	286	2	
162	284	2	
161	283	2	
160	282	2	
159	281	2	
157	279	2	
156	278	2	
155	277	2	
152	274	2	
151	273	2	
148	270	2	
147	269	2	
146	268	2	
145	267	2	
144	266	2	
143	265	2	
142	264	2	
141	263	2	
139	261	2	
138	260	2	
137	259	2	
136	258	2	
214	336	2	
218	340	2	
219	343	2	
220	344	2	
221	348	2	
222	349	2	
223	350	2	
224	351	2	
225	352	2	
226	353	2	
227	354	2	
228	355	2	
231	358	2	
232	359	2	
234	361	2	
237	364	2	
239	366	2	
242	369	2	
245	372	2	
247	374	2	
248	375	2	
249	376	2	
250	377	2	
251	378	2	
252	379	2	
253	380	2	
254	381	2	
255	382	2	
256	383	2	
257	384	2	
258	385	2	
259	386	2	
260	387	2	
261	388	2	
262	389	2	
263	390	2	
264	391	2	
265	392	2	
266	393	2	
267	394	2	
268	395	2	
269	396	2	
270	397	2	
271	398	2	
272	399	2	
273	400	2	
274	401	2	
275	402	2	
276	403	2	
277	404	2	
278	405	2	
279	406	2	
280	407	2	
281	408	2	
282	409	2	
283	410	2	
284	411	2	
285	412	2	
286	413	2	
287	414	2	
288	415	2	
289	416	2	
290	417	2	
291	418	2	
292	419	2	
293	420	2	
294	421	2	
295	422	2	
296	423	2	
297	424	2	
298	425	2	
299	426	2	
300	427	2	
301	428	2	
302	429	2	
303	434	2	
304	435	2	
305	436	2	
306	437	2	
307	438	2	
308	439	2	
309	440	2	
310	441	2	
311	442	2	
312	443	2	
313	444	2	
314	445	2	
315	446	2	
316	447	2	
317	448	2	
318	449	2	
319	450	2	
320	451	2	
321	452	2	
322	453	2	
323	454	2	
324	455	2	
325	456	2	
326	457	2	
327	458	2	
328	459	2	
329	460	2	
330	461	2	
331	462	2	
332	463	2	
333	464	2	
334	465	2	
335	466	2	
336	467	2	
337	468	2	
338	469	2	
339	470	2	
340	471	2	
341	472	2	
342	473	2	
343	474	2	
344	475	2	
345	476	2	
346	477	2	
347	478	2	
348	479	2	
349	480	2	
350	481	2	
351	482	2	
352	483	2	
353	484	2	
354	485	2	
355	486	2	
356	487	2	
357	488	2	
358	489	2	
359	490	2	
360	491	2	
361	492	2	
362	493	2	
363	494	2	
364	495	2	
365	496	2	
366	497	2	
367	498	2	
368	499	2	
369	500	2	
370	501	2	
371	502	2	
372	503	2	
373	504	2	
374	505	2	
375	506	2	
376	507	2	
377	508	2	
378	509	2	
379	510	2	
380	511	2	
381	512	2	
382	513	2	
383	514	2	
384	515	2	
230	357	2	
233	360	2	
235	362	2	
236	363	2	
238	365	2	
240	367	2	
241	368	2	
246	373	2	
217	339	2	
216	338	2	
215	337	2	
213	335	2	
212	334	2	
210	332	2	
209	331	2	
208	330	2	
206	328	2	
205	327	2	
204	326	2	
203	325	2	
202	324	2	
201	323	2	
200	322	2	
244	371	2	
386	517	2	
387	518	2	
388	519	2	
389	520	2	
390	521	2	
391	522	2	
392	523	2	
393	524	2	
394	525	2	
395	526	2	
396	527	2	
397	528	2	
398	529	2	
399	530	2	
400	531	2	
401	532	2	
402	533	2	
403	534	2	
404	535	2	
405	538	2	
406	539	2	
407	540	2	
408	541	2	
409	542	2	
410	543	2	
411	544	2	
412	545	2	
413	546	2	
414	547	2	
415	548	2	
416	549	2	
417	550	2	
419	552	2	
421	554	2	
423	556	2	
425	558	2	
427	560	2	
429	562	2	
430	563	2	
431	564	2	
432	565	2	
433	566	2	
435	568	2	
436	569	2	
437	570	2	
438	571	2	
439	572	2	
440	573	2	
441	574	2	
442	575	2	
443	576	2	
444	577	2	
445	578	2	
446	579	2	
447	580	2	
448	581	2	
449	582	2	
450	583	2	
451	584	2	
452	585	2	
453	586	2	
454	587	2	
455	588	2	
456	589	2	
457	590	2	
458	591	2	
459	592	2	
460	593	2	
461	595	2	
462	597	2	
463	598	2	
464	599	2	
465	600	2	
466	601	2	
467	602	2	
468	603	2	
469	604	2	
470	605	2	
471	606	2	
472	607	2	
473	608	2	
474	609	2	
475	610	2	
476	611	2	
477	612	2	
478	613	2	
479	614	2	
25	145	2	
29	149	2	
229	356	2	
243	370	2	
480	617	2	
481	618	2	
482	619	2	
483	620	2	
484	621	2	
485	622	2	
486	623	2	
487	624	2	
488	625	2	
489	626	2	
490	627	2	
491	628	2	
492	629	2	
493	630	2	
494	631	2	
495	632	2	
496	633	2	
497	634	2	
498	635	2	
499	636	2	
500	637	2	
501	638	2	
502	639	2	
503	640	2	
504	641	2	
505	642	2	
506	643	2	
507	644	2	
508	645	2	
509	646	2	
510	647	2	
511	648	2	
512	649	2	
513	650	2	
514	651	2	
515	652	2	
516	653	2	
517	654	2	
518	655	2	
519	656	2	
520	657	2	
521	658	2	
522	659	2	
523	660	2	
524	661	2	
525	662	2	
526	663	2	
527	664	2	
528	665	2	
529	666	2	
530	668	2	
531	669	2	
532	670	2	
533	671	2	
534	672	2	
535	673	2	
536	674	2	
537	675	2	
538	676	2	
539	677	2	
540	678	2	
541	679	2	
542	680	2	
543	681	2	
544	682	2	
545	683	2	
546	684	2	
547	685	2	
548	686	2	
549	687	2	
550	688	2	
551	689	2	
552	690	2	
553	691	2	
554	692	2	
555	693	2	
556	694	2	
557	695	2	
558	696	2	
559	697	2	
560	698	2	
561	699	2	
562	700	2	
563	701	2	
564	702	2	
565	703	2	
424	557	2	
426	559	2	
428	561	2	
420	553	2	
418	551	2	
566	704	2	
567	705	2	
568	706	2	
569	707	2	
570	708	2	
571	709	2	
572	710	2	
573	711	2	
574	712	2	
575	713	2	
576	714	2	
577	715	2	
578	716	2	
579	718	2	
580	720	2	
581	721	2	
582	722	2	
583	723	2	
584	724	2	
585	725	2	
586	726	2	
587	727	2	
588	728	2	
589	729	2	
590	730	2	
591	731	2	
592	732	2	
593	734	2	
594	735	2	
595	736	2	
596	737	2	
597	738	2	
598	740	2	
599	741	2	
600	742	2	
601	743	2	
602	744	2	
603	745	2	
604	746	2	
605	747	2	
606	748	2	
607	749	2	
608	750	2	
609	751	2	
610	752	2	
611	753	2	
612	754	2	
613	755	2	
614	756	2	
615	757	2	
616	758	2	
617	759	2	
618	760	2	
619	761	2	
620	762	2	
621	763	2	
622	764	2	
623	765	2	
624	766	2	
625	767	2	
626	768	2	
627	769	2	
628	770	2	
629	771	2	
630	772	2	
631	773	2	
632	774	2	
103	225	2	
633	775	2	
133	255	2	
634	776	2	
635	777	2	
636	778	2	
637	779	2	
638	780	2	
639	781	2	
640	782	2	
641	783	2	
642	784	2	
643	785	2	
644	786	2	
645	787	2	
646	788	2	
647	789	2	
648	791	2	
649	792	2	
650	793	2	
651	794	2	
652	795	2	
653	796	2	
654	797	2	
655	798	2	
656	799	2	
657	800	2	
658	801	2	
659	802	2	
660	803	2	
661	804	2	
662	805	2	
663	806	2	
664	807	2	
665	808	2	
666	809	2	
667	810	2	
668	811	2	
669	812	2	
670	813	2	
671	814	2	
672	815	2	
673	816	2	
674	817	2	
675	818	2	
676	819	2	
677	820	2	
679	822	2	
680	823	2	
681	824	2	
682	825	2	
683	826	2	
684	827	2	
685	828	2	
686	829	2	
687	830	2	
688	831	2	
689	832	2	
690	833	2	
691	834	2	
692	835	2	
693	836	2	
694	837	2	
695	838	2	
696	839	2	
697	840	2	
698	841	2	
699	842	2	
700	843	2	
701	844	2	
702	845	2	
703	846	2	
704	847	2	
705	848	2	
706	849	2	
707	850	2	
708	851	2	
709	852	2	
710	853	2	
711	854	2	
712	855	2	
713	856	2	
714	857	2	
715	858	2	
716	859	2	
717	860	2	
718	861	2	
719	862	2	
720	863	2	
721	864	2	
722	865	2	
723	866	2	
422	555	2	
207	329	2	
188	310	2	
173	295	2	
154	276	2	
140	262	2	
724	867	2	
725	868	2	
726	869	2	
727	870	2	
728	871	2	
729	872	2	
730	873	2	
731	874	2	
732	875	2	
733	876	2	
734	877	2	
735	878	2	
736	879	2	
737	880	2	
738	881	2	
739	882	2	
740	883	2	
741	884	2	
742	885	2	
743	886	2	
745	888	2	
746	889	2	
747	890	2	
748	891	2	
749	892	2	
750	893	2	
751	894	2	
752	895	2	
753	896	2	
754	897	2	
755	898	2	
756	899	2	
757	900	2	
758	901	2	
759	902	2	
760	903	2	
761	904	2	
762	905	2	
763	906	2	
764	907	2	
765	908	2	
766	909	2	
767	910	2	
768	911	2	
769	912	2	
770	913	2	
771	914	2	
772	915	2	
773	916	2	
774	917	2	
775	918	2	
776	919	2	
777	920	2	
778	921	2	
779	922	2	
780	923	2	
781	924	2	
782	925	2	
783	926	2	
784	927	2	
785	928	2	
786	929	2	
787	930	2	
788	931	2	
789	932	2	
790	933	2	
791	934	2	
792	935	2	
793	936	2	
794	937	2	
795	938	2	
796	939	2	
797	940	2	
798	941	2	
799	942	2	
800	944	2	
801	945	2	
802	946	2	
803	947	2	
804	948	2	
805	949	2	
806	950	2	
807	951	2	
808	952	2	
809	953	2	
810	954	2	
811	955	2	
812	956	2	
813	957	2	
814	958	2	
815	959	2	
816	960	2	
817	961	2	
818	962	2	
820	964	2	
821	965	2	
822	966	2	
823	967	2	
824	968	2	
825	969	2	
826	970	2	
827	971	2	
828	972	2	
829	973	2	
830	974	2	
831	975	2	
832	976	2	
833	977	2	
834	978	2	
835	979	2	
836	980	2	
837	981	2	
838	982	2	
839	983	2	
840	984	2	
841	985	2	
842	986	2	
843	987	2	
844	988	2	
845	989	2	
846	990	2	
847	991	2	
848	992	2	
849	993	2	
850	994	2	
851	995	2	
852	996	2	
853	997	2	
854	998	2	
855	999	2	
856	1000	2	
857	1001	2	
858	1002	2	
859	1003	2	
860	1004	2	
861	1005	2	
862	1006	2	
863	1007	2	
864	1008	2	
865	1009	2	
866	1010	2	
867	1011	2	
868	1012	2	
869	1013	2	
870	1014	2	
871	1015	2	
872	1016	2	
873	1017	2	
874	1018	2	
875	1019	2	
876	1020	2	
\.


--
-- Name: rendiciones_verificacion_verificacion_seq; Type: SEQUENCE SET; Schema: aplicacion; Owner: postgres
--

SELECT pg_catalog.setval('aplicacion.rendiciones_verificacion_verificacion_seq', 876, true);


--
-- Data for Name: resoluciones; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.resoluciones (id, numero, estado) FROM stdin;
1	4545/19	2
2	456/19	1
4	789/19	2
5	456444/20	2
6	7777/77	2
9	254/19	2
7	168/19	2
8	61/19	2
\.


--
-- Data for Name: resoluciones_estados; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.resoluciones_estados (estado, descripcion) FROM stdin;
1	Aprobado
2	Con Reparo
\.


--
-- Name: resoluciones_id_seq; Type: SEQUENCE SET; Schema: aplicacion; Owner: postgres
--

SELECT pg_catalog.setval('aplicacion.resoluciones_id_seq', 9, true);


--
-- Data for Name: tipos_comprobantes; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.tipos_comprobantes (tipo_comprobante, descripcion) FROM stdin;
1	Factura Contado
2	Factura Credito
3	Auto Factura
4	Recibo de Dinero
\.


--
-- Data for Name: tipos_transferencias; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.tipos_transferencias (tipo_transferencia, descripcion) FROM stdin;
1	Ordinario
2	Extraordinario
3	Por Cumplimiento de Meta
\.


--
-- Data for Name: transferencias_fondos; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.transferencias_fondos (transferencia, resolucion_numero, saldo_anterior, origen_ingreso, recibo_numero, comprobante_numero, deposito_fecha, total_depositado, consejo, total_rendicion, estado, tipo_transferencia, estado_transferencia) FROM stdin;
53	61/19	0	151	39	22052903	2019-04-30	15000000	408	14900000	1	1	1
54	61/19	0	151	16	178236	2019-04-26	15000000	409	14790827	1	1	1
59	61/19	0	151	49	4146138	2019-05-03	15000000	415	14913135	1	1	1
55	61/19	0	151	128	100960	2019-05-09	15000000	410	15000000	1	1	1
58	61/19	0	151	71	4146014	2019-04-16	15000000	414	15000000	1	1	1
51	61/19	1500000	151	92	100956	2019-05-09	30000000	406	28400000	1	1	1
56	61/19	1000000	151	69	100959	2019-05-09	15000000	412	15000000	1	1	1
47	61/19	0	151	281	1588153	2019-05-02	130000000	401	125091003	1	1	1
93	254/19	72650	151	51	4404673	2019-08-16	15000000	415	14955999	1	1	1
77	168/19	209173	151	17	100328	2019-06-14	15000000	409	14610000	1	1	1
60	61/19	0	151	11	4146139	2019-05-03	25000000	416	25000000	1	1	1
52	61/19	0	151	117	103270	2019-05-02	15000000	407	14500000	1	1	1
57	61/19	0	151	38	1562551	2019-04-17	15000000	413	15000000	1	1	1
84	254/19	350000	151	95	450958	2019-08-16	30000000	406	29450000	1	1	1
63	168/19	0	151	12	4442390	2019-06-03	25000000	416	25000000	1	1	1
89	254/19	0	151	33	706110	2019-08-06	15000000	411	15000000	1	1	1
78	168/19	86865	151	50	4395002	2019-06-18	15000000	415	14656650	1	1	1
85	254/19	500000	151	119	450959	2019-08-16	15000000	407	15000000	1	1	1
72	168/19	3100000	151	93	350445	2019-06-25	30000000	406	26850000	1	1	1
91	254/19	0	151	43	100676	2019-08-02	15000000	413	15000000	1	1	1
96	254/19	1479998	151	65	4993073	2019-08-14	15000000	418	14832000	1	1	1
92	254/19	0	151	73	4901683	2019-08-01	15000000	414	15000000	1	1	1
94	254/19	0	151	59	4993004	2019-08-07	25000000	416	25000000	1	1	1
88	254/19	0	151	131	450960	2019-08-16	15000000	410	15000000	1	1	1
62	168/19	30000	151	21	3937336	2019-06-03	15000000	417	14479999	1	1	1
46	61/19	0	151	18	480115	2019-04-16	17000000	402	17000000	1	1	1
49	61/19	0	151	119	33230	2019-04-16	15000000	404	15000000	1	1	1
69	168/19	100000	151	41	23872527	2019-06-05	15000000	408	14351131	1	1	1
64	168/19	0	151	72	3937335	2019-06-03	15000000	414	15000000	1	1	1
48	61/19	0	151	27	4354365	2019-05-07	30000000	403	27298000	1	1	1
67	168/19	0	151	32	706109	2019-06-04	15000000	411	15000000	1	1	1
50	61/19	0	151	87	1591321	2019-05-02	20000000	405	18000000	1	1	1
61	61/19	0	151	20	4146061	2019-04-24	15000000	417	14970000	1	1	1
73	61/19	0	151	63	4146012	2019-04-16	15000000	418	14220002	1	1	1
79	254/19	5084496	151	284	2041359	2019-08-16	65000000	401	64678105	1	1	1
80	254/19	0	151	20	480117	2019-08-02	17000000	402	17000000	1	1	1
98	254/19	48869	151	44	23872963	2019-08-06	15000000	408	14900000	1	1	1
70	168/19	500000	151	118	350444	2019-06-25	15000000	407	0	1	1	1
81	254/19	0	151	29	4811756	2019-08-07	30000000	403	0	1	1	1
95	254/19	550001	151	23	4404649	2019-08-08	15000000	417	14841366	1	1	1
45	168/19	779998	151	64	4764550	2019-06-15	15000000	418	14300000	1	1	1
97	168/19	0	151	129	350446	2019-06-25	15000000	410	15000000	1	1	1
83	254/19	2000000	151	40	2092261	2019-08-07	20000000	405	0	1	1	1
82	254/19	0	151	121	346222	2019-08-02	15000000	404	15000000	1	1	1
75	168/19	2702000	151	28	4538498	2019-06-19	30000000	403	29766500	1	1	1
99	254/19	599173	151	18	450892	2019-08-14	15000000	409	14650003	1	1	1
74	168/19	0	151	19	480116	2019-06-05	17000000	402	17000000	1	1	1
71	168/19	0	151	90	87273	2019-06-14	20000000	405	20000000	1	1	1
76	168/19	0	151	120	320624	2019-05-31	15000000	404	15000000	1	1	1
65	168/19	0	151	40	101577	2019-05-31	15000000	413	15000000	1	1	1
90	254/19	500000	151	71	450962	2019-08-16	15000000	412	14900000	1	1	1
66	168/19	0	151	70	350447	2019-06-25	15000000	412	14500000	1	1	1
\.


--
-- Data for Name: verificacion_estado; Type: TABLE DATA; Schema: aplicacion; Owner: postgres
--

COPY aplicacion.verificacion_estado (estado, descripcion) FROM stdin;
0	sin estado
1	corregir
2	Verificado ok
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.roles (rol, nombre_rol) FROM stdin;
9	SysAdmin
56	rol consejo 100
57	rol consejo 101
58	rol consejos de salud
50	MSPadmin
60	rol consejo 1110
61	rol consejo 1115
62	rol consejo 1105
63	rol consejo 1114
64	rol consejo 1117
65	rol verificador
66	rol consejo 111
67	rol_publico
68	rol consejo 102
69	rol consejo 103
70	rol consejo 104
71	rol consejo 105
72	rol consejo 106
73	rol consejo 107
74	rol consejo 108
75	rol consejo 109
76	rol consejo 110
78	rol consejo 112
79	rol consejo 200
80	rol consejo 201
81	rol consejo 202
82	rol consejo 203
83	rol consejo 204
84	rol consejo 205
85	rol consejo 206
86	rol consejo 207
87	rol consejo 208
88	rol consejo 209
89	rol consejo 210
90	rol consejo 211
91	rol consejo 212
92	rol consejo 213
93	rol consejo 214
94	rol consejo 215
95	rol consejo 216
96	rol consejo 217
97	rol consejo 218
98	rol consejo 219
99	rol consejo 220
100	rol consejo 221
101	rol consejo 300
102	rol consejo 301
103	rol consejo 302
104	rol consejo 303
105	rol consejo 304
106	rol consejo 305
107	rol consejo 306
108	rol consejo 307
109	rol consejo 308
110	rol consejo 309
111	rol consejo 310
112	rol consejo 311
113	rol consejo 312
114	rol consejo 313
115	rol consejo 314
116	rol consejo 315
117	rol consejo 316
118	rol consejo 317
119	rol consejo 318
120	rol consejo 319
121	rol consejo 320
122	rol consejo 400
123	rol consejo 401
124	rol consejo 402
125	rol consejo 403
126	rol consejo 404
127	rol consejo 405
128	rol consejo 406
129	rol consejo 407
130	rol consejo 408
131	rol consejo 409
133	rol consejo 410
134	rol consejo 411
135	rol consejo 412
136	rol consejo 413
137	rol consejo 414
138	rol consejo 415
139	rol consejo 416
140	rol consejo 417
141	rol consejo 418
142	rol consejo 500
143	rol consejo 501
144	rol consejo 502
145	rol consejo 503
146	rol consejo 504
147	rol consejo 505
148	rol consejo 506
149	rol consejo 507
150	rol consejo 508
151	rol consejo 509
152	rol consejo 510
153	rol consejo 511
154	rol consejo 512
155	rol consejo 513
156	rol consejo 514
157	rol consejo 515
158	rol consejo 516
159	rol consejo 517
160	rol consejo 518
161	rol consejo 519
162	rol consejo 520
163	rol consejo 521
164	rol consejo 522
165	rol consejo 600
166	rol consejo 601
167	rol consejo 602
168	rol consejo 603
169	rol consejo 604
170	rol consejo 605
171	rol consejo 606
172	rol consejo 607
173	rol consejo 608
174	rol consejo 609
175	rol consejo 610
176	rol consejo 611
177	rol consejo 700
178	rol consejo 701
179	rol consejo 702
180	rol consejo 703
181	rol consejo 704
182	rol consejo 705
183	rol consejo 706
184	rol consejo 707
185	rol consejo 708
186	rol consejo 709
187	rol consejo 710
188	rol consejo 711
189	rol consejo 712
190	rol consejo 713
191	rol consejo 714
192	rol consejo 715
193	rol consejo 716
194	rol consejo 717
195	ro consejo 718
196	rol consejo 719
197	rol consejo 720
198	rol consejo 721
199	rol consejo 722
200	rol consejo 723
201	rol consejo 724
202	rol consejo 725
203	rol consejo 726
204	rol consejo 727
205	rol consejo 728
206	rol consejo 729
207	rol consejo 730
208	rol consejo 800
209	rol consejo 801
210	rol consejo 802
211	rol consejo 803
212	rol consejo 804
213	rol consejo 805
214	rol consejo 806
215	rol consejo 807
217	rol consejo 808
216	rol consejo 809
218	rol consejo 810
219	rol consejo 900
220	rol consejo 901
221	rol consejo 902
222	rol consejo 903
223	rol consejo 904
224	rol consejo 905
225	rol consejo 906
226	rol consejo 907
227	rol consejo 908
228	rol consejo 909
229	rol consejo 910
230	rol consejo 911
231	rol consejo 912
232	rol consejo 913
233	rol consejo 914
234	rol consejo 915
235	rol consejo 916
236	rol consejo 917
237	rol consejo 918
238	rol consejo 1000
239	rol consejo 1001
240	rol consejo 1002
241	rol consejo 1003
242	rol consejo 1004
243	rol consejo 1005
244	rol consejo 1006
245	rol consejo 1007
246	rol consejo 1008
247	rol consejo 1009
248	rol consejo 1010
249	rol consejo 1011
250	rol consejo 1012
251	rol consejo 1013
252	rol consejo 1014
253	rol consejo 1015
254	rol consejo 1016
255	rol consejo 1017
256	rol consejo 1018
257	rol consejo 1019
258	rol consejo 1020
259	rol consejo 1021
260	rol consejo 1022
261	rol consejo 1100
262	rol consejo 1101
263	rol consejo 1102
264	rol consejo 1103
265	rol consejo 1104
266	rol consejo 1106
267	rol consejo 1107
268	rol consejo 1108
270	rol consejo 1109
271	rol consejo 1111
273	rol consejo 1112
274	rol consejo 1113
275	rol consejo 1116
279	rol consejo 1118
280	rol consejo 1119
281	rol consejo 1200
282	rol consejo 1202
283	rol consejo 1203
284	rol consejo 1204
285	rol consejo 1205
286	rol consejo 1206
287	rol consejo 1207
288	rol consejo 1208
289	rol consejo 1209
290	rol consejo 1210
291	rol consejo 1211
292	rol consejo 1212
293	rol consejo 1213
294	rol consejo 1214
295	rol consejo 1215
296	rol consejo 1216
297	rol consejo 1201
298	rol consejo 1300
299	rol consejo 1301
300	rol consejo 1302
301	rol consejo 1303
302	rol consejo 1304
303	rol consejo 1305
304	rol consejo 1400
305	rol consejo 1401
306	rol consejo 1402
307	rol consejo 1403
308	rol consejo 1404
309	rol consejo 1405
310	rol consejo 1406
311	rol consejo 1407
312	rol consejo 1408
313	rol consejo 1409
314	rol consejo 1410
315	rol consejo 1411
316	rol consejo 1412
317	rol consejo 1413
318	rol consejo 1414
319	rol consejo 1415
320	rol consejo 1500
321	rol consejo 1501
322	rol consejo 1502
323	rol consejo 1503
325	rol consejo 1505
326	rol consejo 1506
327	rol consejo 1507
328	rol consejo 1508
324	rol consejo 1504
329	rol consejo 1600
330	rol consejo 1601
331	rol consejo 1602
332	rol consejo 1603
333	rol consejo 1700
334	rol consejo 1701
335	rol consejo 1702
336	rol consejo 1703
337	rol consejo 1704
338	rol usuario 1800
\.


--
-- Name: roles_rol_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.roles_rol_seq', 338, true);


--
-- Data for Name: roles_x_selectores; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.roles_x_selectores (id, rol, selector) FROM stdin;
3	9	36
4	9	2
5	9	3
7	9	38
8	43	38
10	9	40
11	9	41
12	9	42
13	9	44
14	9	46
2	9	11
18	9	49
19	9	50
20	9	51
21	54	44
23	54	1
26	9	54
31	58	36
33	58	56
34	58	57
35	9	57
36	50	57
37	50	53
38	50	54
39	50	36
40	50	59
41	50	61
42	65	57
43	65	36
44	65	62
45	50	63
46	65	63
47	58	63
48	58	61
49	50	65
50	65	65
51	58	65
52	58	66
53	50	66
54	65	66
58	67	36
59	67	63
60	67	65
62	67	66
\.


--
-- Name: roles_x_selectores_id_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.roles_x_selectores_id_seq', 67, true);


--
-- Data for Name: selectores; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.selectores (id, superior, descripcion, ord, link) FROM stdin;
2	1	Usuarios	1	/sistema/usuario/
3	1	Roles	2	/sistema/rol/
11	1	Acceso menu	3	/sistema/selector/
36	1	Salir	7	/
1	0	Sistema	10	
54	52	Departamentos 	50	/aplicacion/departamento/
57	1	Cambio de password	4	/aplicacion/cambiopass/
55	52	Transferencia de los Fondos de Equidad	30	/aplicacion/transferenciafondo/
56	52	Rendición de los Fondos de Equidad	40	/aplicacion/rendiciongasto/
59	52	Transferencia de los Fondos de Equidad	60	/aplicacion/transferenciafondo-a/
62	52	Verificacion	70	/aplicacion/rendicionverificacion/
53	52	Consejos de salud	10	/aplicacion/consejosalud/
64	0	Consultas	5	
52	0	Movimientos	11	
63	64	Transferencias de los Fondos de Equidad	10	/publico/consulta001/
60	0	Anexo B09	4	
65	64	Resumen General por Objeto de Gastos	20	/publico/consulta002/
66	64	Resumen de Saldos de las Transferencias	30	/publico/consulta003/
61	60	Planilla de Ejecución de Ingresos y Egresos	10	/reporte/rendiciongasto/
\.


--
-- Name: selectores_id_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.selectores_id_seq', 68, true);


--
-- Data for Name: selectores_x_webservice; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.selectores_x_webservice (id, selector, wservice) FROM stdin;
\.


--
-- Name: selectores_x_webservice_id_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.selectores_x_webservice_id_seq', 1, false);


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.usuarios (usuario, cuenta, clave, token_iat) FROM stdin;
79	usu1115	88312ef17169bea63ae2f9d3f249795e	\N
78	usu1110	a97d1408329fa9ade5efb36fc79e6365	1570054042930
81	usu1114	02da3152e75daddafb423e5f2c3d7ecd	\N
80	usu1105	d09e1c8bfcf6cfc8b4e8d3a06ef07b6b	1570813839656
143	usu402	e6b48448e33783c820a3d14f93f7a4c0	1575639991075
75	usu100	1ba995ed8bed6fdbd5aab705b042e565	1574802296522
122	usu302	f4abe9d925472b6afa0f671421ccfd67	\N
119	usu221	978c32c7e0446b8bdba7131565ff51b7	1574299124767
97	usu200	b3f032b44bc69666af9151d771b65565	1574298271389
139	usu319	1f2c079744e162d7eb1ae57b4037c8da	\N
98	usu201	457cd22110172f9f026bdac8b3d1253b	\N
99	usu202	f5a366263b4a333de5adc61b9f8836cf	\N
137	usu317	2a946c343fa9086ff362d0c22689d90b	\N
100	usu203	a1ebdff181819b1ec209ddf9d7a8c0af	\N
126	usu306	e3ac516e5a712431c3ff8469e36e624b	\N
101	usu204	aaa65c76041d58738ceba7b2ee2dfbc1	\N
102	usu205	f56c553579531d70f72ad2bf3b303723	\N
129	usu309	b2e2d234577429bff26fc88bf7412de9	\N
132	usu312	367285bf65a5f1b35b9525f58f6e744e	\N
103	usu206	63ee03f4016607b98b3faebd64abf349	\N
123	usu303	f1b7db146f6aa3acc72ac7a8fbcc9e39	\N
160	usu500	d9bd1b4adbd521381001602daa4e3940	\N
86	usu102	00bc7cd8898dc39188eb33c260ed6eb1	\N
166	usu506	ec3e959f92cf8da1e7e1f77f7956c5fe	\N
164	usu504	f6e4c7ac140bcc8512c2e4e09bdab81a	\N
104	usu207	8f74b849e03ca880ecf767d92a2eeafc	\N
162	usu502	3efce68e33d3db4628b749365752ea0e	\N
88	usu104	82c2180488ad0500cac9014e61a99e26	\N
135	usu315	1a2b3fa2e8c416ceb06692488dbcc9bd	\N
105	usu208	ef1812d51a1f200a1886f73d5d73dff9	\N
89	usu105	d5803544167220c7832ee5e58aae6667	\N
163	usu503	3c99beb7187f46b40f54a59ffaad7b39	\N
90	usu106	2fd0a4a9b150647c3aef59ea5b29a41e	\N
106	usu209	2e45ecb28beb4011d7150a45397f3664	\N
91	usu107	488d47f3c0167a762d57f17a24eb70d4	\N
120	usu300	59c8ccaf332d54f026e8da714b08f5fe	\N
92	usu108	4adc519cfdfe12a6e9426b7feeb9486c	\N
107	usu210	ed21790e75af4f31bb22927748e16350	\N
140	usu320	d89a98441da0e356be0218c3e552a802	\N
93	usu109	6aa0ec94d17d82259099cd09965ad1db	\N
84	usu111	8b138e700f26eb63f06d38ce13c2d3de	1574793774293
108	usu211	4473deca1140db7e2953962274fe91e2	\N
94	usu110	7bbae671ed0504dd992bb45cdcbe1ed4	\N
130	usu310	09359fe455efe5510d99cf7f2489a50f	\N
109	usu212	937afe16ef8f76ec9cd088704f922a2a	\N
87	usu103	3ef024a71e44eb4d2e704d4832fbd1ec	1574298135363
96	usu112	2fa23f806cef0e7e71ea4f5894fa34b3	\N
124	usu304	ee6a7fd528d3371bd3e6ba844eef248e	\N
110	usu213	854b2d59a852804b611b3478927787e4	\N
127	usu307	033ff36d42711acb101029cd60a26ece	\N
138	usu318	c13b675cea6be668c8034671d15c7072	\N
111	usu214	01283c674e4578ea3505ab2d8d798904	\N
118	usu221	978c32c7e0446b8bdba7131565ff51b7	1574298978642
133	usu313	ee7f9a64b3cc9335da469b3029c27248	\N
112	usu215	717ec7eca2e5aa782dd0909f160595b5	\N
141	usu400	6148a69ba47e5fad4fea9354b8393521	\N
113	usu216	e1bd7f79bddfb609f89de750723c9907	\N
183	usu600	bd7ff677112a87404924da68ef4393a6	\N
121	usu301	bd45227b02e2f1f40ca53d7dd7f31cc4	\N
136	usu316	b475b0d706d196bd7fecef5ac7af9710	\N
114	usu217	fdf4bb6594b08d34ecfe1e70bb57e975	\N
82	usu1117	7804655e68965afcbbcc08f3d4e98323	1574374758840
115	usu218	dc2417f55253c24f2ad1116ca09366db	\N
76	usu101	588ae558dad04ab7717e82219f7d9d4c	1574793133591
116	usu219	293002cf92434c2cf97ba9a59e6d0cd4	\N
125	usu305	0ef0aae81d8330e95ded99fd35fef670	\N
117	usu220	a1b1f4bad589c5d55c363e1f4e877849	\N
128	usu308	b9146891d6a81e781e85609ad11ee208	\N
131	usu311	9c091acf743e1b62c0c8bc71bd4945ff	\N
134	usu314	0142c8d6bbf429d4f719d4f29230409e	\N
165	usu505	cf3742388b903a4eaad8c2c4e1954c6d	\N
161	usu501	49db6b0e8b1070f2e97fa7602b24c6eb	\N
167	usu507	925a41fa808a5001bbb42d0fb8febd49	\N
168	usu508	37f9f0b309d2c9b765ab4435969f159c	\N
169	usu509	b9b2feaef00ce78a5ffe8b50bf2da712	\N
170	usu510	62223bdca08f5b0c8c90a61b895e46e2	\N
171	usu511	fd762981fc067383daa6fe232bc5d842	\N
172	usu512	fda919151377581fa9415f3e4b602a66	\N
173	usu513	6acfb09dc96f3f6b0d3e47250fdc17ba	\N
174	usu514	8f947fa748e6bb5502fca4cd4776112e	\N
175	usu515	deedb1b7cb29118fb30c32cf016565ae	\N
176	usu516	36c52ba82c4b2a84c141053ff3fa1667	\N
147	usu406	b905fa7dc414c914bb45cecb0772de71	1575284892086
157	usu416	ccd4926cd8bb66a557360654c73b334d	1575315050419
152	usu411	7df863e10d5a67b18fa27a5941634806	1574964900985
144	usu403	692ba7d5b9d11ead47bd636301491b0d	1575035487526
83	verifi01	139ebd8e5780e019c651f489426ca764	1574969526536
159	usu418	c3178893c8d998978ce7fa75715b3015	1575316308572
146	usu405	ef19bcdfd4591d7bb0626356dfdd3b43	1575038872833
153	usu412	55a5ad73daf0616432e91d36c1e2fd6d	1575309384261
145	usu404	0276ed47e668949e3268236d58aa93df	1574964702957
155	usu414	37a6967063a5981e45b30e88297ecfae	1575313728592
1	root	4d186321c1a7f0f354b297e8914ab240	1575829363272
149	usu408	61cb99d778a541260694eecf878a508c	1575299986245
148	usu407	ab01acffd1fd3b0033fa0c900a84dbcc	1575299426303
151	usu410	2f3fc8d4ae293a7a9bbe4810862a3229	1575307862980
158	usu417	9980054bbfa8a12955cf3ecacfb7cae0	1575315814930
150	usu409	4e517b4d92cb24150637655720e521ee	1575305795850
177	usu517	07849219687758c120ee502b9d1a06aa	\N
178	usu518	f78ce8ef74597c66b6e0738700807534	\N
179	usu519	e97a706a8452c6c8190da99acfdbc032	\N
180	usu520	2a08ea875686a078894e00240c026a99	\N
181	usu521	f83c41c27f5751b59c0c8a886f4b07d5	\N
182	usu522	8a172b490f59e200e501ddba77664864	\N
270	usu1014	d5c36cd7179f6088874350b72e962ac8	\N
271	usu1015	d7580566e76bd149dc67cfca4cb7a522	\N
279	usu1100	2ef6f43dcf71fd22f834acfd084d3ea1	\N
256	usu1000	f3ea0a06e82d1b472147887a01d500dd	\N
257	usu1001	2db28130ef3ecfd30db248b265ad9b97	\N
258	usu1002	7d8fb758de9ad96331e9dc232fd72d78	\N
259	usu1003	c8db40cc5f9871d723c507c50346e4e6	\N
226	usu800	495911fbe54ab50b1fd2481c532ccfdd	\N
227	usu801	a5e183091bf525d538014c51fd878883	\N
228	usu802	3be1430b5dba5345f96d1a56eeaa4e9a	\N
229	usu803	2f723cf7eeb57236fa1a24497e378998	\N
184	usu601	ac6b1d01e7fed149b133521817eac15c	\N
185	usu602	9b4dc3d75104dd718c79622dbcb6eb21	\N
186	usu603	7249aab042fa82722f0d19182a21cec4	\N
187	usu604	4fa61c3dee05ea9443104be011f15ad1	\N
188	usu605	4996dbdd01cdecdd67b8f3908ed476c5	\N
189	usu606	33734f5a175ef0c7a923157db3ef9556	\N
190	usu607	a2208b506f0b1a7884b80604046f65cc	\N
191	usu608	eb692b8982ba932ede9af9fcbd6ef624	\N
192	usu609	474198362a338e1477a5f97e2e50ca8a	\N
193	usu610	5dbe35dff9aa21ee0cce82e89dfc574a	\N
194	usu611	7bbac696e4a0538e6d624324e34e99cb	\N
230	usu804	a707c196e544192b4fdac17bffdfb6ef	\N
232	usu806	85666c1229ca8def4a800d03b4c650b2	\N
233	usu807	c0b1930de98776cfc9ddbaebfec69571	\N
234	usu808	f3ac97924dec80b821a6a298df46d973	\N
235	usu809	360240c60bfd17a30cd993b948961d10	\N
236	usu810	8039f25090c2089e8c1c89ff7a0f4075	\N
260	usu1004	33759ca210abc41e2bd8fbc0fe2ed212	\N
261	usu1005	e3a9a7aea684c344ac28981ccc03cffb	\N
262	usu1006	83f5fd74a8e309cff5d5f51891f83e5f	\N
263	usu1007	6e1345472a02abbf8ac661363c2c8967	\N
231	usu805	46c4e187da914f60c6922f050958bec5	1574354684805
237	usu900	d2057e5d7394e1c1f3a0d1e6ee51dd75	\N
238	usu901	c5e687d7d6aa1b18a02d21b3cdc1e90e	\N
239	usu902	722cfbe0ca584c2528e54947a1db0fd4	\N
240	usu903	74b986de3afe1977c4fe2111daf4ef33	\N
241	usu904	e68e1e176429d44e0a113a4ff85a3302	\N
242	usu905	7eea94538721c64fc82421b77b7280e7	\N
243	usu906	13db151bac20187571862ae40eac6538	\N
244	usu907	3211e1ed0f134192f2ae7b6e8c7389a1	\N
245	usu908	dd582ff837c78d5d753331aab4aec1ea	\N
246	usu909	b528935c1bcd192e4bc66f45af5bffdc	\N
247	usu910	4f6ead10eabb0152d89baa86b6e1de00	\N
248	usu911	755dadbcc8d4b608cfa4a11933f4a854	\N
249	usu912	bc13a8e8d54890c00dbef5cbcbd22664	\N
250	usu913	03d24f59624288370067a2e4263b40dd	\N
251	usu914	349c541e8db4b069c2be15615c672394	\N
252	usu915	1da0ff3457b4d6c5d7a47ce494f30263	\N
195	usu700	1f8cac77b2bcb253c9227af9faa2b4fb	\N
196	usu701	bf8c85f5ae9c22dc2a4649d27a055de9	\N
197	usu702	6511d58d22a1467fefc7c87e583b7117	\N
198	usu703	061534df7bc9df9c4a0dc2f0d21f588e	\N
199	usu704	e423cab87283a0f94eb6d5a477d93fad	\N
200	usu705	ee16527654c59f878320211cb7fd40cb	\N
201	usu706	9617c575d16d3358ef84376eb5bf7910	\N
202	usu707	10a8d41073522099e539afb1d0566692	\N
203	usu708	9248978ce12ef90a38edc5ccbef5bdfa	\N
204	usu709	7be322c20f72726a84f07c37f9645570	\N
205	usu710	f733aaceea0a98daadf1051d19e9a7a9	\N
206	usu711	62b1ebc372ce2751fcb93bc98d1379c1	\N
207	usu712	d7c36c9cebf4d63bb946889d875f1ae1	\N
208	usu713	709969e2c61cbd2630a339e9ba6abe57	\N
209	usu714	3e1ae60f54dcc6a8b1c095218ed4f0d7	\N
210	usu715	6dbecabc1b9112b5dc3859d8a5191600	\N
211	usu716	597f3086b31245dbe09110527752bcff	\N
212	usu717	6fe56ea71e83e9b89741100ff41edd06	\N
213	usu718	2346e1f4b2b9e1f28489dc854f991946	\N
214	usu719	194f293a73cc3026489437922fd3774e	\N
215	usu720	3b2f58e24c327417e6006a86b693037a	\N
216	usu721	20785a7cca08183b3cb2c2fc4e8fd661	\N
217	usu722	9ac3d8d9c3b3d74f6468dd44112fcd78	\N
218	usu723	ea91b3ecaee097254ff768a0df9c79f9	\N
219	usu724	6009fb689faa9e11c93a6f0c9cb6c21d	\N
253	usu916	4dcec7a85ffd8d4ca48f8acddab2ff02	\N
220	usu725	e5bd5395d6e98723954e0be0209cb3b6	\N
221	usu726	93827d20c8cdaf1dd5339f336ff0a6b2	\N
222	usu727	3332498dbefebf9cb270af19a136743c	\N
223	usu728	22bd6d4002ace62a0514e17e931ee268	\N
224	usu729	5eece1b9148c9f4c12c31d06e98a557f	\N
225	usu730	06de2ce885f0800c11e4c4e027df2127	\N
254	usu917	ab8f9c9129ef35f88c4f538a1f2ead74	\N
255	usu918	a83fe3a789bd43a86f874f6ac65f24d5	\N
264	usu1008	234610131c95b3e7e1ddde4b9f6ac2b9	\N
265	usu1009	a32563a72c4a7faaaf0693f2cb01d7e1	\N
266	usu1010	c21dc649136ad1999d097b187c267a6e	\N
272	usu1016	f97e48289c2d53c2776b04cb1c0f85ad	\N
267	usu1011	ae9ab3ea9f6955d84c41b3c06808ad3a	\N
268	usu1012	edf751db52139a2dbdd27d85c8d27238	\N
269	usu1013	46709f7c8a78b92a11a0a57ec42d75a8	\N
273	usu1017	1c8ed58a0bdfbfd594bb51fb37a49743	\N
274	usu1018	729afd7e06664616326ad9e9e4e2b7d4	\N
275	usu1019	b45f6a4a631c25ccc4877766602d409f	\N
276	usu1020	ba174a41c5f40d846e6d4ffcfae9ece8	\N
277	usu1021	d8ebb762bd25a25fce2fb26058f5ad16	\N
278	usu1022	d47d373c6e8fd6063192ba98bac59df2	\N
280	usu1101	25fc9154712522f135e0d77e3d5f8c40	\N
281	usu1102	6d4bc3df835121e12df7b39050781734	\N
282	usu1103	08f9629e9c760392170ef4326106e54a	\N
283	usu1104	6c0d69af5382b0a91283ad0809f20d8f	\N
284	usu1106	e9811e7e0b1805144b6f9fb123421d43	\N
285	usu1107	645c80e96ca5f5744527c4b3bda9f23f	\N
286	usu1108	a43e0e38f111fa181c50a80390b110a4	\N
287	usu1109	9a5fa1d9464e75674f6d1d16f5a5bddd	\N
288	usu1111	4f73ccf05ef95bc2aff21f980e739230	\N
289	usu1112	da6e5922c0ed29709b4aed9eaf14c530	\N
290	usu1113	446451b4e40b010573ca1a0b2b4af537	\N
293	usu1118	eac8fc2a6b20ea20c8d6bae02cd47c1b	\N
294	usu1119	0f11d7fb442949898e4faf2f007aabf6	\N
335	usu1415	463dd0aa4817300bf3843e53d6bfa630	\N
342	usu1506	d87198b86589424b6424db0c3f77259f	\N
343	usu1507	9e5ecebc77d6999b1f2ae518667f9d21	\N
344	usu1508	1ad0f9423711274b0e89e82a7e40a2f7	\N
352	usu1703	e1192c112c1bb5c60205b6f965eb3c05	\N
353	usu1704	7f17d4515da0f4c7573c81d76e26aabc	\N
77	mspadmin	c20ad4d76fe97759aa27a0c99bff6710	1575885431760
355	verifi02	6d0c9fa8d065e205120b24259fe0a0ea	\N
356	verifi03	790815d65e9c5d257cdc52d280c0e71e	\N
142	usu401	7b700e4402b547ac0987b8671e9b817e	1575885888745
358	verifi05	fb10807acbef63c31d822fc2b662cf2d	\N
359	verifi06	e9e825e68f7e2fbb4cc9e75f27000e1f	\N
360	verifi07	225a00e33d393bbe5d5c9d79398f9e77	\N
354	usu1800	bd8884c22c3c567ec39b110fff6a34e5	\N
361	verifi08	19f097e25bd89739c80fc8ffce17261e	\N
291	usu1116	f7ee1c031fb7acdbdc84cfefd36005eb	1574374393239
295	usu1200	43f02e9b77a460173cfd87299972bf3e	\N
296	usu1201	a7b3c04d6acf3440f4d1a99408e6192c	\N
297	usu1201	a7b3c04d6acf3440f4d1a99408e6192c	\N
298	usu1202	c9b72a444df998d6765c42899807c1bf	\N
299	usu1203	0e8bbd6fa6f982d9b8dbcad1c4b9d2aa	\N
300	usu1204	391cc97cb28af7da5322acfb74e94183	\N
301	usu1205	e766ba091d2fc92876b8303204bb85c4	\N
302	usu1206	71dee0bee60983b3beede95792bac404	\N
303	usu1207	66375b3956aab14eb428045d11aa63a5	\N
304	usu1208	6b44c3df4b606ed10ec2be24432c5f12	\N
305	usu1209	4171e2a75382d850734863340f72db70	\N
306	usu1210	12da498b03128d33491d780f81b64fb4	\N
307	usu1211	32c9e69301d28ab0004224c2fdc28933	\N
308	usu1212	1fe3637b3c6b08a6aefa08e0cd22611f	\N
309	usu1213	44f6ab56d8cfb6614022e27cf17176d3	\N
310	usu1214	e98820725f5ce5dfb5df3f228101a300	\N
311	usu1215	dca21c5ef3578c4d5696c7f6cf959fb9	\N
312	usu1216	35506dd091a81a83f4f1bb9e79410326	\N
345	usu1600	44e0d7cd88ba41bbf0c0a9cb00c9bff9	\N
346	usu1601	0cfcd7441e04983b113d9149f0de5634	\N
347	usu1602	b2bf248c418663c1223355cce080e80e	\N
348	usu1603	4cf0fb70ea5a79476412a4836d20fabc	\N
362	verifi09	cdcee5dac0c9ba31675636e3fb368402	\N
363	verifi10	2455d13de893cf8e7b678b0a41250464	\N
364	verifi11	cfee0b97f5cda8add27475b85185c4a6	\N
365	verifi12	072ae96181aab97355b4b7ad29ce848c	\N
366	verifi13	74473f6bd04485e1f17fab2e251438f4	\N
367	verifi14	da18928882d22b8390e0bd7082bc4a1b	\N
368	verifi15	2eae652b56c48db5160847147e3f5ae8	\N
349	usu1700	a1902ecb94ad29110189cb5e5518c437	\N
336	usu1500	9fc35cd429f7eb4d30c25369488adbd2	\N
337	usu1501	813a73ea8f4d56bcbc9847779a731f42	\N
338	usu1502	789bf7d593ffcc4090b87a782aafccd1	\N
339	usu1503	352faa6ade5310001eb8f8dce6cb684f	\N
357	verifi04	bd27c14c64bc77c75b0129e105b9d028	1575829304720
320	usu1400	3e5ebd33b2d80cc159f97c4ec7c08916	\N
321	usu1401	3dd963ff69627a8307dd133232d57d8a	\N
322	usu1402	fdaecf22a92dec046b87469832cec24d	\N
323	usu1403	bc0e1ce860f7f2669ee5457ef29a9f39	\N
324	usu1404	12fa16095bf553c683f440ad8d4b17ec	\N
325	usu1405	e9b21ee8f7ef35ad63a731a460cfbfa0	\N
326	usu1406	c727184cf2df29a8c38aa5fbfbebad3c	\N
327	usu1407	5e2fcc208b8b583e53a49b07a0ee61e5	\N
328	usu1408	cae06026099057e77d283dcdde6621a3	\N
329	usu1409	b71bb44943f0926590464207d963f56d	\N
313	usu1300	a1c2aa54ad41e57f5872e4f3f1d045c2	\N
314	usu1301	549b040159ad97b09b9274b7ab8d6967	\N
315	usu1302	eb4f29f2fd1bb10739c433c1983f3e30	\N
316	usu1304	52c09c9650a1874aabfbafb9439f05f4	\N
317	usu1305	811d6d1e0395bf3ba73781ca472a61c7	\N
318	usu1315	95824ae615f2183c0a38d79d3981fe88	\N
319	usu1305	811d6d1e0395bf3ba73781ca472a61c7	\N
330	usu1410	ae4ccfd72663726218f70fd69b823d26	\N
340	usu1504	5cd856c259a5bc28ec4e6c4be09be212	\N
331	usu1411	3aafe66ffa909c3d26fd33bb4f1b1e11	\N
332	usu1412	00a5fdeb40336729a3fc7e370fdcd632	\N
333	usu1413	b2cdd7eba6e92ec63687de6d4a80655e	\N
334	usu1414	f070f342d872f7f5d99bd7fb322e13cb	\N
341	usu1505	b3d19d463f5d63ab80995488d90b5b40	\N
350	usu1701	40aba4376a79f54d1e165fbad26c49ab	\N
351	usu1702	54d09f15e76450861ef3d842042d68d4	\N
369	verifi16	df54b87eae73e71956bf41c80bd35e08	\N
370	verifi17	b2256a0353df3e1c8b05e6d04d421923	\N
371	verifi18	414348e0bed2cf2d17d0a859445659e4	\N
85	publico	83951dcbdb9d96d2e43b8f20c3663943	1575895309911
156	usu415	525ad0eb4639a26c1d444bd7940bf9f7	1575395181766
154	usu413	720e06d53dae8b6b4444324cd141253d	1575310767079
\.


--
-- Name: usuarios_usuario_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.usuarios_usuario_seq', 371, true);


--
-- Data for Name: usuarios_x_roles; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.usuarios_x_roles (id, usuario, rol) FROM stdin;
140	1	9
179	75	56
180	76	57
181	76	58
182	75	58
183	77	50
184	78	60
185	78	58
186	79	61
187	79	58
188	80	62
189	80	58
190	81	63
191	81	58
194	83	65
195	84	66
196	84	58
197	85	67
198	86	68
199	86	58
200	87	69
201	87	58
202	88	70
203	88	58
204	89	71
205	89	58
206	90	72
207	90	58
208	91	73
209	91	58
210	92	74
211	92	58
212	93	75
213	93	58
214	94	76
215	94	58
218	96	78
219	96	58
220	97	79
221	98	80
222	99	81
223	100	82
224	101	83
225	102	84
226	103	85
227	104	86
228	105	87
229	106	88
230	107	89
231	108	90
232	109	91
233	110	92
234	111	93
235	112	94
236	113	95
237	114	96
239	115	97
240	116	98
241	117	99
242	119	100
243	97	58
244	98	58
245	99	58
246	100	58
247	101	58
248	102	58
249	103	58
250	104	58
251	105	58
252	106	58
253	107	58
254	108	58
255	109	58
256	110	58
257	112	58
258	114	58
259	116	58
260	117	58
261	119	58
262	120	101
263	120	58
264	121	102
265	121	58
266	122	103
267	122	58
268	123	104
269	123	58
270	124	105
271	124	58
272	125	106
273	125	58
274	126	107
275	126	58
276	127	108
277	127	58
278	128	109
279	128	58
280	129	110
281	129	58
282	130	111
283	130	58
284	131	112
285	131	58
286	132	113
287	132	58
288	133	114
289	133	58
290	134	115
291	134	58
292	135	116
293	135	58
294	136	117
295	136	58
296	137	118
297	137	58
298	138	119
299	138	58
300	139	120
301	139	58
302	140	121
303	140	58
304	141	122
305	141	58
306	142	123
307	142	58
308	143	124
309	143	58
310	144	125
311	144	58
312	145	126
313	145	58
314	146	127
315	146	58
316	147	128
317	147	58
318	148	129
319	148	58
320	149	130
321	149	58
322	150	131
323	150	58
324	151	133
325	151	58
326	152	134
327	152	58
328	153	135
329	153	58
330	154	136
331	154	58
332	155	137
333	155	58
334	156	138
335	156	58
336	157	139
337	157	58
338	158	140
339	158	58
340	159	141
341	159	58
342	160	142
343	160	58
344	161	143
345	161	58
346	162	144
347	162	58
348	163	145
349	163	58
350	164	146
351	164	58
352	165	147
353	165	58
354	166	148
355	166	58
356	167	149
357	167	58
358	168	150
359	168	58
360	169	151
361	169	58
362	170	152
363	170	58
364	171	153
365	171	58
366	172	154
367	172	58
368	173	155
369	173	58
370	174	156
371	174	58
372	175	157
373	175	58
374	176	158
375	176	58
376	177	159
377	177	58
378	178	160
379	178	58
380	179	161
381	179	58
382	180	162
383	180	58
384	181	163
385	181	58
386	182	164
387	182	58
388	183	165
389	183	58
390	184	166
391	184	58
392	185	167
393	185	58
394	186	168
395	186	58
396	187	169
397	187	58
398	188	170
399	188	58
400	189	171
401	189	58
402	190	172
403	190	58
404	191	173
405	191	58
406	192	174
407	192	58
408	193	175
409	193	58
410	194	176
411	194	58
412	195	177
413	195	58
414	196	178
415	196	58
416	197	179
417	197	58
418	198	180
419	198	58
420	199	181
421	199	58
422	200	182
423	200	58
424	201	183
425	201	58
426	202	184
427	202	58
428	203	185
429	203	58
430	204	186
431	204	58
432	205	187
433	205	58
434	206	188
435	206	58
436	207	189
437	207	58
438	208	190
439	208	58
440	209	191
441	209	58
442	210	192
443	210	58
444	211	193
445	211	58
446	212	194
447	212	58
448	213	195
449	213	58
450	214	196
451	214	58
452	215	197
453	215	58
454	216	198
455	216	58
456	217	199
457	217	58
458	218	200
459	218	58
460	219	201
461	219	58
462	220	202
463	220	58
464	221	203
465	221	58
466	222	204
467	222	58
468	223	205
469	223	58
470	224	206
471	224	58
472	225	207
473	225	58
474	226	208
475	226	58
476	227	209
477	227	58
478	228	210
479	228	58
480	229	211
481	229	58
482	230	212
483	230	58
484	231	213
485	231	58
486	232	214
487	232	58
488	233	215
489	233	58
490	234	217
491	234	58
492	235	216
493	235	58
494	236	218
495	236	58
496	237	219
497	237	58
498	238	220
499	238	58
500	239	221
501	239	58
502	240	222
503	240	58
504	241	223
505	241	58
506	242	224
507	242	58
508	243	225
509	243	58
510	244	226
511	244	58
512	245	227
513	245	58
514	246	228
515	246	58
516	247	229
517	247	58
518	248	230
519	248	58
520	249	231
521	249	58
522	250	232
523	250	58
524	251	233
525	251	58
526	252	234
527	252	58
528	253	235
529	253	58
530	254	236
531	254	58
532	255	237
533	255	58
534	256	238
535	256	58
536	257	239
537	257	58
538	258	240
539	258	58
540	259	241
541	259	58
542	260	242
543	260	58
544	261	243
545	261	58
546	262	244
547	262	58
548	263	245
549	263	58
550	264	246
551	264	58
552	265	247
553	265	58
554	266	248
555	266	58
556	267	249
557	267	58
558	268	250
559	268	58
560	269	251
561	269	58
562	270	252
563	270	58
564	271	253
565	271	58
566	272	254
567	272	58
568	273	255
569	273	58
570	274	256
571	274	58
572	275	257
573	275	58
574	276	258
575	276	58
576	277	259
577	277	58
578	278	260
579	278	58
580	279	261
581	279	58
582	280	262
583	280	58
584	281	263
585	281	58
586	282	264
587	282	58
588	283	265
589	283	58
590	284	266
591	284	58
592	285	267
593	285	58
594	286	268
595	286	58
596	287	270
597	287	58
598	288	271
599	288	58
600	289	273
601	289	58
602	290	274
603	290	58
604	291	275
605	291	58
608	293	279
609	293	58
610	294	280
611	294	58
614	82	58
615	82	64
616	295	281
617	295	58
618	297	297
619	297	58
620	298	282
621	298	58
622	299	283
623	299	58
624	300	284
625	300	58
626	301	285
627	301	58
628	302	286
629	302	58
630	303	287
631	303	58
632	304	288
633	304	58
634	305	289
635	305	58
636	306	290
637	306	58
638	307	291
639	307	58
640	308	292
641	308	58
642	309	293
643	309	58
644	310	294
645	310	58
646	311	295
647	311	58
648	312	296
649	312	58
650	313	298
651	313	58
652	314	299
653	314	58
654	315	300
655	315	58
656	316	302
657	316	58
658	319	303
659	319	58
660	320	304
661	320	58
662	321	305
663	321	58
664	322	306
665	322	58
666	323	307
667	323	58
668	324	308
669	324	58
670	325	309
671	325	58
672	326	310
673	326	58
674	327	311
675	327	58
676	328	312
677	328	58
678	329	313
679	329	58
680	330	314
681	330	58
682	331	315
683	331	58
684	332	316
685	332	58
686	333	317
687	333	58
690	334	318
691	334	58
692	335	319
693	335	58
694	336	320
695	336	58
696	337	321
697	337	58
698	338	322
699	338	58
700	339	323
701	339	58
702	340	324
703	340	58
704	341	325
705	341	58
706	342	326
707	342	85
708	343	327
709	343	58
710	344	328
711	344	58
712	345	329
713	345	58
714	346	330
715	346	58
716	347	331
717	347	58
718	348	332
719	348	58
720	349	333
721	349	58
722	350	334
723	350	58
724	351	335
725	351	58
726	352	336
727	352	58
728	353	337
729	353	58
730	354	338
731	354	58
732	355	65
733	356	65
734	357	65
735	358	65
736	359	65
737	360	65
738	361	65
739	362	65
740	363	65
741	364	65
742	365	65
743	366	65
744	367	65
745	368	65
746	369	65
747	370	65
748	371	65
\.


--
-- Name: usuarios_x_roles_id_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.usuarios_x_roles_id_seq', 748, true);


--
-- Data for Name: webservice; Type: TABLE DATA; Schema: sistema; Owner: postgres
--

COPY sistema.webservice (wservice, path, nombre) FROM stdin;
\.


--
-- Name: webservice_wservice_seq; Type: SEQUENCE SET; Schema: sistema; Owner: postgres
--

SELECT pg_catalog.setval('sistema.webservice_wservice_seq', 1, false);


--
-- Name: consejos_salud_cod_key; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.consejos_salud
    ADD CONSTRAINT consejos_salud_cod_key UNIQUE (cod);


--
-- Name: consejos_salud_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.consejos_salud
    ADD CONSTRAINT consejos_salud_pkey PRIMARY KEY (id);


--
-- Name: departamentos_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.departamentos
    ADD CONSTRAINT departamentos_pkey PRIMARY KEY (dpto);


--
-- Name: fondos_equidad_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.transferencias_fondos
    ADD CONSTRAINT fondos_equidad_pkey PRIMARY KEY (transferencia);


--
-- Name: objetos_gastos_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.objetos_gastos
    ADD CONSTRAINT objetos_gastos_pkey PRIMARY KEY (objeto);


--
-- Name: origenes_ingresos_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.origenes_ingresos
    ADD CONSTRAINT origenes_ingresos_pkey PRIMARY KEY (origen);


--
-- Name: rendiciones_gastos_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos
    ADD CONSTRAINT rendiciones_gastos_pkey PRIMARY KEY (rendicion);


--
-- Name: rendiciones_verificacion_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_verificacion
    ADD CONSTRAINT rendiciones_verificacion_pkey PRIMARY KEY (verificacion);


--
-- Name: resoluciones_estados_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.resoluciones_estados
    ADD CONSTRAINT resoluciones_estados_pkey PRIMARY KEY (estado);


--
-- Name: resoluciones_numero_key; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.resoluciones
    ADD CONSTRAINT resoluciones_numero_key UNIQUE (numero);


--
-- Name: resoluciones_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.resoluciones
    ADD CONSTRAINT resoluciones_pkey PRIMARY KEY (id);


--
-- Name: tipo_comprobantes_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.tipos_comprobantes
    ADD CONSTRAINT tipo_comprobantes_pkey PRIMARY KEY (tipo_comprobante);


--
-- Name: tipo_transferencia_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.tipos_transferencias
    ADD CONSTRAINT tipo_transferencia_pkey PRIMARY KEY (tipo_transferencia);


--
-- Name: transferencias_fondos_resolucion_numero_consejo_key; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.transferencias_fondos
    ADD CONSTRAINT transferencias_fondos_resolucion_numero_consejo_key UNIQUE (resolucion_numero, consejo);


--
-- Name: verificacion_estado_pkey; Type: CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.verificacion_estado
    ADD CONSTRAINT verificacion_estado_pkey PRIMARY KEY (estado);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (rol);


--
-- Name: roles_x_selectores_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.roles_x_selectores
    ADD CONSTRAINT roles_x_selectores_pkey PRIMARY KEY (id);


--
-- Name: selectores_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.selectores
    ADD CONSTRAINT selectores_pkey PRIMARY KEY (id);


--
-- Name: selectores_x_webservice_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.selectores_x_webservice
    ADD CONSTRAINT selectores_x_webservice_pkey PRIMARY KEY (id);


--
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (usuario);


--
-- Name: usuarios_x_roles_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios_x_roles
    ADD CONSTRAINT usuarios_x_roles_pkey PRIMARY KEY (id);


--
-- Name: webservice_pkey; Type: CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.webservice
    ADD CONSTRAINT webservice_pkey PRIMARY KEY (wservice);


--
-- Name: t_rendiciones_after_delete; Type: TRIGGER; Schema: aplicacion; Owner: postgres
--

CREATE TRIGGER t_rendiciones_after_delete AFTER DELETE ON aplicacion.rendiciones_gastos FOR EACH ROW EXECUTE PROCEDURE aplicacion.rendiciones_suma_detalles_delete();


--
-- Name: t_rendiciones_after_insert; Type: TRIGGER; Schema: aplicacion; Owner: postgres
--

CREATE TRIGGER t_rendiciones_after_insert AFTER INSERT ON aplicacion.rendiciones_gastos FOR EACH ROW EXECUTE PROCEDURE aplicacion.rendiciones_suma_detalles();


--
-- Name: t_rendiciones_after_update; Type: TRIGGER; Schema: aplicacion; Owner: postgres
--

CREATE TRIGGER t_rendiciones_after_update AFTER UPDATE ON aplicacion.rendiciones_gastos FOR EACH ROW EXECUTE PROCEDURE aplicacion.rendiciones_suma_detalles();


--
-- Name: consejos_salud_dpto_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.consejos_salud
    ADD CONSTRAINT consejos_salud_dpto_fkey FOREIGN KEY (dpto) REFERENCES aplicacion.departamentos(dpto);


--
-- Name: rendiciones_gastos_consejo_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos
    ADD CONSTRAINT rendiciones_gastos_consejo_fkey FOREIGN KEY (consejo) REFERENCES aplicacion.consejos_salud(cod);


--
-- Name: rendiciones_gastos_objeto_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos
    ADD CONSTRAINT rendiciones_gastos_objeto_fkey FOREIGN KEY (objeto) REFERENCES aplicacion.objetos_gastos(objeto);


--
-- Name: rendiciones_gastos_tipo_comprobante_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos
    ADD CONSTRAINT rendiciones_gastos_tipo_comprobante_fkey FOREIGN KEY (tipo_comprobante) REFERENCES aplicacion.tipos_comprobantes(tipo_comprobante);


--
-- Name: rendiciones_gastos_transferencia_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_gastos
    ADD CONSTRAINT rendiciones_gastos_transferencia_fkey FOREIGN KEY (transferencia) REFERENCES aplicacion.transferencias_fondos(transferencia) ON DELETE RESTRICT;


--
-- Name: rendiciones_verificacion_rendicion_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.rendiciones_verificacion
    ADD CONSTRAINT rendiciones_verificacion_rendicion_fkey FOREIGN KEY (rendicion) REFERENCES aplicacion.rendiciones_gastos(rendicion) ON DELETE CASCADE;


--
-- Name: transferencias_fondos_consejo_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.transferencias_fondos
    ADD CONSTRAINT transferencias_fondos_consejo_fkey FOREIGN KEY (consejo) REFERENCES aplicacion.consejos_salud(cod);


--
-- Name: transferencias_fondos_tipo_transferencia_fkey; Type: FK CONSTRAINT; Schema: aplicacion; Owner: postgres
--

ALTER TABLE ONLY aplicacion.transferencias_fondos
    ADD CONSTRAINT transferencias_fondos_tipo_transferencia_fkey FOREIGN KEY (tipo_transferencia) REFERENCES aplicacion.tipos_transferencias(tipo_transferencia);


--
-- Name: usuarios_x_roles_rol_fkey; Type: FK CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios_x_roles
    ADD CONSTRAINT usuarios_x_roles_rol_fkey FOREIGN KEY (rol) REFERENCES sistema.roles(rol);


--
-- Name: usuarios_x_roles_usuario_fkey; Type: FK CONSTRAINT; Schema: sistema; Owner: postgres
--

ALTER TABLE ONLY sistema.usuarios_x_roles
    ADD CONSTRAINT usuarios_x_roles_usuario_fkey FOREIGN KEY (usuario) REFERENCES sistema.usuarios(usuario);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


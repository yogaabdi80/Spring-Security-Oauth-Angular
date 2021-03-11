CREATE TABLE public.oauth_access_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL,
	authentication bytea NULL,
	refresh_token varchar(256) NULL
);

CREATE TABLE public.oauth_client_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL
);

CREATE TABLE public.oauth_refresh_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication bytea NULL
);

CREATE TABLE public.oauth_client_details (
	client_id varchar(256) NOT NULL,
	resource_ids varchar(256) NULL,
	client_secret varchar(256) NULL,
	"scope" varchar(256) NULL,
	authorized_grant_types varchar(256) NULL,
	web_server_redirect_uri varchar(256) NULL,
	autoapprove varchar(256) NULL,
	access_token_validity numeric(11) NULL DEFAULT NULL::numeric,
	refresh_token_validity numeric(11) NULL DEFAULT NULL::numeric,
	authorities varchar(256) NULL DEFAULT NULL::character varying,
	additional_information varchar(4096) NULL DEFAULT NULL::character varying,
	CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
);

INSERT INTO public.oauth_client_details (client_id,resource_ids,client_secret,"scope",authorized_grant_types,web_server_redirect_uri,autoapprove,access_token_validity,refresh_token_validity,authorities,additional_information) VALUES
	 ('mobileapp','belajar','$2a$10$rk7SKH0KPDnMWVmFPenbt.dWDgkFd5R03GKpCKzwfRr3fNTlNt03G','read,write,admin','password,refresh_token','http://localhost:8080/api/halo',NULL,NULL,NULL,NULL,NULL),
	 ('clientspamobile','belajar','$2a$10$rk7SKH0KPDnMWVmFPenbt.dWDgkFd5R03GKpCKzwfRr3fNTlNt03G','read,write,admin','implicit','http://localhost:8080/api/halo',NULL,NULL,NULL,NULL,NULL),
	 ('clientauthcode','belajar','$2a$10$rk7SKH0KPDnMWVmFPenbt.dWDgkFd5R03GKpCKzwfRr3fNTlNt03G','profile_edit,transaksi_menu,input_barang','authorization_code,refresh_token','http://localhost','true',86400,NULL,NULL,NULL);

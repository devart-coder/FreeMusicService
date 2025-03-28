module authserver {
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.boot;
	requires spring.core;
	requires spring.security.web;
	requires spring.security.config;
	requires spring.security.oauth2.authorization.server;
	requires spring.security.crypto;
	requires spring.security.core;
	requires com.nimbusds.jose.jwt;
	requires music_service.*;
}
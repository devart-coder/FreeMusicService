module music_service {
	requires spring.data.jdbc;
	requires spring.context;
	requires spring.boot.autoconfigure;
	requires spring.boot;
	requires spring.web;
	requires spring.beans;
	requires com.fasterxml.jackson.databind;
	requires spring.security.core;
	requires org.slf4j;
	requires spring.security.crypto;
	export src.main.java.*;
}
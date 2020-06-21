package com.diaz.springboot.backend.apirest.auth;

public class JwtConfig {
	
	public static final String LLAVE_SECRETA = "La.clave.de.finanza.backend.1560";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEAy5N9jX+ftfUboJbWh1aNUfp07PdD8sCYbuVUupSZY72rIXRC\r\n" + 
			"hZidQa8j7cMN4i3NlKNHRsG5Mf/W5/RKBP5PKnz5IaZdQWvvdWMTMnLyJuKuPc/X\r\n" + 
			"wt9CSnIF+50fC1lk68EpHsu87nfGN6bT1WCpcU7NoamYPIKSADyer4qemRXUtAkV\r\n" + 
			"zEJzCIsM1H7z7dlJohXALL3Jgi1oZpE2qJliz49ItDK7kA3BsvmbNgj/BkRWgs4x\r\n" + 
			"RtEJ7WvSlouJzShPDGRrvt4GX1ja0K/QJPIRJ8f/8UKp6Rk0/4+znpEgcZ+E9lmX\r\n" + 
			"xA20vPUbG+S9lN3zck9jEE09AJ3zGZ01CpNBYwIDAQABAoIBAQCc6q8YZotg4fHN\r\n" + 
			"svbhkyGTUw7VqTLC5xmYQUUyHGCvtP835jlwaNauTqXPG06UTOHt9H14WNK7a4zn\r\n" + 
			"djGn765t/a669VVknCITxa6CcH+e4ZOVKaQSHa0nXQSnkdDgGqPj5RwhFMIovkIX\r\n" + 
			"m4wlKUDyid+PAP7LhYmsjQ10GdFuBTnfxppfEvShFoY4IfXb1Zxv93q8JD8S20Y/\r\n" + 
			"8TVneb2YSFcrAcZJKkjvEhpNWrj3BVqMKlqOLzszNFDkLSnB9OJ0SSKxR+oDeHZF\r\n" + 
			"hwebSiK7W3tTOzQcXy0/ILIus9KEx67ZODJ2r5hpVE+aZSGRqu7uZn5ckSmHstRw\r\n" + 
			"XeXrFDwhAoGBAOZxqFovKVwQxqV5ltPQlQXbikG08Ggx0SnIypYR7dZxDpeoMekk\r\n" + 
			"5UU2WCdxu5w91G3l2MupxorXmv5TmlKR5OJIReO/J8m1bwOAwJivStkEJoRLGrps\r\n" + 
			"CeuYNwfKa138/t6U4+/ZbnQKMLKXdZSLqKSowgI44LL2Hbr9bcT42rrtAoGBAOIn\r\n" + 
			"DOb25BVDzqoNiDwEo+vaY/PG7KRF3lEWRK9C2stMvFTB2QtIl/HeQn8umOIDk1q1\r\n" + 
			"tGUanA3XFH4bqtNhyOfyItV2Lnd48mgjFr0soQGVN5hRiGhYX4fYq6xraxmOYW1a\r\n" + 
			"ttkzUrHI0XU70gqdloYEvFy2BSXByaOrysgZslOPAoGANhY7I1+qX3d4iGIvamus\r\n" + 
			"boGGw8FKI8p9g0cjdXGj1w8WE7ZgcI4+Wa5VpnxlQliowVqPNc4VUYUlVcgdkn+3\r\n" + 
			"rvyzmq7FEezJr5LKoYuTD7iPqcFO9q5g7bD+SY/S/25gC7H6hdMMv6Ocpm4yJLla\r\n" + 
			"zbrmPktHQfnOJJNS7kZSYEkCgYAWg0SpiVJTO5jLHOW+88dNp0Pj9J2CDOKAt/LG\r\n" + 
			"b6VQL6df2Jw+n09g6Hs+JelaNnjowsXeerOmPQ7xLtfmiwlw7Y3CKi2d/xBChZux\r\n" + 
			"4Ut12ODb7OHjYUjBQTR1yYuz05SnSiyBmrZUtGUFgpr6K43ccYQ+xm/kvlHQVT2u\r\n" + 
			"hcBWyQKBgQCXFGUcm4QEgL5glQZP+MBwMObsI3sSZXUqEGYV913Txbxf7i4HWRSB\r\n" + 
			"VORhOxNjT0WDF4f9Vkg/Lm7sLWFJAYhOikCXbbhfdf36t0an5pQpsk+cEYy3E8Mq\r\n" + 
			"Xgq2+q1O2vrqS2qOpK9zcjLDgcyWZ+mBdlBodwNde4wzVyCZ/pc06A==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy5N9jX+ftfUboJbWh1aN\r\n" + 
			"Ufp07PdD8sCYbuVUupSZY72rIXRChZidQa8j7cMN4i3NlKNHRsG5Mf/W5/RKBP5P\r\n" + 
			"Knz5IaZdQWvvdWMTMnLyJuKuPc/Xwt9CSnIF+50fC1lk68EpHsu87nfGN6bT1WCp\r\n" + 
			"cU7NoamYPIKSADyer4qemRXUtAkVzEJzCIsM1H7z7dlJohXALL3Jgi1oZpE2qJli\r\n" + 
			"z49ItDK7kA3BsvmbNgj/BkRWgs4xRtEJ7WvSlouJzShPDGRrvt4GX1ja0K/QJPIR\r\n" + 
			"J8f/8UKp6Rk0/4+znpEgcZ+E9lmXxA20vPUbG+S9lN3zck9jEE09AJ3zGZ01CpNB\r\n" + 
			"YwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----"; 
}

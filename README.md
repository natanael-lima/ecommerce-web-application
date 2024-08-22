# Proyecto E-Commerce Griiin

**Descripción**  
Proyecto Griiin es una plataforma de comercio electrónico con un panel de control para gestionar usuarios, categorías y productos destacados. Ofrece una función de búsqueda para encontrar artículos populares y filtros para ordenar productos por categoría o precio, brindando una experiencia de compra fluida.

## Tecnologías

- **Backend:**
  - [Java](https://www.oracle.com/java/)
  - [Spring Boot](https://spring.io/projects/spring-boot): Marco de trabajo para el desarrollo de aplicaciones Java.
  - [Spring Security](https://spring.io/projects/spring-security): Framework para la seguridad de aplicaciones Java.
  - [Spring Data](https://spring.io/projects/spring-data): Facilita la implementación de repositorios y el acceso a datos.
  - [JPA](https://www.hibernate.org/): Especificación de Java para el mapeo objeto-relacional.
  - [JWT](https://jwt.io/): Método de autenticación basada en tokens JSON.

- **Frontend:**
  - [Angular 17](https://angular.io/): Framework para desarrollar aplicaciones web.
  - [HTML](https://developer.mozilla.org/en-US/docs/Web/HTML): Lenguaje de marcado para estructurar las páginas web.
  - [CSS](https://developer.mozilla.org/en-US/docs/Web/CSS): Lenguaje de hojas de estilo para el diseño de páginas web.

- **Base de Datos:**
  - [MySQL](https://www.mysql.com/): Sistema de gestión de bases de datos relacional utilizado para almacenar información de usuarios, productos y categorías.

- **Herramientas de Desarrollo:**
  - [Postman](https://www.postman.com/): Herramienta para probar y desarrollar APIs REST.

## Instalación y Configuración

### Configuración del Backend

1. **Configura el archivo `application.properties` con los detalles de tu base de datos MySQL.**

2. **Ejecuta la aplicación Spring Boot:**
   ```bash
   mvn spring-boot:run

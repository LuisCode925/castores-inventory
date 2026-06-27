# Prueba tecnica

- IDE: Visual Studio Code (Extensiones abajo)
  - angular.ng-template
  - astro-build.astro-vscode
  - bmewburn.vscode-intelephense-client
  - bradlc.vscode-tailwindcss
  - burkeholland.simple-react-snippets
  - esbenp.prettier-vscode
  - gruntfuggly.todo-tree
  - inu1255.easy-snippet
  - mechatroner.rainbow-csv
  - ms-azuretools.vscode-containers
  - ms-azuretools.vscode-docker
  - ms-python.debugpy
  - ms-python.python
  - ms-python.vscode-pylance
  - ms-python.vscode-python-envs
  - phproberto.vscode-php-getters-setters
  - pkief.material-icon-theme
  - quicktype.quicktype
  - redhat.java
  - redhat.vscode-xml
  - redhat.vscode-yaml
  - streetsidesoftware.code-spell-checker
  - streetsidesoftware.code-spell-checker-spanish
  - svelte.svelte-vscode
  - unifiedjs.vscode-mdx
  - vmware.vscode-boot-dev-pack
  - vmware.vscode-spring-boot
  - vscjava.vscode-gradle
  - vscjava.vscode-java-debug
  - vscjava.vscode-java-dependency
  - vscjava.vscode-java-pack
  - vscjava.vscode-java-test
  - vscjava.vscode-maven
  - vscjava.vscode-spring-boot-dashboard
  - vscjava.vscode-spring-initializr
  - xdebug.php-debug
  - xdebug.php-pack
- Java 25 con Spring Boot 4.1.0.
- Base de datos: MySQL version 8.0 y Phpmyadmin con docker.
- Es necesario para en frontend tener el [Angular CLI] (https://angular.dev/tools/cli/setup-local)
- Versiones Espesificas: Docker version 27.4.1 (build b9d17ea), Java 25 (openjdk 25.0.3 2026-04-21), NodeJS v24.15.0, pnpm 11.9.0, Angular CLI 22.0.4

## Setup

El proyecto cuenta con 2 partes una es que maneja el API REST que esta escrito en Java utilizando Spring Boot y la parte del Frontend
que esta escrita en Angular 22 por lo que seran 2 repositorios de codigo.

### IMPORTANTE: El proyecto utilizara los siguientes puertos del localhost: 8080, 8090, 4200

1. En un directorio de trabajo clone ambos repositorios: inventory y inventory-front
2. Primero se levanta la parte del backend, abre una terminal dentro de la carpeta **inventory/** y ejecuta el comando `docker compose up -d`

- La terminacion **-d** es para liberar la misma termial ejecutando los contenedores en segundo plano.
- Para eliminar los contenedores se debe estar sobre el mismo diorectorio **inventory/** ya que ahi esta el archivo **compose.yaml** y ejecutar `docker compose down -v`

3. Para ejecutar el proyecto se utilizara directamente maven `mvn spring-boot:run`, en vez de ejecutarlo desde un IDE.
4. No es necesario crear el schema de la base de datos ya que Spring Boot ejecuta las sentencias SQL del archivo **src/main/resources/data.sql**
5. Para comprobar que la base de datos se inicializo correctamente se puede ver el log del ultimo comando y tambioen comprobando llendo al contenedor
   de phpMyAdmin corriendo en **http://localhost:8080/** este contenedor tiene un formulario para conectarse con las siguientes credenciales de prueba:
   - Servidor: mysql
   - Usuario: inventory_manager
   - Contraseña: password
6. Si todo esta correcto en la parte izquierda deveria aparecer la base de datos "inventory" con 6 tablas en total y algunos datos de prueba.
7. Con otra terminal se abre el directorio **inventory-front/** primero se descargan las dependencias del proyecto con `pnpm install`.
8. Posteriormente mediante el Angular CLI se ejecuta `ng serve -o` que abrira la pagina en el navegador.
9. En la base de datos estan 3 usuarios con diferentes roles siendo estos ADMINISTRATOR, WAREHOUSEMAN, USER
   - Los usuarios tienen los siguinetes correos: admin@gmail.com -> ADMINISTRATOR, worker@gmail.com -> WAREHOUSEMAN, user@gmail.com -> USER
   - Todos comparten las misma contraseña para facilitar las pruebas: **925lolXD$**

## ESCENARIO

El sistema tendrá 2 roles y los permisos de los mismos se describen a continuación:

| Permiso                                  | Administrador | Almacenista |
| :--------------------------------------- | :-----------: | :---------: |
| Ver módulo inventario (UI)               |       ✓       |      ✓      |
| Agregar nuevos productos                 |       ✓       |      X      |
| Aumentar inventario                      |       ✓       |      X      |
| Dar de baja/reactivar un producto        |       ✓       |      X      |
| Ver módulo para Salida de productos (UI) |       X       |      ✓      |
| Sacar inventario del almacén             |       X       |      ✓      |
| Ver módulo del histórico (UI)            |       ✓       |      X      |

Administrador

- Ver módulo inventario (UI)
  1. Ver todos los productos [GET "/api/v1/products"] \*
  2. Agregar nuevos productos [POST "/api/v1/products"]
  3. Aumentar inventario [PATCH "/api/v1/products/{productId}"]
  4. Reactivar un producto [PATCH "/api/v1/products/{productId}/activate"]
  5. Dar de baja un producto [DELETE "/api/v1/products/{productId}"]

- Ver módulo del histórico (UI)
  1. Todas las transacciones [GET "/api/v1/transactions"]
  2. Filtrar las transacciones por tipo [GET "/api/v1/transactions/{type}"]

Almacenista

- Ver módulo inventario (UI)
  1. Ver todos los productos [GET "/api/v1/products"] \*
- Ver módulo para Salida de productos (UI)
  1. Se SOLO los productos activos [GET "/api/v1/products/actives"]
  2. Sacar inventario del almacén [PATCH "/api/v1/products"]

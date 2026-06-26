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

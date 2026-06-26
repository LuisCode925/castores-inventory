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

- Ver módulo inventario (UI - GET)
- Agregar nuevos productos
- Aumentar inventario
- Dar de baja/reactivar un producto
- Ver módulo del histórico (UI - GET)

Almacenista

- Ver módulo inventario (UI - GET)
- Ver módulo para Salida de productos (UI - GET)
- Sacar inventario del almacén

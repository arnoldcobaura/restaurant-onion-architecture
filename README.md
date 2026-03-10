# Restaurant Onion Architecture

Proyecto de ejemplo que implementa **Clean Architecture (Arquitectura Limpia)** con el patrón **Onion Architecture** usando Spring Boot.

## 📋 Descripción

Sistema de gestión de pedidos de restaurante que demuestra los principios de arquitectura limpia:
- Separación clara de capas (Domain, Application, Infrastructure, Interfaces)
- Inversión de dependencias
- Lógica de negocio independiente de frameworks
- Fácil de testear y mantener

## 🏗️ Estructura del Proyecto

```
src/main/java/com/empresa/restaurant_onion_architecture/
├── domain/                          # Capa de Dominio (Núcleo)
│   ├── Cliente.java                # Entidad de negocio
│   ├── Pedido.java                 # Entidad de negocio
│   ├── Plato.java                  # Entidad de negocio
│   └── interfaces/
│       └── IPedidoRepository.java   # Interfaz de repositorio
├── application/                     # Capa de Aplicación (Casos de uso)
│   └── PedidoService.java          # Servicio de aplicación
├── infrastructure/                  # Capa de Infraestructura
│   └── persistence/
│       └── MySQLPedidoRepository.java  # Implementación del repositorio
├── adapter/                         # Capa de Adaptadores
│   └── controllers/
│       └── PedidoController.java    # Controlador REST
└── RestaurantOnionArchitectureApplication.java  # Clase principal de Spring Boot
```

## 🎯 Principios de Clean Architecture

### 1. **Domain Layer (Capa de Dominio)**
Contiene la lógica de negocio pura:
- `Cliente`: Entidad que representa un cliente
- `Pedido`: Entidad que representa un pedido con validaciones
- `Plato`: Entidad que representa un plato
- `IPedidoRepository`: Interfaz que define el contrato de persistencia

**Características:**
- No depende de ningún framework
- Contiene las reglas de negocio
- Validaciones en la entidad (no se puede confirmar un pedido vacío)
- Las interfaces de repositorio están en `domain/interfaces/` para mantener la inversión de dependencias

### 2. **Application Layer (Capa de Aplicación)**
Implementa los casos de uso del sistema:
- `PedidoService`: Orquesta las operaciones sobre pedidos
- Depende de `IPedidoRepository` (interfaz del domain)
- Valida que los pedidos existan antes de operar
- Coordina entre el domain y la infraestructura

### 3. **Infrastructure Layer (Capa de Infraestructura)**
Implementa los detalles técnicos:
- `MySQLPedidoRepository`: Implementa `IPedidoRepository` del domain
- Maneja la persistencia (actualmente en memoria con HashMap)
- Asigna IDs automáticamente en la primera inserción
- Gestiona el almacenamiento de datos

### 4. **Adapter Layer (Capa de Adaptadores)**
Expone la API REST:
- `PedidoController`: Controlador REST
- Recibe requests HTTP
- Retorna respuestas JSON
- Inyecta `PedidoService` por constructor

## 🔄 Flujo de Dependencias

```
Adapter (Controllers)
    ↓ depende de
Application (Services)
    ↓ depende de
Domain (Interfaces/Abstracciones)
    ↑ implementada por
Infrastructure (Implementaciones)
```

**Inversión de Dependencias (DIP):** 
- Las capas internas (Domain) nunca dependen de las externas (Infrastructure, Adapter)
- Infrastructure implementa las interfaces definidas en Domain
- Application depende de abstracciones (IPedidoRepository), no de implementaciones concretas
- Adapter depende de Application, no de Infrastructure directamente
- Esto permite cambiar la implementación sin afectar el resto del código

## 🚀 Endpoints API

### 1. Crear un nuevo pedido
```bash
curl -X POST http://localhost:8080/pedidos
```

**Respuesta:**
```json
{
  "id": 1,
  "platos": [],
  "confirmado": false
}
```

El servidor asigna automáticamente el ID al pedido en la primera inserción.

### 2. Agregar un plato al pedido
```bash
curl -X POST http://localhost:8080/pedidos/1/platos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pasta","precio":15.5}'
```

**Respuesta:**
```json
{
  "id": 1,
  "platos": [
    {
      "nombre": "Pasta",
      "precio": 15.5
    }
  ],
  "confirmado": false
}
```

Ahora el endpoint acepta el plato desde el request body en lugar de hardcodearlo.

### 3. Confirmar un pedido
```bash
curl -X POST http://localhost:8080/pedidos/1/confirmar
```

**Respuesta:**
```json
{
  "id": 1,
  "platos": [
    {
      "nombre": "Pasta",
      "precio": 15.5
    }
  ],
  "confirmado": true
}
```

El estado `confirmado` cambia a `true` cuando se confirma el pedido.

### 4. Obtener un pedido
```bash
curl -X GET http://localhost:8080/pedidos/1
```

**Respuesta:**
```json
{
  "id": 1,
  "platos": [
    {
      "nombre": "Pasta",
      "precio": 15.5
    }
  ],
  "confirmado": true
}
```

Retorna el pedido completo con todos sus datos.

## 📦 Dependencias

- **Spring Boot 3.5.11**
- **Java 17**
- **Maven 3.6+**
- **Lombok** (para reducir boilerplate)
- **Spring Boot DevTools** (para desarrollo)
- **Spring Boot Test** (para testing)

## 🛠️ Requisitos

- JDK 17 o superior
- Maven 3.6+

## ⚙️ Instalación y Ejecución

### 1. Clonar o descargar el proyecto
```bash
cd restaurant-onion-architecture
```

### 2. Compilar el proyecto
```bash
mvn clean install
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 💡 Detalles de Implementación

### Inyección de Dependencias
- `PedidoController` recibe `PedidoService` por constructor
- `PedidoService` recibe `IPedidoRepository` por constructor
- Spring Boot gestiona automáticamente las inyecciones con `@Service`

### Almacenamiento de Datos
- Actualmente utiliza **HashMap en memoria** para persistencia
- Los datos se pierden al reiniciar la aplicación
- Preparado para migrar a MySQL o cualquier otra base de datos sin cambiar la lógica de negocio

### Asignación de IDs
- Los IDs se asignan automáticamente en la clase `MySQLPedidoRepository`
- Se incrementan secuencialmente desde 1
- Cada pedido tiene un ID único

## 🧪 Validaciones de Negocio

El sistema implementa las siguientes validaciones en la capa de dominio y aplicación:

1. **No se puede agregar platos a un pedido confirmado**
   - Ubicación: `Pedido.agregarPlato()`
   - Lanza: `RuntimeException: El pedido ya está confirmado`
   - Valida que el estado del pedido sea consistente

2. **No se puede confirmar un pedido vacío**
   - Ubicación: `Pedido.confirmar()`
   - Lanza: `RuntimeException: No se puede confirmar un pedido vacío`
   - Asegura que todo pedido tenga al menos un plato

3. **No se puede operar sobre un pedido inexistente**
   - Ubicación: `PedidoService` (métodos: agregarPlato, confirmarPedido, obtenerPedido)
   - Lanza: `RuntimeException: Pedido no encontrado con id: {id}`
   - Evita operaciones sobre pedidos que no existen en la base de datos
   - Proporciona feedback claro al usuario

## 🧪 Testing

El proyecto incluye **Spring Boot Test** para escribir pruebas unitarias e integración:

```bash
mvn test
```

### Recomendaciones para Testing
- **Tests unitarios**: Probar la lógica de negocio en `Pedido` sin dependencias
- **Tests de servicio**: Probar `PedidoService` con mocks de `IPedidoRepository`
- **Tests de integración**: Probar los endpoints REST con `@SpringBootTest`
- **Cobertura**: Apuntar a >80% de cobertura en las capas de Domain y Application

## 🔮 Mejoras Futuras

- [ ] Integración con base de datos MySQL real (H2 para desarrollo)
- [ ] Manejo de excepciones personalizadas (crear `PedidoNotFoundException`, `PedidoInvalidoException`)
- [ ] Tests unitarios para Domain, Application e Infrastructure
- [ ] Tests de integración para los endpoints REST
- [ ] Validación de datos con Bean Validation (@NotNull, @NotBlank, etc.)
- [ ] Documentación Swagger/OpenAPI
- [ ] Autenticación y autorización con Spring Security
- [ ] Logging y monitoreo con SLF4J y Logback
- [ ] Transacciones ACID con JPA/Hibernate
- [ ] Manejo de errores global con @ControllerAdvice
- [ ] DTOs (Data Transfer Objects) para separar la API del domain
- [ ] Paginación y filtrado en listados
- [ ] Entidad Cliente integrada con Pedido
- [ ] Cálculo de total de pedidos
- [ ] Historial de cambios en pedidos

## 📚 Conceptos Clave

### Clean Architecture
Arquitectura que prioriza la independencia de frameworks y la testabilidad. Las reglas de negocio están en el centro y no dependen de detalles técnicos.

**Beneficios:**
- Código más testeable
- Fácil de mantener y escalar
- Independencia de frameworks
- Cambios en la infraestructura no afectan la lógica de negocio

### Onion Architecture
Variante de clean architecture donde las capas se organizan en "capas de cebolla" con el dominio en el centro.

**Estructura:**
- El dominio es el núcleo
- Cada capa externa depende de la capa interna
- Las dependencias siempre apuntan hacia adentro

### Dependency Inversion Principle (DIP)
Las clases de alto nivel no dependen de clases de bajo nivel. Ambas dependen de abstracciones (interfaces).

**En este proyecto:**
- `PedidoService` (Application) depende de `IPedidoRepository` (Domain)
- `MySQLPedidoRepository` (Infrastructure) implementa `IPedidoRepository`
- Esto permite cambiar la implementación sin modificar el servicio

### Repository Pattern
Patrón que abstrae la persistencia, permitiendo cambiar la implementación sin afectar la lógica de negocio.

**En este proyecto:**
- `IPedidoRepository` define el contrato
- `MySQLPedidoRepository` implementa la persistencia
- Se puede cambiar a JPA, MongoDB, etc. sin afectar el servicio

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👨‍💻 Autor

Proyecto de ejemplo para aprender Clean Architecture con Spring Boot.

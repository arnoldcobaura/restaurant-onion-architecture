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
│   ├── Pedido.java                 # Entidad de negocio
│   ├── Plato.java                  # Entidad de negocio
│   └── PedidoRepository.java        # Interfaz de repositorio
├── application/                     # Capa de Aplicación (Casos de uso)
│   └── PedidoService.java          # Servicio de aplicación
├── infrastructure/                  # Capa de Infraestructura
│   └── persistence/
│       └── MySQLPedidoRepository.java  # Implementación del repositorio
└── interfaces/                      # Capa de Interfaces (Controladores)
    └── controllers/
        └── PedidoController.java    # Controlador REST
```

## 🎯 Principios de Clean Architecture

### 1. **Domain Layer (Capa de Dominio)**
Contiene la lógica de negocio pura:
- `Pedido`: Entidad que representa un pedido con validaciones
- `Plato`: Entidad que representa un plato
- `PedidoRepository`: Interfaz que define el contrato de persistencia

**Características:**
- No depende de ningún framework
- Contiene las reglas de negocio
- Validaciones en la entidad (no se puede confirmar un pedido vacío)

### 2. **Application Layer (Capa de Aplicación)**
Implementa los casos de uso del sistema:
- `PedidoService`: Orquesta las operaciones sobre pedidos
- Valida que los pedidos existan antes de operar
- Coordina entre el domain y la infraestructura

### 3. **Infrastructure Layer (Capa de Infraestructura)**
Implementa los detalles técnicos:
- `MySQLPedidoRepository`: Implementa la persistencia (actualmente en memoria)
- Maneja la asignación de IDs
- Gestiona el almacenamiento de datos

### 4. **Interfaces Layer (Capa de Interfaces)**
Expone la API REST:
- `PedidoController`: Controlador REST
- Recibe requests HTTP
- Retorna respuestas JSON

## 🔄 Flujo de Dependencias

```
Interfaces (Controllers)
    ↓ depende de
Application (Services)
    ↓ depende de
Domain (Interfaces/Abstracciones)
    ↓ implementada por
Infrastructure (Implementaciones)
```

**Inversión de Dependencias:** Las capas internas (Domain) nunca dependen de las externas (Infrastructure, Interfaces).

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

## 📦 Dependencias

- **Spring Boot 3.5.11**
- **Java 17**
- **Maven**
- **Lombok** (opcional, para reducir boilerplate)

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

## 🧪 Validaciones de Negocio

El sistema implementa las siguientes validaciones:

1. **No se puede agregar platos a un pedido confirmado**
   - Lanza: `RuntimeException: El pedido ya está confirmado`

2. **No se puede confirmar un pedido vacío**
   - Lanza: `RuntimeException: No se puede confirmar un pedido vacío`

3. **No se puede operar sobre un pedido inexistente**
   - Lanza: `RuntimeException: Pedido no encontrado con id: {id}`

## Mejoras Futuras

- [ ] Integración con base de datos MySQL real (H2 para desarrollo)
- [ ] Manejo de excepciones personalizadas
- [ ] Tests unitarios y de integración
- [ ] Validación de datos con Bean Validation
- [ ] Documentación Swagger/OpenAPI
- [ ] Autenticación y autorización
- [ ] Logging y monitoreo
- [ ] Transacciones ACID

## Conceptos Clave

### Clean Architecture
Arquitectura que prioriza la independencia de frameworks y la testabilidad. Las reglas de negocio están en el centro y no dependen de detalles técnicos.

### Onion Architecture
Variante de clean architecture donde las capas se organizan en "capas de cebolla" con el dominio en el centro.

### Dependency Inversion Principle (DIP)
Las clases de alto nivel no dependen de clases de bajo nivel. Ambas dependen de abstracciones (interfaces).

### Repository Pattern
Patrón que abstrae la persistencia, permitiendo cambiar la implementación sin afectar la lógica de negocio.

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👨‍💻 Autor

Proyecto de ejemplo para aprender Clean Architecture con Spring Boot.

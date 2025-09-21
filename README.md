# **Product Service**

## **Description:**

A comprehensive Spring Boot microservice for managing products in an e-commerce platform. The service provides CRUD operations for products, categories, and product attributes with advanced features like pagination, sorting, and filtering.

## **Features:**

- **Product Management**: Complete CRUD operations for products
- **Category Management**: Load and manage product categories from taxonomy files
- **Product Attributes**: Manage product-specific attributes and variants
- **Advanced Search**: Fetch products with pagination, sorting, and filtering capabilities
- **RESTful API**: Well-structured REST endpoints with proper HTTP status codes
- **MongoDB Integration**: NoSQL database for flexible product data storage
- **Docker Support**: Containerized deployment with multi-stage builds
- **Spring Boot 3.0**: Built on latest Spring Boot framework with Java 17

## **Technology Stack:**

- **Framework**: Spring Boot 3.0.7
- **Language**: Java 17
- **Database**: MongoDB
- **Build Tool**: Gradle
- **Container**: Docker with multi-stage builds
- **Libraries**: Lombok, Guava, Spring Data MongoDB

## **API Endpoints:**

### Product Endpoints
- `GET /` - Health check endpoint
- `POST /product` - Create a new product
- `PUT /product/{id}` - Update an existing product
- `GET /product/{id}` - Get product by ID
- `GET /product` - Get paginated product list with filtering and sorting
- `DELETE /product/{id}` - Delete a single product
- `DELETE /product` - Delete multiple products (batch delete)

### Category Endpoints
- `GET /load` - Load categories from taxonomy file

### Query Parameters for Product Listing
- `page` (default: 0) - Page number for pagination
- `pageSize` (default: 10) - Number of items per page
- `sortField` (default: "id") - Field to sort by
- `sortOrder` (default: 0) - Sort order (0=ascending, 1=descending)
- `filter` (default: "") - Filter string for product search

## **Prerequisites:**

- Java 17 SDK installed and configured in PATH
- MongoDB instance (local or remote)
- Gradle (wrapper included)

## **Configuration:**

The application supports multiple environment profiles:
- **Development**: `application-dev.properties`
- **Production**: `application-prod.properties`
- **Local**: `application-local.properties`

Default configuration in [`application.properties`](src/main/resources/application.properties:1):
```properties
spring.profiles.active=dev
server.port=8080
```

## **Usage:**

### Local Development
Make sure you have Java 17 SDK installed in your PATH:

```shell
# Run with Gradle wrapper
./gradlew bootRun

# Or run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Build Application
```shell
# Build the application
./gradlew build

# Run tests
./gradlew test
```

### Docker Deployment
```shell
# Build Docker image
docker build -t product-service .

# Run container
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev product-service
```

## **Database Setup:**

The service uses MongoDB as the primary database. Ensure MongoDB is running and accessible. The connection details can be configured in the respective profile properties files.

## **System Architecture**

### **Component Architecture**
```mermaid
graph TB
    subgraph "Client Layer"
        CLIENT[Web Client/Mobile App]
    end
    
    subgraph "API Layer"
        APIGATEWAY[API Gateway]
    end
    
    subgraph "Application Layer"
        PRODUCTSERVICE[Product Service<br/>Spring Boot 3.0]
        
        subgraph "Controllers"
            PRODCTRL[Product Controller]
            CATCTRL[Category Controller]
            ATTRCTRL[Attribute Controller]
        end
        
        subgraph "Business Logic"
            PRODSVC[Product Service]
            CATSVC[Category Service]
            ATTRSVC[Attribute Service]
        end
        
        subgraph "Data Access"
            PRODREPO[Product Repository]
            CATREPO[Category Repository]
            ATTRREPO[Attribute Repository]
        end
    end
    
    subgraph "Data Layer"
        MONGODB[(MongoDB<br/>NoSQL Database)]
    end
    
    CLIENT --> APIGATEWAY
    APIGATEWAY --> PRODUCTSERVICE
    PRODUCTSERVICE --> PRODCTRL
    PRODUCTSERVICE --> CATCTRL
    PRODUCTSERVICE --> ATTRCTRL
    
    PRODCTRL --> PRODSVC
    CATCTRL --> CATSVC
    ATTRCTRL --> ATTRSVC
    
    PRODSVC --> PRODREPO
    CATSVC --> CATREPO
    ATTRSVC --> ATTRREPO
    
    PRODREPO --> MONGODB
    CATREPO --> MONGODB
    ATTRREPO --> MONGODB
    
    style CLIENT fill:#4A90E2,stroke:#333,stroke-width:2px,color:#fff
    style APIGATEWAY fill:#50C878,stroke:#333,stroke-width:2px,color:#fff
    style PRODUCTSERVICE fill:#FF6B6B,stroke:#333,stroke-width:3px,color:#fff
    style MONGODB fill:#47A248,stroke:#333,stroke-width:2px,color:#fff
```

### **Request Flow Sequence Diagram**
```mermaid
sequenceDiagram
    participant Client
    participant API as API Gateway
    participant Controller as Product Controller
    participant Service as Product Service
    participant Repository as Product Repository
    participant DB as MongoDB
    
    Client->>API: HTTP Request (GET/POST/PUT/DELETE)
    API->>API: Validate Request & Authentication
    API->>Controller: Forward Request
    Controller->>Controller: Validate Input Parameters
    
    alt GET Request (Query Products)
        Controller->>Service: getProducts(filter, page, size, sort)
        Service->>Repository: findAllWithPagination()
        Repository->>DB: db.products.find()
        DB-->>Repository: Product List
        Repository-->>Service: Page<Product>
        Service-->>Controller: Products DTO
        Controller-->>API: ResponseEntity.ok()
        API-->>Client: HTTP 200 + JSON Response
    end
    
    alt POST Request (Create Product)
        Controller->>Service: saveProduct(product)
        Service->>Service: Validate Product Data
        Service->>Repository: save(product)
        Repository->>DB: db.products.insert()
        DB-->>Repository: Saved Product
        Repository-->>Service: Product with ID
        Service-->>Controller: Created Product
        Controller-->>API: ResponseEntity.created()
        API-->>Client: HTTP 201 + Location Header
    end
    
    alt Error Scenario
        Controller->>Controller: Exception Occurs
        Controller-->>API: ResponseStatusException
        API-->>Client: HTTP 4xx/5xx + Error Message
    end
    
    Note over Client,DB: All database operations are asynchronous<br/>and use Spring Data MongoDB repositories
```

## **Project Structure:**

```
src/
├── main/
│   ├── java/org/aztekcoder/ecommerce/productservice/
│   │   ├── controller/     # REST API controllers
│   │   ├── entity/         # Data models/entities
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   └── ProductApplication.java  # Main application class
│   └── resources/
│       ├── application*.properties  # Configuration files
│       └── taxonomy-with-ids.en-US.txt  # Category taxonomy data
└── test/                   # Unit and integration tests
```

## **Error Handling:**

The service implements comprehensive error handling with appropriate HTTP status codes:
- `200 OK` - Successful operations
- `201 Created` - Resource creation successful
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server-side errors

## **Testing:**

Run the test suite using:
```shell
./gradlew test
```

The project includes unit tests for core functionality and integration tests for API endpoints.

## **Deployment:**

The application can be deployed using:
1. **Traditional deployment**: Build JAR and run with Java
2. **Docker containers**: Use provided Dockerfile for containerized deployment
3. **Cloud platforms**: Compatible with AWS ECS, Kubernetes, and other container orchestrators

## **Monitoring and Logging:**

The service includes Spring Boot Actuator endpoints for health checks and monitoring (when enabled).


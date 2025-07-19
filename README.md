# CRFA Metadata Service

A Micronaut-based service that serves metadata from the [CRFA Offchain Data Registry](https://github.com/Cardano-Fans/crfa-offchain-data-registry).

## Prerequisites

- **JDK 24**
- **Maven 3.9+**

## Quick Start

### Development Mode

For development with automatic reload and enhanced debugging:

```bash
# Method 1: Direct Maven execution with automatic reloading (recommended)
mvn mn:run

# Method 2: Compile and run with continuous compilation
mvn compile exec:exec

# Method 3: Package and run with development profile
mvn clean package
java -Dmicronaut.environments=dev -jar target/crfa-metadata-service-0.9.1.jar
```

**Development Features:**
- Automatic code reloading (when using `mvn mn:run`)
- Continuous compilation
- Enhanced logging and debugging
- Development-friendly error pages
- Hot reload for configuration changes
- Port: `8082` (configured in application.yml)

**Development Workflow:**
1. Start with `mvn mn:run`
2. Make code changes in your IDE
3. Save files - changes are automatically reloaded
4. Test immediately without restart

### Production Mode

For production deployment:

```bash
# Build optimized package
mvn clean package -Dmicronaut.environments=prod

# Run in production mode
java -Dmicronaut.environments=prod \
     -Xms512m -Xmx1024m \
     -server \
     -jar target/crfa-metadata-service-0.9.1.jar
```

**Production Features:**
- Optimized JVM settings
- Minimal logging
- Production error handling
- Environment-specific configuration

## Building

```bash
# Clean build
mvn clean compile

# Full package with tests
mvn clean package

# Skip tests (faster build)
mvn clean package -DskipTests
```

## Running

### Local Development
```bash
# Method 1: Direct Maven execution (recommended for development)
mvn mn:run

# Method 2: Traditional jar execution
java -jar target/crfa-metadata-service-0.9.1.jar
```

### Docker (Production)
```bash
# Build native image (if configured)
mvn clean package -Dpackaging=docker-native

# Run with Docker
docker run -p 8082:8082 crfa-metadata-service
```

## Configuration

### Environment Variables

| Variable | Description | Default | Example |
|----------|-------------|---------|---------|
| `MICRONAUT_ENVIRONMENTS` | Active environment profile | none | `dev`, `prod` |
| `MICRONAUT_SERVER_PORT` | Server port | `8082` | `8080` |
| `JAVA_OPTS` | JVM options | none | `-Xmx2g -Xms1g` |

### Profiles

- **dev**: Development mode with enhanced debugging
- **prod**: Production mode with optimized settings
- **test**: Test environment configuration

## API Usage

### Health Check
```bash
curl http://localhost:8082/health
```

### Metadata Lookup
```bash
# Get metadata by hash
curl http://localhost:8082/metadata/by-hash/e45605e3f7d131723422c67353a3d2e0cccc06192e2e92efab9c8deb | jq "."

# Health and readiness checks
curl http://localhost:8082/health/liveness
curl http://localhost:8082/health/readiness
```

## Development Workflow

1. **Start development server:**
   ```bash
   mvn mn:run
   ```

2. **Make code changes** - the server will automatically reload

3. **Test your changes:**
   ```bash
   curl http://localhost:8082/health
   ```

4. **Run tests:**
   ```bash
   mvn test
   ```

## Deployment

### Production Checklist

- [ ] Set `MICRONAUT_ENVIRONMENTS=prod`
- [ ] Configure appropriate JVM heap size
- [ ] Set up monitoring and logging
- [ ] Configure reverse proxy (nginx/Apache)
- [ ] Set up health checks
- [ ] Configure SSL/TLS termination

### Systemd Service (Linux)

Create `/etc/systemd/system/crfa-metadata-service.service`:

```ini
[Unit]
Description=CRFA Metadata Service
After=network.target

[Service]
Type=simple
User=crfa
ExecStart=/usr/bin/java -Dmicronaut.environments=prod \
          -Xms512m -Xmx1024m \
          -jar /opt/crfa/crfa-metadata-service-0.9.1.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

## Technology Stack

- **Framework**: Micronaut 4.9.0
- **Java**: JDK 24
- **Build Tool**: Maven 3.9+
- **Server**: Netty (embedded)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `mvn test`
5. Submit a pull request

## License

MIT License - see [LICENSE.md](LICENSE.md) for details.

Copyright 2025 Cardano Fans

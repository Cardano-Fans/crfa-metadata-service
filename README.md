# CRFA Metadata Service

A Micronaut-based service that serves metadata from the [CRFA Offchain Data Registry](https://github.com/Cardano-Fans/crfa-offchain-data-registry).

## Prerequisites

- **JDK 24**
- **Gradle 8.3+**

## Quick Start

### Development Mode

For development with automatic reload and enhanced debugging:

```bash
# Method 1: Direct Gradle execution with automatic reloading (recommended)
./gradlew run --continuous

# Method 2: Build and run with development profile
./gradlew build
java -Dmicronaut.environments=dev -jar build/libs/crfa-metadata-service-0.10.0-all.jar
```

**Development Features:**
- Automatic code reloading (when using `--continuous`)
- Continuous compilation
- Enhanced logging and debugging
- Development-friendly error pages
- Hot reload for configuration changes
- Port: `8082` (configured in application.yml)

**Development Workflow:**
1. Start with `./gradlew run --continuous`
2. Make code changes in your IDE
3. Save files - changes are automatically reloaded
4. Test immediately without restart

### Production Mode

For production deployment:

```bash
# Build optimized package
./gradlew shadowJar

# Run in production mode
java -Dmicronaut.environments=prod \
     -Xms512m -Xmx1024m \
     -server \
     -jar build/libs/crfa-metadata-service-0.10.0-all.jar
```

**Production Features:**
- Optimized JVM settings
- Minimal logging
- Production error handling
- Environment-specific configuration

## Building

```bash
# Clean build
./gradlew clean build

# Full package with tests
./gradlew shadowJar

# Skip tests (faster build)
./gradlew shadowJar -x test

# Build native image
./gradlew nativeCompile
```

## Running

### Local Development
```bash
# Method 1: Direct Gradle execution (recommended for development)
./gradlew run

# Method 2: Traditional jar execution
java -jar build/libs/crfa-metadata-service-0.10.0-all.jar
```

### Docker (Production)
```bash
# Build JVM Docker image
docker build --build-arg VERSION=0.10.0 -f Dockerfile.jvm -t crfa-metadata-service:jvm .

# Build native Docker image
docker build --build-arg VERSION=0.10.0 -f Dockerfile.native -t crfa-metadata-service:native .

# Run with Docker (with restart policy)
docker run -d --name crfa-metadata-service -p 8082:8082 --restart unless-stopped crfa-metadata-service:jvm
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
   ./gradlew run --continuous
   ```

2. **Make code changes** - the server will automatically reload

3. **Test your changes:**
   ```bash
   curl http://localhost:8082/health
   ```

4. **Run tests:**
   ```bash
   ./gradlew test
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
          -jar /opt/crfa/crfa-metadata-service-0.10.0-all.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

## Technology Stack

- **Framework**: Micronaut 4.9.1
- **Java**: JDK 24
- **Build Tool**: Gradle 8.3+
- **Server**: Netty (embedded)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `./gradlew test`
5. Submit a pull request

## License

MIT License - see [LICENSE.md](LICENSE.md) for details.

Copyright 2025 Cardano Fans
